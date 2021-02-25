package mock.femr.data.models;

import femr.data.models.core.IRankedPatientMatch;
import femr.data.models.mysql.Patient;

public class MockRankedPatientMatch implements IRankedPatientMatch {

    private int rank = 120;
    private Patient patient = null;


    @Override
    public Integer getRank() {
        return null;
    }

    @Override
    public Patient getPatient() {
        return null;
    }

    @Override
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void setRank(int rank) {

    }
}