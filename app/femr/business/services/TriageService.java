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
import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryHelper;
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

public class TriageService implements ITriageService {

    //repositories
    private final IRepository<IChiefComplaint> chiefComplaintRepository;
    private final IRepository<IPatient> patientRepository;
    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<IUser> userRepository;
    private final IRepository<IVital> vitalRepository;
    private final Provider<IPatientEncounterVital> patientEncounterVitalProvider;

    private final DomainMapper domainMapper;

    @Inject
    public TriageService(IRepository<IChiefComplaint> chiefComplaintRepository,
                         IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitaRepository,
                         IRepository<IUser> userRepository,
                         IRepository<IVital> vitalRepository,
                         Provider<IPatientEncounterVital> patientEncounterVitalProvider,

                         DomainMapper domainMapper) {
        this.chiefComplaintRepository = chiefComplaintRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterVitalRepository = patientEncounterVitaRepository;
        this.userRepository = userRepository;
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
            PatientItem patientItem = domainMapper.createPatientItem(savedPatient, null, null, null, null);
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
            response.setResponseObject(domainMapper.createPatientItem(newPatient, null, null, null, null));
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
            ExpressionList<User> nurseQuery = QueryProvider.getUserQuery()
                    .where()
                    .eq("email", patientEncounterItem.getNurseEmailAddress());

            IUser user = userRepository.findOne(nurseQuery);


            IPatientEncounter newPatientEncounter = domainMapper.createPatientEncounter(patientEncounterItem, user.getId());
            newPatientEncounter = patientEncounterRepository.create(newPatientEncounter);

            List<IChiefComplaint> chiefComplaints = new ArrayList<>();
            for (String cc : patientEncounterItem.getChiefComplaints()){
                chiefComplaints.add(domainMapper.createChiefComplaint(cc, newPatientEncounter.getId()));
            }
            if (chiefComplaints != null && chiefComplaints.size() > 0){
                chiefComplaintRepository.createAll(chiefComplaints);
            }


            response.setResponseObject(DomainMapper.createPatientEncounterItem(newPatientEncounter));
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
