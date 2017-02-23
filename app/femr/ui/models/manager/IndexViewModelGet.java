package femr.ui.models.manager;

import femr.common.models.PatientEncounterItem;

import java.util.List;

//Manages Manger Controller
public class IndexViewModelGet {

    //store list of patient encounter itesm
    private List<PatientEncounterItem> encounterItem;

    private String userFriendlyDate;

    private String userFriendlyTrip;

    // Sets patient encounter items
    public void setPatientEncounter(List<PatientEncounterItem> patientEncounter) {
        this.encounterItem = patientEncounter;
    }
    // gets array list of Patient Encounter Items
    public List<PatientEncounterItem> getEncounter() {
        return encounterItem;
    }

    public String getUserFriendlyDate() {
        return userFriendlyDate;
    }

    public void setUserFriendlyDate(String userFriendlyDate) {
        this.userFriendlyDate = userFriendlyDate;
    }

    public String getUserFriendlyTrip() {
        return userFriendlyTrip;
    }

    public void setUserFriendlyTrip(String userFriendlyTrip) {
        this.userFriendlyTrip = userFriendlyTrip;
    }
}
