package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.common.models.IPatientEncounterHpiField;
import femr.data.models.PatientEncounterHpiField;

public class PatientEncounterHpiFieldProvider implements Provider<IPatientEncounterHpiField> {
    @Override
    public IPatientEncounterHpiField get() {
        return new PatientEncounterHpiField();
    }
}
