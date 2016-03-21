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
import femr.common.models.PatientItem;

import java.util.Map;

public interface IPatientService {

    /**
     * Retrieves all possible age classifications for a patient.
     *
     * @return a service response that contains a map that maps age classifications to their respective desriptions
     * and/or errors if they exist.
     */
    ServiceResponse<Map<String,String>> retrieveAgeClassifications();

    /**
     * Updates a patients sex if that patient does not previously have one assigned. If sex is null then it just gets and
     * returns the patient.
     *
     * @param id the id of the patient, not null
     * @param sex the sex of the patient, may be null
     * @return a service response that contains a PatientItem representing the patient that was updated
     * and/or errors if they exist.
     */
    ServiceResponse<PatientItem> updateSex(int id, String sex);

    /**
     * Creates a new patient.
     *
     * @param patient patient to be created. TODO: separate this into parameters
     * @return a service response that contains a PatientItem representing the patient that was created
     * and/or errors if they exist.
     */
    ServiceResponse<PatientItem> createPatient(PatientItem patient, ISearchService searchService, int check);

    /**
     * Soft deletes a patient
     * @return a service response that contains a PatientItem representing that the patient was deleted
     * and/or errors if they exist.
     */
    ServiceResponse<PatientItem> deletePatient(int patientId, int deleteByUserID);
}
