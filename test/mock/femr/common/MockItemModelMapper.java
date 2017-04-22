package mock.femr.common;

import femr.common.IItemModelMapper;
import femr.common.models.*;
import femr.data.models.core.*;
import femr.data.models.mysql.MedicationInventory;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class MockItemModelMapper implements IItemModelMapper{
    @Override
    public CityItem createCityItem(String cityName, String countryName) {
        return null;
    }

    @Override
    public MedicationAdministrationItem createMedicationAdministrationItem(IConceptPrescriptionAdministration conceptPrescriptionAdministration) {
        return null;
    }

    @Override
    public MedicationItem createMedicationItem(IMedication medication, Integer quantityCurrent, Integer quantityTotal, DateTime isDeleted) {
        return null;
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
    public PatientItem createPatientItem(int id, String firstName, String lastName, String phoneNumber, String city, String address, int userId, Date age, String sex, Integer weeksPregnant, Integer heightFeet, Integer heightInches, Float weight, String pathToPatientPhoto, Integer photoId, String ageClassification) {
        return null;
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
    public PrescriptionItem createPrescriptionItem(int id, String name, String originalMedicationName, String firstName, String lastName, IConceptPrescriptionAdministration conceptPrescriptionAdministration, Integer amount, IMedication medication, Integer quantityCurrent, Integer quantityInitial, Boolean isCounseled) {
        return null;
    }

    @Override
    public PrescriptionItem createPrescriptionItemWithReplacement(int id, String name, String replacementMedicationName, int replacementAmount, int replacementId, String firstName, String lastName,
                                                                  IConceptPrescriptionAdministration conceptPrescriptionAdministration, Integer amount,
                                                                  IMedication medication, Integer quantityCurrent, Integer quantityInitial, Boolean isCounseled)
    { return null; }

    @Override
    public ProblemItem createProblemItem(String name) {
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
