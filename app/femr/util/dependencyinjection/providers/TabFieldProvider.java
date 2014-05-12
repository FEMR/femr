package femr.util.dependencyinjection.providers;

import femr.common.models.ITabField;
import femr.data.models.TabField;

import javax.inject.Provider;

/**
 * Created by kevin on 5/31/14.
 */
public class TabFieldProvider implements Provider<ITabField> {
    @Override
    public ITabField get() {
        return new TabField();
    }
}
