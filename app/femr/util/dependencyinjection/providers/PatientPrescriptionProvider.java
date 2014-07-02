package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.data.models.IPatientPrescription;
import femr.data.models.PatientPrescription;

public class PatientPrescriptionProvider implements Provider<IPatientPrescription> {
    @Override
    public IPatientPrescription get() {
        return new PatientPrescription();
    }
}

