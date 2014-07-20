package femr.business.services;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.common.dto.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.util.DataStructure.VitalMultiMap;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.*;

public class SearchService implements ISearchService {
    private final IRepository<IMedication> medicationRepository;
    private final IRepository<IPatient> patientRepository;
    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;
    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private final IRepository<ISystemSetting> systemSettingRepository;
    private final IRepository<IVital> vitalRepository;
    private final DomainMapper domainMapper;

    @Inject
    public SearchService(IRepository<IMedication> medicationRepository,
                         IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository,
                         IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                         IRepository<IPatientPrescription> patientPrescriptionRepository,
                         IRepository<IVital> vitalRepository,
                         IRepository<ISystemSetting> systemSettingRepository,
                         DomainMapper domainMapper) {

        this.medicationRepository = medicationRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.vitalRepository = vitalRepository;
        this.systemSettingRepository = systemSettingRepository;
        this.domainMapper = domainMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PatientItem>> getPatientsFromQueryString(String patientSearchQuery) {
        ServiceResponse<List<PatientItem>> response = new ServiceResponse<>();
        if (StringUtils.isNullOrWhiteSpace(patientSearchQuery)) {
            response.addError("", "bad parameters");
            return response;
        }

        String[] words = patientSearchQuery.trim().split(" ");
        Integer id = null;
        String firstName = null;
        String lastName = null;
        String firstOrLastName = null;
        if (words.length == 0) {
            //nothing was in the query
            response.addError("", "query string empty");
            return response;
        } else if (words.length == 1) {
            //could be an ID or a name
            try {
                //see if it is a number
                id = Integer.parseInt(words[0]);
            } catch (NumberFormatException ex) {
                //see if it it a string
                firstOrLastName = words[0];
            }
        } else if (words.length == 2) {
            firstName = words[0];
            lastName = words[1];
        } else {
            response.addError("", "too many words in query string");
            return response;
        }


        //Build the Query
        Query<Patient> query = null;
        if (id != null) {
            //if we have an id, that is all we need.
            //this is the most ideal scenario
            query = QueryProvider.getPatientQuery()
                    .where()
                    .eq("id", id)
                    .order()
                    .desc("id");

        } else if (StringUtils.isNotNullOrWhiteSpace(firstName) && StringUtils.isNotNullOrWhiteSpace(lastName)) {
            //if we have a first and last name
            //this is the second most ideal scenario
            query = QueryProvider.getPatientQuery()
                    .where()
                    .eq("first_name", firstName)
                    .eq("last_name", lastName)
                    .order()
                    .desc("id");

        } else if (StringUtils.isNotNullOrWhiteSpace(firstOrLastName)) {
            //if we have a word that could either be a first name or a last name
            query = QueryProvider.getPatientQuery()
                    .where()
                    .or(
                            Expr.eq("first_name", firstOrLastName),
                            Expr.eq("last_name", firstOrLastName))
                    .order()
                    .desc("id");

        } else {
            response.addError("", "too many parameters in query");
        }

        //Execute the query
        try {
            List<? extends IPatient> patients = patientRepository.find(query);
            List<PatientItem> patientItems = new ArrayList<>();
            for (IPatient p : patients) {
                patientItems.add(domainMapper.createPatientItem(p, null));
            }
            response.setResponseObject(patientItems);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;
    }

    //change this to return a List<IPatientEncounterVital
    @Override
    public ServiceResponse<List<? extends IPatientEncounterVital>> findPatientEncounterVitals(int encounterId, String name) {
        Query<PatientEncounterVital> query = QueryProvider.getPatientEncounterVitalQuery()
                .fetch("vital")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("vital.name", name)
                .order().desc("date_taken");

        List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(query);
        ServiceResponse<List<? extends IPatientEncounterVital>> response = new ServiceResponse<>();
        if (patientEncounterVitals.size() > 0) {
            response.setResponseObject(patientEncounterVitals);
        } else {
            response.addError("patientEncounterVital", "could not find vital");
        }
        return response;
    }


    @Override
    public ServiceResponse<List<PatientEncounterItem>> findPatientEncounterItemsById(int patientId) {
        ServiceResponse<List<PatientEncounterItem>> response = new ServiceResponse<>();
        Query<PatientEncounter> query = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("patient_id", patientId)
                .order()
                .desc("date_of_visit");
        try {
            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.find(query);
            List<PatientEncounterItem> patientEncounterItems = new ArrayList<>();
            for (IPatientEncounter pe : patientEncounters) {
                patientEncounterItems.add(domainMapper.createPatientEncounterItem(pe, null));
            }
            response.setResponseObject(patientEncounterItems);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;
    }


    @Override
    public ServiceResponse<List<? extends IVital>> findAllVitals() {
        List<? extends IVital> vitals = vitalRepository.findAll(Vital.class);
        ServiceResponse<List<? extends IVital>> response = new ServiceResponse<>();
        if (vitals.size() > 0) {
            response.setResponseObject(vitals);
        } else {
            response.addError("vitals", "no vitals available");
        }
        return response;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<VitalMultiMap> getVitalMultiMap(int encounterId) {
        List<? extends IVital> vitals = findAllVitals().getResponseObject();
        VitalMultiMap vitalMultiMap = new VitalMultiMap();
        String vitalFieldName;
        String vitalFieldDate;
        ServiceResponse<List<? extends IPatientEncounterVital>> patientVitalServiceResponse;

        for (int vitalFieldIndex = 0; vitalFieldIndex < vitals.size(); vitalFieldIndex++) {
            vitalFieldName = vitals.get(vitalFieldIndex).getName().trim();
            patientVitalServiceResponse = findPatientEncounterVitals(encounterId, vitalFieldName);

            if (patientVitalServiceResponse.hasErrors()) {
                continue;
            } else {
                for (IPatientEncounterVital vitalData : patientVitalServiceResponse.getResponseObject()) {
                    vitalFieldDate = vitalData.getDateTaken().trim();
                    vitalMultiMap.put(vitalFieldName, vitalFieldDate, vitalData.getVitalValue());
                }
            }
        }
        ServiceResponse<VitalMultiMap> response = new ServiceResponse<>();
        response.setResponseObject(vitalMultiMap);
        return response;
    }

    /**
     * Finds a prescription by its ID so we can find the name of the replacement meds
     *
     * @param id The id of the medication to find
     * @return the response object
     */
    public ServiceResponse<IPatientPrescription> findPatientPrescriptionById(int id) {
        ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery().where().eq("id", id);
        IPatientPrescription patientPrescription = patientPrescriptionRepository.findOne(query);
        ServiceResponse<IPatientPrescription> response = new ServiceResponse<>();
        if (patientPrescription == null) {
            response.addError("patientPrescription", "could not find a prescription with that id");
        } else {
            response.setResponseObject(patientPrescription);
        }
        return response;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientEncounterItem> findPatientEncounterItemById(int patientId) {
        ServiceResponse<PatientEncounterItem> response = new ServiceResponse<>();
        if (patientId < 1) {
            response.addError("", "Invalid patient ID.");
            return response;
        }
        Query<PatientEncounter> query = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("patient_id", patientId)
                .order()
                .asc("date_of_visit");
        try {
            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.find(query);
            if (patientEncounters.size() < 1) {
                response.addError("", "That patient does not exist.");
                return response;
            }
            IPatientEncounter currentPatientEncounter = patientEncounters.get(patientEncounters.size() - 1);

            DateTime dateOfMedicalVisit = currentPatientEncounter.getDateOfMedicalVisit();
            DateTime dateOfPharmacyVisit = currentPatientEncounter.getDateOfPharmacyVisit();
            Boolean isClosed = false;
            DateTime dateNow = dateUtils.getCurrentDateTime();

            if (dateOfPharmacyVisit != null) {
                isClosed = true;

            } else if (dateOfMedicalVisit != null) {
                //give 1 day before closing
                DateTime dayAfterMedicalVisit = dateOfMedicalVisit.plusDays(1);
                if (dateNow.isAfter(dayAfterMedicalVisit)) {
                    isClosed = true;
                }

            } else {
                //give 2 days before closing
                DateTime dayAfterAfterEncounter = currentPatientEncounter.getDateOfVisit().plusDays(2);
                if (dateNow.isAfter(dayAfterAfterEncounter)) {
                    isClosed = true;
                }
            }


            PatientEncounterItem patientEncounterItem = domainMapper.createPatientEncounterItem(currentPatientEncounter, isClosed);
            response.setResponseObject(patientEncounterItem);

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> findPatientItemById(Integer id) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (id == null || id < 0) {
            response.addError("", "id can not be null or less than 1");
            return response;
        }

        //get patient encounters so we can use the newest one
        Query<PatientEncounter> peQuery = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("patient_id", id)
                .order()
                .desc("date_of_visit");
        try {
            //IPatient savedPatient = patientRepository.findOne(query);
            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.find(peQuery);
            if (patientEncounters.size() < 1) throw new Exception();

            IPatientEncounter recentEncounter = patientEncounters.get(0);
            IPatient savedPatient = patientEncounters.get(0).getPatient();

            PatientItem patientItem = domainMapper.createPatientItem(savedPatient, recentEncounter.getWeeksPregnant());

            if (savedPatient.getPhoto() != null) {
                patientItem.setPathToPhoto("/photo/patient/" + id + "?showDefault=false");
            }

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
    public ServiceResponse<List<PrescriptionItem>> findUnreplacedPrescriptionItems(int encounterId) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();
        ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery()
                .where()
                .eq("encounter_id", encounterId)
                .eq("replacement_id", null);
        try {
            List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(query);
            List<PrescriptionItem> prescriptionItems = new ArrayList<>();

            for (IPatientPrescription pp : patientPrescriptions) {
                PrescriptionItem prescriptionItem = new PrescriptionItem();
                prescriptionItem.setName(pp.getMedicationName());
                prescriptionItem.setId(pp.getId());
                prescriptionItems.add(prescriptionItem);
            }

            response.setResponseObject(prescriptionItems);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<ProblemItem>> findProblemItems(int encounterId) {
        ServiceResponse<List<ProblemItem>> response = new ServiceResponse<>();
        List<ProblemItem> problemItems = new ArrayList<>();
        Query<PatientEncounterTabField> query = QueryProvider.getPatientEncounterTabFieldQuery()
                .fetch("tabField")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("tabField.name", "problem")
                .order()
                .asc("date_taken");

        try {
            List<? extends IPatientEncounterTabField> patientEncounterTreatmentFields = patientEncounterTabFieldRepository.find(query);
            if (patientEncounterTreatmentFields == null) {
                response.addError("", "bad query");
            } else {
                for (IPatientEncounterTabField petf : patientEncounterTreatmentFields) {
                    problemItems.add(domainMapper.createProblemItem(petf));
                }
                response.setResponseObject(problemItems);
            }
        } catch (Exception ex) {
            response.addError("", "error");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Integer> parseIdFromQueryString(String query) {
        ServiceResponse<Integer> response = new ServiceResponse<>();

        if (StringUtils.isNullOrWhiteSpace(query)) {
            response.addError("", "No patient ID provided.");
        } else {
            try {
                query = query.trim();
                Integer id = Integer.parseInt(query);
                response.setResponseObject(id);
            } catch (NumberFormatException ex) {
                response.addError("", "Could not read patient ID.");
            }

        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PrescriptionItem>> findAllPrescriptionItemsByEncounterId(int encounterId) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();
        if (encounterId < 1) {
            response.addError("", "invalid parameters");
            return response;
        }

        ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery()
                .where()
                .eq("encounter_id", encounterId);

        try {
            List<PrescriptionItem> prescriptionItems = new ArrayList<>();
            List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(query);
            for (IPatientPrescription pp : patientPrescriptions) {
                prescriptionItems.add(domainMapper.createPatientPrescriptionItem(pp));
            }
            response.setResponseObject(prescriptionItems);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    public ServiceResponse<SettingItem> getSystemSettings() {
        ServiceResponse<SettingItem> response = new ServiceResponse<>();
        try {
            List<? extends ISystemSetting> systemSettings = systemSettingRepository.findAll(SystemSetting.class);
            SettingItem settingItem = new SettingItem();
            if (systemSettings == null || systemSettings.size() == 0) {
                response.addError("", "error");
            } else {
                //uhhh lol
                if (systemSettings.get(0).isActive()) {
                    settingItem.setMultipleChiefComplaint(true);
                } else {
                    settingItem.setMultipleChiefComplaint(false);
                }

                if (systemSettings.get(1).isActive()) {
                    settingItem.setPmhTab(true);
                } else {
                    settingItem.setPmhTab(false);
                }

                if (systemSettings.get(2).isActive()) {
                    settingItem.setPhotoTab(true);
                } else {
                    settingItem.setPhotoTab(false);
                }
            }
            response.setResponseObject(settingItem);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;
    }
}
