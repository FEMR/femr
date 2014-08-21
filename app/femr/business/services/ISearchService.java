package femr.business.services;

import femr.common.dto.ServiceResponse;
import femr.common.models.*;
import femr.data.models.*;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;

import java.util.List;

public interface ISearchService {

    /**
     * Find a patient by patient id
     *
     * @param patientId id of the patient
     * @return the patient
     */
    ServiceResponse<PatientItem> findPatientItemByPatientId(int patientId);

    /**
     * Find a patient by encounter id
     *
     * @param encounterId id of an encounter
     * @return the patient
     */
    ServiceResponse<PatientItem> findPatientItemByEncounterId(int encounterId);

    /**
     * Find an encounter by its ID
     *
     * @param encounterId the id of the encounter
     * @return the encounter
     */
    ServiceResponse<PatientEncounterItem> findPatientEncounterItemByEncounterId(int encounterId);

    /**
     * Find the most current patient encounter by patient id
     *
     * @param patientId id of the patient
     * @return the patient's encounter with a field indicating whether or not it is open
     */
    ServiceResponse<PatientEncounterItem> findRecentPatientEncounterItemByPatientId(int patientId);

    /**
     * Find all patient encounters by patient id
     *
     * @param patientId id of the patient
     * @return all encounters of patient in descending order
     */
    ServiceResponse<List<PatientEncounterItem>> findPatientEncounterItemsByPatientId(int patientId);

    /**
     * Find all prescriptions that have not been replaced. This does not imply they have been dispensed.
     *
     * @param encounterId id of the encounter
     * @return all prescriptions that have not been replaced
     */
    ServiceResponse<List<PrescriptionItem>> findUnreplacedPrescriptionItems(int encounterId);

    /**
     * Find all prescriptions that have been dispensed to the patient.
     *
     * @param encounterId id of the encounter
     * @return all prescriptions that have been dispensed
     */
    ServiceResponse<List<PrescriptionItem>> findDispensedPrescriptionItems(int encounterId);

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
     * Create a map of tabs and their fields where the key can be the name of the tab
     * or the date
     *
     * @param encounterId id of the encounter
     * @return
     */
    ServiceResponse<TabFieldMultiMap> getTabFieldMultiMap(int encounterId);

    /**
     * Create linked hash map of vitals where the key is the date as well as the name
     *
     * @param encounterId the id of the encounter to get vitals for
     * @return vitals and dates related to encounter
     */
    ServiceResponse<VitalMultiMap> getVitalMultiMap(int encounterId);

    /**
     * Get all current system setting values, only works for one right now.
     * Will need to be expanded later
     * @return
     */
    ServiceResponse<SettingItem> getSystemSettings();

    /**
     * Get all custom fields that exist in the system
     * @return list of their names
     */
    ServiceResponse<List<String>> getCustomFieldList();
}
