package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;

import java.util.List;

public interface ISearchService {
    ServiceResponse<IPatient> findPatientById(int id);

    ServiceResponse<IPatient> findPatientByName(String firstName, String lastName);

    List<? extends IPatientEncounter> findAllEncountersByPatientId(int id);
}
