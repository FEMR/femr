package femr.ui.models.manager;

import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.common.models.TabFieldItem;
import femr.data.models.mysql.PatientEncounter;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//Manages Manger Controller
public class IndexViewModelGet {

    // sets array list of Patient Items
    private List<PatientItem> triagePatients;
    private List<PatientEncounter> encounter;
    private List<VitalMultiMap> vitalItems;
    private List<PatientEncounterItem> encounterItem;
    List<Map<String, TabFieldItem>> tab = new ArrayList<Map<String, TabFieldItem>>();

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
    public DateTime getTodayDate()
    {
        return  DateTime.now();

    }
    //patient encounter items
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

    public void setHPI(List<Map<String, TabFieldItem>> tab) {
        this.tab = tab;
    }
    // gets array list item of HPI Tab Mapped Items

    public Map<String, TabFieldItem>  getHPI(int i){
        return tab.get(i);
    }

    // gets array list of HPI Items
    public List<Map<String, TabFieldItem>> getHpi() {
        return tab;
    }

    public void setVitals(List<VitalMultiMap> vital) {
        this.vitalItems = vital;
    }
    // gets array list item of vital Items

    public VitalMultiMap getVitals (int i){
        return vitalItems.get(i);
    }

    // gets array list of Vital Items
    public List<VitalMultiMap> getVital() {
        return vitalItems;
    }
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
}
