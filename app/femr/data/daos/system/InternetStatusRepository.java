package femr.data.daos.system;

import femr.data.daos.core.IInternetStatusRepository;
import femr.data.models.mysql.InternetStatus;
import io.ebean.Ebean;
import io.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.helpers.QueryProvider;
import femr.data.models.core.*;
import play.Logger;

public class InternetStatusRepository implements IInternetStatusRepository {
  private final Provider<IInternetStatus> internetStatusProvider;

  @Inject
  public InternetStatusRepository(Provider<IInternetStatus> internetStatusProvider){

    this.internetStatusProvider = internetStatusProvider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IInternetStatus retrieveInternetStatus(){

    IInternetStatus response = null;
    try {

      Query<InternetStatus> internetStatusList = QueryProvider.getInternetStatusQuery()
              .select("status");

      response = internetStatusList.findOne();
    } catch (Exception ex) {

      Logger.error("InternetStatusRepository-retrieveInternetStatus", ex.getMessage());
      throw ex;
    }

    return response;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IInternetStatus updateInternetStatus(IInternetStatus status) {

    try {

      Ebean.save(status);
    } catch (Exception ex) {

      Logger.error("InternetStatusRepository-updateInternetStatus", ex.getMessage());
      throw ex;
    }

    return status;
  }
}
