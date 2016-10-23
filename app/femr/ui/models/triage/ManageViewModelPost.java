package femr.ui.models.triage;

import femr.common.models.PatientItem;

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
}
