package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.services.core.IEncounterService;
import femr.business.services.core.IPhotoService;
import femr.business.services.core.ISearchService;
import femr.business.services.core.ISessionService;
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
    private final IPhotoService photoService;

    @Inject
    public HistoryController(IEncounterService encounterService,
                             ISessionService sessionService,
                             ISearchService searchService,
                             IPhotoService photoService) {

        this.encounterService = encounterService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.photoService = photoService;
    }

    public Result indexPatientGet(String query) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        boolean error = false;


        IndexPatientViewModelGet viewModel = new IndexPatientViewModelGet();

        //how do we show more than one?
        query = query.replace("-", " ");
        ServiceResponse<List<PatientItem>> patientResponse = searchService.getPatientsFromQueryString(query);
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

        ServiceResponse<List<PatientEncounterItem>> patientEncountersServiceResponse = searchService.findPatientEncounterItemsByPatientId(patientItems.get(0).getId());
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
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        IndexEncounterViewModel indexEncounterViewModel = new IndexEncounterViewModel();
        IndexEncounterMedicalViewModel indexEncounterMedicalViewModel = new IndexEncounterMedicalViewModel();
        IndexEncounterPharmacyViewModel indexEncounterPharmacyViewModel = new IndexEncounterPharmacyViewModel();

        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemByEncounterId(encounterId);
        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientItem patientItem = patientItemServiceResponse.getResponseObject();
        patientItem.setPathToPhoto(routes.PhotoController.GetPatientPhoto(patientItem.getId(), true).toString());
        indexEncounterViewModel.setPatientItem(patientItem);

        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.findPatientEncounterItemByEncounterId(encounterId);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        indexEncounterViewModel.setPatientEncounterItem(patientEncounterItemServiceResponse.getResponseObject());

        //get vitals
        ServiceResponse<VitalMultiMap> patientEncounterVitalMapResponse = searchService.getVitalMultiMap(encounterId);
        if (patientEncounterVitalMapResponse.hasErrors()) {
            throw new RuntimeException();
        }
        indexEncounterMedicalViewModel.setVitalList(patientEncounterVitalMapResponse.getResponseObject());

        //get photos
        ServiceResponse<List<PhotoItem>> photoListResponse = photoService.GetEncounterPhotos(encounterId);
        if (photoListResponse.hasErrors()) {
            throw new RuntimeException();
        }
        indexEncounterMedicalViewModel.setPhotos(photoListResponse.getResponseObject());

        ServiceResponse<TabFieldMultiMap> patientEncounterTabFieldResponse = searchService.getTabFieldMultiMap(encounterId);
        if (patientEncounterTabFieldResponse.hasErrors()) {
            throw new RuntimeException();
        }
        TabFieldMultiMap tabFieldMultiMap = patientEncounterTabFieldResponse.getResponseObject();

        //extract the most recent treatment fields
        Map<String, String> treatmentFields = new HashMap<>();
        treatmentFields.put("assessment", tabFieldMultiMap.getMostRecent("assessment", null));
        treatmentFields.put("treatment", tabFieldMultiMap.getMostRecent("treatment", null));
        indexEncounterMedicalViewModel.setTreatmentFields(treatmentFields);

        //extract the most recent pmh fields
        Map<String, String> pmhFields = new HashMap<>();
        pmhFields.put("medicalSurgicalHistory", tabFieldMultiMap.getMostRecent("medicalSurgicalHistory", null));
        pmhFields.put("socialHistory", tabFieldMultiMap.getMostRecent("socialHistory", null));
        pmhFields.put("currentMedications", tabFieldMultiMap.getMostRecent("currentMedications", null));
        pmhFields.put("familyHistory", tabFieldMultiMap.getMostRecent("familyHistory", null));
        indexEncounterMedicalViewModel.setPmhFields(pmhFields);

        //extract the most recent hpi fields
        if (patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints().size() > 1) {
            indexEncounterMedicalViewModel.setHpiFieldsWithMultipleChiefComplaints(extractHpiFieldsWithMultipleChiefComplaints(tabFieldMultiMap, patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints()));
            indexEncounterMedicalViewModel.setIsMultipleChiefComplaints(true);
        }else{
            Map<String,String> hpiFields = new HashMap<>();
            hpiFields.put("onset", tabFieldMultiMap.getMostRecent("onset", null));
            hpiFields.put("quality", tabFieldMultiMap.getMostRecent("quality", null));
            hpiFields.put("radiation", tabFieldMultiMap.getMostRecent("radiation", null));
            hpiFields.put("severity", tabFieldMultiMap.getMostRecent("severity", null));
            hpiFields.put("provokes", tabFieldMultiMap.getMostRecent("provokes", null));
            hpiFields.put("palliates", tabFieldMultiMap.getMostRecent("palliates", null));
            hpiFields.put("timeOfDay", tabFieldMultiMap.getMostRecent("timeOfDay", null));
            hpiFields.put("narrative", tabFieldMultiMap.getMostRecent("narrative", null));
            hpiFields.put("physicalExamination", tabFieldMultiMap.getMostRecent("physicalExamination", null));
            indexEncounterMedicalViewModel.setHpiFieldsWithoutMultipleChiefComplaints(hpiFields);
            indexEncounterMedicalViewModel.setIsMultipleChiefComplaints(false);
        }

        //extract the most recent custom fields
        indexEncounterMedicalViewModel.setCustomFields(extractCustomFields(tabFieldMultiMap));

        //get problems
        List<String> problems = new ArrayList<>();
        ServiceResponse<List<ProblemItem>> problemItemServiceResponse = encounterService.findProblemItems(encounterId);
        if (problemItemServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        for (ProblemItem pi : problemItemServiceResponse.getResponseObject()){
            problems.add(pi.getName());
        }
        indexEncounterPharmacyViewModel.setProblems(problems);

        //get prescriptions
        List<String> prescriptions = new ArrayList<>();
        ServiceResponse<List<PrescriptionItem>> prescriptionItemServiceResponse = searchService.findDispensedPrescriptionItems(encounterId);
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
        ServiceResponse<List<String>> customFieldNamesServiceResponse = searchService.getCustomFieldList();
        if (customFieldNamesServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<String> customFieldNamess = customFieldNamesServiceResponse.getResponseObject();
        for (String customField : customFieldNamess) {
            customFields.put(customField, tabFieldMultiMap.getMostRecent(customField, null));
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
            hpiFieldsUnderChiefComplaint.put("onset", tabFieldMultiMap.getMostRecent("onset", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("quality", tabFieldMultiMap.getMostRecent("quality", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("radiation", tabFieldMultiMap.getMostRecent("radiation", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("severity", tabFieldMultiMap.getMostRecent("severity", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("provokes", tabFieldMultiMap.getMostRecent("provokes", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("palliates", tabFieldMultiMap.getMostRecent("palliates", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("timeOfDay", tabFieldMultiMap.getMostRecent("timeOfDay", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("narrative", tabFieldMultiMap.getMostRecent("narrative", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("physicalExamination", tabFieldMultiMap.getMostRecent("physicalExamination", cc.trim()));
            hpiFields.put(cc.trim(), hpiFieldsUnderChiefComplaint);
        }

        return hpiFields;
    }

}
