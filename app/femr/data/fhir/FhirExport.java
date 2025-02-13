package femr.data.fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.BundleBuilder;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r5.model.Composition;
import org.hl7.fhir.r5.model.Patient;

/**
 * Converts between fEMR data models and FHIR models.
 */
public class FhirExport {

    private static final FhirContext fhirContext = FhirContext.forR5();
    public FhirContext getFhirContext() {
        return fhirContext;
    }

    BundleBuilder bundleBuilder = new BundleBuilder(this.getFhirContext());
    Composition composition;

    FhirExport() {
        bundleBuilder.setType("document");

        // Create bundle entry
        IBase entry = bundleBuilder.addEntry();

        Composition composition = new Composition();
        this.composition = composition;

        // So we see it in a blank document
        composition.setId("1");

        bundleBuilder.addToEntry(entry, "resource", composition);
    }

    public String toJson() {
        // Create a parser
        IParser parser = this.getFhirContext().newJsonParser();

        // Indent the output
        parser.setPrettyPrint(true);

        // Serialize it
        return parser.encodeResourceToString(this.bundleBuilder.getBundle());
    }


}
