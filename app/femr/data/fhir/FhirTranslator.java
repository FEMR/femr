package femr.data.fhir;

import ca.uhn.fhir.context.FhirContext;

/**
 * Converts between fEMR data models and FHIR models.
 */
public class FhirTranslator {
    // Singleton Pattern
    private static FhirTranslator instance;
    private FhirTranslator() {}
    public static FhirTranslator getInstance() {
        if (instance == null) {
            instance = new FhirTranslator();
        }
        return instance;
    }

    private final FhirContext fhirContext = FhirContext.forR5();
    public FhirContext getFhirContext() {
        return fhirContext;
    }


}
