package femr.data.daos.core;

import femr.data.models.core.IPatientEncounterTabField;

import java.util.List;

public interface IPatientEncounterTabFieldRepository {
    /**
     *  Get all tab fields an encounter.
     * @param patientEncounterId Encounter to get patient tab fields for
     * @return List of patient tab fields for that encounter
     */
    List<? extends IPatientEncounterTabField> getAllByEncounter(int patientEncounterId);
}
