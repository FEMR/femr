package femr.ui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.triage.*;
import femr.ui.views.html.triage.index;
import femr.util.stringhelpers.StringUtils;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class TriageController extends Controller {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private final IEncounterService encounterService;
    private final IPatientService patientService;
    private final ISessionService sessionService;
    private final ISearchService searchService;
    private final IPhotoService photoService;
    private final IVitalService vitalService;

    @Inject
    public TriageController(AssetsFinder assetsFinder,
                            FormFactory formFactory,
                            IEncounterService encounterService,
                            ISessionService sessionService,
                            ISearchService searchService,
                            IPatientService patientService,
                            IPhotoService photoService,
                            IVitalService vitalService) {

        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.encounterService = encounterService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.patientService = patientService;
        this.photoService = photoService;
        this.vitalService = vitalService;
    }

    public Result indexGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        //retrieve all the vitals in the database so we can dynamically name
        //the vitals in the view
        ServiceResponse<List<VitalItem>> vitalServiceResponse = vitalService.retrieveAllVitalItems();
        if (vitalServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        //initialize an empty patient
        PatientItem patientItem = new PatientItem();

        //get settings
        ServiceResponse<SettingItem> settingItemServiceResponse = searchService.retrieveSystemSettings();
        if (settingItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        //get age classifications
        ServiceResponse<Map<String, String>> patientAgeClassificationsResponse = patientService.retrieveAgeClassifications();
        if (patientAgeClassificationsResponse.hasErrors()) {
            throw new RuntimeException();
        }

        IndexViewModelGet viewModelGet = new IndexViewModelGet();
        viewModelGet.setVitalNames(vitalServiceResponse.getResponseObject());
        viewModelGet.setPatient(patientItem);
        viewModelGet.setSearchError(false);
        viewModelGet.setSettings(settingItemServiceResponse.getResponseObject());
        viewModelGet.setPossibleAgeClassifications(patientAgeClassificationsResponse.getResponseObject());

        return ok(index.render(currentUser, viewModelGet, assetsFinder));
    }

    /*
    Used when user has searched for an existing patient
    and wants to create a new encounter
     */
    public Result indexPopulatedGet(int patientId) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        //retrieve vitals names for dynamic html element naming
        ServiceResponse<List<VitalItem>> vitalServiceResponse = vitalService.retrieveAllVitalItems();
        if (vitalServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        //AJ Saclayan New Encounter Warning
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrieveRecentPatientEncounterItemByPatientId(patientId);
        if(patientEncounterItemServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        PatientEncounterItem patientEncounter = patientEncounterItemServiceResponse.getResponseObject();

        //get the patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.retrievePatientItemByPatientId(patientId);
        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }


        PatientItem patient = patientItemServiceResponse.getResponseObject();

        //get the settings
        ServiceResponse<SettingItem> settingItemServiceResponse = searchService.retrieveSystemSettings();
        if (settingItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        //get age classifications
        ServiceResponse<Map<String, String>> patientAgeClassificationsResponse = patientService.retrieveAgeClassifications();
        if (patientAgeClassificationsResponse.hasErrors()) {
            throw new RuntimeException();
        }

        IndexViewModelGet viewModelGet = new IndexViewModelGet();
        viewModelGet.setSettings(settingItemServiceResponse.getResponseObject());
        viewModelGet.setPatient(patient);
        viewModelGet.setVitalNames(vitalServiceResponse.getResponseObject());
        viewModelGet.setPossibleAgeClassifications(patientAgeClassificationsResponse.getResponseObject());
        //Patient has an open encounter for medical
        if(patientEncounter != null && !patientEncounter.getIsClosed()){
            viewModelGet.setLinkToMedical(true);
        }
        else{
            viewModelGet.setLinkToMedical(false);
        }

        return ok(index.render(currentUser, viewModelGet, assetsFinder));
    }

    /*
   *if id is 0 then it is a new patient and a new encounter
   * if id is > 0 then it is only a new encounter
    */
    public Result indexPost(int id) {

        final Form<IndexViewModelPost> IndexViewModelForm = formFactory.form(IndexViewModelPost.class).bindFromRequest();
        IndexViewModelPost viewModel = IndexViewModelForm.get();
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        //create a new patient
        //or get current patient for new encounter
        ServiceResponse<PatientItem> patientServiceResponse;
        PatientItem patientItem;
        if (id == 0) {
            patientItem = populatePatientItem(viewModel, currentUser);
            patientServiceResponse = patientService.createPatient(patientItem);
        } else {

            patientServiceResponse = patientService.updatePatientAge(id, viewModel.getAge());
            if (patientServiceResponse.hasErrors()){

                Logger.error("TriageController-indexPost", "there was an issue updating the patient's age");
                throw new RuntimeException();
            }

            patientServiceResponse = patientService.updatePatientAddress(id, viewModel.getAddress());
            if (patientServiceResponse.hasErrors()) {

                Logger.error("TriageController-indexPost", "there was an issue updating the patient's address");
                throw new RuntimeException();
            }

            patientServiceResponse = patientService.updatePatientPhoneNumber(id, viewModel.getPhoneNumber());
            if (patientServiceResponse.hasErrors()){

                Logger.error("TriageController-indexPost", "there was an issue updating the patient's phone number");
                throw new RuntimeException();
            }

            patientServiceResponse = patientService.updatePatientSex(id, viewModel.getSex());
            if (patientServiceResponse.hasErrors()){

                Logger.error("TriageController-indexPost", "there was an issue updating the patient's sex");
                throw new RuntimeException();
            }

        }

        patientItem = patientServiceResponse.getResponseObject();

        photoService.createPatientPhoto(viewModel.getPatientPhotoCropped(), patientItem.getId(), viewModel.getDeletePhoto());
        //V code for saving photo without javascript
        //currently javascript is required
        //Http.MultipartFormData.FilePart fpPhoto = request().body().asMultipartFormData().getFile("patientPhoto");


        List<String> chiefComplaints = parseChiefComplaintsJSON(viewModel.getChiefComplaint(), viewModel.getChiefComplaintsJSON());
        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = encounterService.createPatientEncounter(patientItem.getId(), currentUser.getId(), currentUser.getTripId(), viewModel.getAgeClassification(), chiefComplaints);
        PatientEncounterItem patientEncounterItem;
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

        //Alaa Serhan
        if (viewModel.getTemperature() != null) {
            Float temperature = viewModel.getTemperature();
            newVitals.put("temperature", temperature);
        }

        if (viewModel.getOxygenSaturation() != null) {
            newVitals.put("oxygenSaturation", viewModel.getOxygenSaturation());
        }


        if (viewModel.getHeightFeet() != null) {

            // if a value for feet (or meters) is entered and inches (or centimeters) equals null, initialize inches to 0
            if (viewModel.getHeightInches() == null) {
                newVitals.put("heightInches", 0f);
            }
            Float heightFeet = viewModel.getHeightFeet().floatValue();
            newVitals.put("heightFeet", heightFeet);
        }


        if (viewModel.getHeightInches() != null) {

            // if a value for inches (or centimeters) is entered and feet (or meters) equals null, initialize feet to 0
            if (viewModel.getHeightFeet() == null) {
                newVitals.put("heightFeet", 0f);
            }
            Float heightInches = viewModel.getHeightInches().floatValue();
            newVitals.put("heightInches", heightInches);
        }

        //Alaa Serhan
        if (viewModel.getWeight() != null) {
            Float weight = viewModel.getWeight();
            newVitals.put("weight", weight);
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

		 if (viewModel.getWeeksPregnant() != null) { /*Sam Zanni*/
            newVitals.put("weeksPregnant", viewModel.getWeeksPregnant().floatValue());
        }

        // Osman

        if(viewModel.getSmoker() != null){
            newVitals.put("smoker", viewModel.getSmoker().floatValue());
        }

        if(viewModel.getDiabetes() != null){
            newVitals.put("diabetic", viewModel.getDiabetes().floatValue());
        }

        if(viewModel.getAlcohol() != null){
            newVitals.put("alcohol", viewModel.getAlcohol().floatValue());
        }

        if(viewModel.getCholesterol() != null){
            newVitals.put("cholesterol", viewModel.getCholesterol().floatValue());
        }

        if(viewModel.getHypertension() != null){
            newVitals.put("hypertension", viewModel.getHypertension().floatValue());
        }
		
        ServiceResponse<List<VitalItem>> vitalServiceResponse = vitalService.createPatientEncounterVitalItems(newVitals, currentUser.getId(), patientEncounterItem.getId());
        if (vitalServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
                //getIsDiabetesScreenPerformed is empty if user was not prompted to screen,
               // otherwise its boolean indicating whether screening occurred or not
        if (isDiabetesPromptTurnedOn() && !(viewModel.getIsDiabetesScreenPerformed().isEmpty())){
            ServiceResponse<PatientEncounterItem> diabetesScreenServiceResponse = encounterService.screenPatientForDiabetes(patientEncounterItem.getId(), currentUser.getId(), Boolean.valueOf(viewModel.getIsDiabetesScreenPerformed().toString()));
            if (diabetesScreenServiceResponse.hasErrors()){
                throw new RuntimeException();
            }
        }

        return redirect(routes.HistoryController.indexPatientGet(Integer.toString(patientServiceResponse.getResponseObject().getId())));
    }
  //  public Result deletePatientPost(int patientId, int deleteByUserID){
    public Result deletePatientPost(int patientId){
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        final Form<DeleteViewModelPost> DeleteViewModelForm = formFactory.form(DeleteViewModelPost.class);
        DeleteViewModelPost reasonDeleted = DeleteViewModelForm.bindFromRequest().get();
        //Getting UserItem
        ServiceResponse<PatientItem> patientItemResponse= patientService.deletePatient(patientId, currentUser.getId(), reasonDeleted.getReasonDeleted());

        if(patientItemResponse.hasErrors())
            throw new RuntimeException();

        return redirect(routes.TriageController.indexGet());
    }

   public Result deleteEncounterPost(int patientId,int encounterId){

       CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        final Form<DeleteViewModelPost> DeleteViewModelForm = formFactory.form(DeleteViewModelPost.class);
        DeleteViewModelPost reasonEncounterDeleted = DeleteViewModelForm.bindFromRequest().get();
        if(reasonEncounterDeleted.getReasonEncounterDeleted().equals("")){
            return redirect(routes.HistoryController.indexPatientGet("" + patientId));

        }
        else {
            //Method sets encounter as deleted in the database
            ServiceResponse<PatientEncounterItem> patientItemResponse = encounterService.deleteEncounter(currentUser.getId(), reasonEncounterDeleted.getReasonEncounterDeleted(), encounterId);

            if (patientItemResponse.hasErrors())
                throw new RuntimeException();
            return redirect(routes.HistoryController.indexPatientGet("" + patientId));
        }
    }
    private boolean isDiabetesPromptTurnedOn(){

        //get system settings to determine if diabetes prompt is turned on
        ServiceResponse<SettingItem> settingsResponse = searchService.retrieveSystemSettings();
        if (settingsResponse.hasErrors()){
            throw new RuntimeException();
        }

        return settingsResponse.getResponseObject().isDiabetesPrompt();
    }

    private PatientItem populatePatientItem(IndexViewModelPost viewModelPost, CurrentUser currentUser) {
        PatientItem patient = new PatientItem();
        patient.setUserId(currentUser.getId());
        patient.setFirstName(viewModelPost.getFirstName());
        patient.setLastName(viewModelPost.getLastName());
        patient.setPhoneNumber(viewModelPost.getPhoneNumber());
        if (viewModelPost.getAge() != null) {
            patient.setBirth(viewModelPost.getAge());
        }
        patient.setSex(viewModelPost.getSex());
        patient.setAddress(viewModelPost.getAddress());
        patient.setCity(viewModelPost.getCity());

        return patient;
    }

    private List<String> parseChiefComplaintsJSON(String chiefComplaint, String chiefComplaintJSON) {
        List<String> chiefComplaints = new ArrayList<>();
        //JSON chief complaints (multiple chief complaints - requires javascript)
        //this won't happen if the multiple chief complaint
        // feature is turned off (chiefComplaintJSON will be null)
        if (StringUtils.isNotNullOrWhiteSpace(chiefComplaintJSON)) {

            Gson gson = new Gson();
            chiefComplaints = gson.fromJson(chiefComplaintJSON, new TypeToken<List<String>>() {
            }.getType());

        } else {
            if (StringUtils.isNotNullOrWhiteSpace(chiefComplaint)) {
                chiefComplaints.add(chiefComplaint);
            }
        }

        return chiefComplaints;
    }

//    //AJ Saclayan Cities
//    public void editPost()
//    {
//        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();
//
//        EditViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();
//
//        List<CityItem> cityItems = viewModelPost.getCities()
//                .stream()
//                .filter(cityItem -> cityItem.getCityName() != null)
//                .collect(Collectors.toList());
//    }
}
