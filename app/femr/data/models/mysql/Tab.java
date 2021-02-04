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
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tabs")
public class Tab implements ITab {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "user_id", nullable = true)
    private Integer userId;
    @Column(name = "date_created", nullable = false)
    private DateTime dateCreated;
    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;
    @Column(name = "left_column_size", nullable = false)
    private int leftColumnSize;
    @Column(name = "right_column_size", nullable = false)
    private int rightColumnSize;
    @Column(name = "isCustom", nullable = false)
    private boolean isCustom;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tab")
    private List<TabField> tabFields;

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
    public Integer getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public DateTime getDateCreated() {
        return dateCreated;
    }

    @Override
    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public int getLeftColumnSize() {
        return leftColumnSize;
    }

    @Override
    public void setLeftColumnSize(int leftColumnSize) {
        this.leftColumnSize = leftColumnSize;
    }

    @Override
    public int getRightColumnSize() {
        return rightColumnSize;
    }

    @Override
    public void setRightColumnSize(int rightColumnSize) {
        this.rightColumnSize = rightColumnSize;
    }

    @Override
    public boolean getIsCustom() {
        return isCustom;
    }

    @Override
    public void setIsCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    @Override
    public List<TabField> getTabFields() {
        return tabFields;
    }

    @Override
    public void setTabFields(List<TabField> tabFields) {
        this.tabFields = tabFields;
    }
}
