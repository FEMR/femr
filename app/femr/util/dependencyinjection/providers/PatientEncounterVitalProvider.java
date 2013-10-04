package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.common.models.IPatientEncounterVital;
import femr.data.models.PatientEncounterVital;

public class PatientEncounterVitalProvider implements Provider<IPatientEncounterVital> {
    @Override
    public IPatientEncounterVital get() {
        return new PatientEncounterVital();
    }
}
