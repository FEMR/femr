package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.common.models.IPatientResearch;
import femr.data.models.PatientResearch;

/**
 *  This is the PatientResearch Provider
 */
public class PatientResearchProvider implements Provider<IPatientResearch> {

    @Override
    public IPatientResearch get() {
        return new PatientResearch();
    }
}
