package femr.data.daos.core;

import femr.data.models.core.*;

public interface IInternetStatusRepository {

  /**
   * Retrieve a patient age classification from the database by its name.
   *
   * @return the patient age classification or NULL if not found or if errors occur
   */
  IInternetStatus retrieveInternetStatus();

  /**
   *  Creates OR Updates a patient. If you send an existing patient, this will update their
   *  record. (existing = available id)
   *
   *  @param  patient data object to save
   *  @return the new or updated patient
   **/
  IInternetStatus updateInternetStatus(IInternetStatus status);
}
