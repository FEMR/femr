package femr.ui.models.pharmacy;

public class CreateViewModel {
    private String firstName;
    private String lastName;
    private int pID;
    private int age;
    private float weight;
    private int height;
    private Boolean pregnancyStatus;
    private String diagnosis;
    private String medication1;
    private int medication1Amount;
    private String replacementMedication1;
    private int replacementAmount1;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Boolean getPregnancyStatus() {
        return pregnancyStatus;
    }

    public void setPregnancyStatus(Boolean pregnancyStatus) {
        this.pregnancyStatus = pregnancyStatus;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedication1() {
        return medication1;
    }

    public void setMedication1(String medication1) {
        this.medication1 = medication1;
    }

    public int getMedication1Amount() {
        return medication1Amount;
    }

    public void setMedication1Amount(int medication1Amount) {
        this.medication1Amount = medication1Amount;
    }

    public String getReplacementMedication1() {
        return replacementMedication1;
    }

    public void setReplacementMedication1(String replacementMedication1) {
        this.replacementMedication1 = replacementMedication1;
    }

    public int getReplacementAmount1() {
        return replacementAmount1;
    }

    public void setReplacementAmount1(int replacementAmount1) {
        this.replacementAmount1 = replacementAmount1;
    }
}
