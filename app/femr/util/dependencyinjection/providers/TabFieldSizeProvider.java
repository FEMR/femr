package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.common.models.ITabFieldSize;
import femr.data.models.TabFieldSize;

public class TabFieldSizeProvider implements Provider<ITabFieldSize> {
    @Override
    public ITabFieldSize get() {
        return new TabFieldSize();
    }
}
