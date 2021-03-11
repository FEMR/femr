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
import femr.util.DataStructure.Mapping.VitalMultiMap;

import java.util.List;
import java.util.Map;

public interface IVitalService {

    /**
     * Create all vitals for an encounter.
     *
     * @param patientEncounterVitalMap A <name,value> keypair of vitals to be created, not null
     * @param userId                   User creating the vitals, not null
     * @param encounterId              Encounter that the vitals are for, not null
     * @return a service response that contains a list of VitalItems that were created
     * and/or errors if they exist.
     */
    ServiceResponse<List<VitalItem>> createPatientEncounterVitalItems(Map<String, Float> patientEncounterVitalMap, int userId, int encounterId);

    /**
     * Create all vitals for an encounter. TODO: how is this different from createPatientEncounterVitalItems?
     *
     * @param patientEncounterVital A <name,value> keypair of vitals to be created, not null
     * @param userId                User creating the vitals, not null
     * @param encounterId           Encounter that the vitals are for, not null
     * @return a service response that contains a list of VitalItems that were created
     * and/or errors if they exist.
     */
    ServiceResponse<List<VitalItem>> createPatientEncounterVitals(Map<String, Float> patientEncounterVital, int userId, int encounterId);

    /**
     * Gets vital items, but only the names
     *
     * @return a service response that contains a list of VitalItems with no values
     * and/or errors if they exist.
     */
    ServiceResponse<List<VitalItem>> retrieveAllVitalItems();

    /**
     * Retrieve a map of vitals where the key is the date as well as the name
     *
     * @param encounterId the id of the encounter to get vitals for, not null
     * @return a service response that contains a VitalMultiMap
     * and/or errors if they exist.
     */
    ServiceResponse<VitalMultiMap> retrieveVitalMultiMap(int encounterId);
}
