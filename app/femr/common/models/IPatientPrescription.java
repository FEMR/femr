package femr.common.models;

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

    int getReplacementId();

    void setReplacementId(int replacementId);
}
