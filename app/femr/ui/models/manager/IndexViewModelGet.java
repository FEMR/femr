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

import static femr.util.calculations.dateUtils.getDisplayTime;

//Manages Manger Controller
public class IndexViewModelGet {

    //private String TriageTime=getDisplayTime(item.getTriageDateOfVisit());
    private List<PatientItem> triagePatients;
    private List<PatientEncounter> encounter;
    private List<VitalMultiMap> vitalItems;
    private List<PatientEncounterItem> encounterItem;
    List<Map<String, TabFieldItem>> tab = new ArrayList<Map<String, TabFieldItem>>();
    public void setTriagePatients(List<PatientItem> patient) {
        this.triagePatients = patient;
    }
    public PatientItem getTriagePatients (int i){
        return triagePatients.get(i);
    }
    public List<PatientItem> getTriagePatients() {
        return triagePatients;
    }
    // Sets patient encounter items
    public void setPatientEncounter(List<PatientEncounterItem> patientEncounter) {this.encounterItem = patientEncounter;}
    // gets array list item of Patient Encounter Items
    public PatientEncounterItem getEncounter (int i){
        return encounterItem.get(i);
    }
    // gets array list of Patient Encounter Items
    public List<PatientEncounterItem> getEncounter() {
        return encounterItem;
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
    public String getIriageTime(PatientEncounterItem item)
    {
        return getDisplayTime(item.getTriageDateOfVisit());
    }
    public String getMedicalTime(PatientEncounterItem item)
    {
        return getDisplayTime(item.getMedicalDateOfVisit());
    }
    public String getPharmTime(PatientEncounterItem item)
    {
        return getDisplayTime(item.getPharmacyDateOfVisit());
    }
    public String getTurnAroundT(PatientEncounterItem item)
    {
        return dateUtils.getTurnAroundTime(item);
    }
    public String getToday()
    {
        return dateUtils.getFriendlyDate(DateTime.now().toDate());

    }
    public DateTime getTodayDate()
    {
        return  DateTime.now();
    }
}
