package femr.data.models;

import org.joda.time.DateTime;

/**
 * Created by kevin on 5/31/14.
 */
public interface IPatientEncounterTabField {
    int getId();

    void setId(int id);

    int getUserId();

    void setUserId(int userId);

    int getPatientEncounterId();

    void setPatientEncounterId(int patientEncounterId);

    ITabField getTabField();

    void setTabField(ITabField tabField);

    String getTabFieldValue();

    void setTabFieldValue(String tabFieldValue);

    DateTime getDateTaken();

    void setDateTaken(DateTime dateTaken);

    ChiefComplaint getChiefComplaint();

    void setChiefComplaint(IChiefComplaint chiefComplaint);
}
