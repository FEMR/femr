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
import femr.common.models.ProblemItem;
import femr.common.models.TabFieldItem;
import femr.common.models.UserItem;
import femr.data.models.core.IPatientEncounter;

import java.util.List;
import java.util.Map;

public interface IEncounterService {

    /**
     * Create a patient encounter
     *
     * @param patientEncounterItem the patient encounter
     * @return the patient encounter with id (pk)
     */
    ServiceResponse<PatientEncounterItem> createPatientEncounter(PatientEncounterItem patientEncounterItem);

    /**
     * Checks a patient into medical (updates date_of_medical_visit and the user checking them in)
     *
     * @param encounterId current encounter id
     * @param userId      id of the physician
     * @return updated patient encounter
     */
    ServiceResponse<PatientEncounterItem> checkPatientInToMedical(int encounterId, int userId);

    /**
     * Checks a patient into pharmacy (updates date_of_pharmacy_visit and identifies the user)
     *
     * @param encounterId current encounter
     * @param userId      id of the pharmacist
     * @return updated patient encounter
     */
    ServiceResponse<IPatientEncounter> checkPatientInToPharmacy(int encounterId, int userId);

    /**
     * Gets the physician that saw a patient in medical.
     *
     * @param encounterId id of the encounter to check
     * @return the physician or null
     */
    ServiceResponse<UserItem> getPhysicianThatCheckedInPatientToMedical(int encounterId);

    /**
     * Finds non-custom current field values for medical tabs. Current means the field
     * currently has a value.
     *
     * @param encounterId current encounter id
     * @return Mapping of the field name to the fielditem
     */
    ServiceResponse<Map<String, TabFieldItem>> findCurrentTabFieldsByEncounterId(int encounterId);

    /**
     * Adds tab field items to the PatientEncounterTabField table
     *
     * @param tabFieldItems list of fields to be saved
     * @param encounterId   id of the current encounter
     * @param userId        id of the user saving the fields
     * @return updated list of items
     */
    ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFields(List<TabFieldItem> tabFieldItems, int encounterId, int userId);

    /**
     * Find all problems
     *
     * @param encounterId id of the encounter
     * @return list of problems
     */
    ServiceResponse<List<ProblemItem>> findProblemItems(int encounterId);

}
