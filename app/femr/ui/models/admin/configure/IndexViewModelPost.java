package femr.ui.models.admin.configure;

import java.util.List;

public class IndexViewModelPost {
    private List<String> settings;
    private List<String> descriptions;

    public List<String> getSettings() {
        return settings;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setSettings(List<String> settings) {
        this.settings = settings;
    }
}
