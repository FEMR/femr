package mock.femr.data.daos;

import femr.data.daos.core.IEncounterRepository;
import femr.data.models.core.IPatientEncounter;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MockEncounterRepository implements IEncounterRepository {
    public List<IPatientEncounter> mockEncounters = new ArrayList<>();

    @Override
    public IPatientEncounter createPatientEncounter(int patientID, DateTime date, int userId, Integer patientAgeClassificationId, Integer tripId, String languageCode) {
        return null;
    }

    @Override
    public IPatientEncounter deletePatientEncounter(int encounterId, String reason, int userId) {
        return null;
    }

    @Override
    public List<? extends IPatientEncounter> retrievePatientEncounters(DateTime from, DateTime to, Integer tripId) {
        return null;
    }

    @Override
    public IPatientEncounter retrievePatientEncounterById(int id) {
        return null;
    }

    @Override
    public List<? extends IPatientEncounter> retrievePatientEncountersByPatientIdAsc(int patientId) {
        return mockEncounters;
    }

    @Override
    public List<? extends IPatientEncounter> retrievePatientEncountersByPatientIdDesc(int patientId) {
        return mockEncounters;
    }

    @Override
    public IPatientEncounter savePatientEncounterMedicalCheckin(int encounterId, int userId, DateTime date) {
        return null;
    }

    @Override
    public IPatientEncounter savePatientEncounterPharmacyCheckin(int encounterId, int userId, DateTime date) {
        return null;
    }

    @Override
    public IPatientEncounter savePatientEncounterDiabetesScreening(int encounterId, int userId, DateTime date, Boolean isScreened) {
        return null;
    }
}