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
import femr.ui.models.medical.*;
import femr.ui.views.html.medical.*;
import femr.util.DataStructure.VitalMultiMap;
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

    public Result indexGet(Integer patientId) {

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        return ok(index.render(currentUserSession, null, patientId));
    }

    public Result searchPost() {
        //searchPost validates the search before redirecting to either indexGet
        //or editGet
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        SearchViewModel searchViewModel = searchViewModelForm.bindFromRequest().get();
        int id = searchViewModel.getId();
        String message;

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);
        if (patientServiceResponse.hasErrors()) {
            message = "That patient can not be found.";
            return ok(index.render(currentUserSession, message, 0));
        }

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(id);
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        boolean hasPatientBeenCheckedIn = medicalService.hasPatientBeenCheckedInByPhysician(patientEncounter.getId());
        if (hasPatientBeenCheckedIn == true) {
            ServiceResponse<DateTime> dateResponse = medicalService.getDateOfCheckIn(patientEncounter.getId());
            if (dateResponse.hasErrors()) {
                message = "A fatal error has been encountered. Please try again.";
                return ok(index.render(currentUserSession, message, 0));
            }

            DateTime dateNow = dateUtils.getCurrentDateTime();
            DateTime dateTaken = dateResponse.getResponseObject();

            if (dateNow.dayOfYear().equals(dateTaken.dayOfYear())) {
                message = "That patient has already been seen today. Would you like to edit their encounter?";
            } else {
                message = "That patient's encounter has been closed.";
                id = 0;
            }
            return ok(index.render(currentUserSession, message, id));
        }
        return redirect(routes.MedicalController.editGet(searchViewModel.getId()));
    }

    public Result editGet(int patientId) {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        //Get Patient
        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(patientId);
        if (patientServiceResponse.hasErrors()) {
            return internalServerError();
        }
        IPatient patient = patientServiceResponse.getResponseObject();

        //Get Patient Encounter
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse
                = searchService.findCurrentEncounterByPatientId(patientId);
        if (patientEncounterServiceResponse.hasErrors()) {
            return internalServerError();
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        //Get Prescriptions
        ServiceResponse<List<? extends IPatientPrescription>> patientPrescriptionsServiceResponse
                = searchService.findPrescriptionsByEncounterId(patientEncounter.getId());
        List<? extends IPatientPrescription> patientPrescriptions = new ArrayList<>();
        if (patientPrescriptionsServiceResponse.hasErrors()) {
            //do nothing, there might not always be available prescriptions
        } else {
            patientPrescriptions = patientPrescriptionsServiceResponse.getResponseObject();
        }

        //region **Get treatment fields, HPI, PMH, and vitals**

        /* Treatment Fields */
        //String = HpiFieldName
        //List = list of values based on encounter ID OR null if none exist
        ServiceResponse<Map<String, List<? extends IPatientEncounterTreatmentField>>> patientEncounterTreatmentMapResponse
                = medicalService.findTreatmentFieldsByEncounterId(patientEncounter.getId());
        Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap;
        if (patientEncounterTreatmentMapResponse.hasErrors()) {
            return internalServerError();
        } else {
            patientEncounterTreatmentMap = patientEncounterTreatmentMapResponse.getResponseObject();
        }

        /* HPI Fields */
        //String = HpiFieldName
        //List = list of values based on encounter ID OR null if none exist
        ServiceResponse<Map<String, List<? extends IPatientEncounterHpiField>>> patientEncounterHpiMapResponse
                = medicalService.findHpiFieldsByEncounterId(patientEncounter.getId());
        Map<String, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap;
        if (patientEncounterHpiMapResponse.hasErrors()) {
            return internalServerError();
        } else {
            patientEncounterHpiMap = patientEncounterHpiMapResponse.getResponseObject();
        }

        /* PMH fields */
        //String = HpiFieldName
        //List = list of values based on encounter ID OR null if none exist
        ServiceResponse<Map<String, List<? extends IPatientEncounterPmhField>>> patientEncounterPmhMapResponse
                = medicalService.findPmhFieldsByEncounterId(patientEncounter.getId());
        Map<String, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap;
        if (patientEncounterPmhMapResponse.hasErrors()) {
            return internalServerError();
        } else {
            patientEncounterPmhMap = patientEncounterPmhMapResponse.getResponseObject();
        }

        /* Vital Fields */
        ServiceResponse<VitalMultiMap> patientEncounterVitalMapResponse
                = searchService.getVitalMultiMap(patientEncounter.getId());
        VitalMultiMap vitalMap;
        if (patientEncounterVitalMapResponse.hasErrors()) {
            return internalServerError();
        } else {
            vitalMap = patientEncounterVitalMapResponse.getResponseObject();
        }

        //endregion

        //Populate ViewModel

        CreateViewModelGet viewModelGet = medicalHelper.populateViewModelGet(patient,
                patientEncounter,
                patientPrescriptions,
                vitalMap,
                patientEncounterTreatmentMap,
                patientEncounterHpiMap,
                patientEncounterPmhMap);

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

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(patientId);
        if (patientServiceResponse.hasErrors()) {
            return internalServerError();
        }
        IPatient patient = patientServiceResponse.getResponseObject();

        //region **Save stuff**
        //HPI tab
        List<? extends IPatientEncounterHpiField> patientEncounterHpiFields = getPatientEncounterHpiFields(viewModelPost, patientEncounter.getId(), currentUserSession.getId());
        ServiceResponse<List<? extends IPatientEncounterHpiField>> patientEncounterHpiFieldsServiceResponse = medicalService.createPatientEncounterHpiFields(patientEncounterHpiFields);
        if (patientEncounterHpiFieldsServiceResponse.hasErrors()) {
            return internalServerError();
        }

        //PMH tab
        List<? extends IPatientEncounterPmhField> patientEncounterPmhFields = getPatientEncounterPmhFields(viewModelPost, patientEncounter.getId(), currentUserSession.getId());
        ServiceResponse<List<? extends IPatientEncounterPmhField>> patientEncounterPmhFieldsServiceResponse = medicalService.createPatientEncounterPmhFields(patientEncounterPmhFields);
        if (patientEncounterPmhFieldsServiceResponse.hasErrors()) {
            return internalServerError();
        }

        //treatment tab
        List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields = getPatientEncounterTreatmentFields(viewModelPost, patientEncounter.getId(), currentUserSession.getId());
        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentFieldsServiceResponse = medicalService.createPatientEncounterTreatmentFields(patientEncounterTreatmentFields);
        if (patientEncounterTreatmentFieldsServiceResponse.hasErrors()) {
            return internalServerError();
        }

        //prescriptions
        List<? extends IPatientPrescription> patientPrescriptions = getPatientPrescriptions(viewModelPost, patientEncounter.getId(), currentUserSession.getId());
        ServiceResponse<List<? extends IPatientPrescription>> patientPrescriptionsServiceResponse = medicalService.createPatientPrescriptions(patientPrescriptions);
        if (patientPrescriptionsServiceResponse.hasErrors()) {
            return internalServerError();
        }
        //endregion

        String message = "Patient information for " + patient.getFirstName() + " " + patient.getLastName() + " (id: " + patient.getId() + ") was saved successfully.";

        return ok(index.render(currentUserSession, message, 0));
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
        /*ServiceResponse<List<? extends IPatientEncounterVital>> patientEncounterVitalServiceResponse = triageService.createPatientEncounterVitals(patientEncounterVitals);
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            return internalServerError();
        } */

        return ok("true");
    }

    //partials
    public Result newVitalsGet(){
        return ok(newVitals.render());

    }
    public Result listVitalsGet(Integer id){
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(id);
        if (patientEncounterServiceResponse.hasErrors()){
            return internalServerError();
        }
        ServiceResponse<VitalMultiMap> vitalMultiMapServiceResponse = searchService.getVitalMultiMap(patientEncounterServiceResponse.getResponseObject().getId());
        if (vitalMultiMapServiceResponse.hasErrors()){
            return internalServerError();
        }

        return ok(listVitals.render(vitalMultiMapServiceResponse.getResponseObject()));
    }

    //region **generate lists of stuff from CreateViewModelPost**
    private List<? extends IPatientPrescription> getPatientPrescriptions(CreateViewModelPost viewModelPost, int patientEncounterId, int userId) {
        IPatientPrescription patientPrescription;
        List<IPatientPrescription> patientPrescriptions = new ArrayList<>();
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription1())) {
            patientPrescription = medicalHelper.getPatientPrescription(userId, patientEncounterId, viewModelPost.getPrescription1());
            patientPrescriptions.add(patientPrescription);
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription2())) {
            patientPrescription = medicalHelper.getPatientPrescription(userId, patientEncounterId, viewModelPost.getPrescription2());
            patientPrescriptions.add(patientPrescription);
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription3())) {
            patientPrescription = medicalHelper.getPatientPrescription(userId, patientEncounterId, viewModelPost.getPrescription3());
            patientPrescriptions.add(patientPrescription);
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription4())) {
            patientPrescription = medicalHelper.getPatientPrescription(userId, patientEncounterId, viewModelPost.getPrescription4());
            patientPrescriptions.add(patientPrescription);
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription5())) {
            patientPrescription = medicalHelper.getPatientPrescription(userId, patientEncounterId, viewModelPost.getPrescription5());
            patientPrescriptions.add(patientPrescription);
        }
        return patientPrescriptions;
    }

    private List<? extends IPatientEncounterTreatmentField> getPatientEncounterTreatmentFields(CreateViewModelPost viewModelPost, int patientEncounterId, int userId) {
        ServiceResponse<ITreatmentField> treatmentFieldServiceResponse;
        ServiceResponse<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldServiceResponse;
        IPatientEncounterTreatmentField patientEncounterTreatmentField;
        List<IPatientEncounterTreatmentField> patientEncounterTreatmentFields = new ArrayList<>();

        patientEncounterTreatmentFieldServiceResponse = searchService.findRecentTreatmentField(patientEncounterId, "assessment");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAssessment()) && (patientEncounterTreatmentFieldServiceResponse.hasErrors() || !viewModelPost.getAssessment().equals(patientEncounterTreatmentFieldServiceResponse.getResponseObject().getTreatmentFieldValue()))) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("assessment");
            if (!treatmentFieldServiceResponse.hasErrors()) {
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId, patientEncounterId, treatmentFieldServiceResponse.getResponseObject(), viewModelPost.getAssessment());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        patientEncounterTreatmentFieldServiceResponse = searchService.findRecentTreatmentField(patientEncounterId, "treatment");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getTreatment()) && (patientEncounterTreatmentFieldServiceResponse.hasErrors() || !viewModelPost.getTreatment().equals(patientEncounterTreatmentFieldServiceResponse.getResponseObject().getTreatmentFieldValue()))) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("treatment");
            if (!treatmentFieldServiceResponse.hasErrors()) {
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId, patientEncounterId, treatmentFieldServiceResponse.getResponseObject(), viewModelPost.getTreatment());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem1())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("problem");
            if (!treatmentFieldServiceResponse.hasErrors()) {
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId, patientEncounterId, treatmentFieldServiceResponse.getResponseObject(), viewModelPost.getProblem1());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem2())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("problem");
            if (!treatmentFieldServiceResponse.hasErrors()) {
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId, patientEncounterId, treatmentFieldServiceResponse.getResponseObject(), viewModelPost.getProblem2());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem3())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("problem");
            if (!treatmentFieldServiceResponse.hasErrors()) {
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId, patientEncounterId, treatmentFieldServiceResponse.getResponseObject(), viewModelPost.getProblem3());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem4())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("problem");
            if (!treatmentFieldServiceResponse.hasErrors()) {
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId, patientEncounterId, treatmentFieldServiceResponse.getResponseObject(), viewModelPost.getProblem4());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem5())) {
            treatmentFieldServiceResponse = searchService.findTreatmentField("problem");
            if (!treatmentFieldServiceResponse.hasErrors()) {
                patientEncounterTreatmentField = medicalHelper.getPatientEncounterTreatmentField(userId, patientEncounterId, treatmentFieldServiceResponse.getResponseObject(), viewModelPost.getProblem5());
                patientEncounterTreatmentFields.add(patientEncounterTreatmentField);
            }
        }
        return patientEncounterTreatmentFields;
    }

    private List<? extends IPatientEncounterPmhField> getPatientEncounterPmhFields(CreateViewModelPost viewModelPost, int patientEncounterId, int userId) {
        /* Get current PMH field value. If it changed, save the new one */
        ServiceResponse<IPmhField> pmhFieldServiceResponse;
        ServiceResponse<IPatientEncounterPmhField> patientEncounterPmhFieldServiceResponse;
        IPatientEncounterPmhField patientEncounterPmhField;
        List<IPatientEncounterPmhField> patientEncounterPmhFields = new ArrayList<>();

        //If the patientEncounterPmhFieldServiceResponse has errors, that means nothing was found.
        //In this case, that means it's a new field and not necessarily an error
        patientEncounterPmhFieldServiceResponse = searchService.findRecentPmhField(patientEncounterId, "medicalSurgicalHistory");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getMedicalSurgicalHistory()) && (patientEncounterPmhFieldServiceResponse.hasErrors() || !viewModelPost.getMedicalSurgicalHistory().equals(patientEncounterPmhFieldServiceResponse.getResponseObject().getPmhFieldValue()))) {
            pmhFieldServiceResponse = searchService.findPmhField("medicalSurgicalHistory");
            if (!pmhFieldServiceResponse.hasErrors()) {
                patientEncounterPmhField = medicalHelper.getPatientEncounterPmhField(userId, patientEncounterId, pmhFieldServiceResponse.getResponseObject(), viewModelPost.getMedicalSurgicalHistory());
                patientEncounterPmhFields.add(patientEncounterPmhField);
            }
        }
        patientEncounterPmhFieldServiceResponse = searchService.findRecentPmhField(patientEncounterId, "socialHistory");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getSocialHistory()) && (patientEncounterPmhFieldServiceResponse.hasErrors() || !viewModelPost.getSocialHistory().equals(patientEncounterPmhFieldServiceResponse.getResponseObject().getPmhFieldValue()))) {
            pmhFieldServiceResponse = searchService.findPmhField("socialHistory");
            if (!pmhFieldServiceResponse.hasErrors()) {
                patientEncounterPmhField = medicalHelper.getPatientEncounterPmhField(userId, patientEncounterId, pmhFieldServiceResponse.getResponseObject(), viewModelPost.getSocialHistory());
                patientEncounterPmhFields.add(patientEncounterPmhField);
            }
        }
        patientEncounterPmhFieldServiceResponse = searchService.findRecentPmhField(patientEncounterId, "currentMedication");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getCurrentMedication()) && (patientEncounterPmhFieldServiceResponse.hasErrors() || !viewModelPost.getCurrentMedication().equals(patientEncounterPmhFieldServiceResponse.getResponseObject().getPmhFieldValue()))) {
            pmhFieldServiceResponse = searchService.findPmhField("currentMedication");
            if (!pmhFieldServiceResponse.hasErrors()) {
                patientEncounterPmhField = medicalHelper.getPatientEncounterPmhField(userId, patientEncounterId, pmhFieldServiceResponse.getResponseObject(), viewModelPost.getCurrentMedication());
                patientEncounterPmhFields.add(patientEncounterPmhField);
            }
        }
        patientEncounterPmhFieldServiceResponse = searchService.findRecentPmhField(patientEncounterId, "familyHistory");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getFamilyHistory()) && (patientEncounterPmhFieldServiceResponse.hasErrors() || !viewModelPost.getFamilyHistory().equals(patientEncounterPmhFieldServiceResponse.getResponseObject().getPmhFieldValue()))) {
            pmhFieldServiceResponse = searchService.findPmhField("familyHistory");
            if (!pmhFieldServiceResponse.hasErrors()) {
                patientEncounterPmhField = medicalHelper.getPatientEncounterPmhField(userId, patientEncounterId, pmhFieldServiceResponse.getResponseObject(), viewModelPost.getFamilyHistory());
                patientEncounterPmhFields.add(patientEncounterPmhField);
            }
        }
        return patientEncounterPmhFields;
    }

    private List<? extends IPatientEncounterHpiField> getPatientEncounterHpiFields(CreateViewModelPost viewModelPost, int patientEncounterId, int userId) {
        /* Get current HPI field value. If it changed, save the new one */
        ServiceResponse<IHpiField> hpiFieldServiceResponse;
        ServiceResponse<IPatientEncounterHpiField> patientEncounterHpiFieldServiceResponse;
        IPatientEncounterHpiField patientEncounterHpiField;
        List<IPatientEncounterHpiField> patientEncounterHpiFields = new ArrayList();

        //If the patientEncounterHpiFieldServiceResponse has errors, that means nothing was found.
        //In this case, that means it's a new field and not necessarily an error
        patientEncounterHpiFieldServiceResponse = searchService.findRecentHpiField(patientEncounterId, "onset");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getOnset()) && (patientEncounterHpiFieldServiceResponse.hasErrors() || !viewModelPost.getOnset().equals(patientEncounterHpiFieldServiceResponse.getResponseObject().getHpiFieldValue()))) {
            hpiFieldServiceResponse = searchService.findHpiField("onset");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getOnset());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        patientEncounterHpiFieldServiceResponse = searchService.findRecentHpiField(patientEncounterId, "severity");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getSeverity()) && (patientEncounterHpiFieldServiceResponse.hasErrors() || !viewModelPost.getSeverity().equals(patientEncounterHpiFieldServiceResponse.getResponseObject().getHpiFieldValue()))) {
            hpiFieldServiceResponse = searchService.findHpiField("severity");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getSeverity());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        patientEncounterHpiFieldServiceResponse = searchService.findRecentHpiField(patientEncounterId, "radiation");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getRadiation()) && (patientEncounterHpiFieldServiceResponse.hasErrors() || !viewModelPost.getRadiation().equals(patientEncounterHpiFieldServiceResponse.getResponseObject().getHpiFieldValue()))) {
            hpiFieldServiceResponse = searchService.findHpiField("radiation");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getRadiation());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        patientEncounterHpiFieldServiceResponse = searchService.findRecentHpiField(patientEncounterId, "quality");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getQuality()) && (patientEncounterHpiFieldServiceResponse.hasErrors() || !viewModelPost.getQuality().equals(patientEncounterHpiFieldServiceResponse.getResponseObject().getHpiFieldValue()))) {
            hpiFieldServiceResponse = searchService.findHpiField("quality");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getQuality());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        patientEncounterHpiFieldServiceResponse = searchService.findRecentHpiField(patientEncounterId, "provokes");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProvokes()) && (patientEncounterHpiFieldServiceResponse.hasErrors() || !viewModelPost.getProvokes().equals(patientEncounterHpiFieldServiceResponse.getResponseObject().getHpiFieldValue()))) {
            hpiFieldServiceResponse = searchService.findHpiField("provokes");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getProvokes());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        patientEncounterHpiFieldServiceResponse = searchService.findRecentHpiField(patientEncounterId, "palliates");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPalliates()) && (patientEncounterHpiFieldServiceResponse.hasErrors() || !viewModelPost.getPalliates().equals(patientEncounterHpiFieldServiceResponse.getResponseObject().getHpiFieldValue()))) {
            hpiFieldServiceResponse = searchService.findHpiField("palliates");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getPalliates());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        patientEncounterHpiFieldServiceResponse = searchService.findRecentHpiField(patientEncounterId, "timeOfDay");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getTimeOfDay()) && (patientEncounterHpiFieldServiceResponse.hasErrors() || !viewModelPost.getTimeOfDay().equals(patientEncounterHpiFieldServiceResponse.getResponseObject().getHpiFieldValue()))) {
            hpiFieldServiceResponse = searchService.findHpiField("timeOfDay");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getTimeOfDay());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        patientEncounterHpiFieldServiceResponse = searchService.findRecentHpiField(patientEncounterId, "physicalExamination");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPhysicalExamination()) && (patientEncounterHpiFieldServiceResponse.hasErrors() || !viewModelPost.getPhysicalExamination().equals(patientEncounterHpiFieldServiceResponse.getResponseObject().getHpiFieldValue()))) {
            hpiFieldServiceResponse = searchService.findHpiField("physicalExamination");
            if (!hpiFieldServiceResponse.hasErrors()) {
                patientEncounterHpiField = medicalHelper.getPatientEncounterHpiField(userId, patientEncounterId, hpiFieldServiceResponse.getResponseObject(), viewModelPost.getPhysicalExamination());
                patientEncounterHpiFields.add(patientEncounterHpiField);
            }
        }
        patientEncounterHpiFieldServiceResponse = searchService.findRecentHpiField(patientEncounterId, "narrative");
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getNarrative()) && (patientEncounterHpiFieldServiceResponse.hasErrors() || !viewModelPost.getNarrative().equals(patientEncounterHpiFieldServiceResponse.getResponseObject().getHpiFieldValue()))) {
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
