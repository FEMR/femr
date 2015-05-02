package femr.data.daos.system;

import femr.data.daos.Repository;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.IPatient;

public class PatientRepository extends Repository<IPatient> implements IPatientRepository {
    @Override
    public IPatient findPatientById(int id) {

    }
}
