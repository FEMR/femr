package femr.ui.models.triage;

//NOTE: the triage view sets the input element names dynamically
//based on the vital name entry in the database, but this
//ViewModel does NOT.
public class CreateViewModel {
    //begin patient
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int age;
    private String sex;
    //begin vitals
    private Float bloodPressureSystolic;
    private Float bloodPressureDiastolic;
    private Float heartRate;
    private Float temperature;
    private Float respiratoryRate;
    private Float oxygenSaturation;
    private Float heightFeet;
    private Float heightInches;
    private Float weight;
    //begin encounter
    private String chiefComplaint;
    private Integer weeksPregnant;

    public CreateViewModel() {
    }

    //begin general info
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    //begin vitals
    public Float getBloodPressureSystolic() {
        return bloodPressureSystolic;
    }

    public void setBloodPressureSystolic(Float bloodPressureSystolic) {
        this.bloodPressureSystolic = bloodPressureSystolic;
    }

    public Float getBloodPressureDiastolic() {
        return bloodPressureSystolic;
    }

    public void setBloodPressureDiastolic(Float bloodPressureDiastolic) {
        this.bloodPressureSystolic = bloodPressureDiastolic;
    }

    public Float getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Float heartRate) {
        this.heartRate = heartRate;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(Float heightFeet) {
        this.heightFeet = heightFeet;
    }

    public Float getHeightInches() {
        return heightFeet;
    }

    public void setHeightInches(Float heightInches) {
        this.heightFeet = heightInches;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public Float getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Float respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public Float getOxygenSaturation() {
        return oxygenSaturation;
    }

    public void setOxygenSaturation(Float oxygenSaturation) {
        this.oxygenSaturation = oxygenSaturation;
    }
}
