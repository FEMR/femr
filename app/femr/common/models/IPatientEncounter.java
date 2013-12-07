package femr.common.models;

public interface IPatientEncounter {
    int getId();

    int getPatientId();

    void setPatientId(int patientId);

    int getUserId();

    void setUserId(int userId);

    String getDateOfVisit();

    void setDateOfVisit(String dateOfVisit);

    String getChiefComplaint();

    void setChiefComplaint(String chiefComplaint);

    Integer getWeeksPregnant();

    void setWeeksPregnant(Integer weeksPregnant);

    Boolean getIsPregnant();

    void setIsPregnant(Boolean isPregnant);
}
