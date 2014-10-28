package femr.ui.models.admin.configure;

import java.util.HashMap;
import java.util.Map;

public class IndexViewModelGet {
    private Map<String, Boolean> settings;

    public IndexViewModelGet(){
        this.settings = new HashMap<>();
    }

    public Map<String, Boolean> getSettings() {
        return settings;
    }

    public void setSetting(String name, Boolean isActive){
        settings.put(name, isActive);
    }

    public void setSettings(Map<String, Boolean> settings) {
        this.settings = settings;
    }
}
