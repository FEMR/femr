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

import femr.common.models.PatientItem;
import femr.common.models.SettingItem;
import femr.common.models.VitalItem;
import java.util.List;
import java.util.Map;

//NOTE: the triage view sets the input element names dynamically
//based on the vital name entry in the database, but this
//ViewModel does NOT.
public class IndexViewModelGet {
    //patient info
    //contains photo path
    private PatientItem patient;
    //search info
    private boolean searchError = false;
    //vital names
    private List<VitalItem> vitalNames;
    //system settings
    private SettingItem settings;
    //all possible options for age classification
    private Map<String,String> possibleAgeClassifications;

    //Hidden Link
    private boolean linkToMedical = false;

    public boolean isLinkToMedical(){
        return linkToMedical;
    }

    public void setLinkToMedical(boolean linkToMedical){
        this.linkToMedical = linkToMedical;
    }

    public boolean isSearchError() {
        return searchError;
    }

    public void setSearchError(boolean searchError) {
        this.searchError = searchError;
    }

    public PatientItem getPatient() {
        return patient;
    }

    public void setPatient(PatientItem patient) {
        this.patient = patient;
    }

    public List<VitalItem> getVitalNames() {
        return vitalNames;
    }

    public void setVitalNames(List<VitalItem> vitalNames) {
        this.vitalNames = vitalNames;
    }

    public SettingItem getSettings() {
        return settings;
    }

    public void setSettings(SettingItem settings) {
        this.settings = settings;
    }

    public Map<String, String> getPossibleAgeClassifications() {
        return possibleAgeClassifications;
    }

    public void setPossibleAgeClassifications(Map<String, String> possibleAgeClassifications) {
        this.possibleAgeClassifications = possibleAgeClassifications;
    }
}
