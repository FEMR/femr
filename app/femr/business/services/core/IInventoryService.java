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

public interface IInventoryService {

    /**
     * Retrieves a list of all medications in the system, including duplicates.
     *
     * @return a service response that contains a list of MedicationItems
     * and/or errors if they exist.
     */
    ServiceResponse<List<MedicationItem>> retrieveMedicationInventory();

    /**

     * Creates a new medication in the inventory.

     * Gets medicine that is currently not deleted, but paginated.
     * @param pageNum Page number to retrieve
     * @param rowsPerPage Rows per page
     * @param sorting List of sorts to apply to query
     * @param filters List of filters to apply to query
     * @return list of MedicationItems for use by controller
     */
    ServiceResponse<ObjectNode> getPaginatedMedicationInventory(int pageNum, int rowsPerPage, List<DataGridSorting> sorting, List<DataGridFilter> filters);

    /**
     * Add a new medication to the inventory

     *
     * @param medicationItem the medication TODO: separate this into parameters
     * @return a service response that contains a MedicationItem representing the medication that was just created
     * and/or errors if they exist.
     */
    ServiceResponse<MedicationItem> createMedication(MedicationItem medicationItem);

    /**
     * Retrieve a list of all available units for measuring.
     *
     * @return a service response that contains a list of strings that are the available units
     * and/or errors if they exist.
     */
    ServiceResponse<List<String>> retrieveAvailableUnits();

    /**
     * Retrieve a list of all available forms of medication
     *
     * @return a service response that contains a list of strings that are the available forms
     * and/or errors if they exist.
     */
    ServiceResponse<List<String>> retrieveAvailableForms();
}
