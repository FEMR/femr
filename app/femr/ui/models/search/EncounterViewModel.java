package femr.ui.models.search;


import femr.common.models.*;
import femr.util.DataStructure.VitalMultiMap;
import femr.util.DataStructure.Pair;
import java.util.List;
import java.util.Map;


public class EncounterViewModel {

    //patient
    private PatientItem patientItem;
    private PatientEncounterItem patientEncounterItem;
    private List<PhotoItem> photos;

    //tabs and tab fields
    private List<TabItem> customTabs;
    private Map<String, List<TabFieldItem>> customFields;
    protected Map<String, TabFieldItem> staticFields;

    // List of Vitals
    private VitalMultiMap vitalList;

    //medications
    private String prescription1;
    private String prescription2;
    private String prescription3;
    private String prescription4;
    private String prescription5;
    private String[] medications;
    private List<Pair<String,String>> medicationAndReplacement;   // holds a list of medication and the replacement meds if it exist

    // Get the doctor's name who saw the patient at medical station
    private String doctorFirstName;
    private String doctorLastName;

    // the Pharmacist who gave the meds
    private String pharmacistFirstName;
    private String pharmacistLastName;







    public List<Pair<String, String>> getMedicationAndReplacement() { return medicationAndReplacement; }

    public void setMedicationAndReplacement(List<Pair<String, String>> medicationAndReplacement) {
        this.medicationAndReplacement = medicationAndReplacement;
    }

    // Getters and setters for the doctors name
    public String getDoctorFirstName() { return doctorFirstName; }

    public void setDoctorFirstName(String doctorFirstName) { this.doctorFirstName = doctorFirstName; }

    public String getDoctorLastName() { return doctorLastName; }

    public void setDoctorLastName(String doctorLastName) { this.doctorLastName = doctorLastName; }

    // getters and setters for pharmacist name
    public String getPharmacistFirstName() { return pharmacistFirstName; }

    public void setPharmacistFirstName(String pharmacistFirstName) { this.pharmacistFirstName = pharmacistFirstName; }

    public String getPharmacistLastName() { return pharmacistLastName; }

    public void setPharmacistLastName(String pharmacistLastName) { this.pharmacistLastName = pharmacistLastName; }


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

    public List<PhotoItem> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoItem> photos) {
        this.photos = photos;
    }

    public List<TabItem> getCustomTabs() {
        return customTabs;
    }

    public void setCustomTabs(List<TabItem> customTabs) {
        this.customTabs = customTabs;
    }

    public Map<String, List<TabFieldItem>> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, List<TabFieldItem>> customFields) {
        this.customFields = customFields;
    }

    public Map<String, TabFieldItem> getStaticFields() {
        return staticFields;
    }

    public void setStaticFields(Map<String, TabFieldItem> staticFields) {
        this.staticFields = staticFields;
    }

    public VitalMultiMap getVitalList() {
        return vitalList;
    }

    public void setVitalList(VitalMultiMap vitalList) {
        this.vitalList = vitalList;
    }

    public String[] getMedications() {
        return medications;
    }

    public void setMedications(String[] medications) {
        this.medications = medications;
    }

    public String getPrescription1() {
        return prescription1;
    }

    public void setPrescription1(String prescription1) {
        this.prescription1 = prescription1;
    }

    public String getPrescription2() {
        return prescription2;
    }

    public void setPrescription2(String prescription2) {
        this.prescription2 = prescription2;
    }

    public String getPrescription3() {
        return prescription3;
    }

    public void setPrescription3(String prescription3) {
        this.prescription3 = prescription3;
    }

    public String getPrescription4() {
        return prescription4;
    }

    public void setPrescription4(String prescription4) {
        this.prescription4 = prescription4;
    }

    public String getPrescription5() {
        return prescription5;
    }

    public void setPrescription5(String prescription5) {
        this.prescription5 = prescription5;
    }
}
