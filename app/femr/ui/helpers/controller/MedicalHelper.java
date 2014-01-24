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
import java.util.Map;

public class MedicalHelper {
    private Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider;
    private Provider<IPatientEncounterHpiField> patientEncounterHpiFieldProvider;
    private Provider<IPatientEncounterPmhField> patientEncounterPmhFieldProvider;
    private Provider<IPatientPrescription> patientPrescriptionProvider;
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;

    @Inject
    public MedicalHelper(Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider, Provider<IPatientEncounterHpiField> patientEncounterHpiFieldProvider, Provider<IPatientEncounterPmhField> patientEncounterPmhFieldProvider, Provider<IPatientPrescription> patientPrescriptionProvider, Provider<IPatientEncounterVital> patientEncounterVitalProvider) {
        this.patientEncounterTreatmentFieldProvider = patientEncounterTreatmentFieldProvider;
        this.patientEncounterHpiFieldProvider = patientEncounterHpiFieldProvider;
        this.patientEncounterPmhFieldProvider = patientEncounterPmhFieldProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.patientPrescriptionProvider = patientPrescriptionProvider;
    }


    public List<IPatientEncounterHpiField> populateHpiFields(CreateViewModelPost viewModelPost, IPatientEncounter patientEncounter, CurrentUser currentUserSession) {
        List<IPatientEncounterHpiField> patientEncounterHpiFields = new ArrayList<>();
        IPatientEncounterHpiField[] patientEncounterHpiField = new IPatientEncounterHpiField[10];
        for (int i = 0; i < 10; i++) {
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
        patientEncounterHpiField[9].setHpiFieldValue(viewModelPost.getNarrative());

        patientEncounterHpiFields.addAll(Arrays.asList(patientEncounterHpiField));
        return patientEncounterHpiFields;
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

    public IPatientEncounterVital getPatientEncounterVital(int userId, int patientEncounterId, IVital vital, double vitalValue) {
        IPatientEncounterVital patientEncounterVital = patientEncounterVitalProvider.get();
        patientEncounterVital.setUserId(userId);
        patientEncounterVital.setPatientEncounterId(patientEncounterId);
        patientEncounterVital.setVital(vital);
        patientEncounterVital.setVitalValue((float) vitalValue);
        patientEncounterVital.setDateTaken(dateUtils.getCurrentDateTimeString());
        return patientEncounterVital;
    }

    public IPatientEncounterTreatmentField getPatientEncounterTreatmentField(int userId, int patientEncounterId, ITreatmentField treatmentField, String treatmentValue) {
        IPatientEncounterTreatmentField patientEncounterTreatmentField = patientEncounterTreatmentFieldProvider.get();
        patientEncounterTreatmentField.setUserId(userId);
        patientEncounterTreatmentField.setPatientEncounterId(patientEncounterId);
        patientEncounterTreatmentField.setTreatmentField(treatmentField);
        patientEncounterTreatmentField.setTreatmentFieldValue(treatmentValue.trim());
        patientEncounterTreatmentField.setDateTaken(dateUtils.getCurrentDateTime());
        return patientEncounterTreatmentField;
    }

    public IPatientEncounterPmhField getPatientEncounterPmhField(int userId, int patientEncounterId, IPmhField pmhField, String pmhValue){
        IPatientEncounterPmhField patientEncounterPmhField = patientEncounterPmhFieldProvider.get();
        patientEncounterPmhField.setUserId(userId);
        patientEncounterPmhField.setPatientEncounterId(patientEncounterId);
        patientEncounterPmhField.setPmhField(pmhField);
        patientEncounterPmhField.setPmhFieldValue(pmhValue.trim());
        patientEncounterPmhField.setDateTaken(dateUtils.getCurrentDateTime());
        return patientEncounterPmhField;
    }

    public CreateViewModelPost populateViewModelPost(List<? extends IPatientPrescription> patientPrescriptions, Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap, Map<Integer, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap, Map<String, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap) {
        //the maps are set in descending order based on when the treatment value was taken
        //this way, if the record has been edited we will see the most recent value
        CreateViewModelPost viewModelPost = new CreateViewModelPost();

        //prescriptions
        List<String> dynamicViewMedications = new ArrayList<>();
        for (int filledPrescription = 0; filledPrescription < patientPrescriptions.size(); filledPrescription++) {
            if (patientPrescriptions.get(filledPrescription).getReplaced() != true) {
                dynamicViewMedications.add(patientPrescriptions.get(filledPrescription).getMedicationName());
            }
        }
        viewModelPost.setPrescription1(getPrescriptionOrNull(1, dynamicViewMedications));
        viewModelPost.setPrescription2(getPrescriptionOrNull(2, dynamicViewMedications));
        viewModelPost.setPrescription3(getPrescriptionOrNull(3, dynamicViewMedications));
        viewModelPost.setPrescription4(getPrescriptionOrNull(4, dynamicViewMedications));
        viewModelPost.setPrescription5(getPrescriptionOrNull(5, dynamicViewMedications));

        //treatment fields
        viewModelPost.setAssessment(getTreatmentFieldOrNull("assessment", patientEncounterTreatmentMap));

        viewModelPost.setProblem1(getTreatmentProblemOrNull(1, patientEncounterTreatmentMap));
        viewModelPost.setProblem2(getTreatmentProblemOrNull(2, patientEncounterTreatmentMap));
        viewModelPost.setProblem3(getTreatmentProblemOrNull(3, patientEncounterTreatmentMap));
        viewModelPost.setProblem4(getTreatmentProblemOrNull(4, patientEncounterTreatmentMap));
        viewModelPost.setProblem5(getTreatmentProblemOrNull(5, patientEncounterTreatmentMap));
        viewModelPost.setTreatment(getTreatmentFieldOrNull("treatment", patientEncounterTreatmentMap));
        viewModelPost.setFamilyHistory(getTreatmentFieldOrNull("familyHistory", patientEncounterTreatmentMap));
        //hpi fields
        viewModelPost.setOnset(getHpiFieldOrNull(1, patientEncounterHpiMap));
        viewModelPost.setOnsetTime(getHpiFieldOrNull(2, patientEncounterHpiMap));
        viewModelPost.setSeverity(getHpiFieldOrNull(3, patientEncounterHpiMap));
        viewModelPost.setRadiation(getHpiFieldOrNull(4, patientEncounterHpiMap));
        viewModelPost.setQuality(getHpiFieldOrNull(5, patientEncounterHpiMap));
        viewModelPost.setProvokes(getHpiFieldOrNull(6, patientEncounterHpiMap));
        viewModelPost.setPalliates(getHpiFieldOrNull(7, patientEncounterHpiMap));
        viewModelPost.setTimeOfDay(getHpiFieldOrNull(8, patientEncounterHpiMap));
        viewModelPost.setPhysicalExamination(getHpiFieldOrNull(9, patientEncounterHpiMap));
        viewModelPost.setNarrative(getHpiFieldOrNull(10, patientEncounterHpiMap));

        //pmh fields
        viewModelPost.setMedicalSurgicalHistory(getPmhFieldOrNull("medicalSurgicalHistory", patientEncounterPmhMap));
        viewModelPost.setSocialHistory(getPmhFieldOrNull("socialHistory", patientEncounterPmhMap));
        viewModelPost.setCurrentMedication(getPmhFieldOrNull("currentMedication", patientEncounterPmhMap));
        viewModelPost.setFamilyHistory(getPmhFieldOrNull("familyHistory", patientEncounterPmhMap));

        return viewModelPost;
    }

    public CreateViewModelGet populateViewModelGet(IPatient patient, IPatientEncounter patientEncounter, CreateViewModelPost viewModelPost) {
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
        //editable information - prescriptions
        viewModelGet.setPrescription1(viewModelPost.getPrescription1());
        viewModelGet.setPrescription2(viewModelPost.getPrescription2());
        viewModelGet.setPrescription3(viewModelPost.getPrescription3());
        viewModelGet.setPrescription4(viewModelPost.getPrescription4());
        viewModelGet.setPrescription5(viewModelPost.getPrescription5());
        //editable information - Treatment_fields
        viewModelGet.setAssessment(viewModelPost.getAssessment().trim());
        viewModelGet.setProblem1(viewModelPost.getProblem1().trim());
        viewModelGet.setProblem2(viewModelPost.getProblem2().trim());
        viewModelGet.setProblem3(viewModelPost.getProblem3().trim());
        viewModelGet.setProblem4(viewModelPost.getProblem4().trim());
        viewModelGet.setProblem5(viewModelPost.getProblem5().trim());
        viewModelGet.setTreatment(viewModelPost.getTreatment().trim());
        viewModelGet.setFamilyHistory(viewModelPost.getFamilyHistory().trim());
        //editable information - Hpi_fields
        viewModelGet.setOnset(viewModelPost.getOnset());
        viewModelGet.setSeverity(viewModelPost.getSeverity());
        viewModelGet.setRadiation(viewModelPost.getRadiation());
        viewModelGet.setQuality(viewModelPost.getQuality());
        viewModelGet.setProvokes(viewModelPost.getProvokes());
        viewModelGet.setPalliates(viewModelPost.getPalliates());
        viewModelGet.setTimeOfDay(viewModelPost.getTimeOfDay());
        viewModelGet.setPhysicalExamination(viewModelPost.getPhysicalExamination());
        viewModelGet.setNarrative(viewModelPost.getNarrative());

        //editable information - Pmh_fields
        viewModelGet.setMedicalSurgicalHistory(viewModelPost.getMedicalSurgicalHistory().trim());
        viewModelGet.setSocialHistory(viewModelPost.getSocialHistory().trim());
        viewModelGet.setCurrentMedication(viewModelPost.getCurrentMedication().trim());
        viewModelGet.setFamilyHistory(viewModelPost.getFamilyHistory().trim());


        return viewModelGet;
    }

    private String getPrescriptionOrNull(int number, List<String> patientPrescriptions) {
        if (patientPrescriptions.size() >= number) {
            return patientPrescriptions.get(number - 1);
        } else {
            return null;
        }
    }

    private Float getVitalOrNull(IPatientEncounterVital patientEncounterVital) {
        if (patientEncounterVital == null)
            return null;
        else
            return patientEncounterVital.getVitalValue();
    }

    private String getHpiFieldOrNull(int key, Map<Integer, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap) {
        if (patientEncounterHpiMap.containsKey(key)) {
            if (patientEncounterHpiMap.get(key).size() < 1) {
                return null;
            }
            return patientEncounterHpiMap.get(key).get(0).getHpiFieldValue();
        }
        return null;
    }

    private String getPmhFieldOrNull(String key, Map<String, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap) {
        if (patientEncounterPmhMap.containsKey(key)) {
            if (patientEncounterPmhMap.get(key).size() < 1) {
                return null;
            }
            return patientEncounterPmhMap.get(key).get(0).getPmhFieldValue();
        }
        return null;
    }


    private String getTreatmentFieldOrNull(String key, Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap) {
        if (patientEncounterTreatmentMap.containsKey(key)) {
            if (patientEncounterTreatmentMap.get(key).size() < 1) {
                return null;
            }
            return patientEncounterTreatmentMap.get(key).get(0).getTreatmentFieldValue();
        }
        return null;
    }

    private String getTreatmentProblemOrNull(int problem, Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap) {
        if (patientEncounterTreatmentMap.containsKey("problem")) {
            if (patientEncounterTreatmentMap.get("problem").size() >= problem) {
                return patientEncounterTreatmentMap.get("problem").get(problem - 1).getTreatmentFieldValue();
            }
            return null;
        }
        return null;
    }
}
