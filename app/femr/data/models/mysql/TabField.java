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
package femr.data.models.mysql;

import femr.data.models.core.ITab;
import femr.data.models.core.ITabField;
import femr.data.models.core.ITabFieldSize;
import femr.data.models.core.ITabFieldType;

import javax.persistence.*;

@Entity
@Table(name = "tab_fields")
public class TabField implements ITabField {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tab_id", nullable = false)
    private Tab tab;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false)
    private TabFieldType tabFieldType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id", nullable = false)
    private TabFieldSize tabFieldSize;
    @Column(name = "sort_order", unique = false, nullable = true)
    private Integer order;
    @Column(name = "placeholder", unique = false, nullable = true)
    private String placeholder;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public ITabFieldType getTabFieldType() {
        return tabFieldType;
    }

    @Override
    public void setTabFieldType(ITabFieldType tabFieldType) {
        this.tabFieldType = (TabFieldType) tabFieldType;
    }

    @Override
    public ITabFieldSize getTabFieldSize() {
        return tabFieldSize;
    }

    @Override
    public void setTabFieldSize(ITabFieldSize tabFieldSize) {
        this.tabFieldSize = (TabFieldSize) tabFieldSize;
    }

    @Override
    public Integer getOrder() {
        return order;
    }

    @Override
    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public ITab getTab() {
        return tab;
    }

    @Override
    public void setTab(ITab tab) {
        this.tab = (Tab) tab;
    }
}
