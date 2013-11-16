package femr.ui.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IMedicalService;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.*;
import femr.ui.helpers.controller.MedicalHelper;
import femr.ui.models.medical.CreateViewModelGet;
import femr.ui.models.medical.CreateViewModelPost;
import femr.ui.models.medical.UpdateVitalsModel;
import femr.ui.views.html.medical.index;
import femr.ui.views.html.medical.indexPopulated;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicalController extends Controller {

    private final Form<CreateViewModelPost> createViewModelPostForm = Form.form(CreateViewModelPost.class);
    private final Form<UpdateVitalsModel> updateVitalsModelForm = Form.form(UpdateVitalsModel.class);
    private Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider;
    private Provider<IPatientEncounterHpiField> patientEncounterHpiFieldProvider;
    private Provider<IPatientPrescription> patientPrescriptionProvider;
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private ISessionService sessionService;
    private ISearchService searchService;
    private ITriageService triageService;
    private IMedicalService medicalService;
    private MedicalHelper medicalHelper;

    @Inject
    public MedicalController(ISessionService sessionService, ISearchService searchService, ITriageService triageService, IMedicalService medicalService, Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider, Provider<IPatientEncounterHpiField> patientEncounterHpiFieldProvider, Provider<IPatientPrescription> patientPrescriptionProvider, Provider<IPatientEncounterVital> patientEncounterVitalProvider, MedicalHelper medicalHelper) {

        this.sessionService = sessionService;
        this.searchService = searchService;
        this.triageService = triageService;
        this.medicalService = medicalService;
        this.patientEncounterTreatmentFieldProvider = patientEncounterTreatmentFieldProvider;
        this.patientEncounterHpiFieldProvider = patientEncounterHpiFieldProvider;
        this.patientPrescriptionProvider = patientPrescriptionProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.medicalHelper = medicalHelper;
    }

    public Result createGet() {

        boolean error = false;

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        return ok(index.render(currentUserSession, error, null));
    }

    public Result createPopulatedPost() {

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        CreateViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(viewModelPost.getId());
        if (patientEncounterServiceResponse.hasErrors()) {
            return createGet();
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        //Treatment Data
        List<IPatientEncounterTreatmentField> patientEncounterTreatmentFields = populatePatientEncounterTreatmentFields(viewModelPost, patientEncounter, currentUserSession);

        for (int i = 0; i < patientEncounterTreatmentFields.size(); i++) {
            if (StringUtils.isNullOrWhiteSpace(patientEncounterTreatmentFields.get(i).getTreatmentFieldValue())) {
                continue;
            } else {
                medicalService.createPatientEncounterTreatmentField(patientEncounterTreatmentFields.get(i));
            }
        }

        //prescriptions
        List<IPatientPrescription> patientPrescriptions = populatePatientPrescriptions(viewModelPost, patientEncounter, currentUserSession);
        for (int k = 0; k < patientPrescriptions.size(); k++) {
            if (StringUtils.isNullOrWhiteSpace(patientPrescriptions.get(k).getMedicationName())) {
                continue;
            } else {
                medicalService.createPatientPrescription(patientPrescriptions.get(k));
            }
        }

        //HPI Data
        List<IPatientEncounterHpiField> patientEncounterHpiFields = populatePatientEncounterHpiFields(viewModelPost, patientEncounter, currentUserSession);

        for (int j = 0; j < patientEncounterHpiFields.size(); j++) {
            if (StringUtils.isNullOrWhiteSpace(patientEncounterHpiFields.get(j).getHpiFieldValue())) {
                continue;
            } else {
                medicalService.createPatientEncounterHpiField(patientEncounterHpiFields.get(j));
            }
        }

        return createGet();
    }

    public Result updateVitalsPost(int id) {

        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<IPatientEncounter> currentEncounterByPatientId = searchService.findCurrentEncounterByPatientId(id);

        UpdateVitalsModel updateVitalsModel = updateVitalsModelForm.bindFromRequest().get();
        updatePatientVitals(updateVitalsModel, currentUser.getId(), currentEncounterByPatientId.getResponseObject().getId());

        return ok("true");
    }

    private void updatePatientVitals(UpdateVitalsModel updateVitalsModel, int currentUserId, int patientEncounterId) {

        List<Double> vitals = new ArrayList<>();
        vitals.add(updateVitalsModel.getRespRate());
        vitals.add(updateVitalsModel.getHeartRate());
        vitals.add(updateVitalsModel.getTemperature());
        vitals.add(updateVitalsModel.getOxygen());
        vitals.add(updateVitalsModel.getHeightFt());
        vitals.add(updateVitalsModel.getHeightIn());
        vitals.add(updateVitalsModel.getWeight());
        vitals.add(updateVitalsModel.getBpSystolic());
        vitals.add(updateVitalsModel.getBpDiastolic());

        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            IPatientEncounterVital patientEncounterVital = patientEncounterVitalProvider.get();
            patientEncounterVital.setDateTaken((dateUtils.getCurrentDateTime()));
            patientEncounterVital.setUserId(currentUserId);
            patientEncounterVital.setPatientEncounterId(patientEncounterId);
            patientEncounterVital.setVitalId(i + 1);
            patientEncounterVital.setVitalValue(vitals.get(i).floatValue());
            patientEncounterVitals.add(patientEncounterVital);
        }

        for (int i = 0; i < patientEncounterVitals.size(); i++) {
            if (patientEncounterVitals.get(i).getVitalValue() > 0) {
                triageService.createPatientEncounterVital(patientEncounterVitals.get(i));
            }
        }
    }

    public Result createPopulatedGet() {

        boolean error = false;

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        CreateViewModelGet viewModel = new CreateViewModelGet();

        String s_patientID = request().getQueryString("id");
        s_patientID = s_patientID.trim();
        int i_patientID = Integer.parseInt(s_patientID);

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(i_patientID);
        if (patientServiceResponse.hasErrors()) {
            error = true;
            return ok(index.render(currentUserSession, error, "That patient can not be found."));
        }
        IPatient patient = patientServiceResponse.getResponseObject();
        viewModel.setpID(patient.getId());
        viewModel.setCity(patient.getCity());
        viewModel.setFirstName(patient.getFirstName());
        viewModel.setLastName(patient.getLastName());
        viewModel.setAge(dateUtils.calculateYears(patient.getAge()));
        viewModel.setSex(patient.getSex());

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(i_patientID);
        if (patientEncounterServiceResponse.hasErrors()) {
            error = true;
            return ok(index.render(currentUserSession, error, "An error occurred"));
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();
        viewModel.setChiefComplaint(patientEncounter.getChiefComplaint());
        viewModel.setWeeksPregnant(patientEncounter.getWeeksPregnant());

        //populating this viewModel with vitals is
        //(unfortunately and temporarily) dependant
        // on the order of vitals in the database

        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse;

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(1, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setRespiratoryRate(null);
        } else {
            viewModel.setRespiratoryRate(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(2, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setHeartRate(null);
        } else {
            viewModel.setHeartRate(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(3, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setTemperature(null);
        } else {
            viewModel.setTemperature(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(4, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setOxygenSaturation(null);
        } else {
            viewModel.setOxygenSaturation(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(5, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setHeightFeet(null);
        } else {
            viewModel.setHeightFeet(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(6, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setHeightInches(null);
        } else {
            viewModel.setHeightInches(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(7, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setWeight(null);
        } else {
            viewModel.setWeight(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(8, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setBloodPressureSystolic(null);
        } else {
            viewModel.setBloodPressureSystolic(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(9, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setBloodPressureDiastolic(null);
        } else {
            viewModel.setBloodPressureDiastolic(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        //check to make sure a patient hasn't been checked in before
        //if they have, don't goto the populated page
        boolean hasPatientBeenCheckedIn = medicalService.hasPatientBeenCheckedIn(patientEncounter.getId());
        if (hasPatientBeenCheckedIn == true) {
            return ok(index.render(currentUserSession, true, "That patient has already been seen"));
        } else {
            return ok(indexPopulated.render(currentUserSession, viewModel));
        }
    }

    //helper functions

    private List<IPatientEncounterHpiField> populatePatientEncounterHpiFields(CreateViewModelPost viewModelPost, IPatientEncounter patientEncounter, CurrentUser currentUserSession) {

        List<IPatientEncounterHpiField> patientEncounterHpiFields = new ArrayList<>();
        IPatientEncounterHpiField[] patientEncounterHpiField = new IPatientEncounterHpiField[9];
        for (int i = 0; i < 9; i++) {
            patientEncounterHpiField[i] = patientEncounterHpiFieldProvider.get();
            patientEncounterHpiField[i].setDateTaken(medicalService.getCurrentDateTime());
            patientEncounterHpiField[i].setPatientEncounterId(patientEncounter.getId());
            patientEncounterHpiField[i].setUserId(currentUserSession.getId());
            patientEncounterHpiField[i].setHpiFieldId(i + 1);
        }
        patientEncounterHpiField[0].setHpiFieldValue(viewModelPost.getOnset());
        patientEncounterHpiField[1].setHpiFieldValue(viewModelPost.getOnsetTime());
        patientEncounterHpiField[2].setHpiFieldValue(viewModelPost.getSeverity());
        patientEncounterHpiField[3].setHpiFieldValue(viewModelPost.getRadiation());
        patientEncounterHpiField[4].setHpiFieldValue(viewModelPost.getQuality());
        patientEncounterHpiField[5].setHpiFieldValue(viewModelPost.getProvokes());
        patientEncounterHpiField[6].setHpiFieldValue(viewModelPost.getPalliates());
        patientEncounterHpiField[7].setHpiFieldValue(viewModelPost.getTimeOfDay());
        patientEncounterHpiField[8].setHpiFieldValue(viewModelPost.getPhysicalExamination());

        patientEncounterHpiFields.addAll(Arrays.asList(patientEncounterHpiField));
        return patientEncounterHpiFields;
    }

    private List<IPatientEncounterTreatmentField> populatePatientEncounterTreatmentFields(CreateViewModelPost viewModelPost, IPatientEncounter patientEncounter, CurrentUser currentUserSession) {

        List<IPatientEncounterTreatmentField> patientEncounterTreatmentFields = new ArrayList<>();
        IPatientEncounterTreatmentField[] patientEncounterTreatmentField = new IPatientEncounterTreatmentField[8];
        for (int i = 0; i < 8; i++) {
            patientEncounterTreatmentField[i] = patientEncounterTreatmentFieldProvider.get();
            patientEncounterTreatmentField[i].setDateTaken(medicalService.getCurrentDateTime());
            patientEncounterTreatmentField[i].setPatientEncounterId(patientEncounter.getId());
            patientEncounterTreatmentField[i].setUserId(currentUserSession.getId());
        }

        patientEncounterTreatmentField[0].setTreatmentFieldId(1);
        patientEncounterTreatmentField[0].setTreatmentFieldValue(viewModelPost.getAssessment());

        patientEncounterTreatmentField[1].setTreatmentFieldId(2);
        patientEncounterTreatmentField[1].setTreatmentFieldValue(viewModelPost.getProblem1());
        patientEncounterTreatmentField[2].setTreatmentFieldId(2);
        patientEncounterTreatmentField[2].setTreatmentFieldValue(viewModelPost.getProblem2());
        patientEncounterTreatmentField[3].setTreatmentFieldId(2);
        patientEncounterTreatmentField[3].setTreatmentFieldValue(viewModelPost.getProblem3());
        patientEncounterTreatmentField[4].setTreatmentFieldId(2);
        patientEncounterTreatmentField[4].setTreatmentFieldValue(viewModelPost.getProblem4());
        patientEncounterTreatmentField[5].setTreatmentFieldId(2);
        patientEncounterTreatmentField[5].setTreatmentFieldValue(viewModelPost.getProblem5());

        patientEncounterTreatmentField[6].setTreatmentFieldId(3);
        patientEncounterTreatmentField[6].setTreatmentFieldValue(viewModelPost.getTreatment());
        patientEncounterTreatmentField[7].setTreatmentFieldId(4);
        patientEncounterTreatmentField[7].setTreatmentFieldValue(viewModelPost.getFamilyHistory());

        patientEncounterTreatmentFields.addAll(Arrays.asList(patientEncounterTreatmentField));
        return patientEncounterTreatmentFields;
    }

    private List<IPatientPrescription> populatePatientPrescriptions(CreateViewModelPost viewModelPost, IPatientEncounter patientEncounter, CurrentUser currentUserSession) {

        int SIZE = 5;
        List<IPatientPrescription> patientPrescriptions = new ArrayList<>();
        IPatientPrescription[] patientPrescription = new IPatientPrescription[SIZE];
        for (int i = 0; i < SIZE; i++) {
            patientPrescription[i] = patientPrescriptionProvider.get();
            patientPrescription[i].setEncounterId(patientEncounter.getId());
            patientPrescription[i].setUserId(currentUserSession.getId());
            patientPrescription[i].setReplaced(false);
            patientPrescription[i].setReplacementId(null);
        }

        patientPrescription[0].setMedicationName(viewModelPost.getPrescription1());
        patientPrescription[1].setMedicationName(viewModelPost.getPrescription2());
        patientPrescription[2].setMedicationName(viewModelPost.getPrescription3());
        patientPrescription[3].setMedicationName(viewModelPost.getPrescription4());
        patientPrescription[4].setMedicationName(viewModelPost.getPrescription5());

        patientPrescriptions.addAll(Arrays.asList(patientPrescription));
        return patientPrescriptions;
    }
}
