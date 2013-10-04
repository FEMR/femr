package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.common.models.IPatient;
import femr.data.models.Patient;

public class PatientProvider implements Provider<IPatient> {
    @Override
    public IPatient get() {
        return new Patient();
    }
}
