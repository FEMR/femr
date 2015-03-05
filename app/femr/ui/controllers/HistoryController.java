package femr.ui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.history.*;
import femr.ui.models.medical.EditViewModelPost;

import femr.ui.models.medical.UpdateVitalsModel;
import femr.ui.views.html.history.indexEncounter;
import femr.ui.views.html.history.indexPatient;
import femr.ui.views.html.history.editEncounterGet;
import femr.ui.views.html.history.editEncounterPost;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.mvc.Security;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class HistoryController extends Controller {
    private final Form<IndexPatientViewModelPost> EditEncounterForm = Form.form(IndexPatientViewModelPost.class);
    private final Form<fieldValueViewModel> fieldValueViewModelForm = Form.form(fieldValueViewModel.class);
    private final IMedicationService medicationService;
    private final IEncounterService encounterService;
    private final ISessionService sessionService;
    private final ISearchService searchService;
    private final ITabService tabService;
    private final IPhotoService photoService;
    private final IVitalService vitalService;
    private final IEncounterService assessmentService;

    @Inject
    public HistoryController(IEncounterService encounterService,
                             IEncounterService assessmentService,
                             ISessionService sessionService,
                             IMedicationService medicationService,
                             ISearchService searchService,
                             ITabService tabService,
                             IPhotoService photoService,
                             IVitalService vitalService) {

        this.encounterService = encounterService;
        this.assessmentService = assessmentService;
        this.sessionService = sessionService;
        this.medicationService = medicationService;
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
        indexEncounterMedicalViewModel.setVitalList(patientEncounterVitalMapResponse.getResponseObject());

        //get photos
        ServiceResponse<List<PhotoItem>> photoListResponse = photoService.retrieveEncounterPhotos(encounterId);
        if (photoListResponse.hasErrors()) {
            throw new RuntimeException();
        }
        indexEncounterMedicalViewModel.setPhotos(photoListResponse.getResponseObject());

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

    private List<TabFieldItem> mapHpiFieldItems(IndexPatientViewModelPost patientViewModelPost) {
        List<TabFieldItem> tabFieldItems = new ArrayList<>();
        //hpi fields
        if (patientViewModelPost.getOnset() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getOnset()))
            tabFieldItems.add(createTabFieldItem("onset", patientViewModelPost.getOnset()));
        if (patientViewModelPost.getOnsetTime() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getOnsetTime()))
            tabFieldItems.add(createTabFieldItem("onsetTime", patientViewModelPost.getOnsetTime()));
        if (patientViewModelPost.getSeverity() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getSeverity()))
            tabFieldItems.add(createTabFieldItem("severity", patientViewModelPost.getSeverity()));
        if (patientViewModelPost.getRadiation() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getRadiation()))
            tabFieldItems.add(createTabFieldItem("radiation", patientViewModelPost.getRadiation()));
        if (patientViewModelPost.getQuality() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getQuality()))
            tabFieldItems.add(createTabFieldItem("quality", patientViewModelPost.getQuality()));
        if (patientViewModelPost.getProvokes() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getProvokes()))
            tabFieldItems.add(createTabFieldItem("provokes", patientViewModelPost.getProvokes()));
        if (patientViewModelPost.getPalliates() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getPalliates()))
            tabFieldItems.add(createTabFieldItem("palliates", patientViewModelPost.getPalliates()));
        if (patientViewModelPost.getTimeOfDay() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getTimeOfDay()))
            tabFieldItems.add(createTabFieldItem("timeOfDay", patientViewModelPost.getTimeOfDay()));
        if (patientViewModelPost.getPhysicalExamination() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getPhysicalExamination()))
            tabFieldItems.add(createTabFieldItem("physicalExamination", patientViewModelPost.getPhysicalExamination()));
        if (patientViewModelPost.getNarrative() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getNarrative()))
            tabFieldItems.add(createTabFieldItem("narrative", patientViewModelPost.getNarrative()));
        return tabFieldItems;
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
    private List<TabFieldItem> mapPmhFieldItems(IndexPatientViewModelPost patientViewModelPost) {
        List<TabFieldItem> tabFieldItems = new ArrayList<>();
        //Pmh_fields
        if (patientViewModelPost.getMedicalSurgicalHistory() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getMedicalSurgicalHistory()))
            tabFieldItems.add(createTabFieldItem("medicalSurgicalHistory", patientViewModelPost.getMedicalSurgicalHistory()));
        if (patientViewModelPost.getSocialHistory() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getSocialHistory()))
            tabFieldItems.add(createTabFieldItem("socialHistory", patientViewModelPost.getSocialHistory()));
        if (patientViewModelPost.getCurrentMedication() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getCurrentMedication()))
            tabFieldItems.add(createTabFieldItem("currentMedication", patientViewModelPost.getCurrentMedication()));
        if (patientViewModelPost.getFamilyHistory() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getFamilyHistory()))
            tabFieldItems.add(createTabFieldItem("familyHistory", patientViewModelPost.getFamilyHistory()));
        return tabFieldItems;
    }
    private List<TabFieldItem> mapTreatmentFieldItems(IndexPatientViewModelPost patientViewModelPost) {
        List<TabFieldItem> tabFieldItems = new ArrayList<>();
        //treatment fields
        if (patientViewModelPost.getAssessment() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getAssessment()))
            tabFieldItems.add(createTabFieldItem("assessment", patientViewModelPost.getAssessment()));
        if (patientViewModelPost.getProblem1() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getProblem1()))
            tabFieldItems.add(createTabFieldItem("problem", patientViewModelPost.getProblem1()));
        if (patientViewModelPost.getProblem2() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getProblem2()))
            tabFieldItems.add(createTabFieldItem("problem", patientViewModelPost.getProblem2()));
        if (patientViewModelPost.getProblem3() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getProblem3()))
            tabFieldItems.add(createTabFieldItem("problem", patientViewModelPost.getProblem3()));
        if (patientViewModelPost.getProblem4() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getProblem4()))
            tabFieldItems.add(createTabFieldItem("problem", patientViewModelPost.getProblem4()));
        if (patientViewModelPost.getProblem5() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getProblem5()))
            tabFieldItems.add(createTabFieldItem("problem", patientViewModelPost.getProblem5()));
        if (patientViewModelPost.getTreatment() != null && StringUtils.isNotNullOrWhiteSpace(patientViewModelPost.getTreatment()))
            tabFieldItems.add(createTabFieldItem("treatment", patientViewModelPost.getTreatment()));
        return tabFieldItems;
    }



    public Result updateFieldPost(int id) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<PatientEncounterItem> currentEncounterByPatientId = searchService.findRecentPatientEncounterItemByPatientId(id);
        if (currentEncounterByPatientId.hasErrors()) {
            throw new RuntimeException();
        }
        //update date_of_medical_visit when a vital is updated
        encounterService.checkPatientInToMedical(currentEncounterByPatientId.getResponseObject().getId(), currentUser.getId());

        PatientEncounterItem patientEncounter = currentEncounterByPatientId.getResponseObject();

        fieldValueViewModel fieldValueViewModel = fieldValueViewModelForm.bindFromRequest().get();

      //  Map<String,Integer> patientEncounterAssessments = getPatientAssessments(fieldValueViewModel);
      //ServiceResponse<List<AssessmentItem>> patientEncounterAssessmentsServiceResponse =
            //    assessmentService.updatePatientEncounterTabFields(currentUser.getId(),patientEncounter.getId());
      //  if (patientEncounterAssessmentsServiceResponse.hasErrors()) {
        //    throw new RuntimeException();
       //}

        return ok("true");
    }

    private Map< String,Integer> getPatientAssessments(fieldValueViewModel viewModel) {
        Map<String,Integer> newAssessments = new HashMap<>();
        if (viewModel.getFieldName() != null) {
            newAssessments.put("fieldName", viewModel.getFieldName());
        }
        if (viewModel.getFieldValue() != null) {
            newAssessments.put("fieldValue", viewModel.getFieldValue());
        }


        return newAssessments;
    }

}
