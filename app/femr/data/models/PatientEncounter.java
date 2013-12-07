package femr.data.models;

import femr.common.models.IPatientEncounter;
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
    private String dateOfVisit;
    @Column(name = "chief_complaint", nullable = true)
    private String chiefComplaint;
    @Column(name = "is_pregnant", nullable = true)
    private Boolean isPregnant;

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
    public Boolean getIsPregnant() {
        return isPregnant;
    }

    @Override
    public void setIsPregnant(Boolean isPregnant) {
        this.isPregnant = isPregnant;
    }
}
