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

import org.joda.time.DateTime;

import java.util.List;

public interface IUser {

    int getId();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    List<IRole> getRoles();

    void setRoles(List<? extends IRole> roles);

    void addRole(IRole role);

    DateTime getLastLogin();

    void setLastLogin(DateTime lastLogin);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

    Boolean getPasswordReset();

    void setPasswordReset(Boolean passwordReset);

    /**
     * special remarks that help identify the user
     * @return
     */
    String getNotes();

    void setNotes(String notes);

    // AJ Saclayan Password Constraint
    DateTime getPasswordCreatedDate();

    void setPasswordCreatedDate(DateTime date);
    
    List<IMissionTrip> getMissionTrips();

    void setMissionTrips(List<IMissionTrip> missionTrips);

    DateTime getDateCreated(); //Sam Zanni

    void setDateCreated(DateTime DateCreated); //Sam Zanni

    Integer getCreatedBy(); //Sam Zanni

    void setCreatedBy(Integer CreatedBy); //Sam Zanni
}
