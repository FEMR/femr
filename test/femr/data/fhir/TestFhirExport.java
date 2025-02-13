package femr.data.fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.BundleBuilder;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r5.model.Composition;
import org.hl7.fhir.r5.model.Patient;
import org.junit.Test;

public class TestFhirExport {


    @Test
    public void smokeTestBlankDocument() {
        FhirExport export = new FhirExport();
        System.out.println(export.toJson());

    }
}
