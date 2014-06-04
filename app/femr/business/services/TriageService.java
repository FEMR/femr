package femr.business.services;

import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.common.dto.ServiceResponse;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.common.models.VitalItem;
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
    private final IRepository<IPatient> patientRepository;
    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<IVital> vitalRepository;
    private final Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private final DomainMapper domainMapper;

    @Inject
    public TriageService(IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitaRepository,
                         IRepository<IVital> vitalRepository,
                         Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                         DomainMapper domainMapper) {
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterVitalRepository = patientEncounterVitaRepository;
        this.vitalRepository = vitalRepository;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.domainMapper = domainMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> findPatientAndUpdateSex(int id, String sex) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (id < 1) {
            response.addError("", "patient id can not be less than 1");
            return response;
        }

        ExpressionList<Patient> query = QueryProvider.getPatientQuery()
                .where()
                .eq("id", id);

        try {
            IPatient savedPatient = patientRepository.findOne(query);
            //if a patient doesn't have a sex and the
            //user is trying to identify the patients sex
            if (StringUtils.isNullOrWhiteSpace(savedPatient.getSex()) && StringUtils.isNotNullOrWhiteSpace(sex)) {
                savedPatient.setSex(sex);
                savedPatient = patientRepository.update(savedPatient);
            }
            PatientItem patientItem = domainMapper.createPatientItem(savedPatient, null);
            response.setResponseObject(patientItem);

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<VitalItem>> findAllVitalItems() {
        ServiceResponse<List<VitalItem>> response = new ServiceResponse<>();

        try {
            List<? extends IVital> vitals = vitalRepository.findAll(Vital.class);
            List<VitalItem> vitalItems = new ArrayList<>();
            for (IVital v : vitals) {
                vitalItems.add(domainMapper.createVitalItem(v));
            }
            response.setResponseObject(vitalItems);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> createPatient(PatientItem patient) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (patient == null) {
            response.addError("", "no patient received");
            return response;
        }

        try {
            IPatient newPatient = domainMapper.createPatient(patient);
            newPatient = patientRepository.create(newPatient);
            response.setResponseObject(domainMapper.createPatientItem(newPatient, null));
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientEncounterItem> createPatientEncounter(PatientEncounterItem patientEncounterItem) {
        ServiceResponse<PatientEncounterItem> response = new ServiceResponse<>();
        if (patientEncounterItem == null) {
            response.addError("", "no patient encounter item specified");
            return response;
        }

        try {
            IPatientEncounter newPatientEncounter = domainMapper.createPatientEncounter(patientEncounterItem, patientEncounterItem.getUserId());
            newPatientEncounter = patientEncounterRepository.create(newPatientEncounter);
            response.setResponseObject(domainMapper.createPatientEncounterItem(newPatientEncounter, false));
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<VitalItem>> createPatientEncounterVitalItems(Map<String, Float> patientEncounterVitalMap, int userId, int encounterId) {
        ServiceResponse<List<VitalItem>> response = new ServiceResponse<>();
        if (patientEncounterVitalMap == null || userId < 1 || encounterId < 1) {
            response.addError("", "bad parameters");
            return response;
        }

        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();
        IPatientEncounterVital patientEncounterVital;
        IVital vital;

        ExpressionList<Vital> query;
        String currentTime = dateUtils.getCurrentDateTimeString();

        try {


            for (String key : patientEncounterVitalMap.keySet()) {
                if (patientEncounterVitalMap.get(key) != null) {

                    query = QueryProvider.getVitalQuery().where().eq("name", key);
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
            List<VitalItem> vitalItems = new ArrayList<>();
            for (IPatientEncounterVital pev : patientEncounterVitals) {
                vitalItems.add(domainMapper.createVitalItem(pev));
            }

            response.setResponseObject(vitalItems);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

}
