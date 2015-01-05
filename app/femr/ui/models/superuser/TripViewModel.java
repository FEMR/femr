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

import femr.util.stringhelpers.StringUtils;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripViewModel {
    //team stuff
    private String team;
    private String teamLocation;
    private String description;
    //trip stuff
    private String city;
    private String country;
    private Date startDate;
    private Date endDate;

    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if (StringUtils.isNullOrWhiteSpace(team)) {
            errors.add(new ValidationError("team", "team name is a required field"));
        }
        if (StringUtils.isNullOrWhiteSpace(teamLocation)) {
            errors.add(new ValidationError("teamLocation", "team location is a required field"));
        }
        if (StringUtils.isNullOrWhiteSpace(description)) {
            errors.add(new ValidationError("description", "team description is a required field"));
        }
        if (StringUtils.isNullOrWhiteSpace(city)) {
            errors.add(new ValidationError("city", "trip city is a required field"));
        }
        if (StringUtils.isNullOrWhiteSpace(country)) {
            errors.add(new ValidationError("country", "trip country is a required field"));
        }
        if (startDate == null) {
            errors.add(new ValidationError("startDate", "trip start date is a required field"));
        }
        if (endDate == null) {
            errors.add(new ValidationError("endDate", "trip end date is a required field"));
        }

        return errors.isEmpty() ? null : errors;
    }


    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTeamLocation() {
        return teamLocation;
    }

    public void setTeamLocation(String teamLocation) {
        this.teamLocation = teamLocation;
    }
}
