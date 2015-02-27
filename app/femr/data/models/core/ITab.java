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
package femr.data.models.core;

import femr.data.models.mysql.TabField;
import org.joda.time.DateTime;

import java.util.List;

/**
 * A tab on the medical screen - allows for dynamic tabs
 */
public interface ITab {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Integer getUserId();

    void setUserId(Integer userId);

    DateTime getDateCreated();

    void setDateCreated(DateTime dateCreated);

    boolean getIsDeleted();

    void setIsDeleted(boolean isDeleted);

    int getLeftColumnSize();

    void setLeftColumnSize(int leftColumnSize);

    int getRightColumnSize();

    void setRightColumnSize(int rightColumnSize);

    boolean getIsCustom();

    void setIsCustom(boolean isCustom);

    List<TabField> getTabFields();

    void setTabFields(List<TabField> tabFields);
}
