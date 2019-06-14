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
package femr.common.models;

import femr.util.stringhelpers.StringUtils;

import java.util.*;

//One ResearchExportItem per line in the research CSV export
public class ResearchExportItem {

    private UUID patientId;
    private String patientCity;
    private String gender;
    private Integer age;
    private String birthDate;
    private Boolean isPregnant;
    private Integer weeksPregnant;
    private String dayOfVisit;
    private Integer tripId;
    private List<String> chiefComplaints = new ArrayList<>();
    private List<String> dispensedMedications = new ArrayList<>();
    private Map<String, Float> vitalMap = new HashMap<>();
    private Map<String, List<String>> tabFieldMap = new HashMap<>();
    private String tripTeam;
    private String tripCountry;

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getPatientCity() {
        return patientCity;
    }

    public void setPatientCity(String patientCity) {
        this.patientCity = patientCity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {

        if(StringUtils.isNullOrWhiteSpace(gender)) gender = "Not Available";

        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getIsPregnant() {
        return isPregnant;
    }

    public void setIsPregnant(Boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    public Integer getWeeksPregnant() {
        return weeksPregnant;
    }

    public void setWeeksPregnant(Integer weeksPregnant) {
        this.weeksPregnant = weeksPregnant;
    }

    public List<String> getChiefComplaints() {
        return chiefComplaints;
    }

    public void setChiefComplaints(List<String> chiefComplaints) {
        this.chiefComplaints = chiefComplaints;
    }

    public List<String> getDispensedMedications() {
        return dispensedMedications;
    }

    public void setDispensedMedications(List<String> dispensedMedications) {
        this.dispensedMedications = dispensedMedications;
    }

    public Map<String, Float> getVitalMap() {
        return vitalMap;
    }

    public void setVitalMap(Map<String, Float> vitalMap) {
        this.vitalMap = vitalMap;
    }

    public Map<String, List<String>> getTabFieldMap() {
        return tabFieldMap;
    }

    public void setTabFieldMap(Map<String, List<String>> tabFieldMap) {
        this.tabFieldMap = tabFieldMap;
    }

    public String getDayOfVisit() {
        return dayOfVisit;
    }

    public void setDayOfVisit(String day) {
        this.dayOfVisit = day;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getTripTeam() {
        return tripTeam;
    }

    public void setTripTeam(String tripTeam) {
        this.tripTeam = tripTeam;
    }

    public String getTripCountry() {
        return tripCountry;
    }

    public void setTripCountry(String tripCountry) {
        this.tripCountry = tripCountry;
    }

}
