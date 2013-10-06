package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;

import java.util.List;

public interface ISearchService {
    ServiceResponse<IPatient> findPatientById(String id);

    List<? extends IPatientEncounter> findAllEncounters();
}
