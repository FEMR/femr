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

import femr.common.models.*; /* Alaa Serhan */
import femr.common.models.PhotoItem;
import femr.common.models.TabFieldItem;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import java.util.List;
import java.util.Map;

public class IndexEncounterMedicalViewModel {
    //Photos that were entered on the photo tab in Medical
    private List<PhotoItem> photos;
    //Vitals for the patients encounter including replacements, if any
    private VitalMultiMap vitalList;

    private List<String> chiefComplaints;
    //Map<fieldName, fieldValue>
    private Map<String, TabFieldItem> pmhFields;
    private Map<String, TabFieldItem> treatmentFields;
    private Map<String, TabFieldItem> customFields;

    //Map<chiefComplaint, Map<fieldName, fieldValue>>
    private boolean isMultipleChiefComplaints;
    private Map<String, Map<String, TabFieldItem>> hpiFieldsWithMultipleChiefComplaints;
    private Map<String, TabFieldItem> hpiFieldsWithoutMultipleChiefComplaints;

    private SettingItem settings; /* Alaa Serhan */


    public List<PhotoItem> getPhotos() {
        return photos;
    }

    public PhotoItem getPhoto(int index) {
        return photos.get(index);
    }

    public void setPhotos(List<PhotoItem> photos) {
        this.photos = photos;
    }

    public VitalMultiMap getVitalList() {
        return vitalList;
    }

    public void setVitalList(VitalMultiMap vitalList) {
        this.vitalList = vitalList;
    }

    public List<String> getChiefComplaints() {
        return chiefComplaints;
    }

    public void setChiefComplaints(List<String> chiefComplaints) {
        this.chiefComplaints = chiefComplaints;
    }

    public Map<String, TabFieldItem> getPmhFields() {
        return pmhFields;
    }

    public void setPmhFields(Map<String, TabFieldItem> pmhFields) {
        this.pmhFields = pmhFields;
    }

    public Map<String, TabFieldItem> getTreatmentFields() {
        return treatmentFields;
    }

    public void setTreatmentFields(Map<String, TabFieldItem> treatmentFields) {
        this.treatmentFields = treatmentFields;
    }

    public Map<String, TabFieldItem> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, TabFieldItem> customFields) {
        this.customFields = customFields;
    }

    public boolean isMultipleChiefComplaints() {
        return isMultipleChiefComplaints;
    }

    public void setIsMultipleChiefComplaints(boolean isMultipleChiefComplaints) {
        this.isMultipleChiefComplaints = isMultipleChiefComplaints;
    }

    public Map<String, Map<String, TabFieldItem>> getHpiFieldsWithMultipleChiefComplaints() {
        return hpiFieldsWithMultipleChiefComplaints;
    }

    public void setHpiFieldsWithMultipleChiefComplaints(Map<String, Map<String, TabFieldItem>> hpiFieldsWithMultipleChiefComplaints) {
        this.hpiFieldsWithMultipleChiefComplaints = hpiFieldsWithMultipleChiefComplaints;
    }

    public Map<String, TabFieldItem> getHpiFieldsWithoutMultipleChiefComplaints() {
        return hpiFieldsWithoutMultipleChiefComplaints;
    }

    public void setHpiFieldsWithoutMultipleChiefComplaints(Map<String, TabFieldItem> hpiFieldsWithoutMultipleChiefComplaints) {
        this.hpiFieldsWithoutMultipleChiefComplaints = hpiFieldsWithoutMultipleChiefComplaints;
    }

    /* Alaa Serhan */
    public SettingItem getSettings() {
        return settings;
    }
    public void setSettings(SettingItem settings) {
        this.settings = settings;
    }
}
