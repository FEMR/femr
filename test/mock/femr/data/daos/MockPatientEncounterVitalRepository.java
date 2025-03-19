package mock.femr.data.daos;

import femr.data.daos.core.IPatientEncounterVitalRepository;
import femr.data.models.core.IPatientEncounterVital;
import mock.femr.data.models.MockPatientEncounterVital;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MockPatientEncounterVitalRepository implements IPatientEncounterVitalRepository {

    HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();

    public MockPatientEncounterVitalRepository() {
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        vitals.add(new MockPatientEncounterVital());
        encounterVitals.put(0, vitals);
    }

    public void setEncounterVitals(HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals) {
        this.encounterVitals = encounterVitals;
    }

    @Override
    public List<? extends IPatientEncounterVital> getAllByEncounter(int patientEncounterId) {
        return encounterVitals.getOrDefault(patientEncounterId, Collections.emptyList());
    }
}
