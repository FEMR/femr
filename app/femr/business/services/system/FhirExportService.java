package femr.business.services.system;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.BundleBuilder;
import com.google.inject.Inject;
import femr.business.services.core.IFhirExportService;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.IPatient;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r5.model.Composition;

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

        IPatient patient = patientRepository.retrievePatientById(patientId);

        patient.getAge();

        return bundleBuilder;

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
