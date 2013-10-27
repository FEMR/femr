package femr.business.services;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IVital;
import femr.data.daos.IRepository;
import femr.data.models.Patient;

public class PharmacyService implements IPharmacyService {

    private IRepository<IPatient> patientRepository;
    private IRepository<IPatientEncounter> patientEncounterRepository;
    private IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private IRepository<IVital> vitalRepository;


    @Inject
    public PharmacyService(){

    }
    @Override
    public ServiceResponse<IPatient> findPatientById(int id) {
        ExpressionList<Patient> query = getPatientQuery().where().eq("id",id);
        IPatient savedPatient = patientRepository.findOne(query);

        ServiceResponse<IPatient> response = new ServiceResponse<>();
        if (savedPatient == null){
            response.addError("id","id does not exist");
        }
        else{
            response.setResponseObject(savedPatient);
        }
        return response;
    }

    @Override
    public ServiceResponse<IPatient> findPatientByName(String firstName, String lastName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ServiceResponse<IPatientEncounter> findPatientEncounterById(int id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }
}

