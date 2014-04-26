package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IPhoto;
import femr.ui.models.data.PatientEncounterItem;
import femr.ui.models.data.PatientItem;
import femr.ui.models.data.VitalItem;

import java.util.List;
import java.util.Map;

/**
 * Interface for Triage Service. Contains the documentation which
 * is inherited in the implementations.
 */
public interface ITriageService {
    /**
     * Retrieve patient and update the patients sex. Used when a user submits a sex for
     * a patient with a previously unidentified sex.
     *
     * @param id the id of the patient
     * @param sex the sex of the patient
     * @return the updated patient
     */
    ServiceResponse<PatientItem> findPatientAndUpdateSex(int id, String sex);

    /**
     * Gets vital items, but only the names
     *
     * @return Returns a list of all vitals without values
     */
    ServiceResponse<List<VitalItem>> findAllVitalItems();

    /**
     * Creates a new patient
     * @param patient patient to be created
     * @return patient with an assigned Id (pk)
     */
    ServiceResponse<PatientItem> createPatient(PatientItem patient);

    /**
     * Create a patient encounter
     *
     * @param patientEncounterItem the patient encounter
     * @return the patient encounter with id (pk)
     */
    ServiceResponse<PatientEncounterItem> createPatientEncounter(PatientEncounterItem patientEncounterItem);

    /**
     * Create all vitals for an encounter
     *
     * @param patientEncounterVitalMap A <name,value> keypair of vitals to be created
     * @param userId User creating the vitals
     * @param encounterId Encounter that the vitals are for
     * @return List of vitals that were created
     */
    ServiceResponse<List<VitalItem>> createPatientEncounterVitalItems(Map<String,Float> patientEncounterVitalMap, int userId, int encounterId);

    /**
     * Find a patient by id
     * @param id id of the patient
     * @return the patient
     */
    ServiceResponse<PatientItem> findPatientItemById(Integer id);
}
