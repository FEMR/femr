package femr.ui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import femr.business.dtos.*;
import femr.business.services.*;
import femr.common.models.IPhoto;
import femr.common.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.medical.*;
import femr.ui.models.medical.json.JCustomField;
import femr.ui.views.html.medical.index;
import femr.ui.views.html.medical.edit;
import femr.ui.views.html.medical.newVitals;
import femr.ui.views.html.medical.listVitals;
import femr.util.DataStructure.VitalMultiMap;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.MultipartFormData.FilePart;
import java.util.*;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class MedicalController extends Controller {

    private final Form<EditViewModelPost> createViewModelPostForm = Form.form(EditViewModelPost.class);
    private final Form<UpdateVitalsModel> updateVitalsModelForm = Form.form(UpdateVitalsModel.class);
    private final ISessionService sessionService;
    private final ISearchService searchService;
    private final IMedicalService medicalService;
    private final IPhotoService photoService;

    @Inject
    public MedicalController(ISessionService sessionService,
                             ISearchService searchService,
                             IMedicalService medicalService,
                             IPhotoService photoService) {
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.medicalService = medicalService;
        this.photoService = photoService;
    }


    public Result indexGet() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        return ok(index.render(currentUserSession, null, 0));
    }

    public Result indexPost() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        String queryString_id = request().body().asFormUrlEncoded().get("id")[0];
        ServiceResponse<Integer> idQueryStringResponse = searchService.parseIdFromQueryString(queryString_id);
        if (idQueryStringResponse.hasErrors()) {
            return ok(index.render(currentUserSession, idQueryStringResponse.getErrors().get(""), 0));
        }
        Integer patientId = idQueryStringResponse.getResponseObject();

        //get the patient's encounter
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.findPatientEncounterItemById(patientId);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            return ok(index.render(currentUserSession, patientEncounterItemServiceResponse.getErrors().get(""), 0));
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();

        //check for encounter closed
        if (patientEncounterItem.getIsClosed()) {
            return ok(index.render(currentUserSession, "That patient's encounter has been closed.", 0));
        }

        //check if the doc has already seen the patient today
        if (medicalService.hasPatientBeenCheckedInByPhysician(patientEncounterItem.getId()).getResponseObject()) {
            return ok(index.render(currentUserSession, "That patient has already been seen today. Would you like to edit their encounter?", patientId));
        }

        return redirect(routes.MedicalController.editGet(patientId));
    }

    public Result editGet(int patientId) {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        EditViewModelGet viewModelGet = new EditViewModelGet();

        //Get Patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemById(patientId);
        if (patientItemServiceResponse.hasErrors()) {
            return internalServerError();
        } else {
            viewModelGet.setPatientItem(patientItemServiceResponse.getResponseObject());
        }

        //Get Patient Encounter
        PatientEncounterItem patientEncounter;
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.findPatientEncounterItemById(patientId);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            return internalServerError();
        } else {
            patientEncounter = patientEncounterItemServiceResponse.getResponseObject();
            viewModelGet.setPatientEncounterItem(patientEncounter);
        }

        //find patient prescriptions, if they exist
        ServiceResponse<List<PrescriptionItem>> prescriptionItemServiceResponse = searchService.findUnreplacedPrescriptionItems(patientEncounter.getId());
        if (prescriptionItemServiceResponse.hasErrors()) {
            return internalServerError();
        } else {
            viewModelGet.setPrescriptionItems(prescriptionItemServiceResponse.getResponseObject());
        }

        //create a vital multi map
        ServiceResponse<VitalMultiMap> patientEncounterVitalMapResponse = searchService.getVitalMultiMap(patientEncounter.getId());
        if (patientEncounterVitalMapResponse.hasErrors()) {
            return internalServerError();
        } else {
            viewModelGet.setVitalMap(patientEncounterVitalMapResponse.getResponseObject());
        }

        //get non-custom fields
        ServiceResponse<Map<String, TabFieldItem>> patientEncounterTabFieldResponse = medicalService.findCurrentTabFieldsByEncounterId(patientEncounter.getId());
        Map<String, TabFieldItem> tabFieldItemMap;
        if (patientEncounterTabFieldResponse.hasErrors()) {
            return internalServerError();
        } else {
            tabFieldItemMap = patientEncounterTabFieldResponse.getResponseObject();
            viewModelGet.setStaticFields(tabFieldItemMap);
        }

        //get custom tabs/fields
        ServiceResponse<List<TabItem>> tabItemResponse = medicalService.getCustomTabs();
        if (tabItemResponse.hasErrors()) {
            return internalServerError();
        } else {
            viewModelGet.setCustomTabs(tabItemResponse.getResponseObject());
        }
        ServiceResponse<Map<String, List<TabFieldItem>>> tabFieldResponse = medicalService.getCustomFields(patientEncounter.getId());
        if (tabFieldResponse.hasErrors()) {
            return internalServerError();
        } else {
            viewModelGet.setCustomFields(tabFieldResponse.getResponseObject());
        }

        //store this in view model somehow
        ServiceResponse<List<IPhoto>> photoListResponse = photoService.GetEncounterPhotos(patientEncounter.getId());
        if (photoListResponse.hasErrors()) {
            return internalServerError();
        } else {
            viewModelGet.setPhotos(getPhotoModel(photoListResponse.getResponseObject()));
        }

        return ok(edit.render(currentUserSession, viewModelGet));
    }

    public Result editPost(int patientId) {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        EditViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();

        //get current encounter
        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = searchService.findPatientEncounterItemById(patientId);
        if (patientEncounterServiceResponse.hasErrors()) {
            return internalServerError();
        }
        PatientEncounterItem patientEncounterItem = patientEncounterServiceResponse.getResponseObject();
        patientEncounterItem = medicalService.checkPatientIn(patientEncounterItem.getId(), currentUserSession.getId()).getResponseObject();
        //update patient encounter

        //Maps the dynamic tab name to the field list
        Gson gson = new Gson();
        Map<String, List<JCustomField>> customFieldInformation = gson.fromJson(viewModelPost.getCustomFieldJSON(), new TypeToken<Map<String, List<JCustomField>>>() {
        }.getType());

        List<TabFieldItem> customFieldItems = new ArrayList<>();
        for (Map.Entry<String, List<JCustomField>> entry : customFieldInformation.entrySet()) {
            List<JCustomField> fields = entry.getValue();
            for (JCustomField jcf : fields) {
                TabFieldItem tabFieldItem = new TabFieldItem();
                tabFieldItem.setName(jcf.getName());
                tabFieldItem.setValue(jcf.getValue());
                tabFieldItem.setIsCustom(true);
                customFieldItems.add(tabFieldItem);
            }
        }

        //save the custom fields, if any
        if (customFieldItems.size() > 0){
            ServiceResponse<List<TabFieldItem>> customFieldItemResponse =
                    medicalService.createPatientEncounterTabFields(customFieldItems, patientEncounterItem.getId(), currentUserSession.getId());
            if (customFieldItemResponse.hasErrors()) {
                return internalServerError();
            }
        }

        //save the non-custom fields
        List<TabFieldItem> nonCustomFieldItems = mapTabFieldItems(viewModelPost);
        ServiceResponse<List<TabFieldItem>> nonCustomFieldItemResponse =
                medicalService.createPatientEncounterTabFields(nonCustomFieldItems, patientEncounterItem.getId(), currentUserSession.getId());
        if (nonCustomFieldItemResponse.hasErrors()){
            return internalServerError();
        }


        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemById(patientId);
        if (patientItemServiceResponse.hasErrors()) {
            return internalServerError();
        }
        PatientItem patientItem = patientItemServiceResponse.getResponseObject();


        List<FilePart> fps = request().body().asMultipartFormData().getFiles();

        //wtf is this
        photoService.HandleEncounterPhotos(fps, patientEncounterItem, viewModelPost);




        //save prescriptions
        List<PrescriptionItem> prescriptionItems = new ArrayList<>();
        if (viewModelPost.getPrescription1() != null)
            prescriptionItems.add(new PrescriptionItem(viewModelPost.getPrescription1()));
        if (viewModelPost.getPrescription2() != null)
            prescriptionItems.add(new PrescriptionItem(viewModelPost.getPrescription2()));
        if (viewModelPost.getPrescription3() != null)
            prescriptionItems.add(new PrescriptionItem(viewModelPost.getPrescription3()));
        if (viewModelPost.getPrescription4() != null)
            prescriptionItems.add(new PrescriptionItem(viewModelPost.getPrescription4()));
        if (viewModelPost.getPrescription5() != null)
            prescriptionItems.add(new PrescriptionItem(viewModelPost.getPrescription5()));
        if (prescriptionItems.size() > 0) {
            ServiceResponse<List<PrescriptionItem>> prescriptionResponse =
                    medicalService.createPatientPrescriptions(prescriptionItems, currentUserSession.getId(), patientEncounterItem.getId());
            if (prescriptionResponse.hasErrors()){
                return internalServerError();
            }
        }

        String message = "Patient information for " + patientItem.getFirstName() + " " + patientItem.getLastName() + " (id: " + patientItem.getId() + ") was saved successfully.";

        return ok(index.render(currentUserSession, message, 0));
    }

    public Result updateVitalsPost(int id) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<PatientEncounterItem> currentEncounterByPatientId = searchService.findPatientEncounterItemById(id);
        if (currentEncounterByPatientId.hasErrors()) {
            return internalServerError();
        }
        //update date_of_medical_visit when a vital is updated
        medicalService.checkPatientIn(currentEncounterByPatientId.getResponseObject().getId(), currentUser.getId());

        PatientEncounterItem patientEncounter = currentEncounterByPatientId.getResponseObject();

        UpdateVitalsModel updateVitalsModel = updateVitalsModelForm.bindFromRequest().get();

        Map<String, Float> patientEncounterVitals = getPatientEncounterVitals(updateVitalsModel);
        ServiceResponse<List<VitalItem>> patientEncounterVitalsServiceResponse =
                medicalService.createPatientEncounterVitals(patientEncounterVitals, currentUser.getId(), patientEncounter.getId());
        if (patientEncounterVitalsServiceResponse.hasErrors()) {
            return internalServerError();
        }

        return ok("true");
    }

    //partials
    public Result newVitalsGet() {
        return ok(newVitals.render());
    }

    public Result listVitalsGet(Integer id) {


        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = searchService.findPatientEncounterItemById(id);
        if (patientEncounterServiceResponse.hasErrors()) {
            return internalServerError();
        }
        ServiceResponse<VitalMultiMap> vitalMultiMapServiceResponse = searchService.getVitalMultiMap(patientEncounterServiceResponse.getResponseObject().getId());
        if (vitalMultiMapServiceResponse.hasErrors()) {
            return internalServerError();
        }

        return ok(listVitals.render(vitalMultiMapServiceResponse.getResponseObject()));
    }

    /**
     * Maps vitals from view model to a Map structure
     *
     * @param viewModel the view model with POST data
     * @return Mapped vital value to vital name
     */
    private Map<String, Float> getPatientEncounterVitals(UpdateVitalsModel viewModel) {
        Map<String, Float> newVitals = new HashMap<>();
        if (viewModel.getRespiratoryRate() != null) {
            newVitals.put("respiratoryRate", viewModel.getRespiratoryRate());
        }
        if (viewModel.getHeartRate() != null) {
            newVitals.put("heartRate", viewModel.getHeartRate());
        }
        if (viewModel.getTemperature() != null) {
            newVitals.put("temperature", viewModel.getTemperature());
        }
        if (viewModel.getOxygenSaturation() != null) {
            newVitals.put("oxygenSaturation", viewModel.getOxygenSaturation());
        }
        if (viewModel.getHeightFeet() != null) {
            newVitals.put("heightFeet", viewModel.getHeightFeet());
        }
        if (viewModel.getHeightInches() != null) {
            newVitals.put("heightInches", viewModel.getHeightInches());
        }
        if (viewModel.getWeight() != null) {
            newVitals.put("weight", viewModel.getWeight());
        }
        if (viewModel.getBloodPressureSystolic() != null) {
            newVitals.put("bloodPressureSystolic", viewModel.getBloodPressureSystolic());
        }
        if (viewModel.getBloodPressureDiastolic() != null) {
            newVitals.put("bloodPressureDiastolic", viewModel.getBloodPressureDiastolic());
        }
        if (viewModel.getGlucose() != null) {
            newVitals.put("glucose", viewModel.getGlucose());
        }
        return newVitals;
    }

    /**
     * Creates a list of non-custom tab field items
     *
     * @param viewModelPost view model POST from edit.scala.html
     * @return a list of values the user entered
     */
    private List<TabFieldItem> mapTabFieldItems(EditViewModelPost viewModelPost){
        List<TabFieldItem> tabFieldItems = new ArrayList<>();

        //treatment fields
        if (viewModelPost.getAssessment() != null) tabFieldItems.add(createTabFieldItem("assessment", viewModelPost.getAssessment()));
        if (viewModelPost.getProblem1() != null) tabFieldItems.add(createTabFieldItem("problem1", viewModelPost.getProblem1()));
        if (viewModelPost.getProblem2() != null) tabFieldItems.add(createTabFieldItem("problem2", viewModelPost.getProblem2()));
        if (viewModelPost.getProblem3() != null) tabFieldItems.add(createTabFieldItem("problem3", viewModelPost.getProblem3()));
        if (viewModelPost.getProblem4() != null) tabFieldItems.add(createTabFieldItem("problem4", viewModelPost.getProblem4()));
        if (viewModelPost.getProblem5() != null) tabFieldItems.add(createTabFieldItem("problem5", viewModelPost.getProblem5()));
        if (viewModelPost.getTreatment() != null) tabFieldItems.add(createTabFieldItem("treatment", viewModelPost.getTreatment()));

        //hpi fields
        if (viewModelPost.getOnset() != null) tabFieldItems.add(createTabFieldItem("onset", viewModelPost.getOnset()));
        if (viewModelPost.getOnsetTime() != null) tabFieldItems.add(createTabFieldItem("onsetTime", viewModelPost.getOnsetTime()));
        if (viewModelPost.getSeverity() != null) tabFieldItems.add(createTabFieldItem("severity", viewModelPost.getSeverity()));
        if (viewModelPost.getRadiation() != null) tabFieldItems.add(createTabFieldItem("radiation", viewModelPost.getRadiation()));
        if (viewModelPost.getQuality() != null) tabFieldItems.add(createTabFieldItem("quality", viewModelPost.getQuality()));
        if (viewModelPost.getProvokes() != null) tabFieldItems.add(createTabFieldItem("provokes", viewModelPost.getOnset()));
        if (viewModelPost.getPalliates() != null) tabFieldItems.add(createTabFieldItem("palliates", viewModelPost.getPalliates()));
        if (viewModelPost.getTimeOfDay() != null) tabFieldItems.add(createTabFieldItem("timeOfDay", viewModelPost.getTimeOfDay()));
        if (viewModelPost.getPhysicalExamination() != null) tabFieldItems.add(createTabFieldItem("physicalExamination", viewModelPost.getPhysicalExamination()));
        if (viewModelPost.getNarrative() != null) tabFieldItems.add(createTabFieldItem("narrative", viewModelPost.getOnset()));

        //Pmh_fields
        if (viewModelPost.getMedicalSurgicalHistory() != null) tabFieldItems.add(createTabFieldItem("medicalSurgicalHistory", viewModelPost.getMedicalSurgicalHistory()));
        if (viewModelPost.getSocialHistory() != null) tabFieldItems.add(createTabFieldItem("socialHistory", viewModelPost.getSocialHistory()));
        if (viewModelPost.getCurrentMedication() != null) tabFieldItems.add(createTabFieldItem("currentMedication", viewModelPost.getCurrentMedication()));
        if (viewModelPost.getFamilyHistory() != null) tabFieldItems.add(createTabFieldItem("familyHistory", viewModelPost.getFamilyHistory()));

        return tabFieldItems;
    }

    /**
     * Creates a non-custom tabfielditem
     *
     * @param name name of the field
     * @param value value of the field
     * @return a non custom tab field item
     */
    private TabFieldItem createTabFieldItem(String name, String value){
        TabFieldItem tabFieldItem = new TabFieldItem();
        tabFieldItem.setIsCustom(false);
        tabFieldItem.setName(name);
        tabFieldItem.setOrder(null);
        tabFieldItem.setPlaceholder(null);
        tabFieldItem.setSize(null);
        tabFieldItem.setType(null);
        tabFieldItem.setValue(value.trim());
        return tabFieldItem;
    }

    /**
     * Gets the photo list and adds them to the Photo Model or sets it to null if it is empty
     * @param photos the list of IPhoto to iterate over
     * @return A list of PhotoModel or null
     */
    private List<PhotoItem> getPhotoModel(List<IPhoto> photos) {
        List<PhotoItem> tempPhotoList = new ArrayList<>();
        if(photos != null)
        {
            for(IPhoto p : photos)
            {
                PhotoItem pm = new PhotoItem();
                pm.setId(p.getId()); //set photo Id
                pm.setImageDesc(p.getDescription()); //set description
                pm.setImageUrl(routes.PhotoController.GetEncounterPhoto(p.getId()).toString()); //set image URL
                pm.setImageDate(StringUtils.ToSimpleDate(p.getInsertTS()));
                tempPhotoList.add(pm);
            }
        }
        return tempPhotoList;
    }
}
