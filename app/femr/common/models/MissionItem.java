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

    public void addMissionTrip(int id,
                               String tripCity,
                               String tripCountry,
                               Date tripStartDate,
                               String friendlyTripStartDate,
                               Date tripEndDate,
                               String friendlyTripEndDate
    ) {

        MissionTripItem missionTripItem = new MissionTripItem();
        missionTripItem.setId(id);
        missionTripItem.setTripCity(tripCity);
        missionTripItem.setTripCountry(tripCountry);
        missionTripItem.setTripStartDate(tripStartDate);
        missionTripItem.setFriendlyTripStartDate(friendlyTripStartDate);
        missionTripItem.setTripEndDate(tripEndDate);
        missionTripItem.setFriendlyTripEndDate(friendlyTripEndDate);
        this.missionTrips.add(missionTripItem);
    }

    public List<MissionTripItem> getMissionTrips() {
        return missionTrips;
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


    public class MissionTripItem {
        private int id;
        private String tripCity;
        private String tripCountry;
        private Date tripStartDate;
        private String friendlyTripStartDate;
        private Date tripEndDate;
        private String friendlyTripEndDate;
        private boolean isCurrent;

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
    }

}
