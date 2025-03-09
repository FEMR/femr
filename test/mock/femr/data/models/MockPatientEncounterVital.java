package mock.femr.data.models;

import femr.data.models.core.*;
import femr.data.models.mysql.MedicationInventory;
import femr.data.models.mysql.Vital;

import java.util.List;

public class MockPatientEncounterVital implements IPatientEncounterVital {

    int patientEncounterId = 0;
    int userId = 0;
    int id = 0;
    IVital vital = new MockVital();
    Float vitalValue = 10.0f;
    String dateTaken = "2025-01-30 17:48:48";

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getUserId() {
        return userId;
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
        this.vital = vital;
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

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
