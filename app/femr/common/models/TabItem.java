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

    public TabItem(){
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

    public void addTabFieldItem(String name, String type){
        //empty, non-custom
        TabFieldItem tabFieldItem = new TabFieldItem();
        tabFieldItem.setName(name);
        tabFieldItem.setType(type);

        this.fields.add(tabFieldItem);
    }

    public void addTabFieldItem(String name, String type, String value, String chiefComplaint){
        //has value & non-custom
        TabFieldItem tabFieldItem = new TabFieldItem();
        tabFieldItem.setName(name);
        tabFieldItem.setType(type);
        tabFieldItem.setValue(value);
        tabFieldItem.setChiefComplaint(chiefComplaint);

        this.fields.add(tabFieldItem);
    }

    public void addTabFieldItem(String name, String type, String size, Integer sortOrder, String placeholder){
        //no value & custom
        TabFieldItem tabFieldItem = new TabFieldItem();
        tabFieldItem.setName(name);
        tabFieldItem.setType(type);
        tabFieldItem.setSize(size);
        tabFieldItem.setOrder(sortOrder);
        tabFieldItem.setPlaceholder(placeholder);

        this.fields.add(tabFieldItem);
    }

    public void addTabFieldItem(String name, String type, String size, Integer sortOrder, String placeholder, String value, String chiefComplaint){
        //has value & custom
        TabFieldItem tabFieldItem = new TabFieldItem();
        tabFieldItem.setName(name);
        tabFieldItem.setType(type);
        tabFieldItem.setSize(size);
        tabFieldItem.setOrder(sortOrder);
        tabFieldItem.setPlaceholder(placeholder);
        tabFieldItem.setChiefComplaint(chiefComplaint);
        tabFieldItem.setValue(value);

        this.fields.add(tabFieldItem);
    }

    public class TabFieldItem {
        private String name;
        private String type;
        private String size;
        private Integer order;
        private String placeholder;
        private String value;
        //chief complaint is used to identify which chief complaint the tab field falls under.
        //this is not always relevant (sometimes they fall under all chief complaints)
        private String chiefComplaint;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getChiefComplaint() {
            return chiefComplaint;
        }

        public void setChiefComplaint(String chiefComplaint) {
            this.chiefComplaint = chiefComplaint;
        }
    }
}
