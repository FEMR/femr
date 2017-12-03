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
package femr.ui.models.admin.inventory;

import femr.common.models.MedicationItem;
import femr.common.models.MissionTripItem;

import java.util.List;

public class ManageViewModelGet {
    private List<MedicationItem> medications;
    private List<MedicationItem> conceptMedications;
    private List<String> availableUnits;
    private List<String> availableForms;
    //trip information
    private MissionTripItem missionTripItem;
    private List<MissionTripItem> missionTripList;

    public List<MissionTripItem> getMissionTripList() { return missionTripList;}

    public void setMissionTripList(List<MissionTripItem> missionTripList) {
        this.missionTripList = missionTripList;
    }

    public List<MedicationItem> getMedications() {
        return medications;
    }

    public void setMedications(List<MedicationItem> medications) {
        this.medications = medications;
    }

    public List<String> getAvailableUnits() {
        return availableUnits;
    }

    public void setAvailableUnits(List<String> availableUnits) {
        this.availableUnits = availableUnits;
    }

    public List<String> getAvailableForms() {
        return availableForms;
    }

    public void setAvailableForms(List<String> availableForms) {
        this.availableForms = availableForms;
    }

    public MissionTripItem getMissionTripItem() {
        return missionTripItem;
    }

    public void setMissionTripItem(MissionTripItem missionTripItem) {
        this.missionTripItem = missionTripItem;
    }

    public List<MedicationItem> getConceptMedications() {
        return conceptMedications;
    }

    public void setConceptMedications(List<MedicationItem> conceptMedications) {
        this.conceptMedications = conceptMedications;
    }
}
