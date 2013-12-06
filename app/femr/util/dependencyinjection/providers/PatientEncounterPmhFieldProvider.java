package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.common.models.IPatientEncounterHpiField;
import femr.common.models.IPatientEncounterPmhField;
import femr.data.models.PatientEncounterHpiField;
import femr.data.models.PatientEncounterPmhField;

public class PatientEncounterPmhFieldProvider implements Provider<IPatientEncounterPmhField> {
    @Override
    public IPatientEncounterPmhField get() {
        return new PatientEncounterPmhField();
    }
}
