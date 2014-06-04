package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.data.models.IPatientEncounter;
import femr.data.models.PatientEncounter;

public class PatientEncounterProvider implements Provider<IPatientEncounter> {
    @Override
    public IPatientEncounter get() {
        return new PatientEncounter();
    }
}
