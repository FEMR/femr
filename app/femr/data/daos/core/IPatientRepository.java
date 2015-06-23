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
package femr.data.daos.core;

import femr.data.models.core.IPatient;
import femr.data.models.core.IPatientAgeClassification;

import java.util.List;

/**
 * A repository to cover the following tables:
 * <ul>
 * <li>patients</li>
 * <li>patient_age_classifications</li>
 * </ul>
 */
public interface IPatientRepository {

    /**
     * Create a new patient in the database
     *
     * @param patient the patient to be created, not null
     * @return the new patient with their ID or null if the save failed
     */
    IPatient createPatient(IPatient patient);

    /**
     * Retrieve one patient by the patient's id.
     *
     * @param id the id of the patient
     * @return the patient or null if none found
     */
    IPatient findPatientById(int id);

    /**
     * Retrieve all patients in the database.
     *
     * @return a list of patients
     */
    List<? extends IPatient> findAllPatients();

    /**
     * Retrieve a patient by their first and last name.
     *
     * @param firstName the first name of the patient, not null
     * @param lastName  the last name of the patient, not null
     * @return a list of patients that match the criteria
     */
    List<? extends IPatient> findPatientsByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Retrieve a patient by their first or last name. This will search the first and last name for a matching string
     *
     * @param firstOrLastName the first/last name of a patient, not null
     * @return a list of patients that match the criteria
     */
    List<? extends IPatient> findPatientsByFirstNameOrLastName(String firstOrLastName);

    /**
     * Update a patient in the database
     *
     * @param patient the patient to be updated based on ID, not null
     * @return the updated patient or null if there was an error updating.
     */
    IPatient updatePatient(IPatient patient);

    /**
     * Retrieve a patient age classification based on its name.
     *
     * @param name the name of the age classification, not null
     * @return the patient age classification, or null if name is null
     */
    IPatientAgeClassification findPatientAgeClassification(String name);

    /**
     * Retrieve all patient age classifications and orders them in ascending sort order.
     *
     * @param isDeleted the isdeleted flag
     * @return list of all patient age classifications in ascending sort order or an empty list if none exist
     */
    List<? extends IPatientAgeClassification> findPatientAgeClassificationsOrderBySortOrder(boolean isDeleted);
}
