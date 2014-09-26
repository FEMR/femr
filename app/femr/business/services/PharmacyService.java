/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.business.services;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.common.models.PrescriptionItem;
import femr.common.dto.ServiceResponse;
import femr.common.models.ProblemItem;
import femr.common.models.VitalItem;
import femr.data.models.IUser;
import femr.data.models.User;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class PharmacyService implements IPharmacyService {
    private final IRepository<IMedication> medicationRepository;
    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;
    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private final IRepository<IUser> userRepository;
    private final DomainMapper domainMapper;

    @Inject
    public PharmacyService(IRepository<IPatientPrescription> patientPrescriptionRepository,
                           IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository,
                           IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                           IRepository<IMedication> medicationRepository,
                           IRepository<IPatientEncounter> patientEncounterRepository,
                           IRepository<IUser> userRepository,
                           DomainMapper domainMapper) {
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
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
            IPatientPrescription newPatientPrescription = domainMapper.createPatientPrescription(0, oldPatientPrescription.getPatientEncounter().getId(), prescriptionItem, null, userId);
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
}

