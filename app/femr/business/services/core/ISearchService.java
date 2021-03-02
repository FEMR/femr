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
import java.util.List;

public interface ISearchService {

    /**
     * Find a patient by patient id.
     *
     * @param patientId id of the patient
     * @return a service response that contains a PatientItem
     * and/or errors if they exist.
     */
    ServiceResponse<PatientItem> retrievePatientItemByPatientId(int patientId);

    /**
     * Retrieve a patient by encounter id.
     *
     * @param encounterId id of an encounter
     * @return a service response that contains a PatientItem
     * and/or errors if they exist.
     */
    ServiceResponse<PatientItem> retrievePatientItemByEncounterId(int encounterId);

    /**
     * Retrieve an encounter by encounter id
     *
     * @param encounterId the id of the encounter
     * @return a service response that contains a PatientEncounterItem
     * and/or errors if they exist.
     */
    ServiceResponse<PatientEncounterItem> retrievePatientEncounterItemByEncounterId(int encounterId);

    /**
     * Retrieve the most current patient encounter for a patient
     *
     * @param patientId id of the patient
     * @return a service response that contains a PatientEncounterItem
     * and/or errors if they exist.
     */
    ServiceResponse<PatientEncounterItem> retrieveRecentPatientEncounterItemByPatientId(int patientId);

    /**
     * Retrieve all patient encounters by patient id
     *
     * @param patientId id of the patient
     * @return a service response that contains a list of PatientEncounterItems
     * and/or errors if they exist.
     */
    ServiceResponse<List<PatientEncounterItem>> retrievePatientEncounterItemsByPatientId(int patientId);

    /**
     * Find all prescriptions that have not been replaced. This does not imply they have been dispensed.
     *
     * @param encounterId id of the encounter
     * @param tripId id of the trip that the user is on. This is used for managing inventory if the user
     *               is assigned to a trip, can be null
     * @return a service response that contains a list of PrescriptionItems
     * and/or errors if they exist.
     */
    ServiceResponse<List<PrescriptionItem>> retrieveUnreplacedPrescriptionItems(int encounterId, Integer tripId);

    /**
     * Find all prescriptions that have been dispensed.
     *
     * @param encounterId id of the encounter
     * @return a service response that contains a list of PrescriptionItems
     * and/or errors if they exist.
     */
    ServiceResponse<List<PrescriptionItem>> retrieveDispensedPrescriptionItems(int encounterId);

    /**
     * Parses an integer from a query string. TODO: this probably shouldn't be in the business layer?
     *
     * @param query the query string
     * @return a service response that contains an Integer
     * and/or errors if they exist.
     */
    ServiceResponse<Integer> parseIdFromQueryString(String query);

    /**
     * Takes a query string with patient info and returns a list of matching patients
     * based on id then first/last name
     *
     * @param patientSearchQuery search query for a patient
     * @return a service response that contains a list of PatientItems
     * and/or errors if they exist.
     */
    ServiceResponse<List<PatientItem>> retrievePatientsFromQueryString(String patientSearchQuery);

    ServiceResponse<List<RankedPatientItem>> retrievePatientsFromTriageSearch(String first, String last, String phone, String addr, String gender, Long age, String city);


    /**
     * Get all current system setting values, only works for one right now.
     * Will need to be expanded later
     *
     * @return a service response that contains a SettingItem
     * and/or errors if they exist.
     */
    ServiceResponse<SettingItem> retrieveSystemSettings();

    /**
     * Get all patient information for searching users.
     *
     * @param tripId id of the current trip. If this is null then all patients will be returned regardless of country, may be null
     * @return a service response that contains a list of PatientItems
     * and/or errors if they exist.
     */
    ServiceResponse<List<PatientItem>> retrievePatientsForSearch(Integer tripId);

    /**
     * Get all diagnosis for help on problem input fields in medical
     *
     * @return a service response that contains a list of Strings
     * and/or errors if they exist.
     */
    ServiceResponse<List<String>> findDiagnosisForSearch();

    /**
     * AJ Saclayan Cities
     * Get string typed by user
     *
     * @return
     */
    ServiceResponse<List<CityItem>> retrieveCitiesFromQueryString(String citySearchQuery);

    /**
     * Get all patient information for searching users.
     *
     * @return a service response that contains a list of PatientItems
     * and/or errors if they exist.
     */
    ServiceResponse<List<CityItem>> retrieveCitiesForSearch();


}
