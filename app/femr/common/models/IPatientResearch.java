package femr.common.models;

/**
 * Defines the interface for all the items that are returned from the research
 * Patient query
 */
public interface IPatientResearch {

    // hpi fields Table
    String getHPI_FIELDS_Onset();
    void  setHPI_FIELDS_Onset(String Onset);

    int getHPI_FIELDS_Severity();
    void setHPI_FIELDS_Severity(int Severity);

    String getHPI_FIELDS_Radiation();
    void setHPI_FIELDS_Radiation(String radiation);

    String getHPI_FIELDS_Quality();
    void setHPI_FIELDS_Quality(String quality);

    String getHPI_FIELDS_Provokes();
    void setHPI_FIELDS_Provokes(String Provokes);

    String getHPI_FIELDS_Palliates();
    void setHPI_FIELDS_Palliates();

    String getHPI_FIELDS_TimeOfDay();
    void setHPI_FIELDS_TimeOfDay(String timeOfDay);

    String getHPI_FIELDS_PhysicalExamination();
    void setHPI_FIELDS_PhysicalExamination(String physicalExamination);

    String getHPI_FIELDS_Narrative();
    void setHPI_FIELDS_Narrative();


    // Patients Field table
    int getPATIENTS_ID();
    void setPATIENTS_ID(int ID);

    String getPATIENTS_Age();
    void setPATIENTS_Age(String Age);

    String getPATIENTS_Sex();
    void setPATIENTS_Sex(String Sex);

    String getPATIENTS_Address();
    void setPATIENTS_Address(String Address);

    String getPATIENTS_City();
    void setPATIENTS_City(String City);

    int getPATIENTS_Photo_id();
    void setPATIENTS_Photo_id(int id);


    // Patient Prescription table
    int getPATIENT_PRESCRIPTIONS_ID();
    void setPATIENT_PRESCRIPTIONS_ID(int ID);

    int getPATIENT_PRESCRIPTIONS_Encounter_id();
    void setPATIENT_PRESCRIPTIONS_Encounter_id(int ID);

    int getPATIENT_PRESCRIPTIONS_User_id();
    void setPATIENT_PRESCRIPTIONS_User_id(int ID);

    int getPATIENT_PRESCRIPTIONS_Amount();
    void setPATIENT_PRESCRIPTIONS_Amount(int Amount);

    int getPATIENT_PRESCRIPTIONS_Replaced();
    void setPATIENT_PRESCRIPTIONS_Replaced(int Replaced);

    String getPATIENT_PRESCRIPTIONS_Reason();
    void setPATIENT_PRESCRIPTIONS_Reason(String Reason);

    int getPATIENT_PRESCRIPTIONS_Replacement_id();
    void setPATIENT_PRESCRIPTIONS_Replacement_id(int ID);

    String getPATIENT_PRESCRIPTIONS_Medication_name();
    void setPATIENT_PRESCRIPTIONS_Medication_name(String Medication);

    String getPATIENT_PRESCRIPTIONS_Date_taken();
    void setPATIENT_PRESCRIPTIONS_Date_taken(String Datetaken);


    // Patient Encounters table
    int getPatient_Encounters_ID();
    void setPatient_Encounters_ID(int ID);

    int getPatient_Encounters_Patient_ID();
    void setPatient_Encounters_Patient_ID(int ID);

    int getPatient_Encounters_User_ID();
    void setPatient_Encounters_User_ID(int ID);

    String getPatient_Encounters_DateOfVisit();
    void setPatient_Encounters_DateOfVisit(String DateOfVisit);

    String getPatient_Encounters_Chief_Complaint();
    void setPatient_Encounters_Chief_Complaint(String Complaint);

    int getPatient_Encounters_Weeks_Pregnant();
    void setPatient_Encounters_Weeks_Pregnant(int Weeks);

    int getPatient_Encounters_Is_Pregnant();
    void setPatient_Encounters_Is_Pregnant(int Pregnant);




}
