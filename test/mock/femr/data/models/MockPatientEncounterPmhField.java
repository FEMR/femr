package mock.femr.data.models;

import femr.common.models.IPatientEncounterPmhField;
import femr.common.models.IPmhField;
import org.joda.time.DateTime;

public class MockPatientEncounterPmhField implements IPatientEncounterPmhField {
    private int id;
    private int userId;
    private int patientEncounterId;
    private MockPmhField pmhField;
    private String pmhFieldValue;
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
    public IPmhField getPmhField() {
        return pmhField;
    }

    @Override
    public void setPmhField(IPmhField pmhField) {
        this.pmhField = (MockPmhField)pmhField;
    }

    @Override
    public String getPmhFieldValue() {
        return pmhFieldValue;
    }

    @Override
    public void setPmhFieldValue(String pmhFieldValue) {
        this.pmhFieldValue = pmhFieldValue;
    }

    @Override
    public DateTime getDateTaken() {
        return dateTaken;
    }

    @Override
    public void setDateTaken(DateTime dateTaken) {
        this.dateTaken = dateTaken;
    }
}
