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
import femr.common.models.IPhoto;
import femr.data.daos.IRepository;
import femr.data.models.Patient;
import femr.data.models.PatientEncounter;
import femr.data.models.PatientEncounterVital;

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
    public ServiceResponse<IPatient> setPhotoId(int id, int photoId)
    {
        ServiceResponse<IPatient> response = new ServiceResponse<>();
        ExpressionList<Patient> query = getPatientQuery().where().eq("id", id);
        IPatient savedPatient = patientRepository.findOne(query);
        if (savedPatient == null){
            response.addError("patient","does not exist");
            return response;
        }
        savedPatient.setPhotoId(photoId);
        savedPatient = patientRepository.update(savedPatient);
        if (savedPatient.getPhotoId() != photoId){
            response.addError("updating","error updating patient photo id");
        }
        response.setResponseObject(savedPatient);
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

    @Override
    public ServiceResponse<String> getDateOfTriageCheckIn(int encounterId) {
        ExpressionList<PatientEncounter> query1 = getPatientEncounter().where().eq("id", encounterId);
        List<? extends IPatientEncounter> patientEncounter = patientEncounterRepository.find(query1);

        ExpressionList<PatientEncounterVital> query2 = getPatientEncounterVital().where().eq("patient_encounter_id", encounterId);
        List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(query2);

        ServiceResponse<String> response = new ServiceResponse<>();

        if (patientEncounter.size() > 0) {
            response.setResponseObject(patientEncounter.get(0).getDateOfVisit());
        } else if (patientEncounterVitals.size() > 0) {
            response.setResponseObject(patientEncounterVitals.get(0).getDateTaken());
        } else {
            response.addError("values", "That patient has no triage record");
        }
        return response;
    }

    private Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }

    private Query<PatientEncounter> getPatientEncounter() {
        return Ebean.find(PatientEncounter.class);
    }

    private Query<PatientEncounterVital> getPatientEncounterVital() {
        return Ebean.find(PatientEncounterVital.class);
    }


}
