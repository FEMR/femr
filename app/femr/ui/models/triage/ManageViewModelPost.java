package femr.ui.models.triage;

import femr.common.models.PatientItem;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

//Manages Triage Patient Controller
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

    public String getToday()
    {
        Date todayDate= DateTime.now().toDate();
        String currentDate= dateUtils.getFriendlyDate(todayDate);
        return currentDate;
    }

}
