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
//    private float bloodPressure;
//    private float heartRate;
//    private float temperature;
//    private float respirations;
//    private float height;
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
//    public float getBloodPressure() {
//        return bloodPressure;
//    }
//
//    public void setBloodPressure(float bloodPressure) {
//        this.bloodPressure = bloodPressure;
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
//    public float getHeight() {
//        return height;
//    }
//
//    public void setHeight(float height) {
//        this.height = height;
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
