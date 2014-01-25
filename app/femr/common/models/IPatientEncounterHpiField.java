package femr.common.models;

import org.joda.time.DateTime;

public interface IPatientEncounterHpiField {
    int getId();

    int getPatientEncounterId();

    void setPatientEncounterId(int patientEncounterId);

    IHpiField getHpiField();

    void setHpiField(IHpiField hpiField);

    String getHpiFieldValue();

    void setHpiFieldValue(String hpiFieldValue);

    DateTime getDateTaken();

    void setDateTaken(DateTime dateTaken);

    int getUserId();

    void setUserId(int userId);
}
