package femr.ui.models.manager;

import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.data.models.mysql.PatientEncounter;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class EditViewModelGet {

    // sets array list of Patient Items
    private List<PatientItem> triagePatients;
    private List<PatientEncounter> encounter;


    PatientEncounter patientEncounter = new PatientEncounter();
    public String getPharmTime(PatientEncounterItem item) {
        String pharmTime;
        if(item.getPharmacyDateOfVisit()!=null) {
            String[] parts = item.getPharmacyDateOfVisit().split("-");
            pharmTime = parts[1];
            return pharmTime;
        }
       else
           return " ";
    }

    public String getTriageTime(PatientEncounterItem item) {
        String triageTime;
        if(item.getTriageDateOfVisit()!=null) {
            String[] parts = item.getTriageDateOfVisit().split("-");
            triageTime = parts[1];
            return triageTime;
        }
        else
            return " ";
    }
    public String getMedicalTime(PatientEncounterItem item) {
        String medicalTime;
        if(item.getMedicalDateOfVisit()!=null) {
            String[] parts = item.getMedicalDateOfVisit().split("-");
            medicalTime = parts[1];
            return medicalTime;
        }
        else
            return " ";
    }

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



    private List<PatientEncounterItem> encounterItem;

    public void setPatientEncounter(List<PatientEncounterItem> patientEncounter) {
        this.encounterItem = patientEncounter;
    }
    // gets array list item of Patient Items

    public PatientEncounterItem getEncounter (int i){
        return encounterItem.get(i);
    }

    // gets array list of Patient Items
    public List<PatientEncounterItem> getEncounter() {
        return encounterItem;
    }

    public String getToday()
    {
        Date todayDate= DateTime.now().toDate();
        String currentDate= dateUtils.getFriendlyDate(todayDate);
        return currentDate;
    }

    public String turnAroundTime(PatientEncounterItem item){
        PatientEncounter patientEncounter = new PatientEncounter();
        String time=" ";
        if(item.getPharmacyDateOfVisit()!= null &&item.getTriageDateOfVisit()!=null) {
            SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
            try {
                Date td,pd;
                td = ft.parse(getTriageTime(item));
                pd= ft.parse(getPharmTime(item));
                long diff = pd.getTime() - td.getTime();
                String ss = String.valueOf(diff / 1000 % 60);
                String sm = String.valueOf(diff / (60 * 1000) % 60);
                String sh = String.valueOf(diff / (60 * 60 * 1000));
                time="Hours: "+sh+" Minutes: " +sm+"  Seconds: "+ss;
            }catch (ParseException e) {
                System.out.println("Unparseable using " + ft);
            }
            return time;
        }
        else {
            return time;
        }
    }

}
