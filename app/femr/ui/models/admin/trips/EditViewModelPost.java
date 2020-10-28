package femr.ui.models.admin.trips;

import java.util.List;

public class EditViewModelPost {

    private List<Integer> newUsersForTrip;
    private List<Integer> removeUsersForTrip;

    public List<Integer> getNewUsersForTrip() {
        return newUsersForTrip;
    }

    public void setNewUsersForTrip(List<Integer> newUsersForTrip) {
        this.newUsersForTrip = newUsersForTrip;
    }

    public List<Integer> getRemoveUsersForTrip() {
        return removeUsersForTrip;
    }

    public void setRemoveUsersForTrip(List<Integer> removeUsersForTrip) {
        this.removeUsersForTrip = removeUsersForTrip;
    }
}
