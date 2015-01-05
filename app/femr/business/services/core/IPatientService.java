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
     * Finds all possible age classifications for a patient
     */
    ServiceResponse<Map<String,String>> findPossibleAgeClassifications();
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
     * Creates a new patient
     * @param patient patient to be created
     * @return patient with an assigned Id (pk)
     */
    ServiceResponse<PatientItem> createPatient(PatientItem patient);
}
