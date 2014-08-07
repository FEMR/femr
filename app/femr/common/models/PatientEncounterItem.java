package femr.common.models;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class PatientEncounterItem {
    private int id;
    private int patientId;
    private int userId;
    private List<String> chiefComplaints;
    private Integer weeksPregnant;
    private Boolean isClosed;
    private DateTime dateOfVisit;
    private String friendlyDateOfVisit;
    private String doctorName;
    private String pharmacistName;

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

    public DateTime getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(DateTime dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPharmacistName() {
        return pharmacistName;
    }

    public void setPharmacistName(String pharmacistName) {
        this.pharmacistName = pharmacistName;
    }

    public String getFriendlyDateOfVisit() {
        return friendlyDateOfVisit;
    }

    public void setFriendlyDateOfVisit(String friendlyDateOfVisit) {
        this.friendlyDateOfVisit = friendlyDateOfVisit;
    }
}
