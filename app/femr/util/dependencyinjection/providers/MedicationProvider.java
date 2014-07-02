package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.data.models.IMedication;
import femr.data.models.Medication;

/**
 * Created by kevin on 5/14/14.
 */
public class MedicationProvider implements Provider<IMedication> {
    @Override
    public IMedication get() {
        return new Medication();
    }
}
