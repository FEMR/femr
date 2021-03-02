/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.ui.models.triage;

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
    public Boolean deletePhoto; //flag to determine if user would like to delete image file
    //begin vitals
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
    private Integer cholesterol;
    private Integer hypertension;
    //multiple chief complaints if they exist
    private String chiefComplaintsJSON;

    //indicates if the "yes" button was clicked for the diabetes screening prompt
    private String isDiabetesScreenPerformed;


    private String patientPhotoCropped;

    public String getPatientPhotoCropped() {
        return patientPhotoCropped;
    }

    public void setPatientPhotoCropped(String patientPhotoCropped) {
        this.patientPhotoCropped = patientPhotoCropped;
    }


    public Boolean getDeletePhoto() {
        return deletePhoto;
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

    public String getIsDiabetesScreenPerformed() {
        return isDiabetesScreenPerformed;
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

    public void setIsDiabetesScreenPerformed(String isDiabetesScreenPerformed) {
        this.isDiabetesScreenPerformed = isDiabetesScreenPerformed;
    }
}
