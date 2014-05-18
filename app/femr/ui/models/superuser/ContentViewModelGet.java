package femr.ui.models.superuser;

import femr.ui.models.data.CustomFieldItem;

import java.util.List;

/**
 * Created by kevin on 5/18/14.
 */
public class ContentViewModelGet {
    private String name;
    private List<CustomFieldItem> currentCustomFieldItemList;
    private List<CustomFieldItem> removedCustomFieldItemList;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CustomFieldItem> getCurrentCustomFieldItemList() {
        return currentCustomFieldItemList;
    }

    public void setCurrentCustomFieldItemList(List<CustomFieldItem> currentCustomFieldItemList) {
        this.currentCustomFieldItemList = currentCustomFieldItemList;
    }

    public List<CustomFieldItem> getRemovedCustomFieldItemList() {
        return removedCustomFieldItemList;
    }

    public void setRemovedCustomFieldItemList(List<CustomFieldItem> removedCustomFieldItemList) {
        this.removedCustomFieldItemList = removedCustomFieldItemList;
    }
}
