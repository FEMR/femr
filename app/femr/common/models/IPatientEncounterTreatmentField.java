package femr.common.models;

import org.joda.time.DateTime;

public interface IPatientEncounterTreatmentField {
    int getId();

    int getUserId();

    void setUserId(int userId);

    int getPatientEncounterId();

    void setPatientEncounterId(int patientEncounterId);

    int getTreatmentFieldId();

    void setTreatmentFieldId(int treatmentFieldId);

    String getTreatmentFieldValue();

    void setTreatmentFieldValue(String treatmentFieldValue);

    DateTime getDateTaken();

    void setDateTaken(DateTime dateTaken);
}
