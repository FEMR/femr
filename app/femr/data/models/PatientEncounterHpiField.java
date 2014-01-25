package femr.data.models;

import femr.common.models.IHpiField;
import femr.common.models.IPatientEncounterHpiField;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "patient_encounter_hpi_fields")
public class PatientEncounterHpiField implements IPatientEncounterHpiField {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "patient_encounter_id", nullable = false)
    private int patientEncounterId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hpi_field_id", nullable = false)
    private HpiField hpiField;
    @Column(name = "hpi_field_value", nullable = false)
    private String hpiFieldValue;
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
    public IHpiField getHpiField() {
        return hpiField;
    }

    @Override
    public void setHpiField(IHpiField hpiField) {
        this.hpiField = (HpiField)hpiField;
    }

    @Override
    public String getHpiFieldValue() {
        return hpiFieldValue;
    }

    @Override
    public void setHpiFieldValue(String hpiFieldValue) {
        this.hpiFieldValue = hpiFieldValue;
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
