package femr.data.models.research;


import femr.common.models.research.IPatient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *  implements the patient model for the research service allowing use to only use a
 *  subset of the columns in the patients table
 */
@Entity
@Table(name = "patients")
public class Patient implements IPatient {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "age", nullable = false)
    private Date age;
    @Column(name = "sex", nullable = true)
    private String sex;
    @Column(name = "city", nullable = false)
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
