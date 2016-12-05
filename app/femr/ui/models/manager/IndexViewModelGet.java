package femr.ui.models.manager;

import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.common.models.TabFieldItem;
import femr.data.models.mysql.PatientEncounter;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
