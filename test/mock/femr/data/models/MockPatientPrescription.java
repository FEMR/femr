package mock.femr.data.models;

import femr.common.models.IPatientPrescription;
import org.joda.time.DateTime;

public class MockPatientPrescription implements IPatientPrescription {
    private int id = 0;
    private int encounterId = 0;
    private int userId = 0;
    private int amount = 0;
    private Boolean replaced = false;
    private String reason = "";
    private Integer replacementId = 0;
    private String medicationName = "";
    private DateTime dateTaken;

    public void setId(int id){this.id = id;}

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
    public DateTime getDateTaken() {
        return dateTaken;
    }

    @Override
    public void setDateTaken(DateTime dateTaken) {
        this.dateTaken = dateTaken;
    }
}
