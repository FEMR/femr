package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.data.models.ChiefComplaint;
import femr.data.models.IChiefComplaint;

public class ChiefComplaintProvider implements Provider<IChiefComplaint> {

    @Override
    public IChiefComplaint get() {
        return new ChiefComplaint();
    }
}
