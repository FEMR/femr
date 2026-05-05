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
package femr.data.models.mysql;

import femr.data.models.core.IWhoReportConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "who_report_config")
public class WhoReportConfig implements IWhoReportConfig {

    @Id
    @Column(name = "mission_trip_id", unique = true, nullable = false)
    private int missionTripId;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "team_type_1mobile")
    private boolean type1Mobile;

    @Column(name = "team_type_1fixed")
    private boolean type1Fixed;

    @Column(name = "team_type_2")
    private boolean type2;

    @Column(name = "team_type_3")
    private boolean type3;

    @Column(name = "team_specialized")
    private boolean specializedCell;

    @Column(name = "contact_persons")
    private String contactPersons;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "email")
    private String email;

    @Column(name = "state_admin1")
    private String stateAdmin1;

    @Column(name = "village_admin3")
    private String villageAdmin3;

    @Column(name = "facility_name")
    private String facilityName;

    @Column(name = "geo_lat")
    private String geoLat;

    @Column(name = "geo_long")
    private String geoLong;

    @Override
    public int getMissionTripId() {
        return missionTripId;
    }

    @Override
    public void setMissionTripId(int missionTripId) {
        this.missionTripId = missionTripId;
    }

    @Override
    public String getOrgName() {
        return orgName;
    }

    @Override
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public boolean isType1Mobile() {
        return type1Mobile;
    }

    @Override
    public void setType1Mobile(boolean type1Mobile) {
        this.type1Mobile = type1Mobile;
    }

    @Override
    public boolean isType1Fixed() {
        return type1Fixed;
    }

    @Override
    public void setType1Fixed(boolean type1Fixed) {
        this.type1Fixed = type1Fixed;
    }

    @Override
    public boolean isType2() {
        return type2;
    }

    @Override
    public void setType2(boolean type2) {
        this.type2 = type2;
    }

    @Override
    public boolean isType3() {
        return type3;
    }

    @Override
    public void setType3(boolean type3) {
        this.type3 = type3;
    }

    @Override
    public boolean isSpecializedCell() {
        return specializedCell;
    }

    @Override
    public void setSpecializedCell(boolean specializedCell) {
        this.specializedCell = specializedCell;
    }

    @Override
    public String getContactPersons() {
        return contactPersons;
    }

    @Override
    public void setContactPersons(String contactPersons) {
        this.contactPersons = contactPersons;
    }

    @Override
    public String getPhoneNo() {
        return phoneNo;
    }

    @Override
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getStateAdmin1() {
        return stateAdmin1;
    }

    @Override
    public void setStateAdmin1(String stateAdmin1) {
        this.stateAdmin1 = stateAdmin1;
    }

    @Override
    public String getVillageAdmin3() {
        return villageAdmin3;
    }

    @Override
    public void setVillageAdmin3(String villageAdmin3) {
        this.villageAdmin3 = villageAdmin3;
    }

    @Override
    public String getFacilityName() {
        return facilityName;
    }

    @Override
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    @Override
    public String getGeoLat() {
        return geoLat;
    }

    @Override
    public void setGeoLat(String geoLat) {
        this.geoLat = geoLat;
    }

    @Override
    public String getGeoLong() {
        return geoLong;
    }

    @Override
    public void setGeoLong(String geoLong) {
        this.geoLong = geoLong;
    }
}
