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
package femr.ui.models.history;

import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.common.models.RankedPatientItem;

import java.util.List;

public class IndexPatientViewModelGet {
    //used to show a list of alternative patients that also
    //match the users search criteria
    private List<PatientItem> patientItems;
    private List<RankedPatientItem> rankedPatientItems;

    //used for the patient that is being shown no matter what
    private PatientItem patientItem;
    //patient encounters available for the patient
    private List<PatientEncounterItem> patientEncounterItems;

    public List<PatientItem> getPatientItems() {
        return patientItems;
    }
    public List<RankedPatientItem> getRankedPatientItems() {
        return rankedPatientItems;
    }

    public void setPatientItems(List<PatientItem> patientItems) {
        this.patientItems = patientItems;
    }

    public void setRankedPatientItems(List<RankedPatientItem> rankedPatientItems) {
        this.rankedPatientItems = rankedPatientItems;
    }

    public PatientItem getPatientItem() {
        return patientItem;
    }

    public void setPatientItem(PatientItem patientItem) {
        this.patientItem = patientItem;
    }

    public List<PatientEncounterItem> getPatientEncounterItems() {
        return patientEncounterItems;
    }

    public void setPatientEncounterItems(List<PatientEncounterItem> patientEncounterItems) {
        this.patientEncounterItems = patientEncounterItems;
    }
}
