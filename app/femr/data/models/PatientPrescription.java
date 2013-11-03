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
    private int replacementId;

    @Override
    public int getId(){
        return id;
    }

    @Override
    public int getPatientEncounterId() {
        return encounterId;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void SetAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public Boolean isReplace() {
        return replaced;
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
    public int getReplacement() {
        return replacementId;
    }

    @Override
    public void SetReplacement(int replacementMedication) {
        this.replacementId = replacementMedication;
    }


}
