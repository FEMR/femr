package femr.common.models;

public interface IPatientPrescription {
    int getId();

    int getPatientEncounterId();

    int getUserId();

    int getAmount();

    void SetAmount(int amount);

    Boolean isReplace();

    String getReason();

    void setReason(String reason);

    int getReplacement();

    void SetReplacement(int replacementMedication);
}
