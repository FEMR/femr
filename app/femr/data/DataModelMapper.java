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
     * Creates an IMedication
     * TODO: this should be a prescription, not a medication
     *
     * @param name name of the medication
     * @return a new IMedication
     */
    public IMedication createMedication(String name) {

        IMedication medication = medicationProvider.get();

        medication.setName(name);
        medication.setIsDeleted(false);

        return medication;
    }

    /**
     * Creates a brand new medication that is being added to the inventory
     *
     * @param medicationItem medication item without active ingredients, separate active ingredients in the service
     * @return a new MedicationItem
     */
    public IMedication createMedication(MedicationItem medicationItem, List<IMedicationActiveDrug> medicationActiveDrugs, IMedicationForm medicationForm) {

        if (medicationItem == null) {

            return null;
        }

        IMedication medication = medicationProvider.get();

        medication.setName(medicationItem.getName());
        medication.setQuantity_total(medicationItem.getQuantity_total());
        medication.setQuantity_current(medicationItem.getQuantity_current());
        medication.setIsDeleted(false);
        medication.setMedicationActiveDrugs(medicationActiveDrugs);
        medication.setMedicationForm(medicationForm);

        return medication;
    }

    /**
     * Creates a new active drug
     *
     * @param value                    strength of the drug
     * @param isDenominator            is the drug a denominator
     * @param activeDrugUnitId         id of the unit for measurement of the drug
     * @param medicationActiveDrugName the drug name
     * @return new active drug
     */
    public IMedicationActiveDrug createMedicationActiveDrug(int value, boolean isDenominator, int activeDrugUnitId, IMedicationActiveDrugName medicationActiveDrugName) {

        IMedicationActiveDrug medicationActiveDrug = medicationActiveDrugProvider.get();

        medicationActiveDrug.setValue(value);
        medicationActiveDrug.setDenominator(isDenominator);
        medicationActiveDrug.setMedicationMeasurementUnit(Ebean.getReference(medicationMeasurementUnitProvider.get().getClass(), activeDrugUnitId));
        medicationActiveDrug.setMedicationActiveDrugName(medicationActiveDrugName);

        return medicationActiveDrug;
    }

    public IMedicationActiveDrugName createMedicationActiveDrugName(String name) {

        IMedicationActiveDrugName medicationActiveDrugName = medicationActiveDrugNameProvider.get();

        medicationActiveDrugName.setName(name);

        return medicationActiveDrugName;
    }

    public IMedicationForm createMedicationForm(String name) {

        IMedicationForm medicationForm = medicationFormProvider.get();

        medicationForm.setName(name);
        medicationForm.setIsDeleted(false);

        return medicationForm;
    }

    public IMissionCity createMissionCity(String name, IMissionCountry missionCountry) {

        if (StringUtils.isNullOrWhiteSpace(name) || missionCountry == null) {

            return null;
        }

        IMissionCity missionCity = missionCityProvider.get();

        missionCity.setMissionCountry(missionCountry);
        missionCity.setName(name);

        return missionCity;
    }

    public IMissionCountry createMissionCountry(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        IMissionCountry missionCountry = missionCountryProvider.get();

        missionCountry.setName(name);

        return missionCountry;
    }

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
     * Create a new mission trip
     *
     * @param startDate   start date of the trip
     * @param endDate     end date of the trip
     * @param isCurrent   is this the current trip?
     * @param missionCity the city where the trip is taking place
     * @param missionTeam the country where the trip is taking place
     * @return a brand spankin' new freakin' mission trip
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

    public IPatient createPatient(PatientItem patientItem) {

        if (patientItem == null) {

            return null;
        }

        IPatient patient = patientProvider.get();

        patient.setUserId(patientItem.getUserId());
        patient.setFirstName(patientItem.getFirstName());
        patient.setLastName(patientItem.getLastName());
        if (patientItem.getBirth() != null)
            patient.setAge(patientItem.getBirth());
        patient.setSex(patientItem.getSex());
        patient.setAddress(patientItem.getAddress());
        patient.setCity(patientItem.getCity());
        if (patientItem.getPhotoId() != null)
            patient.setPhoto(Ebean.getReference(photoProvider.get().getClass(), patientItem.getPhotoId()));

        return patient;
    }

    /**
     * Create a new PatientEncounter (DAO)
     *
     * @param patientEncounterItem patient encounter DTO
     * @param userId               id of the user creating the encounter
     * @return a new PatientEncounter
     */
    public IPatientEncounter createPatientEncounter(PatientEncounterItem patientEncounterItem, int userId, Integer patientAgeClassificationId, Integer tripId) {

        if (patientEncounterItem == null || userId < 1) {

            return null;
        }

        IPatientEncounter patientEncounter = patientEncounterProvider.get();

        patientEncounter.setDateOfTriageVisit(DateTime.now());
        //provide a proxy patient for the encounter
        patientEncounter.setPatient(Ebean.getReference(patientProvider.get().getClass(), patientEncounterItem.getPatientId()));
        patientEncounter.setNurse(Ebean.getReference(userProvider.get().getClass(), userId));
        patientEncounter.setWeeksPregnant(patientEncounterItem.getWeeksPregnant());
        if (patientAgeClassificationId != null)
            patientEncounter.setPatientAgeClassification(Ebean.getReference(patientAgeClassificationProvider.get().getClass(), patientAgeClassificationId));
        if (tripId != null)
            patientEncounter.setMissionTrip(Ebean.getReference(missionTripProvider.get().getClass(), tripId));

        return patientEncounter;
    }

    /**
     * Creates a new patientEncounterTabField
     *
     * @param tabFieldId  id of the tab field
     * @param userId      id of the user filling out the value
     * @param value       value of the field
     * @param encounterId encounter id of the visit
     * @return a new patient encounter tab field!!
     */
    public IPatientEncounterTabField createPatientEncounterTabField(int tabFieldId, int userId, String value, int encounterId, DateTime dateTaken, Integer chiefComplaintId) {

        if (StringUtils.isNullOrWhiteSpace(value)) {

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

    public IPatientEncounterVital createPatientEncounterVital(int encounterId, int userId, String time, IVital vital, float vitalKey) {

        if (vital == null || encounterId < 1 || userId < 1) {

            return null;
        }

        IPatientEncounterVital patientEncounterVital = patientEncounterVitalProvider.get();

        patientEncounterVital.setPatientEncounterId(encounterId);
        patientEncounterVital.setUserId(userId);
        patientEncounterVital.setDateTaken(time);
        patientEncounterVital.setVital(vital);
        patientEncounterVital.setVitalValue(vitalKey);

        return patientEncounterVital;
    }

    /**
     * Creates a new IPatientPrescription
     *
     * @param amount        amount of medication dispensed
     * @param medication    the medication
     * @param userId        id of the user creating the prescription
     * @param encounterId   encounter id of the prescription
     * @param replacementId id of the prescription being replaced OR null
     * @param isDispensed   is the patient prescription dispensed to the patient yet
     * @return a new IPatientPrescription
     */
    public IPatientPrescription createPatientPrescription(int amount, IMedication medication, int userId, int encounterId, Integer replacementId, boolean isDispensed, boolean isCounseled) {

        if (medication == null || StringUtils.isNullOrWhiteSpace(medication.getName()) || userId < 1 || encounterId < 1) {

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
     * Create a new photo
     *
     * @param description
     * @param filePath
     * @return
     */
    public IPhoto createPhoto(String description, String filePath) {

        if (StringUtils.isNullOrWhiteSpace(filePath)){

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
     * Create a new Tab (DAO)
     *
     * @param tabItem   tab DTO
     * @param isDeleted whether or not the tab is considered deleted
     * @param userId    id of the user creating the tab
     * @return a new Tab
     */
    public ITab createTab(TabItem tabItem, Boolean isDeleted, int userId) {

        if (tabItem == null) {

            return null;
        }

        ITab tab = tabProvider.get();

        tab.setDateCreated(dateUtils.getCurrentDateTime());
        tab.setIsDeleted(isDeleted);
        tab.setLeftColumnSize(tabItem.getLeftColumnSize());
        tab.setRightColumnSize(tabItem.getRightColumnSize());
        tab.setName(tabItem.getName());
        tab.setUserId(userId);
        //this will always be true if a tab is being programmatically created by a user
        tab.setIsCustom(true);

        return tab;
    }

    /**
     * Create a new TabField (DAO)
     *
     * @param tabFieldItem  the new TabFieldItem (DTO)
     * @param isDeleted     whether or not the tab field is considered deleted
     * @param iTabFieldSize size of the TabField
     * @param iTabFieldType type of the TabField
     * @return
     */
    public ITabField createTabField(TabFieldItem tabFieldItem, Boolean isDeleted, ITabFieldSize iTabFieldSize, ITabFieldType iTabFieldType, ITab tab) {

        if (tabFieldItem == null || iTabFieldSize == null || iTabFieldType == null || tab == null) {

            return null;
        }

        ITabField tabField = tabFieldProvider.get();

        tabField.setIsDeleted(isDeleted);
        tabField.setName(tabFieldItem.getName());
        tabField.setOrder(tabFieldItem.getOrder());
        tabField.setPlaceholder(tabFieldItem.getPlaceholder());
        tabField.setTabFieldSize(iTabFieldSize);
        tabField.setTabFieldType(iTabFieldType);
        tabField.setTab(tab);

        return tabField;
    }

    /**
     * Create a new user - MAKE SURE YOU ENCRYPT THE PASSWORD
     *
     * @param userItem        useritem from the UI
     * @param password        unencrypted password
     * @param isDeleted       is the user deleted
     * @param isPasswordReset does the user need to do a password reset
     * @return
     */
    public IUser createUser(UserItem userItem, String password, boolean isDeleted, boolean isPasswordReset, List<? extends IRole> roles) {

        IUser user = userProvider.get();

        user.setFirstName(userItem.getFirstName());
        user.setLastName(userItem.getLastName());
        user.setEmail(userItem.getEmail());
        user.setPassword(password);
        user.setLastLogin(dateUtils.getCurrentDateTime());
        user.setDeleted(isDeleted);
        user.setPasswordReset(isPasswordReset);
        user.setNotes(userItem.getNotes());
        user.setRoles(roles);

        return user;
    }
}
