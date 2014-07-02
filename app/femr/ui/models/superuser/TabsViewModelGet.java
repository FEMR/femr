package femr.ui.models.superuser;

import femr.common.models.TabItem;

import java.util.List;

public class TabsViewModelGet {
    private List<TabItem> currentTabs;
    private List<TabItem> deletedTabs;

    public List<TabItem> getCurrentTabs() {
        return currentTabs;
    }

    public void setCurrentTabs(List<TabItem> currentTabs) {
        this.currentTabs = currentTabs;
    }

    public List<TabItem> getDeletedTabs() {
        return deletedTabs;
    }

    public void setDeletedTabs(List<TabItem> deletedTabs) {
        this.deletedTabs = deletedTabs;
    }
}
