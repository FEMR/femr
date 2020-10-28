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
import femr.common.models.PatientEncounterItem;
import femr.common.models.*;
import femr.data.models.core.IPatientEncounter;
import femr.data.models.core.IPatientEncounterTabField;
import femr.data.models.mysql.PatientEncounterTabField;

import java.util.List;
import java.util.Map;

public interface IEncounterService {

    /**
     * Create a new patient encounter. Chief complaint sort order is the same as
     * the order they exist in the list.
     *
     * @param patientId id of the patient to create a new encounter for, not null
     * @param userId id of the user creating the new encounter, not null
     * @param tripId id of the current mission trip if it exists, may be null
     * @param ageClassification age classification of the patient if it exists (child, adult, etc), may be null
     * @param chiefComplaints a list of chief complaints that were entered for this patient, may be null or empty
     * @return a service response that contains a PatientEncounterItem representing the patient encounter that was created
     * and/or errors if they exist.
     */
    ServiceResponse<PatientEncounterItem> createPatientEncounter(int patientId, int userId, Integer tripId, String ageClassification, List<String> chiefComplaints);

    /**
     * Checks a patient into medical by updating the time of their visit and the user who saw them.
     *
     * @param encounterId current encounter id, not null
     * @param userId      id of the physician, not null
     * @return a service response that contains a PatientEncounterItem representing the patient encounter that was updated
     * and/or errors if they exist.
     */
    ServiceResponse<PatientEncounterItem> checkPatientInToMedical(int encounterId, int userId);

    /**
     * Checks a patient into pharmacy by updating the time of their visit and the user who saw them.
     *
     * @param encounterId current encounter id, not null
     * @param userId      id of the pharmacist, not null
     * @return a service response that contains an IPatientEncounter representing the patient encounter that was updated
     * and/or errors if they exist. TODO: remove the data model here
     */
    ServiceResponse<IPatientEncounter> checkPatientInToPharmacy(int encounterId, int userId);

    /**
     * Retrieves the physician that saw a patient in medical.
     *
     * @param encounterId id of the encounter, not null
     * @return a service response that contains the user and/or errors if they exist.
     */
    ServiceResponse<UserItem> retrievePhysicianThatCheckedInPatientToMedical(int encounterId);

    /**
     * Creates a bunch of tab fields that belong to a chief complaint
     *
     * @param tabFieldNameValues a mapping of tab field names to their respective values, not null/empty
     * @param encounterId the id of the encounter, not null
     * @param userId the id of the user creating the tab fields, not null
     * @param chiefComplaint the chief complaint name that the tab fields belong to, not null
     * @return a list of created tabfielditems
     */
    ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFields(Map<String, String> tabFieldNameValues, int encounterId, int userId, String chiefComplaint);

    /**
     * Creates a bunch of tab fields
     *
     * @param tabFieldNameValues a mapping of tab field names to their respective values, not null/empty
     * @param encounterId the id of the encounter, not null
     * @param userId the id of the user creating the tab fields, not null
     * @return a list of created tabfielditems
     */
    ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFields(Map<String, String> tabFieldNameValues, int encounterId, int userId);

    /**
     * Create a list of problems.
     *
     * @param problemValues each problem TODO: filter out empty/null values
     * @param encounterId id of the current encounter, not null
     * @param userId id of the user saving the problems, not null
     * @return a service response that contains a list of ProblemItems representing the problems that were created
     * and/or errors if they exist.
     */
    ServiceResponse<List<ProblemItem>> createProblems(List<String> problemValues, int encounterId, int userId);

    /**
     * Deletes a patient encounter
     *
     * @param deleteByUserID id of the user deleting the encounter, not null
     * @param reason reason that the user is deleting the encounter, may be null
     * @param encounterId id of the encounter to delete, not null
     * @return a service response that contains the deleted patient encounter item
     */
    ServiceResponse<PatientEncounterItem> deleteEncounter( int deleteByUserID, String reason,int encounterId);

    /**
     * Retrieves all problems.
     *
     * @param encounterId id of the encounter, not null
     * @return a service response that contains a list of ProblemItems that exist
     * and/or errors if they exist.
     */
    ServiceResponse<List<ProblemItem>> retrieveProblemItems(int encounterId);

    /**
     * Retrieves all pharmacy notes.
     *
     * @param encounterId id of the encounter, not null
     * @return a service response that contains a list of NoteItems that exist
     * and/or errors if they exist.
     */
    ServiceResponse<List<NoteItem>> retrieveNoteItems(int encounterId);

    /**
     * Marks that a patient was screened for diabetes during an encounter
     *
     * @param encounterId id of the encounter for the patient, not null
     * @param userId id of the physician that screened the patient for diabetes, not null
     * @param isScreened true if patient was screened for diabetes, false if user opted not to screen patient,
     * null if user was never prompted to screen patient for diabetes
     * @return updated patient encounter item and/or errors if they exist, or null if errors
     */
    ServiceResponse<PatientEncounterItem> screenPatientForDiabetes(int encounterId, int userId, Boolean isScreened);

    /**
     *Returns a list of PatientEncounters of the current day
     *@param tripID id of trip of current user, not null
     *@return List of PatientEncounterItems who were checked in on the current Day
     */
    ServiceResponse<List<PatientEncounterItem>> retrieveCurrentDayPatientEncounters(int tripID);

    /**
     * Deletes a problem that was submitted on the Medical page as part of a patient encounter
     *
     * @param encounterId id of the encounter, not null
     * @param problem the string of the problem itself, not null
     * @param userId id of the user deleting the problem, not null
     * @return true if deleting was successful or false if it did not succeed
     */
    ServiceResponse<Boolean> deleteExistingProblem(int encounterId, String problem, int userId);
}