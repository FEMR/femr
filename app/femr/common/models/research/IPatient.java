package femr.common.models.research;

import java.util.Date;

/**
 * the interface for the patient table used in the research service to get only a subset of the patient
 * columns back
 */
public interface IPatient {

    int getId();
    void setId(int id);

    Date getAge();
    void setAge(Date age);

    String getSex();
    void setSex(String sex);

    String getCity();
    void setCity(String city);

}
