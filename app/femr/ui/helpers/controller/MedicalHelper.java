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

    public IPatientEncounterHpiField getPatientEncounterHpiField(int userId, int patientEncounterId, IHpiField hpiField, String hpiValue){
        IPatientEncounterHpiField patientEncounterHpiField = patientEncounterHpiFieldProvider.get();
        patientEncounterHpiField.setUserId(userId);
        patientEncounterHpiField.setPatientEncounterId(patientEncounterId);
        patientEncounterHpiField.setHpiField(hpiField);
        patientEncounterHpiField.setHpiFieldValue(hpiValue);
        patientEncounterHpiField.setDateTaken(dateUtils.getCurrentDateTime());
        return patientEncounterHpiField;
    }

    public CreateViewModelPost populateViewModelPost(List<? extends IPatientPrescription> patientPrescriptions, Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap, Map<String, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap, Map<String, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap) {
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
        viewModelPost.setOnset(getHpiFieldOrNull("onset", patientEncounterHpiMap));
        viewModelPost.setSeverity(getHpiFieldOrNull("severity", patientEncounterHpiMap));
        viewModelPost.setRadiation(getHpiFieldOrNull("radiation", patientEncounterHpiMap));
        viewModelPost.setQuality(getHpiFieldOrNull("quality", patientEncounterHpiMap));
        viewModelPost.setProvokes(getHpiFieldOrNull("provokes", patientEncounterHpiMap));
        viewModelPost.setPalliates(getHpiFieldOrNull("palliates", patientEncounterHpiMap));
        viewModelPost.setTimeOfDay(getHpiFieldOrNull("timeOfDay", patientEncounterHpiMap));
        viewModelPost.setPhysicalExamination(getHpiFieldOrNull("physicalExamination", patientEncounterHpiMap));
        viewModelPost.setNarrative(getHpiFieldOrNull("narrative", patientEncounterHpiMap));

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
        viewModelGet.setAssessment(viewModelPost.getAssessment());
        viewModelGet.setProblem1(viewModelPost.getProblem1());
        viewModelGet.setProblem2(viewModelPost.getProblem2());
        viewModelGet.setProblem3(viewModelPost.getProblem3());
        viewModelGet.setProblem4(viewModelPost.getProblem4());
        viewModelGet.setProblem5(viewModelPost.getProblem5());
        viewModelGet.setTreatment(viewModelPost.getTreatment());
        viewModelGet.setFamilyHistory(viewModelPost.getFamilyHistory());
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
        viewModelGet.setMedicalSurgicalHistory(viewModelPost.getMedicalSurgicalHistory());
        viewModelGet.setSocialHistory(viewModelPost.getSocialHistory());
        viewModelGet.setCurrentMedication(viewModelPost.getCurrentMedication());
        viewModelGet.setFamilyHistory(viewModelPost.getFamilyHistory());


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

    private String getHpiFieldOrNull(String key, Map<String, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap) {
        if (patientEncounterHpiMap.containsKey(key)) {
            if (patientEncounterHpiMap.get(key).size() < 1) {
                return null;
            }
            return patientEncounterHpiMap.get(key).get(0).getHpiFieldValue().trim();
        }
        return null;
    }

    private String getPmhFieldOrNull(String key, Map<String, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap) {
        if (patientEncounterPmhMap.containsKey(key)) {
            if (patientEncounterPmhMap.get(key).size() < 1) {
                return null;
            }
            return patientEncounterPmhMap.get(key).get(0).getPmhFieldValue().trim();
        }
        return null;
    }


    private String getTreatmentFieldOrNull(String key, Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap) {
        if (patientEncounterTreatmentMap.containsKey(key)) {
            if (patientEncounterTreatmentMap.get(key).size() < 1) {
                return null;
            }
            return patientEncounterTreatmentMap.get(key).get(0).getTreatmentFieldValue().trim();
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
