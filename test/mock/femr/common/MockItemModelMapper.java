package mock.femr.common;

import femr.common.IItemModelMapper;
import femr.common.models.*;
import femr.data.models.core.*;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class MockItemModelMapper implements IItemModelMapper{

    public boolean createPatientItemWasCalled = false;
    public boolean createPrescriptionItemWasCalled = false;

    @Override
    public CityItem createCityItem(String cityName, String countryName) {
        return null;
    }

    @Override
    public MedicationAdministrationItem createMedicationAdministrationItem(IConceptPrescriptionAdministration conceptPrescriptionAdministration) {
        return null;
    }

    @Override
    public MedicationItem createMedicationItem(IMedication medication, Integer quantityCurrent, Integer quantityTotal, DateTime isDeleted, String timeAdded, String createdBy) {
        MedicationItem medicationItem = new MedicationItem();
        medicationItem.setId(medication.getId());
        return medicationItem;
    }

    @Override
    public MissionItem createMissionItem(IMissionTeam missionTeam, List<MissionTripItem> missionTripItems) {
        return null;
    }

    @Override
    public MissionTripItem createMissionTripItem(IMissionTrip missionTrip) {
        return null;
    }

    @Override
    public PatientItem createPatientItem(int id, String firstName, String lastName, String phoneNumber, String city, String address,
                                         int userId, Date age, String sex, Integer weeksPregnant, Integer heightFeet, Integer heightInches,
                                         Float weight, String pathToPatientPhoto, Integer photoId, String ageClassification, Integer smoker, Integer diabetic, Integer alcohol, Integer cholesterol, Integer hypertension) {


        //don't really need a mock patient item yet
        PatientItem patientItem = new PatientItem();
        patientItem.setId(id);
        patientItem.setFirstName(firstName);
        patientItem.setLastName(lastName);
        patientItem.setPhoneNumber(phoneNumber);
        patientItem.setCity(city);
        patientItem.setAddress(address);
        patientItem.setUserId(userId);
        patientItem.setSex(sex);
        patientItem.setWeeksPregnant(weeksPregnant);
        patientItem.setHeightFeet(heightFeet);
        patientItem.setHeightInches(heightInches);
        patientItem.setWeight(weight);
        patientItem.setPathToPhoto(pathToPatientPhoto);
        patientItem.setPhotoId(photoId);
        patientItem.setBirth(age);
        patientItem.setAge(ageClassification);

        createPatientItemWasCalled = true;
        return patientItem;
    }

    @Override
    public PatientEncounterItem createPatientEncounterItem(IPatientEncounter patientEncounter) {
        return null;
    }

    @Override
    public PhotoItem createPhotoItem(int id, String description, Date insertTimeStamp, String imageURL) {
        return null;
    }

    @Override
    public PrescriptionItem createPrescriptionItem(int id, String name, String firstName, String lastName, IConceptPrescriptionAdministration conceptPrescriptionAdministration, Integer amount, Boolean isCounseled, MedicationItem medicationItem) {
        createPrescriptionItemWasCalled = true;
        PrescriptionItem prescriptionItem = new PrescriptionItem();

        prescriptionItem.setMedicationID(id);
        prescriptionItem.setAmount(amount);
        prescriptionItem.setAdministrationID(conceptPrescriptionAdministration.getId());

        return prescriptionItem;
    }

    @Override
    public PrescriptionItem createPrescriptionItemWithReplacement(int id, String name, String replacementMedicationName, int replacementId, String firstName, String lastName, IConceptPrescriptionAdministration conceptPrescriptionAdministration, Integer amount, Boolean isCounseled, MedicationItem medicationItem) {
        return null;
    }

    @Override
    public ProblemItem createProblemItem(String name) {
        return null;
    }

    @Override
    public NoteItem createNoteItem(String name, DateTime datetimestamp, String reporter) {
        return null;
    }

    @Override
    public SettingItem createSettingItem(List<? extends ISystemSetting> systemSettings) {
        return null;
    }

    @Override
    public TabItem createTabItem(String name, boolean isCustom, Integer leftColumnSize, Integer rightColumnSize) {
        return null;
    }

    @Override
    public TabFieldItem createTabFieldItem(String name, String type, String size, Integer order, String placeholder, String value, String chiefComplaint, boolean isCustom) {
        return null;
    }

    @Override
    public TabFieldItem createTabFieldItem(String name, String type, String size, Integer order, String placeholder, String value, String chiefComplaint, boolean isCustom, String userName) {
        return null;
    }

    @Override
    public TeamItem createTeamItem(String name, String location, String description) {
        return null;
    }

    @Override
    public TripItem createTripItem(String teamName, String tripCity, String tripCountry, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public UserItem createUserItem(IUser user) {
        return null;
    }

    @Override
    public VitalItem createVitalItem(String name, Float value) {
        return null;
    }
}
