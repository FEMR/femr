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
import femr.data.models.mysql.MedicationInventory;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Responsible for creating item objects (common/models)
 * Only visible to ui & service layer.
 */
public class ItemModelMapper {

    private final MyIItemModelMapper iItemModelMapper = new MyIItemModelMapper();


    private class MyIItemModelMapper implements IItemModelMapper {
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
        public MedicationItem createMedicationItem(IMedication medication, Integer quantityCurrent, Integer quantityTotal, DateTime isDeleted) {

            if (medication == null) {

                return null;
            }

            MedicationItem medicationItem = new MedicationItem();

            medicationItem.setId(medication.getId());
            medicationItem.setName(medication.getName());
            medicationItem.setQuantityCurrent(quantityCurrent);
            medicationItem.setQuantityTotal(quantityTotal);
            if (medication.getConceptMedicationForm() != null) {
                medicationItem.setForm(medication.getConceptMedicationForm().getName());
            }

            DecimalFormat df = new DecimalFormat("######.####");
            int count = 0;
            String fullActiveDrugName = "";
            for (IMedicationGenericStrength medicationGenericStrength : medication.getMedicationGenericStrengths()) {

                medicationItem.addActiveIngredient(medicationGenericStrength.getMedicationGeneric().getName(),
                        medicationGenericStrength.getConceptMedicationUnit().getName(),
                        medicationGenericStrength.getValue(),
                        medicationGenericStrength.isDenominator()
                );
                if (count == 0){
                    fullActiveDrugName = fullActiveDrugName.concat(df.format(medicationGenericStrength.getValue()) + " " + medicationGenericStrength.getConceptMedicationUnit().getName() + " " + medicationGenericStrength.getMedicationGeneric().getName());
                }else{
                    fullActiveDrugName = fullActiveDrugName.concat(" / " + df.format(medicationGenericStrength.getValue()) + " " + medicationGenericStrength.getConceptMedicationUnit().getName() + " " + medicationGenericStrength.getMedicationGeneric().getName());
                }

                count++;
            }

            medicationItem.setFullName(medicationItem.getName().concat(" " + fullActiveDrugName));
            if (StringUtils.isNotNullOrWhiteSpace(medicationItem.getForm()))
                medicationItem.setFullName(medicationItem.getFullName().concat(" " + "(" + medicationItem.getForm() + ")"));

            //Check to see if medication is deleted.
            if(isDeleted != null)
                medicationItem.setIsDeleted(isDeleted);

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

            missionTripItem.setFriendlyTripTitle(
                    StringUtils.generateMissionTripTitle(missionTripItem.getTeamName(), missionTripItem.getTripCountry(), missionTripItem.getTripStartDate(), missionTripItem.getTripEndDate())
            );

            return missionTripItem;
        }

        /**
         * {@inheritDoc}
         * @param inputPatientItem
         */
        @Override
        public PatientItem createPatientItem(InputPatientItem inputPatientItem) {

            if (StringUtils.isNullOrWhiteSpace(inputPatientItem.getFirstName()) ||
                    StringUtils.isNullOrWhiteSpace(inputPatientItem.getLastName()) ||
                    StringUtils.isNullOrWhiteSpace(inputPatientItem.getCity())) {

                return null;
            }

            PatientItem patientItem = new PatientItem();

            //required fields
            patientItem.setId(inputPatientItem.getId());
            patientItem.setFirstName(inputPatientItem.getFirstName());
            patientItem.setLastName(inputPatientItem.getLastName());
            patientItem.setYearsOld(dateUtils.getYearsInteger(inputPatientItem.getAge()));
            patientItem.setMonthsOld(dateUtils.getMonthsInteger(inputPatientItem.getAge()));
            patientItem.setCity(inputPatientItem.getCity());
            patientItem.setUserId(inputPatientItem.getUserId());
            patientItem.setIsBirthDateCorrect(inputPatientItem.getIsBirthDateCorrect());
            //optional fields
            if (StringUtils.isNotNullOrWhiteSpace(inputPatientItem.getAddress()))
                patientItem.setAddress(inputPatientItem.getAddress());
            if (StringUtils.isNotNullOrWhiteSpace(inputPatientItem.getSex()))
                patientItem.setSex(inputPatientItem.getSex());
            if (inputPatientItem.getAge() != null) {

                patientItem.setAge(dateUtils.getAge(inputPatientItem.getAge()));//age (int)
                patientItem.setBirth(inputPatientItem.getAge());//date of birth(date)
                patientItem.setFriendlyDateOfBirth(dateUtils.getFriendlyDate(inputPatientItem.getAge()));

            }
            if (StringUtils.isNotNullOrWhiteSpace(inputPatientItem.getPathToPatientPhoto()) && inputPatientItem.getPhotoId() != null) {

                patientItem.setPathToPhoto(inputPatientItem.getPathToPatientPhoto());
                patientItem.setPhotoId(inputPatientItem.getPhotoId());
            }
            if (inputPatientItem.getWeeksPregnant() != null)
                patientItem.setWeeksPregnant(inputPatientItem.getWeeksPregnant());

            if (inputPatientItem.getHeightFeet() != null)
                patientItem.setHeightFeet(inputPatientItem.getHeightFeet());
            else
                patientItem.setHeightFeet(0);

            if (inputPatientItem.getHeightInches() != null)
                patientItem.setHeightInches(inputPatientItem.getHeightInches());
            else
                patientItem.setHeightInches(0);

            if (inputPatientItem.getWeight() != null)
                patientItem.setWeight(inputPatientItem.getWeight());

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

            if( patientEncounter.getMissionTrip() != null ) {
                patientEncounterItem.setMissionTripId(patientEncounter.getMissionTrip().getId());
            }

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
            //checks if the patient has been screened for diabetes during this encounter
            if (patientEncounter.getDateOfDiabeteseScreen() != null)
                patientEncounterItem.setScreenedForDiabetes(true);
            else
                patientEncounterItem.setScreenedForDiabetes(false);

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
                                                       IConceptPrescriptionAdministration medicationAdministration, Integer amount, IMedication medication,
                                                       MedicationInventory medicationInventory, Boolean isCounseled) {


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
                prescriptionItem.setAdministrationID(medicationAdministration.getId());
                prescriptionItem.setAdministrationName(medicationAdministration.getName());
                prescriptionItem.setAdministrationModifier(medicationAdministration.getDailyModifier());
            }
            prescriptionItem.setAmount(amount);

            if (isCounseled != null)
                prescriptionItem.setCounseled(isCounseled);

            if (medication != null) {

                MedicationItem medicationItem;
                if( medicationInventory != null ){

                    medicationItem = iItemModelMapper.createMedicationItem(medication, medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), null);
                    prescriptionItem.setMedicationRemaining( medicationInventory.getQuantityCurrent() );
                }
                else{
                    medicationItem = iItemModelMapper.createMedicationItem(medication, null, null, null);
                }

                prescriptionItem.setMedicationID(medicationItem.getId());

                if (medicationItem.getForm() != null)
                    prescriptionItem.setMedicationForm(medicationItem.getForm());

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
                        case "Diabetes Prompt":
                            settingItem.setIsDiabetesPrompt(ss.isActive());
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
         * @param inputTabFieldItem
         */
        @Override
        public TabFieldItem createTabFieldItem(InputTabFieldItem inputTabFieldItem) {

            if (StringUtils.isNullOrWhiteSpace(inputTabFieldItem.getName())) {

                return null;
            }

            TabFieldItem tabFieldItem = new TabFieldItem();

            tabFieldItem.setName(inputTabFieldItem.getName());
            if (StringUtils.isNotNullOrWhiteSpace(inputTabFieldItem.getPlaceholder()))
                tabFieldItem.setPlaceholder(inputTabFieldItem.getPlaceholder());
            if (inputTabFieldItem.getOrder() != null)
                tabFieldItem.setOrder(inputTabFieldItem.getOrder());
            if (StringUtils.isNotNullOrWhiteSpace(inputTabFieldItem.getSize()))
                tabFieldItem.setSize(inputTabFieldItem.getSize());
            if (StringUtils.isNotNullOrWhiteSpace(inputTabFieldItem.getType()))
                tabFieldItem.setType(inputTabFieldItem.getType());
            if (StringUtils.isNotNullOrWhiteSpace(inputTabFieldItem.getValue()))
                tabFieldItem.setValue(inputTabFieldItem.getValue());
            if (StringUtils.isNotNullOrWhiteSpace(inputTabFieldItem.getChiefComplaint()))
                tabFieldItem.setChiefComplaint(inputTabFieldItem.getChiefComplaint());
            tabFieldItem.setIsCustom(inputTabFieldItem.isCustom());

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
            TabFieldItem temp = iItemModelMapper.createTabFieldItem(new InputTabFieldItem(name, type, size, order, placeholder, value, chiefComplaint, isCustom));
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
            userItem.setCreatedBy(user.getCreatedBy()); //Sam Zanni
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
        public MedicationAdministrationItem createMedicationAdministrationItem(IConceptPrescriptionAdministration conceptPrescriptionAdministration) {

            if (conceptPrescriptionAdministration == null)
                return null;

            MedicationAdministrationItem medicationAdministrationItem = new MedicationAdministrationItem(
                    conceptPrescriptionAdministration.getId(),
                    conceptPrescriptionAdministration.getName(),
                    conceptPrescriptionAdministration.getDailyModifier()
            );

            return medicationAdministrationItem;
        }
    }
}
