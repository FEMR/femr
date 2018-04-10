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
package femr.common.dtos;

import femr.data.models.core.IRole;

import java.util.List;

public class CurrentUser {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<IRole> roles;
    //the id of the current trip, can be null if it doesn't exist
    private Integer tripId;
    long SessionTimeOut;

    public CurrentUser(int id, String firstName, String lastName, String email, List<IRole> roles, Integer tripId, long time) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.tripId = tripId;
        this.SessionTimeOut = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<IRole> getRoles() {
        return roles;
    }

    public Integer getTripId() {
        return tripId;
    }

    public long getTimeout2() {
        return SessionTimeOut;
    }
}