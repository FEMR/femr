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

import femr.data.models.core.IPatientEncounter;
import java.util.List;

public interface IPatientEncounterRepository {

     /**
      * Create a new patient encounter
      *
      * @param patientEncounter the encounter to create
      * @return the newly created patient encounter
      */
     IPatientEncounter create(IPatientEncounter patientEncounter);

     /**
      * Gets a patient encounter
      *
      * @param encounterId the id for the encounter to retrieve
      * @return the patient encounter
      */
     IPatientEncounter findOneById(int encounterId);

     /**
      * Gets a patient's encounters sorted by most recent last
      *
      * @param patientId the patient to search for
      * @return a list of patient encounters
      */
     List<? extends IPatientEncounter> findByPatientIdOrderByDateOfTriageVisitAsc(int patientId);

     /**
      * Gets a patient's encounters sorted by most recent first
      *
      * @param patientId the patient to search for
      * @return a list of patient encounters
      */
     List<? extends IPatientEncounter> findByPatientIdOrderByDateOfTriageVisitDesc(int patientId);

     /**
      * Upates a patient encounter
      *
      * @param patientEncounter the encounter and info to update
      * @return the updated patient encounter
      */
     IPatientEncounter update(IPatientEncounter patientEncounter);
}
