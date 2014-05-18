package femr.common.models.custom;

import org.joda.time.DateTime;

public interface IPatientEncounterCustomField {
    int getId();

    void setId(int id);

    int getUserId();

    void setUserId(int userId);

    int getPatientEncounterId();

    void setPatientEncounterId(int patientEncounterId);

    int getCustomFieldId();

    void setCustomFieldId(int customFieldId);

    String getCustomFieldValue();

    void setCustomFieldValue(String customFieldValue);

    DateTime getDateTaken();

    void setDateTaken(DateTime dateTaken);
}
