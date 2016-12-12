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

    //stores list of triage CheckIn Time
    private List<String> timeOfTriageVisit;
    //stores list of pharmacy CheckIn Time
    private List<String> timeOfPharmVisit;
    //stores list of medical CheckIn Time
    private List<String> timeOfMedicalVisit;
    //stores list of triage patients
    private List<PatientItem> triagePatients;
    //stores list of patient encounters
    private List<PatientEncounter> encounter;
    //stores list of vital maps
    private List<VitalMultiMap> vitalItems;
    //store list of patient encounter itesm
    private List<PatientEncounterItem> encounterItem;
    List<Map<String, TabFieldItem>> tab = new ArrayList<Map<String, TabFieldItem>>();

    //sets a list of triage visit times
    public void setTimeOfTriageVisit(List<String> dateOfTriageVisit) {

        this.timeOfTriageVisit = dateOfTriageVisit;
    }

    //returns a specific String from timeOfTriagePatient array
    public String getTimeOfTriageVisit(int i) {

        return timeOfTriageVisit.get(i);
    }

    //sets list of pharmacy visit times
    public void setTimeOfPharmVisit(List<String> dateOfTPharmVisit) {

        this.timeOfPharmVisit = dateOfTPharmVisit;
    }

    //returns a specific String from time of pharmacy visit array list
    public String getTimeOfPharmVisit(int i) {

        return timeOfPharmVisit.get(i);
    }

    //returns a specific time of medical visit
    public String getTimeOfMedicalVisit(int i) {

        return timeOfMedicalVisit.get(i);
    }

    //sets list of time of medical visit
    public void setTimeOfMedicalVisit(List<String> timeOfMedicalVisit) {

        this.timeOfMedicalVisit = timeOfMedicalVisit;
    }

    //sets List of Triage Patients
    public void setTriagePatients(List<PatientItem> patient) {

        this.triagePatients = patient;
    }

    //gets a specific PatientItem in triagePatient List
    public PatientItem getTriagePatients(int i) {
        return triagePatients.get(i);
    }

    //returns a list of PatientItem
    public List<PatientItem> getTriagePatients() {
        return triagePatients;
    }

    // Sets patient encounter items
    public void setPatientEncounter(List<PatientEncounterItem> patientEncounter) {
        this.encounterItem = patientEncounter;
    }

    // gets array list item of Patient Encounter Items
    public PatientEncounterItem getEncounter(int i) {
        return encounterItem.get(i);
    }

    // gets array list of Patient Encounter Items
    public List<PatientEncounterItem> getEncounter() {
        return encounterItem;
    }

    //sets a list of HPI Items
    public void setHPI(List<Map<String, TabFieldItem>> tab) {
        this.tab = tab;
    }

    // gets array list item of HPI Tab Mapped Items
    public Map<String, TabFieldItem> getHPI(int i) {
        return tab.get(i);
    }

    // gets array list of HPI Items
    public List<Map<String, TabFieldItem>> getHpi() {
        return tab;
    }

    //set Vital Multimap List
    public void setVitals(List<VitalMultiMap> vital) {
        this.vitalItems = vital;
    }

    // gets array list item of vital Items
    public VitalMultiMap getVitals(int i) {
        return vitalItems.get(i);
    }

    // gets array list of Vital Items
    public List<VitalMultiMap> getVital() {
        return vitalItems;
    }

    //returns the turnAroundTime for each patient encounter
    public String getTurnAroundT(PatientEncounterItem item) {
        return dateUtils.getTurnAroundTime(item);
    }

    //returns date in friendly format
    public String getToday() {
        return dateUtils.getFriendlyDate(DateTime.now().toDate());

    }

}
