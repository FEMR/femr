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
                    .fetch("patient")
                    .fetch("patientPrescriptions")
                    .fetch("patientPrescriptions.medication")
                    .fetch("encounterVitals")
                    .fetch("encounterVitals.vital")
                    .fetch("missionTrip")
                    .fetch("missionTrip.missionTeam")
                    .fetch("missionTrip.missionCity")
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
