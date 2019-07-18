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
package femr.data.daos.system;

import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IResearchRepository;
import femr.data.models.core.research.IResearchEncounter;
import play.Logger;
import java.util.Collection;
import java.util.List;

public class ResearchRepository implements IResearchRepository {

    @Override
    public List<? extends IResearchEncounter> findAllEncountersForTripIds(Collection<Integer> tripIds) {

        try {

            return QueryProvider.getResearchEncounterQuery()
                    .where()
                    .in("missionTrip.id", tripIds)
                    .isNull("patient.isDeleted")
                    .isNull("isDeleted")
                    .orderBy()
                    .desc("date_of_triage_visit")
                    .findList();

        } catch(Exception ex) {

            Logger.error("ResearchRepository-findAllEncountersForTripIds", ex.getMessage());
            throw ex;
        }
    }
}
