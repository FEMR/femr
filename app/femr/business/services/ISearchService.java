package femr.business.services;

import femr.common.dto.ServiceResponse;
import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.common.models.PrescriptionItem;
import femr.common.models.ProblemItem;
import femr.data.models.*;
import femr.util.DataStructure.VitalMultiMap;

import java.util.List;

public interface ISearchService {

    ServiceResponse<IPatientPrescription> findPatientPrescriptionById(int id);
    ServiceResponse<List<? extends IVital>> findAllVitals();
    ServiceResponse<List<? extends IPatientEncounterVital>> findPatientEncounterVitals(int encounterId, String name);

    /**
     * Find a patient by id
     * @param patientId id of the patient
     * @return the patient
     */
    ServiceResponse<PatientItem> findPatientItemById(Integer patientId);

    /**
     * Find the most current patient encounter by patient id
     *
     * @param patientId id of the patient
     * @return the patient's encounter with a field indicating whether or not it is open
     */
    ServiceResponse<PatientEncounterItem> findPatientEncounterItemById(int patientId);

    /**
     * Find all patient encounters by patient id
     *
     * @param patientId id of the patient
     * @return all encounters of patient in descending order
     */
    ServiceResponse<List<PatientEncounterItem>> findPatientEncounterItemsById(int patientId);

    /**
     * Find all prescriptions that have not been replaced
     *
     * @param encounterId id of the encounter
     * @return all prescriptions that have not been replaced
     */
    ServiceResponse<List<PrescriptionItem>> findUnreplacedPrescriptionItems(int encounterId);

    /**
     * Find all prescriptions, even ones that have been replaced
     *
     * @param encounterId id of the encounter
     * @return a list of all prescription items for an encounter
     */
    ServiceResponse<List<PrescriptionItem>> findAllPrescriptionItemsByEncounterId(int encounterId);

    /**
     * Find all problems
     *
     * @param encounterId id of the encounter
     * @return list of problems
     */
    ServiceResponse<List<ProblemItem>> findProblemItems(int encounterId);

    /**
     * Parses an integer from a query string
     *
     * @param query the query string
     * @return the integer
     */
    ServiceResponse<Integer> parseIdFromQueryString(String query);

    /**
     * Takes a query string with patient info and returns a list of matching patients
     * based on id then first/last name
     *
     * @param patientSearchQuery search query for a patient
     * @return list of patients
     */
    ServiceResponse<List<PatientItem>> getPatientsFromQueryString(String patientSearchQuery);

    /**
     * Create linked hash map of vitals where the key is the date as well as the name
     *
     * @param encounterId the id of the encounter to get vitals for
     * @return vitals and dates related to encounter
     */
    ServiceResponse<VitalMultiMap> getVitalMultiMap(int encounterId);








}
