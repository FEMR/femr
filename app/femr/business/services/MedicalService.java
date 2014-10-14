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
import com.google.inject.Provider;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.common.dto.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.*;

public class MedicalService implements IMedicalService {

    private final IRepository<IChiefComplaint> chiefComplaintRepository;
    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;
    private final Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private final IRepository<ITab> customTabRepository;
    private final IRepository<ITabField> tabFieldRepository;
    private final IRepository<IUser> userRepository;
    private final IRepository<IVital> vitalRepository;

    private final DomainMapper domainMapper;

    @Inject
    public MedicalService(IRepository<IChiefComplaint> chiefComplaintRepository,
                          IRepository<IPatientEncounter> patientEncounterRepository,
                          IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                          IRepository<ITabField> tabFieldRepository,
                          IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository,
                          IRepository<IPatientPrescription> patientPrescriptionRepository,
                          IRepository<IVital> vitalRepository,
                          IRepository<ITab> customTabRepository,
                          IRepository<IUser> userRepository,
                          Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                          DomainMapper domainMapper) {

        this.chiefComplaintRepository = chiefComplaintRepository;
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
    public ServiceResponse<UserItem> getPhysicianThatCheckedInPatient(int encounterId) {
        ServiceResponse<UserItem> response = new ServiceResponse<>();
        if (encounterId < 1) {
            response.addError("", "encounter id must be greater than 0");
            return response;
        }
        try {
            ExpressionList<PatientEncounter> patientEncounterQuery = QueryProvider.getPatientEncounterQuery()
                    .where()
                    .eq("id", encounterId);
            IPatientEncounter patientEncounter = patientEncounterRepository.findOne(patientEncounterQuery);
            if (patientEncounter.getDoctor() == null) {
                response.setResponseObject(null);
            } else {
                UserItem userItem = DomainMapper.createUserItem(patientEncounter.getDoctor());
                response.setResponseObject(userItem);
            }
        } catch (Exception ex) {
            response.addError("", "error finding encounter");
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
            IMedication medication = domainMapper.createMedication(pi.getName());
            patientPrescriptions.add(domainMapper.createPatientPrescription(0, medication, userId, encounterId, null));
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


        //get patient encounter
        ExpressionList<PatientEncounter> patientEncounterExpressionList = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("id", encounterId);
        IPatientEncounter patientEncounter = patientEncounterRepository.findOne(patientEncounterExpressionList);
        //query without problems
        Query<PatientEncounterTabField> query = QueryProvider.getPatientEncounterTabFieldQuery()
                .fetch("tabField")
                .where()
                .eq("patient_encounter_id", encounterId)
                .ne("tabField.name", "problem")
                .order()
                .desc("date_taken");
        //query for problems
        Query<PatientEncounterTabField> problemQuery = QueryProvider.getPatientEncounterTabFieldQuery()
                .fetch("tabField")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("tabField.name", "problem")
                .order()
                .asc("date_taken");
        try {
            //map non-problems
            List<? extends IPatientEncounterTabField> patientEncounterTabFields = patientEncounterTabFieldRepository.find(query);
            for (IPatientEncounterTabField petf : patientEncounterTabFields) {

                String fieldName = petf.getTabField().getName();

                //if the field is from the HPI tab
                if (petf.getTabField().getTab().getName().equals("HPI")) {

                    if (petf.getChiefComplaint() != null) {
                        int index = patientEncounter.getChiefComplaints().indexOf(petf.getChiefComplaint());
                        fieldName = fieldName + index;
                    } else {
                        fieldName = fieldName + "0";
                    }

                }

                //adds the field and its value to the map if it doesnt already exist.
                //query was sorted in desc order of date to ensure only the newest entries
                //get put in the map
                if (!fieldValueMap.containsKey(fieldName)) {
                    fieldValueMap.put(fieldName, domainMapper.createTabFieldItem(petf));
                }

            }

            //map problems
            List<? extends IPatientEncounterTabField> problemFields = patientEncounterTabFieldRepository.find(problemQuery);
            int problemNumber = 1;
            for (IPatientEncounterTabField petf2 : problemFields) {
                fieldValueMap.put(petf2.getTabField().getName() + problemNumber, domainMapper.createTabFieldItem(petf2));
                problemNumber++;
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
                //get the current tab field item
                ExpressionList<TabField> query = QueryProvider.getTabFieldQuery()
                        .where()
                        .eq("name", tf.getName());
                ITabField tabField = tabFieldRepository.findOne(query);

                //create a patientEncounterTabField for saving
                IPatientEncounterTabField patientEncounterTabField = domainMapper.createPatientEncounterTabField(tabField, userId, tf.getValue(), encounterId);
                if (StringUtils.isNotNullOrWhiteSpace(tf.getChiefComplaint())) {
                    ExpressionList<ChiefComplaint> chiefComplaintExpressionList = QueryProvider.getChiefComplaintQuery()
                            .where()
                            .eq("value", tf.getChiefComplaint().trim())
                            .eq("patient_encounter_id", encounterId);
                    IChiefComplaint chiefComplaint = chiefComplaintRepository.findOne(chiefComplaintExpressionList);
                    if (chiefComplaint != null) {
                        patientEncounterTabField.setChiefComplaint(chiefComplaint);
                    }
                }


                //check to see if the tab field item already exists (updating will result in a duplicate)
                ExpressionList<PatientEncounterTabField> query2 = QueryProvider.getPatientEncounterTabFieldQuery()
                        .where()
                        .eq("tabField", tabField)
                        .eq("patient_encounter_id", encounterId)
                        .eq("tab_field_value", tf.getValue());
                List<? extends IPatientEncounterTabField> patientEncounterTabFields = patientEncounterTabFieldRepository.find(query2);
                if (patientEncounterTabFields != null && patientEncounterTabFields.size() > 0) {
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
                .eq("isDeleted", false)
                .eq("isCustom", true);
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


            response.setResponseObject(DomainMapper.createPatientEncounterItem(patientEncounter));
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }
}
