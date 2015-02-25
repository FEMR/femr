/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.business.services.core;

import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
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
     * Get all current system setting values, only works for one right now.
     * Will need to be expanded later
     * @return
     */
    ServiceResponse<SettingItem> getSystemSettings();

    /**
     * Get all patient information for searching users.
     *
     * @return patient item for json serialization
     */
    ServiceResponse<List<PatientItem>> findPatientsForSearch();

    /**
     * Get all diagnosis for help on problem input fields in medical
     *
     * @return ALL STRINGS, BABY
     */
    ServiceResponse<List<String>> findDiagnosisForSearch();
}
