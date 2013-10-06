package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.data.daos.IRepository;
import femr.data.models.Patient;
import femr.data.models.PatientEncounter;

import java.util.List;

public class SearchService implements ISearchService{
    private IRepository<IPatient> patientRepository;
    private IRepository<IPatientEncounter> patientEncounterRepository;

    @Inject
    public SearchService(IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository){
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
    }

    @Override
    public ServiceResponse<IPatient> findPatientById(int id){
        ExpressionList<Patient> query = getQuery().where().eq("id",id);
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
    public ServiceResponse<IPatient> findPatientByName(String firstName, String lastName){
        ExpressionList<Patient> query = getQuery().where().eq("first_name",firstName).eq("last_name",lastName);
        IPatient savedPatient = patientRepository.findOne(query);

        ServiceResponse<IPatient> response = new ServiceResponse<>();
        if (savedPatient == null){
            response.addError("first name/last name","patient could not be found by name");
        }
        else{
            response.setResponseObject(savedPatient);
        }

        return response;
    }

    private Query<Patient> getQuery() {
        return Ebean.find(Patient.class);
    }

    @Override
    public List<? extends IPatientEncounter> findAllEncounters(){
        List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.findAll(PatientEncounter.class);
        return patientEncounters;
    }
}
