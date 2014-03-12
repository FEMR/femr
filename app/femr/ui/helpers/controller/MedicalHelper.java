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

    public CreateViewModelGet populateViewModelGet(IPatient patient, IPatientEncounter patientEncounter, List<? extends IPatientPrescription> patientPrescriptions, Map<String, List<? extends IPatientEncounterVital>> patientEncounterVitalMap, Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap, Map<String, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap, Map<String, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap) {
        CreateViewModelGet viewModelGet = new CreateViewModelGet();
        //prescriptions
        List<String> dynamicViewMedications = new ArrayList<>();
        for (int filledPrescription = 0; filledPrescription < patientPrescriptions.size(); filledPrescription++) {
            if (patientPrescriptions.get(filledPrescription).getReplaced() != true) {
                dynamicViewMedications.add(patientPrescriptions.get(filledPrescription).getMedicationName());
            }
        }

        //general information of a patient in terms of their id, address, names and their sex
        viewModelGet.setpID(patient.getId());
        viewModelGet.setCity(patient.getCity());
        viewModelGet.setFirstName(patient.getFirstName());
        viewModelGet.setLastName(patient.getLastName());
        viewModelGet.setAge(dateUtils.getAge(patient.getAge()));
        viewModelGet.setSex(patient.getSex());
        //patient encounter in terms of patients pregnancy status and any main complaints
        viewModelGet.setChiefComplaint(patientEncounter.getChiefComplaint());
        viewModelGet.setWeeksPregnant(patientEncounter.getWeeksPregnant());
        viewModelGet.setIsPregnant(patientEncounter.getIsPregnant());

        //editable information - prescriptions
        viewModelGet.setPrescription1(getPrescriptionOrNull(1, dynamicViewMedications));
        viewModelGet.setPrescription2(getPrescriptionOrNull(2, dynamicViewMedications));
        viewModelGet.setPrescription3(getPrescriptionOrNull(3, dynamicViewMedications));
        viewModelGet.setPrescription4(getPrescriptionOrNull(4, dynamicViewMedications));
        viewModelGet.setPrescription5(getPrescriptionOrNull(5, dynamicViewMedications));
        //editable information - Treatment_fields
        viewModelGet.setAssessment(getTreatmentFieldOrNull("assessment", patientEncounterTreatmentMap));
        viewModelGet.setProblem1(getTreatmentProblemOrNull(1, patientEncounterTreatmentMap));
        viewModelGet.setProblem2(getTreatmentProblemOrNull(2, patientEncounterTreatmentMap));
        viewModelGet.setProblem3(getTreatmentProblemOrNull(3, patientEncounterTreatmentMap));
        viewModelGet.setProblem4(getTreatmentProblemOrNull(4, patientEncounterTreatmentMap));
        viewModelGet.setProblem5(getTreatmentProblemOrNull(5, patientEncounterTreatmentMap));
        viewModelGet.setTreatment(getTreatmentFieldOrNull("treatment", patientEncounterTreatmentMap));
        viewModelGet.setFamilyHistory(getTreatmentFieldOrNull("familyHistory", patientEncounterTreatmentMap));
        //editable information - Hpi_fields
        viewModelGet.setOnset(getHpiFieldOrNull("onset", patientEncounterHpiMap));
        viewModelGet.setSeverity(getHpiFieldOrNull("severity", patientEncounterHpiMap));
        viewModelGet.setRadiation(getHpiFieldOrNull("radiation", patientEncounterHpiMap));
        viewModelGet.setQuality(getHpiFieldOrNull("quality", patientEncounterHpiMap));
        viewModelGet.setProvokes(getHpiFieldOrNull("provokes", patientEncounterHpiMap));
        viewModelGet.setPalliates(getHpiFieldOrNull("palliates", patientEncounterHpiMap));
        viewModelGet.setTimeOfDay(getHpiFieldOrNull("timeOfDay", patientEncounterHpiMap));
        viewModelGet.setPhysicalExamination(getHpiFieldOrNull("physicalExamination", patientEncounterHpiMap));
        viewModelGet.setNarrative(getHpiFieldOrNull("narrative", patientEncounterHpiMap));
        //editable information - Pmh_fields
        viewModelGet.setMedicalSurgicalHistory(getPmhFieldOrNull("medicalSurgicalHistory", patientEncounterPmhMap));
        viewModelGet.setSocialHistory(getPmhFieldOrNull("socialHistory", patientEncounterPmhMap));
        viewModelGet.setCurrentMedication(getPmhFieldOrNull("currentMedication", patientEncounterPmhMap));
        viewModelGet.setFamilyHistory(getPmhFieldOrNull("familyHistory", patientEncounterPmhMap));
        //vitals
        viewModelGet.setRespiratoryRate(getIntVitalOrNull("respiratoryRate", patientEncounterVitalMap));
        viewModelGet.setHeartRate(getIntVitalOrNull("heartRate", patientEncounterVitalMap));
        viewModelGet.setHeightFeet(getIntVitalOrNull("heightFeet", patientEncounterVitalMap));
        viewModelGet.setHeightInches(getIntVitalOrNull("heightInches", patientEncounterVitalMap));
        viewModelGet.setBloodPressureSystolic(getIntVitalOrNull("bloodPressureSystolic", patientEncounterVitalMap));
        viewModelGet.setBloodPressureDiastolic(getIntVitalOrNull("bloodPressureDiastolic", patientEncounterVitalMap));
        viewModelGet.setTemperature(getFloatVitalOrNull("temperature", patientEncounterVitalMap));
        viewModelGet.setOxygenSaturation(getFloatVitalOrNull("oxygenSaturation", patientEncounterVitalMap));
        viewModelGet.setWeight(getFloatVitalOrNull("weight", patientEncounterVitalMap));
        viewModelGet.setGlucose(getFloatVitalOrNull("glucose", patientEncounterVitalMap));

        return viewModelGet;
    }

    //region **get encounter fields**
    public IPatientPrescription getPatientPrescription(int userId, int patientEncounterId, String name){
        IPatientPrescription patientPrescription = patientPrescriptionProvider.get();
        patientPrescription.setEncounterId(patientEncounterId);
        patientPrescription.setUserId(userId);
        patientPrescription.setReplaced(false);
        patientPrescription.setReplacementId(null);
        patientPrescription.setDateTaken(dateUtils.getCurrentDateTime());
        patientPrescription.setMedicationName(name);
        return patientPrescription;
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

    public IPatientEncounterPmhField getPatientEncounterPmhField(int userId, int patientEncounterId, IPmhField pmhField, String pmhValue) {
        IPatientEncounterPmhField patientEncounterPmhField = patientEncounterPmhFieldProvider.get();
        patientEncounterPmhField.setUserId(userId);
        patientEncounterPmhField.setPatientEncounterId(patientEncounterId);
        patientEncounterPmhField.setPmhField(pmhField);
        patientEncounterPmhField.setPmhFieldValue(pmhValue.trim());
        patientEncounterPmhField.setDateTaken(dateUtils.getCurrentDateTime());
        return patientEncounterPmhField;
    }

    public IPatientEncounterHpiField getPatientEncounterHpiField(int userId, int patientEncounterId, IHpiField hpiField, String hpiValue) {
        IPatientEncounterHpiField patientEncounterHpiField = patientEncounterHpiFieldProvider.get();
        patientEncounterHpiField.setUserId(userId);
        patientEncounterHpiField.setPatientEncounterId(patientEncounterId);
        patientEncounterHpiField.setHpiField(hpiField);
        patientEncounterHpiField.setHpiFieldValue(hpiValue);
        patientEncounterHpiField.setDateTaken(dateUtils.getCurrentDateTime());
        return patientEncounterHpiField;
    }
    //endregion

    //region **get value or get null**
    private String getPrescriptionOrNull(int number, List<String> patientPrescriptions) {
        if (patientPrescriptions.size() >= number) {
            return patientPrescriptions.get(number - 1);
        } else {
            return null;
        }
    }

    private Integer getIntVitalOrNull(String key, Map<String, List<? extends IPatientEncounterVital>> patientEncounterVitalMap) {
        if (patientEncounterVitalMap.containsKey(key)) {
            if (patientEncounterVitalMap.get(key).size() < 1) {
                return null;
            }
            return patientEncounterVitalMap.get(key).get(0).getVitalValue().intValue();
        }
        return null;
    }

    private Float getFloatVitalOrNull(String key, Map<String, List<? extends IPatientEncounterVital>> patientEncounterVitalMap) {
        if (patientEncounterVitalMap.containsKey(key)) {
            if (patientEncounterVitalMap.get(key).size() < 1) {
                return null;
            }
            return patientEncounterVitalMap.get(key).get(0).getVitalValue();
        }
        return null;
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
    //endregion
}
