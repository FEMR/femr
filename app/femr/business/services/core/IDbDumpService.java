package femr.business.services.core;

import femr.common.dtos.ServiceResponse;

public interface IDbDumpService {
    ServiceResponse<Boolean> getAllData();
}
