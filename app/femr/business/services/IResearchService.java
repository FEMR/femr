package femr.business.services;
/**
 * This is the research service interface.
 */
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;

import java.util.List;

public interface IResearchService {
    ServiceResponse<List<? extends IPatient>> findAllPatient();  // returns a list of all the patients so we can narrow it down
    //TODO-RESEARCH: Declare the services
}
