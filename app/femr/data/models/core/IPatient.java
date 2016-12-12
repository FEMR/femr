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

import femr.data.models.mysql.PatientEncounter;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public interface IPatient {

    int getId();

    int getUserId();

    void setUserId(int userId);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    Date getAge();

    void setAge(Date age);

    String getSex();

    void setSex(String sex);

    String getAddress();

    void setAddress(String address);

    String getCity();

    void setCity(String city);

    void setId(int id);

    IPhoto getPhoto();

    void setPhoto(IPhoto photo);

    List<PatientEncounter> getPatientEncounters();

    void setPatientEncounters(List<PatientEncounter> patientEncounters);

    DateTime getIsDeleted();

    void setIsDeleted(DateTime isDeleted);
    
    Integer getDeletedByUserId() ;

    void setDeletedByUserId(Integer userId) ;

    String getReasonDeleted() ;

    void setReasonDeleted(String reason) ;

    
}
