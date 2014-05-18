package femr.ui.models.superuser;

import java.util.List;

public class TabsViewModelGet {
    private List<String> currentTabNames;
    private List<String> deletedTabNames;

    public List<String> getCurrentTabNames() {
        return currentTabNames;
    }

    public void setCurrentTabNames(List<String> currentTabNames) {
        this.currentTabNames = currentTabNames;
    }

    public List<String> getDeletedTabNames() {
        return deletedTabNames;
    }

    public void setDeletedTabNames(List<String> deletedTabNames) {
        this.deletedTabNames = deletedTabNames;
    }
}
