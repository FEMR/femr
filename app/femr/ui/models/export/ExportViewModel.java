package femr.ui.models.export;

import femr.common.models.MissionItem;

import java.util.ArrayList;
import java.util.List;

public class ExportViewModel {

    private List<MissionItem> missionItems = new ArrayList<>();

    public List<MissionItem> getAllMissionItems() {
        return missionItems;
    }

    public void setAllMissionItems(List<MissionItem> allMissionTrips) {
        this.missionItems = allMissionTrips;
    }
}
