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
import femr.business.services.core.IEncounterService;
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
public class DataModelMapper implements IDataModelMapper{

    private final Provider<IChiefComplaint> chiefComplaintProvider;
    private final Provider<ILoginAttempt> loginAttemptProvider;
    private final Provider<IMedication> medicationProvider;
    private final Provider<IMedicationActiveDrugName> medicationActiveDrugNameProvider;
    private final Provider<IMedicationActiveDrug> medicationActiveDrugProvider;
    private final Provider<IMedicationMeasurementUnit> medicationMeasurementUnitProvider;
    private final Provider<IMedicationAdministration> medicationAdministrationProvider;
    private final Provider<IMedicationForm> medicationFormProvider;
    private final Provider<IMedicationInventory> medicationInventoryProvider;
    private final Provider<IMissionCity> missionCityProvider;
    private final Provider<IMissionCountry> missionCountryProvider;
    private final Provider<IMissionTeam> missionTeamProvider;
    private final Provider<IMissionTrip> missionTripProvider;
    private final Provider<IPatient> patientProvider;
    private final Provider<IPatientAgeClassification> patientAgeClassificationProvider;
    private final Provider<IPatientEncounterPhoto> patientEncounterPhotoProvider;
    private final Provider<IPatientEncounter> patientEncounterProvider;
    private final Provider<IEncounterService> patientEncounterTabField;
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
                           Provider<ILoginAttempt> loginAttemptProvider,
                           Provider<IMedication> medicationProvider,
                           Provider<IMedicationActiveDrugName> medicationActiveDrugNameProvider,
                           Provider<IMedicationForm> medicationFormProvider,
                           Provider<IMedicationActiveDrug> medicationActiveDrugProvider,
                           Provider<IMedicationMeasurementUnit> medicationMeasurementUnitProvider,
                           Provider<IMedicationAdministration> medicationAdministrationProvider,
                           Provider<IMedicationInventory> medicationInventoryProvider,
                           Provider<IMissionCity> missionCityProvider,
                           Provider<IMissionCountry> missionCountryProvider,
                           Provider<IMissionTeam> missionTeamProvider,
                           Provider<IMissionTrip> missionTripProvider,
                           Provider<IPatient> patientProvider,
                           Provider<IPatientAgeClassification> patientAgeClassificationProvider,
                           Provider<IPatientEncounterPhoto> patientEncounterPhotoProvider,
                           Provider<IPatientEncounter> patientEncounterProvider,
                           Provider<IEncounterService> patientEncounterTabField,
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
        this.loginAttemptProvider = loginAttemptProvider;
        this.patientEncounterProvider = patientEncounterProvider;
        this.medicationProvider = medicationProvider;
        this.medicationActiveDrugNameProvider = medicationActiveDrugNameProvider;
        this.medicationAdministrationProvider = medicationAdministrationProvider;
        this.medicationFormProvider = medicationFormProvider;
        this.medicationActiveDrugProvider = medicationActiveDrugProvider;
        this.medicationMeasurementUnitProvider = medicationMeasurementUnitProvider;
        this.medicationInventoryProvider = medicationInventoryProvider;
        this.missionCityProvider = missionCityProvider;
        this.missionCountryProvider = missionCountryProvider;
        this.missionTeamProvider = missionTeamProvider;
        this.missionTripProvider = missionTripProvider;
        this.patientProvider = patientProvider;
        this.patientEncounterTabField = patientEncounterTabField;
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public ILoginAttempt createLoginAttempt(String usernameValue, boolean isSuccessful, byte[] ipAddress, Integer userId){

        if (StringUtils.isNullOrWhiteSpace(usernameValue) || ipAddress == null) {

            return null;
        }
        ILoginAttempt loginAttempt = loginAttemptProvider.get();
        loginAttempt.setLoginDate(dateUtils.getCurrentDateTime());
        loginAttempt.setIsSuccessful(isSuccessful);
        loginAttempt.setUsernameAttempt(usernameValue);
        loginAttempt.setIp_address(ipAddress);
        if (userId == null)
            loginAttempt.setUser(null);
        else
            loginAttempt.setUser(Ebean.getReference(userProvider.get().getClass(), userId));

        return loginAttempt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public IMedication createMedication(String name, List<IMedicationActiveDrug> medicationActiveDrugs, IMedicationForm medicationForm) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        IMedication medication = medicationProvider.get();

        medication.setName(name);
        medication.setIsDeleted(false);
        medication.setMedicationActiveDrugs(medicationActiveDrugs);
        medication.setMedicationForm(medicationForm);

        return medication;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedicationActiveDrug createMedicationActiveDrug(int value, boolean isDenominator, int activeDrugUnitId, IMedicationActiveDrugName medicationActiveDrugName) {

        IMedicationActiveDrug medicationActiveDrug = medicationActiveDrugProvider.get();

        medicationActiveDrug.setValue(value);
        medicationActiveDrug.setDenominator(isDenominator);
        medicationActiveDrug.setMedicationMeasurementUnit(Ebean.getReference(medicationMeasurementUnitProvider.get().getClass(), activeDrugUnitId));
        medicationActiveDrug.setMedicationActiveDrugName(medicationActiveDrugName);

        return medicationActiveDrug;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedicationActiveDrugName createMedicationActiveDrugName(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        IMedicationActiveDrugName medicationActiveDrugName = medicationActiveDrugNameProvider.get();

        medicationActiveDrugName.setName(name);

        return medicationActiveDrugName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public IMedicationInventory createMedicationInventory(int quantityCurrent, int quantityTotal, int medicationId, int missionTripId) {

        IMedicationInventory medicationInventory;

        try{

            medicationInventory = medicationInventoryProvider.get();
            medicationInventory.setMedication(Ebean.getReference(medicationProvider.get().getClass(), medicationId));
            medicationInventory.setMissionTrip(Ebean.getReference(missionTripProvider.get().getClass(), missionTripId));
            medicationInventory.setQuantity_current(quantityCurrent);
            medicationInventory.setQuantity_total(quantityTotal);
        }catch(Exception ex){

            medicationInventory = null;
        }

        return medicationInventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public IPatientPrescription createPatientPrescription(int amount, int medicationId, Integer medicationAdministrationId, int userId, int encounterId, Integer replacementId, boolean isDispensed, boolean isCounseled) {

        IPatientPrescription patientPrescription = patientPrescriptionProvider.get();

        patientPrescription.setAmount(amount);
        patientPrescription.setDateTaken(dateUtils.getCurrentDateTime());
        patientPrescription.setPatientEncounter(Ebean.getReference(patientEncounterProvider.get().getClass(), encounterId));
        patientPrescription.setMedication(Ebean.getReference(medicationProvider.get().getClass(), medicationId));
        if (medicationAdministrationId != null)
            patientPrescription.setMedicationAdministration(Ebean.getReference(medicationAdministrationProvider.get().getClass(), medicationAdministrationId));
        patientPrescription.setReplacementId(replacementId);
        patientPrescription.setPhysician(Ebean.getReference(userProvider.get().getClass(), userId));
        patientPrescription.setDispensed(isDispensed);
        patientPrescription.setCounseled(isCounseled);

        return patientPrescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public IRole createRole(String name) {

        // @TODO - Check for null name
        IRole role = roleProvider.get();
        role.setName(name);

        return role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
