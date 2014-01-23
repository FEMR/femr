package femr.data.models;

import femr.common.models.IPatientEncounterTreatmentField;
import femr.common.models.ITreatmentField;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "patient_encounter_treatment_fields")
public class PatientEncounterTreatmentField implements IPatientEncounterTreatmentField {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "patient_encounter_id", nullable = false)
    private int patientEncounterId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="treatment_field_id", nullable = false)
    private TreatmentField treatmentField;
    @Column(name = "treatment_field_value", nullable = false)
    private String treatmentFieldValue;
    @Column(name = "date_taken", nullable = false)
    private DateTime dateTaken;

    @Override
    public int getId() {
        return id;
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
    public ITreatmentField getTreatmentField() {
        return treatmentField;
    }

    @Override
    public void setTreatmentField(ITreatmentField treatmentField) {
        this.treatmentField = (TreatmentField)treatmentField;
    }

    @Override
    public String getTreatmentFieldValue() {
        return treatmentFieldValue;
    }

    @Override
    public void setTreatmentFieldValue(String treatmentFieldValue) {
        this.treatmentFieldValue = treatmentFieldValue;
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
