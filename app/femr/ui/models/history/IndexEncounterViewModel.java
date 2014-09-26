package femr.ui.models.history;

import femr.common.models.*;

public class IndexEncounterViewModel {

    //patient
    private PatientItem patientItem;
    //patient encounter
    private PatientEncounterItem patientEncounterItem;


    public PatientItem getPatientItem() {
        return patientItem;
    }

    public void setPatientItem(PatientItem patientItem) {
        this.patientItem = patientItem;
    }

    public PatientEncounterItem getPatientEncounterItem() {
        return patientEncounterItem;
    }

    public void setPatientEncounterItem(PatientEncounterItem patientEncounterItem) {
        this.patientEncounterItem = patientEncounterItem;
    }
}
