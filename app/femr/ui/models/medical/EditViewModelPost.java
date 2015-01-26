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
package femr.ui.models.medical;

import femr.common.models.PrescriptionItem;
import femr.common.models.TabFieldItem;

import java.util.List;

public class EditViewModelPost {
    private String customFieldJSON;
    private String multipleHpiJSON;

    private List<TabFieldItem> tabFieldItems;

    //patient identifier
    private int id;


    //prescription fields
    private List<PrescriptionItem> prescriptions;

    //treatment fields
    private String assessment;
    private String problem0;
    private String problem1;
    private String problem2;
    private String problem3;
    private String problem4;
    private String treatment;
    //hpi fields
    private String onset;
    private String onsetTime;
    private String severity;
    private String radiation;
    private String quality;
    private String provokes;
    private String palliates;
    private String timeOfDay;
    private String physicalExamination;
    private String narrative;

    //editable information - PMH
    private String familyHistory;
    private String medicalSurgicalHistory;
    private String socialHistory;
    private String currentMedication;

    //Photo Info Arrays
    private List<Boolean> deleteRequested;
    private List<Boolean> hasUpdatedDesc;
    private List<String>  imageDescText;
    private List<Integer> photoId;

    public List<Boolean> getDeleteRequested() {
        return deleteRequested;
    }

    public void setDeleteRequested(List<Boolean> deleteRequested) {
        this.deleteRequested = deleteRequested;
    }

    public List<Boolean> getHasUpdatedDesc() {
        return hasUpdatedDesc;
    }

    public void setHasUpdatedDesc(List<Boolean> hasUpdatedDesc) {
        this.hasUpdatedDesc = hasUpdatedDesc;
    }

    public List<Integer> getPhotoId() {
        return photoId;
    }

    public void setPhotoId(List<Integer> photoId) {
        this.photoId = photoId;
    }

    public List<String> getImageDescText() { return imageDescText; }
    public void setImageDescText(List<String> lst) { this.imageDescText = lst; }


    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getProblem0() {
        return problem0;
    }

    public void setProblem0(String problem0) {
        this.problem0 = problem0;
    }

    public String getProblem1() {
        return problem1;
    }

    public void setProblem1(String problem1) {
        this.problem1 = problem1;
    }

    public String getProblem2() {
        return problem2;
    }

    public void setProblem2(String problem2) {
        this.problem2 = problem2;
    }

    public String getProblem3() {
        return problem3;
    }

    public void setProblem3(String problem3) {
        this.problem3 = problem3;
    }

    public String getProblem4() {
        return problem4;
    }

    public void setProblem4(String problem4) {
        this.problem4 = problem4;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOnset() {
        return onset;
    }

    public void setOnset(String onset) {
        this.onset = onset;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getRadiation() {
        return radiation;
    }

    public void setRadiation(String radiation) {
        this.radiation = radiation;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getProvokes() {
        return provokes;
    }

    public void setProvokes(String provokes) {
        this.provokes = provokes;
    }

    public String getPalliates() {
        return palliates;
    }

    public void setPalliates(String palliates) {
        this.palliates = palliates;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(String physicalExamination) {
        this.physicalExamination = physicalExamination;
    }

    public String getOnsetTime() {
        return onsetTime;
    }

    public void setOnsetTime(String onsetTime) {
        this.onsetTime = onsetTime;
    }


    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getSocialHistory() {
        return socialHistory;
    }

    public void setSocialHistory(String socialHistory) {
        this.socialHistory = socialHistory;
    }

    public String getCurrentMedication() {
        return currentMedication;
    }

    public void setCurrentMedication(String currentMedication) {
        this.currentMedication = currentMedication;
    }

    public String getMedicalSurgicalHistory() {
        return medicalSurgicalHistory;
    }

    public void setMedicalSurgicalHistory(String medicalSurgicalHistory) {
        this.medicalSurgicalHistory = medicalSurgicalHistory;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getCustomFieldJSON() {
        return customFieldJSON;
    }

    public void setCustomFieldJSON(String customFieldJSON) {
        this.customFieldJSON = customFieldJSON;
    }

    public String getMultipleHpiJSON() {
        return multipleHpiJSON;
    }

    public void setMultipleHpiJSON(String multipleHpiJSON) {
        this.multipleHpiJSON = multipleHpiJSON;
    }

    public List<TabFieldItem> getTabFieldItems() {
        return tabFieldItems;
    }

    public void setTabFieldItems(List<TabFieldItem> tabFieldItems) {
        this.tabFieldItems = tabFieldItems;
    }

    public List<PrescriptionItem> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionItem> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
