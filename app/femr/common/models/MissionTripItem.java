package femr.common.models;

import java.util.Date;

/**
 * Created by kevin on 10/18/15.
 */
public class MissionTripItem {

    private int id;
    private String tripCity;
    private String tripCountry;
    private Date tripStartDate;
    private String friendlyTripStartDate;
    private Date tripEndDate;
    private String friendlyTripEndDate;
    private String teamName; //name of the team running the trip (duplicated in MissionItem)

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
}
