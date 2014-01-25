package femr.common.models;

import org.joda.time.DateTime;

public interface IPatientEncounterTreatmentField {
    int getId();

    int getUserId();

    void setUserId(int userId);

    int getPatientEncounterId();

    void setPatientEncounterId(int patientEncounterId);

    ITreatmentField getTreatmentField();

    void setTreatmentField(ITreatmentField treatmentField);

    String getTreatmentFieldValue();

    void setTreatmentFieldValue(String treatmentFieldValue);

    DateTime getDateTaken();

    void setDateTaken(DateTime dateTaken);
}
