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

import java.util.List;
import java.util.Map;
import java.util.UUID;

//One ResearchExportItem per line in the research CSV export
public class ResearchExportItem {

    private UUID patientId;
    private String gender;
    private Integer age;
    private Boolean isPregnant;
    private Integer weeksPregnant;
    private String dayOfVisit;
    private Integer tripId;
    private List<String> chiefComplaints;
    private List<String> prescribedMedications;
    private List<String> dispensedMedications;
    private Map<String, Float> vitalMap;
    private Map<String, String> tabFieldMap;
    private String trip_team;
    private String trip_country;

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public List<String> getPrescribedMedications() {
        return prescribedMedications;
    }

    public void setPrescribedMedications(List<String> prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
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

    public Map<String, String> getTabFieldMap() {
        return tabFieldMap;
    }

    public void setTabFieldMap(Map<String, String> tabFieldMap) {
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

    public String getTrip_team() {
        return trip_team;
    }

    public void setTrip_team(String trip_team) {
        this.trip_team = trip_team;
    }

    public String getTrip_country() {
        return trip_country;
    }

    public void setTrip_country(String trip_country) {
        this.trip_country = trip_country;
    }
}
