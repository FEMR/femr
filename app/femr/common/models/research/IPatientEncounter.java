package femr.common.models.research;

/**
 * interface for the research subset of the patient encounter table
 */
public interface IPatientEncounter {

    int getId();

    int getPatientId();

    void setPatientId(int patientId);

    String getDateOfVisit();

    void setDateOfVisit(String dateOfVisit);

    String getChiefComplaint();

    void setChiefComplaint(String chiefComplaint);

}
