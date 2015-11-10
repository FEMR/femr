package femr.common.models;

import java.util.ArrayList;
import java.util.Date;
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
