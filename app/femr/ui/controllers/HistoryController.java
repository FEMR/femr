package femr.ui.controllers;

import com.google.inject.Inject;
import femr.ui.helpers.security.AllowedRoles;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.history.*;
import femr.ui.views.html.history.indexEncounter;
import femr.ui.views.html.history.indexPatient;
import femr.ui.views.html.history.listTabFieldHistory;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import java.util.ArrayList;
import java.util.HashMap;
import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import java.util.Map;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class HistoryController extends Controller {

    private final Form<fieldValueViewModel> fieldValueViewModelForm = Form.form(fieldValueViewModel.class);
    private final IEncounterService encounterService;
    private final ISessionService sessionService;
    private final ISearchService searchService;
    private final ITabService tabService;
    private final IPhotoService photoService;
    private final IVitalService vitalService;

    @Inject
    public HistoryController(IEncounterService encounterService,
                             ISessionService sessionService,
                             ISearchService searchService,
                             ITabService tabService,
                             IPhotoService photoService,
                             IVitalService vitalService) {

        this.encounterService = encounterService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.tabService = tabService;
        this.photoService = photoService;
        this.vitalService = vitalService;
    }

    public Result indexPatientGet(String query) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        boolean error = false;


        IndexPatientViewModelGet viewModel = new IndexPatientViewModelGet();

        //how do we show more than one?
        query = query.replace("-", " ");
        ServiceResponse<List<PatientItem>> patientResponse = searchService.retrievePatientsFromQueryString(query);
        if (patientResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<PatientItem> patientItems = patientResponse.getResponseObject();


        if (patientItems == null || patientItems.size() < 1) {
//            return ok(showError.render(currentUser));
            //return an error near the search box
        }

        //too much logic - move patient photo finding up to the service layer
        for (PatientItem patientItem : patientItems)
            patientItem.setPathToPhoto(routes.PhotoController.GetPatientPhoto(patientItem.getId(), true).toString());
        viewModel.setPatientItems(patientItems);
        viewModel.setPatientItem(patientItems.get(0));

        ServiceResponse<List<PatientEncounterItem>> patientEncountersServiceResponse = searchService.retrievePatientEncounterItemsByPatientId(patientItems.get(0).getId());
        if (patientEncountersServiceResponse.hasErrors()) {



            throw new RuntimeException();
        }
        List<PatientEncounterItem> patientEncounterItems = patientEncountersServiceResponse.getResponseObject();
        if (patientEncounterItems == null || patientEncounterItems.size() < 1) {
            //return ok(showError.render(currentUser));
            //return an error near the search box
        }
        viewModel.setPatientEncounterItems(patientEncounterItems);


        return ok(indexPatient.render(currentUser, error, viewModel, patientEncounterItems));
    }

    public Result indexEncounterGet(int encounterId) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        IndexEncounterViewModel indexEncounterViewModel = new IndexEncounterViewModel();
        IndexEncounterMedicalViewModel indexEncounterMedicalViewModel = new IndexEncounterMedicalViewModel();
        IndexEncounterPharmacyViewModel indexEncounterPharmacyViewModel = new IndexEncounterPharmacyViewModel();

        /* Alaa Serhan */
        ServiceResponse<SettingItem> response = searchService.retrieveSystemSettings();

        indexEncounterMedicalViewModel.setSettings(response.getResponseObject());

        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.retrievePatientItemByEncounterId(encounterId);

        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientItem patientItem = patientItemServiceResponse.getResponseObject();
        patientItem.setPathToPhoto(routes.PhotoController.GetPatientPhoto(patientItem.getId(), true).toString());
        indexEncounterViewModel.setPatientItem(patientItem);

        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrievePatientEncounterItemByEncounterId(encounterId);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        indexEncounterViewModel.setPatientEncounterItem(patientEncounterItemServiceResponse.getResponseObject());

        //get vitals
        ServiceResponse<VitalMultiMap> patientEncounterVitalMapResponse = vitalService.retrieveVitalMultiMap(encounterId);
        if (patientEncounterVitalMapResponse.hasErrors()) {
            throw new RuntimeException();
        }

        /* Alaa Serhan */
        // Get patient vitals
        VitalMultiMap vitalMultiMap = patientEncounterVitalMapResponse.getResponseObject();
        indexEncounterMedicalViewModel.setVitalList(vitalMultiMap);

        //get photos
        ServiceResponse<List<PhotoItem>> photoListResponse = photoService.retrieveEncounterPhotos(encounterId);
        if (photoListResponse.hasErrors()) {
            throw new RuntimeException();
        }
        indexEncounterMedicalViewModel.setPhotos(photoListResponse.getResponseObject());


        // Get patient encounter tab fields
        ServiceResponse<TabFieldMultiMap> patientEncounterTabFieldResponse = tabService.retrieveTabFieldMultiMap(encounterId);

        if (patientEncounterTabFieldResponse.hasErrors()) {
            throw new RuntimeException();
        }
        TabFieldMultiMap tabFieldMultiMap = patientEncounterTabFieldResponse.getResponseObject();

        //extract the most recent treatment fields
        Map<String, TabFieldItem> treatmentFields = new HashMap<>();
        treatmentFields.put("assessment", tabFieldMultiMap.getMostRecentOrEmpty("assessment", null));
        treatmentFields.put("treatment", tabFieldMultiMap.getMostRecentOrEmpty("treatment", null));
        indexEncounterMedicalViewModel.setTreatmentFields(treatmentFields);

        //extract the most recent pmh fields
        Map<String, TabFieldItem> pmhFields = new HashMap<>();
        pmhFields.put("medicalSurgicalHistory", tabFieldMultiMap.getMostRecentOrEmpty("medicalSurgicalHistory", null));
        pmhFields.put("socialHistory", tabFieldMultiMap.getMostRecentOrEmpty("socialHistory", null));
        pmhFields.put("currentMedications", tabFieldMultiMap.getMostRecentOrEmpty("currentMedication", null));
        pmhFields.put("familyHistory", tabFieldMultiMap.getMostRecentOrEmpty("familyHistory", null));
        indexEncounterMedicalViewModel.setPmhFields(pmhFields);

        //extract the most recent hpi fields
        if (patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints().size() > 1) {
            indexEncounterMedicalViewModel.setHpiFieldsWithMultipleChiefComplaints(extractHpiFieldsWithMultipleChiefComplaints(tabFieldMultiMap, patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints()));
            indexEncounterMedicalViewModel.setIsMultipleChiefComplaints(true);
        }else{
            String chiefComplaint = null;
            if (patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints().size() == 1)
                chiefComplaint = patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints().get(0);
            Map<String, TabFieldItem> hpiFields = new HashMap<>();
            hpiFields.put("onset", tabFieldMultiMap.getMostRecentOrEmpty("onset", chiefComplaint));
            hpiFields.put("quality", tabFieldMultiMap.getMostRecentOrEmpty("quality", chiefComplaint));
            hpiFields.put("radiation", tabFieldMultiMap.getMostRecentOrEmpty("radiation", chiefComplaint));
            hpiFields.put("severity", tabFieldMultiMap.getMostRecentOrEmpty("severity", chiefComplaint));
            hpiFields.put("provokes", tabFieldMultiMap.getMostRecentOrEmpty("provokes", chiefComplaint));
            hpiFields.put("palliates", tabFieldMultiMap.getMostRecentOrEmpty("palliates", chiefComplaint));
            hpiFields.put("timeOfDay", tabFieldMultiMap.getMostRecentOrEmpty("timeOfDay", chiefComplaint));
            hpiFields.put("narrative", tabFieldMultiMap.getMostRecentOrEmpty("narrative", chiefComplaint));
            hpiFields.put("physicalExamination", tabFieldMultiMap.getMostRecentOrEmpty("physicalExamination", chiefComplaint));
            indexEncounterMedicalViewModel.setHpiFieldsWithoutMultipleChiefComplaints(hpiFields);
            indexEncounterMedicalViewModel.setIsMultipleChiefComplaints(false);
        }

        //extract the most recent custom fields
        indexEncounterMedicalViewModel.setCustomFields(extractCustomFields(tabFieldMultiMap));

        //get problems
        List<String> problems = new ArrayList<>();
        ServiceResponse<List<ProblemItem>> problemItemServiceResponse = encounterService.retrieveProblemItems(encounterId);
        if (problemItemServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        for (ProblemItem pi : problemItemServiceResponse.getResponseObject()){
            problems.add(pi.getName());
        }
        indexEncounterPharmacyViewModel.setProblems(problems);

        //get prescriptions
        List<String> prescriptions = new ArrayList<>();
        ServiceResponse<List<PrescriptionItem>> prescriptionItemServiceResponse = searchService.retrieveDispensedPrescriptionItems(encounterId);
        if (prescriptionItemServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        for (PrescriptionItem prescriptionItem : prescriptionItemServiceResponse.getResponseObject()){
            prescriptions.add(prescriptionItem.getName());
        }
        indexEncounterPharmacyViewModel.setPrescriptions(prescriptions);


        return ok(indexEncounter.render(currentUser, indexEncounterViewModel, indexEncounterMedicalViewModel, indexEncounterPharmacyViewModel));
    }

    /**
     * Extracts the most recent custom fields from the tabfieldmultimap.
     *
     * @param tabFieldMultiMap
     * @return
     */
    private Map<String, TabFieldItem> extractCustomFields(TabFieldMultiMap tabFieldMultiMap) {

        Map<String, TabFieldItem> customFields = new HashMap<>();
        List<String> customFieldNames = tabFieldMultiMap.getCustomFieldNameList();
        for (String customField : customFieldNames) {

            customFields.put(customField, tabFieldMultiMap.getMostRecentOrEmpty(customField, null));
        }
        return customFields;
    }

    /**
     * Extracts the most recent HPI fields from the tabfieldmultimap. Takes into consideration all chief complaints.
     *
     * @param tabFieldMultiMap
     * @param chiefComplaints
     * @return
     */
    private Map<String, Map<String, TabFieldItem>> extractHpiFieldsWithMultipleChiefComplaints(TabFieldMultiMap tabFieldMultiMap, List<String> chiefComplaints) {
        Map<String, Map<String, TabFieldItem>> hpiFields = new HashMap<>();

        for (String cc : chiefComplaints) {
            Map<String, TabFieldItem> hpiFieldsUnderChiefComplaint = new HashMap<>();
            hpiFieldsUnderChiefComplaint.put("onset", tabFieldMultiMap.getMostRecentOrEmpty("onset", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("quality", tabFieldMultiMap.getMostRecentOrEmpty("quality", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("radiation", tabFieldMultiMap.getMostRecentOrEmpty("radiation", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("severity", tabFieldMultiMap.getMostRecentOrEmpty("severity", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("provokes", tabFieldMultiMap.getMostRecentOrEmpty("provokes", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("palliates", tabFieldMultiMap.getMostRecentOrEmpty("palliates", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("timeOfDay", tabFieldMultiMap.getMostRecentOrEmpty("timeOfDay", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("narrative", tabFieldMultiMap.getMostRecentOrEmpty("narrative", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("physicalExamination", tabFieldMultiMap.getMostRecentOrEmpty("physicalExamination", cc.trim()));
            hpiFields.put(cc.trim(), hpiFieldsUnderChiefComplaint);
        }

        return hpiFields;

    }

    private TabFieldItem createTabFieldItem(String name, String value) {
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
    private TabFieldItem createTabFieldItemWithChiefComplaint(String name, String value, String complaint) {
        TabFieldItem tabFieldItem = createTabFieldItem(name, value);
        tabFieldItem.setChiefComplaint(complaint);
        return tabFieldItem;
    }

    //Added by Amney Iskandar //

    public Result updateFieldPost(int id) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();


        //If there is no patient Id or Error
        ServiceResponse<PatientEncounterItem> patientEncounterByEncounterId = searchService.retrievePatientEncounterItemByEncounterId(id);
        if (patientEncounterByEncounterId.hasErrors()) {
            throw new RuntimeException();
        }

        //Get Patient Encounter
        PatientEncounterItem patientEncounter = patientEncounterByEncounterId.getResponseObject();

        //Populate model with request data
        fieldValueViewModel fields = fieldValueViewModelForm.bindFromRequest().get();

        //Create a list of submitted tab fields
        List<TabFieldItem> items = new ArrayList<TabFieldItem>();
        if (StringUtils.isNullOrWhiteSpace(fields.getChiefComplaintName()))
            items.add(createTabFieldItem(fields.getFieldName(), fields.getFieldValue()));
        else
            items.add(createTabFieldItemWithChiefComplaint(fields.getFieldName(), fields.getFieldValue(), fields.getChiefComplaintName()));

        //Create encounter tab fields with the item , patient encounter, current User
        ServiceResponse<List<TabFieldItem>> patientEncounterTabFieldsServiceResponse =
               encounterService.createPatientEncounterTabFields(items, patientEncounter.getId(),currentUser.getId());

        if (patientEncounterTabFieldsServiceResponse.hasErrors()) {

            throw new RuntimeException();
        }

        return ok("true");
    }

    //Added by Amney Iskandar //
    public Result listTabFieldHistoryGet(int encounterID) {

        //Retrieve patient encounter
        ServiceResponse<PatientEncounterItem> patientEncounterByEncounterId = searchService.retrievePatientEncounterItemByEncounterId(encounterID);
        //If patient has errors
        if (patientEncounterByEncounterId.hasErrors()) {

            throw new RuntimeException();
        }
        PatientEncounterItem patientEncounter = patientEncounterByEncounterId.getResponseObject();

        //Populate model with request data that was changed
        fieldValueViewModel fields = fieldValueViewModelForm.bindFromRequest().get();

        ServiceResponse<TabFieldMultiMap> tabFieldsReponseObject =
                tabService.findTabFieldMultiMap(patientEncounter.getId(), fields.getFieldName(), fields.getChiefComplaintName());
        if (tabFieldsReponseObject.hasErrors()) {
            throw new RuntimeException();
        }

        return ok(listTabFieldHistory.render(fields.getFieldName(), tabFieldsReponseObject.getResponseObject()));
    }

}
