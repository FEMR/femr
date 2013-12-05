package femr.ui.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.*;
import femr.common.models.*;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.pharmacy.CreateViewModelGet;
import femr.ui.models.pharmacy.CreateViewModelPost;
import femr.ui.views.html.pharmacies.index;
import femr.ui.views.html.pharmacies.populated;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.codehaus.jackson.node.ObjectNode;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;


@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class PharmaciesController extends Controller {
    private final Form<CreateViewModelPost> createViewModelPostForm = Form.form(CreateViewModelPost.class);
    private Provider<IPatientPrescription> patientPrescriptionProvider;
    private ISessionService sessionService;
    private ISearchService searchService;
    private ITriageService triageService;
    private IPharmacyService pharmacyService;
    private IMedicalService medicalService;

    @Inject
    public PharmaciesController(IPharmacyService pharmacyService,
                                ITriageService triageService,
                                ISessionService sessionService,
                                ISearchService searchService,
                                IMedicalService medicalService,
                                Provider<IPatientPrescription> patientPrescriptionProvider) {
        this.pharmacyService = pharmacyService;
        this.triageService = triageService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.medicalService = medicalService;
        this.patientPrescriptionProvider = patientPrescriptionProvider;
    }

    public Result index() {
        boolean error = false;
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        return ok(index.render(currentUserSession, error, null));
    }

    public Result typeaheadJSONGet(){
        ObjectNode result = Json.newObject();

        ServiceResponse<List<? extends IMedication>> medicationServiceResponse = searchService.findAllMedications();
        if (medicationServiceResponse.hasErrors()){
            return ok(result);
        }
        List<? extends IMedication> medications = medicationServiceResponse.getResponseObject();

        for (int medication = 0; medication < medications.size(); medication++){
            result.put("medicine" + medication,medications.get(medication).getName());
        }

        return ok(result);
    }

    public Result createGet() {
        String s_id = request().getQueryString("id");

        //needs to validate an id was received from the query string
        s_id = s_id.trim();

        Integer id = Integer.parseInt(s_id);
        CreateViewModelGet viewModelGet = new CreateViewModelGet();
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        Boolean error = false;

        //return to index if error finding a patient
        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);
        if (patientServiceResponse.hasErrors()) {
            error = true;
            return ok(index.render(currentUserSession, error, "That patient can not be found."));
        }

        IPatient patient = patientServiceResponse.getResponseObject();
        viewModelGet.setpID(patient.getId());
        viewModelGet.setFirstName(patient.getFirstName());
        viewModelGet.setLastName(patient.getLastName());
        viewModelGet.setAge(dateUtils.calculateYears(patient.getAge()));
        viewModelGet.setSex(patient.getSex());

        //return to index if error finding a patient encounter
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(id);
        if (patientEncounterServiceResponse.hasErrors()) {
            error = true;
            return ok(index.render(currentUserSession, error, "An error has occured"));
        }

        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();
        viewModelGet.setWeeksPregnant(patientEncounter.getWeeksPregnant());

        //set viewModel field to null if patient vital does not exist
        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();
        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse;
        int TOTAL_VITALS = 9;
        for (int vital = 1; vital <= TOTAL_VITALS; vital++) {
            patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(vital, patientEncounter.getId());
            if (patientEncounterVitalServiceResponse.hasErrors()) {
                patientEncounterVitals.add(null);
            } else {
                patientEncounterVitals.add(patientEncounterVitalServiceResponse.getResponseObject());
            }
        }
        if (patientEncounterVitals.get(4) == null)
        {
            viewModelGet.setHeightFeet(null);
        }
        else
        {
            viewModelGet.setHeightFeet(getVitalOrNull(patientEncounterVitals.get(4)).intValue());
        }
        if (patientEncounterVitals.get(5) == null)
        {
            viewModelGet.setHeightinches(null);
        }
        else
        {
            viewModelGet.setHeightinches(getVitalOrNull(patientEncounterVitals.get(5)).intValue());
        }
        viewModelGet.setWeight(getVitalOrNull(patientEncounterVitals.get(6)));

        //find patient prescriptions
        ServiceResponse<List<? extends IPatientPrescription>> patientPrescriptionsServiceResponse = searchService.findPrescriptionsByEncounterId(patientEncounter.getId());
        if (patientPrescriptionsServiceResponse.hasErrors()) {
            error = true;
            return ok(index.render(currentUserSession, error, "No prescriptions exist for that patient"));
        }

        List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionsServiceResponse.getResponseObject();
        List<String> dynamicViewMedications = new ArrayList<>();

        for (int filledPrescription = 0; filledPrescription < patientPrescriptions.size(); filledPrescription++) {
            if (patientPrescriptions.get(filledPrescription).getReplaced() != true) {
                dynamicViewMedications.add(patientPrescriptions.get(filledPrescription).getMedicationName());
            }
        }
        //this should probably be left as a List or ArrayList
        String[] viewMedications = new String[dynamicViewMedications.size()];
        viewMedications = dynamicViewMedications.toArray(viewMedications);
        viewModelGet.setMedications(viewMedications);

        //find patient problems, they don't have to exist.
        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> patientEncounterProblemsServiceResponse = searchService.findProblemsByEncounterId(patientEncounter.getId());
        List<? extends IPatientEncounterTreatmentField> patientEncounterProblems = new ArrayList<>();
        List<String> dynamicViewProblems = new ArrayList<>();

        if (patientEncounterProblemsServiceResponse.hasErrors()) {
            //error = true;
        } else {
            patientEncounterProblems = patientEncounterProblemsServiceResponse.getResponseObject();
        }

        if (patientEncounterProblems.size() > 0) {
            for (int problem = 0; problem < patientEncounterProblems.size(); problem++) {
                dynamicViewProblems.add(patientEncounterProblems.get(problem).getTreatmentFieldValue());
            }
        }

        String[] viewProblems = new String[dynamicViewProblems.size()];
        viewProblems = dynamicViewProblems.toArray(viewProblems);
        viewModelGet.setProblems(viewProblems);

        return ok(populated.render(currentUserSession, viewModelGet, error));
    }

    public Result createPost(int id) {
        CreateViewModelPost createViewModelPost = createViewModelPostForm.bindFromRequest().get();
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(id);
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        //replace prescription 1
        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication1())) {
            IPatientPrescription newPatientPrescription = initializeNewPrescription(currentUserSession, patientEncounter, createViewModelPost.getReplacementMedication1());
            ServiceResponse<IPatientPrescription> newPatientPrescriptionServiceResponse = medicalService.createPatientPrescription(newPatientPrescription);

            IPatientPrescription oldPatientPrescription = updateOldPrescription(newPatientPrescriptionServiceResponse.getResponseObject().getId(), patientEncounter.getId(), createViewModelPost.getPrescription1());
            ServiceResponse<IPatientPrescription> updatedOldPatientPrescription = pharmacyService.updatePatientPrescription(oldPatientPrescription);
        }
        //replace prescription 2
        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication2())) {
            IPatientPrescription newPatientPrescription = initializeNewPrescription(currentUserSession, patientEncounter, createViewModelPost.getReplacementMedication2());
            ServiceResponse<IPatientPrescription> newPatientPrescriptionServiceResponse = medicalService.createPatientPrescription(newPatientPrescription);

            IPatientPrescription oldPatientPrescription = updateOldPrescription(newPatientPrescriptionServiceResponse.getResponseObject().getId(), patientEncounter.getId(), createViewModelPost.getPrescription2());
            ServiceResponse<IPatientPrescription> updatedOldPatientPrescription = pharmacyService.updatePatientPrescription(oldPatientPrescription);
        }
        //replace prescription 3
        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication3())) {
            IPatientPrescription newPatientPrescription = initializeNewPrescription(currentUserSession, patientEncounter, createViewModelPost.getReplacementMedication3());
            ServiceResponse<IPatientPrescription> newPatientPrescriptionServiceResponse = medicalService.createPatientPrescription(newPatientPrescription);

            IPatientPrescription oldPatientPrescription = updateOldPrescription(newPatientPrescriptionServiceResponse.getResponseObject().getId(), patientEncounter.getId(), createViewModelPost.getPrescription3());
            ServiceResponse<IPatientPrescription> updatedOldPatientPrescription = pharmacyService.updatePatientPrescription(oldPatientPrescription);
        }
        //replace prescription 4
        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication4())) {
            IPatientPrescription newPatientPrescription = initializeNewPrescription(currentUserSession, patientEncounter, createViewModelPost.getReplacementMedication4());
            ServiceResponse<IPatientPrescription> newPatientPrescriptionServiceResponse = medicalService.createPatientPrescription(newPatientPrescription);

            IPatientPrescription oldPatientPrescription = updateOldPrescription(newPatientPrescriptionServiceResponse.getResponseObject().getId(), patientEncounter.getId(), createViewModelPost.getPrescription4());
            ServiceResponse<IPatientPrescription> updatedOldPatientPrescription = pharmacyService.updatePatientPrescription(oldPatientPrescription);
        }
        //replace prescription 5
        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication5())) {
            IPatientPrescription newPatientPrescription = initializeNewPrescription(currentUserSession, patientEncounter, createViewModelPost.getReplacementMedication5());
            ServiceResponse<IPatientPrescription> newPatientPrescriptionServiceResponse = medicalService.createPatientPrescription(newPatientPrescription);

            IPatientPrescription oldPatientPrescription = updateOldPrescription(newPatientPrescriptionServiceResponse.getResponseObject().getId(), patientEncounter.getId(), createViewModelPost.getPrescription5());
            ServiceResponse<IPatientPrescription> updatedOldPatientPrescription = pharmacyService.updatePatientPrescription(oldPatientPrescription);
        }
        return index();
    }

    private IPatientPrescription initializeNewPrescription(CurrentUser currentUserSession, IPatientEncounter patientEncounter, String medicationName) {
        IPatientPrescription patientPrescription = patientPrescriptionProvider.get();
        patientPrescription.setEncounterId(patientEncounter.getId());
        patientPrescription.setUserId(currentUserSession.getId());
        patientPrescription.setReplaced(false);
        patientPrescription.setReplacementId(null);
        patientPrescription.setMedicationName(medicationName);
        patientPrescription.setDateTaken(dateUtils.getCurrentDateTime());
        return patientPrescription;
    }

    private IPatientPrescription updateOldPrescription(int replacementId, int encounterId, String name) {
        ServiceResponse<IPatientPrescription> patientPrescriptionServiceResponse = pharmacyService.findPatientPrescriptionByEncounterIdAndPrescriptionName(encounterId, name);
        IPatientPrescription patientPrescription = patientPrescriptionServiceResponse.getResponseObject();
        patientPrescription.setReplaced(true);
        patientPrescription.setReplacementId(replacementId);
        return patientPrescription;
    }

    private Float getVitalOrNull(IPatientEncounterVital patientEncounterVital) {
        if (patientEncounterVital == null)
            return null;
        else
            return patientEncounterVital.getVitalValue();
    }
}
