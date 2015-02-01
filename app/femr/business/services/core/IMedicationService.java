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
import femr.common.models.PrescriptionItem;

import java.util.List;

public interface IMedicationService {

    /**
     * Get a JSON string representing medication names
     *
     * @return JSON object in the form of { medication# : "name" }
     */
    ServiceResponse<List<String>> getMedicationNames();

    /**
     * Create a new prescription and replace an old one with it
     *
     * @param prescriptionItem new prescription to replace the old one
     * @param oldScriptId      id of the old prescription that is being replaced
     * @return updated new prescription
     */
    ServiceResponse<PrescriptionItem> createAndReplacePrescription(PrescriptionItem prescriptionItem, int oldScriptId, int userId, boolean isCounseled);

    /**
     * creates multiple prescriptions
     *
     * @param prescriptionItems list of prescription items
     * @param userId            id of the user saving the prescriptions
     * @param encounterId       id of the current encounter
     * @return updated prescription list
     */
    ServiceResponse<List<PrescriptionItem>> createPatientPrescriptions(List<PrescriptionItem> prescriptionItems, int userId, int encounterId, boolean isDispensed, boolean isCounseled);

    /**
     * Mark prescriptions as filled
     *
     * @param prescriptionIds a list of prescription ids to fill
     * @return prescription items that were filled
     */
    ServiceResponse<List<PrescriptionItem>> markPrescriptionsAsFilled(List<Integer> prescriptionIds);

    /**
     * Mark prescriptions as having the patient counseled
     *
     * @param prescriptionIds a list of prescription ids to identify as counseled
     * @return prescription items that were marked as counseled
     */
    ServiceResponse<List<PrescriptionItem>> markPrescriptionsAsCounseled(List<Integer> prescriptionIds);

    /**
     * Retrieve all medication names for typeahead
     *
     * @return
     */
    ServiceResponse<List<String>> findAllMedications();
}
