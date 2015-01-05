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
import femr.common.models.VitalItem;

import java.util.List;
import java.util.Map;

public interface IVitalService {

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
     * Gets vital items, but only the names
     *
     * @return Returns a list of all vitals without values
     */
    ServiceResponse<List<VitalItem>> findAllVitalItems();

    /**
     * Create all vitals for an encounter
     *
     * @param patientEncounterVitalMap A <name,value> keypair of vitals to be created
     * @param userId User creating the vitals
     * @param encounterId Encounter that the vitals are for
     * @return List of vitals that were created
     */
    ServiceResponse<List<VitalItem>> createPatientEncounterVitalItems(Map<String,Float> patientEncounterVitalMap, int userId, int encounterId);
}
