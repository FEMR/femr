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

import femr.common.models.PhotoItem;
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
    private Map<String, String> pmhFields;
    private Map<String, String> treatmentFields;
    private Map<String, String> customFields;

    //Map<chiefComplaint, Map<fieldName, fieldValue>>
    private boolean isMultipleChiefComplaints;
    private Map<String, Map<String,String>> hpiFieldsWithMultipleChiefComplaints;
    private Map<String, String> hpiFieldsWithoutMultipleChiefComplaints;



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

    public Map<String, String> getPmhFields() {
        return pmhFields;
    }

    public void setPmhFields(Map<String, String> pmhFields) {
        this.pmhFields = pmhFields;
    }

    public Map<String, String> getTreatmentFields() {
        return treatmentFields;
    }

    public void setTreatmentFields(Map<String, String> treatmentFields) {
        this.treatmentFields = treatmentFields;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, String> customFields) {
        this.customFields = customFields;
    }

    public boolean isMultipleChiefComplaints() {
        return isMultipleChiefComplaints;
    }

    public void setIsMultipleChiefComplaints(boolean isMultipleChiefComplaints) {
        this.isMultipleChiefComplaints = isMultipleChiefComplaints;
    }

    public Map<String, Map<String, String>> getHpiFieldsWithMultipleChiefComplaints() {
        return hpiFieldsWithMultipleChiefComplaints;
    }

    public void setHpiFieldsWithMultipleChiefComplaints(Map<String, Map<String, String>> hpiFieldsWithMultipleChiefComplaints) {
        this.hpiFieldsWithMultipleChiefComplaints = hpiFieldsWithMultipleChiefComplaints;
    }

    public Map<String, String> getHpiFieldsWithoutMultipleChiefComplaints() {
        return hpiFieldsWithoutMultipleChiefComplaints;
    }

    public void setHpiFieldsWithoutMultipleChiefComplaints(Map<String, String> hpiFieldsWithoutMultipleChiefComplaints) {
        this.hpiFieldsWithoutMultipleChiefComplaints = hpiFieldsWithoutMultipleChiefComplaints;
    }
}
