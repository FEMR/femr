package femr.data.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patients")
public class Patient implements IPatient {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "user_id", unique = false, nullable = false)
    private int userId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "age", nullable = false)
    private Date age;
    @Column(name = "sex", nullable = true)
    private String sex;
    @Column(name = "address", nullable = true)
    private String address;
    @Column(name = "city", nullable = false)
    private String city;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "photo_id", nullable = true)
    private Photo photo;
//    @Column(name = "photo_id", nullable = true)
//    private Integer photoId;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Date getAge() {
        return age;
    }

    @Override
    public void setAge(Date age) {
        this.age = age;
    }

    @Override
    public String getSex() {
        return sex;
    }

    @Override
    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public IPhoto getPhoto() {
        return photo;
    }

    @Override
    public void setPhoto(IPhoto photo) {
        this.photo = (Photo) photo;
    }
}
