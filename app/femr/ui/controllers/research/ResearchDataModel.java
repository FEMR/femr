package femr.ui.controllers.research;

/**
 * This model stores the return data from the research query
 */
public class ResearchDataModel {

    public int encounterID;
    public int patientID;
    public String age;
    public String city;
    public String sex;
   // public String medication;
    public String dateTaken;
    public String condition;


    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getEncounterID() {
        return encounterID;
    }

    public void setEncounterID(int encounterID) {
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

//    public String getMedication() {
//        return medication;
//    }
//
//    public void setMedication(String medication) {
//        this.medication = medication;
//    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
