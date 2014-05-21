package femr.ui.models.superuser;

import femr.ui.models.data.custom.CustomTabItem;

import java.util.List;

public class TabsViewModelGet {
    private List<CustomTabItem> currentTabs;
    private List<CustomTabItem> deletedTabs;

    public List<CustomTabItem> getCurrentTabs() {
        return currentTabs;
    }

    public void setCurrentTabs(List<CustomTabItem> currentTabs) {
        this.currentTabs = currentTabs;
    }

    public List<CustomTabItem> getDeletedTabs() {
        return deletedTabs;
    }

    public void setDeletedTabs(List<CustomTabItem> deletedTabs) {
        this.deletedTabs = deletedTabs;
    }
}
