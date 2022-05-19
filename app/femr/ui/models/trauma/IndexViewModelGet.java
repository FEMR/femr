package femr.ui.models.trauma;

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

    public List<VitalItem> getVitalNamesForTable() {
        return vitalNames.subList(0, 11);
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
