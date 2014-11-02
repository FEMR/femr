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
import femr.common.models.MedicationItem;

import java.util.List;

public interface IInventoryService {

    /**
     * Gets all medicine that is currently not deleted
     *
     * @return list of MedicationItems for use by controller
     */
    ServiceResponse<List<MedicationItem>> getMedicationInventory();

    /**
     * Add a new medication to the inventory
     *
     * @param medicationItem the medication
     * @return
     */
    ServiceResponse<MedicationItem> createMedication(MedicationItem medicationItem);

    /**
     * Get a list of available units for the user
     */
    ServiceResponse<List<String>> getAvailableUnits();

    /**
     * Get a list of available forms for the user
     */
    ServiceResponse<List<String>> getAvailableForms();
}
