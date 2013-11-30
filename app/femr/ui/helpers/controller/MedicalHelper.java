package femr.ui.helpers.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.common.models.*;
import femr.ui.models.medical.CreateViewModelPost;
import femr.ui.models.medical.CreateViewModelGet;
import femr.ui.models.medical.UpdateVitalsModel;
import femr.util.calculations.dateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicalHelper {
    private Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider;
    private Provider<IPatientEncounterHpiField> patientEncounterHpiFieldProvider;
    private Provider<IPatientPrescription> patientPrescriptionProvider;
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;

    @Inject
    public MedicalHelper(Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider, Provider<IPatientEncounterHpiField> patientEncounterHpiFieldProvider, Provider<IPatientPrescription> patientPrescriptionProvider, Provider<IPatientEncounterVital> patientEncounterVitalProvider) {
        this.patientEncounterTreatmentFieldProvider = patientEncounterTreatmentFieldProvider;
        this.patientEncounterHpiFieldProvider = patientEncounterHpiFieldProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.patientPrescriptionProvider = patientPrescriptionProvider;
    }


    public List<IPatientEncounterHpiField> populateHpiFields(CreateViewModelPost viewModelPost, IPatientEncounter patientEncounter, CurrentUser currentUserSession) {
        List<IPatientEncounterHpiField> patientEncounterHpiFields = new ArrayList<>();
        IPatientEncounterHpiField[] patientEncounterHpiField = new IPatientEncounterHpiField[9];
        for (int i = 0; i < 9; i++) {
            patientEncounterHpiField[i] = patientEncounterHpiFieldProvider.get();
            patientEncounterHpiField[i].setDateTaken(dateUtils.getCurrentDateTime());
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

    public List<IPatientEncounterTreatmentField> populateTreatmentFields(CreateViewModelPost viewModelPost, IPatientEncounter patientEncounter, CurrentUser currentUserSession) {

        List<IPatientEncounterTreatmentField> patientEncounterTreatmentFields = new ArrayList<>();
        IPatientEncounterTreatmentField[] patientEncounterTreatmentField = new IPatientEncounterTreatmentField[8];
        for (int i = 0; i < 8; i++) {
            patientEncounterTreatmentField[i] = patientEncounterTreatmentFieldProvider.get();
            patientEncounterTreatmentField[i].setDateTaken(dateUtils.getCurrentDateTime());
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

    public List<IPatientPrescription> populatePatientPrescriptions(CreateViewModelPost viewModelPost, IPatientEncounter patientEncounter, CurrentUser currentUserSession) {

        int SIZE = 5;
        List<IPatientPrescription> patientPrescriptions = new ArrayList<>();
        IPatientPrescription[] patientPrescription = new IPatientPrescription[SIZE];
        for (int i = 0; i < SIZE; i++) {
            patientPrescription[i] = patientPrescriptionProvider.get();
            patientPrescription[i].setEncounterId(patientEncounter.getId());
            patientPrescription[i].setUserId(currentUserSession.getId());
            patientPrescription[i].setReplaced(false);
            patientPrescription[i].setReplacementId(null);
            patientPrescription[i].setDateTaken(dateUtils.getCurrentDateTime());
        }

        patientPrescription[0].setMedicationName(viewModelPost.getPrescription1());
        patientPrescription[1].setMedicationName(viewModelPost.getPrescription2());
        patientPrescription[2].setMedicationName(viewModelPost.getPrescription3());
        patientPrescription[3].setMedicationName(viewModelPost.getPrescription4());
        patientPrescription[4].setMedicationName(viewModelPost.getPrescription5());

        patientPrescriptions.addAll(Arrays.asList(patientPrescription));
        return patientPrescriptions;
    }

    public List<IPatientEncounterVital> populatePatientVitals(UpdateVitalsModel updateVitalsModel, int currentUserId, int patientEncounterId) {
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
            patientEncounterVital.setDateTaken((dateUtils.getCurrentDateTimeString()));
            patientEncounterVital.setUserId(currentUserId);
            patientEncounterVital.setPatientEncounterId(patientEncounterId);
            patientEncounterVital.setVitalId(i + 1);
            patientEncounterVital.setVitalValue(vitals.get(i).floatValue());
            patientEncounterVitals.add(patientEncounterVital);
        }
        return patientEncounterVitals;
    }

    public CreateViewModelPost populateViewModelPost(int encounterId){
        CreateViewModelPost viewModelPost = new CreateViewModelPost();
        return viewModelPost;
    }

    public CreateViewModelGet populateViewModelGet(IPatient patient, IPatientEncounter patientEncounter, List<? extends IPatientEncounterVital> patientEncounterVitals) {
        CreateViewModelGet viewModelGet = new CreateViewModelGet();
        //patient
        viewModelGet.setpID(patient.getId());
        viewModelGet.setCity(patient.getCity());
        viewModelGet.setFirstName(patient.getFirstName());
        viewModelGet.setLastName(patient.getLastName());
        viewModelGet.setAge(dateUtils.calculateYears(patient.getAge()));
        viewModelGet.setSex(patient.getSex());
        //patient encounter
        viewModelGet.setChiefComplaint(patientEncounter.getChiefComplaint());
        viewModelGet.setWeeksPregnant(patientEncounter.getWeeksPregnant());
        //patient encounter vitals
        viewModelGet.setRespiratoryRate(getVitalOrNull(patientEncounterVitals.get(0)));
        viewModelGet.setHeartRate(getVitalOrNull(patientEncounterVitals.get(1)));
        viewModelGet.setTemperature(getVitalOrNull(patientEncounterVitals.get(2)));
        viewModelGet.setOxygenSaturation(getVitalOrNull(patientEncounterVitals.get(3)));
        viewModelGet.setHeightFeet(getVitalOrNull(patientEncounterVitals.get(4)));
        viewModelGet.setHeightInches(getVitalOrNull(patientEncounterVitals.get(5)));
        viewModelGet.setWeight(getVitalOrNull(patientEncounterVitals.get(6)));
        viewModelGet.setBloodPressureSystolic(getVitalOrNull(patientEncounterVitals.get(7)));
        viewModelGet.setBloodPressureDiastolic(getVitalOrNull(patientEncounterVitals.get(8)));

        return viewModelGet;
    }

    private Float getVitalOrNull(IPatientEncounterVital patientEncounterVital) {
        if (patientEncounterVital == null)
            return null;
        else
            return patientEncounterVital.getVitalValue();
    }
}
