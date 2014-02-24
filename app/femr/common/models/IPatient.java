package femr.common.models;

import java.util.Date;

public interface IPatient {

    int getId();

    int getUserId();

    void setUserId(int userId);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    Date getAge();

    void setAge(Date age);

    String getSex();

    void setSex(String sex);

    String getAddress();

    void setAddress(String address);

    String getCity();

    void setCity(String city);

    void setId(int id);

    Integer getPhotoId();

    void setPhotoId(Integer id);

}
