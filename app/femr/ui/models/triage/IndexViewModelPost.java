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

//NOTE: the triage view sets the input element names dynamically
//based on the vital name entry in the database, but this
//ViewModel does NOT.
public class IndexViewModelPost {
    //begin patient
    private String firstName;
    private String lastName;
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

    //Alaa Serhan
    private Float celsiusTemperature;

    private Integer respiratoryRate;
    private Float oxygenSaturation;

    private Integer heightFeet;
    private Integer heightInches;

    //Alaa Serhan
    private Integer heightMeters;
    private Integer heightCentimeters;


    private Float weight;

    //Alaa Serhan
    private Float kgWeight;

    private Integer glucose;
    //begin encounter
    private String chiefComplaint;
    private Integer weeksPregnant;

    //multiple chief complaints if they exist
    private String chiefComplaintsJSON;


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


    // Alaa Serhan
    public Float getCelsiusTemperature() {
        //returns farenheit because you want to save it in the database like that
        return celsiusTemperature * 9/5 + 32;
    }

    public void setCelsiusTemperature(Float celsiusTemperature) {
        this.celsiusTemperature = celsiusTemperature;
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


    //Alaa Serhan
    public Float getHeightMeters()
    {
        return heightMeters * 3.2808f;
    }

    public void setHeightMeters(Integer heightMeters)
    {
        this.heightMeters = heightMeters;
    }

    public Float getHeightCentimeters()
    {
        // Alaa - actually returns the height in inches
        return heightCentimeters * (0.39370f);
    }

    public void setHeightCentimeters(Integer heightCentimeters)
    {
        this.heightCentimeters = heightCentimeters;
    }

    public Float getWeightKg()
    {
        if(kgWeight != null){
            return kgWeight * 2;
        } else {
            return (100000000f);
        }

    }

    public void setWeightKg(Float kgWeight)
    {
        this.kgWeight = kgWeight;
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
}
