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

public class ContentViewModelPost {
    private String addName;
    private String addType;
    private String addSize;
    private Integer addOrder;
    private String addPlaceholder;
    private String toggleName;

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public String getAddSize() {
        return addSize;
    }

    public void setAddSize(String addSize) {
        this.addSize = addSize;
    }

    public String getToggleName() {
        return toggleName;
    }

    public void setToggleName(String toggleName) {
        this.toggleName = toggleName;
    }

    public Integer getAddOrder() {
        return addOrder;
    }

    public void setAddOrder(Integer addOrder) {
        this.addOrder = addOrder;
    }

    public String getAddPlaceholder() {
        return addPlaceholder;
    }

    public void setAddPlaceholder(String addPlaceholder) {
        this.addPlaceholder = addPlaceholder;
    }
}
