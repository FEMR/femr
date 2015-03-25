package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.history.IndexEncounterMedicalViewModel;
import femr.ui.models.history.IndexEncounterPharmacyViewModel;
import femr.ui.models.history.IndexEncounterViewModel;
import femr.ui.models.history.IndexPatientViewModelGet;
import femr.ui.views.html.history.indexEncounter;
import femr.ui.views.html.history.indexPatient;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.calculations.VitalUnitConverter;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.mvc.Security;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class HistoryController extends Controller {

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

        // If metric view is enabled convert vitals to metric
        if (indexEncounterMedicalViewModel.getSettings().isMetric()) {
            vitalMultiMap = VitalUnitConverter.toMetric(vitalMultiMap);
        }
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
        Map<String, String> treatmentFields = new HashMap<>();
        treatmentFields.put("assessment", tabFieldMultiMap.getMostRecentOrEmpty("assessment", null).getValue());
        treatmentFields.put("treatment", tabFieldMultiMap.getMostRecentOrEmpty("treatment", null).getValue());
        indexEncounterMedicalViewModel.setTreatmentFields(treatmentFields);

        //extract the most recent pmh fields
        Map<String, String> pmhFields = new HashMap<>();
        pmhFields.put("medicalSurgicalHistory", tabFieldMultiMap.getMostRecentOrEmpty("medicalSurgicalHistory", null).getValue());
        pmhFields.put("socialHistory", tabFieldMultiMap.getMostRecentOrEmpty("socialHistory", null).getValue());
        pmhFields.put("currentMedications", tabFieldMultiMap.getMostRecentOrEmpty("currentMedication", null).getValue());
        pmhFields.put("familyHistory", tabFieldMultiMap.getMostRecentOrEmpty("familyHistory", null).getValue());
        indexEncounterMedicalViewModel.setPmhFields(pmhFields);

        //extract the most recent hpi fields
        if (patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints().size() > 1) {
            indexEncounterMedicalViewModel.setHpiFieldsWithMultipleChiefComplaints(extractHpiFieldsWithMultipleChiefComplaints(tabFieldMultiMap, patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints()));
            indexEncounterMedicalViewModel.setIsMultipleChiefComplaints(true);
        }else{
            String chiefComplaint = null;
            if (patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints().size() == 1)
                chiefComplaint = patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints().get(0);
            Map<String,String> hpiFields = new HashMap<>();
            hpiFields.put("onset", tabFieldMultiMap.getMostRecentOrEmpty("onset", chiefComplaint).getValue());
            hpiFields.put("quality", tabFieldMultiMap.getMostRecentOrEmpty("quality", chiefComplaint).getValue());
            hpiFields.put("radiation", tabFieldMultiMap.getMostRecentOrEmpty("radiation", chiefComplaint).getValue());
            hpiFields.put("severity", tabFieldMultiMap.getMostRecentOrEmpty("severity", chiefComplaint).getValue());
            hpiFields.put("provokes", tabFieldMultiMap.getMostRecentOrEmpty("provokes", chiefComplaint).getValue());
            hpiFields.put("palliates", tabFieldMultiMap.getMostRecentOrEmpty("palliates", chiefComplaint).getValue());
            hpiFields.put("timeOfDay", tabFieldMultiMap.getMostRecentOrEmpty("timeOfDay", chiefComplaint).getValue());
            hpiFields.put("narrative", tabFieldMultiMap.getMostRecentOrEmpty("narrative", chiefComplaint).getValue());
            hpiFields.put("physicalExamination", tabFieldMultiMap.getMostRecentOrEmpty("physicalExamination", chiefComplaint).getValue());
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
    private Map<String, String> extractCustomFields(TabFieldMultiMap tabFieldMultiMap) {

        Map<String, String> customFields = new HashMap<>();
        List<String> customFieldNames = tabFieldMultiMap.getCustomFieldNameList();
        for (String customField : customFieldNames) {

            customFields.put(customField, tabFieldMultiMap.getMostRecentOrEmpty(customField, null).getValue());
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
    private Map<String, Map<String, String>> extractHpiFieldsWithMultipleChiefComplaints(TabFieldMultiMap tabFieldMultiMap, List<String> chiefComplaints) {
        Map<String, Map<String, String>> hpiFields = new HashMap<>();

        for (String cc : chiefComplaints) {
            Map<String, String> hpiFieldsUnderChiefComplaint = new HashMap<>();
            hpiFieldsUnderChiefComplaint.put("onset", tabFieldMultiMap.getMostRecentOrEmpty("onset", cc.trim()).getValue());
            hpiFieldsUnderChiefComplaint.put("quality", tabFieldMultiMap.getMostRecentOrEmpty("quality", cc.trim()).getValue());
            hpiFieldsUnderChiefComplaint.put("radiation", tabFieldMultiMap.getMostRecentOrEmpty("radiation", cc.trim()).getValue());
            hpiFieldsUnderChiefComplaint.put("severity", tabFieldMultiMap.getMostRecentOrEmpty("severity", cc.trim()).getValue());
            hpiFieldsUnderChiefComplaint.put("provokes", tabFieldMultiMap.getMostRecentOrEmpty("provokes", cc.trim()).getValue());
            hpiFieldsUnderChiefComplaint.put("palliates", tabFieldMultiMap.getMostRecentOrEmpty("palliates", cc.trim()).getValue());
            hpiFieldsUnderChiefComplaint.put("timeOfDay", tabFieldMultiMap.getMostRecentOrEmpty("timeOfDay", cc.trim()).getValue());
            hpiFieldsUnderChiefComplaint.put("narrative", tabFieldMultiMap.getMostRecentOrEmpty("narrative", cc.trim()).getValue());
            hpiFieldsUnderChiefComplaint.put("physicalExamination", tabFieldMultiMap.getMostRecentOrEmpty("physicalExamination", cc.trim()).getValue());
            hpiFields.put(cc.trim(), hpiFieldsUnderChiefComplaint);
        }

        return hpiFields;
    }

}
