package femr.data.models.core;

import femr.data.models.mysql.Patient;

public interface IRankedPatientMatch {
    Integer getRank();

    Patient getPatient();

    void setPatient(Patient patient);

    void setRank(int rank);
}
