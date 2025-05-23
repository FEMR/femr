package mock.femr.data.daos;

import femr.data.daos.core.IEncounterRepository;
import femr.data.models.core.IPatientEncounter;
import mock.femr.data.models.MockPatientEncounter;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockEncounterRepository implements IEncounterRepository {
    public List<IPatientEncounter> patientEncounters = new ArrayList<>();

    public MockEncounterRepository() {
        patientEncounters.add(new MockPatientEncounter());
    }

    public void setPatientEncounters(List<IPatientEncounter> patientEncounters) {
        this.patientEncounters = patientEncounters;
    }

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
        return patientEncounters;
    }

    @Override
    public IPatientEncounter retrievePatientEncounterById(int id) {
        return null;
    }

    @Override
    public List<? extends IPatientEncounter> retrievePatientEncountersByPatientIdAsc(int patientId) {
        return patientEncounters.stream().filter((encounter) -> encounter.getPatient().getId() == patientId).collect(Collectors.toList());
    }

    @Override
    public List<? extends IPatientEncounter> retrievePatientEncountersByPatientIdDesc(int patientId) {
        return patientEncounters.stream().filter((encounter) -> encounter.getPatient().getId() == patientId).collect(Collectors.toList());
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
