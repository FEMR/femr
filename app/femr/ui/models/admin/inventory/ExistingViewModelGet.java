package femr.ui.models.admin.inventory;

import femr.common.models.MedicationItem;
import femr.common.models.MissionTripItem;

import java.util.List;

/**
 * Created by kevin on 14/05/17.
 */
public class ExistingViewModelGet {

    private List<MedicationItem> conceptMedications;
    private MissionTripItem missionTripItem;

    public List<MedicationItem> getConceptMedications() {
        return conceptMedications;
    }

    public void setConceptMedications(List<MedicationItem> conceptMedications) {
        this.conceptMedications = conceptMedications;
    }

    public MissionTripItem getMissionTripItem() {
        return missionTripItem;
    }

    public void setMissionTripItem(MissionTripItem missionTripItem) {
        this.missionTripItem = missionTripItem;
    }
}
