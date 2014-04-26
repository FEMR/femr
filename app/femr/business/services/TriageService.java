package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.ui.controllers.routes;
import femr.ui.models.data.PatientEncounterItem;
import femr.ui.models.data.PatientItem;
import femr.ui.models.data.VitalItem;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Triage Service has been refactored alongside the Triage Controller
 * in order to be exposed to UI elements and decrease the amount
 * of business logic being performed in the Triage Controller.
 */
public class TriageService implements ITriageService {

    //repositories
    private IRepository<IPatient> patientRepository;
    private IRepository<IPatientEncounter> patientEncounterRepository;
    private IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private IRepository<IVital> vitalRepository;
    private IRepository<IPhoto> photoRepository;
    //providers
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private Provider<IVital> vitalProvider;
    private Provider<IPatient> patientProvider;
    private Provider<IPatientEncounter> patientEncounterProvider;

    @Inject
    public TriageService(IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitaRepository,
                         IRepository<IVital> vitalRepository,
                         IRepository<IPhoto> photoRepository,
                         Provider<IPatientEncounterVital> patientEncounterVitalProvider,
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
        this.photoRepository = photoRepository;
    }

    /**
     * {@inheritDoc}
     */
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
     * {@inheritDoc}
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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



    /**
     *  {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> findPatientItemById(Integer id) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (id == null) {
            response.addError("id", "null");
            return response;
        }

        ExpressionList<Patient> query = getPatientQuery().where().eq("id", id);
        IPatient savedPatient = patientRepository.findOne(query);

        if (savedPatient == null) {
            response.addError("id", "id does not exist");
        } else {
            PatientItem patientItem = new PatientItem();

            ExpressionList<Photo> photoQuery = Ebean.find(Photo.class).where().eq("id", savedPatient.getId());
            IPhoto savedPhoto = photoRepository.findOne(photoQuery);
            if (savedPhoto != null) {
                patientItem.setPathToPhoto(routes.PhotoController.GetPatientPhoto(id, false).toString());
            } else {
                patientItem.setPathToPhoto("");
            }
            patientItem.setId(savedPatient.getId());
            patientItem.setFirstName(savedPatient.getFirstName());
            patientItem.setLastName(savedPatient.getLastName());
            patientItem.setAddress(savedPatient.getAddress());
            patientItem.setCity(savedPatient.getCity());
            patientItem.setAge(dateUtils.getAge(savedPatient.getAge()));
            patientItem.setBirth(savedPatient.getAge());
            patientItem.setSex(savedPatient.getSex());
            patientItem.setPhotoId(savedPatient.getPhotoId());

            response.setResponseObject(patientItem);
        }
        return response;
    }

    //region **ui <-> data converters**
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
    //endregion

    //region **Query providers**
    private Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }
    private Query<Vital> getVitalQuery() {
        return Ebean.find(Vital.class);
    }
    //endregion

}
