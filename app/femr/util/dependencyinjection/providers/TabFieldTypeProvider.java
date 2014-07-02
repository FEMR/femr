package femr.util.dependencyinjection.providers;

import femr.data.models.ITabFieldType;
import femr.data.models.TabFieldType;

import javax.inject.Provider;

/**
 * Created by kevin on 5/31/14.
 */
public class TabFieldTypeProvider implements Provider<ITabFieldType> {
    @Override
    public ITabFieldType get() {
        return new TabFieldType();
    }
}
