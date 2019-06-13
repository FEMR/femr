package femr.data.daos.core;

import femr.data.models.core.research.IResearchEncounter;

import java.util.Collection;
import java.util.List;

public interface IResearchRepository {

    List<? extends IResearchEncounter> findAllEncountersForTripIds(Collection<Integer> tripIds);
}
