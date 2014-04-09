package femr.ui.controllers.research;

import java.util.List;

/**
 * This model represents the patient data being returned from the sql
 * query
 */
public class ResearchDataModel {

    private String encounterID;
    private String patientID;
    private String age;
    private String city;
    private String sex;
    private List<String> medication;
    private String dateTaken;
    //private String condition;
    private String problem;


    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getEncounterID() {
        return encounterID;
    }

    public void setEncounterID(String encounterID) {
        this.encounterID = encounterID;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<String> getMedication() {
        return medication;
    }

    public void setMedication(List<String> medication) {
        this.medication = medication;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

//    public String getCondition() {
//        return condition;
//    }
//
//    public void setCondition(String condition) {
//        this.condition = condition;
//    }

}
