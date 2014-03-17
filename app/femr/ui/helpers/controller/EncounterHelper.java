package femr.ui.helpers.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.IUserService;
import femr.common.models.*;
import femr.ui.models.search.CreateEncounterViewModel;
import femr.util.DataStructure.VitalMultiMap;
import femr.util.DataStructure.Pair;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EncounterHelper {
    private Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider;
    private Provider<IPatientEncounterHpiField> patientEncounterHpiFieldProvider;
    private Provider<IPatientEncounterPmhField> patientEncounterPmhFieldProvider;
    private Provider<IPatientPrescription> patientPrescriptionProvider;
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private IUserService userService;
    private ISearchService searchService;

    @Inject
    public EncounterHelper(Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider, Provider<IPatientEncounterHpiField> patientEncounterHpiFieldProvider, Provider<IPatientEncounterPmhField> patientEncounterPmhFieldProvider, Provider<IPatientPrescription> patientPrescriptionProvider, Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                           IUserService userService, ISearchService searchService) {
        this.patientEncounterTreatmentFieldProvider = patientEncounterTreatmentFieldProvider;
        this.patientEncounterHpiFieldProvider = patientEncounterHpiFieldProvider;
        this.patientEncounterPmhFieldProvider = patientEncounterPmhFieldProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.patientPrescriptionProvider = patientPrescriptionProvider;
        this.userService = userService; // Used to get the username associated with the values
        this.searchService = searchService; // used to retreive the replacement pharmacy name
    }

    public CreateEncounterViewModel populateViewModelGet(IPatient patient, IPatientEncounter patientEncounter, List<? extends IPatientPrescription> patientPrescriptions, VitalMultiMap patientEncounterVitalMap, Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap, Map<String, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap, Map<String, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap) {
        CreateEncounterViewModel viewModelGet = new CreateEncounterViewModel();
        //prescriptions

        // Create a list of pairs that have medication and the replacement medication if it exist for each entry
        List<Pair<String,String>> medicationAndReplacement = new LinkedList<>();
        List<Integer> ignoreList = new ArrayList<>(); // list used to make sure we ignore duplicate entries

        for (IPatientPrescription patientPrescription : patientPrescriptions) {
            // check if the medication was replaced if so save it and the replacement medications name
            if (patientPrescription.getReplaced()) {
                medicationAndReplacement.add(new Pair<String, String>(patientPrescription.getMedicationName(),
                        getPrescriptionNameById(patientPrescription.getReplacementId())));
                // add the replaced prescription id to the ignore list so we don't added it twice
                ignoreList.add(patientPrescription.getReplacementId());
            } else if (!ignoreList.contains(patientPrescription.getId())) {
                // if the medication is not in the ignore list
                medicationAndReplacement.add(new Pair<String, String>(patientPrescription.getMedicationName(), ""));
            }
        }


        // Set the doctor's first and last name
        viewModelGet.setDoctorFirstName(getDoctorFirstNameOrNull(patientEncounter.getId()));  //this.userService.findById(patientEncounter.getUserId()).getFirstName()
        viewModelGet.setDoctorLastName(getDoctorLastNameOrNull(patientEncounter.getId()));
        // set the pharmacist first and last name
        viewModelGet.setPharmacistFirstName(null);
        viewModelGet.setPharmacistLastName(null);

        //patient
        viewModelGet.setpID(patient.getId());
        viewModelGet.setCity(patient.getCity());
        viewModelGet.setFirstName(patient.getFirstName());
        viewModelGet.setLastName(patient.getLastName());
        viewModelGet.setAge(dateUtils.getAge(patient.getAge()));
        viewModelGet.setSex(patient.getSex());
        //patient encounter
        viewModelGet.setChiefComplaint(patientEncounter.getChiefComplaint());
        viewModelGet.setWeeksPregnant(patientEncounter.getWeeksPregnant());
        //editable information - prescriptions
        viewModelGet.setPrescription1(getOriginalPrescriptionOrNull(1, medicationAndReplacement));
        viewModelGet.setPrescription2(getOriginalPrescriptionOrNull(2, medicationAndReplacement));
        viewModelGet.setPrescription3(getOriginalPrescriptionOrNull(3, medicationAndReplacement));
        viewModelGet.setPrescription4(getOriginalPrescriptionOrNull(4, medicationAndReplacement));
        viewModelGet.setPrescription5(getOriginalPrescriptionOrNull(5, medicationAndReplacement));
        //viewModelGet.setMedications(viewMedications);

        // sets the Medication and Replacement Pair list
        viewModelGet.setMedicationAndReplacement(medicationAndReplacement);
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
        viewModelGet.setVitalList(patientEncounterVitalMap);
//        viewModelGet.setRespiratoryRate(getIntVitalOrNull("respiratoryRate", patientEncounterVitalMap));
//        viewModelGet.setHeartRate(getIntVitalOrNull("heartRate", patientEncounterVitalMap));
//        viewModelGet.setHeightFeet(getIntVitalOrNull("heightFeet", patientEncounterVitalMap));
//        viewModelGet.setHeightInches(getIntVitalOrNull("heightInches", patientEncounterVitalMap));
//        viewModelGet.setBloodPressureSystolic(getIntVitalOrNull("bloodPressureSystolic", patientEncounterVitalMap));
//        viewModelGet.setBloodPressureDiastolic(getIntVitalOrNull("bloodPressureDiastolic", patientEncounterVitalMap));
//        viewModelGet.setTemperature(getFloatVitalOrNull("temperature", patientEncounterVitalMap));
//        viewModelGet.setOxygenSaturation(getFloatVitalOrNull("oxygenSaturation", patientEncounterVitalMap));
//        viewModelGet.setWeight(getFloatVitalOrNull("weight", patientEncounterVitalMap));
//        viewModelGet.setGlucose(getFloatVitalOrNull("glucose", patientEncounterVitalMap));
        //Medication
        return viewModelGet;
    }


    /**
     * Gets the first name of the doctor given an encounter ID.  If there are multiple doctors only display latest
     * @param encounterId The ID of the encounter you want a doctor name from
     * @return The First name of the doctor as a string
     */
    private String getDoctorFirstNameOrNull(int encounterId) {
        // Gets the doctor for the patient encounter.
        // this is done by first looking at the Hpi field for a UserId  if empty then look in Pmh, then treatment
        ServiceResponse<IPatientEncounterHpiField> patientEncounterHpiFieldResponse = searchService.findDoctorIdByEncounterIdInHpiField(encounterId);
        IPatientEncounterHpiField patientEncounterHpiField;
        int doctorID;
        String firstName = null;
        String tempName = null;
        DateTime dateTaken = null;
        //check for errors
        if(!patientEncounterHpiFieldResponse.hasErrors()) {
            patientEncounterHpiField = patientEncounterHpiFieldResponse.getResponseObject();
            doctorID = patientEncounterHpiField.getUserId();
            firstName = this.userService.findById(doctorID).getFirstName().trim();
            dateTaken = patientEncounterHpiField.getDateTaken();
        }

        // Assume that HPI failed or was empty, try the Pmh field instead
        ServiceResponse<IPatientEncounterPmhField> patientEncounterPmhFieldResponse = searchService.findDoctorIdByEncounterIdInPmhField(encounterId);
        IPatientEncounterPmhField patientEncounterPmhField;
        if(!patientEncounterPmhFieldResponse.hasErrors()) {
            patientEncounterPmhField = patientEncounterPmhFieldResponse.getResponseObject();
            doctorID = patientEncounterPmhField.getUserId();
            tempName = this.userService.findById(doctorID).getFirstName().trim();
            // check to see if this is newer then the previous entry if so use it instead
            if(firstName != null && patientEncounterPmhField.getDateTaken().isAfter(dateTaken)) {
                firstName = tempName;
                dateTaken = patientEncounterPmhField.getDateTaken();
            }
            if(firstName == null) {
                firstName = tempName;
                dateTaken = patientEncounterPmhField.getDateTaken();
            }
        }

        ServiceResponse<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldResponse = searchService.findDoctorIdByEncounterIdInTreatmentField(encounterId);
        IPatientEncounterTreatmentField patientEncounterTreatmentField;
        if(!patientEncounterTreatmentFieldResponse.hasErrors()) {
            patientEncounterTreatmentField = patientEncounterTreatmentFieldResponse.getResponseObject();
            doctorID = patientEncounterTreatmentField.getUserId();
            tempName = this.userService.findById(doctorID).getFirstName().trim();
            // check to see if this is newer then the previous entry if so use it instead
            if(firstName != null && patientEncounterTreatmentField.getDateTaken().isAfter(dateTaken)) {
                firstName = tempName;
            }
            if(firstName == null) {
                firstName = tempName;
            }
        }
        // if we reach this point we failed to find a userid in any of the medical fields so return null
        return firstName;
    }

    /**
     * Gets the last name of the doctor given an encounter ID.  If there are multiple doctors only display latest
     * @param encounterId The ID of the encounter you want a doctors name from
     * @return The Last name of the doctor as a string
     */
    private String getDoctorLastNameOrNull(int encounterId) {
        // Gets the doctor for the patient encounter.
        ServiceResponse<IPatientEncounterHpiField> patientEncounterHpiFieldResponse = searchService.findDoctorIdByEncounterIdInHpiField(encounterId);
        IPatientEncounterHpiField patientEncounterHpiField;
        int doctorID;
        String lastName = null;
        String tempName = null;
        DateTime dateTaken = null;
        //check for errors
        if(!patientEncounterHpiFieldResponse.hasErrors()) {
            patientEncounterHpiField = patientEncounterHpiFieldResponse.getResponseObject();
            doctorID = patientEncounterHpiField.getUserId();
            lastName = this.userService.findById(doctorID).getLastName().trim();
            dateTaken = patientEncounterHpiField.getDateTaken();
        }

        // get the Pmh data
        ServiceResponse<IPatientEncounterPmhField> patientEncounterPmhFieldResponse = searchService.findDoctorIdByEncounterIdInPmhField(encounterId);
        IPatientEncounterPmhField patientEncounterPmhField;
        if(!patientEncounterPmhFieldResponse.hasErrors()) {
            patientEncounterPmhField = patientEncounterPmhFieldResponse.getResponseObject();
            doctorID = patientEncounterPmhField.getUserId();
            tempName = this.userService.findById(doctorID).getLastName().trim();
            // check to see if this is newer than the previous entry, if so use it instead
            if(lastName != null && patientEncounterPmhField.getDateTaken().isAfter(dateTaken)) {
                lastName = tempName;
                dateTaken = patientEncounterPmhField.getDateTaken();
            }
            if(lastName == null) {
                lastName = tempName;
                dateTaken = patientEncounterPmhField.getDateTaken();
            }
        }

        ServiceResponse<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldResponse = searchService.findDoctorIdByEncounterIdInTreatmentField(encounterId);
        IPatientEncounterTreatmentField patientEncounterTreatmentField;
        if(!patientEncounterTreatmentFieldResponse.hasErrors()) {
            patientEncounterTreatmentField = patientEncounterTreatmentFieldResponse.getResponseObject();
            doctorID = patientEncounterTreatmentField.getUserId();
            tempName = this.userService.findById(doctorID).getLastName().trim();
            // check to see if this is newer then the previous entry if so use it instead
            if(lastName != null && patientEncounterTreatmentField.getDateTaken().isAfter(dateTaken)) {
                lastName = tempName;
            }
            if(lastName == null) {
                lastName = tempName;
            }
        }
        return lastName;
    }


    //region **get value or get null**
    private String getOriginalPrescriptionOrNull(int number, List<Pair<String, String>> patientPrescriptions) {
        if (patientPrescriptions.size() >= number) {
            return patientPrescriptions.get(number - 1).getKey();
        } else {
            return null;
        }
    }

    /**
     * given the id for a prescription the function will return its name.
     * used for getting the name of the replacement prescription
     * @param id the id of the prescription
     * @return The name of the Prescription as a string
     */
    private String getPrescriptionNameById(int id) {
        ServiceResponse<IPatientPrescription> patientPrescriptionServiceResponse = searchService.findPatientPrescriptionById(id);
        IPatientPrescription patientPrescription = patientPrescriptionServiceResponse.getResponseObject();
        return patientPrescription.getMedicationName();
    }

    // get the replaced boolean for a perscription or return false by default
    private Boolean getReplacedOrNull(int number, List<? extends IPatientPrescription> patientPrescriptions){
        if(patientPrescriptions.size() >= number) {
            return patientPrescriptions.get(number - 1).getReplaced();
        }
        else{
            return false;
        }
    }
//gets the value of the hpi field, or return null if they are invalid
    private String getHpiFieldOrNull(String key, Map<String, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap) {
        if (patientEncounterHpiMap.containsKey(key)) {
            if (patientEncounterHpiMap.get(key).size() < 1) {
                return null;
            }
            return patientEncounterHpiMap.get(key).get(0).getHpiFieldValue().trim();
        }
        return null;
    }

    ////gets the value of the PMH field, or return null if they are invalid
    private String getPmhFieldOrNull(String key, Map<String, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap) {
        if (patientEncounterPmhMap.containsKey(key)) {
            if (patientEncounterPmhMap.get(key).size() < 1) {
                return null;
            }
            return patientEncounterPmhMap.get(key).get(0).getPmhFieldValue().trim();
        }
        return null;
    }

    //gets the value of the treatment field, or return null if they are invalid
    private String getTreatmentFieldOrNull(String key, Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap) {
        if (patientEncounterTreatmentMap.containsKey(key)) {
            if (patientEncounterTreatmentMap.get(key).size() < 1) {
                return null;
            }
            return patientEncounterTreatmentMap.get(key).get(0).getTreatmentFieldValue().trim();
        }
        return null;
    }

    ////gets the value of the Treatment Problem field, or return null if they are invalid
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
