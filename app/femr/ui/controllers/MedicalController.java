package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IMedicalService;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.*;
import femr.data.models.PatientEncounterHpiField;
import femr.ui.helpers.controller.MedicalHelper;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.medical.CreateViewModelGet;
import femr.ui.models.medical.CreateViewModelPost;
import femr.ui.models.medical.SearchViewModel;
import femr.ui.models.medical.UpdateVitalsModel;
import femr.ui.views.html.medical.edit;
import femr.ui.views.html.medical.index;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.*;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class MedicalController extends Controller {

    private final Form<CreateViewModelPost> createViewModelPostForm = Form.form(CreateViewModelPost.class);
    private final Form<SearchViewModel> searchViewModelForm = Form.form(SearchViewModel.class);
    private final Form<UpdateVitalsModel> updateVitalsModelForm = Form.form(UpdateVitalsModel.class);
    private ISessionService sessionService;
    private ISearchService searchService;
    private ITriageService triageService;
    private IMedicalService medicalService;
    private MedicalHelper medicalHelper;

    @Inject
    public MedicalController(ISessionService sessionService, ISearchService searchService, ITriageService triageService, IMedicalService medicalService, MedicalHelper medicalHelper) {

        this.sessionService = sessionService;
        this.searchService = searchService;
        this.triageService = triageService;
        this.medicalService = medicalService;
        this.medicalHelper = medicalHelper;
    }

    public Result indexGet(Integer patientId, String message) {

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        return ok(index.render(currentUserSession, message, patientId));
    }

    public Result searchPost() {
        //searchPost validates the search before redirecting to either indexGet
        //or editGet

        SearchViewModel searchViewModel = searchViewModelForm.bindFromRequest().get();
        int id = searchViewModel.getId();

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);
        if (patientServiceResponse.hasErrors()) {
            return redirect(routes.MedicalController.indexGet(0, "That patient can not be found."));
        }

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(id);
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        boolean hasPatientBeenCheckedIn = medicalService.hasPatientBeenCheckedIn(patientEncounter.getId());
        if (hasPatientBeenCheckedIn == true) {
            String message;
            ServiceResponse<DateTime> dateResponse = medicalService.getDateOfCheckIn(patientEncounter.getId());
            if (dateResponse.hasErrors()) {
                return redirect(routes.MedicalController.indexGet(0, "A fatal error has been encountered. Please try again."));
            }

            DateTime dateNow = dateUtils.getCurrentDateTime();
            DateTime dateTaken = dateResponse.getResponseObject();

            if (dateNow.dayOfYear().equals(dateTaken.dayOfYear())) {
                message = "That patient has already been seen today. Would you like to edit their encounter?";
            } else {
                message = "That patient's encounter has been closed.";
                id = 0;
            }
            return redirect(routes.MedicalController.indexGet(id, message));
        }
        return redirect(routes.MedicalController.editGet(searchViewModel.getId()));
    }

    public Result editGet(int patientId) {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        //current Patient info for view model
        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(patientId);
        if (patientServiceResponse.hasErrors()) {
            //this error should have been caught by searchPost
            return internalServerError();
        }
        IPatient patient = patientServiceResponse.getResponseObject();

        //current Encounter info for view model
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(patientId);
        if (patientEncounterServiceResponse.hasErrors()) {
            return internalServerError();
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();


        //get a list of prescriptions
        ServiceResponse<List<? extends IPatientPrescription>> patientPrescriptionsServiceResponse = searchService.findPrescriptionsByEncounterId(patientEncounter.getId());
        List<? extends IPatientPrescription> patientPrescriptions = new ArrayList<>();
        if (patientPrescriptionsServiceResponse.hasErrors()) {
            //do nothing, there might not always be available prescriptions
        } else {
            patientPrescriptions = patientPrescriptionsServiceResponse.getResponseObject();
        }

        //region **Mapping of treatment fields, HPI, PMH, and vitals**

        //Create linked hash map of treatment fields
        //get a list of available treatment fields
        ServiceResponse<List<? extends ITreatmentField>> treatmentFieldsServiceResponse = searchService.findAllTreatmentFields();
        List<? extends ITreatmentField> treatmentFields = treatmentFieldsServiceResponse.getResponseObject();

        //initalize map to store treatment fields: Map<treatmentFieldName, List of values>
        //the list of values is stored in desecending order by time taken
        Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap = new LinkedHashMap<>();
        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> patientTreatmentServiceResponse;
        String treatmentFieldName;
        //loop through each available treatment field and build the map
        for (int treatmentFieldIndex = 0; treatmentFieldIndex < treatmentFields.size(); treatmentFieldIndex++) {
            treatmentFieldName = treatmentFields.get(treatmentFieldIndex).getName().trim();
            patientTreatmentServiceResponse = searchService.findTreatmentFields(patientEncounter.getId(), treatmentFieldName);
            if (patientTreatmentServiceResponse.hasErrors()) {
                continue;
            } else {
                patientEncounterTreatmentMap.put(treatmentFieldName, patientTreatmentServiceResponse.getResponseObject());
            }
        }

        //Create linked hash map of history of present illness fields
        ServiceResponse<List<? extends IHpiField>> hpiFieldServiceResponse = searchService.findAllHpiFields();
        List<? extends IHpiField> hpiFields = hpiFieldServiceResponse.getResponseObject();

        Map<String, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap = new LinkedHashMap<>();
        ServiceResponse<List<? extends IPatientEncounterHpiField>> patientHpiServiceResponse;
        String hpiFieldName;
        for (int hpiFieldIndex = 0; hpiFieldIndex < hpiFields.size(); hpiFieldIndex++) {
            hpiFieldName = hpiFields.get(hpiFieldIndex).getName().trim();
            patientHpiServiceResponse = searchService.findHpiFields(patientEncounter.getId(), hpiFieldName);
            if (patientHpiServiceResponse.hasErrors()) {
                continue;
            } else {
                patientEncounterHpiMap.put(hpiFieldName, patientHpiServiceResponse.getResponseObject());
            }
        }

        //Create linked hash map of past medical history fields
        ServiceResponse<List<? extends IPmhField>> pmhFieldServiceResponse = searchService.findAllPmhFields();
        List<? extends IPmhField> pmhFields = pmhFieldServiceResponse.getResponseObject();

        Map<String, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap = new LinkedHashMap<>();
        ServiceResponse<List<? extends IPatientEncounterPmhField>> patientPmhServiceResponse;
        String pmhFieldName;
        for (int pmhFieldIndex = 0; pmhFieldIndex < pmhFields.size(); pmhFieldIndex++) {
            pmhFieldName = pmhFields.get(pmhFieldIndex).getName().trim();
            patientPmhServiceResponse = searchService.findPmhFields(patientEncounter.getId(), pmhFieldName);
            if (patientPmhServiceResponse.hasErrors()) {
                continue;
            } else {
                patientEncounterPmhMap.put(pmhFieldName, patientPmhServiceResponse.getResponseObject());
            }
        }

        //Create linked hash map of vitals
        ServiceResponse<List<? extends IVital>> vitalServiceResponse = searchService.findAllVitals();
        List<? extends IVital> vitals = vitalServiceResponse.getResponseObject();

        Map<String, List<? extends IPatientEncounterVital>> patientEncounterVitalMap = new LinkedHashMap<>();
        ServiceResponse<List<? extends IPatientEncounterVital>> patientVitalServiceResponse;
        String vitalFieldName;
        for (int vitalFieldIndex = 0; vitalFieldIndex < vitals.size(); vitalFieldIndex++) {
            vitalFieldName = vitals.get(vitalFieldIndex).getName().trim();
            patientVitalServiceResponse = searchService.findPatientEncounterVitals(patientEncounter.getId(), vitalFieldName);
            if (patientVitalServiceResponse.hasErrors()) {
                continue;
            } else {
                patientEncounterVitalMap.put(vitalFieldName, patientVitalServiceResponse.getResponseObject());
            }
        }

        //endregion

        //set up viewModelGet with everything except vitals
        CreateViewModelGet viewModelGet = medicalHelper.populateViewModelGet(patient, patientEncounter, patientPrescriptions, patientEncounterVitalMap, patientEncounterTreatmentMap, patientEncounterHpiMap, patientEncounterPmhMap);

        return ok(edit.render(currentUserSession, viewModelGet));
    }

    public Result editPost(int patientId) {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        CreateViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(patientId);
        if (patientEncounterServiceResponse.hasErrors()) {
            return internalServerError();
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        //region **Save stuff**
        //HPI tab
        List<? extends IPatientEncounterHpiField> patientEncounterHpiFields = getPatientEncounterHpiFields(viewModelPost,patientEncounter.getId(),currentUserSession.getId());
        ServiceResponse<List<? extends IPatientEncounterHpiField>> patientEncounterHpiFieldsServiceResponse = medicalService.createPatientEncounterHpiFields(patientEncounterHpiFields);
        if (patientEncounterHpiFieldsServiceResponse.hasErrors()){
            return internalServerError();
        }

        //PMH tab
        List<? extends IPatientEncounterPmhField> patientEncounterPmhFields = getPatientEncounterPmhFields(viewModelPost,patientEncounter.getId(), currentUserSession.getId());
        ServiceResponse<List<? extends IPatientEncounterPmhField>> patientEncounterPmhFieldsServiceResponse = medicalService.createPatientEncounterPmhFields(patientEncounterPmhFields);
        if (patientEncounterPmhFieldsServiceResponse.hasErrors()){
            return internalServerError();
        }

        //treatment tab
        List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields = getPatientEncounterTreatmentFields(viewModelPost, patientEncounter.getId(), currentUserSession.getId());
        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentFieldsServiceResponse = medicalService.createPatientEncounterTreatmentFields(patientEncounterTreatmentFields);
        if (patientEncounterTreatmentFieldsServiceResponse.hasErrors()){
            return internalServerError();
        }

        //prescriptions
        List<? extends IPatientPrescription> patientPrescriptions = getPatientPrescriptions(viewModelPost, patientEncounter.getId(), currentUserSession.getId());
        ServiceResponse<List<? extends IPatientPrescription>> patientPrescriptionsServiceResponse = medicalService.createPatientPrescriptions(patientPrescriptions);
        if (patientPrescriptionsServiceResponse.hasErrors()){
            return internalServerError();
        }
        //endregion

        return redirect(routes.MedicalController.indexGet(0, null));
    }

    public Result updateVitalsPost(int id) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<IPatientEncounter> currentEncounterByPatientId = searchService.findCurrentEncounterByPatientId(id);
        if (currentEncounterByPatientId.hasErrors()) {
            return internalServerError();
        }

        IPatientEncounter patientEncounter = currentEncounterByPatientId.getResponseObject();

        UpdateVitalsModel updateVitalsModel = updateVitalsModelForm.bindFromRequest().get();

        List<? extends IPatientEncounterVital> patientEncounterVitals = getPatientEncounterVitals(updateVitalsModel, patientEncounter.getId(), currentUser.getId());
        ServiceResponse<List<? extends IPatientEncounterVital>> patientEncounterVitalServiceResponse = triageService.createPatientEncounterVitals(patientEncounterVitals);
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            return internalServerError();
        }

        return ok("true");
    }

    //region **generate lists of stuff from CreateViewModelPost**
    private List<? extends IPatientPrescription> getPatientPrescriptions(CreateViewModelPost viewModelPost, int patientEncounterId, int userId){
        IPatientPrescription patientPrescription;
        List<IPatientPrescription> patientPrescriptions = new ArrayList<>();
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription1())) {
            patientPrescription = medicalHelper.getPatientPrescription(userId,patientEncounterId,viewModelPost.getPrescription1());
            patientPrescriptions.add(patientPrescription);
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription2())) {
            patientPrescription = medicalHelper.getPatientPrescription(userId,patientEncounterId,viewModelPost.getPrescription2());
            patientPrescriptions.add(patientPrescription);
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription3())) {
            patientPrescription = medicalHelper.getPatientPrescription(userId,patientEncounterId,viewModelPost.getPrescription3());
            patientPrescriptions.add(patientPrescription);
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription4())) {
            patientPrescription = medicalHelper.getPatientPrescription(userId,patientEncounterId,viewModelPost.getPrescription4());
            patientPrescriptions.add(patientPrescription);
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription5())) {
            patientPrescription = medicalHelper.getPatientPrescription(userId,patientEncounterId,viewModelPost.getPrescription5());
            patientPrescriptions.add(patientPrescription);
        }
        return patientPrescriptions;
    }
    private List<? extends IPatientEncounterTreatmentField> getPatientEncounterTreatmentFields(CreateViewModelPost viewModelPost, int patientEncounterId, int userId){
        ServiceResponse<ITreatmentField> treatmentFieldServiceResponse;
        IPatientEncounterTreatmentField patientEncounterTreatmentField;
        List<IPatientEncounterTreatmentField> patientEncounterTreatmentFields = new ArrayList<>();

        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAssessment())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("assessment");
            if (!treatmentFieldServiceResponse.hasErrors()){
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId, patientEncounterId, treatmentFieldServiceResponse.getResponseObject(), viewModelPost.getAssessment());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getTreatment())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("treatment");
            if (!treatmentFieldServiceResponse.hasErrors()){
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId,patientEncounterId,treatmentFieldServiceResponse.getResponseObject(),viewModelPost.getTreatment());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem1())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("problem");
            if (!treatmentFieldServiceResponse.hasErrors()){
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId,patientEncounterId,treatmentFieldServiceResponse.getResponseObject(),viewModelPost.getProblem1());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem2())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("problem");
            if (!treatmentFieldServiceResponse.hasErrors()){
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId,patientEncounterId,treatmentFieldServiceResponse.getResponseObject(),viewModelPost.getProblem2());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem3())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("problem");
            if (!treatmentFieldServiceResponse.hasErrors()){
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId,patientEncounterId,treatmentFieldServiceResponse.getResponseObject(),viewModelPost.getProblem3());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem4())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("problem");
            if (!treatmentFieldServiceResponse.hasErrors()){
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId,patientEncounterId,treatmentFieldServiceResponse.getResponseObject(),viewModelPost.getProblem4());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem5())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("problem");
            if (!treatmentFieldServiceResponse.hasErrors()){
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId,patientEncounterId,treatmentFieldServiceResponse.getResponseObject(),viewModelPost.getProblem5());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        return patientEncounterTreatmentFields;
    }
    private List<? extends IPatientEncounterPmhField> getPatientEncounterPmhFields(CreateViewModelPost viewModelPost, int patientEncounterId, int userId){
        ServiceResponse<IPmhField> pmhFieldServiceResponse;
        IPatientEncounterPmhField patientEncounterPmhField;
        List<IPatientEncounterPmhField> patientEncounterPmhFields = new ArrayList<>();

        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getMedicalSurgicalHistory())) {
            pmhFieldServiceResponse = searchService.findPmhField("medicalSurgicalHistory");
            if (!pmhFieldServiceResponse.hasErrors()){
                patientEncounterPmhField = medicalHelper.getPatientEncounterPmhField(userId,patientEncounterId,pmhFieldServiceResponse.getResponseObject(),viewModelPost.getMedicalSurgicalHistory());
                patientEncounterPmhFields.add(patientEncounterPmhField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getSocialHistory())) {
            pmhFieldServiceResponse = searchService.findPmhField("socialHistory");
            if (!pmhFieldServiceResponse.hasErrors()){
                patientEncounterPmhField = medicalHelper.getPatientEncounterPmhField(userId,patientEncounterId,pmhFieldServiceResponse.getResponseObject(),viewModelPost.getSocialHistory());
                patientEncounterPmhFields.add(patientEncounterPmhField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getCurrentMedication())) {
            pmhFieldServiceResponse = searchService.findPmhField("currentMedication");
            if (!pmhFieldServiceResponse.hasErrors()){
                patientEncounterPmhField = medicalHelper.getPatientEncounterPmhField(userId,patientEncounterId,pmhFieldServiceResponse.getResponseObject(),viewModelPost.getCurrentMedication());
                patientEncounterPmhFields.add(patientEncounterPmhField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getFamilyHistory())) {
            pmhFieldServiceResponse = searchService.findPmhField("familyHistory");
            if (!pmhFieldServiceResponse.hasErrors()){
                patientEncounterPmhField = medicalHelper.getPatientEncounterPmhField(userId,patientEncounterId,pmhFieldServiceResponse.getResponseObject(),viewModelPost.getFamilyHistory());
                patientEncounterPmhFields.add(patientEncounterPmhField);
            }
        }
        return patientEncounterPmhFields;
    }
    private List<? extends IPatientEncounterHpiField> getPatientEncounterHpiFields(CreateViewModelPost viewModelPost, int patientEncounterId, int userId) {
        ServiceResponse<IHpiField> hpiFieldServiceResponse;
        IPatientEncounterHpiField patientEncounterHpiField;
        List<IPatientEncounterHpiField> patientEncounterHpiFields = new ArrayList();

        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getOnset())) {
            hpiFieldServiceResponse = searchService.findHpiField("onset");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getOnset());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getSeverity())) {
            hpiFieldServiceResponse = searchService.findHpiField("severity");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getSeverity());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getRadiation())) {
            hpiFieldServiceResponse = searchService.findHpiField("radiation");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getRadiation());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getQuality())) {
            hpiFieldServiceResponse = searchService.findHpiField("quality");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getQuality());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProvokes())) {
            hpiFieldServiceResponse = searchService.findHpiField("provokes");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getProvokes());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPalliates())) {
            hpiFieldServiceResponse = searchService.findHpiField("palliates");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getPalliates());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getTimeOfDay())) {
            hpiFieldServiceResponse = searchService.findHpiField("timeOfDay");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getTimeOfDay());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPhysicalExamination())) {
            hpiFieldServiceResponse = searchService.findHpiField("physicalExamination");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getPhysicalExamination());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getNarrative())) {
            hpiFieldServiceResponse = searchService.findHpiField("narrative");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getNarrative());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        return patientEncounterHpiFields;
    }
    private List<? extends IPatientEncounterVital> getPatientEncounterVitals(UpdateVitalsModel updateVitalsModel, int patientEncounterId, int userId) {
        ServiceResponse<IVital> vitalServiceResponse;
        IPatientEncounterVital patientEncounterVital;
        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();

        if (updateVitalsModel.getRespRate() > 0) {
            vitalServiceResponse = searchService.findVital("respiratoryRate");
            if (!vitalServiceResponse.hasErrors()) {
                patientEncounterVital = medicalHelper.getPatientEncounterVital(userId, patientEncounterId, vitalServiceResponse.getResponseObject(), updateVitalsModel.getRespRate());
                patientEncounterVitals.add(patientEncounterVital);
            }
        }
        if (updateVitalsModel.getHeartRate() > 0) {
            vitalServiceResponse = searchService.findVital("heartRate");
            if (!vitalServiceResponse.hasErrors()) {
                patientEncounterVital = medicalHelper.getPatientEncounterVital(userId, patientEncounterId, vitalServiceResponse.getResponseObject(), updateVitalsModel.getHeartRate());
                patientEncounterVitals.add(patientEncounterVital);
            }
        }
        if (updateVitalsModel.getTemperature() > 0) {
            vitalServiceResponse = searchService.findVital("temperature");
            if (!vitalServiceResponse.hasErrors()) {
                patientEncounterVital = medicalHelper.getPatientEncounterVital(userId, patientEncounterId, vitalServiceResponse.getResponseObject(), updateVitalsModel.getTemperature());
                patientEncounterVitals.add(patientEncounterVital);
            }
        }
        if (updateVitalsModel.getOxygen() > 0) {
            vitalServiceResponse = searchService.findVital("oxygenSaturation");
            if (!vitalServiceResponse.hasErrors()) {
                patientEncounterVital = medicalHelper.getPatientEncounterVital(userId, patientEncounterId, vitalServiceResponse.getResponseObject(), updateVitalsModel.getOxygen());
                patientEncounterVitals.add(patientEncounterVital);
            }
        }
        if (updateVitalsModel.getHeightIn() > 0) {
            vitalServiceResponse = searchService.findVital("heightInches");
            if (!vitalServiceResponse.hasErrors()) {
                patientEncounterVital = medicalHelper.getPatientEncounterVital(userId, patientEncounterId, vitalServiceResponse.getResponseObject(), updateVitalsModel.getHeightIn());
                patientEncounterVitals.add(patientEncounterVital);
            }
        }
        if (updateVitalsModel.getHeightFt() > 0) {
            vitalServiceResponse = searchService.findVital("heightFeet");
            if (!vitalServiceResponse.hasErrors()) {
                patientEncounterVital = medicalHelper.getPatientEncounterVital(userId, patientEncounterId, vitalServiceResponse.getResponseObject(), updateVitalsModel.getHeightFt());
                patientEncounterVitals.add(patientEncounterVital);
            }
        }
        if (updateVitalsModel.getWeight() > 0) {
            vitalServiceResponse = searchService.findVital("weight");
            if (!vitalServiceResponse.hasErrors()) {
                patientEncounterVital = medicalHelper.getPatientEncounterVital(userId, patientEncounterId, vitalServiceResponse.getResponseObject(), updateVitalsModel.getWeight());
                patientEncounterVitals.add(patientEncounterVital);
            }
        }
        if (updateVitalsModel.getBpSystolic() > 0) {
            vitalServiceResponse = searchService.findVital("bloodPressureSystolic");
            if (!vitalServiceResponse.hasErrors()) {
                patientEncounterVital = medicalHelper.getPatientEncounterVital(userId, patientEncounterId, vitalServiceResponse.getResponseObject(), updateVitalsModel.getBpSystolic());
                patientEncounterVitals.add(patientEncounterVital);
            }
        }
        if (updateVitalsModel.getBpDiastolic() > 0) {
            vitalServiceResponse = searchService.findVital("bloodPressureDiastolic");
            if (!vitalServiceResponse.hasErrors()) {
                patientEncounterVital = medicalHelper.getPatientEncounterVital(userId, patientEncounterId, vitalServiceResponse.getResponseObject(), updateVitalsModel.getBpDiastolic());
                patientEncounterVitals.add(patientEncounterVital);
            }
        }
        if (updateVitalsModel.getGlucose() > 0) {
            vitalServiceResponse = searchService.findVital("glucose");
            if (!vitalServiceResponse.hasErrors()) {
                patientEncounterVital = medicalHelper.getPatientEncounterVital(userId, patientEncounterId, vitalServiceResponse.getResponseObject(), updateVitalsModel.getGlucose());
                patientEncounterVitals.add(patientEncounterVital);
            }
        }
        return patientEncounterVitals;
    }
    //endregion
}
