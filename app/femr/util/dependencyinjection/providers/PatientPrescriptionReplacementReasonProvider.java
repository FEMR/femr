package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.data.models.core.IPatientPrescriptionReplacementReason;
import femr.data.models.mysql.PatientPrescriptionReplacementReason;

public class PatientPrescriptionReplacementReasonProvider implements Provider<IPatientPrescriptionReplacementReason> {
    @Override
    public IPatientPrescriptionReplacementReason get() {
        return new PatientPrescriptionReplacementReason();
    }
}
