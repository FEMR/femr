package femr.data.models;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name="patient_encounter_tab_fields")
public class PatientEncounterTabField implements IPatientEncounterTabField{
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "patient_encounter_id", nullable = false)
    private int patientEncounterId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tab_field_id", nullable = false)
    private TabField tabField;
    @Column(name = "tab_field_value", nullable = false)
    private String tabFieldValue;
    @Column(name = "date_taken", nullable = false)
    private DateTime dateTaken;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chief_complaint_id", nullable = true)
    private ChiefComplaint chiefComplaint;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
    public int getPatientEncounterId() {
        return patientEncounterId;
    }

    @Override
    public void setPatientEncounterId(int patientEncounterId) {
        this.patientEncounterId = patientEncounterId;
    }

    @Override
    public ITabField getTabField() {
        return tabField;
    }

    @Override
    public void setTabField(ITabField tabField) {
        this.tabField = (TabField) tabField;
    }

    @Override
    public String getTabFieldValue() {
        return tabFieldValue;
    }

    @Override
    public void setTabFieldValue(String tabFieldValue) {
        this.tabFieldValue = tabFieldValue;
    }

    @Override
    public DateTime getDateTaken() {
        return dateTaken;
    }

    @Override
    public void setDateTaken(DateTime dateTaken) {
        this.dateTaken = dateTaken;
    }

    @Override
    public ChiefComplaint getChiefComplaint() {
        return chiefComplaint;
    }

    @Override
    public void setChiefComplaint(IChiefComplaint chiefComplaint) {
        this.chiefComplaint = (ChiefComplaint) chiefComplaint;
    }
}
