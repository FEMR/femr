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

import java.io.File;
import java.util.*;

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

        PatientEncounterCreationProcess process = new PatientEncounterCreationProcess(
                id, viewModel, currentUser,
                patientService, encounterService, photoService,
                vitalService, searchService);

        int patientId = process.execute();

        return redirect(routes.HistoryController.indexPatientGet(Integer.toString(patientId)));
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
}



class PatientEncounterCreationProcess {
    private final int id;
    private final IndexViewModelPost viewModel;
    private final CurrentUser currentUser;
    private final IPatientService patientService;
    private final IEncounterService encounterService;
    private final IPhotoService photoService;
    private final IVitalService vitalService;
    private final ISearchService searchService;

    public PatientEncounterCreationProcess(
            int id,
            IndexViewModelPost viewModel,
            CurrentUser currentUser,
            IPatientService patientService,
            IEncounterService encounterService,
            IPhotoService photoService,
            IVitalService vitalService,
            ISearchService searchService) {
        this.id = id;
        this.viewModel = viewModel;
        this.currentUser = currentUser;
        this.patientService = patientService;
        this.encounterService = encounterService;
        this.photoService = photoService;
        this.vitalService = vitalService;
        this.searchService = searchService;
    }


    public int execute() {

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
        photoService.createPatientPhoto(patientItem.getPathToPhoto(), patientItem.getId(), viewModel.getDeletePhoto());

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

        return patientServiceResponse.getResponseObject().getId();
    }

    private boolean isDiabetesPromptTurnedOn(){

        //get system settings to determine if diabetes prompt is turned on
        ServiceResponse<SettingItem> settingsResponse = searchService.retrieveSystemSettings();
        if (settingsResponse.hasErrors()){
            throw new RuntimeException();
        }

        return settingsResponse.getResponseObject().isDiabetesPrompt();
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

    private PatientItem populatePatientItem(IndexViewModelPost viewModelPost, CurrentUser currentUser) {
        PatientItem patient = new PatientItem();
        patient.setUserId(currentUser.getId());
        patient.setFirstName(viewModelPost.getFirstName());
        patient.setLastName(viewModelPost.getLastName());
        patient.setPhoneNumber(viewModelPost.getPhoneNumber());
        System.out.println("YEARS: " + viewModelPost.getYears());

        if(viewModelPost.getYears() != null && viewModelPost.getMonths() != null) {

            patient.setYearsOld(viewModelPost.getYears());
            patient.setMonthsOld(viewModelPost.getMonths());
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.YEAR, viewModelPost.getYears());
            calendar.add(Calendar.MONTH, viewModelPost.getMonths());
            patient.setBirth(calendar.getTime());

        }else if (viewModelPost.getAge() != null) {
            patient.setBirth(viewModelPost.getAge());
        }else if(viewModelPost.getAgeClassification() != null){

            //patch fix, I am generalizing age to get a generalized birthdate for the database
            switch (viewModelPost.getAgeClassification()){

                case "infant":
                    Date currentDate = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.YEAR, -1);
                    patient.setBirth(calendar.getTime());
                    break;

                case "child":
                    Date currentDate2 = new Date();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(currentDate2);
                    calendar2.add(Calendar.YEAR, -6);
                    patient.setBirth(calendar2.getTime());
                    break;

                case "teenager":
                    Date currentDate3 = new Date();
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTime(currentDate3);
                    calendar3.add(Calendar.YEAR, -15);
                    patient.setBirth(calendar3.getTime());
                    break;

                case "adult":
                    Date currentDate4 = new Date();
                    Calendar calendar4 = Calendar.getInstance();
                    calendar4.setTime(currentDate4);
                    calendar4.add(Calendar.YEAR, -41);
                    patient.setBirth(calendar4.getTime());
                    break;

                case "elder":
                    Date currentDate5 = new Date();
                    Calendar calendar5 = Calendar.getInstance();
                    calendar5.setTime(currentDate5);
                    calendar5.add(Calendar.YEAR, -65);
                    patient.setBirth(calendar5.getTime());
                    break;

            }


        }

        patient.setSex(viewModelPost.getSex());
        patient.setAddress(viewModelPost.getAddress());
        patient.setCity(viewModelPost.getCity());
        patient.setPathToPhoto(viewModelPost.getPatientPhotoCropped());
        return patient;
    }

}