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

import femr.data.models.core.IChiefComplaint;

import java.util.List;

public interface IChiefComplaintRepository {

    /**
     * Create new Chief complaints in the database
     *
     * @param chiefComplaints the list of chief complaints to save
     * @return a list of newly created chief complaints or null if there was an error
     */
    List<? extends IChiefComplaint> createAll(List<? extends IChiefComplaint> chiefComplaints);

    /**
     * Finds all the chief complaints for a patient encounter
     *
     * @param encounterId the id of the encounter
     * @return a list of chief complaints or null if there was an error
     */
    List<? extends IChiefComplaint> findAllByPatientEncounterId(int encounterId);

    /**
     * Finds all the chief complaints for a patient encounter, sorted by sort order
     *
     * @param encounterId the id of the encounter
     * @return a list of chief complaints or null if there was an error
     */
    List<? extends IChiefComplaint> findAllByPatientEncounterIdOrderBySortOrderAsc(int encounterId);

}
