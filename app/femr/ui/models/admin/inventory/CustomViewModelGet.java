package femr.ui.models.admin.inventory;

import femr.common.models.MissionTripItem;

import java.util.List;

/**
 * Created by kevin on 14/05/17.
 */
public class CustomViewModelGet {

    private List<String> availableUnits;
    private List<String> availableForms;
    private MissionTripItem missionTripItem;

    public List<String> getAvailableUnits() {
        return availableUnits;
    }

    public void setAvailableUnits(List<String> availableUnits) {
        this.availableUnits = availableUnits;
    }

    public List<String> getAvailableForms() {
        return availableForms;
    }

    public void setAvailableForms(List<String> availableForms) {
        this.availableForms = availableForms;
    }

    public MissionTripItem getMissionTripItem() {
        return missionTripItem;
    }

    public void setMissionTripItem(MissionTripItem missionTripItem) {
        this.missionTripItem = missionTripItem;
    }
}
