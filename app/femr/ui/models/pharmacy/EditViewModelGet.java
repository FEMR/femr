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
package femr.ui.models.pharmacy;

import femr.common.models.*;

import java.util.List;

public class EditViewModelGet {
    private SettingItem settings;
    private PatientItem patient;
    private PatientEncounterItem patientEncounterItem;
    //Prescriptions
    private List<PrescriptionItem> prescriptions;
    //Problems
    private List<ProblemItem> problems;
    private List<NoteItem> notes;
    private List<MedicationAdministrationItem> medicationAdministrationItems;

    public List<PrescriptionItem> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionItem> prescriptions) {
        this.prescriptions = prescriptions;
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

    public List<NoteItem> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteItem> notes) {
        this.notes = notes;
    }

    // Alaa Serhan
    public SettingItem getSettings() { return settings; }

    public void setSettings(SettingItem settings) { this.settings = settings; }

    public List<MedicationAdministrationItem> getMedicationAdministrationItems() {
        return medicationAdministrationItems;
    }

    public void setMedicationAdministrationItems(List<MedicationAdministrationItem> medicationAdministrationItems) {
        this.medicationAdministrationItems = medicationAdministrationItems;
    }
}
