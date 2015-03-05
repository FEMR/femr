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

import femr.business.helpers.LogicDoer;
import femr.common.models.*;
import femr.data.models.core.*;
import femr.ui.models.research.FilterViewModel;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Responsible for creating item objects (common/models)
 * Only visible to ui & service layer.
 */
public class UIModelMapper {

    /**
     * Generate and provide an instance of CityItem.
     *
     * @param cityName    name of the city, not null
     * @param countryName name of the country that the city is in, not null
     * @return a new city item or null if processing fails
     */
    public static CityItem createCityItem(String cityName, String countryName) {

        if (StringUtils.isNullOrWhiteSpace(cityName) || StringUtils.isNullOrWhiteSpace(countryName)) {

            return null;
        }

        CityItem cityItem = new CityItem();

        cityItem.setCityName(cityName);
        cityItem.setCountryName(countryName);

        return cityItem;
    }

    /**
     * Generate and provide an instance of MedicationItem.
     *
     * @param medication the medication data item, not null
     * @return a new MedicationItem or null if processing fails
     */
    public static MedicationItem createMedicationItem(IMedication medication) {

        if (medication == null) {

            return null;
        }

        MedicationItem medicationItem = new MedicationItem();

        medicationItem.setId(medication.getId());
        medicationItem.setName(medication.getName());
        medicationItem.setQuantity_current(medication.getQuantity_current());
        medicationItem.setQuantity_total(medication.getQuantity_total());
        if (medication.getMedicationForm() != null) {
            medicationItem.setForm(medication.getMedicationForm().getName());
        }

        String fullActiveDrugName = "";
        for (IMedicationActiveDrug medicationActiveDrug : medication.getMedicationActiveDrugs()) {
            medicationItem.addActiveIngredient(medicationActiveDrug.getMedicationActiveDrugName().getName(),
                    medicationActiveDrug.getMedicationMeasurementUnit().getName(),
                    medicationActiveDrug.getValue(),
                    medicationActiveDrug.isDenominator()
            );
            fullActiveDrugName = fullActiveDrugName.concat(medicationActiveDrug.getValue() + medicationActiveDrug.getMedicationMeasurementUnit().getName() + " " + medicationActiveDrug.getMedicationActiveDrugName().getName());
        }

        medicationItem.setFullName(medicationItem.getName().concat(" " + fullActiveDrugName));

        return medicationItem;
    }

    /**
     * Generate and provide an instance of MissionItem.
     *
     * @param missionTeam the mission team data item, not null
     * @return a new MissionItem or null if processing fails
     */
    public static MissionItem createMissionItem(IMissionTeam missionTeam) {

        if (missionTeam == null) {

            return null;
        }

        MissionItem missionItem = new MissionItem();

        missionItem.setTeamName(missionTeam.getName());
        missionItem.setTeamLocation(missionTeam.getLocation());
        missionItem.setTeamDescription(missionTeam.getDescription());

        for (IMissionTrip mt : missionTeam.getMissionTrips()) {

            missionItem.addMissionTrip(mt.getId(),
                    mt.getMissionCity().getName(),
                    mt.getMissionCity().getMissionCountry().getName(),
                    mt.getStartDate(),
                    dateUtils.getFriendlyDate(mt.getStartDate()),
                    mt.getEndDate(),
                    dateUtils.getFriendlyDate(mt.getEndDate()),
                    mt.isCurrent());
        }

        return missionItem;
    }

    /**
     * Generate and provide an instance of PatientItem. See parameter descriptions for which fields must be filled out.
     *
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
     * @return a new PatientItem or null if processing fails, may be null
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
     * Generate and provide an instance of PatientEncounterItem
     *
     * @param patientEncounter patient encounter info, not null
     * @return a new PatientEncounterItem or null if processing fails
     */
    public static PatientEncounterItem createPatientEncounterItem(IPatientEncounter patientEncounter) {

        if (patientEncounter == null || patientEncounter.getPatient() == null) {

            return null;
        }

        PatientEncounterItem patientEncounterItem = new PatientEncounterItem();

        for (IChiefComplaint cc : patientEncounter.getChiefComplaints()) {

            patientEncounterItem.addChiefComplaint(cc.getValue());
        }
        patientEncounterItem.setId(patientEncounter.getId());
        patientEncounterItem.setPatientId(patientEncounter.getPatient().getId());
        patientEncounterItem.setWeeksPregnant(patientEncounter.getWeeksPregnant());
        patientEncounterItem.setTriageDateOfVisit(dateUtils.getFriendlyDate(patientEncounter.getDateOfTriageVisit()));
        if (patientEncounter.getDateOfMedicalVisit() != null)
            patientEncounterItem.setMedicalDateOfVisit(dateUtils.getFriendlyDate(patientEncounter.getDateOfMedicalVisit()));
        if (patientEncounter.getDateOfPharmacyVisit() != null)
            patientEncounterItem.setPharmacyDateOfVisit(dateUtils.getFriendlyDate(patientEncounter.getDateOfPharmacyVisit()));
        patientEncounterItem.setIsClosed(LogicDoer.isEncounterClosed(patientEncounter));
        patientEncounterItem.setNurseEmailAddress(patientEncounter.getNurse().getEmail());
        if (patientEncounter.getDoctor() != null)
            patientEncounterItem.setPhysicianEmailAddress(patientEncounter.getDoctor().getEmail());
        if (patientEncounter.getPharmacist() != null)
            patientEncounterItem.setPharmacistEmailAddress(patientEncounter.getPharmacist().getEmail());

        return patientEncounterItem;
    }

    /**
     * Generate and provide an instance of PhotoItem
     *
     * @param id              id of the photo, not null
     * @param description     description of the photo, may be null
     * @param insertTimeStamp photo timestamp, not null
     * @param imageURL        url to the image, not null
     * @return a new PhotoItem or null if processing fails
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
     * Generate and provide an instance of PrescriptionItem
     *
     * @param id            id of the prescription, not null
     * @param name          name of the prescription, not null
     * @param replacementId id of the prescription that replaced this prescription, may be null
     * @param firstName     first name of the person that prescribed the medication, may be null
     * @param lastName      last name of the person that prescribed the medication, may be null
     * @return a new PrescriptionItem or null if processing fails
     */
    public static PrescriptionItem createPrescriptionItem(int id, String name, Integer replacementId, String firstName, String lastName) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        PrescriptionItem prescriptionItem = new PrescriptionItem();

        prescriptionItem.setId(id);
        prescriptionItem.setName(name);
        if (replacementId != null)
            prescriptionItem.setReplacementId(replacementId);
        if (StringUtils.isNotNullOrWhiteSpace(firstName))
            prescriptionItem.setPrescriberFirstName(firstName);
        if (StringUtils.isNotNullOrWhiteSpace(lastName))
            prescriptionItem.setPrescriberLastName(lastName);

        return prescriptionItem;
    }

    /**
     * Generate and provide an instance of ProblemItem.
     *
     * @param name the name of the problem, not null
     * @return a new ProblemItem or null if processing fails
     */
    public static ProblemItem createProblemItem(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        ProblemItem problemItem = new ProblemItem();

        problemItem.setName(name);

        return problemItem;
    }
    public static AssessmentItem assessmentProblemItem(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        AssessmentItem assessmentItem = new AssessmentItem();

        assessmentItem.getFieldName();
        assessmentItem.getFieldValue();

        return assessmentItem;
    }
    /**
     * Generate and provide an instance of ResearchFilterItem.
     *
     * @param filterViewModel a viewmodel, not null
     * @return ResearchFilterItem or null if processing fails
     */
    public static ResearchFilterItem createResearchFilterItem(FilterViewModel filterViewModel) {

        if (filterViewModel == null) {

            return null;
        }

        ResearchFilterItem filterItem = new ResearchFilterItem();

        filterItem.setPrimaryDataset(filterViewModel.getPrimaryDataset());
        filterItem.setSecondaryDataset(filterViewModel.getSecondaryDataset());
        filterItem.setGraphType(filterViewModel.getGraphType());
        filterItem.setStartDate(filterViewModel.getStartDate());
        filterItem.setEndDate(filterViewModel.getEndDate());

        Integer groupFactor = filterViewModel.getGroupFactor();
        filterItem.setGroupFactor(groupFactor);
        if (groupFactor != null && groupFactor > 0) {

            filterItem.setGroupPrimary(filterViewModel.isGroupPrimary());
        } else {

            filterItem.setGroupPrimary(false);
        }

        filterItem.setFilterRangeStart(filterViewModel.getFilterRangeStart());
        filterItem.setFilterRangeEnd(filterViewModel.getFilterRangeEnd());
        filterItem.setMedicationName(filterViewModel.getMedicationName());

        return filterItem;
    }

    /**
     * Generate and provide an instance of SettingItem.
     *
     * @param systemSettings a list of all system settings, not null
     * @return a new SettingItem or null if processing fails
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
     * Generate and provide an instance of TabItem.
     *
     * @param name            name of the tab, not null
     * @param isCustom        was the tab custom made, not null
     * @param leftColumnSize  size of the left column, not null
     * @param rightColumnSize size of the right column, not null
     * @return a new TabItem or null if processing fails
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
     * Generate and provide an instance of TabFieldItem.
     *
     * @param name           the name of the field, not null
     * @param type           the fields type e.g. number, text, may be null
     * @param size           the size of the field e.g. small, med, large, may be null
     * @param order          sorting order for the field, may be null
     * @param placeholder    placeholder text for the field, may be null
     * @param value          current value of the field, may be null
     * @param chiefComplaint what chief complaint the field belongs to,, may be null
     * @param isCustom       identifies if the tabfielditem is custom made, not null
     * @return a new TabFieldItem or null if processing fails
     */
    public static TabFieldItem createTabFieldItem(String name,
                                                  String type,
                                                  String size,
                                                  Integer order,
                                                  String placeholder,
                                                  String value,
                                                  String chiefComplaint,
                                                  boolean isCustom) {

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
        tabFieldItem.setIsCustom(isCustom);

        return tabFieldItem;
    }

    /**
     * Generate and provide an instance of TeamItem.
     *
     * @param name        name of the team, not null
     * @param location    where the team is based out of, may be null
     * @param description a description of the team, may be null
     * @return a new team item or null if processing fails
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
     * Generate and provide an instance of TripItem.
     *
     * @param teamName    name of the team, not null
     * @param tripCity    city of the trip, not null
     * @param tripCountry country of the trip, not null
     * @param startDate   when the trip starts, not null
     * @param endDate     when the trip ends, not null
     * @return a new trip item or null if processing fails
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
     * Generate and provide an instance of UserItem.
     *
     * @param user DAO user, not null
     * @return new userItem or null if processing fails
     */
    public static UserItem createUserItem(IUser user) {

        if (user == null) {

            return null;
        }

        UserItem userItem = new UserItem();

        userItem.setId(user.getId());
        userItem.setEmail(user.getEmail());
        userItem.setFirstName(user.getFirstName());
        userItem.setLastName(user.getLastName());
        userItem.setLastLoginDate(dateUtils.getFriendlyDate(user.getLastLogin()));
        for (IRole role : user.getRoles()) {
            if (role != null && StringUtils.isNotNullOrWhiteSpace(role.getName())) {
                userItem.addRole(role.getName());
            }
        }
        userItem.setNotes(user.getNotes());
        userItem.setDeleted(user.getDeleted());
        userItem.setPasswordReset(user.getPasswordReset());

        return userItem;
    }

    /**
     * Generate and provide an instance of VitalItem.
     *
     * @param name  name of the vital, not null
     * @param value value of the vital, may be null
     * @return a new VitalItem or null if processing fails
     */
    public static VitalItem createVitalItem(String name, Float value) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        VitalItem vitalItem = new VitalItem();

        vitalItem.setName(name);
        if (value != null)
            vitalItem.setValue(value);

        return vitalItem;
    }
}
