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
import femr.common.models.MedicationItem;

import java.util.List;

public interface IConceptService {

    /**
     * Retrieves a list of all medications in the concept dictionary that are
     * not deleted
     * @return MedicationItem containing all of the medications in the concept dictionary.
     */
    ServiceResponse<List<MedicationItem>> retrieveAllMedicationConcepts();

    /**
     * Retreives a medication in the concept dictionary.
     *
     * @param conceptMedicationID id of the concept medication to retrieve
     * @return MedicationItem with the id provided or null if doesn't exist/error
     */
    ServiceResponse<MedicationItem> retrieveConceptMedication(int conceptMedicationID);
}
