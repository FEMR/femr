package femr.util.dependencyinjection.providers;

import femr.common.models.IPatientEncounterTabField;
import femr.data.models.PatientEncounterTabField;

import javax.inject.Provider;

/**
 * Created by kevin on 5/31/14.
 */
public class PatientEncounterTabFieldProvider implements Provider<IPatientEncounterTabField> {
    @Override
    public IPatientEncounterTabField get() {
        return new PatientEncounterTabField();
    }
}
