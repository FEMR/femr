package femr.data.models;

import femr.common.models.IPatient;
import femr.common.models.IPatientResearch;

import javax.persistence.*;
import java.util.List;

/**
 * Creates the model that will store the return information from a research query
 */
@Entity
@Table(name = "patient_encounters")
public class PatientResearch implements IPatientResearch{

    // fields from patient_encounter table
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "patient_id", nullable = false)
    private int patientId;
    @Column(name = "date_of_visit", nullable = false)
    private String dateOfVisit;
    @Column(name = "chief_complaint", nullable = true)
    private String chiefComplaint;

    // fields from patient table
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Patient.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "patients",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "age", referencedColumnName = "id")})
    private List<IPatient> patient;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getPatientId() {
        return patientId;
    }

    @Override
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Override
    public String getDateOfVisit() {
        return dateOfVisit;
    }

    @Override
    public void setDateOfVisit(String dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    @Override
    public String getChiefComplaint() {
        return chiefComplaint;
    }

    @Override
    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    @Override
    public List<IPatient> getPatient() {
        return patient;
    }

    @Override
    public void setPatient(List<IPatient> patient) {
        this.patient = patient;
    }
    
}
