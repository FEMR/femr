package femr.ui.models.export;

import java.util.ArrayList;
import java.util.List;

public class FilterViewModel {

    private List<Integer> missionTripIds = new ArrayList<>();

    public void setMissionTripIds(List<Integer> missionTripIds) {
        this.missionTripIds = missionTripIds;
    }

    public List<Integer> getMissionTripIds() {
        return missionTripIds;
    }
}
