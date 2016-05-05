package femr.ui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import femr.business.helpers.LogicDoer;
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
import org.joda.time.DateTime;
import play.data.Form;
import play.mvc.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.stream.Collectors;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class TriageController extends Controller {
    //AJ Saclayan Cities
    private final Form<EditViewModelPost> createViewModelPostForm = Form.form(EditViewModelPost.class);
    private final Form<IndexViewModelPost> IndexViewModelForm = Form.form(IndexViewModelPost.class);
    private final IEncounterService encounterService;
    private final IPatientService patientService;
    private final ISessionService sessionService;
    private final ISearchService searchService;
    private final IPhotoService photoService;
    private final IVitalService vitalService;

    @Inject
    public TriageController(IEncounterService encounterService,
                            ISessionService sessionService,
                            ISearchService searchService,
                            IPatientService patientService,
                            IPhotoService photoService,
                            IVitalService vitalService) {

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

        //initalize an empty patient
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

        return ok(index.render(currentUser, viewModelGet));
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
        if(patientEncounter.getIsClosed() == false){
            viewModelGet.setLinkToMedical(true);
        }
        else{
            viewModelGet.setLinkToMedical(false);
        }

        return ok(index.render(currentUser, viewModelGet));
    }

    /*
   *if id is 0 then it is a new patient and a new encounter
   * if id is > 0 then it is only a new encounter
    */
    public Result indexPost(int id) {

        IndexViewModelPost viewModel = IndexViewModelForm.bindFromRequest().get();
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        //create a new patient
        //or get current patient for new encounter
        ServiceResponse<PatientItem> patientServiceResponse;
        PatientItem patientItem;
        if (id == 0) {
            patientItem = populatePatientItem(viewModel, currentUser);
            patientServiceResponse = patientService.createPatient(patientItem);
        } else {
            patientServiceResponse = patientService.updateSex(id, viewModel.getSex());
        }
        if (patientServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        patientItem = patientServiceResponse.getResponseObject();

        // Check if patient's numerical age is within range of age classification
        int totalMonths = (int)Math.floor(patientItem.getMonthsOld() / 12);

        if (totalMonths >= 0 && totalMonths <= 1 && !viewModel.getAgeClassification().toLowerCase().equals("infant")) {
            throw new RuntimeException("Invalid age classification");
        } else if (totalMonths >= 2 && totalMonths <= 12 && !viewModel.getAgeClassification().toLowerCase().equals("child")) {
            throw new RuntimeException("Invalid age classification");
        } else if (totalMonths >= 13 && totalMonths <= 17 && !viewModel.getAgeClassification().toLowerCase().equals("teen")) {
            throw new RuntimeException("Invalid age classification");
        } else if (totalMonths >= 18 && totalMonths <= 64 && !viewModel.getAgeClassification().toLowerCase().equals("adult")) {
            throw new RuntimeException("Invalid age classification");
        } else if (totalMonths >= 64 && !viewModel.getAgeClassification().toLowerCase().equals("elder")) {
            throw new RuntimeException("Invalid age classification");
        }

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
            Float heightFeet = viewModel.getHeightFeet().floatValue();
            newVitals.put("heightFeet", heightFeet);
        }

        if (viewModel.getHeightInches() != null) {
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

        //Getting UserItem
        ServiceResponse<PatientItem> patientItemResponse= patientService.deletePatient(patientId, currentUser.getId());

        if(patientItemResponse.hasErrors())
            throw new RuntimeException();

        return redirect(routes.TriageController.indexGet());
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
