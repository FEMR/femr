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

import femr.data.models.mysql.ChiefComplaint;
import femr.data.models.mysql.User;
import org.joda.time.DateTime;

/**
 * Represents fields that are on each tab on the medical screen
 */
public interface IPatientEncounterTabField {
    int getId();

    void setId(int id);

    int getUserId();

    void setUserId(int userId);

    String getUserName();

    void setUser(User user);

    int getPatientEncounterId();

    void setPatientEncounterId(int patientEncounterId);

    ITabField getTabField();

    void setTabField(ITabField tabField);

    String getTabFieldValue();

    void setTabFieldValue(String tabFieldValue);

    DateTime getDateTaken();

    void setDateTaken(DateTime dateTaken);

    ChiefComplaint getChiefComplaint();

    void setChiefComplaint(IChiefComplaint chiefComplaint);

    DateTime getIsDeleted();

    void setIsDeleted(DateTime time);

    Integer getDeletedByUserId();

    void setDeletedByUserId(Integer userId);
}
