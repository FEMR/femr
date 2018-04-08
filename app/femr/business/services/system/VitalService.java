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

import io.ebean.ExpressionList;
import io.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IVitalService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.VitalItem;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.IPatientEncounterVital;
import femr.data.models.core.ISystemSetting;
import femr.data.models.core.IVital;
import femr.data.models.mysql.PatientEncounterVital;
import femr.data.models.mysql.SystemSetting;
import femr.data.models.mysql.Vital;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.calculations.LocaleUnitConverter;
import femr.util.calculations.dateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VitalService implements IVitalService {

    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<IVital> vitalRepository;
    private final IDataModelMapper dataModelMapper;
    private final IRepository<ISystemSetting> systemSettingRepository;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public VitalService(IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                        IRepository<IVital> vitalRepository,
                        IDataModelMapper dataModelMapper,
                        IRepository<ISystemSetting> settingsReposity,
                        @Named("identified") IItemModelMapper itemModelMapper) {

        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.vitalRepository = vitalRepository;
        this.dataModelMapper = dataModelMapper;
        this.systemSettingRepository = settingsReposity;
        this.itemModelMapper = itemModelMapper;
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

        // Convert vitals to imperial for storing in DB
        if (isMetric())
            LocaleUnitConverter.toImperial(patientEncounterVitalMap);

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
                    vitalItems.add(itemModelMapper.createVitalItem(pev.getVital().getName(), pev.getVitalValue()));
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
    public ServiceResponse<List<VitalItem>> retrieveAllVitalItems() {
        ServiceResponse<List<VitalItem>> response = new ServiceResponse<>();

        try {
            List<? extends IVital> vitals = vitalRepository.findAll(Vital.class);
            List<VitalItem> vitalItems = new ArrayList<>();
            for (IVital v : vitals) {
                vitalItems.add(itemModelMapper.createVitalItem(v.getName(), null));
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
        if (patientEncounterVitalMap == null) {
            response.addError("", "bad parameters");
            return response;
        }

        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();
        IPatientEncounterVital patientEncounterVital;
        IVital vital;

        ExpressionList<Vital> query;
        String currentTime = dateUtils.getCurrentDateTimeString();

        // Convert vitals to imperial for storing in DB
        if (isMetric())
            LocaleUnitConverter.toImperial(patientEncounterVitalMap);


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
                   vitalItems.add(itemModelMapper.createVitalItem(pev.getVital().getName(), pev.getVitalValue()));
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
    public ServiceResponse<VitalMultiMap> retrieveVitalMultiMap(int encounterId) {
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

            // convert the multimap to metric - if condition removed and converting to metric for dual unit display
         //   if (isMetric()) {
                vitalMultiMap = LocaleUnitConverter.toMetric(vitalMultiMap);


        } catch (Exception ex) {
            response.addError("", "bad query");
        }

        response.setResponseObject(vitalMultiMap);
        return response;
    }

    /**
     * Gets isActive of the metric setting
     * @return
     */
    private boolean isMetric() {
        ExpressionList<SystemSetting> query = QueryProvider.getSystemSettingQuery()
                .where()
                .eq("name", "Metric System Option");
        ISystemSetting isMetric = systemSettingRepository.findOne(query);
        return isMetric.isActive();
    }
}
