package femr.ui.models.pharmacy;

public class CreateViewModel {
    //General Information
    private String firstName;
    private String lastName;
    private int pID;
    private int age;
    private String sex;
    private Boolean pregnancyStatus;
    private Integer weeksPregnant;
    //Vital Information
    private Float weight;
    private Float heightFeet;
    private Float heightinches;
    //Information from medical
    private String diagnosis;



    private String[] medications;


    private int medication1Amount;
    //Submitting replacement medications/replacement amounts
    private String replacementMedication1;
    private int replacementAmount1;


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

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
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

    public void setHeightFeet(Float heightFeet) {
        this.heightFeet = heightFeet;
    }

    public Float getHeightFeet() {
        return heightFeet;
    }

    public void setHeightinches(Float heightinches) {
        this.heightinches = heightinches;
    }

    public Float getHeightinches() {
        return heightinches;
    }

    public String[] getMedications() {
        return medications;
    }

    public String getMedication(int i){
        return medications[i];
    }

    public void setMedications(String[] medications) {
        this.medications = medications;
    }
}
