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

import femr.data.models.core.IMissionTeam;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mission_teams")
public class MissionTeam implements IMissionTeam {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "location", unique = true, nullable = false)
    private String location;
    @Column(name = "description", unique = true, nullable = false)
    private String description;
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "missionTeam")
    private List<MissionTrip> missionTrips;
    private String languageCode;

    @Override
    public int getId() {
        return id;
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
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<MissionTrip> getMissionTrips() {
        return missionTrips;
    }

    @Override
    public void setMissionTrips(List<MissionTrip> missionTrips) {
        this.missionTrips = missionTrips;
    }
    @Override
    public String getLanguageCode() { return this.languageCode; }

    @Override
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
