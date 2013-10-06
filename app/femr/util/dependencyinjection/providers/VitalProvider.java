package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.common.models.IVital;
import femr.data.models.Vital;

public class VitalProvider implements Provider<IVital> {
    @Override
    public IVital get() {
        return new Vital();
    }
}
