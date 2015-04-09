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
     * Create a new prescription that replaces an old one.
     *
     * @param prescriptionItem new prescription to replace the old one, not null TODO: separate into parameters
     * @param oldScriptId      id of the old prescription that is being replaced
     * @return a service response that contains a PrescriptionItem that was created
     * and/or errors if they exist.
     */
    ServiceResponse<PrescriptionItem> createAndReplacePrescription(PrescriptionItem prescriptionItem, int oldScriptId, int userId, boolean isCounseled);

    /**
     * Creates multiple new prescriptions.
     *
     * @param prescriptions     prescriptions, not null, greater than 0
     * @param userId            id of the user creating the prescriptions, not null
     * @param encounterId       id of the encounter, not null
     * @param isDispensed       true if the prescription was dispensed, not null
     * @param isCounseled       true if the patient was counseled about the prescription, not null
     * @return a service response that contains a list of updated PrescriptionItems that were created
     * and/or errors if they exist.
     */
    ServiceResponse<List<PrescriptionItem>> createPatientPrescriptions(List<PrescriptionItem> prescriptions, int userId, int encounterId, boolean isDispensed, boolean isCounseled);

    /**
     * Flags a prescription to say that it was filled.
     *
     * @param prescriptionIds a list of prescription ids to identify as filled
     * @return a service response that contains a list of updated PrescriptionItems that were filled
     * and/or errors if they exist.
     */
    ServiceResponse<List<PrescriptionItem>> flagPrescriptionsAsFilled(List<Integer> prescriptionIds);

    /**
     * Flags a prescription to say that the patient was counseled before dispensing it.
     *
     * @param prescriptionIds a list of prescription ids to identify as counseled
     * @return a service response that contains a list of updated PrescriptionItems that were flagged
     * and/or errors if they exist.
     */
    ServiceResponse<List<PrescriptionItem>> flagPrescriptionsAsCounseled(List<Integer> prescriptionIds);

    /**
     * Retrieves a list of all medications in the system, excluding duplicates.
     *
     * @return a service response that contains a list of Strings
     * and/or errors if they exist.
     */
    ServiceResponse<List<String>> retrieveAllMedications();
}
