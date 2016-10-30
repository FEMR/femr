package femr.ui.models.triage;

import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.data.models.core.IPatient;

import java.util.List;

/**
 * Created by fq251 on 10/22/2016.
 */
public class ManageViewModelPost{

    // sets array list of Patient Items
    private List<PatientItem> triagePatients;

    public void setTriagePatients(List<PatientItem> patient) {
        this.triagePatients = patient;
    }
    // gets array list item of Patient Items

    public PatientItem getTriagePatients (int i){
        return triagePatients.get(i);
    }

    // gets array list of Patient Items
    public List<PatientItem> getTriagePatients() {
        return triagePatients;
    }


    private List<PatientEncounterItem> encounterItems;

    public void setEncounterItems(List<PatientEncounterItem> encounterItems) {
        this.encounterItems = encounterItems;
    }
    // gets array list item of Patient Items

    public PatientEncounterItem getEncounterItems (int i){
        return encounterItems.get(i);
    }

    // gets array list of Patient Items
    public List<PatientEncounterItem> getEncounterItems() {
        return encounterItems;
    }

    private List<IPatient> patients;


    public void setPatients(List<IPatient> patients) {
        this.patients = patients;
    }
    // gets array list item of Patient Items

    public IPatient getPatients (int i){
        return patients.get(i);
    }

    // gets array list of Patient Items
    public List<IPatient> getPatients() {
        return patients;
    }
}
