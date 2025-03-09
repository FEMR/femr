package femr.data.daos.core;

import femr.data.models.core.IPatientEncounterVital;

import java.util.List;

public interface IPatientEncounterVitalRepository {
    /**
     *  Get all vitals an encounter.
     * @param patientEncounterId Encounter to get patient observations for
     * @return List of patient vitals for that encounter
     */
    List<? extends IPatientEncounterVital> getAllByEncounter(int patientEncounterId);
}
