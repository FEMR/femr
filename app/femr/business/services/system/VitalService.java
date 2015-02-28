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
package femr.business.services.system;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IVitalService;
import femr.common.UIModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.VitalItem;
import femr.data.DataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.IPatientEncounterVital;
import femr.data.models.core.IVital;
import femr.data.models.mysql.PatientEncounterVital;
import femr.data.models.mysql.Vital;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.calculations.dateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VitalService implements IVitalService {

    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<IVital> vitalRepository;
    private final DataModelMapper dataModelMapper;

    @Inject
    public VitalService(IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                        IRepository<IVital> vitalRepository,
                        DataModelMapper dataModelMapper){

        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.vitalRepository = vitalRepository;
        this.dataModelMapper = dataModelMapper;
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
                patientEncounterVitals.add(dataModelMapper.createPatientEncounterVital(encounterId, userId, currentTime, vital.getId(), patientEncounterVitalMap.get(key)));
            }
        }

        try {
            List<VitalItem> vitalItems = new ArrayList<>();
            List<? extends IPatientEncounterVital> newPatientEncounterVitals = patientEncounterVitalRepository.createAll(patientEncounterVitals);
            for (IPatientEncounterVital pev : newPatientEncounterVitals) {
                if (pev.getVital() != null)
                    vitalItems.add(UIModelMapper.createVitalItem(pev.getVital().getName(), pev.getVitalValue()));
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
    public ServiceResponse<List<VitalItem>> findAllVitalItems() {
        ServiceResponse<List<VitalItem>> response = new ServiceResponse<>();

        try {
            List<? extends IVital> vitals = vitalRepository.findAll(Vital.class);
            List<VitalItem> vitalItems = new ArrayList<>();
            for (IVital v : vitals) {
                vitalItems.add(UIModelMapper.createVitalItem(v.getName(), null));
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
                    patientEncounterVitals.add(dataModelMapper.createPatientEncounterVital(encounterId, userId, currentTime, vital.getId(), patientEncounterVitalMap.get(key)));
                }
            }

            List<? extends IPatientEncounterVital> newPatientEncounterVitals = patientEncounterVitalRepository.createAll(patientEncounterVitals);
            List<VitalItem> vitalItems = new ArrayList<>();
            for (IPatientEncounterVital pev : patientEncounterVitals) {
                if (pev.getVital() != null)
                    vitalItems.add(UIModelMapper.createVitalItem(pev.getVital().getName(), pev.getVitalValue()));
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
    public ServiceResponse<VitalMultiMap> findVitalMultiMap(int encounterId) {
        ServiceResponse<VitalMultiMap> response = new ServiceResponse<>();
        VitalMultiMap vitalMultiMap = new VitalMultiMap();

        Query<PatientEncounterVital> query = QueryProvider.getPatientEncounterVitalQuery()
                .where()
                .eq("patient_encounter_id", encounterId)
                .order()
                .desc("date_taken");
        try {
            List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(query);

            if (patientEncounterVitals != null) {
                for (IPatientEncounterVital vitalData : patientEncounterVitals) {
                    vitalMultiMap.put(vitalData.getVital().getName(), vitalData.getDateTaken().trim(), vitalData.getVitalValue());
                }
            }
        } catch (Exception ex) {
            response.addError("", "bad query");
        }

        response.setResponseObject(vitalMultiMap);
        return response;
    }
}
