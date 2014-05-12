package femr.business.services;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.DomainMapper;
import femr.business.QueryProvider;
import femr.business.dtos.*;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;

import java.util.*;

public class MedicalService implements IMedicalService {

    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<ITabField> tabFieldRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;
    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private final IRepository<IVital> vitalRepository;
    private final IRepository<ITab> customTabRepository;
    private final IRepository<IUser> userRepository;
    private final Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private final DomainMapper domainMapper;

    @Inject
    public MedicalService(IRepository<IPatientEncounter> patientEncounterRepository,
                          IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                          IRepository<ITabField> tabFieldRepository,
                          IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository,
                          IRepository<IPatientPrescription> patientPrescriptionRepository,
                          IRepository<IVital> vitalRepository,
                          IRepository<ITab> customTabRepository,
                          IRepository<IUser> userRepository,
                          Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                          DomainMapper domainMapper) {

        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.tabFieldRepository = tabFieldRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.vitalRepository = vitalRepository;
        this.customTabRepository = customTabRepository;
        this.userRepository = userRepository;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.domainMapper = domainMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> hasPatientBeenCheckedInByPhysician(int encounterId) {
        ServiceResponse<Boolean> response = new ServiceResponse<>();
        if (encounterId < 1) {
            response.addError("", "encounter id must be greater than 0");
            return response;
        }
        try {
            ExpressionList<PatientEncounterTabField> patientEncounterTabFieldQuery = QueryProvider.getPatientEncounterTabFieldQuery()
                    .where()
                    .eq("patient_encounter_id", encounterId);
            List<? extends IPatientEncounterTabField> patientEncounterTabFields = patientEncounterTabFieldRepository.find(patientEncounterTabFieldQuery);


            ExpressionList<PatientPrescription> prescriptionQuery = QueryProvider.getPatientPrescriptionQuery()
                    .where()
                    .eq("encounter_id", encounterId);
            List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(prescriptionQuery);

            if (patientEncounterTabFields.size() > 0 || patientPrescriptions.size() > 0) {
                response.setResponseObject(true);
            }

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PrescriptionItem>> createPatientPrescriptions(List<PrescriptionItem> prescriptionItems, int userId, int encounterId) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();
        if (prescriptionItems == null || userId < 1 || encounterId < 1) {
            response.addError("", "invalid parameters");
            return response;
        }

        List<IPatientPrescription> patientPrescriptions = new ArrayList<>();
        for (PrescriptionItem pi : prescriptionItems) {
            patientPrescriptions.add(domainMapper.createPatientPrescription(pi, userId, encounterId, null));
        }

        try {
            List<? extends IPatientPrescription> newPatientPrescriptions = patientPrescriptionRepository.createAll(patientPrescriptions);
            List<PrescriptionItem> newPrescriptionItems = new ArrayList<>();
            for (IPatientPrescription pp : newPatientPrescriptions) {
                newPrescriptionItems.add(domainMapper.createPatientPrescriptionItem(pp));
            }
            response.setResponseObject(newPrescriptionItems);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Map<String, TabFieldItem>> findCurrentTabFieldsByEncounterId(int encounterId) {
        ServiceResponse<Map<String, TabFieldItem>> response = new ServiceResponse<>();
        if (encounterId < 1) {
            response.addError("", "encounter id invalid");
            return response;
        }
        Map<String, TabFieldItem> fieldValueMap = new LinkedHashMap<>();
        Query<PatientEncounterTabField> query = QueryProvider.getPatientEncounterTabFieldQuery()
                .where()
                .eq("patient_encounter_id", encounterId)
                .order()
                .desc("date_taken");
        try {
            List<? extends IPatientEncounterTabField> patientEncounterTabFields = patientEncounterTabFieldRepository.find(query);
            for (IPatientEncounterTabField petf : patientEncounterTabFields) {
                //since the fields were sorted by date in the query, this if statement
                //ensures that only the up-to-date field values are inserted into the map
                if (!fieldValueMap.containsKey(petf.getTabField().getName())) {
                    fieldValueMap.put(petf.getTabField().getName(), domainMapper.createTabFieldItem(petf));
                }
            }
            response.setResponseObject(fieldValueMap);

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFields(List<TabFieldItem> tabFieldItems, int encounterId, int userId) {
        ServiceResponse<List<TabFieldItem>> response = new ServiceResponse<>();

        //list of values to insert into database
        List<IPatientEncounterTabField> tabFields = new ArrayList<>();
        try {
            for (TabFieldItem tf : tabFieldItems) {
                ExpressionList<TabField> query = QueryProvider.getTabFieldQuery()
                        .where()
                        .eq("name", tf.getName());
                ITabField tabField = tabFieldRepository.findOne(query);


                IPatientEncounterTabField patientEncounterTabField = domainMapper.createPatientEncounterTabField(tabField, userId, tf.getValue());


                ExpressionList<PatientEncounterTabField> query2 = QueryProvider.getPatientEncounterTabFieldQuery()
                        .where()
                        .eq("tabField", tabField)
                        .eq("patient_encounter_id", encounterId)
                        .eq("tab_field_value", tf.getValue());
                if (patientEncounterTabFieldRepository.findOne(query2) != null) {
                    //already exists - field wasn't changed
                } else {
                    tabFields.add(patientEncounterTabField);
                }
            }
            List<? extends IPatientEncounterTabField> savedTabFields = patientEncounterTabFieldRepository.createAll(tabFields);
            List<TabFieldItem> tabFieldItemsToReturn = new ArrayList<>();
            for (IPatientEncounterTabField petf : savedTabFields) {
                tabFieldItemsToReturn.add(domainMapper.createTabFieldItem(petf));
            }
            response.setResponseObject(tabFieldItemsToReturn);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Map<String, List<TabFieldItem>>> getCustomFields(int encounterId) {
        ServiceResponse<Map<String, List<TabFieldItem>>> response = new ServiceResponse<>();
        if (encounterId < 1) {
            response.addError("", "encounterId must be greater than 0");
            return response;
        }
        Map<String, List<TabFieldItem>> customFieldMap = new HashMap<>();
        ExpressionList<Tab> query = QueryProvider.getTabQuery()
                .where()
                .eq("isDeleted", false);
        try {
            //O(n^2) because who gives a fuck
            List<? extends ITab> customTabs = customTabRepository.find(query);
            for (ITab ct : customTabs) {
                Query<TabField> query2 = QueryProvider.getTabFieldQuery()
                        .fetch("tab")
                        .where()
                        .eq("isDeleted", false)
                        .eq("tab.name", ct.getName())
                        .order()
                        .asc("sort_order");


                List<? extends ITabField> customFields = tabFieldRepository.find(query2);
                List<TabFieldItem> customFieldItems = new ArrayList<>();
                for (ITabField cf : customFields) {
                    Query<PatientEncounterTabField> query3 = QueryProvider.getPatientEncounterTabFieldQuery()
                            .where()
                            .eq("tabField", cf)//somethings fucky
                            .eq("patient_encounter_id", encounterId)
                            .order()
                            .desc("date_taken");

                    List<? extends IPatientEncounterTabField> patientEncounterCustomField = patientEncounterTabFieldRepository.find(query3);
                    if (patientEncounterCustomField != null && patientEncounterCustomField.size() > 0) {
                        customFieldItems.add(domainMapper.createTabFieldItem(patientEncounterCustomField.get(0)));
                    } else {
                        customFieldItems.add(domainMapper.createTabFieldItem(cf));
                    }


                }
                customFieldMap.put(ct.getName(), customFieldItems);

            }
            response.setResponseObject(customFieldMap);
        } catch (Exception ex) {
            response.addError("", "error");
            return response;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<VitalItem>> createPatientEncounterVitals(Map<String, Float> patientEncounterVitalMap, int userId, int encounterId) {
        ServiceResponse<List<VitalItem>> response = new ServiceResponse<>();
        if (patientEncounterVitalMap == null || userId < 1 || encounterId < 1) {
            response.addError("", "invalid parameters");
            return response;
        }
        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();
        IPatientEncounterVital patientEncounterVital;
        IVital vital;

        ExpressionList<Vital> query;
        String currentTime = dateUtils.getCurrentDateTimeString();

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

        try {
            List<VitalItem> vitalItems = new ArrayList<>();
            List<? extends IPatientEncounterVital> newPatientEncounterVitals = patientEncounterVitalRepository.createAll(patientEncounterVitals);
            for (IPatientEncounterVital pev : newPatientEncounterVitals) {
                vitalItems.add(domainMapper.createVitalItem(pev));
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
    public ServiceResponse<List<TabItem>> getCustomTabs() {
        ServiceResponse<List<TabItem>> response = new ServiceResponse<>();
        ExpressionList<Tab> query = QueryProvider.getTabQuery()
                .where()
                .eq("isDeleted", false);
        try {
            List<? extends ITab> customTabs = customTabRepository.find(query);
            List<TabItem> customTabNames = new ArrayList<>();

            for (ITab t : customTabs) {
                customTabNames.add(domainMapper.createTabItem(t));
            }
            response.setResponseObject(customTabNames);
        } catch (Exception ex) {
            response.addError("", "error");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientEncounterItem> checkPatientIn(int encounterId, int userId) {
        ServiceResponse<PatientEncounterItem> response = new ServiceResponse<>();
        if (encounterId < 1) {
            response.addError("", "encounterId must be greater than 0");
            return response;
        }

        ExpressionList<PatientEncounter> query = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("id", encounterId);

        try {
            IPatientEncounter patientEncounter = patientEncounterRepository.findOne(query);
            patientEncounter.setDateOfMedicalVisit(DateTime.now());
            ExpressionList<User> getUserQuery = QueryProvider.getUserQuery()
                    .where()
                    .eq("id", userId);
            IUser user = userRepository.findOne(getUserQuery);
            patientEncounter.setDoctor(user);

            patientEncounter = patientEncounterRepository.update(patientEncounter);


            response.setResponseObject(domainMapper.createPatientEncounterItem(patientEncounter, false));
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }
}
