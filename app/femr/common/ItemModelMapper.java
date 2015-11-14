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
import femr.data.models.mysql.PatientPrescription;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Responsible for creating item objects (common/models)
 * Only visible to ui & service layer.
 */
public class ItemModelMapper implements IItemModelMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public CityItem createCityItem(String cityName, String countryName) {

        if (StringUtils.isNullOrWhiteSpace(cityName) || StringUtils.isNullOrWhiteSpace(countryName)) {

            return null;
        }

        CityItem cityItem = new CityItem();

        cityItem.setCityName(cityName);
        cityItem.setCountryName(countryName);

        return cityItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MedicationItem createMedicationItem(IMedication medication, Integer quantityCurrent, Integer quantityTotal) {

        if (medication == null) {

            return null;
        }

        MedicationItem medicationItem = new MedicationItem();

        medicationItem.setId(medication.getId());
        medicationItem.setName(medication.getName());
        medicationItem.setQuantity_current(quantityCurrent);
        medicationItem.setQuantity_total(quantityTotal);
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
     * {@inheritDoc}
     */
    @Override
    public MissionItem createMissionItem(IMissionTeam missionTeam, List<MissionTripItem> missionTripItems) {

        if (missionTeam == null) {

            return null;
        }

        MissionItem missionItem = new MissionItem();

        missionItem.setTeamName(missionTeam.getName());
        missionItem.setTeamLocation(missionTeam.getLocation());
        missionItem.setTeamDescription(missionTeam.getDescription());
        missionItem.setMissionTrips(missionTripItems);

        return missionItem;
    }

    @Override
    public MissionTripItem createMissionTripItem(IMissionTrip missionTrip){

        if (missionTrip == null){

            return null;
        }

        MissionTripItem missionTripItem = new MissionTripItem();
        missionTripItem.setId(missionTrip.getId());
        if (missionTrip.getMissionCity() != null)
            missionTripItem.setTripCity(missionTrip.getMissionCity().getName());
        if (missionTrip.getMissionCity() != null)
            missionTripItem.setTripCountry(missionTrip.getMissionCity().getMissionCountry().getName());
        missionTripItem.setTripStartDate(missionTrip.getStartDate());
        missionTripItem.setFriendlyTripStartDate(dateUtils.getFriendlyDate(missionTrip.getStartDate()));
        missionTripItem.setTripEndDate(missionTrip.getEndDate());
        missionTripItem.setFriendlyTripEndDate(dateUtils.getFriendlyDate(missionTrip.getEndDate()));
        missionTripItem.setTeamName(missionTrip.getMissionTeam().getName());

        return missionTripItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientItem createPatientItem(int id,
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
                                                Integer photoId){

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
        else
            patientItem.setHeightFeet(0);

        if (heightInches != null)
            patientItem.setHeightInches(heightInches);
        else
            patientItem.setHeightInches(0);

        if (weight != null)
            patientItem.setWeight(weight);

        return patientItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientEncounterItem createPatientEncounterItem(IPatientEncounter patientEncounter) {

        if (patientEncounter == null || patientEncounter.getPatient() == null) {

            return null;
        }

        PatientEncounterItem patientEncounterItem = new PatientEncounterItem();

        for (IChiefComplaint cc : patientEncounter.getChiefComplaints()) {

            patientEncounterItem.addChiefComplaint(cc.getValue());
        }
        patientEncounterItem.setId(patientEncounter.getId());
        patientEncounterItem.setPatientId(patientEncounter.getPatient().getId());
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
        patientEncounterItem.setNurseFullName(patientEncounter.getNurse().getFirstName() + " " + patientEncounter.getNurse().getLastName()); // Andrew Change

        if (patientEncounter.getDoctor() != null) {
            patientEncounterItem.setPhysicianFullName(patientEncounter.getDoctor().getFirstName() + " " + patientEncounter.getDoctor().getLastName()); // Andrew Change
        }
        if (patientEncounter.getPharmacist() != null) {
            patientEncounterItem.setPharmacistFullName(patientEncounter.getPharmacist().getFirstName() + " " + patientEncounter.getPharmacist().getLastName()); // Andrew Change
        }
        return patientEncounterItem;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhotoItem createPhotoItem(int id, String description, Date insertTimeStamp, String imageURL) {

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
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItem createPrescriptionItem(int id, String name, String originalMedicationName, String firstName, String lastName,
                                                   IMedicationAdministration medicationAdministration, Integer amount, IMedication medication) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        PrescriptionItem prescriptionItem = new PrescriptionItem();

        prescriptionItem.setId(id);
        prescriptionItem.setName(name);
        if (originalMedicationName != null)
            prescriptionItem.setOriginalMedicationName(originalMedicationName);
        if (StringUtils.isNotNullOrWhiteSpace(firstName))
            prescriptionItem.setPrescriberFirstName(firstName);
        if (StringUtils.isNotNullOrWhiteSpace(lastName))
            prescriptionItem.setPrescriberLastName(lastName);

        if (medicationAdministration != null) {
            prescriptionItem.setAdministrationId(medicationAdministration.getId());
            prescriptionItem.setAdministrationName(medicationAdministration.getName());
            prescriptionItem.setAdministrationModifier(medicationAdministration.getDailyModifier());
        }
        if (amount != null)
            prescriptionItem.setAmount(amount);

        if (medication != null) {
            MedicationItem medicationItem = createMedicationItem(medication, null, null);
            prescriptionItem.setMedicationID(medicationItem.getId());

            if (medicationItem.getForm() != null)
                prescriptionItem.setMedicationForm(medicationItem.getForm());

            prescriptionItem.setMedicationRemaining(medicationItem.getQuantity_current());



            if (medicationItem.getActiveIngredients() != null)
                prescriptionItem.setMedicationActiveDrugs(medicationItem.getActiveIngredients());
        }
        return prescriptionItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProblemItem createProblemItem(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        ProblemItem problemItem = new ProblemItem();

        problemItem.setName(name);

        return problemItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SettingItem createSettingItem(List<? extends ISystemSetting> systemSettings) {

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
                    case "Metric System Option": //Alaa Serhan - set the metric system option
                        settingItem.setMetric(ss.isActive());
                        break;
                }
            }
        }

        return settingItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TabItem createTabItem(String name, boolean isCustom, Integer leftColumnSize, Integer rightColumnSize) {

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
     * {@inheritDoc}
     */
    @Override
    public TabFieldItem createTabFieldItem(String name,
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
     * {@inheritDoc}
     */
    @Override
    public TabFieldItem createTabFieldItem(String name,
                                                  String type,
                                                  String size,
                                                  Integer order,
                                                  String placeholder,
                                                  String value,
                                                  String chiefComplaint,
                                                  boolean isCustom,
                                                  String userName) {
        TabFieldItem temp = createTabFieldItem(name, type, size, order, placeholder, value, chiefComplaint, isCustom);
        if (StringUtils.isNotNullOrWhiteSpace(userName))
            temp.setUserName(userName);
        return temp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TeamItem createTeamItem(String name, String location, String description) {

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
     * {@inheritDoc}
     */
    @Override
    public TripItem createTripItem(String teamName, String tripCity, String tripCountry, Date startDate, Date endDate) {

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
     * {@inheritDoc}
     */
    @Override
    public UserItem createUserItem(IUser user) {

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
        userItem.setPasswordCreatedDate(dateUtils.getFriendlyDate(user.getPasswordCreatedDate()));
        userItem.setUserCreated(user.getUserCreated()); //Sam Zanni
        userItem.setDateCreated(dateUtils.getFriendlyDate(user.getDateCreated())); //Sam Zanni


        return userItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VitalItem createVitalItem(String name, Float value) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        VitalItem vitalItem = new VitalItem();

        vitalItem.setName(name);
        if (value != null)
            vitalItem.setValue(value);

        return vitalItem;
    }

    /**
     * {@inheritDoc}
     */
    public MedicationAdministrationItem createMedicationAdministrationItem(IMedicationAdministration medicationAdministration) {

        if (medicationAdministration == null)
            return null;

        MedicationAdministrationItem medicationAdministrationItem = new MedicationAdministrationItem(
                medicationAdministration.getId(),
                medicationAdministration.getName(),
                medicationAdministration.getDailyModifier()
        );

        return medicationAdministrationItem;
    }
}
