package femr.ui.models.triage;

import femr.common.models.IVital;

import java.util.Date;
import java.util.List;

//NOTE: the triage view sets the input element names dynamically
//based on the vital name entry in the database, but this
//ViewModel does NOT.
public class CreateViewModelGet {
    //patient info
    private int Id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private Integer age;
    private Date birth;
    private String sex;
    //search info
    private boolean searchError = false;
    //vital names
    List<? extends IVital> vitalNames;

    public CreateViewModelGet() {
    }

    //begin general info
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isSearchError() {
        return searchError;
    }

    public void setSearchError(boolean searchError) {
        this.searchError = searchError;
    }

    public List<? extends IVital> getVitalNames(){
        return vitalNames;
    }
    public void setVitalNames(List<? extends IVital> vitalNames){
        this.vitalNames = vitalNames;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
