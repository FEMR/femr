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
