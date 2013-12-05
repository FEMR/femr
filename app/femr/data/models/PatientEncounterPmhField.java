package femr.data.models;

import femr.common.models.IPatientEncounterHpiField;
import femr.common.models.IPatientEncounterPmhField;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patient_encounter_pmh_fields")
public class PatientEncounterPmhField implements IPatientEncounterPmhField {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "patient_encounter_id", nullable = false)
    private int patientEncounterId;
    @Column(name = "pmh_field_id", nullable = false)
    private int pmhFieldId;
    @Column(name = "pmh_field_value", nullable = false)
    private String pmhFieldValue;
    @Column(name = "date_taken", nullable = false)
    private DateTime dateTaken;

    @Override
    public int getId() {
        return id;
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
    public int getPmhFieldId() {
        return pmhFieldId;
    }

    @Override
    public void setPmhFieldId(int pmhFieldId) {
        this.pmhFieldId = pmhFieldId;
    }

    @Override
    public String getPmhFieldValue() {
        return pmhFieldValue;
    }

    @Override
    public void setPmhFieldValue(String pmhFieldValue) {
        this.pmhFieldValue = pmhFieldValue;
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
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
