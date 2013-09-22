package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;

public interface ITriageService {
    ServiceResponse<IPatient> createPatient(IPatient patient);

}
