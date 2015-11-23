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

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks a mission (name, location, dates, etc)
 */
public class MissionItem {

    private String teamName;
    private String teamLocation;
    private String teamDescription;
    private List<MissionTripItem> missionTrips;

    public MissionItem() {
        this.missionTrips = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLocation() {
        return teamLocation;
    }

    public void setTeamLocation(String teamLocation) {
        this.teamLocation = teamLocation;
    }

    public String getTeamDescription() {
        return teamDescription;
    }

    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
    }

    public List<MissionTripItem> getMissionTrips() {
        return missionTrips;
    }

    public void setMissionTrips(List<MissionTripItem> missionTrips) {
        this.missionTrips = missionTrips;
    }

    public void addMissionTrip(MissionTripItem missionTripItem) {

        this.missionTrips.add(missionTripItem);
    }
}
