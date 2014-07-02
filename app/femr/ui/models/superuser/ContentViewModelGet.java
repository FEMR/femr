package femr.ui.models.superuser;

import femr.common.models.TabFieldItem;

import java.util.List;

/**
 * Created by kevin on 5/18/14.
 */
public class ContentViewModelGet {
    private String name;
    private List<TabFieldItem> currentCustomFieldItemList;
    private List<TabFieldItem> removedCustomFieldItemList;
    private List<String> customFieldTypes;
    private List<String> customFieldSizes;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCustomFieldTypes() {
        return customFieldTypes;
    }

    public void setCustomFieldTypes(List<String> customFieldTypes) {
        this.customFieldTypes = customFieldTypes;
    }

    public List<String> getCustomFieldSizes() {
        return customFieldSizes;
    }

    public void setCustomFieldSizes(List<String> customFieldSizes) {
        this.customFieldSizes = customFieldSizes;
    }

    public List<TabFieldItem> getCurrentCustomFieldItemList() {
        return currentCustomFieldItemList;
    }

    public void setCurrentCustomFieldItemList(List<TabFieldItem> currentCustomFieldItemList) {
        this.currentCustomFieldItemList = currentCustomFieldItemList;
    }

    public List<TabFieldItem> getRemovedCustomFieldItemList() {
        return removedCustomFieldItemList;
    }

    public void setRemovedCustomFieldItemList(List<TabFieldItem> removedCustomFieldItemList) {
        this.removedCustomFieldItemList = removedCustomFieldItemList;
    }
}
