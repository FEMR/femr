package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.models.mysql.Roles;
import femr.ui.controllers.helpers.FieldHelper;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.medical.*;
import femr.ui.views.html.medical.index;
import femr.ui.views.html.medical.edit;
import femr.ui.views.html.medical.newVitals;
import femr.ui.views.html.medical.listVitals;
import femr.ui.views.html.partials.medical.tabs.prescriptionRow;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.*;
import java.util.stream.Collectors;

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
    private final FieldHelper fieldHelper;
    private final IInventoryService inventoryService;

    @Inject
    public MedicalController(ITabService tabService,
                             IEncounterService encounterService,
                             IMedicationService medicationService,
                             IPhotoService photoService,
                             ISessionService sessionService,
                             ISearchService searchService,
                             IVitalService vitalService,
                             IInventoryService inventoryService) {
        this.tabService = tabService;
        this.encounterService = encounterService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.medicationService = medicationService;
        this.photoService = photoService;
        this.vitalService = vitalService;
        this.fieldHelper = new FieldHelper();
        this.inventoryService = inventoryService;
    }

    public Result indexGet() {
        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();

        return ok(index.render(currentUserSession, null, 0));
    }

    public Result indexPost() {
        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();

        String queryString_id = request().body().asFormUrlEncoded().get("id")[0];
        ServiceResponse<Integer> idQueryStringResponse = searchService.parseIdFromQueryString(queryString_id);
        if (idQueryStringResponse.hasErrors()) {

            return ok(index.render(currentUserSession, idQueryStringResponse.getErrors().get(""), 0));
        }
        Integer patientId = idQueryStringResponse.getResponseObject();

        //get the patient's encounter
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrieveRecentPatientEncounterItemByPatientId(patientId);
        if (patientEncounterItemServiceResponse.hasErrors()) {

            return ok(index.render(currentUserSession, patientEncounterItemServiceResponse.getErrors().get(""), 0));
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();

        //check for encounter closed
        if (patientEncounterItem.getIsClosed()) {

            return ok(index.render(currentUserSession, "That patient's encounter has been closed.", 0));
        }

        //check if the doc has already seen the patient today
        ServiceResponse<UserItem> userItemServiceResponse = encounterService.retrievePhysicianThatCheckedInPatientToMedical(patientEncounterItem.getId());
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

        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();

        EditViewModelGet viewModelGet = new EditViewModelGet();

        //Get Patient Encounter
        PatientEncounterItem patientEncounter;
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrieveRecentPatientEncounterItemByPatientId(patientId);
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
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.retrievePatientItemByPatientId(patientId);
        if (patientItemServiceResponse.hasErrors()) {

            throw new RuntimeException();
        }
        viewModelGet.setPatientItem(patientItemServiceResponse.getResponseObject());

        //get prescriptions
        ServiceResponse<List<PrescriptionItem>> prescriptionItemServiceResponse = searchService.retrieveUnreplacedPrescriptionItems(patientEncounter.getId());
        if (prescriptionItemServiceResponse.hasErrors()) {

            throw new RuntimeException();
        }
        viewModelGet.setPrescriptionItems(prescriptionItemServiceResponse.getResponseObject());

        //get MedicationAdministrationItems
        ServiceResponse<List<MedicationAdministrationItem>> medicationAdministrationItemServiceResponse =
                medicationService.retrieveAvailableMedicationAdministrations();
        if (medicationAdministrationItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setMedicationAdministrationItems(medicationAdministrationItemServiceResponse.getResponseObject());

        //get problems
        ServiceResponse<List<ProblemItem>> problemItemServiceResponse = encounterService.retrieveProblemItems(patientEncounter.getId());
        if (problemItemServiceResponse.hasErrors()) {

            throw new RuntimeException();
        }
        viewModelGet.setProblemItems(problemItemServiceResponse.getResponseObject());

        //get vitals
        ServiceResponse<VitalMultiMap> vitalMapResponse = vitalService.retrieveVitalMultiMap(patientEncounter.getId());
        if (vitalMapResponse.hasErrors()) {

            throw new RuntimeException();
        }

        //get all fields and their values
        ServiceResponse<TabFieldMultiMap> tabFieldMultiMapResponse = tabService.retrieveTabFieldMultiMap(patientEncounter.getId());
        if (tabFieldMultiMapResponse.hasErrors()) {

            throw new RuntimeException();
        }
        TabFieldMultiMap tabFieldMultiMap = tabFieldMultiMapResponse.getResponseObject();
        ServiceResponse<List<TabItem>> tabItemServiceResponse = tabService.retrieveAvailableTabs(false);
        if (tabItemServiceResponse.hasErrors()) {

            throw new RuntimeException();
        }

        ServiceResponse<Map<String, List<String>>> tabFieldToTabMappingServiceResponse = tabService.retrieveTabFieldToTabMapping(false, false);
        if (tabFieldToTabMappingServiceResponse.hasErrors()){

            throw new RuntimeException();
        }
        Map<String, List<String>> tabFieldToTabMapping = tabFieldToTabMappingServiceResponse.getResponseObject();



        List<TabItem> tabItems = tabItemServiceResponse.getResponseObject();
        //match the fields to their respective tabs
        for (TabItem tabItem : tabItems) {

            switch (tabItem.getName().toLowerCase()) {
                case "hpi":
                    tabItem.setFields(FieldHelper.structureHPIFieldsForView(tabFieldMultiMap, tabFieldToTabMapping.get("hpi")));
                    break;
                case "pmh":
                    tabItem.setFields(FieldHelper.structurePMHFieldsForView(tabFieldMultiMap, tabFieldToTabMapping.get("pmh")));
                    break;
                case "treatment":
                    tabItem.setFields(FieldHelper.structureTreatmentFieldsForView(tabFieldMultiMap, tabFieldToTabMapping.get("treatment")));
                    break;
                case "photos":
                    break;
                default:
                    tabItem.setFields(fieldHelper.structureDynamicFieldsForView(tabFieldMultiMap, tabFieldToTabMapping.get(tabItem.getName().toLowerCase())));
                    break;
            }
        }
        tabItems = FieldHelper.applyIndicesToFieldsForView(tabItems);
        viewModelGet.setTabItems(tabItems);
        viewModelGet.setChiefComplaints(tabFieldMultiMap.getChiefComplaintList());

        ServiceResponse<List<PhotoItem>> photoListResponse = photoService.retrieveEncounterPhotos(patientEncounter.getId());
        if (photoListResponse.hasErrors()) {

            throw new RuntimeException();
        } else {

            viewModelGet.setPhotos(photoListResponse.getResponseObject());
        }

        ServiceResponse<SettingItem> response = searchService.retrieveSystemSettings();
        viewModelGet.setSettings(response.getResponseObject());

        //Alaa Serhan
        VitalMultiMap vitalMultiMap = vitalMapResponse.getResponseObject();

        return ok(edit.render(currentUserSession, vitalMultiMap, viewModelGet));
    }

    /**
     * Get the populated partial view that represents 1 row of new prescription fields
     * - meant to be an AJAX call
     *
     * @param index
     * @return
     */
    public Result prescriptionRowGet( int index )
    {
        //get MedicationAdministrationItems
        ServiceResponse<List<MedicationAdministrationItem>> medicationAdministrationItemServiceResponse =
                medicationService.retrieveAvailableMedicationAdministrations();
        if (medicationAdministrationItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<MedicationAdministrationItem> items = medicationAdministrationItemServiceResponse.getResponseObject();

        return ok( prescriptionRow.render( items, index, null ) );
    }

    public Result editPost(int patientId) {

        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();

        EditViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();

        //get current patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.retrievePatientItemByPatientId(patientId);
        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientItem patientItem = patientItemServiceResponse.getResponseObject();

        //get current encounter
        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = searchService.retrieveRecentPatientEncounterItemByPatientId(patientId);
        if (patientEncounterServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientEncounterItem patientEncounterItem = patientEncounterServiceResponse.getResponseObject();
        patientEncounterItem = encounterService.checkPatientInToMedical(patientEncounterItem.getId(), currentUserSession.getId()).getResponseObject();

        //get and save problems
        List<String> problemList = new ArrayList<>();
        for (ProblemItem pi : viewModelPost.getProblems()) {
            if (StringUtils.isNotNullOrWhiteSpace(pi.getName())) {
                problemList.add(pi.getName());
            }
        }
        if (problemList.size() > 0) {
            encounterService.createProblems(problemList, patientEncounterItem.getId(), currentUserSession.getId());
        }

        //get tab fields that do not have a related chief complaint and put them into a nice map
        Map<String, String> tabFieldItemsWithNoRelatedChiefComplaint = new HashMap<>();
        Map<String, Map<String, String>> tabFieldItemsWithChiefComplaint = new HashMap<>();
        //get tab fields other than problems
        for (TabFieldItem tfi : viewModelPost.getTabFieldItems()) {
            if (StringUtils.isNotNullOrWhiteSpace(tfi.getValue()) && StringUtils.isNullOrWhiteSpace(tfi.getChiefComplaint())) {

                tabFieldItemsWithNoRelatedChiefComplaint.put(tfi.getName(), tfi.getValue());
            }else if (StringUtils.isNotNullOrWhiteSpace(tfi.getValue()) && StringUtils.isNotNullOrWhiteSpace(tfi.getChiefComplaint())) {

                // Get the tabField Map for chief complaint
                Map<String, String> tabFieldMap = tabFieldItemsWithChiefComplaint.get(tfi.getChiefComplaint());
                if (tabFieldMap == null){
                    // if it does not exist, create it
                    tabFieldMap = new HashMap<>();
                }
                // create and add a tabFieldMap to the Map of Maps for the chief complaint, ummm  yea...
                tabFieldMap.put(tfi.getName(), tfi.getValue());
                tabFieldItemsWithChiefComplaint.put(tfi.getChiefComplaint(), tabFieldMap);
            }
        }
        //save the tab fields that do not have a related chief complaint
        ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFieldsServiceResponse;
        if (tabFieldItemsWithNoRelatedChiefComplaint.size() > 0) {

            createPatientEncounterTabFieldsServiceResponse = encounterService.createPatientEncounterTabFields(tabFieldItemsWithNoRelatedChiefComplaint, patientEncounterItem.getId(), currentUserSession.getId());
            if (createPatientEncounterTabFieldsServiceResponse.hasErrors()) {

                throw new RuntimeException();
            }
        }
        //save the tab fields that do have related chief complaint(s)
        ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFieldsWithChiefComplaintsServiceResponse;
        if (tabFieldItemsWithChiefComplaint.size() > 0){
            //call the service once for each existing chief complaint
            for (Map.Entry<String, Map<String,String>> entry : tabFieldItemsWithChiefComplaint.entrySet()){

                createPatientEncounterTabFieldsWithChiefComplaintsServiceResponse = encounterService.createPatientEncounterTabFields(entry.getValue(), patientEncounterItem.getId(), currentUserSession.getId(), entry.getKey());
                if (createPatientEncounterTabFieldsWithChiefComplaintsServiceResponse.hasErrors()){

                    throw new RuntimeException();
                }
            }
        }

        //create patient encounter photos
        photoService.createEncounterPhotos(request().body().asMultipartFormData().getFiles(), patientEncounterItem, viewModelPost);

        //get the prescriptions that have an ID (e.g. prescriptions that exist in the dictionary).
        List<PrescriptionItem> prescriptionItemsWithID = viewModelPost.getPrescriptions()
                .stream()
                .filter(prescription -> prescription.getMedicationID() != null)
                .collect(Collectors.toList());

        //create the prescriptions that already have an ID
        ServiceResponse<PrescriptionItem> createPrescriptionServiceResponse;
        for (PrescriptionItem prescriptionItem : prescriptionItemsWithID){

            createPrescriptionServiceResponse = medicationService.createPrescription(
                    prescriptionItem.getMedicationID(),
                    prescriptionItem.getAdministrationID(),
                    patientEncounterItem.getId(),
                    currentUserSession.getId(),
                    prescriptionItem.getAmount(),
                    null);

            if (createPrescriptionServiceResponse.hasErrors()){

                throw new RuntimeException();
            }
        }

        // get the prescriptions that DO NOT have an ID (e.g. prescriptions that DO NOT exist in the dictionary).
        // also ignore new new prescriptions that do not have a name
        List<PrescriptionItem> prescriptionItemsWithoutID = viewModelPost.getPrescriptions()
                .stream()
                .filter( prescription -> prescription.getMedicationID() == null )
                .filter( prescription -> StringUtils.isNotNullOrWhiteSpace( prescription.getMedicationName() ) )
                .collect(Collectors.toList());

        for (PrescriptionItem prescriptionItem : prescriptionItemsWithoutID){

            //The POST data sends -1 if an administration ID is not set. Null is more appropriate for the
            //service layer
            if (prescriptionItem.getAdministrationID() == -1)
                prescriptionItem.setAdministrationID(null);

            createPrescriptionServiceResponse = medicationService.createPrescriptionWithNewMedication(
                    prescriptionItem.getMedicationName(),
                    prescriptionItem.getAdministrationID(),
                    patientEncounterItem.getId(),
                    currentUserSession.getId(),
                    prescriptionItem.getAmount(),
                    null);

            if (createPrescriptionServiceResponse.hasErrors()){

                throw new RuntimeException();
            }
        }



        String message = "Patient information for " + patientItem.getFirstName() + " " + patientItem.getLastName() + " (id: " + patientItem.getId() + ") was saved successfully.";

        return ok(index.render(currentUserSession, message, 0));
    }

    public Result updateVitalsPost(int id) {

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        // Alaa Serhan
        // Add View Model to Get the Settings to see if METRIC SYSTEM are set or not
        EditViewModelGet viewModelGet = new EditViewModelGet();
        ServiceResponse<SettingItem> response = searchService.retrieveSystemSettings();
        viewModelGet.setSettings(response.getResponseObject());

        // Get patient Encounter
        ServiceResponse<PatientEncounterItem> currentEncounterByPatientId = searchService.retrieveRecentPatientEncounterItemByPatientId(id);

        if (currentEncounterByPatientId.hasErrors()) {
            throw new RuntimeException();
        }
        PatientEncounterItem patientEncounter = currentEncounterByPatientId.getResponseObject();

        //update date_of_medical_visit when a vital is updated
        encounterService.checkPatientInToMedical(currentEncounterByPatientId.getResponseObject().getId(), currentUser.getId());

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

        // Alaa Serhan - Add View Model to Get the Settings to see if METRIC SYSTEM are set or not
        EditViewModelGet viewModelGet = new EditViewModelGet();
        ServiceResponse<SettingItem> response = searchService.retrieveSystemSettings();
        viewModelGet.setSettings(response.getResponseObject());

        return ok(newVitals.render(viewModelGet));
    }

    public Result listVitalsGet(Integer id) {
        // Alaa Serhan - Add View Model to Get the Settings to see if METRIC SYSTEM are set or not
        EditViewModelGet viewModelGet = new EditViewModelGet();
        ServiceResponse<SettingItem> response = searchService.retrieveSystemSettings();
        viewModelGet.setSettings(response.getResponseObject());

        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = searchService.retrieveRecentPatientEncounterItemByPatientId(id);

        if (patientEncounterServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        ServiceResponse<VitalMultiMap> vitalMultiMapServiceResponse = vitalService.retrieveVitalMultiMap(patientEncounterServiceResponse.getResponseObject().getId());
        if (vitalMultiMapServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        //Alaa Serhan
        VitalMultiMap vitalMap = vitalMultiMapServiceResponse.getResponseObject();

        return ok(listVitals.render(vitalMap, viewModelGet));
    }

    /**
     * Maps vitals from view model to a Map structure
     *
     * @param viewModel the view model with POST data
     * @return Mapped vital value to vital name
     */
    private Map<String, Float> getPatientEncounterVitals(UpdateVitalsModel viewModel) {
        EditViewModelGet viewModelGet = new EditViewModelGet();
        ServiceResponse<SettingItem> response = searchService.retrieveSystemSettings();
        viewModelGet.setSettings(response.getResponseObject());

        Map<String, Float> newVitals = new HashMap<>();
        if (viewModel.getRespiratoryRate() != null) {
            newVitals.put("respiratoryRate", viewModel.getRespiratoryRate());
        }

        if (viewModel.getHeartRate() != null) {
            newVitals.put("heartRate", viewModel.getHeartRate());
        }

        //Alaa Serhan
        if (viewModel.getTemperature() != null) {
            Float temperature = viewModel.getTemperature();

            newVitals.put("temperature", temperature);
        }
        if (viewModel.getOxygenSaturation() != null) {
            newVitals.put("oxygenSaturation", viewModel.getOxygenSaturation());
        }

        //Alaa Serhan
        if (viewModel.getHeightFeet() != null && viewModel.getHeightInches() != null) {
            Float heightFeet = viewModel.getHeightFeet().floatValue();
            Float heightInches = viewModel.getHeightInches().floatValue();

            newVitals.put("heightFeet", heightFeet);
            newVitals.put("heightInches", heightInches);
        }

        //Alaa Serhan
        if (viewModel.getWeight() != null) {
            Float weight = viewModel.getWeight();

            newVitals.put("weight", weight);
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

        if (viewModel.getWeeksPregnant() != null) {
            newVitals.put("weeksPregnant", viewModel.getWeeksPregnant());
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
