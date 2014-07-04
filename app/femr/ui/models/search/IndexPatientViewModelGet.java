package femr.ui.models.search;

import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;

import java.util.List;

public class IndexPatientViewModelGet {
    //used to show a list of alternative patients that also
    //match the users search criteria
    private List<PatientItem> patientItems;
    //used for the patient that is being shown no matter what
    private PatientItem patientItem;
    //patient encounters available for the patient
    private List<PatientEncounterItem> patientEncounterItems;



    public List<PatientItem> getPatientItems() {
        return patientItems;
    }

    public void setPatientItems(List<PatientItem> patientItems) {
        this.patientItems = patientItems;
    }

    public PatientItem getPatientItem() {
        return patientItem;
    }

    public void setPatientItem(PatientItem patientItem) {
        this.patientItem = patientItem;
    }

    public List<PatientEncounterItem> getPatientEncounterItems() {
        return patientEncounterItems;
    }

    public void setPatientEncounterItems(List<PatientEncounterItem> patientEncounterItems) {
        this.patientEncounterItems = patientEncounterItems;
    }
}
