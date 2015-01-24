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
package femr.business.helpers;

import com.avaje.ebean.Ebean;
import com.google.inject.Inject;

import javax.inject.Provider;

import femr.common.models.*;
import femr.ui.models.research.FilterViewModel;
import femr.data.models.core.*;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Responsible for mapping Domain objects.
 */
public class DomainMapper {

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
    public DomainMapper(Provider<IChiefComplaint> chiefComplaintProvider,
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
     * Create a new TabFieldItem
     *
     * @param patientEncounterTabField DAO with joined TabField
     * @return tab field with value
     */
    public static TabFieldItem createTabFieldItem(IPatientEncounterTabField patientEncounterTabField) {
        if (patientEncounterTabField == null || patientEncounterTabField.getTabField() == null) {
            return null;
        }

        TabFieldItem tabFieldItem = new TabFieldItem();
        tabFieldItem.setName(patientEncounterTabField.getTabField().getName());
        tabFieldItem.setOrder(patientEncounterTabField.getTabField().getOrder());
        tabFieldItem.setPlaceholder(patientEncounterTabField.getTabField().getPlaceholder());
        if (patientEncounterTabField.getTabField().getTabFieldSize() != null)
            tabFieldItem.setSize(patientEncounterTabField.getTabField().getTabFieldSize().getName());
        if (patientEncounterTabField.getTabField().getTabFieldType() != null)
            tabFieldItem.setType(patientEncounterTabField.getTabField().getTabFieldType().getName());
        tabFieldItem.setValue(patientEncounterTabField.getTabFieldValue());
        if (patientEncounterTabField.getTabField().getTab() == null) tabFieldItem.setIsCustom(false);
        else tabFieldItem.setIsCustom(true);
        //if (patientEncounterTabField.getChiefComplaint() != null)
          //  tabFieldItem.setChiefComplaint(patientEncounterTabField.getChiefComplaint().getValue());

        return tabFieldItem;
    }

    /**
     * Create a new TabFieldItem (DTO) based on empty tab field
     *
     * @param tabField basic DAO TabField
     * @return tab field with NULL value
     */
    public static TabFieldItem createTabFieldItem(ITabField tabField) {
        if (tabField == null) {
            return null;
        }
        TabFieldItem tabFieldItem = new TabFieldItem();
        tabFieldItem.setName(tabField.getName());
        tabFieldItem.setOrder(tabField.getOrder());
        tabFieldItem.setPlaceholder(tabField.getPlaceholder());
        if (tabField.getTabFieldSize() != null) tabFieldItem.setSize(tabField.getTabFieldSize().getName());
        if (tabField.getTabFieldType() != null) tabFieldItem.setType(tabField.getTabFieldType().getName());
        tabFieldItem.setValue(null);
        if (tabField.getTab() == null) tabFieldItem.setIsCustom(false);
        else tabFieldItem.setIsCustom(true);

        return tabFieldItem;
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

    public IMissionCountry createMissionCountry(String name) {
        if (StringUtils.isNullOrWhiteSpace(name)) {
            return null;
        }
        IMissionCountry missionCountry = missionCountryProvider.get();
        missionCountry.setName(name);
        return missionCountry;
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
     * Create a new TabItem (DTO)
     *
     * @param tab basic DAO Tab
     * @return a new TabItem
     */
    public static TabItem createTabItem(ITab tab) {
        if (tab == null) {
            return null;
        }
        TabItem tabItem = new TabItem();
        tabItem.setName(tab.getName());
        tabItem.setLeftColumnSize(tab.getLeftColumnSize());
        tabItem.setRightColumnSize(tab.getRightColumnSize());
        return tabItem;
    }

    public IRole createRole(String name) {
        IRole role = roleProvider.get();
        role.setName(name);
        return role;
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
        user.setLastLogin(null);
        user.setDeleted(isDeleted);
        user.setPasswordReset(isPasswordReset);
        user.setNotes(userItem.getNotes());
        user.setRoles(roles);
        return user;
    }

    /**
     * Create a new UserItem (DTO)
     *
     * @param user DAO user
     * @return new userItem
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
        if(user.getLastLogin() != null)
            userItem.setLastLoginDate(dateUtils.getFriendlyDate(user.getLastLogin()));
        else
            userItem.setLastLoginDate("Never");

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

    public IMedicationForm createMedicationForm(String name) {
        IMedicationForm medicationForm = medicationFormProvider.get();
        medicationForm.setName(name);
        medicationForm.setIsDeleted(false);
        return medicationForm;
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
     * Create a new PatientEncounterItem (DTO)
     *
     * @param patientEncounter patient encounter info
     * @return a new PatientEncounterItem
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

    public IChiefComplaint createChiefComplaint(String value, int patientEncounterId) {
        if (StringUtils.isNullOrWhiteSpace(value)) {
            return null;
        }
        IChiefComplaint chiefComplaint = chiefComplaintProvider.get();
        chiefComplaint.setValue(value);
        chiefComplaint.setPatientEncounter(Ebean.getReference(patientEncounterProvider.get().getClass(), patientEncounterId));
        return chiefComplaint;
    }

    public static MissionItem createMissionItem(IMissionTeam missionTeam) {

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
     * Maps an IVital to a VitalItem.
     *
     * @param vital the IVital
     * @return a new VitalItem with no value
     */
    public static VitalItem createVitalItem(IVital vital) {
        //TODO: this shouldn't happen, no need for a vitalitem without a value
        if (vital == null) {
            return null;
        }
        VitalItem vitalItem = new VitalItem();
        vitalItem.setName(vital.getName());
        return vitalItem;
    }



    /**
     * Creates a new patientEncounterTabField
     *
     * @param tabField    DAO tabfield
     * @param userId      id of the user filling out the value
     * @param value       value of the field
     * @param encounterId encounter id of the visit
     * @return a new patient encounter tab field!!
     */
    public IPatientEncounterTabField createPatientEncounterTabField(ITabField tabField, int userId, String value, int encounterId) {
        if (tabField == null || StringUtils.isNullOrWhiteSpace(value)) {
            return null;
        }
        IPatientEncounterTabField patientEncounterTabField = patientEncounterTabFieldProvider.get();
        patientEncounterTabField.setDateTaken(dateUtils.getCurrentDateTime());
        patientEncounterTabField.setUserId(userId);
        patientEncounterTabField.setPatientEncounterId(encounterId);
        patientEncounterTabField.setTabField(tabField);
        patientEncounterTabField.setTabFieldValue(value);
        return patientEncounterTabField;
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
        if (StringUtils.isNullOrWhiteSpace(filePath))
            return null;
        IPhoto photo = photoProvider.get();
        if (StringUtils.isNullOrWhiteSpace(description)) photo.setDescription("");
        else photo.setDescription(description);
        photo.setFilePath(filePath);
        return photo;
    }

    /**
     * Create research filter
     *
     * @param filterViewModel
     * @return ResearchFilterItem
     */
    public static ResearchFilterItem createResearchFilterItem(FilterViewModel filterViewModel) {

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

        filterItem.setRangeStart(filterViewModel.getRangeStart());
        filterItem.setRangeEnd(filterViewModel.getRangeEnd());

        filterItem.setMedicationId(filterViewModel.getMedicationId());

        return filterItem;
    }

}
