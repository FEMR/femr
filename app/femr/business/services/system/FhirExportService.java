package femr.business.services.system;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.BundleBuilder;
import com.google.inject.Inject;
import femr.business.services.core.IFhirExportService;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.IPatient;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r5.model.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Converts between fEMR data models and FHIR models.
 */
public class FhirExportService implements IFhirExportService {

    private static final FhirContext fhirContext = FhirContext.forR5();

    IPatientRepository patientRepository;

    @Inject
    public FhirExportService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    private BundleBuilder buildPatientBundle(int patientId) {


        BundleBuilder bundleBuilder = new BundleBuilder(fhirContext);

        bundleBuilder.setType("document");

        // Create bundle entry
        IBase entry = bundleBuilder.addEntry();

        Composition composition = new Composition();

        // So we see it in a blank document
        composition.setId("1");

        bundleBuilder.addToEntry(entry, "resource", composition);

        addPatientData(bundleBuilder, patientId);

        return bundleBuilder;

    }

    private void addPatientData(BundleBuilder bundleBuilder, int patientId) {

        IPatient patient = patientRepository.retrievePatientById(patientId);

        Patient fhirPatient = new Patient();

        IBase entry = bundleBuilder.addEntry();
        bundleBuilder.addToEntry(entry, "resource", fhirPatient);

        fhirPatient.setGender(getAdministrativeGender(patient.getSex()));

        fhirPatient.setId(Integer.toString(patient.getId()));


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
