package femr.common.models;

import org.joda.time.DateTime;

public interface IPatientPrescription {

    int getId();

    int getEncounterId();

    void setEncounterId(int encounterId);

    int getUserId();

    void setUserId(int userId);

    int getAmount();

    void setAmount(int amount);

    Boolean getReplaced();

    void setReplaced(Boolean replaced);

    String getReason();

    void setReason(String reason);

    Integer getReplacementId();

    void setReplacementId(Integer replacementId);

    String getMedicationName();

    void setMedicationName(String medicationName);

    DateTime getDateTaken();

    void setDateTaken(DateTime dateTaken);
}
