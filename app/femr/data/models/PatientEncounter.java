package femr.data.models;

import femr.common.models.IPatientEncounter;
import femr.common.models.IUser;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "patient_encounters")
public class PatientEncounter implements IPatientEncounter {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "patient_id", nullable = false)
    private int patientId;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "date_of_visit", nullable = false)
    private DateTime dateOfVisit;
    @Column(name = "chief_complaint", nullable = true)
    private String chiefComplaint;
    @Column(name = "weeks_pregnant", nullable = true)
    private Integer weeksPregnant;
    @Column(name = "date_of_medical_visit", nullable = true)
    private DateTime dateOfMedicalVisit;
    @Column(name = "date_of_pharmacy_visit", nullable = true)
    private DateTime dateOfPharmacyVisit;
    @Column(name= "user_id_medical", nullable = true)
    private User doctor;
    @Column(name= "user_id_pharmacy", nullable = true)
    private User pharmacist;

    @Override
    public int getId() {
        return id;
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
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
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
    public Integer getWeeksPregnant() {
        return weeksPregnant;
    }

    @Override
    public void setWeeksPregnant(Integer weeksPregnant) {
        this.weeksPregnant = weeksPregnant;
    }

    @Override
    public DateTime getDateOfVisit() {
        return dateOfVisit;
    }

    @Override
    public void setDateOfVisit(DateTime dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    @Override
    public DateTime getDateOfMedicalVisit() {
        return dateOfMedicalVisit;
    }

    @Override
    public void setDateOfMedicalVisit(DateTime dateOfMedicalVisit) {
        this.dateOfMedicalVisit = dateOfMedicalVisit;
    }

    @Override
    public DateTime getDateOfPharmacyVisit() {
        return dateOfPharmacyVisit;
    }

    @Override
    public void setDateOfPharmacyVisit(DateTime dateOfPharmacyVisit) {
        this.dateOfPharmacyVisit = dateOfPharmacyVisit;
    }

    @Override
    public IUser getDoctor() {
        return doctor;
    }

    @Override
    public void setDoctor(IUser doctor) {
        this.doctor = (User) doctor;
    }

    @Override
    public IUser getPharmacist() {
        return pharmacist;
    }

    @Override
    public void setPharmacist(IUser pharmacist) {
        this.pharmacist = (User) pharmacist;
    }
}
