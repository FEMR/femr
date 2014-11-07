/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.ui.models.superuser;

import femr.common.models.TabFieldItem;
import java.util.List;

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
