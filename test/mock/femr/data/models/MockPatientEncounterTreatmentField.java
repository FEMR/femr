package mock.femr.data.models;

import femr.common.models.IPatientEncounterTreatmentField;
import femr.data.models.ITreatmentField;
import org.joda.time.DateTime;

public class MockPatientEncounterTreatmentField implements IPatientEncounterTreatmentField{

    private int id = 0;
    private int userId = 0;
    private int patientEncounterId = 0;
    private MockTreatmentField treatmentField = null;
    private String treatmentFieldValue = "";
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
    public ITreatmentField getTreatmentField() {
        return treatmentField;
    }

    @Override
    public void setTreatmentField(ITreatmentField treatmentField) {
        this.treatmentField = (MockTreatmentField)treatmentField;
    }

    @Override
    public String getTreatmentFieldValue() {
        return treatmentFieldValue;
    }

    @Override
    public void setTreatmentFieldValue(String treatmentFieldValue) {
        this.treatmentFieldValue = treatmentFieldValue;
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
