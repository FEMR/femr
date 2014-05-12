package femr.business.services;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.DomainMapper;
import femr.business.QueryProvider;
import femr.business.dtos.PrescriptionItem;
import femr.business.dtos.ServiceResponse;
import femr.business.dtos.VitalItem;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class PharmacyService implements IPharmacyService {
    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<IMedication> medicationRepository;
    private final IRepository<IUser> userRepository;
    private final DomainMapper domainMapper;

    @Inject
    public PharmacyService(IRepository<IPatientPrescription> patientPrescriptionRepository,
                           IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                           IRepository<IMedication> medicationRepository,
                           IRepository<IPatientEncounter> patientEncounterRepository,
                           IRepository<IUser> userRepository,
                           DomainMapper domainMapper) {
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.medicationRepository = medicationRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.userRepository = userRepository;
        this.domainMapper = domainMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> findAllMedications() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();

        try {
            List<String> medicationNames = new ArrayList<>();
            List<? extends IMedication> medications = medicationRepository.findAll(Medication.class);
            for (IMedication m : medications) {
                medicationNames.add(m.getName());
            }
            response.setResponseObject(medicationNames);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<VitalItem>> findPharmacyVitalItems(int encounterId) {
        ServiceResponse<List<VitalItem>> response = new ServiceResponse<>();
        if (encounterId < 1) {
            response.addError("", "encounterId can not be less than 1");
            return response;
        }

        try {
            List<VitalItem> vitalItems = new ArrayList<>();

            //more than 2 "ORs" isn't nicely supported in eBean: e.g.: heightFeet OR weight OR heightInches
            //therefore 3 separate queries are used. did i mention fuck ebean?
            Query<PatientEncounterVital> query1 = QueryProvider.getPatientEncounterVitalQuery()
                    .fetch("vital")
                    .where()
                    .eq("patient_encounter_id", encounterId)
                    .eq("vital.name", "heightFeet")
                    .order().desc("date_taken");
            List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(query1);
            if (patientEncounterVitals.size() > 0) {
                VitalItem vitalItem = new VitalItem();
                vitalItem.setName(patientEncounterVitals.get(0).getVital().getName());
                vitalItem.setValue(patientEncounterVitals.get(0).getVitalValue());
                vitalItems.add(vitalItem);
            }

            Query<PatientEncounterVital> query2 = QueryProvider.getPatientEncounterVitalQuery()
                    .fetch("vital")
                    .where()
                    .eq("patient_encounter_id", encounterId)
                    .eq("vital.name", "weight")
                    .order().desc("date_taken");
            patientEncounterVitals = patientEncounterVitalRepository.find(query2);
            if (patientEncounterVitals.size() > 0) {
                VitalItem vitalItem = new VitalItem();
                vitalItem.setName(patientEncounterVitals.get(0).getVital().getName());
                vitalItem.setValue(patientEncounterVitals.get(0).getVitalValue());
                vitalItems.add(vitalItem);
            }

            Query<PatientEncounterVital> query3 = QueryProvider.getPatientEncounterVitalQuery()
                    .fetch("vital")
                    .where()
                    .eq("patient_encounter_id", encounterId)
                    .eq("vital.name", "heightInches")
                    .order().desc("date_taken");
            patientEncounterVitals = patientEncounterVitalRepository.find(query3);
            if (patientEncounterVitals.size() > 0) {
                VitalItem vitalItem = new VitalItem();
                vitalItem.setName(patientEncounterVitals.get(0).getVital().getName());
                vitalItem.setValue(patientEncounterVitals.get(0).getVitalValue());
                vitalItems.add(vitalItem);
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
    public ServiceResponse<IPatientEncounter> checkPatientIn(int encounterId, int userId) {
        ServiceResponse<IPatientEncounter> response = new ServiceResponse<>();
        if (encounterId < 1) {
            response.addError("", "encounterId can not be less than 1");
            return response;
        }

        try {
            ExpressionList<PatientEncounter> query = QueryProvider.getPatientEncounterQuery().where().eq("id", encounterId);
            IPatientEncounter patientEncounter = patientEncounterRepository.findOne(query);
            patientEncounter.setDateOfPharmacyVisit(DateTime.now());
            ExpressionList<User> getUserQuery = QueryProvider.getUserQuery()
                    .where()
                    .eq("id", userId);
            IUser user = userRepository.findOne(getUserQuery);
            patientEncounter.setPharmacist(user);
            patientEncounter = patientEncounterRepository.update(patientEncounter);
            response.setResponseObject(patientEncounter);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PrescriptionItem> createAndReplacePrescription(PrescriptionItem prescriptionItem, int oldScriptId, int userId) {
        ServiceResponse<PrescriptionItem> response = new ServiceResponse<>();
        if (prescriptionItem == null || StringUtils.isNullOrWhiteSpace(prescriptionItem.getName()) || oldScriptId < 1 || userId < 1) {
            response.addError("", "bad parameters");
            return response;
        }

        try {
            ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery()
                    .where()
                    .eq("id", oldScriptId);

            IPatientPrescription oldPatientPrescription = patientPrescriptionRepository.findOne(query);

            //create new prescription
            IPatientPrescription newPatientPrescription = domainMapper.createPatientPrescription(0, oldPatientPrescription.getEncounterId(), prescriptionItem, null, userId);
            newPatientPrescription = patientPrescriptionRepository.create(newPatientPrescription);

            //replace the old prescription
            oldPatientPrescription.setReplacementId(newPatientPrescription.getId());
            patientPrescriptionRepository.update(oldPatientPrescription);

            PrescriptionItem newPrescriptionItem = domainMapper.createPrescriptionItem(newPatientPrescription);
            response.setResponseObject(newPrescriptionItem);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }
}

