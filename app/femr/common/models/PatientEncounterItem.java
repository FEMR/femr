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
package femr.common.models;

import java.util.ArrayList;
import java.util.List;

public class PatientEncounterItem {
    private int id;
    private PatientItem patientItem;
    private Integer missionTripId;

    //age classification is not part of the patient because it doesn't change with time
    //i.e. when you save the birth date of a patient, you can always tell how old the patient is,
    //it is part of a patient
    private String ageClassification;//infant, teen, etc
    private List<String> chiefComplaints;
    private Boolean isClosed;

    private String triageDateOfVisit;
    private String nurseEmailAddress;
    private String nurseFullName; //Andrew Display Username instead of Email Address Fix

    private String medicalDateOfVisit;
    private String physicianEmailAddress;
    private String physicianFullName; //Andrew Display Username instead of Email Address Fix

    private String pharmacyDateOfVisit;
    private String pharmacistEmailAddress;
    private String pharmacistFullName; //Andrew Display Username instead of Email Address Fix
    private Boolean screenedForDiabetes;

    //amount of time it took the patient to complete the encounter
    private String turnAroundTime;

    public PatientEncounterItem(){
        this.chiefComplaints = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PatientItem getPatientItem() {
        return patientItem;
    }

    public void setPatientItem(PatientItem patientItem) {
        this.patientItem = patientItem;
    }

    public Integer getMissionTripId() {
        return missionTripId;
    }

    public void setMissionTripId(Integer missionTripId) {
        this.missionTripId = missionTripId;
    }

    public List<String> getChiefComplaints() {
        return chiefComplaints;
    }

    public void setChiefComplaints(List<String> chiefComplaints) {
        this.chiefComplaints = chiefComplaints;
    }

    public void addChiefComplaint(String chiefComplaint){
        chiefComplaints.add(chiefComplaint);
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getTriageDateOfVisit() {
        return triageDateOfVisit;
    }

    public void setTriageDateOfVisit(String triageDateOfVisit) {
        this.triageDateOfVisit = triageDateOfVisit;
    }

    public String getNurseEmailAddress() {
        return nurseEmailAddress;
    }

    public void setNurseEmailAddress(String nurseEmailAddress) {
        this.nurseEmailAddress = nurseEmailAddress;
    }

    public String getPhysicianEmailAddress() {
        return physicianEmailAddress;
    }

    public void setPhysicianEmailAddress(String physicianEmailAddress) {
        this.physicianEmailAddress = physicianEmailAddress;
    }

    public String getPharmacistEmailAddress() {
        return pharmacistEmailAddress;
    }

    public void setPharmacistEmailAddress(String pharmacistEmailAddress) {
        this.pharmacistEmailAddress = pharmacistEmailAddress;
    }

    public String getNurseFullName() { //Andrew Change
        return nurseFullName;
    }

    public void setNurseFullName(String nurseFullName) {this.nurseFullName = nurseFullName;}       //Andrew Display Username instead of Email Address Fix

    public String getPhysicianFullName() { return physicianFullName; }

    public void setPhysicianFullName(String physicianFullName) {this.physicianFullName = physicianFullName;} //Andrew Display Username instead of Email Address Fix

    public String getPharmacistFullName() { return pharmacistFullName; }

    public void setPharmacistFullName(String pharmacistFullName) { this.pharmacistFullName = pharmacistFullName;} //Andrew Display Username instead of Email Address Fix

    public String getMedicalDateOfVisit() {
        return medicalDateOfVisit;
    }

    public void setMedicalDateOfVisit(String medicalDateOfVisit) {
        this.medicalDateOfVisit = medicalDateOfVisit;
    }

    public String getPharmacyDateOfVisit() {
        return pharmacyDateOfVisit;
    }

    public void setPharmacyDateOfVisit(String pharmacyDateOfVisit) {
        this.pharmacyDateOfVisit = pharmacyDateOfVisit;
    }

    public String getAgeClassification() {
        return ageClassification;
    }

    public void setAgeClassification(String ageClassification) {
        this.ageClassification = ageClassification;
    }

    public Boolean getScreenedForDiabetes() {
        return screenedForDiabetes;
    }

    public void setScreenedForDiabetes(Boolean screenedForDiabetes) {
        this.screenedForDiabetes = screenedForDiabetes;
    }

    public String getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(String turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }
}
