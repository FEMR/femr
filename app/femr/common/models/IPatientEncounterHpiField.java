package femr.common.models;

public interface IPatientEncounterHpiField {
    int getId();

    int getPatientEncounterId();

    void setPatientEncounterId(int patientEncounterId);

    int getHpiFieldId();

    void setHpiFieldId(int hpiFieldId);

    String getHpiFieldValue();

    void setHpiFieldValue(String hpiFieldValue);

    String getDateTaken();

    void setDateTaken(String dateTaken);

    int getUserId();

    void setUserId(int userId);
}
