package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.data.models.core.IPatientPrescriptionReplacement;
import femr.data.models.mysql.PatientPrescriptionReplacement;

public class PatientPrescriptionReplacementProvider implements Provider<IPatientPrescriptionReplacement> {
    @Override
    public IPatientPrescriptionReplacement get() {
        return new PatientPrescriptionReplacement();
    }
}
