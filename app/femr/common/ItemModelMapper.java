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
    public MedicationItem createMedicationItem(IMedication medication, Integer quantityCurrent, Integer quantityTotal, DateTime isDeleted, String timeAdded, String createdBy) {

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

        medicationItem.setTimeAdded(timeAdded);
        medicationItem.setCreatedBy(createdBy);

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
     */
    @Override
    public PatientItem createPatientItem(int id,
                                                String firstName,
                                                String lastName,
                                                String phoneNumber,
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
                                                Integer photoId,
                                                String ageClassification,
                                                Integer smoker,
                                                Integer diabetic,
                                                Integer alcohol,
                                                Integer cholesterol,
                                                Integer hypertension) {
        // Osman above
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
        patientItem.setYearsOld(dateUtils.getYearsInteger(age));
        patientItem.setMonthsOld(dateUtils.getMonthsInteger(age));
        patientItem.setCity(city);
        patientItem.setUserId(userId);
        //optional fields
        if (StringUtils.isNotNullOrWhiteSpace(phoneNumber))
            patientItem.setPhoneNumber(phoneNumber);
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

        if (smoker != null)
            patientItem.setSmoker(smoker);

        if (diabetic != null)
            patientItem.setDiabetic(diabetic);

        if (alcohol != null)
            patientItem.setAlcohol(alcohol);

        if (cholesterol != null)
            patientItem.setCholesterol(cholesterol);

        if (hypertension != null)
            patientItem.setHypertension(hypertension);

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

        //needs special checks for age classification/photo
        patientEncounterItem.setPatientItem(createPatientItem(patientEncounter.getPatient().getId(),
                patientEncounter.getPatient().getFirstName(),
                patientEncounter.getPatient().getLastName(),
                patientEncounter.getPatient().getPhoneNumber(),
                patientEncounter.getPatient().getCity(),
                patientEncounter.getPatient().getAddress(),
                patientEncounter.getPatient().getUserId(),
                patientEncounter.getPatient().getAge(),
                patientEncounter.getPatient().getSex(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));
        // Osman above

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
        patientEncounterItem.setTurnAroundTime(dateUtils.getTurnAroundTime(patientEncounterItem));
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
    public PrescriptionItem createPrescriptionItem(int id, String name, String firstName, String lastName,
                                                   IConceptPrescriptionAdministration medicationAdministration,
                                                   Integer amount, Boolean isCounseled, MedicationItem medicationItem) {


        PrescriptionItem prescriptionItem = new PrescriptionItem();

        prescriptionItem.setId(id);
        prescriptionItem.setName(name);
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

        if (medicationItem != null) {
            if (medicationItem.getQuantityCurrent() == null)
                prescriptionItem.setFormularyMessage("Medication is not found in the formulary");
            else if (amount != null && amount > medicationItem.getQuantityCurrent())
                prescriptionItem.setFormularyMessage("Not Enough Medication Remaining to Dispense!");
        } else {
            prescriptionItem.setFormularyMessage("Medication is not found in the formulary");
        }

        prescriptionItem.setMedicationItem(medicationItem);
        prescriptionItem.setMedicationID(medicationItem.getId());//redundant, but keeping in for now to debug
        return prescriptionItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionItem createPrescriptionItemWithReplacement(int id, String name, String replacementMedicationName, int replacementId, String firstName, String lastName,
                                                                  IConceptPrescriptionAdministration conceptPrescriptionAdministration, Integer amount,
                                                                  Boolean isCounseled, MedicationItem medicationItem)  {

        PrescriptionItem prescriptionItem = createPrescriptionItem(id, name, firstName, lastName, conceptPrescriptionAdministration, amount, isCounseled, medicationItem);
        if (replacementMedicationName != null)
            prescriptionItem.setReplacementMedicationName(replacementMedicationName);
        prescriptionItem.setReplacementId(replacementId);
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
    public NoteItem createNoteItem(String name, DateTime datetimestamp, String reporter) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        NoteItem noteItem = new NoteItem();

        noteItem.setName(name);
        noteItem.setDate(datetimestamp);
        noteItem.setReporter(reporter);

        return noteItem;
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
