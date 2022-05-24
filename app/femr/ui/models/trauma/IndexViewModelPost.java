package femr.ui.models.trauma;

import java.util.Date;

public class IndexViewModelPost {

    //begin patient
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String city;
    private Date age;
    private String ageClassification;
    private String sex;
    private String patientPhotoCropped;
    private Integer bloodPressureSystolic;
    private Integer bloodPressureDiastolic;
    private Integer heartRate;
    private Float temperature;
    private Integer respiratoryRate;
    private Float oxygenSaturation;
    private Integer heightFeet;
    private Integer heightInches;
    private Float weight;
    private Integer glucose;
    //begin encounter
    private String chiefComplaint;
    private Integer weeksPregnant;
    //Osman
    private Integer smoker;
    private Integer diabetic;
    private Integer alcohol;
    private String isDiabetesScreenPerformed;

    public Boolean deletePhoto; //flag to determine if user would like to delete image file

    private Integer cholesterol;
    private Integer hypertension;
    //multiple chief complaints if they exist
    private String chiefComplaintsJSON;
    public String getPatientPhotoCropped() {
        return patientPhotoCropped;
    }

    public void setPatientPhotoCropped(String patientPhotoCropped) {
        this.patientPhotoCropped = patientPhotoCropped;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    //begin vitals
    public Integer getBloodPressureSystolic() {
        return bloodPressureSystolic;
    }

    public void setBloodPressureSystolic(Integer bloodPressureSystolic) {
        this.bloodPressureSystolic = bloodPressureSystolic;
    }

    public Integer getBloodPressureDiastolic() {
        return bloodPressureDiastolic;
    }

    public void setBloodPressureDiastolic(Integer bloodPressureDiastolic) {
        this.bloodPressureDiastolic = bloodPressureDiastolic;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Integer getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(Integer heightFeet) {
        this.heightFeet = heightFeet;
    }

    public Integer getHeightInches() {
        return heightInches;
    }

    public void setHeightInches(Integer heightInches) {

        this.heightInches = heightInches;
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

    public Boolean getDeletePhoto() {
        return deletePhoto;
    }

    public Integer getSmoker() {return smoker;}
    public void setSmoker(Integer smoker){this.smoker = smoker;}
    public Integer getDiabetes() {return diabetic;}
    public void setDiabetic(Integer diabetes){this.diabetic = diabetes;}

    public Integer getAlcohol() {return alcohol;}
    public void setAlcohol(Integer alcohol){this.alcohol = alcohol;}

    public Integer getCholesterol() {return cholesterol;}
    public void setCholesterol(Integer cholesterol){this.cholesterol = cholesterol;}

    public Integer getHypertension() {return hypertension;}
    public void setHypertension(Integer hypertension){this.hypertension = hypertension;}

    public String getIsDiabetesScreenPerformed() {
        return isDiabetesScreenPerformed;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public Integer getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Integer respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public Float getOxygenSaturation() {
        return oxygenSaturation;
    }

    public void setOxygenSaturation(Float oxygenSaturation) {
        this.oxygenSaturation = oxygenSaturation;
    }

    public Integer getWeeksPregnant() {
        return weeksPregnant;
    }

    public void setWeeksPregnant(Integer weeksPregnant) {
        this.weeksPregnant = weeksPregnant;
    }

    public Integer getGlucose() {
        return glucose;
    }

    public void setGlucose(Integer glucose) {
        this.glucose = glucose;
    }

    public String getChiefComplaintsJSON() {
        return chiefComplaintsJSON;
    }

    public void setChiefComplaintsJSON(String chiefComplaintsJSON) {
        this.chiefComplaintsJSON = chiefComplaintsJSON;
    }

    public String getAgeClassification() {
        return ageClassification;
    }

    public void setAgeClassification(String ageClassification) {
        this.ageClassification = ageClassification;
    }


    public void setIsDiabetesScreenPerformed(String isDiabetesScreenPerformed) {
        this.isDiabetesScreenPerformed = isDiabetesScreenPerformed;
    }

}
