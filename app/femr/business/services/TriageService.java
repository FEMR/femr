package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.Patient;
import femr.data.models.PatientEncounter;
import femr.data.models.PatientEncounterVital;
import femr.data.models.Vital;
import femr.ui.models.data.PatientEncounterItem;
import femr.ui.models.data.PatientItem;
import femr.ui.models.data.VitalItem;
import femr.ui.models.triage.IndexViewModelPost;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
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
    private Provider<IPatient> patientProvider;
    private IRepository<IVital> vitalRepository;
    private Provider<IPatientEncounter> patientEncounterProvider;

    @Inject
    public TriageService(IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitaRepository,
                         Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                         IRepository<IVital> vitalRepository,
                         Provider<IVital> vitalProvider,
                         Provider<IPatient> patientProvider,
                         Provider<IPatientEncounter> patientEncounterProvider) {
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterVitalRepository = patientEncounterVitaRepository;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.vitalRepository = vitalRepository;
        this.vitalProvider = vitalProvider;
        this.patientProvider = patientProvider;
        this.patientEncounterProvider = patientEncounterProvider;
    }

    @Override
    public ServiceResponse<PatientItem> findPatientAndUpdateSex(int id, String sex) {
        ExpressionList<Patient> query = getPatientQuery().where().eq("id", id);
        IPatient savedPatient = patientRepository.findOne(query);
        //if a patient doesn't have a sex and the
        //user is trying to identify the patients sex
        if (StringUtils.isNullOrWhiteSpace(savedPatient.getSex()) && StringUtils.isNotNullOrWhiteSpace(sex)) {
            savedPatient.setSex(sex);
            savedPatient = patientRepository.update(savedPatient);
        }

        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (savedPatient == null) {
            response.addError("", "problem updating");
        } else {
            PatientItem patientItem = populatePatientItem(savedPatient);
            response.setResponseObject(patientItem);
        }
        return response;
    }

    /**
     * Gets vital items, but only the names
     *
     * @return Returns a list of all vitals without values
     */
    @Override
    public ServiceResponse<List<VitalItem>> findAllVitalItems() {
        List<? extends IVital> vitals = vitalRepository.findAll(Vital.class);
        List<VitalItem> vitalItems = new ArrayList<>();
        VitalItem vitalItem;
        for (IVital v : vitals) {
            vitalItem = new VitalItem();
            vitalItem.setName(v.getName());
            vitalItems.add(vitalItem);
        }
        ServiceResponse<List<VitalItem>> response = new ServiceResponse<>();
        if (vitals.size() > 0) {
            response.setResponseObject(vitalItems);
        } else {
            response.addError("vitals", "no vitals available");
        }
        return response;
    }

    @Override
    public ServiceResponse<PatientItem> createPatient(PatientItem patient) {
        IPatient newPatient = populatePatient(patient);

        newPatient = patientRepository.create(newPatient);

        ServiceResponse<PatientItem> response = new ServiceResponse<>();

        if (newPatient != null) {
            patient.setId(newPatient.getId());
            response.setResponseObject(patient);
        } else {
            response.addError("patient", "patient could not be saved to database");
        }

        return response;
    }

    @Override
    public ServiceResponse<PatientEncounterItem> createPatientEncounter(PatientEncounterItem patientEncounterItem) {
        IPatientEncounter newPatientEncounter = populatePatientEncounter(patientEncounterItem);
        newPatientEncounter = patientEncounterRepository.create(newPatientEncounter);

        ServiceResponse<PatientEncounterItem> response = new ServiceResponse<>();
        if (newPatientEncounter != null) {
            patientEncounterItem.setId(newPatientEncounter.getId());
            response.setResponseObject(patientEncounterItem);
        } else {
            response.addError("patient encounter", "patient encounter could not be saved to database");
        }
        return response;
    }

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

    @Override
    public ServiceResponse<List<VitalItem>> createPatientEncounterVitalItems(Map<String, Float> patientEncounterVitalMap, int userId, int encounterId) {
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
        VitalItem vitalItem;
        List<VitalItem> vitalItems = new ArrayList<>();
        for (IPatientEncounterVital pev : patientEncounterVitals) {
            vitalItem = new VitalItem();
            vitalItem.setName(pev.getVital().getName());
            vitalItem.setValue(pev.getVitalValue());
            vitalItems.add(vitalItem);
        }
        ServiceResponse<List<VitalItem>> response = new ServiceResponse<>();

        if (newPatientEncounterVitals != null) {
            response.setResponseObject(vitalItems);
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

    private IPatient populatePatient(PatientItem patient) {
        //create an IPatient from a PatientItem
        //everything except the ID
        IPatient newPatient = patientProvider.get();
        newPatient.setUserId(patient.getUserId());
        newPatient.setFirstName(patient.getFirstName());
        newPatient.setLastName(patient.getLastName());
        newPatient.setAge(patient.getBirth());
        newPatient.setSex(patient.getSex());
        newPatient.setAddress(patient.getAddress());
        newPatient.setCity(patient.getCity());
        newPatient.setPhotoId(patient.getPhotoId());

        return newPatient;
    }

    private PatientItem populatePatientItem(IPatient patient) {
        PatientItem patientItem = new PatientItem();
        patientItem.setAddress(patient.getAddress());
        patientItem.setBirth(patient.getAge());
        patientItem.setCity(patient.getCity());
        patientItem.setFirstName(patient.getFirstName());
        patientItem.setId(patient.getId());
        patientItem.setLastName(patient.getLastName());
        patientItem.setPhotoId(patient.getPhotoId());
        patientItem.setSex(patient.getSex());
        patientItem.setUserId(patient.getUserId());

        //also set path to photo?
        return patientItem;
    }

    private IPatientEncounter populatePatientEncounter(PatientEncounterItem patientEncounterItem) {
        IPatientEncounter patientEncounter = patientEncounterProvider.get();
        patientEncounter.setPatientId(patientEncounterItem.getPatientId());
        patientEncounter.setUserId(patientEncounterItem.getUserId());
        patientEncounter.setDateOfVisit(patientEncounterItem.getDateOfVisit());
        patientEncounter.setChiefComplaint(patientEncounterItem.getChiefComplaint());
        patientEncounter.setWeeksPregnant(patientEncounterItem.getWeeksPregnant());
        patientEncounter.setIsPregnant(patientEncounterItem.getIsPregnant());
        return patientEncounter;
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

    private Query<Vital> getVitalQuery() {
        return Ebean.find(Vital.class);
    }

}
