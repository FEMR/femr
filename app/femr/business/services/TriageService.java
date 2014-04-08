package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.Patient;
import femr.data.models.Vital;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TriageService implements ITriageService {

    private IRepository<IPatient> patientRepository;
    private IRepository<IPatientEncounter> patientEncounterRepository;
    private IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private Provider<IVital> vitalProvider;
    private IRepository<IVital> vitalRepository;

    @Inject
    public TriageService(IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitaRepository,
                         Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                         IRepository<IVital> vitalRepository,
                         Provider<IVital> vitalProvider) {
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterVitalRepository = patientEncounterVitaRepository;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.vitalRepository = vitalRepository;
        this.vitalProvider = vitalProvider;
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
    public ServiceResponse<IPatient> setPhotoId(int id, int photoId) {
        ServiceResponse<IPatient> response = new ServiceResponse<>();
        ExpressionList<Patient> query = getPatientQuery().where().eq("id", id);
        IPatient savedPatient = patientRepository.findOne(query);
        if (savedPatient == null) {
            response.addError("patient", "does not exist");
            return response;
        }
        savedPatient.setPhotoId(photoId);
        savedPatient = patientRepository.update(savedPatient);
        if (savedPatient.getPhotoId() != photoId) {
            response.addError("updating", "error updating patient photo id");
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

//    @Override
//    public ServiceResponse<IPatientEncounterVital> createPatientEncounterVital(IPatientEncounterVital patientEncounterVital) {
//        IPatientEncounterVital newPatientEncounterVital = patientEncounterVitalRepository.create(patientEncounterVital);
//        ServiceResponse<IPatientEncounterVital> response = new ServiceResponse<>();
//
//        if (newPatientEncounterVital != null) {
//            response.setResponseObject(newPatientEncounterVital);
//        } else {
//            response.addError("patient encounter vital", "patient encounter vital could not be saved to database");
//        }
//        return response;
//    }

    //    @Override
//    public ServiceResponse<List<? extends IPatientEncounterVital>> createPatientEncounterVitals(List<? extends IPatientEncounterVital> patientEncounterVitals) {
//        List<? extends IPatientEncounterVital> newPatientEncounterVitals = patientEncounterVitalRepository.createAll(patientEncounterVitals);
//        ServiceResponse<List<? extends IPatientEncounterVital>> response = new ServiceResponse<>();
//
//        if (newPatientEncounterVitals != null) {
//            response.setResponseObject(newPatientEncounterVitals);
//        } else {
//            response.addError("", "patient encounter vitals could not be saved to database");
//        }
//        return response;
//    }
    @Override
    public ServiceResponse<List<? extends IPatientEncounterVital>> createPatientEncounterVitals(Map<String, Float> patientEncounterVitalMap, int userId, int encounterId) {
        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();
        IPatientEncounterVital patientEncounterVital;
        IVital vital;

        ExpressionList<Vital> query;
        String currentTime = dateUtils.getCurrentDateTimeString();

        for (String key : patientEncounterVitalMap.keySet()) {
            if (patientEncounterVitalMap.get(key) != null) {
                query = getVitalQuery().where().eq("name", key);
                vital = vitalRepository.findOne(query);

                patientEncounterVital = patientEncounterVitalProvider.get();
                patientEncounterVital.setPatientEncounterId(encounterId);
                patientEncounterVital.setUserId(userId);
                patientEncounterVital.setDateTaken(currentTime);
                patientEncounterVital.setVital(vital);
                patientEncounterVital.setVitalValue(patientEncounterVitalMap.get(key));
                patientEncounterVitals.add(patientEncounterVital);
            }
        }

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

    private Query<Vital> getVitalQuery() {
        return Ebean.find(Vital.class);
    }


}
