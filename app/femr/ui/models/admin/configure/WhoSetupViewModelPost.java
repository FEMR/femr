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
package femr.ui.models.admin.configure;

public class WhoSetupViewModelPost {

    private String orgName;
    private boolean type1Mobile;
    private boolean type1Fixed;
    private boolean type2;
    private boolean type3;
    private boolean specializedCell;
    private String contactPersons;
    private String phoneNo;
    private String email;
    private String stateAdmin1;
    private String villageAdmin3;
    private String facilityName;
    private String geoLat;
    private String geoLong;

    public String getOrgName() { return orgName; }
    public void setOrgName(String orgName) { this.orgName = orgName; }

    public boolean isType1Mobile() { return type1Mobile; }
    public void setType1Mobile(boolean type1Mobile) { this.type1Mobile = type1Mobile; }

    public boolean isType1Fixed() { return type1Fixed; }
    public void setType1Fixed(boolean type1Fixed) { this.type1Fixed = type1Fixed; }

    public boolean isType2() { return type2; }
    public void setType2(boolean type2) { this.type2 = type2; }

    public boolean isType3() { return type3; }
    public void setType3(boolean type3) { this.type3 = type3; }

    public boolean isSpecializedCell() { return specializedCell; }
    public void setSpecializedCell(boolean specializedCell) { this.specializedCell = specializedCell; }

    public String getContactPersons() { return contactPersons; }
    public void setContactPersons(String contactPersons) { this.contactPersons = contactPersons; }

    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStateAdmin1() { return stateAdmin1; }
    public void setStateAdmin1(String stateAdmin1) { this.stateAdmin1 = stateAdmin1; }

    public String getVillageAdmin3() { return villageAdmin3; }
    public void setVillageAdmin3(String villageAdmin3) { this.villageAdmin3 = villageAdmin3; }

    public String getFacilityName() { return facilityName; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }

    public String getGeoLat() { return geoLat; }
    public void setGeoLat(String geoLat) { this.geoLat = geoLat; }

    public String getGeoLong() { return geoLong; }
    public void setGeoLong(String geoLong) { this.geoLong = geoLong; }
}
