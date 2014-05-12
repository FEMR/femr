package femr.business;

import com.google.inject.Inject;
import javax.inject.Provider;
import femr.business.dtos.*;
import femr.common.models.*;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;

/**
 * Responsible for mapping Domain objects.
 */
public class DomainMapper {

    private final Provider<IMedication> medicationProvider;
    private final Provider<IPatientEncounterPhoto> patientEncounterPhotoProvider;
    private final Provider<IPatientEncounter> patientEncounterProvider;
    private final Provider<IPatientEncounterTabField> patientEncounterTabFieldProvider;
    private final Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private final Provider<IPatientPrescription> patientPrescriptionProvider;
    private final Provider<IPatient> patientProvider;
    private final Provider<IPatientResearch> patientResearchProvider;
    private final Provider<IPhoto> photoProvider;
    private final Provider<ITabField> tabFieldProvider;
    private final Provider<ITabFieldSize> tabFieldSizeProvider;
    private final Provider<ITabFieldType> tabFieldTypeProvider;
    private final Provider<ITab> tabProvider;
    private final Provider<IUser> userProvider;
    private final Provider<IVital> vitalProvider;

    @Inject
    public DomainMapper(Provider<IMedication> medicationProvider,
                        Provider<IPatientEncounterPhoto> patientEncounterPhotoProvider,
                        Provider<IPatientEncounter> patientEncounterProvider,
                        Provider<IPatientEncounterTabField> patientEncounterTabFieldProvider,
                        Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                        Provider<IPatientPrescription> patientPrescriptionProvider,
                        Provider<IPatient> patientProvider,
                        Provider<IPatientResearch> patientResearchProvider,
                        Provider<IPhoto> photoProvider,
                        Provider<ITabField> tabFieldProvider,
                        Provider<ITabFieldSize> tabFieldSizeProvider,
                        Provider<ITabFieldType> tabFieldTypeProvider,
                        Provider<ITab> tabProvider,
                        Provider<IUser> userProvider,
                        Provider<IVital> vitalProvider) {
        this.patientEncounterProvider = patientEncounterProvider;
        this.medicationProvider = medicationProvider;
        this.patientEncounterPhotoProvider = patientEncounterPhotoProvider;
        this.patientEncounterTabFieldProvider = patientEncounterTabFieldProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.patientPrescriptionProvider = patientPrescriptionProvider;
        this.patientProvider = patientProvider;
        this.patientResearchProvider = patientResearchProvider;
        this.photoProvider = photoProvider;
        this.tabFieldProvider = tabFieldProvider;
        this.tabFieldSizeProvider = tabFieldSizeProvider;
        this.tabFieldTypeProvider = tabFieldTypeProvider;
        this.tabProvider = tabProvider;
        this.userProvider = userProvider;
        this.vitalProvider = vitalProvider;
    }

    /**
     * Create a new TabFieldItem (DTO) that contains a value.
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
        tabFieldItem.setSize(patientEncounterTabField.getTabField().getTabFieldSize().getName());
        tabFieldItem.setType(patientEncounterTabField.getTabField().getTabFieldType().getName());
        tabFieldItem.setValue(patientEncounterTabField.getTabFieldValue());
        if (patientEncounterTabField.getTabField().getTab() == null) tabFieldItem.setIsCustom(false);
        else tabFieldItem.setIsCustom(true);

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
        if (tabFieldItem == null || iTabFieldSize == null || iTabFieldType == null || tab == null){
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
     * Create a new TabItem (DTO)
     *
     * @param tab basic DAO Tab
     * @return a new TabItem
     */
    public static TabItem createTabItem(ITab tab) {
        if (tab == null){
            return null;
        }
        TabItem tabItem = new TabItem();
        tabItem.setName(tab.getName());
        tabItem.setLeftColumnSize(tab.getLeftColumnSize());
        tabItem.setRightColumnSize(tab.getRightColumnSize());
        return tabItem;
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
        if (tabItem == null){
            return null;
        }
        ITab tab = tabProvider.get();
        tab.setDateCreated(dateUtils.getCurrentDateTime());
        tab.setIsDeleted(isDeleted);
        tab.setLeftColumnSize(tabItem.getLeftColumnSize());
        tab.setRightColumnSize(tabItem.getRightColumnSize());
        tab.setName(tabItem.getName());
        tab.setUserId(userId);
        return tab;
    }

    /**
     * Create a new PatientEncounterItem (DTO)
     *
     * @param patientEncounter patient encounter info
     * @param isClosed         whether or not the encounter is open
     * @return a new PatientEncounterItem
     */
    public static PatientEncounterItem createPatientEncounterItem(IPatientEncounter patientEncounter, Boolean isClosed) {
        if (patientEncounter == null) {
            return null;
        }
        PatientEncounterItem patientEncounterItem = new PatientEncounterItem();
        patientEncounterItem.setChiefComplaint(patientEncounter.getChiefComplaint());
        patientEncounterItem.setId(patientEncounter.getId());
        patientEncounterItem.setPatientId(patientEncounter.getPatientId());
        patientEncounterItem.setWeeksPregnant(patientEncounter.getWeeksPregnant());
        patientEncounterItem.setIsClosed(isClosed);
        patientEncounterItem.setUserId(patientEncounter.getUserId());
        patientEncounterItem.setDoctorName(patientEncounter.getDoctor().getFirstName() + patientEncounter.getDoctor().getLastName());
        patientEncounterItem.setPharmacistName(patientEncounter.getPharmacist().getFirstName() + patientEncounter.getPharmacist().getLastName());
        return patientEncounterItem;
    }

    /**
     * Create a new PatientEncounter (DAO)
     *
     * @param patientEncounterItem patient encounter DTO
     * @param userId               id of the user creating the encounter (null if N/A)
     * @return a new PatientEncounter
     */
    public IPatientEncounter createPatientEncounter(PatientEncounterItem patientEncounterItem, Integer userId) {
        if (patientEncounterItem == null || userId < 1) {
            return null;
        }
        IPatientEncounter patientEncounter = patientEncounterProvider.get();
        patientEncounter.setChiefComplaint(patientEncounterItem.getChiefComplaint());
        patientEncounter.setDateOfVisit(patientEncounterItem.getDateOfVisit());
        patientEncounter.setPatientId(patientEncounterItem.getPatientId());
        if (userId != null) patientEncounter.setUserId(userId);
        patientEncounter.setWeeksPregnant(patientEncounterItem.getWeeksPregnant());
        return patientEncounter;
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
        return medicationItem;
    }

    public IMedication createMedication(MedicationItem medicationItem) {
        if (medicationItem == null){
            return null;
        }
        IMedication medication = medicationProvider.get();
        medication.setName(medicationItem.getName());
        medication.setQuantity_total(medicationItem.getQuantity_total());
        medication.setQuantity_current(medicationItem.getQuantity_current());
        medication.setIsDeleted(false);
        return medication;
    }

    /**
     * Creates a patient prescription without mapping from a prescriptionitem
     *
     * @param amount           amount of the prescription
     * @param encounterId      encounter id of the prescription
     * @param prescriptionItem name of the prescription
     * @param replacementId    replacementid of the new prescription
     * @param userId           id of the user creating the prescription
     * @return the new prescription
     */
    public IPatientPrescription createPatientPrescription(int amount, int encounterId, PrescriptionItem prescriptionItem, Integer replacementId, int userId) {
        if (prescriptionItem == null || encounterId < 1 || StringUtils.isNullOrWhiteSpace(prescriptionItem.getName()) || userId < 1) {
            return null;
        }
        IPatientPrescription newPatientPrescription = patientPrescriptionProvider.get();
        newPatientPrescription.setAmount(amount);
        newPatientPrescription.setDateTaken(dateUtils.getCurrentDateTime());
        newPatientPrescription.setEncounterId(encounterId);
        newPatientPrescription.setMedicationName(prescriptionItem.getName());
        newPatientPrescription.setReplacementId(replacementId);
        newPatientPrescription.setUserId(userId);
        return newPatientPrescription;
    }

    public PrescriptionItem createPrescriptionItem(IPatientPrescription patientPrescription) {
        if (patientPrescription == null) {
            return null;
        }
        PrescriptionItem prescriptionItem = new PrescriptionItem();
        prescriptionItem.setId(patientPrescription.getId());
        prescriptionItem.setName(patientPrescription.getMedicationName());
        prescriptionItem.setReplacementId(patientPrescription.getReplacementId());
        return prescriptionItem;
    }

    public IPatient createPatient(PatientItem patientItem) {
        if (patientItem == null) {
            return null;
        }
        IPatient patient = patientProvider.get();
        patient.setUserId(patientItem.getUserId());
        patient.setFirstName(patientItem.getFirstName());
        patient.setLastName(patientItem.getLastName());
        patient.setAge(patientItem.getBirth());
        patient.setSex(patientItem.getSex());
        patient.setAddress(patientItem.getAddress());
        patient.setCity(patientItem.getCity());
        patient.setPhotoId(patientItem.getPhotoId());
        return patient;
    }

    public static PatientItem createPatientItem(IPatient patient) {
        if (patient == null) {
            return null;
        }
        PatientItem patientItem = new PatientItem();
        patientItem.setAddress(patient.getAddress());
        patientItem.setBirth(patient.getAge());
        patientItem.setCity(patient.getCity());
        patientItem.setFirstName(patient.getFirstName());
        patientItem.setId(patient.getId());
        patientItem.setLastName(patient.getLastName());
        patientItem.setPhotoId(patient.getPhotoId());
        patientItem.setSex(patient.getSex());
        patientItem.setUserId(patient.getUserId());

        //lets include a photo path
        return patientItem;
    }

    /**
     * Maps an IVital to a VitalItem.
     *
     * @param vital the IVital
     * @return a new VitalItem with no value
     */
    public static VitalItem createVitalItem(IVital vital) {
        if (vital == null) {
            return null;
        }
        VitalItem vitalItem = new VitalItem();
        vitalItem.setName(vital.getName());
        return vitalItem;
    }

    /**
     * Maps an IPatientEncounterVital to a VitalItem
     *
     * @param patientEncounterVital the IPatientEncounterVital
     * @return a new VitalItem with a value
     */
    public static VitalItem createVitalItem(IPatientEncounterVital patientEncounterVital) {
        if (patientEncounterVital == null || patientEncounterVital.getVital() == null) {
            return null;
        }
        VitalItem vitalItem = new VitalItem();
        vitalItem.setName(patientEncounterVital.getVital().getName());
        vitalItem.setValue(patientEncounterVital.getVitalValue());
        return vitalItem;
    }

    /**
     * gets a photo item
     *
     * @param photo the photo
     * @param imageURL url to the image
     * @return a photoitem
     */
    public PhotoItem createPhotoItem(IPhoto photo, String imageURL) {
        PhotoItem photoItem = new PhotoItem();
        if (photo == null || StringUtils.isNullOrWhiteSpace(imageURL)) {
            return null;
        }

        photoItem.setId(photo.getId());
        photoItem.setImageDesc(photo.getDescription());
        photoItem.setImageUrl(imageURL);
        photoItem.setImageDate(StringUtils.ToSimpleDate(photo.getInsertTS()));

        return photoItem;
    }

    /**
     * Creates a new patientEncounterTabField
     *
     * @param tabField DAO tabfield
     * @param userId id of the user filling out the value
     * @param value value of the field
     * @return a new patient encounter tab field!!
     */
    public IPatientEncounterTabField createPatientEncounterTabField(ITabField tabField, int userId, String value){
        if (tabField == null || StringUtils.isNullOrWhiteSpace(value)){
            return null;
        }
        IPatientEncounterTabField patientEncounterTabField = patientEncounterTabFieldProvider.get();
        patientEncounterTabField.setDateTaken(dateUtils.getCurrentDateTime());
        patientEncounterTabField.setUserId(userId);
        patientEncounterTabField.setTabField(tabField);
        patientEncounterTabField.setTabFieldValue(value);
        return patientEncounterTabField;
    }

    /**
     * Creates a new IPatientPrescription
     *
     * @param prescriptionItem the prescription item
     * @param userId id of the user creating the prescription
     * @param encounterId encounter id of the prescription
     * @param replacementId id of the prescription being replaced OR null
     * @return a new IPatientPrescription
     */
    public IPatientPrescription createPatientPrescription(PrescriptionItem prescriptionItem, int userId, int encounterId, Integer replacementId){
        if (prescriptionItem == null){
            return null;
        }
        IPatientPrescription patientPrescription = patientPrescriptionProvider.get();
        patientPrescription.setAmount(0);//to be changed later
        patientPrescription.setReplacementId(replacementId);
        patientPrescription.setDateTaken(dateUtils.getCurrentDateTime());
        patientPrescription.setEncounterId(encounterId);
        patientPrescription.setMedicationName(prescriptionItem.getName());
        patientPrescription.setUserId(userId);
        return patientPrescription;
    }

    /**
     * Create a new prescription item
     *
     * @param patientPrescription the IPatientPrescription
     * @return a new prescription item
     */
    public PrescriptionItem createPatientPrescriptionItem(IPatientPrescription patientPrescription){
        if (patientPrescription == null){
            return null;
        }

        return new PrescriptionItem(patientPrescription.getMedicationName());
    }

    public ProblemItem createProblemItem(IPatientEncounterTabField patientEncounterTabField){
        if (patientEncounterTabField == null || patientEncounterTabField.getTabField() == null){
            return null;
        }
        ProblemItem problemItem = new ProblemItem();
        problemItem.setName(patientEncounterTabField.getTabField().getName());
        return problemItem;
    }



}
