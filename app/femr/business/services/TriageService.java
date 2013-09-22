package femr.business.services;

import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.data.daos.IRepository;

public class TriageService implements ITriageService {

    private IRepository<IPatient> patientRepository;

    @Inject
    public TriageService(IRepository<IPatient> patientRepository){
        this.patientRepository = patientRepository;
    }

    @Override
    public ServiceResponse<IPatient> createPatient(IPatient patient) {
        patientRepository.create(patient);
        return null;
    }
}
