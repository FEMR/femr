package femr.ui.models.triage;

import java.util.Date;

//NOTE: the triage view sets the input element names dynamically
//based on the vital name entry in the database, but this
//ViewModel does NOT.
public class CreateViewModelGet {
    //patient info
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int age;
    private Date birth;
    private String sex;
    //search info
    private boolean searchError = false;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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
}
