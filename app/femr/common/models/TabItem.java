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
package femr.common.models;

import java.util.ArrayList;
import java.util.List;

public class TabItem {
    private String name;
    private int leftColumnSize;
    private int rightColumnSize;
    private boolean isCustom;
    private List<TabFieldItem> fields;

    public TabItem() {
        this.fields = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeftColumnSize() {
        return leftColumnSize;
    }

    public void setLeftColumnSize(int leftColumnSize) {
        this.leftColumnSize = leftColumnSize;
    }

    public int getRightColumnSize() {
        return rightColumnSize;
    }

    public void setRightColumnSize(int rightColumnSize) {
        this.rightColumnSize = rightColumnSize;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    public List<TabFieldItem> getFields() {
        return fields;
    }

    public void setFields(List<TabFieldItem> fields) {
        this.fields = fields;
    }


    public TabFieldItem getTabFieldItemByName(String name) {
        for (TabFieldItem tfi : this.fields) {
            if (tfi.getName().toLowerCase().equals(name.toLowerCase()))
                return tfi;
        }
        return null;
    }


    public void addTabFieldItem(TabFieldItem tabFieldItem) {

        this.fields.add(tabFieldItem);
    }
}
