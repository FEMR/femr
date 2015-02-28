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
package femr.data;

import com.avaje.ebean.Ebean;
import com.google.inject.Inject;
import femr.common.models.*;
import femr.data.models.core.*;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import javax.inject.Provider;
import java.util.Date;
import java.util.List;

/**
 * Responsible for creating model objects (data/models).
 * Only visible to data & service layer.
 */
public class DataModelMapper {

    private final Provider<IChiefComplaint> chiefComplaintProvider;
    private final Provider<IMedication> medicationProvider;
    private final Provider<IMedicationActiveDrugName> medicationActiveDrugNameProvider;
    private final Provider<IMedicationActiveDrug> medicationActiveDrugProvider;
    private final Provider<IMedicationMeasurementUnit> medicationMeasurementUnitProvider;
    private final Provider<IMedicationForm> medicationFormProvider;
    private final Provider<IMissionCity> missionCityProvider;
    private final Provider<IMissionCountry> missionCountryProvider;
    private final Provider<IMissionTeam> missionTeamProvider;
    private final Provider<IMissionTrip> missionTripProvider;
    private final Provider<IPatient> patientProvider;
    private final Provider<IPatientAgeClassification> patientAgeClassificationProvider;
    private final Provider<IPatientEncounterPhoto> patientEncounterPhotoProvider;
    private final Provider<IPatientEncounter> patientEncounterProvider;
    private final Provider<IPatientEncounterTabField> patientEncounterTabFieldProvider;
    private final Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private final Provider<IPatientPrescription> patientPrescriptionProvider;
    private final Provider<IPhoto> photoProvider;
    private final Provider<IRole> roleProvider;
    private final Provider<ITabField> tabFieldProvider;
    private final Provider<ITabFieldSize> tabFieldSizeProvider;
    private final Provider<ITabFieldType> tabFieldTypeProvider;
    private final Provider<ITab> tabProvider;
    private final Provider<IVital> vitalProvider;
    private final Provider<IUser> userProvider;

    @Inject
    public DataModelMapper(Provider<IChiefComplaint> chiefComplaintProvider,
                           Provider<IMedication> medicationProvider,
                           Provider<IMedicationActiveDrugName> medicationActiveDrugNameProvider,
                           Provider<IMedicationForm> medicationFormProvider,
                           Provider<IMedicationActiveDrug> medicationActiveDrugProvider,
                           Provider<IMedicationMeasurementUnit> medicationMeasurementUnitProvider,
                           Provider<IMissionCity> missionCityProvider,
                           Provider<IMissionCountry> missionCountryProvider,
                           Provider<IMissionTeam> missionTeamProvider,
                           Provider<IMissionTrip> missionTripProvider,
                           Provider<IPatient> patientProvider,
                           Provider<IPatientAgeClassification> patientAgeClassificationProvider,
                           Provider<IPatientEncounterPhoto> patientEncounterPhotoProvider,
                           Provider<IPatientEncounter> patientEncounterProvider,
                           Provider<IPatientEncounterTabField> patientEncounterTabFieldProvider,
                           Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                           Provider<IPatientPrescription> patientPrescriptionProvider,
                           Provider<IPhoto> photoProvider,
                           Provider<IRole> roleProvider,
                           Provider<ITabField> tabFieldProvider,
                           Provider<ITabFieldSize> tabFieldSizeProvider,
                           Provider<ITabFieldType> tabFieldTypeProvider,
                           Provider<ITab> tabProvider,
                           Provider<IUser> userProvider,
                           Provider<IVital> vitalProvider) {

        this.chiefComplaintProvider = chiefComplaintProvider;
        this.patientEncounterProvider = patientEncounterProvider;
        this.medicationProvider = medicationProvider;
        this.medicationActiveDrugNameProvider = medicationActiveDrugNameProvider;
        this.medicationFormProvider = medicationFormProvider;
        this.medicationActiveDrugProvider = medicationActiveDrugProvider;
        this.medicationMeasurementUnitProvider = medicationMeasurementUnitProvider;
        this.missionCityProvider = missionCityProvider;
        this.missionCountryProvider = missionCountryProvider;
        this.missionTeamProvider = missionTeamProvider;
        this.missionTripProvider = missionTripProvider;
        this.patientProvider = patientProvider;
        this.patientAgeClassificationProvider = patientAgeClassificationProvider;
        this.patientEncounterPhotoProvider = patientEncounterPhotoProvider;
        this.patientEncounterTabFieldProvider = patientEncounterTabFieldProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.patientPrescriptionProvider = patientPrescriptionProvider;
        this.photoProvider = photoProvider;
        this.roleProvider = roleProvider;
        this.tabFieldProvider = tabFieldProvider;
        this.tabFieldSizeProvider = tabFieldSizeProvider;
        this.tabFieldTypeProvider = tabFieldTypeProvider;
        this.tabProvider = tabProvider;
        this.userProvider = userProvider;
        this.vitalProvider = vitalProvider;
    }

    /**
     * Generate and provide an implementation of IChiefComplaint.
     *
     * @param value              the chief complaint itself, not null
     * @param patientEncounterId id of the encounter that the chief complaint belongs to, not null
     * @param sortOrder          the order in which the chief complaint is sorted(when dealing with >1 chief complaint), may be null
     * @return an implementation of IChiefComplaint or null if processing fails
     */
    public IChiefComplaint createChiefComplaint(String value, int patientEncounterId, Integer sortOrder) {

        if (StringUtils.isNullOrWhiteSpace(value)) {

            return null;
        }

        IChiefComplaint chiefComplaint = chiefComplaintProvider.get();
        chiefComplaint.setValue(value);
        chiefComplaint.setPatientEncounter(Ebean.getReference(patientEncounterProvider.get().getClass(), patientEncounterId));
        chiefComplaint.setSortOrder(sortOrder);

        return chiefComplaint;
    }

    /**
     * Generate and provide an implementation of IMedication.
     *
     * @param name name of the medication, not null
     * @return an implementation of IMedication or null if processing fails
     */
    public IMedication createMedication(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        IMedication medication = medicationProvider.get();

        medication.setName(name);
        medication.setIsDeleted(false);

        return medication;
    }

    /**
     * Generate and provide an implementation of IMedication for use in adding to inventory.
     *
     * @param name                  name of the medication, not null
     * @param total                 the total quantity of the medication, not null
     * @param current               the current quantity of the medication, not null
     * @param medicationActiveDrugs active drugs in the medication, may be null
     * @param medicationForm        the medications form e.g. cream/chewable/pill, may be null
     * @return an implementation of IMedication or null if processing fails
     */
    public IMedication createMedication(String name, Integer total, Integer current, List<IMedicationActiveDrug> medicationActiveDrugs, IMedicationForm medicationForm) {

        if (StringUtils.isNullOrWhiteSpace(name) || total == null || current == null) {

            return null;
        }

        IMedication medication = medicationProvider.get();

        medication.setName(name);
        medication.setQuantity_total(total);
        medication.setQuantity_current(current);
        medication.setIsDeleted(false);
        medication.setMedicationActiveDrugs(medicationActiveDrugs);
        medication.setMedicationForm(medicationForm);

        return medication;
    }

    /**
     * Generate and provide an implementation of IMedicationActiveDrug.
     *
     * @param value                    strength of the drug, not null
     * @param isDenominator            is the drug a denominator, not null
     * @param activeDrugUnitId         id of the unit for measurement of the drug, not null
     * @param medicationActiveDrugName the drug name, may be null
     * @return an implementation of IMedicationActiveDrug
     */
    public IMedicationActiveDrug createMedicationActiveDrug(int value, boolean isDenominator, int activeDrugUnitId, IMedicationActiveDrugName medicationActiveDrugName) {

        IMedicationActiveDrug medicationActiveDrug = medicationActiveDrugProvider.get();

        medicationActiveDrug.setValue(value);
        medicationActiveDrug.setDenominator(isDenominator);
        medicationActiveDrug.setMedicationMeasurementUnit(Ebean.getReference(medicationMeasurementUnitProvider.get().getClass(), activeDrugUnitId));
        medicationActiveDrug.setMedicationActiveDrugName(medicationActiveDrugName);

        return medicationActiveDrug;
    }

    /**
     * Generate and provide an implementation of IMedicationActiveDrugName.
     *
     * @param name name of the drug, not null
     * @return an implementation of IMedicationActiveDrugName or null if processing fails
     */
    public IMedicationActiveDrugName createMedicationActiveDrugName(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        IMedicationActiveDrugName medicationActiveDrugName = medicationActiveDrugNameProvider.get();

        medicationActiveDrugName.setName(name);

        return medicationActiveDrugName;
    }

    /**
     * Generate and provide an implementation of IMedicationForm.
     *
     * @param name name of the form e.g. cream/chewable/pill, not null
     * @return an implementation of IMedicationForm or null if processing fails
     */
    public IMedicationForm createMedicationForm(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        IMedicationForm medicationForm = medicationFormProvider.get();

        medicationForm.setName(name);
        medicationForm.setIsDeleted(false);

        return medicationForm;
    }

    /**
     * Generate and provide an implementation of IMissionCity.
     *
     * @param name           name of the city, not null
     * @param missionCountry the country model, not null
     * @return an implementation of IMissionCity or null if processing fails
     */
    public IMissionCity createMissionCity(String name, IMissionCountry missionCountry) {

        if (StringUtils.isNullOrWhiteSpace(name) || missionCountry == null) {

            return null;
        }

        IMissionCity missionCity = missionCityProvider.get();

        missionCity.setMissionCountry(missionCountry);
        missionCity.setName(name);

        return missionCity;
    }

    /**
     * Generate and provide an implementation of IMissionTeam.
     *
     * @param name        the team name, not null
     * @param location    where the team is based out of, may be null
     * @param description a description of the team, may be null
     * @return an implementation of IMissionTeam or null if processing fails
     */
    public IMissionTeam createMissionTeam(String name, String location, String description) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        IMissionTeam missionTeam = missionTeamProvider.get();

        missionTeam.setName(name);
        missionTeam.setLocation(location);
        missionTeam.setDescription(description);

        return missionTeam;
    }

    /**
     * Generate and provide an implementation of IMissionTrip.
     *
     * @param startDate   start date of the trip, not null
     * @param endDate     end date of the trip, not null
     * @param isCurrent   is this the current trip?, not null
     * @param missionCity the city where the trip is taking place, not null
     * @param missionTeam the country where the trip is taking place, not null
     * @return an implementation of IMissionTrip or null if processing fails
     */
    public IMissionTrip createMissionTrip(Date startDate, Date endDate, boolean isCurrent, IMissionCity missionCity, IMissionTeam missionTeam) {

        if (startDate == null || endDate == null || missionCity == null || missionTeam == null) {

            return null;
        }

        IMissionTrip missionTrip = missionTripProvider.get();

        missionTrip.setCurrent(isCurrent);
        missionTrip.setStartDate(startDate);
        missionTrip.setEndDate(endDate);
        missionTrip.setMissionCity(missionCity);
        missionTrip.setMissionTeam(missionTeam);

        return missionTrip;
    }

    /**
     * Generate and provide an implementation of IPatient.
     *
     * @param userID    id of the user creating the patient, not null
     * @param firstName first name of the patient, not null
     * @param lastName  last name of the patient, not null
     * @param birthday  the patients birthday, may be null
     * @param sex       the sex of the patient, may be null
     * @param address   the address of the patients residence, may be null
     * @param city      the city of the patient, may be null
     * @param photoID   the id of a photo of the patient, may be null
     * @return an implementation of IPatient or null if processing fails
     */
    public IPatient createPatient(int userID, String firstName, String lastName, Date birthday, String sex, String address, String city, Integer photoID) {

        if (userID < 0 || StringUtils.isNullOrWhiteSpace(firstName) || StringUtils.isNullOrWhiteSpace(lastName)) {

            return null;
        }

        IPatient patient = patientProvider.get();

        patient.setUserId(userID);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        if (birthday != null)
            patient.setAge(birthday);
        patient.setSex(sex);
        patient.setAddress(address);
        patient.setCity(city);
        if (photoID != null)
            patient.setPhoto(Ebean.getReference(photoProvider.get().getClass(), photoID));

        return patient;
    }

    /**
     * Generate and provide an implementation of IPatientEncounter.
     *
     * @param patientID                  id of the patient, not null
     * @param date                       date of checking for triage, not null
     * @param weeksPregnant              weeks pregnant of the patient, may be null
     * @param userId                     id of the user creating the encounter, not null
     * @param patientAgeClassificationId id of the age classification, may be null
     * @param tripId                     id of the trip, may be null
     * @return an implementation of IPatientEncounter or null if processing fails
     */
    public IPatientEncounter createPatientEncounter(int patientID, DateTime date, Integer weeksPregnant, int userId, Integer patientAgeClassificationId, Integer tripId) {

        if (patientID < 1 || userId < 1 || date == null) {

            return null;
        }

        IPatientEncounter patientEncounter = patientEncounterProvider.get();

        patientEncounter.setDateOfTriageVisit(date);
        //provide a proxy patient for the encounter
        patientEncounter.setPatient(Ebean.getReference(patientProvider.get().getClass(), patientID));
        patientEncounter.setNurse(Ebean.getReference(userProvider.get().getClass(), userId));
        patientEncounter.setWeeksPregnant(weeksPregnant);
        if (patientAgeClassificationId != null)
            patientEncounter.setPatientAgeClassification(Ebean.getReference(patientAgeClassificationProvider.get().getClass(), patientAgeClassificationId));
        if (tripId != null)
            patientEncounter.setMissionTrip(Ebean.getReference(missionTripProvider.get().getClass(), tripId));

        return patientEncounter;
    }

    /**
     * Generate and provide an implementation of IPatientEncounterTabField
     *
     * @param tabFieldId       id of the field, not null
     * @param userId           id of the user creating the field, not null
     * @param value            value of the field, not null
     * @param encounterId      id of the encounter, not null
     * @param dateTaken        date the field was recorded, not null
     * @param chiefComplaintId id of the chief complaint, may be null
     * @return an implementation of IPatientEncounterTabfield or null if processing fails
     */
    public IPatientEncounterTabField createPatientEncounterTabField(int tabFieldId, int userId, String value, int encounterId, DateTime dateTaken, Integer chiefComplaintId) {

        if (tabFieldId < 1 || userId < 1 || StringUtils.isNullOrWhiteSpace(value) || encounterId < 1 || dateTaken == null) {

            return null;
        }

        IPatientEncounterTabField patientEncounterTabField = patientEncounterTabFieldProvider.get();

        patientEncounterTabField.setDateTaken(dateTaken);
        patientEncounterTabField.setUserId(userId);
        patientEncounterTabField.setPatientEncounterId(encounterId);
        patientEncounterTabField.setTabField(Ebean.getReference(tabFieldProvider.get().getClass(), tabFieldId));
        patientEncounterTabField.setTabFieldValue(value);
        if (chiefComplaintId != null)
            patientEncounterTabField.setChiefComplaint(Ebean.getReference(chiefComplaintProvider.get().getClass(), chiefComplaintId));

        return patientEncounterTabField;
    }

    /**
     * Generate and provide an implementation of IPatientEncounterVital
     *
     * @param encounterId id of the encounter, not null
     * @param userId      id of the user creating the vital value, not null
     * @param time        when the vital was recorded, not null
     * @param vitalID     id of the vital field, not null
     * @param value       value of the vital, not null
     * @return an implementation of IPatientEncounterVital or null if processing fails
     */
    public IPatientEncounterVital createPatientEncounterVital(int encounterId, int userId, String time, int vitalID, float value) {

        if (encounterId < 1 || userId < 1 || StringUtils.isNullOrWhiteSpace(time) || vitalID < 1) {

            return null;
        }

        IPatientEncounterVital patientEncounterVital = patientEncounterVitalProvider.get();

        patientEncounterVital.setPatientEncounterId(encounterId);
        patientEncounterVital.setUserId(userId);
        patientEncounterVital.setDateTaken(time);
        patientEncounterVital.setVital(Ebean.getReference(vitalProvider.get().getClass(), vitalID));
        patientEncounterVital.setVitalValue(value);

        return patientEncounterVital;
    }

    /**
     * Generate and provide an implementation of IPatientPrescription
     *
     * @param amount        amount of medication dispensed, not null
     * @param medication    the medication, not null
     * @param userId        id of the user creating the prescription, not null
     * @param encounterId   encounter id of the prescription, not null
     * @param replacementId id of the prescription being replaced, may be null
     * @param isDispensed   is the patient prescription dispensed to the patient yet, not null
     * @return an implementation of IPatientPrescription or null if processing fails, not null
     */
    public IPatientPrescription createPatientPrescription(int amount, IMedication medication, int userId, int encounterId, Integer replacementId, boolean isDispensed, boolean isCounseled) {

        if (medication == null || userId < 1 || encounterId < 1) {

            return null;
        }

        IPatientPrescription patientPrescription = patientPrescriptionProvider.get();

        patientPrescription.setAmount(amount);
        patientPrescription.setDateTaken(dateUtils.getCurrentDateTime());
        patientPrescription.setPatientEncounter(Ebean.getReference(patientEncounterProvider.get().getClass(), encounterId));
        patientPrescription.setMedication(medication);
        patientPrescription.setReplacementId(replacementId);
        patientPrescription.setPhysician(Ebean.getReference(userProvider.get().getClass(), userId));
        patientPrescription.setDispensed(isDispensed);
        patientPrescription.setCounseled(isCounseled);

        return patientPrescription;
    }

    /**
     * Generate and provide an implementation of IPhoto.
     *
     * @param description description of the photo, may be null
     * @param filePath    path to the file, not null
     * @return an implementation of IPhoto or null if processing fails
     */
    public IPhoto createPhoto(String description, String filePath) {

        if (StringUtils.isNullOrWhiteSpace(filePath)) {

            return null;
        }

        IPhoto photo = photoProvider.get();

        if (StringUtils.isNullOrWhiteSpace(description)) photo.setDescription("");
        else photo.setDescription(description);
        photo.setFilePath(filePath);

        return photo;
    }

    public IRole createRole(String name) {

        IRole role = roleProvider.get();

        role.setName(name);

        return role;
    }

    /**
     * Generate and provide an implementation of ITab.
     *
     * @param date      date of creation, not null
     * @param leftSize  left column size, not null
     * @param rightSize right column size, not null
     * @param name      name of the tab, not null
     * @param isDeleted is the tab deleted, not null
     * @param userId    id of the user creating the tab, not null
     * @return an implementation of ITab or null if processing fails
     */
    public ITab createTab(DateTime date, int leftSize, int rightSize, String name, boolean isDeleted, int userId) {

        if (date == null || StringUtils.isNullOrWhiteSpace(name) || userId < 1) {

            return null;
        }

        ITab tab = tabProvider.get();

        tab.setDateCreated(date);
        tab.setIsDeleted(isDeleted);
        tab.setLeftColumnSize(leftSize);
        tab.setRightColumnSize(rightSize);
        tab.setName(name);
        tab.setUserId(userId);
        //this will always be true if a tab is being programmatically created by a user
        tab.setIsCustom(true);

        return tab;
    }

    /**
     * Generate and provide an implementation of ITabField
     *
     * @param name           name of the field, not null
     * @param order          order of the field, may be null
     * @param placeholder    placeholder for the field, may be null
     * @param isDeleted      is the field deleted, not null
     * @param tabFieldSizeID id of {@link femr.data.models.core.ITabFieldSize}, not null
     * @param tabFieldTypeID id of {@link femr.data.models.core.ITabFieldType}, not null
     * @param tabID          id of {@link femr.data.models.core.ITab}, not null
     * @return an implementation of ITabField or null if processing fails
     */
    public ITabField createTabField(String name, Integer order, String placeholder, boolean isDeleted, int tabFieldSizeID, int tabFieldTypeID, int tabID) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        ITabField tabField = tabFieldProvider.get();

        tabField.setIsDeleted(isDeleted);
        tabField.setName(name);
        tabField.setOrder(order);
        tabField.setPlaceholder(placeholder);
        tabField.setTabFieldSize(Ebean.getReference(tabFieldSizeProvider.get().getClass(), tabFieldSizeID));
        tabField.setTabFieldType(Ebean.getReference(tabFieldTypeProvider.get().getClass(), tabFieldTypeID));
        tabField.setTab(Ebean.getReference(tabProvider.get().getClass(), tabID));

        return tabField;
    }

    /**
     * Generate and provide an implementation of IUser
     *
     * @param firstName       first name of the user, not null
     * @param lastName        last name of the user, may be null
     * @param email           email address of the user, not null
     * @param date            date of last login, not null
     * @param notes           notes about who the user is, may be null
     * @param password        password for the user, not null
     * @param isDeleted       is the user deleted, not null
     * @param isPasswordReset is the users password marked for a reset the next time they log in, not null
     * @param roles           a list of roles, must have at least one, not null
     * @return an implementation of IUser or null if processing fails
     */
    public IUser createUser(String firstName, String lastName, String email, DateTime date, String notes, String password, boolean isDeleted, boolean isPasswordReset, List<? extends IRole> roles) {

        if (StringUtils.isNullOrWhiteSpace(firstName) || StringUtils.isNullOrWhiteSpace(password) || StringUtils.isNullOrWhiteSpace(email) || date == null || roles == null || roles.size() < 1) {

            return null;
        }

        IUser user = userProvider.get();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setLastLogin(date);
        user.setDeleted(isDeleted);
        user.setPasswordReset(isPasswordReset);
        user.setNotes(notes);
        user.setRoles(roles);

        return user;
    }
}
