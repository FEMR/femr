package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.common.models.IPatientEncounterTreatmentField;
import femr.data.models.PatientEncounterTreatmentField;

public class PatientEncounterTreatmentFieldProvider implements Provider<IPatientEncounterTreatmentField> {
    @Override
    public IPatientEncounterTreatmentField get() {
        return new PatientEncounterTreatmentField();
    }
}
