package femr.util.dependencyinjection.providers;

import femr.data.models.ITab;
import femr.data.models.Tab;

import javax.inject.Provider;

/**
 * Created by kevin on 5/31/14.
 */
public class TabProvider implements Provider<ITab> {
    @Override
    public ITab get() {
        return new Tab();
    }
}
