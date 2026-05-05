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
package femr.data.models.core;

public interface IWhoReportConfig {

    int getMissionTripId();

    void setMissionTripId(int missionTripId);

    String getOrgName();

    void setOrgName(String orgName);

    boolean isType1Mobile();

    void setType1Mobile(boolean type1Mobile);

    boolean isType1Fixed();

    void setType1Fixed(boolean type1Fixed);

    boolean isType2();

    void setType2(boolean type2);

    boolean isType3();

    void setType3(boolean type3);

    boolean isSpecializedCell();

    void setSpecializedCell(boolean specializedCell);

    String getContactPersons();

    void setContactPersons(String contactPersons);

    String getPhoneNo();

    void setPhoneNo(String phoneNo);

    String getEmail();

    void setEmail(String email);

    String getStateAdmin1();

    void setStateAdmin1(String stateAdmin1);

    String getVillageAdmin3();

    void setVillageAdmin3(String villageAdmin3);

    String getFacilityName();

    void setFacilityName(String facilityName);

    String getGeoLat();

    void setGeoLat(String geoLat);

    String getGeoLong();

    void setGeoLong(String geoLong);
}
