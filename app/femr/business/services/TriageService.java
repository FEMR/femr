package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.data.daos.IRepository;
import femr.data.models.Patient;

import java.util.List;

public class TriageService implements ITriageService {

    private IRepository<IPatient> patientRepository;
    private IRepository<IPatientEncounter> patientEncounterRepository;
    private IRepository<IPatientEncounterVital> patientEncounterVitalRepository;

    @Inject
    public TriageService(IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitaRepository) {
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterVitalRepository = patientEncounterVitaRepository;
    }

    @Override
    public ServiceResponse<IPatient> createPatient(IPatient patient) {
        IPatient newPatient = patientRepository.create(patient);
        ServiceResponse<IPatient> response = new ServiceResponse<>();

        if (newPatient != null) {
            response.setResponseObject(newPatient);
        } else {
            response.addError("patient", "patient could not be saved to database");
        }

        return response;
    }

    @Override
    public ServiceResponse<IPatient> updatePatient(IPatient patient) {
        patient = patientRepository.update(patient);
        ServiceResponse<IPatient> response = new ServiceResponse<>();
        if (patient == null) {
            response.addError("", "problem updating");
        } else {
            response.setResponseObject(patient);
        }
        return response;
    }

    @Override
    public ServiceResponse<IPatientEncounter> createPatientEncounter(IPatientEncounter patientEncounter) {
        IPatientEncounter newPatientEncounter = patientEncounterRepository.create(patientEncounter);
        ServiceResponse<IPatientEncounter> response = new ServiceResponse<>();

        if (newPatientEncounter != null) {
            response.setResponseObject(newPatientEncounter);
        } else {
            response.addError("patient encounter", "patient encounter could not be saved to database");
        }
        return response;
    }

    @Override
    public ServiceResponse<IPatientEncounterVital> createPatientEncounterVital(IPatientEncounterVital patientEncounterVital) {
        IPatientEncounterVital newPatientEncounterVital = patientEncounterVitalRepository.create(patientEncounterVital);
        ServiceResponse<IPatientEncounterVital> response = new ServiceResponse<>();

        if (newPatientEncounterVital != null) {
            response.setResponseObject(newPatientEncounterVital);
        } else {
            response.addError("patient encounter vital", "patient encounter vital could not be saved to database");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPatientEncounterVital>> createPatientEncounterVitals(List<? extends IPatientEncounterVital> patientEncounterVitals) {
        List<? extends IPatientEncounterVital> newPatientEncounterVitals = patientEncounterVitalRepository.createAll(patientEncounterVitals);
        ServiceResponse<List<? extends IPatientEncounterVital>> response = new ServiceResponse<>();

        if (newPatientEncounterVitals != null) {
            response.setResponseObject(newPatientEncounterVitals);
        } else {
            response.addError("", "patient encounter vitals could not be saved to database");
        }
        return response;
    }

    private Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }
}
