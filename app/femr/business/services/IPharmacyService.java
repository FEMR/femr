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

import femr.common.models.PrescriptionItem;
import femr.common.dto.ServiceResponse;
import femr.common.models.ProblemItem;
import femr.common.models.VitalItem;
import femr.data.models.IPatientEncounter;

import java.util.List;

public interface IPharmacyService {

    /**
     * Checks a patient into pharmacy (updates date_of_pharmacy_visit and identifies the user)
     *
     * @param encounterId current encounter
     * @param userId      id of the pharmacist
     * @return updated patient encounter
     */
    ServiceResponse<IPatientEncounter> checkPatientIn(int encounterId, int userId);

    /**
     * Create a new prescription and replace an old one with it
     *
     * @param prescriptionItem new prescription to replace the old one
     * @param oldScriptId      id of the old prescription that is being replaced
     * @return updated new prescription
     */
    ServiceResponse<PrescriptionItem> createAndReplacePrescription(PrescriptionItem prescriptionItem, int oldScriptId, int userId);

    /**
     * Find all problems
     *
     * @param encounterId id of the encounter
     * @return list of problems
     */
    ServiceResponse<List<ProblemItem>> findProblemItems(int encounterId);

    /**
     * Retrieve all medication names for typeahead
     *
     * @return
     */
    ServiceResponse<List<String>> findAllMedications();
}
