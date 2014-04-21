package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import com.typesafe.config.ConfigFactory;
import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.ui.controllers.routes;
import femr.ui.models.data.PatientItem;
import femr.ui.models.data.VitalItem;
import femr.util.DataStructure.VitalMultiMap;
import femr.util.calculations.dateUtils;

import javax.xml.ws.Service;
import java.io.File;
import java.util.*;

public class SearchService implements ISearchService {
    private IRepository<IMedication> medicationRepository;
    private IRepository<IPatient> patientRepository;
    private IRepository<IPatientEncounter> patientEncounterRepository;
    private IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository;
    private IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private IRepository<IPatientPrescription> patientPrescriptionRepository;
    private IRepository<IVital> vitalRepository;
    private IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository;
    private IRepository<IPatientEncounterPmhField> patientEncounterPmhFieldRepository;
    private IRepository<ITreatmentField> treatmentFieldRepository;
    private IRepository<IPmhField> pmhFieldRepository;
    private IRepository<IHpiField> hpiFieldRepository;
    private IRepository<IPhoto> photoRepository;

    @Inject
    public SearchService(IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                         IRepository<IVital> vitalRepository,
                         IRepository<IPatientPrescription> patientPrescriptionRepository,
                         IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository,
                         IRepository<IMedication> medicationRepository,
                         IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository,
                         IRepository<IPatientEncounterPmhField> patientEncounterPmhFieldRepository,
                         IRepository<ITreatmentField> treatmentFieldRepository,
                         IRepository<IPmhField> pmhFieldRepository,
                         IRepository<IHpiField> hpiFieldRepository,
                         IRepository<IPhoto> photoRepository) {
        this.medicationRepository = medicationRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterTreatmentFieldRepository = patientEncounterTreatmentFieldRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.vitalRepository = vitalRepository;
        this.patientEncounterHpiFieldRepository = patientEncounterHpiFieldRepository;
        this.patientEncounterPmhFieldRepository = patientEncounterPmhFieldRepository;
        this.treatmentFieldRepository = treatmentFieldRepository;
        this.pmhFieldRepository = pmhFieldRepository;
        this.hpiFieldRepository = hpiFieldRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public ServiceResponse<IPatient> findPatientById(int id) {
        ExpressionList<Patient> query = getPatientQuery().where().eq("id", id);
        IPatient savedPatient = patientRepository.findOne(query);

        ServiceResponse<IPatient> response = new ServiceResponse<>();
        if (savedPatient == null) {
            response.addError("id", "id does not exist");
        } else {
            response.setResponseObject(savedPatient);
        }
        return response;
    }

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
                patientItem.setPathToPhoto(ConfigFactory.load().getString("photos.path") + savedPhoto.getFilePath());
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

    @Override
    public ServiceResponse<IPatientEncounter> findPatientEncounterById(int id) {
        ExpressionList<PatientEncounter> query = getPatientEncounterQuery().where().eq("id", id);
        IPatientEncounter patientEncounter = patientEncounterRepository.findOne(query);

        ServiceResponse<IPatientEncounter> response = new ServiceResponse<>();
        if (patientEncounter == null) {
            response.addError("id", "id does not exist");
        } else {
            response.setResponseObject(patientEncounter);
        }
        return response;
    }

    @Override
    public ServiceResponse<IPatientEncounter> findCurrentEncounterByPatientId(int id) {
        ExpressionList<PatientEncounter> query =
                getPatientEncounterQuery().where().eq("patient_id", id);
        List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.find(query);

        ServiceResponse<IPatientEncounter> response = new ServiceResponse<>();
        if (patientEncounters.size() < 1) {
            response.addError("id", "No encounters exist for that id");
        } else {
            int size = patientEncounters.size();
            response.setResponseObject(patientEncounters.get(size - 1));
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPatient>> findPatientByName(String firstName, String lastName) {
        ExpressionList<Patient> query;
        if (!firstName.isEmpty() && lastName.isEmpty()) {
            query = getPatientQuery().where().eq("first_name", firstName);
        } else if (firstName.isEmpty() && !lastName.isEmpty()) {
            query = getPatientQuery().where().eq("last_name", lastName);
        } else {
            query = getPatientQuery().where().eq("first_name", firstName).eq("last_name", lastName);
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
        Query<PatientEncounterVital> query = getPatientEncounterVitalQuery()
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
        Query<PatientEncounter> query = getPatientEncounterQuery().where().eq("patient_id", id).order().desc("date_of_visit");
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
    public ServiceResponse<List<? extends IPatientPrescription>> findPrescriptionsByEncounterId(int id) {
        ExpressionList<PatientPrescription> query = getPatientPrescriptionQuery().where().eq("encounter_id", id);
        List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(query);
        ServiceResponse<List<? extends IPatientPrescription>> response = new ServiceResponse<>();
        if (patientPrescriptions.size() > 0) {
            response.setResponseObject(patientPrescriptions);
        } else {
            response.addError("prescriptions", "No prescriptions found");
        }
        return response;
    }


    @Override
    public ServiceResponse<IPatientEncounterTreatmentField> findRecentTreatmentField(int encounterId, String name) {
        Query<PatientEncounterTreatmentField> query = getPatientEncounterTreatmentFieldQuery()
                .fetch("treatmentField")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("treatmentField.name", name)
                .order().desc("date_taken");
        List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields = patientEncounterTreatmentFieldRepository.find(query);
        ServiceResponse<IPatientEncounterTreatmentField> response = new ServiceResponse<>();
        if (patientEncounterTreatmentFields.size() > 0) {
            response.setResponseObject(patientEncounterTreatmentFields.get(0));
        } else {
            response.addError(name, "Could not find any Treatment entries");
        }
        return response;
    }

    @Override
    public ServiceResponse<IPatientEncounterHpiField> findRecentHpiField(int encounterId, String name) {
        Query<PatientEncounterHpiField> query = getPatientEncounterHpiFieldQuery()
                .fetch("hpiField")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("hpiField.name", name)
                .order().desc("date_taken");
        List<? extends IPatientEncounterHpiField> patientEncounterHpiFields = patientEncounterHpiFieldRepository.find(query);
        ServiceResponse<IPatientEncounterHpiField> response = new ServiceResponse<>();
        if (patientEncounterHpiFields.size() > 0) {
            response.setResponseObject(patientEncounterHpiFields.get(0));
        } else {
            response.addError(name, "Could not find any HPI entries");
        }
        return response;
    }

    @Override
    public ServiceResponse<IPatientEncounterPmhField> findRecentPmhField(int encounterId, String name) {
        Query<PatientEncounterPmhField> query = getPatientEncounterPmhFieldQuery()
                .fetch("pmhField")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("pmhField.name", name)
                .order().desc("date_taken");
        List<? extends IPatientEncounterPmhField> patientEncounterPmhFields = patientEncounterPmhFieldRepository.find(query);
        ServiceResponse<IPatientEncounterPmhField> response = new ServiceResponse<>();
        if (patientEncounterPmhFields.size() > 0) {
            response.setResponseObject(patientEncounterPmhFields.get(0));
        } else {
            response.addError(name, "Could not find any PMH entries");
        }
        return response;
    }


    @Override
    public ServiceResponse<List<? extends IPatientEncounterHpiField>> findHpiFields(int encounterId, String name) {
        Query<PatientEncounterHpiField> query = getPatientEncounterHpiFieldQuery()
                .fetch("hpiField")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("hpiField.name", name)
                .order().desc("date_taken");
        List<? extends IPatientEncounterHpiField> patientEncounterHpiFields = patientEncounterHpiFieldRepository.find(query);
        ServiceResponse<List<? extends IPatientEncounterHpiField>> response = new ServiceResponse<>();
        if (patientEncounterHpiFields.size() > 0) {
            response.setResponseObject(patientEncounterHpiFields);
        } else {
            response.addError(name, "Could not find any HPI entries");
        }
        return response;
    }


    @Override
    public ServiceResponse<List<? extends IPatientEncounterTreatmentField>> findTreatmentFields(int encounterId, String name) {
        Query<PatientEncounterTreatmentField> query = getPatientEncounterTreatmentFieldQuery()
                .fetch("treatmentField")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("treatmentField.name", name)
                .order().desc("date_taken");
        List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields = patientEncounterTreatmentFieldRepository.find(query);
        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> response = new ServiceResponse<>();
        if (patientEncounterTreatmentFields.size() > 0) {
            response.setResponseObject(patientEncounterTreatmentFields);
        } else {
            response.addError(name, "Could not find any treatment entries");
        }
        return response;
    }


    @Override
    public ServiceResponse<List<? extends IPatientEncounterPmhField>> findPmhFields(int encounterId, String name) {
        Query<PatientEncounterPmhField> query = getPatientEncounterPmhFieldQuery()
                .fetch("pmhField")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("pmhField.name", name)
                .order().desc("date_taken");
        List<? extends IPatientEncounterPmhField> patientEncounterPmhFields = patientEncounterPmhFieldRepository.find(query);
        ServiceResponse<List<? extends IPatientEncounterPmhField>> response = new ServiceResponse<>();
        if (patientEncounterPmhFields.size() > 0) {
            response.setResponseObject(patientEncounterPmhFields);
        } else {
            response.addError("", "Could not find any PMH fields");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPatientEncounterTreatmentField>> findProblemsByEncounterId(int id) {
        ExpressionList<PatientEncounterTreatmentField> query = getPatientEncounterTreatmentFieldQuery().where().eq("patient_encounter_id", id).eq("treatment_field_id", 2);
        List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields = patientEncounterTreatmentFieldRepository.find(query);
        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> response = new ServiceResponse<>();
        if (patientEncounterTreatmentFields.size() > 0) {
            response.setResponseObject(patientEncounterTreatmentFields);
        } else {
            response.addError("problems", "could not find any problems");
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

    @Override
    public ServiceResponse<List<? extends ITreatmentField>> findAllTreatmentFields() {
        List<? extends ITreatmentField> treatmentFields = treatmentFieldRepository.findAll(TreatmentField.class);
        ServiceResponse<List<? extends ITreatmentField>> response = new ServiceResponse<>();
        if (treatmentFields.size() > 0) {
            response.setResponseObject(treatmentFields);
        } else {
            response.addError("", "no treatment fields available");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IHpiField>> findAllHpiFields() {
        List<? extends IHpiField> hpiFields = hpiFieldRepository.findAll(HpiField.class);
        ServiceResponse<List<? extends IHpiField>> response = new ServiceResponse<>();
        if (hpiFields.size() > 0) {
            response.setResponseObject(hpiFields);
        } else {
            response.addError("", "no hpi fields available");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPmhField>> findAllPmhFields() {
        List<? extends IPmhField> pmhFields = pmhFieldRepository.findAll(PmhField.class);
        ServiceResponse<List<? extends IPmhField>> response = new ServiceResponse<>();
        if (pmhFields.size() > 0) {
            response.setResponseObject(pmhFields);
        } else {
            response.addError("", "no pmh fields available");
        }
        return response;
    }

    @Override
    public ServiceResponse<IVital> findVital(String name) {
        ExpressionList<Vital> query = getVitalQuery().where().eq("name", name);
        IVital vital = vitalRepository.findOne(query);

        ServiceResponse<IVital> response = new ServiceResponse<>();
        if (vital != null) {
            response.setResponseObject(vital);
        } else {
            response.addError("", "error finding vital");
        }
        return response;
    }

    @Override
    public ServiceResponse<IPmhField> findPmhField(String name) {
        ExpressionList<PmhField> query = getPmhFieldQuery().where().eq("name", name);
        IPmhField pmhField = pmhFieldRepository.findOne(query);
        ServiceResponse<IPmhField> response = new ServiceResponse<>();
        if (pmhField != null) {
            response.setResponseObject(pmhField);
        } else {
            response.addError("", "error finding pmh field");
        }
        return response;
    }

    @Override
    public ServiceResponse<IHpiField> findHpiField(String name) {
        ExpressionList<HpiField> query = getHpiFieldQuery().where().eq("name", name);
        IHpiField hpiField = hpiFieldRepository.findOne(query);

        ServiceResponse<IHpiField> response = new ServiceResponse<>();
        if (hpiField != null) {
            response.setResponseObject(hpiField);
        } else {
            response.addError("", "error finding hpi field");
        }
        return response;
    }

    @Override
    public ServiceResponse<ITreatmentField> findTreatmentField(String name) {
        ExpressionList<TreatmentField> query = getTreatmentFieldQuery().where().eq("name", name);
        ITreatmentField treatmentField = treatmentFieldRepository.findOne(query);

        ServiceResponse<ITreatmentField> response = new ServiceResponse<>();
        if (treatmentField != null) {
            response.setResponseObject(treatmentField);
        } else {
            response.addError("", "error finding treatment field");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IMedication>> findAllMedications() {
        List<? extends IMedication> medications = medicationRepository.findAll(Medication.class);
        ServiceResponse<List<? extends IMedication>> response = new ServiceResponse<>();
        if (medications.size() > 0) {
            response.setResponseObject(medications);
        } else {
            response.addError("medications", "no medications available");
        }
        return response;
    }

    /**
     * Create linked hash map of vitals where the key is the date as well as the name
     *
     * @param encounterId the id of the encounter to get vitals for
     * @return vitals and dates related to encounter
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
        ExpressionList<PatientPrescription> query = getPatientPrescriptionQuery().where().eq("id", id);
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
     * Gets the id of the doctor for a given Encounter in the HpiField
     *
     * @param id The encounter ID
     * @return The userID associated with that field
     */
    public ServiceResponse<IPatientEncounterHpiField> findDoctorIdByEncounterIdInHpiField(int id) {
        Query<PatientEncounterHpiField> query = getPatientEncounterHpiFieldQuery()
                .fetch("hpiField")
                .where()
                .eq("patient_encounter_id", id)
                .order().desc("date_taken");
        List<? extends IPatientEncounterHpiField> patientEncounterHpiFields = patientEncounterHpiFieldRepository.find(query);
        IPatientEncounterHpiField patientEncounterHpiField;
        ServiceResponse<IPatientEncounterHpiField> response = new ServiceResponse<>();
        //check to see if the list is empty
        if (patientEncounterHpiFields.isEmpty()) {
            response.addError("patientEncounterHpiField", "Could not find Hpi Field for the given encounter id");
        } else {
            patientEncounterHpiField = patientEncounterHpiFields.get(0); // the first entry should be the newest
            response.setResponseObject(patientEncounterHpiField);
        }
        return response;
    }


    /**
     * Gets the id of the doctor for a given Encounter in the PmhField
     *
     * @param id The encounter ID
     * @return The userID associated with that field
     */
    public ServiceResponse<IPatientEncounterPmhField> findDoctorIdByEncounterIdInPmhField(int id) {
        Query<PatientEncounterPmhField> query = getPatientEncounterPmhFieldQuery()
                .fetch("pmhField")
                .where()
                .eq("patient_encounter_id", id)
                .order().desc("date_taken");
        List<? extends IPatientEncounterPmhField> patientEncounterPmhFields = patientEncounterPmhFieldRepository.find(query);
        IPatientEncounterPmhField patientEncounterPmhField;
        ServiceResponse<IPatientEncounterPmhField> response = new ServiceResponse<>();
        //check to see if the list is empty
        if (patientEncounterPmhFields.isEmpty()) {
            response.addError("patientEncounterPmhField", "Could not find Pmh field for the given encounter id");
        } else {
            patientEncounterPmhField = patientEncounterPmhFields.get(0); // the first entry should be the newest
            response.setResponseObject(patientEncounterPmhField);
        }
        return response;
    }


    /**
     * Gets the id of the doctor for a given Encounter in the TreatmentField
     *
     * @param id The encounter ID
     * @return The userID associated with that field
     */
    public ServiceResponse<IPatientEncounterTreatmentField> findDoctorIdByEncounterIdInTreatmentField(int id) {
        Query<PatientEncounterTreatmentField> query = getPatientEncounterTreatmentFieldQuery()
                .fetch("treatmentField")
                .where()
                .eq("patient_encounter_id", id)
                .order().desc("date_taken");
        List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields = patientEncounterTreatmentFieldRepository.find(query);
        IPatientEncounterTreatmentField patientEncounterTreatmentField;
        ServiceResponse<IPatientEncounterTreatmentField> response = new ServiceResponse<>();
        if (patientEncounterTreatmentFields.isEmpty()) {
            response.addError("patientEncounterTreatmentField", "Could not find Treatment field for the given encounter id");
        } else {
            patientEncounterTreatmentField = patientEncounterTreatmentFields.get(0); // the first entry should be the newest
            response.setResponseObject(patientEncounterTreatmentField);
        }
        return response;
    }

    private Query<Vital> getVitalQuery() {
        return Ebean.find(Vital.class);
    }

    private Query<TreatmentField> getTreatmentFieldQuery() {
        return Ebean.find(TreatmentField.class);
    }

    private Query<PmhField> getPmhFieldQuery() {
        return Ebean.find(PmhField.class);
    }

    private Query<HpiField> getHpiFieldQuery() {
        return Ebean.find(HpiField.class);
    }

    private Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }

    private Query<PatientEncounter> getPatientEncounterQuery() {
        return Ebean.find(PatientEncounter.class);
    }

    private Query<PatientEncounterVital> getPatientEncounterVitalQuery() {
        return Ebean.find(PatientEncounterVital.class);
    }

    private Query<PatientPrescription> getPatientPrescriptionQuery() {
        return Ebean.find(PatientPrescription.class);
    }

    private Query<PatientEncounterTreatmentField> getPatientEncounterTreatmentFieldQuery() {
        return Ebean.find(PatientEncounterTreatmentField.class);
    }

    private Query<PatientEncounterHpiField> getPatientEncounterHpiFieldQuery() {
        return Ebean.find(PatientEncounterHpiField.class);
    }

    private Query<PatientEncounterPmhField> getPatientEncounterPmhFieldQuery() {
        return Ebean.find(PatientEncounterPmhField.class);
    }
}
