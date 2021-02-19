package femr.business.services.system;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.services.core.IInternetStatusService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.InternetStatusItem;
import femr.data.daos.core.IInternetStatusRepository;
import femr.data.models.core.*;
import play.Logger;

public class InternetStatusService implements IInternetStatusService {
  private final IInternetStatusRepository internetStatusRepository;
  private final IItemModelMapper itemModelMapper;

  @Inject
  public InternetStatusService(IInternetStatusRepository internetStatusRepository,
                        @Named("identified") IItemModelMapper itemModelMapper) {

    this.internetStatusRepository = internetStatusRepository;
    this.itemModelMapper = itemModelMapper;
  }

  /**
   * {@inheritDoc}
   */
  public ServiceResponse<InternetStatusItem>  retrieveInternetStatus() {

    ServiceResponse<InternetStatusItem>  response = new ServiceResponse<>();

    try {

      IInternetStatus status = internetStatusRepository.retrieveInternetStatus();
      InternetStatusItem internetStatusItem;
      internetStatusItem = itemModelMapper.createInternetStatusItem(status);
      response.setResponseObject(internetStatusItem);
    } catch (Exception ex) {

      Logger.error("InternetStatusService-retrieveInternetStatus", ex);
      response.addError("", ex.getMessage());
    }
    return response;
  }

  public ServiceResponse<InternetStatusItem> updateInternetStatus(boolean flag) {
    ServiceResponse<InternetStatusItem> response = new ServiceResponse<>();

    try {
      IInternetStatus internetStatus = internetStatusRepository.retrieveInternetStatus();
      internetStatus.setStatus(flag);

      internetStatus = internetStatusRepository.updateInternetStatus(internetStatus);
      response.setResponseObject(itemModelMapper.createInternetStatusItem(internetStatus));
    } catch (Exception ex) {
      response.addError("", ex.getMessage());
    }
    return response;
  }

}
