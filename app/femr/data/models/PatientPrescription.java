package femr.data.models;

import femr.common.models.IPatientPrescription;
import javax.persistence.*;

@Entity
@Table(name = "patient_prescriptions")
public class PatientPrescription implements IPatientPrescription {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "encounter_id", nullable = false)
    private int encounterId;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "amount", nullable = true)
    private  int amount;
    @Column(name = "replaced", nullable = false)
    private Boolean replaced;
    @Column(name = "reason", nullable = true)
    private String reason;
    @Column(name = "replacement_id", nullable = true)
    private Integer replacementId;
    @Column(name = "medication_name", nullable = false)
    private String medicationName;
    @Column(name = "date_taken", nullable = false)
    private String dateTaken;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getEncounterId() {
        return encounterId;
    }

    @Override
    public void setEncounterId(int encounterId) {
        this.encounterId = encounterId;
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
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public Boolean getReplaced() {
        return replaced;
    }

    @Override
    public void setReplaced(Boolean replaced) {
        this.replaced = replaced;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public Integer getReplacementId() {
        return replacementId;
    }

    @Override
    public void setReplacementId(Integer replacementId) {
        this.replacementId = replacementId;
    }

    @Override
    public String getMedicationName() {
        return medicationName;
    }

    @Override
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    @Override
    public String getDateTaken() {
        return dateTaken;
    }

    @Override
    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }
}
