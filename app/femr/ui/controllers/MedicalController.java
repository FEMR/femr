package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.models.mysql.Roles;
import femr.data.models.mysql.TabField;
import femr.ui.controllers.helpers.FieldHelper;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.medical.*;
import femr.ui.views.html.medical.index;
import femr.ui.views.html.medical.edit;
import femr.ui.views.html.medical.newVitals;
import femr.ui.views.html.medical.listVitals;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.apache.commons.collections.MapIterator;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.*;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class MedicalController extends Controller {

    private final Form<EditViewModelPost> createViewModelPostForm = Form.form(EditViewModelPost.class);
    private final Form<UpdateVitalsModel> updateVitalsModelForm = Form.form(UpdateVitalsModel.class);
    private final ITabService tabService;
    private final IEncounterService encounterService;
    private final IMedicationService medicationService;
    private final IPhotoService photoService;
    private final ISessionService sessionService;
    private final ISearchService searchService;
    private final IVitalService vitalService;

    @Inject
    public MedicalController(ITabService tabService,
                             IEncounterService encounterService,
                             IMedicationService medicationService,
                             IPhotoService photoService,
                             ISessionService sessionService,
                             ISearchService searchService,
                             IVitalService vitalService) {
        this.tabService = tabService;
        this.encounterService = encounterService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.medicationService = medicationService;
        this.photoService = photoService;
        this.vitalService = vitalService;
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
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.findRecentPatientEncounterItemByPatientId(patientId);
        if (patientEncounterItemServiceResponse.hasErrors()) {

            return ok(index.render(currentUserSession, patientEncounterItemServiceResponse.getErrors().get(""), 0));
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();

        //check for encounter closed
        if (patientEncounterItem.getIsClosed()) {

            return ok(index.render(currentUserSession, "That patient's encounter has been closed.", 0));
        }

        //check if the doc has already seen the patient today
        ServiceResponse<UserItem> userItemServiceResponse = encounterService.getPhysicianThatCheckedInPatientToMedical(patientEncounterItem.getId());
        if (userItemServiceResponse.hasErrors()) {

            throw new RuntimeException();
        } else {

            if (userItemServiceResponse.getResponseObject() != null) {

                return ok(index.render(currentUserSession, "That patient has already been seen today. Would you like to edit their encounter?", patientId));
            }
        }

        return redirect(routes.MedicalController.editGet(patientId));
    }

    public Result editGet(int patientId) {

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        EditViewModelGet viewModelGet = new EditViewModelGet();

        //Get Patient Encounter
        PatientEncounterItem patientEncounter;
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.findRecentPatientEncounterItemByPatientId(patientId);
        if (patientEncounterItemServiceResponse.hasErrors()) {

            throw new RuntimeException();
        }
        patientEncounter = patientEncounterItemServiceResponse.getResponseObject();
        viewModelGet.setPatientEncounterItem(patientEncounter);

        //verify encounter is still open
        if (patientEncounter.getIsClosed()) {

            return ok(index.render(currentUserSession, "That patient's encounter has been closed.", 0));
        }

        //get patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemByPatientId(patientId);
        if (patientItemServiceResponse.hasErrors()) {

            throw new RuntimeException();
        }
        viewModelGet.setPatientItem(patientItemServiceResponse.getResponseObject());

        //get prescriptions
        ServiceResponse<List<PrescriptionItem>> prescriptionItemServiceResponse = searchService.findUnreplacedPrescriptionItems(patientEncounter.getId());
        if (prescriptionItemServiceResponse.hasErrors()) {

            throw new RuntimeException();
        }
        viewModelGet.setPrescriptionItems(prescriptionItemServiceResponse.getResponseObject());

        //get problems
        ServiceResponse<List<ProblemItem>> problemItemServiceResponse = encounterService.findProblemItems(patientEncounter.getId());
        if (problemItemServiceResponse.hasErrors()) {

            throw new RuntimeException();
        }
        viewModelGet.setProblemItems(problemItemServiceResponse.getResponseObject());

        //get vitals
        ServiceResponse<VitalMultiMap> vitalMapResponse = vitalService.findVitalMultiMap(patientEncounter.getId());
        if (vitalMapResponse.hasErrors()) {

            throw new RuntimeException();
        }

        //get all fields and their values
        ServiceResponse<TabFieldMultiMap> tabFieldMultiMapResponse = tabService.findTabFieldMultiMap(patientEncounter.getId());
        if (tabFieldMultiMapResponse.hasErrors()) {

            throw new RuntimeException();
        }
        TabFieldMultiMap tabFieldMultiMap = tabFieldMultiMapResponse.getResponseObject();
        ServiceResponse<List<TabItem>> tabItemServiceResponse = tabService.findAvailableTabs(false);
        if (tabItemServiceResponse.hasErrors()) {

            throw new RuntimeException();
        }
        List<TabItem> tabItems = tabItemServiceResponse.getResponseObject();
        //match the fields to their respective tabs
        for (TabItem tabItem : tabItems) {

            switch (tabItem.getName().toLowerCase()) {
                case "hpi":
                    tabItem.setFields(FieldHelper.structureHPIFieldsForView(tabFieldMultiMap));
                    break;
                case "pmh":
                    tabItem.setFields(FieldHelper.structurePMHFieldsForView(tabFieldMultiMap));
                    break;
                case "treatment":
                    tabItem.setFields(FieldHelper.structureTreatmentFieldsForView(tabFieldMultiMap));
                    break;
            }
        }
        tabItems = FieldHelper.applyIndicesToFieldsForView(tabItems);
        viewModelGet.setTabItems(tabItems);
        viewModelGet.setChiefComplaints(tabFieldMultiMap.getChiefComplaintList());

        ServiceResponse<List<PhotoItem>> photoListResponse = photoService.GetEncounterPhotos(patientEncounter.getId());
        if (photoListResponse.hasErrors()) {

            throw new RuntimeException();
        } else {

            viewModelGet.setPhotos(photoListResponse.getResponseObject());
        }

        ServiceResponse<SettingItem> response = searchService.getSystemSettings();
        viewModelGet.setSettings(response.getResponseObject());

        return ok(edit.render(currentUserSession, vitalMapResponse.getResponseObject(), viewModelGet));
    }

    public Result editPost(int patientId) {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        EditViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();

        //get current patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemByPatientId(patientId);
        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientItem patientItem = patientItemServiceResponse.getResponseObject();

        //get current encounter
        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = searchService.findRecentPatientEncounterItemByPatientId(patientId);
        if (patientEncounterServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientEncounterItem patientEncounterItem = patientEncounterServiceResponse.getResponseObject();
        patientEncounterItem = encounterService.checkPatientInToMedical(patientEncounterItem.getId(), currentUserSession.getId()).getResponseObject();


        //create patient encounter tab fields

        //get problems
        List<String> problemList = new ArrayList<>();
        for (ProblemItem pi : viewModelPost.getProblems()) {
            if (StringUtils.isNotNullOrWhiteSpace(pi.getName()))
                problemList.add(pi.getName());
        }
        //save problems
        if (problemList.size() > 0)
            encounterService.createProblems(problemList, patientEncounterItem.getId(), currentUserSession.getId());

        TabFieldMultiMap tabFieldMultiMap = new TabFieldMultiMap();
        String date = dateUtils.getCurrentDateTimeString();

        //get non-custom tab fields other than problems
        for (TabFieldItem tfi : viewModelPost.getTabFieldItems()) {
            if (StringUtils.isNotNullOrWhiteSpace(tfi.getValue()))
                tabFieldMultiMap.put(tfi.getName(), date, tfi.getChiefComplaint(), tfi.getValue());
        }



        /*
        //get custom tab fields
        Map<String, List<JCustomField>> customFieldInformation = new Gson().fromJson(viewModelPost.getCustomFieldJSON(), new TypeToken<Map<String, List<JCustomField>>>() {
        }.getType());
        for (Map.Entry<String, List<JCustomField>> entry : customFieldInformation.entrySet()) {
            for (JCustomField jcf : entry.getValue()) {
                if (StringUtils.isNotNullOrWhiteSpace(jcf.getValue()))
                    tabFieldMultiMap.put(jcf.getName(), date, "", jcf.getValue());
            }
        } */
        //save dat sheeeit, mayne
        //if (tabFieldsWithValue.size() > 0) {

        /*
        if (tabFieldMultiMap.getSize() > 0) {

            ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFieldsServiceResponse = encounterService.createPatientEncounterTabFields(tabFieldMultiMap, patientEncounterItem.getId(), currentUserSession.getId());
            if (createPatientEncounterTabFieldsServiceResponse.hasErrors()) {

                throw new RuntimeException();
            }
        } */


        //create patient encounter photos
        photoService.HandleEncounterPhotos(request().body().asMultipartFormData().getFiles(), patientEncounterItem, viewModelPost);

        //create prescriptions
        List<String> prescriptions = new ArrayList<>();
        for (PrescriptionItem pi : viewModelPost.getPrescriptions()) {
            if (StringUtils.isNotNullOrWhiteSpace(pi.getName()))
                prescriptions.add(pi.getName());
        }
        if (prescriptions.size() > 0) {
            ServiceResponse<List<PrescriptionItem>> prescriptionResponse = medicationService.createPatientPrescriptions(prescriptions, currentUserSession.getId(), patientEncounterItem.getId(), false, false);
            if (prescriptionResponse.hasErrors()) {
                throw new RuntimeException();
            }
        }


        String message = "Patient information for " + patientItem.getFirstName() + " " + patientItem.getLastName() + " (id: " + patientItem.getId() + ") was saved successfully.";

        return ok(index.render(currentUserSession, message, 0));
    }

    public Result updateVitalsPost(int id) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<PatientEncounterItem> currentEncounterByPatientId = searchService.findRecentPatientEncounterItemByPatientId(id);
        if (currentEncounterByPatientId.hasErrors()) {
            throw new RuntimeException();
        }
        //update date_of_medical_visit when a vital is updated
        encounterService.checkPatientInToMedical(currentEncounterByPatientId.getResponseObject().getId(), currentUser.getId());

        PatientEncounterItem patientEncounter = currentEncounterByPatientId.getResponseObject();

        UpdateVitalsModel updateVitalsModel = updateVitalsModelForm.bindFromRequest().get();

        Map<String, Float> patientEncounterVitals = getPatientEncounterVitals(updateVitalsModel);
        ServiceResponse<List<VitalItem>> patientEncounterVitalsServiceResponse =
                vitalService.createPatientEncounterVitals(patientEncounterVitals, currentUser.getId(), patientEncounter.getId());
        if (patientEncounterVitalsServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        return ok("true");
    }

    //partials
    public Result newVitalsGet() {
        return ok(newVitals.render());
    }

    public Result listVitalsGet(Integer id) {


        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = searchService.findRecentPatientEncounterItemByPatientId(id);
        if (patientEncounterServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        ServiceResponse<VitalMultiMap> vitalMultiMapServiceResponse = vitalService.findVitalMultiMap(patientEncounterServiceResponse.getResponseObject().getId());
        if (vitalMultiMapServiceResponse.hasErrors()) {
            throw new RuntimeException();
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

    /*
    private List<TabFieldItem> mapHpiFieldItemsFromJSON(String JSON) {
        List<TabFieldItem> tabFieldItems = new ArrayList<>();
        Gson gson = new Gson();
        //get values from JSON, assign list of values to chief complaint
        Map<String, List<JCustomField>> hpiTabInformation = gson.fromJson(JSON, new TypeToken<Map<String, List<JCustomField>>>() {
        }.getType());

        for (Map.Entry<String, List<JCustomField>> entry : hpiTabInformation.entrySet()) {
            List<JCustomField> fields = entry.getValue();

            for (JCustomField jcf : fields) {
                TabFieldItem tabFieldItem = new TabFieldItem();
                tabFieldItem = jcf.getName());
                tabFieldItem.setChiefComplaint(entry.getKey().trim());
                tabFieldItem.setIsCustom(false);
                tabFieldItem.setValue(jcf.getValue());
                tabFieldItems.add(tabFieldItem);
            }
        }
        return tabFieldItems;
    }     */
}
