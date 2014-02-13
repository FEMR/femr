package mock.femr.data.models;

import femr.common.models.IHpiField;
import femr.common.models.IPatientEncounterHpiField;
import org.joda.time.DateTime;

public class MockPatientEncounterHpiField implements IPatientEncounterHpiField {

    private int id = 0;
    private int userId = 0;
    private int patientEncounterId = 0;
    private MockHpiField hpiField = null;
    private String hpiFieldValue = "";
    private DateTime dateTaken;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public IHpiField getHpiField() {
        return hpiField;
    }

    @Override
    public void setHpiField(IHpiField hpiField) {
        this.hpiField = (MockHpiField)hpiField;
    }

    @Override
    public String getHpiFieldValue() {
        return hpiFieldValue;
    }

    @Override
    public void setHpiFieldValue(String hpiFieldValue) {
        this.hpiFieldValue = hpiFieldValue;
    }

    @Override
    public DateTime getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(DateTime dateTaken) {
        this.dateTaken = dateTaken;
    }
}
