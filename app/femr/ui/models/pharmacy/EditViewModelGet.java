package femr.ui.models.pharmacy;

import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.common.models.PrescriptionItem;
import femr.common.models.ProblemItem;

import java.util.List;

public class EditViewModelGet {
    private PatientItem patient;
    private PatientEncounterItem patientEncounterItem;
    //Prescriptions
    private List<PrescriptionItem> medications;
    //Problems
    private List<ProblemItem> problems;

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
