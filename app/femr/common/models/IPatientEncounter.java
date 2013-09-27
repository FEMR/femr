package femr.common.models;

import java.sql.Timestamp;

public interface IPatientEncounter {
    int getId();

    int getPatientId();

    void setPatientId(int patientId);

    int getUserId();

    void setUserId(int userId);

    Timestamp getDateOfVisit();

    void setDateOfVisit(Timestamp dateOfVisit);

    String getChiefComplaint();

    void setChiefComplaint(String chiefComplaint);
}
