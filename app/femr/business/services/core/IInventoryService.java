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
     * Creates a new medication in the inventory.
     * Gets medicine that is currently not deleted, but paginated.
     *
     * @param pageNum     Page number to retrieve
     * @param rowsPerPage Rows per page
     * @param sorting     List of sorts to apply to query
     * @param filters     List of filters to apply to query
     * @return list of MedicationItems for use by controller
     */
    ServiceResponse<ObjectNode> getPaginatedMedicationInventory(int pageNum, int rowsPerPage, List<DataGridSorting> sorting, List<DataGridFilter> filters);

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

    ServiceResponse<MedicationItem> setQuantityCurrent(int medicationId, int tripId, int quantityCurrent);
    */

    /**
     * Subtracts quantity from the current quantity when someone dispenses medication.
     *
     * @param medicationId id of the medication.
     * @param tripId id of the trip that is bringing the medication.
     * @param quantity amount of medication to subtract from the current amount available.
     * @return a medication item that contains quantity information.
     */
    ServiceResponse<MedicationItem> subtractFromQuantityCurrent(int medicationId, int tripId, int quantityToSubtract);

}
