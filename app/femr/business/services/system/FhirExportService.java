package femr.business.services.system;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.BundleBuilder;
import com.google.inject.Inject;
import femr.business.helpers.FhirCodeableConcepts;
import femr.business.services.core.IFhirExportService;
import femr.data.daos.core.IEncounterRepository;
import femr.data.daos.core.IPatientEncounterVitalRepository;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.IPatient;
import femr.data.models.core.IPatientEncounter;
import femr.data.models.core.IPatientEncounterVital;
import femr.data.daos.core.IPrescriptionRepository;
import femr.data.models.core.*;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r5.model.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.List;

import java.util.*;

/**
 * Converts between fEMR data models and FHIR models.
 */
public class FhirExportService implements IFhirExportService {

    private static final FhirContext fhirContext = FhirContext.forR5();


    IPatientRepository patientRepository;
    IEncounterRepository encounterRepository;
    IPrescriptionRepository prescriptionRepository;
    IPatientEncounterVitalRepository patientEncounterVitalRepository;
    private final String kitId;

    @Inject
    public FhirExportService(IPatientRepository patientRepository, IEncounterRepository encounterRepository, IPrescriptionRepository prescriptionRepository, IPatientEncounterVitalRepository patientEncounterVitalRepository, String kitId) {
        this.patientRepository = patientRepository;
        this.encounterRepository = encounterRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.kitId = kitId;
    }

    /**
     * @param patientId patient ID to export
     * @return JSON encoded string of FHIR bundle.
     */
    @Override
    public String exportPatient(int patientId) {
        return toJson(buildPatientBundle(patientId));
    }
  
    private BundleBuilder buildPatientBundle(int patientId) {

        String fhirPatientId = String.format("%s_%s",kitId, patientId);

        BundleBuilder bundleBuilder = new BundleBuilder(fhirContext);

        bundleBuilder.setType("document");

        // Create bundle entry
        IBase entry = bundleBuilder.addEntry();

        Composition composition = new Composition();

        bundleBuilder.addToEntry(entry, "resource", composition);

        addPatientData(bundleBuilder, patientId, fhirPatientId);

        for (IPatientEncounter encounter: encounterRepository.retrievePatientEncountersByPatientIdAsc(patientId)) {
            List<? extends IPatientEncounterVital> vitals = patientEncounterVitalRepository.getAllByEncounter(encounter.getId());
            addRespirationRate(bundleBuilder, fhirPatientId, vitals);
            addBodyTemp(bundleBuilder, fhirPatientId, vitals);
            addBodyWeight(bundleBuilder, fhirPatientId, vitals);
            addBloodPressure(bundleBuilder, fhirPatientId, vitals);
            addHeartRate(bundleBuilder, fhirPatientId, vitals);
            addOxygenSaturation(bundleBuilder, fhirPatientId, vitals);
            addBodyHeight(bundleBuilder, fhirPatientId, vitals);
            addWeeksPregnant(bundleBuilder, fhirPatientId, vitals);
            addBloodGlucose(bundleBuilder, fhirPatientId, vitals);
        }

        return bundleBuilder;
    }

    /**
     * Adds Weeks Pregnant to FHIR bundle
     * @param bundleBuilder the bundle builder for observation to be added to
     * @param fhirPatientId patient ID in FHIR format (<Global_Kit_ID>_<Local DB ID>)
     * @param vitals list of all the patient's vitals
     */
    private void addBloodGlucose(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {
        for(IPatientEncounterVital vital: vitals) {
            if(vital.getVital().getName().equals("glucose")) {
                Observation observation = addObservationForPatient(bundleBuilder, fhirPatientId, vital.getId());
                observation.setCode(FhirCodeableConcepts.getBloodGlucoseConceptMassPerVolume());
                observation.setEffective(convertFEMRDateTime(vital.getDateTaken()));
                observation.setValue(FhirCodeableConcepts.getmgPerdLQuantity(vital.getVitalValue()));
            }
        }
    }


    /**
     * Adds Weeks Pregnant to FHIR bundle
     * @param bundleBuilder the bundle builder for observation to be added to
     * @param fhirPatientId patient ID in FHIR format (<Global_Kit_ID>_<Local DB ID>)
     * @param vitals list of all the patient's vitals
     */
    private void addWeeksPregnant(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {
        for(IPatientEncounterVital vital: vitals) {
            if(vital.getVital().getName().equals("weeksPregnant")) {
                Observation observation = addObservationForPatient(bundleBuilder, fhirPatientId, vital.getId());
                observation.setCode(FhirCodeableConcepts.getGestationalAge());
                observation.setEffective(convertFEMRDateTime(vital.getDateTaken()));
                observation.setValue(FhirCodeableConcepts.getTimeWeeksQuantity(vital.getVitalValue()));
            }
        }
    }

    /**
     * Adds Patient Body Height to bundle
     * @param bundleBuilder the bundle builder for observation to be added to
     * @param fhirPatientId patient ID in FHIR format (<Global_Kit_ID>_<Local DB ID>)
     * @param vitals list of all the patient's vitals
     */
    private void addBodyHeight(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {

        IPatientEncounterVital heightFeetVital = null;
        IPatientEncounterVital heightInchesVital = null;

        for(IPatientEncounterVital vital: vitals) {
            if(vital.getVital().getName().equals("heightFeet")) {
                heightFeetVital = vital;
            }

            if (vital.getVital().getName().equals("heightInches")) {
                heightInchesVital = vital;
            }
        }

        if (heightFeetVital == null) {
            return;
        }

        // We will use the heightFeet as the primary key, since for some reason it's broken up into 2.
        Observation observation = addObservationForPatient(bundleBuilder, fhirPatientId, heightFeetVital.getId());
        observation.setCode(FhirCodeableConcepts.getBodyHeight());
        observation.setEffective(convertFEMRDateTime(heightFeetVital.getDateTaken()));


        Float heightInches = heightFeetVital.getVitalValue() * 12;

        // This should basically always be the case, but we're just being careful.
        if (heightInchesVital != null) {
            heightInches += heightInchesVital.getVitalValue();
        }

        observation.setValue(FhirCodeableConcepts.getQuantityInches(heightInches));

    }

    /**
     * Adds O2 Sat to bundle
     * @param bundleBuilder the bundle builder for observation to be added to
     * @param fhirPatientId patient ID in FHIR format (<Global_Kit_ID>_<Local DB ID>)
     * @param vitals list of all the patient's vitals
     */
    private void addOxygenSaturation(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {
        for(IPatientEncounterVital vital: vitals) {
            if(vital.getVital().getName().equals("oxygenSaturation")) {
                Observation observation = addObservationForPatient(bundleBuilder, fhirPatientId, vital.getId());
                observation.setCode(FhirCodeableConcepts.getOxygenSaturation());
                observation.setEffective(convertFEMRDateTime(vital.getDateTaken()));
                observation.setValue(FhirCodeableConcepts.getOxygenSaturationQuantity(vital.getVitalValue()));
            }
        }
    }

    /**
     *
     * Adds heart rate to bundle
     * @param bundleBuilder the bundle builder for observation to be added to
     * @param fhirPatientId patient ID in FHIR format (<Global_Kit_ID>_<Local DB ID>)
     * @param vitals list of all the patient's vitals
     */
    private void addHeartRate(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {
        for(IPatientEncounterVital vital: vitals) {
            if(vital.getVital().getName().equals("heartRate")) {
                Observation observation = addObservationForPatient(bundleBuilder, fhirPatientId, vital.getId());
                observation.setCode(FhirCodeableConcepts.getHeartRate());
                observation.setEffective(convertFEMRDateTime(vital.getDateTaken()));
                observation.setValue(FhirCodeableConcepts.getBPMQuantity(vital.getVitalValue()));
            }
        }
    }

    /**
     *
     * Adds blood pressure to bundle
     * @param bundleBuilder the bundle builder for observation to be added to
     * @param fhirPatientId patient ID in FHIR format (<Global_Kit_ID>_<Local DB ID>)
     * @param vitals list of all the patient's vital
     */
    private void addBloodPressure(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {

        for(IPatientEncounterVital vital: vitals) {
            if(vital.getVital().getName().equals("bloodPressureSystolic")) {
                Observation observation = addObservationForPatient(bundleBuilder, fhirPatientId, vital.getId());
                observation.setCode(FhirCodeableConcepts.getBloodPressureSystolic());
                observation.setEffective(convertFEMRDateTime(vital.getDateTaken()));
                observation.setValue(FhirCodeableConcepts.getQuantityMmHG(vital.getVitalValue()));
            }
            if(vital.getVital().getName().equals("bloodPressureDiastolic")){
                Observation observation = addObservationForPatient(bundleBuilder, fhirPatientId, vital.getId());
                observation.setCode(FhirCodeableConcepts.getBloodPressureDiastolic());
                observation.setEffective(convertFEMRDateTime(vital.getDateTaken()));
                observation.setValue(FhirCodeableConcepts.getQuantityMmHG(vital.getVitalValue()));

            }
        }
    }

    /**
     * Adds body temperature to bundle
     * @param bundleBuilder the bundle builder for observation to be added to
     * @param fhirPatientId patient ID in FHIR format (<Global_Kit_ID>_<Local DB ID>)
     * @param vitals list of all the patient's vital
     */
    private void addBodyTemp(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {

        for(IPatientEncounterVital vital: vitals) {
            if(vital.getVital().getName().equals("temperature")) {
                Observation observation = addObservationForPatient(bundleBuilder, fhirPatientId, vital.getId());
                observation.setCode(FhirCodeableConcepts.getBodyTemperature());
                observation.setEffective(convertFEMRDateTime(vital.getDateTaken()));
                observation.setValue(FhirCodeableConcepts.getCelsius(vital.getVitalValue()));

            }
        }
    }

    /**
     * Adds body weight to bundle
     * @param bundleBuilder the bundle builder for observation to be added to
     * @param fhirPatientId patient ID in FHIR format (<Global_Kit_ID>_<Local DB ID>)
     * @param vitals list of all the patient's vital
     */
    private void addBodyWeight(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {

        for(IPatientEncounterVital vital : vitals){
            if(vital.getVital().getName().equals("weight")){
                Observation observation = addObservationForPatient(bundleBuilder, fhirPatientId, vital.getId());
                observation.setCode(FhirCodeableConcepts.getBodyWeight());
                observation.setEffective(convertFEMRDateTime(vital.getDateTaken()));
                observation.setValue(FhirCodeableConcepts.getWeightPounds(vital.getVitalValue()));

            }

        }

    }

    private void addRespirationRate(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {
        for (IPatientEncounterVital vital : vitals) {
            if (vital.getVital().getName().equals("respiratoryRate")) {
                Observation observation = addObservationForPatient(bundleBuilder, fhirPatientId, vital.getId());

                // From https://www.hl7.org/fhir/observation-example-respiratory-rate.json.html
                observation.setCode(FhirCodeableConcepts.getRespiratoryRate());
                observation.setSubject(new Reference(fhirPatientId));
                observation.setEffective(convertFEMRDateTime(vital.getDateTaken()));
                observation.setValue(FhirCodeableConcepts.getBreathsPerMinute(vital.getVitalValue()));
            }
        }

    }

    /**
     * @param bundleBuilder bundle for patient resource to be added to
     * @param patientId Local DB ID of patient to be added
     * @param fhirPatientId FHIR String of patient to be added
     */
    private void addPatientData(BundleBuilder bundleBuilder, int patientId, String fhirPatientId) {

        IPatient patient = patientRepository.retrievePatientById(patientId);

        Patient fhirPatient = new Patient();

        IBase entry = bundleBuilder.addEntry();
        bundleBuilder.addToEntry(entry, "resource", fhirPatient);

        fhirPatient.setGender(getAdministrativeGender(patient.getSex()));

        fhirPatient.setId(fhirPatientId);


        HumanName name = new HumanName();
        name.setGiven(Collections.singletonList(new StringType(patient.getFirstName())));
        name.setFamily(patient.getLastName());
        fhirPatient.setName(Collections.singletonList(name));

        if (patient.getPhoneNumber() != null) {
            ContactPoint contactPoint = new ContactPoint();
            contactPoint.setSystem(ContactPoint.ContactPointSystem.PHONE);
            contactPoint.setValue(patient.getPhoneNumber());
            fhirPatient.setTelecom(Collections.singletonList(contactPoint));
        }

        // Assuming that age means birthDate
        fhirPatient.setBirthDate(patient.getAge());

        if (patient.getAddress() != null) {
            Address address = new Address();
            address.setText(patient.getAddress());
            fhirPatient.setAddress(Collections.singletonList(address));
        }

        // Gather all encounters
        List<? extends IPatientEncounter> encounters = encounterRepository.retrievePatientEncountersByPatientIdAsc(patientId);

        // To ensure we don't add duplicate practitioners
        Set<Integer> addedUserIds = new HashSet<>();

        // Similarly, but for medication IDs
        Set<Integer> addedMedIds = new HashSet<>();

        for (IPatientEncounter encounter : encounters) {
            List<? extends IPatientPrescription> prescriptions = prescriptionRepository.retrieveUnreplacedPrescriptionsByEncounterId(encounter.getId());

            for (IPatientPrescription prescription : prescriptions) {
                addMedicationRequestForPrescription(bundleBuilder, prescription, patientId, addedMedIds);
            }

            IUser nurse = encounter.getNurse();
            IUser physician = encounter.getDoctor();
            IUser pharmacist = encounter.getPharmacist();

            // If nurse is present and not yet added, add them
            if (nurse != null && !addedUserIds.contains(nurse.getId())) {
                addPractitionerData(bundleBuilder, nurse);
                addedUserIds.add(nurse.getId());
            }

            // If physician is present and not yet added, add them
            if (physician != null && !addedUserIds.contains(physician.getId())) {
                addPractitionerData(bundleBuilder, physician);
                addedUserIds.add(physician.getId());
            }

            // If pharmacist is present and not yet added, add them
            if (pharmacist != null && !addedUserIds.contains(pharmacist.getId())) {
                addPractitionerData(bundleBuilder, pharmacist);
                addedUserIds.add(pharmacist.getId());
            }
        }


        // city is omitted as it is where the patient is treated and not
        // a property of the patient itself. So it will go with the encounter model.

        // TODO: add photo

    }

    private void addMedicationRequestForPrescription(BundleBuilder bundleBuilder, IPatientPrescription prescription, int patientId, Set<Integer> addedMedIds) {
        IMedication domainMedication = prescription.getMedication();

        if (domainMedication == null) {
            // If there's no medication info, we can't build the FHIR resources
            return;
        }

        // Only add Medication resource if we haven't already.
        int medId = domainMedication.getId();
        if (!addedMedIds.contains(medId)) {
            // Create a Medication resource
            Medication fhirMedication = new Medication();
            // Ex. "Medication-123"
            fhirMedication.setId("Medication-" + medId);
            fhirMedication.setCode(new CodeableConcept().setText(domainMedication.getName()));

            // Add to bundle
            IBase medEntry = bundleBuilder.addEntry();
            bundleBuilder.addToEntry(medEntry, "resource", fhirMedication);

            // Mark this medId as added
            addedMedIds.add(medId);
        }

        // Now we create a MedicationRequest referencing that Medication
        MedicationRequest fhirMedRequest = new MedicationRequest();
        // Ex. "MedicationRequest-13"
        fhirMedRequest.setId("MedicationRequest-" + prescription.getId());
        // Assign references
        CodeableReference medCodeableRef = new CodeableReference();
        medCodeableRef.setReference(new Reference("Medication-" + medId));
        fhirMedRequest.setMedication(medCodeableRef);

        // Linking to patient
        fhirMedRequest.setSubject(new Reference("Patient/" + patientId));

        IBase requestEntry = bundleBuilder.addEntry();
        bundleBuilder.addToEntry(requestEntry, "resource", fhirMedRequest);
    }

    /**
     * @param patientSex either male or female, or some other string, or null.
     *                   Hopefully that doesn't happen.
     * @return the administrative gender, UNKNOWN if sex is null, or OTHER if its some other string
     */
    private static Enumerations.AdministrativeGender getAdministrativeGender(String patientSex) {
        if (patientSex == null) {
            return Enumerations.AdministrativeGender.UNKNOWN;
        }

        if (patientSex.equalsIgnoreCase("male") ) {
            return Enumerations.AdministrativeGender.MALE;
        } else if (patientSex.equalsIgnoreCase("female")) {
            return Enumerations.AdministrativeGender.FEMALE;
        } else {
            return Enumerations.AdministrativeGender.OTHER;
        }
    }

    /**
     * Helper method for creating Practitioner resources out of IUsers & adding them to a Bundle.
     *
     */
    private void addPractitionerData(BundleBuilder bundleBuilder, IUser user) {
        // Creating Practitioner resource and assigning it a unique ID:
        Practitioner fhirPractitioner = new Practitioner();
        // Ex. User 42 (Nurse)
        fhirPractitioner.setId("User " + user.getId());

        // Populating name
        HumanName name = new HumanName();
        name.setFamily(user.getLastName());
        name.addGiven(user.getFirstName());
        fhirPractitioner.setName(Collections.singletonList(name));

        // If the practitioner has an email, add it as a ContactPoint.
        if (user.getEmail() != null) {
            ContactPoint emailContact = new ContactPoint();
            emailContact.setSystem(ContactPoint.ContactPointSystem.EMAIL);
            emailContact.setValue(user.getEmail());
            fhirPractitioner.setTelecom(Collections.singletonList(emailContact));
        }

        // Add to bundle
        IBase entry = bundleBuilder.addEntry();
        bundleBuilder.addToEntry(entry, "resource", fhirPractitioner);
    }

    private static DateTimeType convertFEMRDateTime(String datetime) {
        DateTimeType effectiveDateTime;
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime localDateTime = DateTime.parse(datetime, dateFormat);
        effectiveDateTime = new DateTimeType(localDateTime.toDateTimeISO().toString());
        return effectiveDateTime;
    }

    /**
     * Adds and returns an observation to the bundle.
     * @param bundleBuilder the bundle for the observation to be added to
     * @param fhirPatientId the FHIR patient id that we have taken this observation for
     * @param observationFEMRId the fEMR id of the observation (PatientEncounterVital) we are referring to
     * @return the newly created observation
     */
    private Observation addObservationForPatient(BundleBuilder bundleBuilder, String fhirPatientId, int observationFEMRId) {

        IBase entry = bundleBuilder.addEntry();
        Observation observation = new Observation();
        bundleBuilder.addToEntry(entry, "resource", observation);
        observation.setId(String.format("%s_%s", kitId, observationFEMRId));
        observation.setSubject(new Reference(fhirPatientId));
        return observation;
    }

    private String toJson(BundleBuilder bundleBuilder) {
        // Create a parser
        IParser parser = fhirContext.newJsonParser();

        // Indent the output
        parser.setPrettyPrint(true);

        // Serialize it
        return parser.encodeResourceToString(bundleBuilder.getBundle());
    }
}
