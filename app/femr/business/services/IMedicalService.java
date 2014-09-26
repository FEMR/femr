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
import femr.common.models.*;
import femr.data.models.IUser;

import java.util.List;
import java.util.Map;

public interface IMedicalService {

    /**
     * Creates all patient encounter vitals
     *
     * @param patientEncounterVital list of vitals for saving
     * @param userId                id of the user saving the vitals
     * @param encounterId           id of the current encounter
     * @return vitals that were saved
     */
    ServiceResponse<List<VitalItem>> createPatientEncounterVitals(Map<String, Float> patientEncounterVital, int userId, int encounterId);

    /**
     * Gets the physician that saw a patient in medical.
     *
     * @param encounterId id of the encounter to check
     * @return the physician or null
     */
    ServiceResponse<UserItem> getPhysicianThatCheckedInPatient(int encounterId);

    /**
     * creates multiple prescriptions
     *
     * @param prescriptionItems list of prescription items
     * @param userId            id of the user saving the prescriptions
     * @param encounterId       id of the current encounter
     * @return updated prescription list
     */
    ServiceResponse<List<PrescriptionItem>> createPatientPrescriptions(List<PrescriptionItem> prescriptionItems, int userId, int encounterId);

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
     * Checks a patient into medical (updates date_of_medical_visit and the user checking them in)
     *
     * @param encounterId current encounter id
     * @param userId      id of the physician
     * @return updated patient encounter
     */
    ServiceResponse<PatientEncounterItem> checkPatientIn(int encounterId, int userId);

    /**
     * Finds non-custom current field values for medical tabs
     *
     * @param encounterId current encounter id
     * @return Mapping of the field name to the fielditem
     */
    ServiceResponse<Map<String, TabFieldItem>> findCurrentTabFieldsByEncounterId(int encounterId);

    /**
     * Gets all available custom tabs for the medical page
     *
     * @return list of tabs
     */
    ServiceResponse<List<TabItem>> getCustomTabs();

    /**
     * Matches a list of TabFieldItems to their respective tab
     *
     * @param encounterId current encounter id
     * @return TabFieldItems mapped to their respective tab
     */
    ServiceResponse<Map<String, List<TabFieldItem>>> getCustomFields(int encounterId);
}
