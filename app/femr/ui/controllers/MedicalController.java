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

        //current vitals for view model
        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();
        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse;
        int TOTAL_VITALS = 10;
        for (int vital = 1; vital <= TOTAL_VITALS; vital++) {
            patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(vital, patientEncounter.getId());
            if (patientEncounterVitalServiceResponse.hasErrors()) {
                patientEncounterVitals.add(null);
            } else {
                patientEncounterVitals.add(patientEncounterVitalServiceResponse.getResponseObject());
            }
        }

        //viewModelPost is populated with editable fields
        ServiceResponse<List<? extends IPatientPrescription>> patientPrescriptionsServiceResponse = searchService.findPrescriptionsByEncounterId(patientEncounter.getId());
        List<? extends IPatientPrescription> patientPrescriptions = new ArrayList<>();
        if (patientPrescriptionsServiceResponse.hasErrors()) {
            //do nothing, there might not always be available prescriptions
        } else {
            patientPrescriptions = patientPrescriptionsServiceResponse.getResponseObject();
        }

        ServiceResponse<Map<Integer, List<? extends IPatientEncounterTreatmentField>>> patientTreatmentFieldsServiceResponse = searchService.findTreatmentFieldsByEncounterId(patientEncounter.getId());
        Map<Integer, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap = new LinkedHashMap<>();
        if (patientTreatmentFieldsServiceResponse.hasErrors()) {
            //do nothing, there might not always be available treatments
        } else {
            patientEncounterTreatmentMap = patientTreatmentFieldsServiceResponse.getResponseObject();
        }

        ServiceResponse<Map<Integer, List<? extends IPatientEncounterHpiField>>> patientHpiFieldsServiceResponse = searchService.findHpiFieldsByEncounterId(patientEncounter.getId());
        Map<Integer, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap = new LinkedHashMap<>();
        if (patientHpiFieldsServiceResponse.hasErrors()) {
            //do nothing, there might not always be available hpi fields
        } else {
            patientEncounterHpiMap = patientHpiFieldsServiceResponse.getResponseObject();
        }

        ServiceResponse<Map<Integer, List<? extends IPatientEncounterPmhField>>> patientPmhFieldsServiceResponse = searchService.findPmhFieldsByEncounterId(patientEncounter.getId());
        Map<Integer, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap = new LinkedHashMap<>();
        if (patientPmhFieldsServiceResponse.hasErrors()) {
            //do nothing, there might not always be available pmh fields
        } else {
            patientEncounterPmhMap = patientPmhFieldsServiceResponse.getResponseObject();
        }
        CreateViewModelPost viewModelPost = medicalHelper.populateViewModelPost(patientPrescriptions, patientEncounterTreatmentMap, patientEncounterHpiMap, patientEncounterPmhMap);
        CreateViewModelGet viewModelGet = medicalHelper.populateViewModelGet(patient, patientEncounter, patientEncounterVitals, viewModelPost);
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

        //HPI Data
        List<IPatientEncounterHpiField> patientEncounterHpiFields = medicalHelper.populateHpiFields(viewModelPost, patientEncounter, currentUserSession);
        ServiceResponse<IPatientEncounterHpiField> patientEncounterHpiFieldServiceResponse;
        for (int j = 0; j < patientEncounterHpiFields.size(); j++) {
            if (StringUtils.isNullOrWhiteSpace(patientEncounterHpiFields.get(j).getHpiFieldValue())) {
                continue;
            } else {
                patientEncounterHpiFieldServiceResponse = medicalService.createPatientEncounterHpiField(patientEncounterHpiFields.get(j));
                if (patientEncounterHpiFieldServiceResponse.hasErrors()) {
                    return internalServerError();
                }
            }
        }

        //PMH Data
        List<IPatientEncounterPmhField> patientEncounterPmhFields = medicalHelper.populatePmhFields(viewModelPost, patientEncounter, currentUserSession);
        ServiceResponse<IPatientEncounterPmhField> patientEncounterPmhFieldServiceResponse;
        for (int m = 0; m < patientEncounterPmhFields.size(); m++) {
            if (StringUtils.isNullOrWhiteSpace(patientEncounterPmhFields.get(m).getPmhFieldValue())) {
                continue;
            } else {
                patientEncounterPmhFieldServiceResponse = medicalService.createPatientEncounterPmhField(patientEncounterPmhFields.get(m));
                if (patientEncounterPmhFieldServiceResponse.hasErrors()) {
                    return internalServerError();
                }
            }
        }
        //Treatment Data
        List<IPatientEncounterTreatmentField> patientEncounterTreatmentFields = medicalHelper.populateTreatmentFields(viewModelPost, patientEncounter, currentUserSession);
        ServiceResponse<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldServiceResponse;
        for (int i = 0; i < patientEncounterTreatmentFields.size(); i++) {
            if (StringUtils.isNullOrWhiteSpace(patientEncounterTreatmentFields.get(i).getTreatmentFieldValue())) {
                continue;
            } else {
                patientEncounterTreatmentFieldServiceResponse = medicalService.createPatientEncounterTreatmentField(patientEncounterTreatmentFields.get(i));
                if (patientEncounterTreatmentFieldServiceResponse.hasErrors()) {
                    return internalServerError();
                }
            }
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


        if (updateVitalsModel.getRespRate() > 0) {
            createPatientEncounterVital("respiratoryRate", updateVitalsModel.getRespRate(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getHeartRate() > 0) {
            createPatientEncounterVital("heartRate", updateVitalsModel.getHeartRate(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getTemperature() > 0) {
            createPatientEncounterVital("temperature", updateVitalsModel.getTemperature(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getOxygen() > 0) {
            createPatientEncounterVital("oxygenSaturation", updateVitalsModel.getOxygen(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getHeightFt() > 0) {
            createPatientEncounterVital("heightFeet", updateVitalsModel.getHeightFt(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getHeightIn() > 0) {
            createPatientEncounterVital("heightInches", updateVitalsModel.getHeightIn(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getWeight() > 0) {
            createPatientEncounterVital("weight", updateVitalsModel.getWeight(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getBpSystolic() > 0) {
            createPatientEncounterVital("bloodPressureSystolic", updateVitalsModel.getBpSystolic(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getBpDiastolic() > 0) {
            createPatientEncounterVital("bloodPressureDiastolic", updateVitalsModel.getBpDiastolic(), currentUser.getId(), patientEncounter.getId());
        }
        if (updateVitalsModel.getGlucose() > 0) {
            createPatientEncounterVital("glucose", updateVitalsModel.getGlucose(), currentUser.getId(), patientEncounter.getId());
        }

        return ok("true");
    }

    private void createPatientEncounterVital(String name, double vitalValue, int userId, int patientEncounterId) {
        ServiceResponse<IVital> vitalServiceResponse = searchService.findVital(name);
        if (vitalServiceResponse.hasErrors()) {
            //error
        }
        IVital vital = vitalServiceResponse.getResponseObject();
        IPatientEncounterVital patientEncounterVital = medicalHelper.createPatientEncounterVital(userId, patientEncounterId, vital, vitalValue);
        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse = triageService.createPatientEncounterVital(patientEncounterVital);
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            //error
        }
    }
}
