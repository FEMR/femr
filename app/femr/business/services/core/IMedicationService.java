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

import com.fasterxml.jackson.databind.node.ObjectNode;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationAdministrationItem;
import femr.common.models.MedicationItem;
import femr.common.models.PrescriptionItem;

import java.util.List;
import java.util.Map;

/**
 * Medication service is responsible for anything related to both medications and prescriptions.
 * It is not responsible for anything to do with the quantities being stored or dispensed (this is the role
 * of the Inventory service).
 */
public interface IMedicationService {

    /**
     * Creates a new prescription. It is assumed the prescription is not yet dispensed.
     *
     * @param medicationId id of the medication. If the medication is not in the concept dictionary, then it should be added prior to calling this. not null.
     * @param administrationId how the medication is administered (BID, etc), may be null.
     * @param encounterId id of the patient encounter, not null.
     * @param userId id of the user dispensing the medication, not null.
     * @param amount how much of the medication is dispensed, not null.
     * @param specialInstructions any special instructions for the prescription, may be null.
     * @return a PrescriptionItem representing the prescription that was just created and/or errors if they exist.
     */
    ServiceResponse<PrescriptionItem> createPrescription(int medicationId, Integer administrationId, int encounterId, int userId, Integer amount, String specialInstructions);

    /**
     * Creates a new prescription when the medication doesn't already exist in the inventory.
     * It is assumed the prescription is not yet dispensed. This will also create the medication so
     * there is no need to call createMedication() first.
     *
     * @param medicationName name of the medication being prescribed, not null
     * @param administrationId how the medication is administered (BID, etc), may be null.
     * @param encounterId id of the patient encounter, not null.
     * @param userId id of the user dispensing the medication, not null.
     * @param amount how much of the medication is dispensed, not null.
     * @param specialInstructions any special instructions for the prescription, may be null.
     * @return a PrescriptionItem representing the prescription that was just created and/or errors if they exist.
     */
    ServiceResponse<PrescriptionItem> createPrescriptionWithNewMedication(String medicationName, Integer administrationId, int encounterId, int userId, Integer amount, String specialInstructions);

    /**
     * Replace an existing prescription with an existing prescription. This will not update the inventory. This will not dispense the prescription.
     * This method does update the patient_prescription_replacements table (which infers the prescription has been replaced).
     *
     * @param prescriptionPairs A mapping of prescriptions to replace in the form <newPrescription, oldPrescription> neither of which are null.
     * @return a PrescriptionItem representing the prescriptions that replaced the old prescriptions and/or errors if they exist.
     */
    ServiceResponse<List<PrescriptionItem>> replacePrescriptions(Map<Integer, Integer> prescriptionPairs);

    /**
     * Dispense an existing prescription. This will not update the inventory. It does update the date dispensed and whether or not the patient
     * was counseled in the patient_prescriptions table.
     *
     * @param prescriptionsToDispense A mapping of prescriptions to dispense in the form <prescriptionId, isCounseled> neither of which are null.
     * @return a PrescriptionItem representing the dispensed prescription and/or errors if they exist.
     */
    ServiceResponse<List<PrescriptionItem>> dispensePrescriptions(Map<Integer, Boolean> prescriptionsToDispense);

    /**
     * Adds a new medication to the system. Does NOT update inventory quantities.
     *
     * @param name name of the medication, may be null
     * @param form form of the medication (e.g. caps/capsules), may be null
     * @param activeIngredients active ingredients in the medication, may be null
     * @return a service response that contains a MedicationItem representing the medication that was just created
     * and/or errors if they exist.
     */
    ServiceResponse<MedicationItem> createMedication(String name, String form, List<MedicationItem.ActiveIngredient> activeIngredients);

    /**
     * Retrieve a list of all available Administrations for medication
     *
     * @return a service response that contains a list of available Administrations that are available
     * and/or errors if they exist
     */
    ServiceResponse<List<MedicationAdministrationItem>> retrieveAvailableMedicationAdministrations();

    /**
     * Retrieve a list of all available forms of medication
     *
     * @return a service response that contains a list of strings that are the available forms
     * and/or errors if they exist.
     */
    ServiceResponse<List<String>> retrieveAvailableMedicationForms();

    /**
     * Retrieve a list of all available units for measuring.
     *
     * @return a service response that contains a list of strings that are the available units
     * and/or errors if they exist.
     */
    ServiceResponse<List<String>> retrieveAvailableMedicationUnits();

    /**
     * Retrieves a ObjectNode of all medications in the system
     *
     * @return a service response that contains a list of ObjectNode's
     * and/or errors if they exist
     */
    ServiceResponse<ObjectNode> retrieveAllMedicationsWithID(Integer tripId);
}
