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

import femr.data.models.core.IPatientEncounterTabField;
import femr.data.models.core.IPatientPrescription;
import femr.data.models.core.ISystemSetting;
import femr.data.models.mysql.SystemSetting;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.jboss.netty.util.internal.StringUtil;

import java.util.Date;
import java.util.List;

/**
 * Responsible for creating item objects (common/models)
 * Only visible to ui & service layer.
 */
public class ItemMapper {

    /**
     * Creates a city item.
     *
     * @param cityName    name of the city
     * @param countryName name of the country that the city is in
     * @return a new city item or null if either parameters don't exist
     */
    public static CityItem createCityItem(String cityName, String countryName) {
        if (StringUtils.isNullOrWhiteSpace(cityName) || StringUtils.isNullOrWhiteSpace(countryName))
            return null;

        CityItem cityItem = new CityItem();
        cityItem.setCityName(cityName);
        cityItem.setCountryName(countryName);
        return cityItem;
    }

    /**
     * Creates a patient item, the following fields are required to have values:
     * <ul>
     * <li>id</li>
     * <li>firstName</li>
     * <li>lastName</li>
     * <li>city</li>
     * <li>userId</li>
     * </ul>
     *
     * @param id                 id of the patient
     * @param firstName          first name of the patient
     * @param lastName           last name of the patient
     * @param city               city that the patient lives in
     * @param address            address of the patient
     * @param userId             id of the user that checked in the patient in triage
     * @param age                age of the patient
     * @param sex                sex of the patient
     * @param weeksPregnant      how many weeks pregnant the patient is
     * @param heightFeet         how tall the patient is (in feet)
     * @param heightInches       how tall the patient is (in inches)
     * @param weight             how much the patient weights (imperial)
     * @param pathToPatientPhoto filepath to the patient photo
     * @param photoId            id of the patients photo
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
     * Create a new prescription item
     *
     * @param prescriptionName name of the prescription
     * @return a new prescription item or null if the name is empty
     */
    public static PrescriptionItem createPatientPrescriptionItem(String prescriptionName) {

        if (StringUtils.isNullOrWhiteSpace(prescriptionName)) {

            return null;
        }

        PrescriptionItem prescriptionItem = new PrescriptionItem();
        prescriptionItem.setName(prescriptionName);

        return prescriptionItem;
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

        if (StringUtils.isNullOrWhiteSpace(imageURL) || insertTimeStamp == null) {

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
     * create a new PrescriptionItem that has been replaced
     *
     * @param id            id of the prescription
     * @param name          name of the prescription
     * @param replacementId id of the prescription that replaced this prescription
     * @return a new PrescriptionItem or null if the required fields are null
     */
    public static PrescriptionItem createPrescriptionItem(int id, String name, Integer replacementId) {

        if (StringUtils.isNullOrWhiteSpace(name) ||
                replacementId == null) {

            return null;
        }

        PrescriptionItem prescriptionItem = new PrescriptionItem();
        prescriptionItem.setId(id);
        prescriptionItem.setName(name);
        prescriptionItem.setReplacementId(replacementId);

        return prescriptionItem;
    }

    /**
     * create a new PrescriptionItem that has not been replaced
     *
     * @param id   id of the prescription
     * @param name name of the prescription
     * @return a new PrescriptionItem or null if the required fields are null
     */
    public static PrescriptionItem createPrescriptionItem(int id, String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        PrescriptionItem prescriptionItem = new PrescriptionItem();
        prescriptionItem.setId(id);
        prescriptionItem.setName(name);

        return prescriptionItem;
    }

    /**
     * create a new ProblemItem
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
     * Creates a setting item, currently requires direct knowledge of a column in the database
     *
     * @param systemSettings a list of all system settings
     * @return
     */
    public static SettingItem createSettingItem(List<? extends ISystemSetting> systemSettings) {

        SettingItem settingItem = new SettingItem();

        if (systemSettings == null)
            return null;
        else if (systemSettings.size() > 0) {

            for (ISystemSetting ss : systemSettings) {
                switch (ss.getName()) {
                    case "Multiple chief complaints":
                        settingItem.setMultipleChiefComplaint(ss.isActive());
                        break;
                    case "Medical PMH Tab":
                        settingItem.setPmhTab(ss.isActive());
                        break;
                    case "Medical Photo Tab":
                        settingItem.setPhotoTab(ss.isActive());
                        break;
                    case "Medical HPI Consolidate":
                        settingItem.setConsolidateHPI(ss.isActive());
                        break;
                }
            }
        }

        return settingItem;
    }

    /**
     * Create a new TabItem
     *
     * @param name            name of the tab
     * @param isCustom        was the tab made custom by superuser?
     * @param leftColumnSize  size of the left column
     * @param rightColumnSize size of the right column
     * @return duh
     */
    public static TabItem createTabItem(String name, boolean isCustom, Integer leftColumnSize, Integer rightColumnSize) {

        if (StringUtils.isNullOrWhiteSpace(name) ||
                leftColumnSize == null ||
                rightColumnSize == null) {

            return null;
        }

        TabItem tabItem = new TabItem();
        tabItem.setName(name);
        tabItem.setCustom(isCustom);
        tabItem.setLeftColumnSize(leftColumnSize);
        tabItem.setRightColumnSize(rightColumnSize);
        return tabItem;
    }

    /**
     * Create a new tab field item without a value
     *
     * @param name        the name of the field
     * @param type        the fields type e.g. number, text
     * @param size        the size of the field e.g. small, med, large
     * @param order       sorting order for the field
     * @param placeholder placeholder text for the field
     * @param value       current value of the field
     * @param chiefComplaint what chief complaint the field belongs to
     * @return a new TabFieldItem or null if name is empty
     */
    public static TabFieldItem createTabFieldItem(String name,
                                                  String type,
                                                  String size,
                                                  Integer order,
                                                  String placeholder,
                                                  String value,
                                                  String chiefComplaint) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        TabFieldItem tabFieldItem = new TabFieldItem();
        tabFieldItem.setName(name);
        if (StringUtils.isNotNullOrWhiteSpace(placeholder))
            tabFieldItem.setPlaceholder(placeholder);
        if (order != null)
            tabFieldItem.setOrder(order);
        if (StringUtils.isNotNullOrWhiteSpace(size))
            tabFieldItem.setSize(size);
        if (StringUtils.isNotNullOrWhiteSpace(type))
            tabFieldItem.setType(type);
        if (StringUtils.isNotNullOrWhiteSpace(value))
            tabFieldItem.setValue(value);
        if (StringUtils.isNotNullOrWhiteSpace(chiefComplaint))
            tabFieldItem.setChiefComplaint(chiefComplaint);

        return tabFieldItem;
    }

    /**
     * Create a team item
     *
     * @param name name of the team
     * @return a new team item or null parameters are empty
     */
    public static TeamItem createTeamItem(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {
            return null;
        }

        TeamItem teamItem = new TeamItem();
        teamItem.setName(name);

        return teamItem;
    }

    /**
     * Create a team item
     *
     * @param name     name of the team
     * @param location where the team is based out of
     * @return a new team item or null parameters are empty
     */
    public static TeamItem createTeamItem(String name, String location) {

        if (StringUtils.isNullOrWhiteSpace(name)) {
            return null;
        }

        TeamItem teamItem = new TeamItem();
        teamItem.setName(name);
        teamItem.setLocation(location);

        return teamItem;
    }

    /**
     * Create a team item
     *
     * @param name        name of the team
     * @param location    where the team is based out of
     * @param description a description of the team
     * @return a new team item or null if name is empty
     */
    public static TeamItem createTeamItem(String name, String location, String description) {

        if (StringUtils.isNullOrWhiteSpace(name)) {
            return null;
        }

        TeamItem teamItem = new TeamItem();
        teamItem.setName(name);
        teamItem.setLocation(location);
        teamItem.setDescription(description);

        return teamItem;
    }


    /**
     * Creates a trip item
     *
     * @param teamName    name of the team
     * @param tripCity    city of the trip
     * @param tripCountry country of the trip
     * @param startDate   when the trip starts
     * @param endDate     when the trip ends
     * @return a new trip item or null if any of the parameters are empty
     */
    public static TripItem createTripItem(String teamName, String tripCity, String tripCountry, Date startDate, Date endDate) {

        if (StringUtils.isNullOrWhiteSpace(teamName) ||
                StringUtils.isNullOrWhiteSpace(tripCity) ||
                StringUtils.isNullOrWhiteSpace(tripCountry) ||
                startDate == null ||
                endDate == null) {
            return null;
        }

        TripItem tripItem = new TripItem();
        tripItem.setTeamName(teamName);
        tripItem.setTripCity(tripCity);
        tripItem.setTripCountry(tripCountry);
        tripItem.setTripStartDate(startDate);
        tripItem.setTripEndDate(endDate);

        return tripItem;
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
