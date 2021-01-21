package mock.femr.data.models;

import femr.data.models.core.IPatient;
import femr.data.models.core.IPhoto;
import femr.data.models.mysql.PatientEncounter;
import femr.data.models.mysql.Photo;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MockPatient implements IPatient {

    private int id = 0;
    private int userId = 0;
    private String firstName = "firstName";
    private String lastName = "lastName";
    private String phoneNumber = "phoneNumber";
    private Date age = new Date();
    private String sex = "age";
    private String address = "address";
    private String city = "city";
    private Photo photo = new Photo();
    private List<PatientEncounter> patientEncounters = new ArrayList<>();
    private DateTime isDeleted = new DateTime();
    private Integer deletedByUserId = 0;
    private String reasonDeleted = "reasonDeleted";


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {

        this.id = id;
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
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
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
    public IPhoto getPhoto() {
        return photo;
    }

    @Override
    public void setPhoto(IPhoto photo) {

        this.photo = (Photo)photo;
    }

    @Override
    public List<PatientEncounter> getPatientEncounters() {
        return patientEncounters;
    }

    @Override
    public void setPatientEncounters(List<PatientEncounter> patientEncounters) {

        this.patientEncounters = patientEncounters;
    }

    @Override
    public DateTime getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(DateTime isDeleted) {

        this.isDeleted = isDeleted;
    }

    @Override
    public Integer getDeletedByUserId() {
        return deletedByUserId;
    }

    @Override
    public void setDeletedByUserId(Integer deletedByUserId) {

        this.deletedByUserId = deletedByUserId;
    }

    @Override
    public String getReasonDeleted() {
        return reasonDeleted;
    }

    @Override
    public void setReasonDeleted(String reason) {

        this.reasonDeleted = reason;
    }
}
