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

import java.util.Date;

public class MissionTripItem {

    private int id;
    private String tripCity;
    private String tripCountry;
    private Date tripStartDate;
    private String friendlyTripStartDate;
    private Date tripEndDate;
    private String friendlyTripEndDate;
    private String teamName; //name of the team running the trip (duplicated in MissionItem)
    private String friendlyTripTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTripCity() {
        return tripCity;
    }

    public void setTripCity(String tripCity) {
        this.tripCity = tripCity;
    }

    public String getTripCountry() {
        return tripCountry;
    }

    public void setTripCountry(String tripCountry) {
        this.tripCountry = tripCountry;
    }

    public Date getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(Date tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public String getFriendlyTripStartDate() {
        return friendlyTripStartDate;
    }

    public void setFriendlyTripStartDate(String friendlyTripStartDate) {
        this.friendlyTripStartDate = friendlyTripStartDate;
    }

    public Date getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(Date tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public String getFriendlyTripEndDate() {
        return friendlyTripEndDate;
    }

    public void setFriendlyTripEndDate(String friendlyTripEndDate) {
        this.friendlyTripEndDate = friendlyTripEndDate;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getFriendlyTripTitle() {
        return friendlyTripTitle;
    }

    public void setFriendlyTripTitle(String friendlyTripTitle) {
        this.friendlyTripTitle = friendlyTripTitle;
    }
}
