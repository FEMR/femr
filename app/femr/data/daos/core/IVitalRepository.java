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
package femr.data.daos.core;

import femr.data.models.core.IPatientEncounterVital;
import femr.data.models.core.IVital;

import java.util.List;

/**
 * A repository to cover the following tables:
 * <ul>
 *     <li>vitals</li>
 *     <li>patient_encounter_vitals</li>
 * </ul>
 */
public interface IVitalRepository {

    /**
     * Creates patientEncounterVitals (the values)
     *
     * @param patientEncounterVitals a list of values to save
     * @return the patient encounter vitals that were saved or null if none exist
     */
    List<? extends IPatientEncounterVital> createAll(List<? extends IPatientEncounterVital> patientEncounterVitals);

    /**
     * Find saved vitals by encounter id
     *
     * @param encounterId id of the encounter, not null
     * @return a list of saved patient encounter vitals in reverse chronological order or null if none exist
     */
    List<? extends IPatientEncounterVital> find(int encounterId);

    /**
     * Finds all available vitals
     *
     * @return a list of available vitals
     */
    List<? extends IVital> findAll();

    /**
     * Finds a vital by name
     *
     * @param name the name of the vital
     * @return returns the vital or null if none exist
     */
    IVital findByName(String name);

    /**
     * Finds height feet vitals for an encounter
     * @param encounterId the id of the encounter, not null
     * @return a list of height feet vitals for an encounter in reverse chronological order
     */
    List<? extends IPatientEncounterVital> findHeightFeetValues(int encounterId);

    /**
     * Finds height inches vitals for an encounter
     * @param encounterId the id of the encounter, not null
     * @return a list of height inches vitals for an encounter in reverse chronological order
     */
    List<? extends IPatientEncounterVital> findHeightInchesValues(int encounterId);

    /**
     * Finds weight vitals for an encounter
     * @param encounterId the id of the encounter, not null
     * @return a list of weight vitals for an encounter in reverse chronological order
     */
    List<? extends IPatientEncounterVital> findWeightValues(int encounterId);

    /**
     * Finds the heightFeet vital
     *
     * @return the heightFeet vital or null if it doesn't exist
     */
    IVital findHeightFeet();

    /**
     * Finds the heightInches vital
     *
     * @return the heightInches vital or null if it doesn't exist
     */
    IVital findHeightInches();
}
