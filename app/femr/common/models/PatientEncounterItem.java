package femr.common.models;

import java.util.ArrayList;
import java.util.List;

public class PatientEncounterItem {
    private int id;
    private int patientId;
    private List<String> chiefComplaints;
    private Integer weeksPregnant;
    private Boolean isClosed;

    private String triageDateOfVisit;
    private String nurseEmailAddress;

    private String medicalDateOfVisit;
    private String physicianEmailAddress;

    private String pharmacyDateOfVisit;
    private String pharmacistEmailAddress;

    public PatientEncounterItem(){
        this.chiefComplaints = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public List<String> getChiefComplaints() {
        return chiefComplaints;
    }

    public void setChiefComplaints(List<String> chiefComplaints) {
        this.chiefComplaints = chiefComplaints;
    }

    public void addChiefComplaint(String chiefComplaint){
        chiefComplaints.add(chiefComplaint);
    }

    public Integer getWeeksPregnant() {
        return weeksPregnant;
    }

    public void setWeeksPregnant(Integer weeksPregnant) {
        this.weeksPregnant = weeksPregnant;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getTriageDateOfVisit() {
        return triageDateOfVisit;
    }

    public void setTriageDateOfVisit(String triageDateOfVisit) {
        this.triageDateOfVisit = triageDateOfVisit;
    }

    public String getNurseEmailAddress() {
        return nurseEmailAddress;
    }

    public void setNurseEmailAddress(String nurseEmailAddress) {
        this.nurseEmailAddress = nurseEmailAddress;
    }

    public String getPhysicianEmailAddress() {
        return physicianEmailAddress;
    }

    public void setPhysicianEmailAddress(String physicianEmailAddress) {
        this.physicianEmailAddress = physicianEmailAddress;
    }

    public String getPharmacistEmailAddress() {
        return pharmacistEmailAddress;
    }

    public void setPharmacistEmailAddress(String pharmacistEmailAddress) {
        this.pharmacistEmailAddress = pharmacistEmailAddress;
    }

    public String getMedicalDateOfVisit() {
        return medicalDateOfVisit;
    }

    public void setMedicalDateOfVisit(String medicalDateOfVisit) {
        this.medicalDateOfVisit = medicalDateOfVisit;
    }

    public String getPharmacyDateOfVisit() {
        return pharmacyDateOfVisit;
    }

    public void setPharmacyDateOfVisit(String pharmacyDateOfVisit) {
        this.pharmacyDateOfVisit = pharmacyDateOfVisit;
    }
}
