package mock.femr.data.daos;

import femr.data.daos.core.IPatientEncounterTabFieldRepository;
import femr.data.models.core.IPatientEncounterTabField;

import java.util.HashMap;
import java.util.List;

public class MockPatientEncounterTabFieldRepository implements IPatientEncounterTabFieldRepository {

    HashMap<Integer, List<? extends IPatientEncounterTabField>> tabFields = new HashMap<>();

    public void setEncounterTabFields(HashMap<Integer, List<? extends IPatientEncounterTabField>> tabFields) {
        this.tabFields = tabFields;
    }


    @Override
    public List<? extends IPatientEncounterTabField> getAllByEncounter(int patientEncounterId) {
        return tabFields.get(patientEncounterId);
    }
}
