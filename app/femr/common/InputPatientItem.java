package femr.common;

import java.util.Date;

public class InputPatientItem {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String city;
    private final String address;
    private final int userId;
    private final Date age;
    private final String sex;
    private final Integer weeksPregnant;
    private final Integer heightFeet;
    private final Integer heightInches;
    private final Float weight;
    private final String pathToPatientPhoto;
    private final Integer photoId;
    private final String ageClassification;
    private final Integer isBirthDateCorrect;

    /**
     * @param id                 id of the patient, not null
     * @param firstName          first name of the patient, not null
     * @param lastName           last name of the patient, not null
     * @param city               city that the patient lives in, not null
     * @param address            address of the patient, may be null
     * @param userId             id of the user that checked in the patient in triage, not null
     * @param age                age of the patient, may be null
     * @param sex                sex of the patient, may be null
     * @param weeksPregnant      how many weeks pregnant the patient is, may be null
     * @param heightFeet         how tall the patient is, may be null
     * @param heightInches       how tall the patient is, may be null
     * @param weight             how much the patient weighs, may be null
     * @param pathToPatientPhoto filepath to the patient photo, may be null
     * @param photoId            id of the patients photo, may be null
     * @param ageClassification  age classification of the patient (adult,child, etc), may be null
     */
    public InputPatientItem(int id, String firstName, String lastName, String city, String address, int userId, Date age, String sex, Integer weeksPregnant, Integer heightFeet, Integer heightInches, Float weight, String pathToPatientPhoto, Integer photoId, String ageClassification, Integer isBirthDateCorrect) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.address = address;
        this.userId = userId;
        this.age = age;
        this.sex = sex;
        this.weeksPregnant = weeksPregnant;
        this.heightFeet = heightFeet;
        this.heightInches = heightInches;
        this.weight = weight;
        this.pathToPatientPhoto = pathToPatientPhoto;
        this.photoId = photoId;
        this.ageClassification = ageClassification;
        this.isBirthDateCorrect = isBirthDateCorrect;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public int getUserId() {
        return userId;
    }

    public Date getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public Integer getWeeksPregnant() {
        return weeksPregnant;
    }

    public Integer getHeightFeet() {
        return heightFeet;
    }

    public Integer getHeightInches() {
        return heightInches;
    }

    public Float getWeight() {
        return weight;
    }

    public String getPathToPatientPhoto() {
        return pathToPatientPhoto;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public String getAgeClassification() {
        return ageClassification;
    }

    public Integer getIsBirthDateCorrect() {
        return isBirthDateCorrect;
    }
}
