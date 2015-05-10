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

import femr.data.models.core.IChiefComplaint;
import femr.data.models.core.IPatientEncounter;

import java.util.List;

/**
 * A repository to cover the following tables:
 * <ul>
 * <li>patient_encounters</li>
 * <li>chief_complaints</li>
 * </ul>
 */
public interface IPatientEncounterRepository {

    /**
     * Create a new patient encounter
     *
     * @param patientEncounter the encounter to create
     * @return the newly created patient encounter or null if the save failed
     */
    IPatientEncounter create(IPatientEncounter patientEncounter);

    /**
     * Gets a patient encounter
     *
     * @param encounterId the id for the encounter to retrieve
     * @return the patient encounter or null if none found
     */
    IPatientEncounter findOneById(int encounterId);

    /**
     * Gets a patient's encounters sorted by most recent last
     *
     * @param patientId the patient to search for
     * @return a list of patient encounters
     */
    List<? extends IPatientEncounter> findByPatientIdOrderByDateOfTriageVisitAsc(int patientId);

    /**
     * Gets a patient's encounters sorted by most recent first
     *
     * @param patientId the patient to search for
     * @return a list of patient encounters or null if there was an error
     */
    List<? extends IPatientEncounter> findByPatientIdOrderByDateOfTriageVisitDesc(int patientId);

    /**
     * Upates a patient encounter
     *
     * @param patientEncounter the encounter and info to update
     * @return the updated patient encounter or null if there was an error updating
     */
    IPatientEncounter update(IPatientEncounter patientEncounter);

    /**
     * Create new Chief complaints in the database
     *
     * @param chiefComplaints the list of chief complaints to save
     * @return a list of newly created chief complaints or null if there was an error
     */
    List<? extends IChiefComplaint> createAllChiefComplaints(List<? extends IChiefComplaint> chiefComplaints);

    /**
     * Finds all the chief complaints for a patient encounter
     *
     * @param encounterId the id of the encounter
     * @return a list of chief complaints or null if there was an error
     */
    List<? extends IChiefComplaint> findAllChiefComplaintsByPatientEncounterId(int encounterId);

    /**
     * Finds all the chief complaints for a patient encounter, sorted by sort order
     *
     * @param encounterId the id of the encounter
     * @return a list of chief complaints or null if there was an error
     */
    List<? extends IChiefComplaint> findAllChiefComplaintsByPatientEncounterIdOrderBySortOrderAsc(int encounterId);

}
