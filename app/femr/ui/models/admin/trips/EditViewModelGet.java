package femr.ui.models.admin.trips;

import femr.common.models.MissionTripItem;
import femr.common.models.UserItem;

import java.util.List;

public class EditViewModelGet {

    private int tripId;
    private MissionTripItem trip;
    private List<UserItem> users;
    private List<UserItem> allUsers;

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public MissionTripItem getTrip() {
        return trip;
    }

    public void setTrip(MissionTripItem trip) {
        this.trip = trip;
    }

    public List<UserItem> getUsers() {
        return users;
    }

    public void setUsers(List<UserItem> users) {
        this.users = users;
    }

    public List<UserItem> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<UserItem> allUsers) {
        this.allUsers = allUsers;
    }
}
