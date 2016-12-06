package femr.ui.models.admin.configure;

import java.util.HashMap;
import java.util.Map;

public class IndexViewModelGet {
    //First map contains Name and isActive pair, second contains Name and description pair
    private Map<String, Boolean> settings;;
    private Map<String, String> descriptions;;

    public IndexViewModelGet(){
        this.settings = new HashMap<>();
        this.descriptions = new HashMap<>();
    }

    public Map<String, Boolean> getSettings() {
        return settings;
    }
    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    public void setSetting(String name, Boolean isActive){
        settings.put(name, isActive);
    }
    public void setDescription(String name, String description){
        descriptions.put(name, description);
    }

    public void setSettings(Map<String, Boolean> settings) {
        this.settings = settings;
    }
    public void setDescriptions(Map<String, String> descriptions) {
        this.descriptions = descriptions;
    }

}
