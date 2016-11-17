package femr.ui.models.manager;

import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;


public class EditViewModelGet {

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



    private List<PatientEncounterItem> encounter;

    public void setPatientEncounter(List<PatientEncounterItem> patientEncounter) {
        this.encounter = patientEncounter;
    }
    // gets array list item of Patient Items

    public PatientEncounterItem getEncounter (int i){
        return encounter.get(i);
    }

    // gets array list of Patient Items
    public List<PatientEncounterItem> getEncounter() {
        return encounter;
    }

    public String getToday()
    {
        Date todayDate= DateTime.now().toDate();
        String currentDate= dateUtils.getFriendlyDate(todayDate);
        return currentDate;
    }

}
