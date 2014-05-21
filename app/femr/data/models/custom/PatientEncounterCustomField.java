package femr.data.models.custom;

import femr.common.models.custom.IPatientEncounterCustomField;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="patient_encounter_custom_fields")
public class PatientEncounterCustomField  implements IPatientEncounterCustomField{
    @Id
    @Column(name ="id", unique=true, nullable = false)
    private int id;
    @Column(name="user_id", nullable = false)
    private int userId;
    @Column(name="patient_encounter_id", nullable = false)
    private int patientEncounterId;
    @Column(name="custom_field_id", nullable = false)
    private int customFieldId;
    @Column(name="custom_field_value", nullable = false)
    private String customFieldValue;
    @Column(name="date_taken", nullable = false)
    private DateTime dateTaken;

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
    public int getCustomFieldId() {
        return customFieldId;
    }

    @Override
    public void setCustomFieldId(int customFieldId) {
        this.customFieldId = customFieldId;
    }

    @Override
    public String getCustomFieldValue() {
        return customFieldValue;
    }

    @Override
    public void setCustomFieldValue(String customFieldValue) {
        this.customFieldValue = customFieldValue;
    }

    @Override
    public DateTime getDateTaken() {
        return dateTaken;
    }

    @Override
    public void setDateTaken(DateTime dateTaken) {
        this.dateTaken = dateTaken;
    }
}
