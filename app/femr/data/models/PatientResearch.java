package femr.data.models;

import com.avaje.ebean.annotation.Sql;
import femr.common.models.IPatient;
import femr.common.models.IPatientResearch;
import femr.common.models.research.IPatientPrescription;
import femr.data.models.research.*;
import femr.data.models.research.PatientPrescription;
import femr.ui.controllers.research.ResearchDataModel;

import javax.persistence.*;
import java.util.List;

/**
 * Creates the model that will store the return information from a research query
 * This uses RowSql
 *
 * //@Table(name = "patient_encounter")
 */
@Entity
@Sql
public class PatientResearch implements IPatientResearch {

    @OneToMany
    ResearchDataModel researchDataModel;

    public ResearchDataModel getResearchDataModel() {
        return researchDataModel;
    }

    public void setResearchDataModel(ResearchDataModel researchDataModel) {
        this.researchDataModel = researchDataModel;
    }

    /*
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "patient_id", nullable = false)
    private int patientId;
    @Column(name = "date_of_visit", nullable = false)
    private String dateOfVisit;
    @Column(name = "chief_complaint", nullable = true)
    private String chiefComplaint;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = femr.data.models.research.Patient.class,
                cascade = CascadeType.ALL)
    @JoinTable(
            name = "patients",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "patient_id")},
            inverseJoinColumns = {@JoinColumn(name = "id", referencedColumnName = "patient_id")})
    private List<femr.common.models.research.IPatient> patients;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = PatientPrescription.class,
                cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_prescriptions",
            joinColumns = {@JoinColumn(name = "encounter_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "encounter_id", referencedColumnName = "id")})
    private List<IPatientPrescription> patientPrescriptions;


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

    public List<femr.common.models.research.IPatient> getPatients() {
        return patients;
    }

    public void setPatients(List<femr.common.models.research.IPatient> patients) {
        this.patients = patients;
    }

    public List<IPatientPrescription> getPatientPrescriptions() {
        return patientPrescriptions;
    }

    public void setPatientPrescriptions(List<IPatientPrescription> patientPrescriptions) {
        this.patientPrescriptions = patientPrescriptions;
    }

    */
}
