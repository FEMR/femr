package femr.ui.models.triage;

public class CreateViewModel {
    //begin general info
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int age;
    private String sex;
    //begin vitals
//    private float bloodPressureSystolic;
//    private float bloodPressureDiastolic;
//    private float heartRate;
//    private float temperature;
//    private float respirations;
//    private float heightFeet;
//    private float heightInches;
//    private float weight;
    private String chiefComplaint;

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
//    public float getBloodPressureSystolic() {
//        return bloodPressureSystolic;
//    }
//
//    public void setBloodPressureSystolic(float bloodPressureSystolic) {
//        this.bloodPressureSystolic = bloodPressureSystolic;
//    }
//
//    public float getBloodPressureDiastolic() {
//        return bloodPressureSystolic;
//    }
//
//    public void setBloodPressureDiastolic(float bloodPressureDiastolic) {
//        this.bloodPressureSystolic = bloodPressureDiastolic;
//    }
//
//    public float getHeartRate() {
//        return heartRate;
//    }
//
//    public void setHeartRate(float heartRate) {
//        this.heartRate = heartRate;
//    }
//
//    public float getTemperature() {
//        return temperature;
//    }
//
//    public void setTemperature(float temperature) {
//        this.temperature = temperature;
//    }
//
//    public float getRespirations() {
//        return respirations;
//    }
//
//    public void setRespirations(float respirations) {
//        this.respirations = respirations;
//    }
//
//    public float getHeightFeet() {
//        return heightFeet;
//    }
//
//    public void setHeightFeet(float heightFeet) {
//        this.heightFeet = heightFeet;
//    }
//
//    public float getHeightInches() {
//        return heightFeet;
//    }
//
//    public void setHeightInches(float heightInches) {
//        this.heightFeet = heightInches;
//    }
//
//    public float getWeight() {
//        return weight;
//    }
//
//    public void setWeight(float weight) {
//        this.weight = weight;
//    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }
}
