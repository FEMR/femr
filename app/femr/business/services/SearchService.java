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

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryHelper;
import femr.business.helpers.QueryProvider;
import femr.common.dto.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.stringhelpers.StringUtils;

import java.util.*;

public class SearchService implements ISearchService {
    private final IRepository<IMedication> medicationRepository;
    private final IRepository<IPatient> patientRepository;
    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;
    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private final IRepository<ISystemSetting> systemSettingRepository;
    private final IRepository<ITabField> tabFieldRepository;
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
                         IRepository<ITabField> tabFieldRepository,
                         DomainMapper domainMapper) {

        this.medicationRepository = medicationRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.vitalRepository = vitalRepository;
        this.systemSettingRepository = systemSettingRepository;
        this.tabFieldRepository = tabFieldRepository;
        this.domainMapper = domainMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> findPatientItemByPatientId(int patientId) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (patientId < 0) {
            response.addError("", "id can not be null or less than 1");
            return response;
        }

        //get patient encounters so we can use the newest one
        Query<PatientEncounter> peQuery = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("patient_id", patientId)
                .order()
                .desc("date_of_triage_visit");
        try {
            //IPatient savedPatient = patientRepository.findOne(query);
            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.find(peQuery);
            if (patientEncounters.size() < 1) throw new Exception();

            IPatientEncounter recentEncounter = patientEncounters.get(0);
            IPatient savedPatient = patientEncounters.get(0).getPatient();
            Integer patientHeightFeet = QueryHelper.findPatientHeightFeet(patientEncounterVitalRepository, recentEncounter.getId());
            Integer patientHeightInches = QueryHelper.findPatientHeightInches(patientEncounterVitalRepository, recentEncounter.getId());
            Float patientWeight = QueryHelper.findPatientWeight(patientEncounterVitalRepository, recentEncounter.getId());


            PatientItem patientItem = DomainMapper.createPatientItem(savedPatient, recentEncounter.getWeeksPregnant(), patientHeightFeet, patientHeightInches, patientWeight);

            if (savedPatient.getPhoto() != null) {
                patientItem.setPathToPhoto("/photo/patient/" + patientId + "?showDefault=false");
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
    public ServiceResponse<PatientItem> findPatientItemByEncounterId(int encounterId) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (encounterId < 0) {
            response.addError("", "invalid id");
            return response;
        }

        ExpressionList<PatientEncounter> patientEncounterQuery = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("id", encounterId);

        try {
            IPatientEncounter patientEncounter = patientEncounterRepository.findOne(patientEncounterQuery);
            IPatient patient = patientEncounter.getPatient();
            Integer patientHeightFeet = QueryHelper.findPatientHeightFeet(patientEncounterVitalRepository, patientEncounter.getId());
            Integer patientHeightInches = QueryHelper.findPatientHeightInches(patientEncounterVitalRepository, patientEncounter.getId());
            Float patientWeight = QueryHelper.findPatientWeight(patientEncounterVitalRepository, patientEncounter.getId());
            PatientItem patientItem = DomainMapper.createPatientItem(patient, patientEncounter.getWeeksPregnant(), patientHeightFeet, patientHeightInches, patientWeight);
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
    public ServiceResponse<PatientEncounterItem> findPatientEncounterItemByEncounterId(int encounterId) {
        ServiceResponse<PatientEncounterItem> response = new ServiceResponse<>();
        if (encounterId < 1) {
            response.addError("", "invalid ID");
            return response;
        }
        ExpressionList<PatientEncounter> patientEncounterQuery = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("id", encounterId);

        try {
            IPatientEncounter patientEncounter = patientEncounterRepository.findOne(patientEncounterQuery);
            PatientEncounterItem patientEncounterItem = DomainMapper.createPatientEncounterItem(patientEncounter);
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
    public ServiceResponse<PatientEncounterItem> findRecentPatientEncounterItemByPatientId(int patientId) {
        ServiceResponse<PatientEncounterItem> response = new ServiceResponse<>();
        if (patientId < 1) {
            response.addError("", "Invalid patient ID.");
            return response;
        }
        Query<PatientEncounter> query = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("patient_id", patientId)
                .order()
                .asc("date_of_triage_visit");
        try {
            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.find(query);
            if (patientEncounters.size() < 1) {
                response.addError("", "That patient does not exist.");
                return response;
            }
            IPatientEncounter currentPatientEncounter = patientEncounters.get(patientEncounters.size() - 1);
            PatientEncounterItem patientEncounterItem = domainMapper.createPatientEncounterItem(currentPatientEncounter);
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
    public ServiceResponse<List<PatientEncounterItem>> findPatientEncounterItemsByPatientId(int patientId) {
        ServiceResponse<List<PatientEncounterItem>> response = new ServiceResponse<>();
        Query<PatientEncounter> query = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("patient_id", patientId)
                .order()
                .desc("date_of_triage_visit");
        try {
            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.find(query);
            List<PatientEncounterItem> patientEncounterItems = new ArrayList<>();
            for (IPatientEncounter pe : patientEncounters) {
                patientEncounterItems.add(DomainMapper.createPatientEncounterItem(pe));
            }
            response.setResponseObject(patientEncounterItems);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
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
                prescriptionItem.setName(pp.getMedication().getName());
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
    public ServiceResponse<List<PrescriptionItem>> findDispensedPrescriptionItems(int encounterId) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();
        ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery()
                .fetch("patientEncounter")
                .where()
                .eq("encounter_id", encounterId)
                .ne("user_id_pharmacy", null)
                .eq("replacement_id", null);
        try {
            List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(query);
            List<PrescriptionItem> prescriptionItems = new ArrayList<>();

            for (IPatientPrescription pp : patientPrescriptions) {
                PrescriptionItem prescriptionItem = new PrescriptionItem();
                prescriptionItem.setName(pp.getMedication().getName());
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
                patientItems.add(DomainMapper.createPatientItem(p, null, null, null, null));
            }
            response.setResponseObject(patientItems);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabFieldMultiMap> getTabFieldMultiMap(int encounterId) {
        ServiceResponse<TabFieldMultiMap> response = new ServiceResponse<>();
        TabFieldMultiMap tabFieldMultiMap = new TabFieldMultiMap();
        String tabFieldName;
        String chiefComplaint;

        Query<PatientEncounterTabField> patientEncounterTabFieldQuery = QueryProvider.getPatientEncounterTabFieldQuery()
                .where()
                .eq("patient_encounter_id", encounterId)
                .order()
                .desc("date_taken");

        try {
            List<? extends IPatientEncounterTabField> patientEncounterTabFields = patientEncounterTabFieldRepository.find(patientEncounterTabFieldQuery);
            if (patientEncounterTabFields != null) {

                for (IPatientEncounterTabField petf : patientEncounterTabFields) {
                    tabFieldName = petf.getTabField().getName();
                    chiefComplaint = null;
                    if (petf.getTabField().getTab().getName().equals("HPI")) {
                        if (petf.getChiefComplaint() != null) {
                            chiefComplaint = petf.getChiefComplaint().getValue();
                        }
                    }

                    tabFieldMultiMap.put(tabFieldName, petf.getDateTaken().toString().trim(), chiefComplaint, petf.getTabFieldValue());
                }
            }
        } catch (Exception ex) {
            response.addError("", "bad querying");
        }

        response.setResponseObject(tabFieldMultiMap);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<VitalMultiMap> getVitalMultiMap(int encounterId) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<SettingItem> getSystemSettings() {
        ServiceResponse<SettingItem> response = new ServiceResponse<>();
        try {
            List<? extends ISystemSetting> systemSettings = systemSettingRepository.findAll(SystemSetting.class);
            SettingItem settingItem = new SettingItem();
            if (systemSettings == null || systemSettings.size() == 0) {
                response.addError("", "no settings exist at this time");
            } else {
                for (ISystemSetting ss : systemSettings) {
                    switch (ss.getName()) {
                        case "Multiple chief complaints":
                            settingItem.setMultipleChiefComplaint(ss.isActive());
                            break;
                        case "Medical PMH Tab":
                            settingItem.setPmhTab(ss.isActive());
                            break;
                        case "Medical Photo Tab":
                            settingItem.setPhotoTab(ss.isActive());
                            break;
                        case "Medical HPI Consolidate":
                            settingItem.setConsolidateHPI(ss.isActive());
                            break;
                    }
                }
            }
            response.setResponseObject(settingItem);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;
    }


    public ServiceResponse<List<String>> getCustomFieldList() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();
        List<String> tabFieldNames = new ArrayList<>();
        ExpressionList<TabField> query = QueryProvider.getTabFieldQuery()
                .fetch("tab")
                .where()
                .eq("tab.isCustom", true);
        try {
            List<? extends ITabField> tabFields = tabFieldRepository.find(query);
            for (ITabField tabField : tabFields) {
                tabFieldNames.add(tabField.getName());
            }
        } catch (Exception ex) {
            response.addError("", "bad query");
        }
        response.setResponseObject(tabFieldNames);
        return response;
    }


}
