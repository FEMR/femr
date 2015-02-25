package femr.ui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.SettingItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.common.models.VitalItem;
import femr.ui.models.triage.*;
import femr.ui.views.html.triage.index;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class TriageController extends Controller {

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
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        //retrieve all the vitals in the database so we can dynamically name
        //the vitals in the view
        ServiceResponse<List<VitalItem>> vitalServiceResponse = vitalService.findAllVitalItems();
        if (vitalServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        //initalize an empty patient
        PatientItem patientItem = new PatientItem();

        //get settings
        ServiceResponse<SettingItem> settingItemServiceResponse = searchService.getSystemSettings();
        if (settingItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        //get age classifications
        ServiceResponse<Map<String,String>> patientAgeClassificationsResponse = patientService.findPossibleAgeClassifications();
        if (patientAgeClassificationsResponse.hasErrors()){
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
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        //retrieve vitals names for dynamic html element naming
        ServiceResponse<List<VitalItem>> vitalServiceResponse = vitalService.findAllVitalItems();
        if (vitalServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        //get the patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemByPatientId(patientId);
        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientItem patient = patientItemServiceResponse.getResponseObject();

        //get the settings
        ServiceResponse<SettingItem> settingItemServiceResponse = searchService.getSystemSettings();
        if (settingItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        //get age classifications
        ServiceResponse<Map<String,String>> patientAgeClassificationsResponse = patientService.findPossibleAgeClassifications();
        if (patientAgeClassificationsResponse.hasErrors()){
            throw new RuntimeException();
        }

        IndexViewModelGet viewModelGet = new IndexViewModelGet();
        viewModelGet.setSettings(settingItemServiceResponse.getResponseObject());
        viewModelGet.setPatient(patient);
        viewModelGet.setVitalNames(vitalServiceResponse.getResponseObject());
        viewModelGet.setPossibleAgeClassifications(patientAgeClassificationsResponse.getResponseObject());

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
            patientServiceResponse = patientService.createPatient(patientItem);
        } else {
            patientServiceResponse = patientService.findPatientAndUpdateSex(id, viewModel.getSex());
        }
        if (patientServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        patientItem = patientServiceResponse.getResponseObject();


        photoService.SavePatientPhotoAndUpdatePatient(viewModel.getPatientPhotoCropped(), patientItem.getId(), viewModel.getDeletePhoto());
        //V code for saving photo without javascript
        //currently javascript is required
        //Http.MultipartFormData.FilePart fpPhoto = request().body().asMultipartFormData().getFile("patientPhoto");


        //create and save a new encounter
        PatientEncounterItem patientEncounterItem =
                populatePatientEncounterItem(viewModel.getChiefComplaint(), viewModel.getChiefComplaintsJSON(), viewModel.getWeeksPregnant(), currentUser, patientServiceResponse.getResponseObject().getId(), viewModel.getAgeClassification());
        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = encounterService.createPatientEncounter(patientEncounterItem);
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

        ServiceResponse<List<VitalItem>> vitalServiceResponse = vitalService.createPatientEncounterVitalItems(newVitals, currentUser.getId(), patientEncounterItem.getId());
        if (vitalServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        return redirect(routes.HistoryController.indexPatientGet(Integer.toString(patientServiceResponse.getResponseObject().getId())));
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

    private PatientEncounterItem populatePatientEncounterItem(String chiefComplaint, String chiefComplaintJSON, Integer weeksPregnant, CurrentUser currentUser, int patientId, String ageClassification) {
        PatientEncounterItem patientEncounterItem = new PatientEncounterItem();
        patientEncounterItem.setPatientId(patientId);
        patientEncounterItem.setNurseEmailAddress(currentUser.getEmail());
        patientEncounterItem.setAgeClassification(ageClassification);
        //JSON chief complaints (multiple chief complaints - requires javascript)
        //this won't happen if the multiple chief complaint
        // feature is turned off (chiefComplaintJSON will be null)
        if (StringUtils.isNotNullOrWhiteSpace(chiefComplaintJSON)) {
            Gson gson = new Gson();
            List<String> multipleChiefComplaints = gson.fromJson(chiefComplaintJSON, new TypeToken<List<String>>() {
            }.getType());
            for (String mcc : multipleChiefComplaints) {
                patientEncounterItem.addChiefComplaint(mcc);
            }
        } else {
            if (StringUtils.isNotNullOrWhiteSpace(chiefComplaint)) {
                patientEncounterItem.addChiefComplaint(chiefComplaint);
            }
        }

        patientEncounterItem.setWeeksPregnant(weeksPregnant);
        return patientEncounterItem;
    }
}
