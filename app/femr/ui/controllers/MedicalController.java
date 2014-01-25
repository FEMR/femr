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

        int success;

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        CreateViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(patientId);
        if (patientEncounterServiceResponse.hasErrors()) {
            return internalServerError();
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        //region **save HPI Data from POST**
        success = 1;
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getOnset())) {
            success = savePatientEncounterHpiField("onset", viewModelPost.getOnset(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getSeverity())) {
            success = savePatientEncounterHpiField("severity", viewModelPost.getSeverity(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getRadiation())) {
            success = savePatientEncounterHpiField("radiation", viewModelPost.getRadiation(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getQuality())) {
            success = savePatientEncounterHpiField("quality", viewModelPost.getQuality(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProvokes())) {
            success = savePatientEncounterHpiField("provokes", viewModelPost.getProvokes(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPalliates())) {
            success = savePatientEncounterHpiField("palliates", viewModelPost.getPalliates(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getTimeOfDay())) {
            success = savePatientEncounterHpiField("timeOfDay", viewModelPost.getTimeOfDay(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPhysicalExamination())) {
            success = savePatientEncounterHpiField("physicalExamination", viewModelPost.getPhysicalExamination(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getNarrative())) {
            success = savePatientEncounterHpiField("narrative", viewModelPost.getNarrative(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (success == 0) {
            return internalServerError();
        }
        //endregion

        //region **save PMH Data from POST**
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
        //endregion

        //region **save Treatment Data from POST**
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
        //endregion

        //region **save Prescription Data from POST**
        success = 1;
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription1())) {
            success = savePatientEncounterPrescription(viewModelPost.getPrescription1(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription2())) {
            success = savePatientEncounterPrescription(viewModelPost.getPrescription2(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription3())) {
            success = savePatientEncounterPrescription(viewModelPost.getPrescription3(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription4())) {
            success = savePatientEncounterPrescription(viewModelPost.getPrescription4(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription5())) {
            success = savePatientEncounterPrescription(viewModelPost.getPrescription5(), currentUserSession.getId(), patientEncounter.getId());
        }
        if (success == 0) {
            return internalServerError();
        }
        //endregion

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

    //region **save stuff**
    //returns 1 on success, 0 on failure
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
    private int savePatientEncounterPrescription(String name, int userId, int patientEncounterId) {
        IPatientPrescription patientPrescription = medicalHelper.getPatientPrescription(userId, patientEncounterId, name);
        ServiceResponse<IPatientPrescription> patientPrescriptionServiceResponse = medicalService.createPatientPrescription(patientPrescription);
        if (patientPrescriptionServiceResponse.hasErrors()) {
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
    private int savePatientEncounterHpiField(String name, String hpiFieldValue, int userId, int patientEncounterId) {
        ServiceResponse<IHpiField> hpiFieldServiceResponse = searchService.findHpiField(name);
        if (hpiFieldServiceResponse.hasErrors()) {
            return 0;
        }
        IHpiField hpiField = hpiFieldServiceResponse.getResponseObject();

        IPatientEncounterHpiField patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiField, hpiFieldValue);
        ServiceResponse<IPatientEncounterHpiField> patientEncounterHpiFieldServiceResponse = medicalService.createPatientEncounterHpiField(patientEncounterHpiField);
        if (patientEncounterHpiFieldServiceResponse.hasErrors()) {
            return 0;
        }
        return 1;
    }
    //endregion
}
