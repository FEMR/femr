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
import femr.data.models.mysql.Patient;

import java.util.Date;
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
     * Updates a patient's address with the address value. If address is null then no action is taken.
     * This method will not remove a patient's address
     *
     * @param id the id of the patient, not null
     * @param address the address of the patient, may be null
     * @return a service response that contains a PatientItem representing the patient that was updated
     * and/or errors if they exist.
     */
    ServiceResponse<PatientItem> updatePatientAddress(int id, String address);

    /**
     * Updates a patient's age with the age value. If age is null then no action is taken.
     * This method will not remove a patient's age
     *
     * @param id id of the patient, not null
     * @param age age of the patient, may be null
     * @return the updated patient item
     */
    ServiceResponse<PatientItem> updatePatientAge(int id, Date age);

    /**
     * Updates the patient's phone number with the phoneNumber value. If phoneNumber is null then no action is taken.
     * This method will not remove a patient's phone number
     *
     * @param id id of the patient, not null
     * @param phoneNumber phone number of the patient, may be null
     * @return the updated patient item
     */
    ServiceResponse<PatientItem> updatePatientPhoneNumber(int id, String phoneNumber);

    /**
     * Updates the patient's sex with whatever value is provided in sex. If sex is null then no action is taken.
     * This method will not remove a patient's sex
     *
     * @param id id of the patient, not null
     * @param sex sex of the patient,  may be null
     * @return the updated patient item
     */
    ServiceResponse<PatientItem> updatePatientSex(int id, String sex);

    /**
     * Creates a new patient.
     *
     * @param patient patient to be created. TODO: separate this into parameters
     * @return a service response that contains a PatientItem representing the patient that was created
     * and/or errors if they exist.
     */
    ServiceResponse<PatientItem> createPatient(PatientItem patient);

    /**
     * Deletes a patient from the system. This doesn't actually remove the patient from the database,
     * just flags them as deleted.
     *
     * @param patientId id of the patient to delete, not null
     * @param deleteByUserID id of the user that is deleting the patient, not null
     * @param reason the reason for deleting the patient, may be null
     * @return the deleted patient and/or errors if they exist
     */
    ServiceResponse<PatientItem> deletePatient(int patientId, int deleteByUserID, String reason);
}
