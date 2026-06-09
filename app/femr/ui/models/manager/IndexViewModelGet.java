package femr.ui.models.manager;

import femr.common.models.PatientEncounterItem;
import femr.common.models.SettingItem;

import java.util.List;

//Manages Manger Controller
public class IndexViewModelGet {

    //store list of patient encounter itesm
    private List<PatientEncounterItem> encounterItem;

    private String isoDate;

    private String userFriendlyTrip;

    // Sets patient encounter items
    public void setPatientEncounter(List<PatientEncounterItem> patientEncounter) {
        this.encounterItem = patientEncounter;
    }
    // gets array list of Patient Encounter Items
    public List<PatientEncounterItem> getEncounter() {
        return encounterItem;
    }

    public String getIsoDate() {
        return isoDate;
    }

    public void setIsoDate(String isoDate) {
        this.isoDate = isoDate;
    }

    public String getUserFriendlyTrip() {
        return userFriendlyTrip;
    }

    public void setUserFriendlyTrip(String userFriendlyTrip) {
        this.userFriendlyTrip = userFriendlyTrip;
    }

    private SettingItem settings;

    public SettingItem getSettings() {
        return settings;
    }

    public void setSettings(SettingItem settings) {
        this.settings = settings;
    }
}
