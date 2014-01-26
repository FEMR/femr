package mock.femr.data.models;

import femr.common.models.IPatientEncounterVital;
import femr.common.models.IVital;

public class MockPatientEncounterVital implements IPatientEncounterVital{

    private int id = 0;
    private int userId = 0;
    private int patientEncounterId = 0;
    private IVital vital;
    private float vitalValue = 0;
    private String dateTaken = "";

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int getPatientEncounterId() {
        return patientEncounterId;
    }

    @Override
    public void setPatientEncounterId(int patientEncounterId) {
        this.patientEncounterId = patientEncounterId;
    }

    @Override
    public IVital getVital() {
        return vital;
    }

    @Override
    public void setVital(IVital vital) {
        IVital newVital = vital;
        this.vital = newVital;
    }

    @Override
    public Float getVitalValue() {
        return vitalValue;
    }

    @Override
    public void setVitalValue(float vitalValue) {
        this.vitalValue = vitalValue;
    }

    @Override
    public String getDateTaken() {
        return dateTaken;
    }

    @Override
    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }
}
