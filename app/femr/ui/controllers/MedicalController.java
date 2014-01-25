package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IMedicalService;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.*;
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
import org.jboss.netty.util.internal.StringUtil;
import org.joda.time.DateTime;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.*;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class MedicalController extends Controller {
    private static final String[] TREATMENT_FIELDS = new String[]{
            "assessment",
            "problem",
            "treatment",
    };
    private static final String[] PMH_FIELDS = new String[]{
            "medicalSurgicalHistory",
            "socialHistory",
            "currentMedication",
            "familyHistory"
    };
    private static final String[] HPI_FIELDS = new String[]{
            "onset",
            "severity",
            "radiation",
            "quality",
            "provokes",
            "palliates",
            "timeOfDay",
            "physicalExamination",
            "narrative"
    };
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


        //viewModelPost is populated with editable fields
        ServiceResponse<List<? extends IPatientPrescription>> patientPrescriptionsServiceResponse = searchService.findPrescriptionsByEncounterId(patientEncounter.getId());
        List<? extends IPatientPrescription> patientPrescriptions = new ArrayList<>();
        if (patientPrescriptionsServiceResponse.hasErrors()) {
            //do nothing, there might not always be available prescriptions
        } else {
            patientPrescriptions = patientPrescriptionsServiceResponse.getResponseObject();
        }

        //Create linked hash map of history of present illness fields
        Map<String, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap = new LinkedHashMap<>();
        ServiceResponse<List<? extends IPatientEncounterHpiField>> patientHpiServiceResponse;
        for (int hpiFieldIndex = 0; hpiFieldIndex < HPI_FIELDS.length; hpiFieldIndex++){
            patientHpiServiceResponse = searchService.findHpiFields(patientEncounter.getId(), HPI_FIELDS[hpiFieldIndex]);
            if (patientHpiServiceResponse.hasErrors()){
                continue;
            }            else{
                patientEncounterHpiMap.put(HPI_FIELDS[hpiFieldIndex], patientHpiServiceResponse.getResponseObject());
            }
        }

        //Create linked hash map of past medical history fields
        Map<String, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap = new LinkedHashMap<>();
        ServiceResponse<List<? extends IPatientEncounterPmhField>> patientPmhServiceResponse;
        for (int pmhFieldIndex = 0; pmhFieldIndex < PMH_FIELDS.length; pmhFieldIndex++) {
            patientPmhServiceResponse = searchService.findPmhFields(patientEncounter.getId(), PMH_FIELDS[pmhFieldIndex]);
            if (patientPmhServiceResponse.hasErrors()) {
                continue;
            } else {
                patientEncounterPmhMap.put(PMH_FIELDS[pmhFieldIndex], patientPmhServiceResponse.getResponseObject());
            }
        }

        //Create linked hash map of treatment fields
        Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap = new LinkedHashMap<>();
        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> patientTreatmentServiceResponse;
        for (int treatmentFieldIndex = 0; treatmentFieldIndex < TREATMENT_FIELDS.length; treatmentFieldIndex++) {
            patientTreatmentServiceResponse = searchService.findTreatmentFields(patientEncounter.getId(), TREATMENT_FIELDS[treatmentFieldIndex]);
            if (patientTreatmentServiceResponse.hasErrors()) {
                continue;
            } else {
                patientEncounterTreatmentMap.put(TREATMENT_FIELDS[treatmentFieldIndex], patientTreatmentServiceResponse.getResponseObject());
            }
        }

        //use CreateViewModelPost as a model for viewModelGet
        CreateViewModelPost viewModelPost = medicalHelper.populateViewModelPost(patientPrescriptions, patientEncounterTreatmentMap, patientEncounterHpiMap, patientEncounterPmhMap);

        //set up viewModelGet with everything except vitals
        CreateViewModelGet viewModelGet = medicalHelper.populateViewModelGet(patient, patientEncounter, viewModelPost);

        //add vitals to viewModelGet
        Float vital = getPatientEncounterVitalOrNull("respiratoryRate", patientEncounter.getId());
        if (vital != null) {
            viewModelGet.setRespiratoryRate(vital.intValue());
        }
        vital = getPatientEncounterVitalOrNull("heartRate", patientEncounter.getId());
        if (vital != null) {
            viewModelGet.setHeartRate(vital.intValue());
        }
        vital = getPatientEncounterVitalOrNull("heightFeet", patientEncounter.getId());
        if (vital != null) {
            viewModelGet.setHeightFeet(vital.intValue());
        }
        vital = getPatientEncounterVitalOrNull("heightInches", patientEncounter.getId());
        if (vital != null) {
            viewModelGet.setHeightInches(vital.intValue());
        }
        vital = getPatientEncounterVitalOrNull("bloodPressureSystolic", patientEncounter.getId());
        if (vital != null) {
            viewModelGet.setBloodPressureSystolic(vital.intValue());
        }
        vital = getPatientEncounterVitalOrNull("bloodPressureDiastolic", patientEncounter.getId());
        if (vital != null) {
            viewModelGet.setBloodPressureDiastolic(vital.intValue());
        }
        viewModelGet.setTemperature(getPatientEncounterVitalOrNull("temperature", patientEncounter.getId()));
        viewModelGet.setOxygenSaturation(getPatientEncounterVitalOrNull("oxygenSaturation", patientEncounter.getId()));
        viewModelGet.setWeight(getPatientEncounterVitalOrNull("weight", patientEncounter.getId()));
        viewModelGet.setGlucose(getPatientEncounterVitalOrNull("glucose", patientEncounter.getId()));

        return ok(edit.render(currentUserSession, viewModelGet));
    }

    public Result editPost(int patientId) {

        int success;

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        CreateViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(patientId);
        if (patientEncounterServiceResponse.hasErrors()) {
            return internalServerError();
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();


        //save HPI Data from POST
        success = 1;
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getOnset())){
            success = savePatientEncounterHpiField("onset", viewModelPost.getOnset(), currentUserSession.getId(),patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getSeverity())){
            success = savePatientEncounterHpiField("severity", viewModelPost.getSeverity(), currentUserSession.getId(),patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getRadiation())){
            success = savePatientEncounterHpiField("radiation", viewModelPost.getRadiation(), currentUserSession.getId(),patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getQuality())){
            success = savePatientEncounterHpiField("quality", viewModelPost.getQuality(), currentUserSession.getId(),patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProvokes())){
            success = savePatientEncounterHpiField("provokes", viewModelPost.getProvokes(), currentUserSession.getId(),patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPalliates())){
            success = savePatientEncounterHpiField("palliates", viewModelPost.getPalliates(), currentUserSession.getId(),patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getTimeOfDay())){
            success = savePatientEncounterHpiField("timeOfDay", viewModelPost.getTimeOfDay(), currentUserSession.getId(),patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPhysicalExamination())){
            success = savePatientEncounterHpiField("physicalExamination", viewModelPost.getPhysicalExamination(), currentUserSession.getId(),patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getNarrative())){
            success = savePatientEncounterHpiField("narrative", viewModelPost.getNarrative(), currentUserSession.getId(),patientEncounter.getId());
        }
        if (success == 0) {
            return internalServerError();
        }

        //save PMH Data from POST
        success = 1;
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getMedicalSurgicalHistory())) {
            success = savePatientEncounterPmhField("medicalSurgicalHistory", viewModelPost.getMedicalSurgicalHistory(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getSocialHistory())) {
            success = savePatientEncounterPmhField("socialHistory", viewModelPost.getSocialHistory(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getCurrentMedication())) {
            success = savePatientEncounterPmhField("currentMedication", viewModelPost.getCurrentMedication(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getFamilyHistory())) {
            success = savePatientEncounterPmhField("familyHistory", viewModelPost.getFamilyHistory(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (success == 0) {
            return internalServerError();
        }

        //save Treatment Data from POST
        success = 1;
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAssessment())) {
            success = savePatientEncounterTreatmentField("assessment", viewModelPost.getAssessment(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getTreatment())) {
            success = savePatientEncounterTreatmentField("treatment", viewModelPost.getTreatment(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem1())) {
            success = savePatientEncounterTreatmentField("problem", viewModelPost.getProblem1(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem2())) {
            success = savePatientEncounterTreatmentField("problem", viewModelPost.getProblem2(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem3())) {
            success = savePatientEncounterTreatmentField("problem", viewModelPost.getProblem3(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem4())) {
            success = savePatientEncounterTreatmentField("problem", viewModelPost.getProblem4(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem5())) {
            success = savePatientEncounterTreatmentField("problem", viewModelPost.getProblem5(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (success == 0) {
            return internalServerError();
        }

        //prescriptions
        List<IPatientPrescription> patientPrescriptions = medicalHelper.populatePatientPrescriptions(viewModelPost, patientEncounter, currentUserSession);
        ServiceResponse<IPatientPrescription> patientPrescriptionServiceResponse;
        for (int k = 0; k < patientPrescriptions.size(); k++) {
            if (StringUtils.isNullOrWhiteSpace(patientPrescriptions.get(k).getMedicationName())) {
                continue;
            } else {
                patientPrescriptionServiceResponse = medicalService.createPatientPrescription(patientPrescriptions.get(k));
                if (patientPrescriptionServiceResponse.hasErrors()) {
                    return internalServerError();
                }
            }
        }
        return redirect(routes.MedicalController.indexGet(0, null));
    }

    public Result updateVitalsPost(int id) {

        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<IPatientEncounter> currentEncounterByPatientId = searchService.findCurrentEncounterByPatientId(id);
        IPatientEncounter patientEncounter = currentEncounterByPatientId.getResponseObject();

        UpdateVitalsModel updateVitalsModel = updateVitalsModelForm.bindFromRequest().get();

        int success = 1;
        if (updateVitalsModel.getRespRate() > 0) {
            success = savePatientEncounterVital("respiratoryRate", updateVitalsModel.getRespRate(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getHeartRate() > 0) {
            success = savePatientEncounterVital("heartRate", updateVitalsModel.getHeartRate(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getTemperature() > 0) {
            success = savePatientEncounterVital("temperature", updateVitalsModel.getTemperature(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getOxygen() > 0) {
            success = savePatientEncounterVital("oxygenSaturation", updateVitalsModel.getOxygen(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getHeightFt() > 0) {
            success = savePatientEncounterVital("heightFeet", updateVitalsModel.getHeightFt(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getHeightIn() > 0) {
            success = savePatientEncounterVital("heightInches", updateVitalsModel.getHeightIn(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getWeight() > 0) {
            success = savePatientEncounterVital("weight", updateVitalsModel.getWeight(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getBpSystolic() > 0) {
            success = savePatientEncounterVital("bloodPressureSystolic", updateVitalsModel.getBpSystolic(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getBpDiastolic() > 0) {
            success = savePatientEncounterVital("bloodPressureDiastolic", updateVitalsModel.getBpDiastolic(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getGlucose() > 0) {
            success = savePatientEncounterVital("glucose", updateVitalsModel.getGlucose(), currentUser.getId(), patientEncounter.getId());
        }
        if (success == 0) {
            return internalServerError();
        }

        return ok("true");
    }

    private int savePatientEncounterVital(String name, double vitalValue, int userId, int patientEncounterId) {
        ServiceResponse<IVital> vitalServiceResponse = searchService.findVital(name);
        if (vitalServiceResponse.hasErrors()) {
            return 0;
        }
        IVital vital = vitalServiceResponse.getResponseObject();
        IPatientEncounterVital patientEncounterVital = medicalHelper.getPatientEncounterVital(userId, patientEncounterId, vital, vitalValue);
        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse = triageService.createPatientEncounterVital(patientEncounterVital);
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            return 0;
        }
        return 1;
    }

    //returns 1 on success, 0 on failure
    private int savePatientEncounterTreatmentField(String name, String treatmentFieldValue, int userId, int patientEncounterId) {
        ServiceResponse<ITreatmentField> treatmentFieldServiceResponse = searchService.findTreatmentField(name);
        if (treatmentFieldServiceResponse.hasErrors()) {
            return 0;
        }
        ITreatmentField treatmentField = treatmentFieldServiceResponse.getResponseObject();

        IPatientEncounterTreatmentField patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId, patientEncounterId, treatmentField, treatmentFieldValue);
        ServiceResponse<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldServiceResponse = medicalService.createPatientEncounterTreatmentField(patientEncounterTreatmentField);
        if (patientEncounterTreatmentFieldServiceResponse.hasErrors()) {
            return 0;
        }
        return 1;
    }

    //returns 1 on success, 0 on failure
    private int savePatientEncounterPmhField(String name, String pmhFieldValue, int userId, int patientEncounterId) {
        ServiceResponse<IPmhField> pmhFieldServiceResponse = searchService.findPmhField(name);
        if (pmhFieldServiceResponse.hasErrors()) {
            return 0;
        }
        IPmhField pmhField = pmhFieldServiceResponse.getResponseObject();

        IPatientEncounterPmhField patientEncounterPmhField = medicalHelper.getPatientEncounterPmhField(userId, patientEncounterId, pmhField, pmhFieldValue);
        ServiceResponse<IPatientEncounterPmhField> patientEncounterPmhFieldServiceResponse = medicalService.createPatientEncounterPmhField(patientEncounterPmhField);
        if (patientEncounterPmhFieldServiceResponse.hasErrors()) {
            return 0;
        }
        return 1;
    }

    //returns 1 on success, 0 on failure
    private int savePatientEncounterHpiField(String name, String hpiFieldValue, int userId, int patientEncounterId){
        ServiceResponse<IHpiField> hpiFieldServiceResponse = searchService.findHpiField(name);
        if (hpiFieldServiceResponse.hasErrors()){
            return 0;
        }
        IHpiField hpiField = hpiFieldServiceResponse.getResponseObject();

        IPatientEncounterHpiField patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiField, hpiFieldValue);
        ServiceResponse<IPatientEncounterHpiField> patientEncounterHpiFieldServiceResponse = medicalService.createPatientEncounterHpiField(patientEncounterHpiField);
        if (patientEncounterHpiFieldServiceResponse.hasErrors()){
            return 0;
        }
        return 1;
    }

    private Float getPatientEncounterVitalOrNull(String name, int encounterId) {
        ServiceResponse<List<? extends IPatientEncounterVital>> patientEncounterVitalServiceResponse;
        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitals(encounterId, name);
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            return null;
        } else {
            return patientEncounterVitalServiceResponse.getResponseObject().get(0).getVitalValue();
        }
    }
}
