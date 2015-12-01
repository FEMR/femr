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
package femr.common.models;

import org.joda.time.DateTime;

import java.util.Date;

public class PatientItem {
    private int Id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String age;//age (int)
    private Date birth;//date of birth (Date)
    private String friendlyDateOfBirth;
    private String sex;
    private Integer photoId;
    private String pathToPhoto;
    private int userId;
    private Integer weeksPregnant;
    private Integer heightFeet;
    private Integer heightInches;
    private Float weight;
    //can be determined from age classification and/or age itself
    private Boolean isOverSeventeen;
    private Boolean isOverTwentyFour;

    public PatientItem(){
        //default empty values
        this.Id = 0;
        this.pathToPhoto = "";
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getWeeksPregnant() {
        return weeksPregnant;
    }

    public void setWeeksPregnant(Integer weeksPregnant) {
        this.weeksPregnant = weeksPregnant;
    }

    public Integer getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(Integer heightFeet) {
        this.heightFeet = heightFeet;
    }

    public Integer getHeightInches() {
        return heightInches;
    }

    public void setHeightInches(Integer heightInches) {
        this.heightInches = heightInches;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getFriendlyDateOfBirth() {
        return friendlyDateOfBirth;
    }

    public void setFriendlyDateOfBirth(String friendlyDateOfBirth) {
        this.friendlyDateOfBirth = friendlyDateOfBirth;
    }

    /**
     * returning null does not imply anything other than laziness.
     * >=18 is true
     * < 18 is false
     */
    public Boolean getIsOverSeventeen() {
        return isOverSeventeen;
    }

    public void setIsOverSeventeen(Boolean isOverSeventeen) {
        this.isOverSeventeen = isOverSeventeen;
    }

    /**
     * >=25 is true
     * < 25 is false
     */
    public Boolean getIsOverTwentyFour() {
        return isOverTwentyFour;
    }

    public void setIsOverTwentyFour(Boolean overTwentyFour) {
        isOverTwentyFour = overTwentyFour;
    }
}
