package femr.ui.models.pharmacy;

import java.util.Date;

public class CreateViewModelGet {
    //General Information
    private String firstName;
    private String lastName;
    private int pID;
    private String age;
    private String sex;
    private Boolean pregnancyStatus;
    private Integer weeksPregnant;
    //Vital Information
    private Float weight;
    private Integer heightFeet;
    private Integer heightinches;
    //Prescriptions
    private String[] medications;
    //Problems
    private String[] problems;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public int getpID() {
        return pID;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setPregnancyStatus(Boolean pregnancyStatus) {
        this.pregnancyStatus = pregnancyStatus;
    }

    public Boolean getPregnancyStatus() {
        return pregnancyStatus;
    }

    public void setWeeksPregnant(Integer weeksPregnant) {
        this.weeksPregnant = weeksPregnant;
    }

    public Integer getWeeksPregnant() {
        return weeksPregnant;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getWeight() {
        return weight;
    }

    public void setHeightFeet(Integer heightFeet) {
        this.heightFeet = heightFeet;
    }

    public Integer getHeightFeet() {
        return heightFeet;
    }

    public void setHeightinches(Integer heightinches) {
        this.heightinches = heightinches;
    }

    public Integer getHeightinches() {
        return heightinches;
    }

    public String[] getMedications() {
        return medications;
    }

    public String getMedication(int i) {
        return medications[i];
    }

    public void setMedications(String[] medications) {
        this.medications = medications;
    }

    public String getProblems(int i) {
        return problems[i];
    }

    public String[] getProblems() {
        return problems;
    }

    public void setProblems(String[] problems) {
        this.problems = problems;
    }
}
