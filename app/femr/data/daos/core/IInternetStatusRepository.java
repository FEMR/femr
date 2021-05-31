package femr.data.daos.core;

import femr.data.models.core.*;

public interface IInternetStatusRepository {

  /**
   * Retrieve the internet status.
   *
   * @return the internet status flag or NULL if not found or if errors occur
   */
  IInternetStatus retrieveInternetStatus();

  /**
   *  Updates the internet status flag whether there is a good enough internet
   *  connection or not to update
   *
   *  @param  status status flag to update to
   *  @return the new or updated status
   **/
  IInternetStatus updateInternetStatus(IInternetStatus status);
}
