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
package femr.ui.models.medical;

public class UpdateVitalsModel {
    private Float bloodPressureSystolic;
    private Float bloodPressureDiastolic;
    private Float heartRate;
    private Float temperature;
    private Float oxygenSaturation;
    private Float respiratoryRate;
    private Float heightFeet;
    private Float heightInches;
    private Float weight;
    private Float glucose;
    private Float weeksPregnant;
    private Integer smoker;
    private Integer diabetic;
    private Integer alcohol;
    private Integer cholesterol;
    private Integer hypertension;

    public Float getBloodPressureSystolic() {
        return bloodPressureSystolic;
    }

    public void setBloodPressureSystolic(Float bloodPressureSystolic) {
        this.bloodPressureSystolic = bloodPressureSystolic;
    }

    public Float getBloodPressureDiastolic() {
        return bloodPressureDiastolic;
    }

    public void setBloodPressureDiastolic(Float bloodPressureDiastolic) {
        this.bloodPressureDiastolic = bloodPressureDiastolic;
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

    public Float getOxygenSaturation() {
        return oxygenSaturation;
    }

    public void setOxygenSaturation(Float oxygenSaturation) {
        this.oxygenSaturation = oxygenSaturation;
    }

    public Float getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Float respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public Float getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(Float heightFeet) {
        this.heightFeet = heightFeet;
    }

    public Float getHeightInches() {
        return heightInches;
    }

    public void setHeightInches(Float heightInches) {
        this.heightInches = heightInches;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getGlucose() {
        return glucose;
    }

    public void setGlucose(Float glucose) {
        this.glucose = glucose;
    }

    public Float getWeeksPregnant() { /*Sam Zanni*/
        return weeksPregnant;
    }

    public void setWeeksPregnant(Float weeksPregnant) { /*Sam Zanni*/
        this.weeksPregnant = weeksPregnant;
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
}
