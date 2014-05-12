package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.DomainMapper;
import femr.business.QueryProvider;
import femr.business.dtos.*;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.ui.controllers.routes;
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
    private final IRepository<IVital> vitalRepository;
    private final IRepository<IPhoto> photoRepository;
    private final DomainMapper domainMapper;

    @Inject
    public SearchService(IRepository<IMedication> medicationRepository,
                         IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository,
                         IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                         IRepository<IPatientPrescription> patientPrescriptionRepository,
                         IRepository<IVital> vitalRepository,
                         IRepository<IPhoto> photoRepository,
                         DomainMapper domainMapper) {

        this.medicationRepository = medicationRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.vitalRepository = vitalRepository;
        this.photoRepository = photoRepository;
        this.domainMapper = domainMapper;
    }

    @Override
    public ServiceResponse<List<? extends IPatient>> findPatientByName(String firstName, String lastName) {
        ExpressionList<Patient> query;
        if (!firstName.isEmpty() && lastName.isEmpty()) {
            query = QueryProvider.getPatientQuery().where().eq("first_name", firstName);
        } else if (firstName.isEmpty() && !lastName.isEmpty()) {
            query = QueryProvider.getPatientQuery().where().eq("last_name", lastName);
        } else {
            query = QueryProvider.getPatientQuery().where().eq("first_name", firstName).eq("last_name", lastName);
        }

        List<? extends IPatient> savedPatients = patientRepository.find(query);
        ServiceResponse<List<? extends IPatient>> response = new ServiceResponse<>();
        if (savedPatients == null || savedPatients.size() == 0) {
            response.addError("first name/last name", "patient could not be found by name");
        } else {
            response.setResponseObject(savedPatients);
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
    public ServiceResponse<List<? extends IPatientEncounter>> findAllEncountersByPatientId(int id) {
        Query<PatientEncounter> query = QueryProvider.getPatientEncounterQuery().where().eq("patient_id", id).order().desc("date_of_visit");
        List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.find(query);
        ServiceResponse<List<? extends IPatientEncounter>> response = new ServiceResponse<>();
        if (patientEncounters.size() > 0) {
            response.setResponseObject(patientEncounters);
        } else {
            response.addError("encounters", "could not find any encounters");
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
            DateTime dateNow = dateUtils.getCurrentDateTime();
            DateTime dateOfMedicalVisit = currentPatientEncounter.getDateOfMedicalVisit();
            DateTime dateOfPharmacyVisit = currentPatientEncounter.getDateOfPharmacyVisit();
            Boolean isClosed = false;
            if (dateOfPharmacyVisit != null) {
                isClosed = true;
                //response.addError("", "That patient's encounter has been closed.");
            } else if ((dateNow.dayOfYear().equals(dateOfMedicalVisit.dayOfYear()) && dateNow.year().equals(dateOfMedicalVisit.year())) == false) {
                isClosed = true;
                //response.addError("", "That patient's encounter has been closed.");
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

        ExpressionList<Patient> query = QueryProvider.getPatientQuery()
                .where()
                .eq("id", id);
        try {
            IPatient savedPatient = patientRepository.findOne(query);
            PatientItem patientItem = domainMapper.createPatientItem(savedPatient);

            ExpressionList<Photo> photoQuery = QueryProvider.getPhotoQuery()
                    .where()
                    .eq("id", savedPatient.getId());
            IPhoto savedPhoto = photoRepository.findOne(photoQuery);
            if (savedPhoto != null) {
                patientItem.setPathToPhoto(routes.PhotoController.GetPatientPhoto(id, false).toString());
            } else {
                patientItem.setPathToPhoto("");
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
                .fetch("tab_field")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("tab_field.name", "problem")
                .order()
                .desc("date_taken");

        List<? extends IPatientEncounterTabField> patientEncounterTreatmentFields = patientEncounterTabFieldRepository.find(query);
        if (patientEncounterTreatmentFields == null) {
            response.addError("", "bad query");
        } else {
            for (IPatientEncounterTabField petf : patientEncounterTreatmentFields) {
                problemItems.add(domainMapper.createProblemItem(petf));
            }
            response.setResponseObject(problemItems);
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
}
