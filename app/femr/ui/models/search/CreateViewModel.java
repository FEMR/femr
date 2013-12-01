package femr.ui.models.search;

import femr.common.models.IPatient;
import femr.util.calculations.dateUtils;

import java.util.List;

public class CreateViewModel {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int age;
    private String sex;
    private int userID;
    private List<IPatient> patientNameResult;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List getPatientNameResult() {
        return patientNameResult;
    }

    public void setDupList(int index){
        IPatient patient = patientNameResult.get(index);
        setFirstName(patient.getFirstName());
        setLastName(patient.getLastName());
        setAddress(patient.getAddress());
        setCity(patient.getCity());
        setSex(patient.getSex());
        setPatientID(patient.getId());
        setAge(dateUtils.calculateYears(patient.getAge()));
    }

    public void setPatientNameResult(List patientNameResult) {
        this.patientNameResult = patientNameResult;
    }

    public int getPatientID() {
        return userID;
    }

    public void setPatientID(int userID) {
        this.userID = userID;
    }
}
