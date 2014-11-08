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
package femr.business.services;

import femr.common.dto.ServiceResponse;
import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.common.models.VitalItem;

import java.util.List;
import java.util.Map;

/**
 * Interface for Triage Service. Contains the documentation which
 * is inherited in the implementations.
 */
public interface ITriageService {
    /**
     * Finds all possible age classifications for a patient
     */
    ServiceResponse<List<String>> findPossibleAgeClassifications();
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
}
