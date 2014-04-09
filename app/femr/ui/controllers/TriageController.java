package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IPhotoService;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.*;
import femr.data.models.Photo;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.triage.CreateViewModelGet;
import femr.ui.models.triage.CreateViewModelPost;
import femr.ui.helpers.controller.TriageHelper;
import femr.ui.views.html.triage.index;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class TriageController extends Controller {

    private final Form<CreateViewModelPost> createViewModelForm = Form.form(CreateViewModelPost.class);
    private ITriageService triageService;
    private ISessionService sessionService;
    private ISearchService searchService;
    private TriageHelper triageHelper;
    private IPhotoService photoService;


    @Inject
    public TriageController(ITriageService triageService,
                            ISessionService sessionService,
                            ISearchService searchService,
                            TriageHelper triageHelper,
                            IPhotoService photoService) {

        this.triageService = triageService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.triageHelper = triageHelper;
        this.photoService = photoService;
    }

    public Result createGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<List<? extends IVital>> vitalServiceResponse = searchService.findAllVitals();
        if (vitalServiceResponse.hasErrors()) {
            return internalServerError();
        }

        CreateViewModelGet viewModelGet = triageHelper.populateViewModelGet(null, null, vitalServiceResponse.getResponseObject(), false);

        return ok(index.render(currentUser, viewModelGet));
    }

    /*
    Used when user has searched for an existing patient
    and wants to create a new encounter
     */
    public Result createPopulatedGet() {
        boolean searchError = false;

        CurrentUser currentUser = sessionService.getCurrentUserSession();

        IPatient patient = null;
        IPhoto patientPhoto = null;

        //retrieve patient id from query string
        String s_id = request().getQueryString("id");
        if (StringUtils.isNullOrWhiteSpace(s_id)) {
            searchError = true;
        } else {
            s_id = s_id.trim();
            Integer id = Integer.parseInt(s_id);
            ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);
            if (patientServiceResponse.hasErrors()) {
                searchError = true;
            } else {
                patient = patientServiceResponse.getResponseObject();
            }
        }

        if (patient != null)
            if (patient.getPhotoId() != null) {
                //fetch photo record:
                ServiceResponse<IPhoto> photoResponse = photoService.getPhotoById(patient.getPhotoId());
                if (!photoResponse.hasErrors())
                    patientPhoto = photoResponse.getResponseObject();
            }

        //retrieve vitals names for dynamic html element naming
        ServiceResponse<List<? extends IVital>> vitalServiceResponse = searchService.findAllVitals();
        if (vitalServiceResponse.hasErrors()) {
            return internalServerError();
        }

        CreateViewModelGet viewModelGet = triageHelper.populateViewModelGet(patient, patientPhoto, vitalServiceResponse.getResponseObject(), searchError);

        return ok(index.render(currentUser, viewModelGet));
    }

    /*
   *if id is 0 then it is a new patient and a new encounter
   * if id is > 0 then it is only a new encounter
    */
    public Result createPost(int id) {
        CreateViewModelPost viewModel = createViewModelForm.bindFromRequest().get();
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        //save new patient if new form
        //else find the patient to create a new encounter for
        ServiceResponse<IPatient> patientServiceResponse;
        IPatient patient;
        if (id == 0) {
            patient = triageHelper.getPatient(viewModel, currentUser);
            patientServiceResponse = triageService.createPatient(patient);
        } else {
            patientServiceResponse = searchService.findPatientById(id);
            patient = patientServiceResponse.getResponseObject();
            if (!StringUtils.isNullOrWhiteSpace(viewModel.getSex())) {
                patient.setSex(viewModel.getSex());
                patientServiceResponse = triageService.updatePatient(patient);
            }
        }
        if (patientServiceResponse.hasErrors()) {
            return internalServerError();
        }

        //Handle photo logic:
        try {
            Http.MultipartFormData.FilePart fpPhoto;
            File photoFile = null;
            fpPhoto = request().body().asMultipartFormData().getFile("patientPhoto");
            if (fpPhoto != null)
                photoFile = fpPhoto.getFile();

            photoService.HandlePatientPhoto(photoFile,
                    patientServiceResponse.getResponseObject(),
                    viewModel.getImageCoords(), viewModel.getDeletePhoto());
        } catch (Exception ex) {
        }

        //create and save a new encounter
        IPatientEncounter patientEncounter = triageHelper.getPatientEncounter(viewModel, currentUser, patientServiceResponse.getResponseObject());
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = triageService.createPatientEncounter(patientEncounter);
        if (patientEncounterServiceResponse.hasErrors()) {
            return internalServerError();
        }


        //save new vitals - check to make sure the vital exists
        //before putting it in the map
        Map<String, Float> newVitals = new HashMap<>();
        if (viewModel.getRespiratoryRate() != null) {
            newVitals.put("respiratoryRate", viewModel.getRespiratoryRate().floatValue());
        }
        if (viewModel.getHeartRate() != null) {
            newVitals.put("heartRate", viewModel.getHeartRate().floatValue());
        }
        if (viewModel.getTemperature() != null){
            newVitals.put("temperature", viewModel.getTemperature());
        }
        if (viewModel.getOxygenSaturation() != null){
            newVitals.put("oxygenSaturation", viewModel.getOxygenSaturation());
        }
        if(viewModel.getHeightFeet() != null){
            newVitals.put("heightFeet", viewModel.getHeightFeet().floatValue());
        }
        if (viewModel.getHeightInches() != null){
            newVitals.put("heightInches", viewModel.getHeightInches().floatValue());
        }
        if (viewModel.getWeight() != null){
            newVitals.put("weight", viewModel.getWeight());
        }
        if (viewModel.getBloodPressureSystolic() != null){
            newVitals.put("bloodPressureSystolic", viewModel.getBloodPressureSystolic().floatValue());
        }
        if (viewModel.getBloodPressureDiastolic() != null){
            newVitals.put("bloodPressureDiastolic", viewModel.getBloodPressureDiastolic().floatValue());
        }
        if (viewModel.getGlucose() != null){
            newVitals.put("glucose", viewModel.getGlucose().floatValue());
        }






        ServiceResponse<List<? extends IPatientEncounterVital>> vitalServiceResponse = triageService.createPatientEncounterVitals(newVitals, currentUser.getId(), patientEncounter.getId());
        if (vitalServiceResponse.hasErrors()) {
            return internalServerError();
        }

        return redirect("/show?id=" + patientServiceResponse.getResponseObject().getId());
    }


}
