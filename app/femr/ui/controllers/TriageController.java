package femr.ui.controllers;

import com.google.inject.Inject;
import femr.common.dto.CurrentUser;
import femr.common.dto.ServiceResponse;
import femr.business.services.IPhotoService;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.data.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.common.models.VitalItem;
import femr.ui.models.triage.*;
import femr.ui.views.html.triage.index;
import femr.util.stringhelpers.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import play.data.Form;
import play.mvc.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class TriageController extends Controller {

    private final Form<IndexViewModelPost> IndexViewModelForm = Form.form(IndexViewModelPost.class);
    private ITriageService triageService;
    private ISessionService sessionService;
    private ISearchService searchService;
    private IPhotoService photoService;

    @Inject
    public TriageController(ITriageService triageService,
                            ISessionService sessionService,
                            ISearchService searchService,
                            IPhotoService photoService) {
        this.triageService = triageService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.photoService = photoService;
    }

    public Result indexGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        //retrieve all the vitals in the database so we can dynamically name
        //the vitals in the view
        ServiceResponse<List<VitalItem>> vitalServiceResponse = triageService.findAllVitalItems();
        if (vitalServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        //initalize an empty patient
        PatientItem patientItem = new PatientItem();

        IndexViewModelGet viewModelGet = new IndexViewModelGet();
        viewModelGet.setVitalNames(vitalServiceResponse.getResponseObject());
        viewModelGet.setPatient(patientItem);
        viewModelGet.setSearchError(false);

        return ok(index.render(currentUser, viewModelGet));
    }

    /*
    Used when user has searched for an existing patient
    and wants to create a new encounter
     */
    public Result indexPopulatedGet() {
        boolean searchError = false;
        PatientItem patient = new PatientItem();

        //get user that is currently logged in
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        //retrieve vitals names for dynamic html element naming
        ServiceResponse<List<VitalItem>> vitalServiceResponse = triageService.findAllVitalItems();
        if (vitalServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        //retrieve patient id from query string and search for the patient
        //error will be returned from business layer if there is anything
        //wrong with the query string and/or patient
        ServiceResponse<PatientItem> patientServiceResponse = searchService.findPatientItemById(
                Integer.valueOf(
                        request().getQueryString("id").trim()
                )
        );
        if (patientServiceResponse.hasErrors()) {
            searchError = true;
        } else {
            patient = patientServiceResponse.getResponseObject();
        }

        //create the view model
        IndexViewModelGet viewModelGet = new IndexViewModelGet();
        viewModelGet.setVitalNames(vitalServiceResponse.getResponseObject());
        viewModelGet.setPatient(patient);
        viewModelGet.setSearchError(searchError);

        return ok(index.render(currentUser, viewModelGet));
    }

    /*
   *if id is 0 then it is a new patient and a new encounter
   * if id is > 0 then it is only a new encounter
    */
    public Result indexPost(int id) {
        IndexViewModelPost viewModel = IndexViewModelForm.bindFromRequest().get();
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        //create a new patient
        //or get current patient for new encounter
        ServiceResponse<PatientItem> patientServiceResponse;
        PatientItem patientItem;
        if (id == 0) {
            patientItem = populatePatientItem(viewModel, currentUser);
            patientServiceResponse = triageService.createPatient(patientItem);
        } else {
            patientServiceResponse = triageService.findPatientAndUpdateSex(id, viewModel.getSex());
        }
        if (patientServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        patientItem = patientServiceResponse.getResponseObject();

        File photoFile = null;
        if (StringUtils.isNotNullOrWhiteSpace(viewModel.getPatientPhotoCropped())) {
            //we have received a base64 encoded data URI for a picture
            //parse the actual data URI
            String parsedImage = viewModel.getPatientPhotoCropped().substring(viewModel.getPatientPhotoCropped().indexOf(",") + 1);
            BufferedImage bufferedImage = decodeToImage(parsedImage);
            photoFile = new File("image.jpg");
            try {
                ImageIO.write(bufferedImage, "jpg", photoFile);
            } catch (Exception ex) {
                //couldn't save the image
            }
        } else {
            //this happens if we don't receieve a base64 encoded data URI
            //it can be because there is no picture or because javascript is disabled.
            Http.MultipartFormData.FilePart fpPhoto = request().body().asMultipartFormData().getFile("patientPhoto");
            if (fpPhoto != null)
                photoFile = fpPhoto.getFile();
        }

        photoService.SavePatientPhotoAndUpdatePatient(photoFile, patientItem.getId(), viewModel.getDeletePhoto());


        //create and save a new encounter
        PatientEncounterItem patientEncounterItem = populatePatientEncounterItem(
                viewModel,
                currentUser,
                patientServiceResponse.getResponseObject().getId()
        );
        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = triageService.createPatientEncounter(patientEncounterItem);
        if (patientEncounterServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            patientEncounterItem = patientEncounterServiceResponse.getResponseObject();
        }

        //save new vitals - check to make sure the vital exists
        //before putting it in the map. This can be done more
        //efficiently with JSON from the view
        Map<String, Float> newVitals = new HashMap<>();
        if (viewModel.getRespiratoryRate() != null) {
            newVitals.put("respiratoryRate", viewModel.getRespiratoryRate().floatValue());
        }
        if (viewModel.getHeartRate() != null) {
            newVitals.put("heartRate", viewModel.getHeartRate().floatValue());
        }
        if (viewModel.getTemperature() != null) {
            newVitals.put("temperature", viewModel.getTemperature());
        }
        if (viewModel.getOxygenSaturation() != null) {
            newVitals.put("oxygenSaturation", viewModel.getOxygenSaturation());
        }
        if (viewModel.getHeightFeet() != null) {
            newVitals.put("heightFeet", viewModel.getHeightFeet().floatValue());
        }
        if (viewModel.getHeightInches() != null) {
            newVitals.put("heightInches", viewModel.getHeightInches().floatValue());
        }
        if (viewModel.getWeight() != null) {
            newVitals.put("weight", viewModel.getWeight());
        }
        if (viewModel.getBloodPressureSystolic() != null) {
            newVitals.put("bloodPressureSystolic", viewModel.getBloodPressureSystolic().floatValue());
        }
        if (viewModel.getBloodPressureDiastolic() != null) {
            newVitals.put("bloodPressureDiastolic", viewModel.getBloodPressureDiastolic().floatValue());
        }
        if (viewModel.getGlucose() != null) {
            newVitals.put("glucose", viewModel.getGlucose().floatValue());
        }

        ServiceResponse<List<VitalItem>> vitalServiceResponse = triageService.createPatientEncounterVitalItems(newVitals, currentUser.getId(), patientEncounterItem.getId());
        if (vitalServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        return redirect("/search/index?id=" + patientServiceResponse.getResponseObject().getId());
    }

    /**
     * Decodes a base64 encoded string to an image
     *
     * @param imageString base64 encoded string that has been parsed to only include imageBytes
     * @return the decoded image
     */
    private static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            Base64 newDecoder = new Base64();
            byte[] bytes = imageString.getBytes(Charset.forName("UTF-8"));
            imageByte = newDecoder.decode(bytes);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    private PatientItem populatePatientItem(IndexViewModelPost viewModelPost, CurrentUser currentUser) {
        PatientItem patient = new PatientItem();
        patient.setUserId(currentUser.getId());
        patient.setFirstName(viewModelPost.getFirstName());
        patient.setLastName(viewModelPost.getLastName());
        patient.setBirth(viewModelPost.getAge());
        patient.setSex(viewModelPost.getSex());
        patient.setAddress(viewModelPost.getAddress());
        patient.setCity(viewModelPost.getCity());

        return patient;
    }

    private PatientEncounterItem populatePatientEncounterItem(IndexViewModelPost viewModelPost, CurrentUser currentUser, int patientId) {
        PatientEncounterItem patientEncounter = new PatientEncounterItem();
        patientEncounter.setPatientId(patientId);
        patientEncounter.setUserId(currentUser.getId());
        patientEncounter.setDateOfVisit(DateTime.now());
        patientEncounter.setChiefComplaint(viewModelPost.getChiefComplaint());
        patientEncounter.setWeeksPregnant(viewModelPost.getWeeksPregnant());
        return patientEncounter;
    }

}
