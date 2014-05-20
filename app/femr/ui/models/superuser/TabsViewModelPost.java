package femr.ui.models.superuser;

/**
 * Created by kevin on 5/18/14.
 */
public class TabsViewModelPost {
    private String addTabName;
    private String deleteTab;
    private Integer addTabLeft;
    private Integer addTabRight;

    public String getAddTabName() {
        return addTabName;
    }

    public void setAddTabName(String addTabName) {
        this.addTabName = addTabName;
    }

    public String getDeleteTab() {
        return deleteTab;
    }

    public void setDeleteTab(String deleteTab) {
        this.deleteTab = deleteTab;
    }

    public Integer getAddTabLeft() {
        return addTabLeft;
    }

    public void setAddTabLeft(Integer addTabLeft) {
        this.addTabLeft = addTabLeft;
    }

    public Integer getAddTabRight() {
        return addTabRight;
    }

    public void setAddTabRight(Integer addTabRight) {
        this.addTabRight = addTabRight;
    }
}
