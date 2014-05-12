package femr.ui.models.pharmacy;

import femr.business.dtos.PatientEncounterItem;
import femr.business.dtos.PatientItem;
import femr.business.dtos.PrescriptionItem;
import femr.business.dtos.ProblemItem;

import java.util.Date;
import java.util.List;

public class EditViewModelGet {
    private PatientItem patient;
    private PatientEncounterItem patientEncounterItem;
    //Vital Information
    private Float weight;
    private Integer heightFeet;
    private Integer heightinches;
    //Prescriptions
    private List<PrescriptionItem> medications;
    //Problems
    private List<ProblemItem> problems;

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getWeight() {
        return weight;
    }

    public void setHeightFeet(Integer heightFeet) {
        this.heightFeet = heightFeet;
    }

    public Integer getHeightFeet() {
        return heightFeet;
    }

    public void setHeightinches(Integer heightinches) {
        this.heightinches = heightinches;
    }

    public Integer getHeightinches() {
        return heightinches;
    }

    public List<PrescriptionItem> getMedications() {
        return medications;
    }

    public void setMedications(List<PrescriptionItem> medications) {
        this.medications = medications;
    }

    public PatientItem getPatient() {
        return patient;
    }

    public void setPatient(PatientItem patient) {
        this.patient = patient;
    }

    public PatientEncounterItem getPatientEncounterItem() {
        return patientEncounterItem;
    }

    public void setPatientEncounterItem(PatientEncounterItem patientEncounterItem) {
        this.patientEncounterItem = patientEncounterItem;
    }

    public List<ProblemItem> getProblems() {
        return problems;
    }

    public void setProblems(List<ProblemItem> problems) {
        this.problems = problems;
    }
}
