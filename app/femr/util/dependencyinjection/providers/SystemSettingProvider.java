package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.data.models.ISystemSetting;
import femr.data.models.SystemSetting;

public class SystemSettingProvider implements Provider<ISystemSetting> {
    @Override
    public ISystemSetting get() {
        return new SystemSetting();
    }
}