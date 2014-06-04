package femr.data.models;

public interface IPatientEncounterVital {
    int getId();

    int getUserId();

    int getPatientEncounterId();

    void setPatientEncounterId(int patientEncounterId);

            /*
    int getVitalId();

    void setVitalId(int vitalId);*/

    IVital getVital();

    void setVital(IVital vital);

    Float getVitalValue();

    void setVitalValue(float vitalValue);

    String getDateTaken();

    void setDateTaken(String dateTaken);

    void setUserId(int userId);
}
