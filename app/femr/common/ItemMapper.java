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
package femr.common;

import femr.common.models.*;
import femr.data.models.core.IPatient;

import femr.data.models.core.IPatientPrescription;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;

import java.util.Date;

/**
 * Responsible for creating item objects (common/models)
 * Only visible to ui & service layer.
 */
public class ItemMapper {


    /**
     * Creates a patient item, the following fields are required to have values:
     * <ul>
     *     <li>id</li>
     *     <li>firstName</li>
     *     <li>lastName</li>
     *     <li>city</li>
     *     <li>userId</li>
     * </ul>
     *
     * @param id id of the patient
     * @param firstName first name of the patient
     * @param lastName last name of the patient
     * @param city city that the patient lives in
     * @param address address of the patient
     * @param userId id of the user that checked in the patient in triage
     * @param age age of the patient
     * @param sex sex of the patient
     * @param weeksPregnant how many weeks pregnant the patient is
     * @param heightFeet how tall the patient is (in feet)
     * @param heightInches how tall the patient is (in inches)
     * @param weight how much the patient weights (imperial)
     * @param pathToPatientPhoto filepath to the patient photo
     * @param photoId id of the patients photo
     * @return a new PatientItem or null if any of the required fields are empty
     */
    public static PatientItem createPatientItem(int id,
                                                String firstName,
                                                String lastName,
                                                String city,
                                                String address,
                                                int userId,
                                                Date age,
                                                String sex,
                                                Integer weeksPregnant,
                                                Integer heightFeet,
                                                Integer heightInches,
                                                Float weight,
                                                String pathToPatientPhoto,
                                                Integer photoId) {

        if (StringUtils.isNullOrWhiteSpace(firstName) ||
                StringUtils.isNullOrWhiteSpace(lastName) ||
                StringUtils.isNullOrWhiteSpace(city)) {

            return null;
        }
        PatientItem patientItem = new PatientItem();
        //required fields
        patientItem.setId(id);
        patientItem.setFirstName(firstName);
        patientItem.setLastName(lastName);
        patientItem.setCity(city);
        patientItem.setUserId(userId);
        //optional fields
        if (StringUtils.isNotNullOrWhiteSpace(address))
            patientItem.setAddress(address);
        if (StringUtils.isNotNullOrWhiteSpace(sex))
            patientItem.setSex(sex);
        if (age != null) {
            patientItem.setAge(dateUtils.getAge(age));//age (int)
            patientItem.setBirth(age);//date of birth(date)
            patientItem.setFriendlyDateOfBirth(dateUtils.getFriendlyDate(age));
        }
        if (StringUtils.isNotNullOrWhiteSpace(pathToPatientPhoto) && photoId != null) {
            patientItem.setPathToPhoto(pathToPatientPhoto);
            patientItem.setPhotoId(photoId);
        }
        if (weeksPregnant != null)
            patientItem.setWeeksPregnant(weeksPregnant);
        if (heightFeet != null)
            patientItem.setHeightFeet(heightFeet);
        if (heightInches != null)
            patientItem.setHeightInches(heightInches);
        if (weight != null)
            patientItem.setWeight(weight);

        return patientItem;
    }

    /**
     * create a photo item, all fields are required
     *
     * @param id              id of the photo
     * @param description     description of the photo
     * @param insertTimeStamp photo timestamp
     * @param imageURL        url to the image
     * @return a new PhotoItem or null if parameters are empty
     */
    public static PhotoItem createPhotoItem(int id, String description, Date insertTimeStamp, String imageURL) {

        if (StringUtils.isNullOrWhiteSpace(description) ||
                StringUtils.isNullOrWhiteSpace(imageURL) ||
                insertTimeStamp == null) {

            return null;
        }
        PhotoItem photoItem = new PhotoItem();
        photoItem.setId(id);
        photoItem.setImageDesc(description);
        photoItem.setImageUrl(imageURL);
        photoItem.setImageDate(StringUtils.ToSimpleDate(insertTimeStamp));

        return photoItem;
    }

    /**
     * create a new PrescriptionItem, the following fields are required to have values:
     * <ul>
     *     <li>id</li>
     *     <li>name</li>
     * </ul>
     *
     * @param id id of the prescription
     * @param name name of the prescription
     * @param replacementId id of the prescription that replaced this prescription
     * @return a new PrescriptionItem or null if the required fields are null
     */
    public static PrescriptionItem createPrescriptionItem(int id, String name, Integer replacementId) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }
        PrescriptionItem prescriptionItem = new PrescriptionItem();
        prescriptionItem.setId(id);
        prescriptionItem.setName(name);
        prescriptionItem.setReplacementId(replacementId);
        return prescriptionItem;
    }

    /**
     * create a new ProblemItem, all fields are requried
     *
     * @param name the name of the problem
     * @return a new ProblemItem or null if parameters are empty
     */
    public static ProblemItem createProblemItem(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }
        ProblemItem problemItem = new ProblemItem();
        problemItem.setName(name);

        return problemItem;
    }

    /**
     * Create a new VitalItem, all fields are required
     *
     * @param name  name of the vital
     * @param value value of the vital
     * @return a new VitalItem or null if parameters are empty
     */
    public static VitalItem createVitalItem(String name, Float value) {

        if (StringUtils.isNullOrWhiteSpace(name) || value == null) {

            return null;
        }
        VitalItem vitalItem = new VitalItem();
        vitalItem.setName(name);
        vitalItem.setValue(value);

        return vitalItem;
    }
}
