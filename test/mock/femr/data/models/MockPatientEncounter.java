package mock.femr.data.models;

import femr.data.models.core.*;
import org.joda.time.DateTime;

import java.util.List;

public class MockPatientEncounter implements IPatientEncounter {

    private int id = -1;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public IPatient getPatient() {
        return null;
    }

    @Override
    public void setPatient(IPatient patient) {

    }

    @Override
    public List<IChiefComplaint> getChiefComplaints() {
        return null;
    }

    @Override
    public void setChiefComplaints(List<IChiefComplaint> chiefComplaints) {

    }

    @Override
    public DateTime getDateOfTriageVisit() {
        return null;
    }

    @Override
    public void setDateOfTriageVisit(DateTime dateOfVisit) {

    }

    @Override
    public DateTime getDateOfMedicalVisit() {
        return null;
    }

    @Override
    public void setDateOfMedicalVisit(DateTime dateOfMedicalVisit) {

    }

    @Override
    public DateTime getDateOfPharmacyVisit() {
        return null;
    }

    @Override
    public void setDateOfPharmacyVisit(DateTime dateOfPharmacyVisit) {

    }

    @Override
    public IUser getDoctor() {
        return null;
    }

    @Override
    public void setDoctor(IUser doctor) {

    }

    @Override
    public IUser getPharmacist() {
        return null;
    }

    @Override
    public void setPharmacist(IUser pharmacist) {

    }

    @Override
    public IUser getNurse() {
        return null;
    }

    @Override
    public void setNurse(IUser nurse) {

    }

    @Override
    public IPatientAgeClassification getPatientAgeClassification() {
        return null;
    }

    @Override
    public void setPatientAgeClassification(IPatientAgeClassification patientAgeClassification) {

    }

    @Override
    public IMissionTrip getMissionTrip() {
        return null;
    }

    @Override
    public void setMissionTrip(IMissionTrip missionTrip) {

    }

    @Override
    public DateTime getDateOfDiabeteseScreen() {
        return null;
    }

    @Override
    public void setDateOfDiabeteseScreen(DateTime dateOfDiabeteseScreen) {

    }

    @Override
    public IUser getDiabetesScreener() {
        return null;
    }

    @Override
    public void setDiabetesScreener(IUser diabetesScreener) {

    }

    @Override
    public Boolean getIsDiabetesScreened() {
        return null;
    }

    @Override
    public void setIsDiabetesScreened(Boolean isDiabetesScreened) {

    }

    @Override
    public DateTime getEncounterDeleted() {
        return null;
    }

    @Override
    public void setEncounterDeleted(DateTime isDeleted) {

    }

    @Override
    public Integer getDeletedByUserId() {
        return null;
    }

    @Override
    public void setDeletedByUserId(Integer userId) {

    }

    @Override
    public String getReasonDeleted() {
        return null;
    }

    @Override
    public void setReasonDeleted(String reason) {

    }
}
