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
import femr.common.models.MedicationItem;
import femr.ui.models.admin.inventory.DataGridFilter;
import femr.ui.models.admin.inventory.DataGridSorting;

import java.util.List;

/**
 * Inventory service is responsible for maintaining and tracking the medication inventory for a team.
 */
public interface IInventoryService {

    /**
     * Sets the total number of a medication in the inventory. If the total number has not yet been set, then it will
     * also set the current quantity to the total quantity (assumes this is a new entry and all the medications are
     * available).
     *
     * @param medicationId id of the medication.
     * @param tripId id of the trip that is bringing the medication.
     * @param quantityTotal amount of the medication being brought.
     * @return a medication item that contains quantity information.
     */
    ServiceResponse<MedicationItem> setQuantityTotal(int medicationId, int tripId, int quantityTotal);

    /**
     * Sets the current number of medications existing in an inventory. This can be used by someone to correct a
     * displayed amount that is off.
     *
     * @param medicationId id of the medication.
     * @param tripId id of the trip that has the medication.
     * @param quantityCurrent amount of the medication currently in the inventory.
     * @return a medication item that contains quantity information.
**/
    ServiceResponse<MedicationItem> setQuantityCurrent(int medicationId, int tripId, int quantityCurrent);

    /**
     * Deletes inventory medication by medication/tripId.
     *
     * @param medicationId id of the medication.
     * @param tripId id of the trip that has the medication.
     * @return
     **/
    ServiceResponse<MedicationItem> deleteInventoryMedication(int medicationId, int tripId);
    /**
     * Subtracts quantity from the current quantity when someone dispenses medication.
     * Subtracts amount dispensed from the current quantity of a medication when someone dispenses a prescription. This
     * will also make sure inventory exists for the medication in a trip before trying to subtract the quantity.
     *
     * @param medicationId id of the medication.
     * @param tripId id of the trip that is bringing the medication.
     * @param quantityToSubtract amount of medication to subtract from the current amount available.
     * @return a medication item that contains quantity information. The quantity information will be null if the inventory
     * did not exist for that medication.
     */
    ServiceResponse<MedicationItem> subtractFromQuantityCurrent(int medicationId, int tripId, int quantityToSubtract);

}
