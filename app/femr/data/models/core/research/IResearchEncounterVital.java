package femr.data.models.core.research;


import femr.data.models.core.IVital;
import femr.data.models.mysql.PatientEncounter;
import femr.data.models.mysql.research.ResearchEncounter;

public interface IResearchEncounterVital {

    int getId();

    int getUserId();

    ResearchEncounter getPatientEncounter();

    void setPatientEncounter(ResearchEncounter patientEncounterId);

    int getVitalId();

    void setVitalId(int vitalId);

    IVital getVital();
    void setVital(IVital vital);

    Float getVitalValue();

    void setVitalValue(float vitalValue);

    String getDateTaken();

    void setDateTaken(String dateTaken);

    void setUserId(int userId);

}
