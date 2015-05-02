package femr.data.daos.core;

import femr.data.models.core.IPatient;

/**
 * Created by kevin on 5/2/15.
 */
public interface IPatientRepository {
    IPatient findPatientById(int id);
}
