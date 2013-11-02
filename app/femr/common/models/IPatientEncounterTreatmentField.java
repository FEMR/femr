package femr.common.models;

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

//    String getDateTaken();
//
//    void setDateTaken(String dateTaken);
}
