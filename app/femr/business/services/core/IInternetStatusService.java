package femr.business.services.core;

import femr.common.dtos.ServiceResponse;
import femr.common.models.InternetStatusItem;
import femr.data.models.mysql.InternetStatus;

public interface IInternetStatusService {
  ServiceResponse<InternetStatusItem> retrieveInternetStatus();
  ServiceResponse<InternetStatusItem> updateInternetStatus(boolean flag);
}
