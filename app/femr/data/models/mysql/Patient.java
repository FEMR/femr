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
package femr.data.models.mysql;

import femr.data.models.core.IPatient;
import femr.data.models.core.IPhoto;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;
    @Column(name = "age")
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
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<PatientEncounter> patientEncounters;
    @Column(name = "isDeleted", nullable = true)
    private DateTime isDeleted;
    @Column(name = "deleted_by_user_id", unique = false, nullable = true)
    private Integer deletedByUserId;
    @Column(name = "reason_deleted", nullable = true)
    private String reasonDeleted;


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
    public void setDeletedByUserId(Integer userId) {
        this.deletedByUserId= userId;
    }

    @Override
    public String getReasonDeleted() { return reasonDeleted; }

    @Override
    public void setReasonDeleted(String reason) { this.reasonDeleted = reason; }

}
