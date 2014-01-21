package femr.common.models;

import femr.data.models.Vital;

public interface IPatientEncounterVital {
    int getId();

    int getUserId();

    int getPatientEncounterId();

    void setPatientEncounterId(int patientEncounterId);

            /*
    int getVitalId();

    void setVitalId(int vitalId);*/

    Vital getVital();

    void setVital(Vital vital);

    float getVitalValue();

    void setVitalValue(float vitalValue);

    String getDateTaken();

    void setDateTaken(String dateTaken);

    void setUserId(int userId);
}
