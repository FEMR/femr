package femr.data.models.research;

import femr.common.models.research.IPatientEncounter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Implements the research patient encounter subset of the full patient encounter
 */
@Entity
@Table(name = "patient_encounters")
public class PatientEncounter implements IPatientEncounter {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "patient_id", nullable = false)
    private int patientId;
    @Column(name = "date_of_visit", nullable = false)
    private String dateOfVisit;
    @Column(name = "chief_complaint", nullable = true)
    private String chiefComplaint;

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(String dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }
}
