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
package femr.ui.models.admin.trips;

import java.util.Date;

public class TripViewModelPost {

    //add team
    private String newTeamName;
    private String newTeamLocation;
    private String newTeamDescription;

    //add city
    private String newCity;
    private String newCityCountry;

    //add trip
    private String newTripTeamName;
    private String newTripCity;
    private String newTripCountry;
    private Date newTripStartDate;
    private Date newTripEndDate;
    private Integer currentTripIdToggle;

    public String getNewTeamName() {
        return newTeamName;
    }

    public void setNewTeamName(String newTeamName) {
        this.newTeamName = newTeamName;
    }

    public String getNewTeamLocation() {
        return newTeamLocation;
    }

    public void setNewTeamLocation(String newTeamLocation) {
        this.newTeamLocation = newTeamLocation;
    }

    public String getNewTeamDescription() {
        return newTeamDescription;
    }

    public void setNewTeamDescription(String newTeamDescription) {
        this.newTeamDescription = newTeamDescription;
    }

    public String getNewTripTeamName() {
        return newTripTeamName;
    }

    public void setNewTripTeamName(String newTripTeamName) {
        this.newTripTeamName = newTripTeamName;
    }

    public String getNewTripCity() {
        return newTripCity;
    }

    public void setNewTripCity(String newTripCity) {
        this.newTripCity = newTripCity;
    }

    public String getNewTripCountry() {
        return newTripCountry;
    }

    public void setNewTripCountry(String newTripCountry) {
        this.newTripCountry = newTripCountry;
    }

    public Date getNewTripStartDate() {
        return newTripStartDate;
    }

    public void setNewTripStartDate(Date newTripStartDate) {
        this.newTripStartDate = newTripStartDate;
    }

    public Date getNewTripEndDate() {
        return newTripEndDate;
    }

    public void setNewTripEndDate(Date newTripEndDate) {
        this.newTripEndDate = newTripEndDate;
    }

    public Integer getCurrentTripIdToggle() {
        return currentTripIdToggle;
    }

    public void setCurrentTripIdToggle(Integer currentTripIdToggle) {
        this.currentTripIdToggle = currentTripIdToggle;
    }

    public String getNewCity() {
        return newCity;
    }

    public void setNewCity(String newCity) {
        this.newCity = newCity;
    }

    public String getNewCityCountry() {
        return newCityCountry;
    }

    public void setNewCityCountry(String newCityCountry) {
        this.newCityCountry = newCityCountry;
    }
}
