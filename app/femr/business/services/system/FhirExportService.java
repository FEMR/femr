package femr.business.services.system;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.BundleBuilder;
import com.google.inject.Inject;
import femr.business.helpers.FhirCodeableConcepts;
import femr.business.services.core.IEncounterService;
import femr.business.services.core.IFhirExportService;
import femr.data.daos.core.IEncounterRepository;
import femr.data.daos.core.IPatientEncounterVitalRepository;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.IPatient;
import femr.data.models.core.IPatientEncounter;
import femr.data.models.core.IPatientEncounterVital;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r5.model.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Converts between fEMR data models and FHIR models.
 */
public class FhirExportService implements IFhirExportService {

    private static final FhirContext fhirContext = FhirContext.forR5();


    IPatientRepository patientRepository;
    IEncounterRepository encounterRepository;
    IPatientEncounterVitalRepository patientEncounterVitalRepository;
    private String kitId;

    @Inject
    public FhirExportService(IPatientRepository patientRepository, IEncounterRepository encounterRepository, IPatientEncounterVitalRepository patientEncounterVitalRepository, String kitId) {
        this.patientRepository = patientRepository;
        this.encounterRepository = encounterRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.kitId = kitId;
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
        }

        return bundleBuilder;

    }

    private void addBloodPressure(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {

        for(IPatientEncounterVital vital: vitals) {
            if(vital.getVital().getName().equals("bloodPressureSystolic")) {
                IBase entry = bundleBuilder.addEntry();
                Observation observation = new Observation();
                bundleBuilder.addToEntry(entry, "resource", observation);
                observation.setId(String.format("%s_%s", kitId, vital.getId()));
                observation.setCode(FhirCodeableConcepts.getBloodPressureSystolic());
                observation.setSubject(new Reference(fhirPatientId));
                DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime localDateTime = DateTime.parse(vital.getDateTaken(), dateFormat);
                DateTimeType effectiveDateTime = new DateTimeType(localDateTime.toDateTimeISO().toString());
                observation.setEffective(effectiveDateTime);
                observation.setValue(FhirCodeableConcepts.getSystolic(vital.getVitalValue()));
            }
            if(vital.getVital().getName().equals("bloodPressureDiastolic")){
                IBase entry = bundleBuilder.addEntry();
                Observation observation = new Observation();
                bundleBuilder.addToEntry(entry, "resource", observation);
                observation.setId(String.format("%s_%s", kitId, vital.getId()));
                observation.setCode(FhirCodeableConcepts.getBloodPressureDiastolic());
                observation.setSubject(new Reference(fhirPatientId));
                DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime localDateTime = DateTime.parse(vital.getDateTaken(), dateFormat);
                DateTimeType effectiveDateTime = new DateTimeType(localDateTime.toDateTimeISO().toString());
                observation.setEffective(effectiveDateTime);
                observation.setValue(FhirCodeableConcepts.getDiastolic(vital.getVitalValue()));

            }
        }

    }

    private void addBodyTemp(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {

        for(IPatientEncounterVital vital: vitals) {
            if(vital.getVital().getName().equals("temperature")) {
                IBase entry = bundleBuilder.addEntry();
                Observation observation = new Observation();
                bundleBuilder.addToEntry(entry, "resource", observation);
                observation.setId(String.format("%s_%s", kitId, vital.getId()));
                observation.setCode(FhirCodeableConcepts.getBodyTemperature());
                observation.setSubject(new Reference(fhirPatientId));
                DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime localDateTime = DateTime.parse(vital.getDateTaken(), dateFormat);
                DateTimeType effectiveDateTime = new DateTimeType(localDateTime.toDateTimeISO().toString());
                observation.setEffective(effectiveDateTime);

                observation.setValue(FhirCodeableConcepts.getTemperature(vital.getVitalValue()));

            }
        }
    }

    private void addBodyWeight(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {

        for(IPatientEncounterVital vital : vitals){
            if(vital.getVital().getName().equals("weight")){
                IBase entry = bundleBuilder.addEntry();
                Observation observation = new Observation();
                bundleBuilder.addToEntry(entry, "resource", observation);
                observation.setId(String.format("%s_%s", kitId, vital.getId()));
                observation.setCode(FhirCodeableConcepts.getBodyWeight());
                observation.setSubject(new Reference(fhirPatientId));

                DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime localDateTime = DateTime.parse(vital.getDateTaken(), dateFormat);
                DateTimeType effectiveDateTime = new DateTimeType(localDateTime.toDateTimeISO().toString());
                observation.setEffective(effectiveDateTime);

                observation.setValue(FhirCodeableConcepts.getWeight(vital.getVitalValue()));

            }

        }

    }

    private void addRespirationRate(BundleBuilder bundleBuilder, String fhirPatientId, List<? extends IPatientEncounterVital> vitals) {
        for (IPatientEncounterVital vital : vitals) {
            if (vital.getVital().getName().equals("respiratoryRate")) {
                IBase entry = bundleBuilder.addEntry();
                Observation observation = new Observation();
                bundleBuilder.addToEntry(entry, "resource", observation);

                observation.setId(String.format("%s_%s", kitId, vital.getId()));

                // From https://www.hl7.org/fhir/observation-example-respiratory-rate.json.html
                observation.setCode(FhirCodeableConcepts.getRespiratoryRate());
                observation.setSubject(new Reference(fhirPatientId));

                DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime localDateTime = DateTime.parse(vital.getDateTaken(), dateFormat);
                DateTimeType effectiveDateTime = new DateTimeType(localDateTime.toDateTimeISO().toString());
                observation.setEffective(effectiveDateTime);

                observation.setValue(FhirCodeableConcepts.getBreathsPerMinute(vital.getVitalValue()));
            }
        }

    }

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

        // city is omitted as it is where the patient is treated and not
        // a property of the patient itself. So it will go with the encounter model.

        // TODO: add photo

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

    private String toJson(BundleBuilder bundleBuilder) {
        // Create a parser
        IParser parser = fhirContext.newJsonParser();

        // Indent the output
        parser.setPrettyPrint(true);

        // Serialize it
        return parser.encodeResourceToString(bundleBuilder.getBundle());
    }


    @Override
    public String exportPatient(int patientId) {
        return toJson(buildPatientBundle(patientId));
    }
}
