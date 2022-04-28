package femr.data.models.core;

import femr.data.models.mysql.PatientEncounter;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public interface ITraumaPatient {
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
