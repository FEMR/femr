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
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IEncounterService;
import femr.business.services.core.IMissionTripService;
import femr.business.services.core.IUserService;
import femr.common.ItemMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.*;

public class EncounterService implements IEncounterService {

    private IMissionTripService missionTripService;
    private final IRepository<IChiefComplaint> chiefComplaintRepository;
    private final IRepository<IPatientAgeClassification> patientAgeClassificationRepository;
    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;
    private final IRepository<ITab> tabRepository;
    private final IRepository<ITabField> tabFieldRepository;
    private final IRepository<IUser> userRepository;
    private final DomainMapper domainMapper;

    @Inject
    public EncounterService(IMissionTripService missionTripService,
                            IRepository<IChiefComplaint> chiefComplaintRepository,
                            IRepository<IPatientAgeClassification> patientAgeClassificationRepository,
                            IRepository<IPatientEncounter> patientEncounterRepository,
                            IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository,
                            IRepository<ITab> tabRepository,
                            IRepository<ITabField> tabFieldRepository,
                            IRepository<IUser> userRepository,
                            DomainMapper domainMapper) {

        this.missionTripService = missionTripService;
        this.chiefComplaintRepository = chiefComplaintRepository;
        this.patientAgeClassificationRepository = patientAgeClassificationRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
        this.tabRepository = tabRepository;
        this.tabFieldRepository = tabFieldRepository;
        this.userRepository = userRepository;
        this.domainMapper = domainMapper;
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
            //find the nurse that checked in the patient
            ExpressionList<User> nurseQuery = QueryProvider.getUserQuery()
                    .where()
                    .eq("email", patientEncounterItem.getNurseEmailAddress());

            IUser nurseUser = userRepository.findOne(nurseQuery);

            //find the age classification of the patient, if it exists
            ExpressionList<PatientAgeClassification> patientAgeClassificationExpressionList = QueryProvider.getPatientAgeClassificationQuery()
                    .where()
                    .eq("name", patientEncounterItem.getAgeClassification());
            IPatientAgeClassification patientAgeClassification = patientAgeClassificationRepository.findOne(patientAgeClassificationExpressionList);
            Integer patientAgeClassificationId = null;
            if (patientAgeClassification != null)
                patientAgeClassificationId = patientAgeClassification.getId();

            //find the current trip, if one exists
            IMissionTrip missionTrip = missionTripService.findCurrentMissionTrip();
            Integer missionTripId = null;
            if (missionTrip != null)
                missionTripId = missionTrip.getId();

            IPatientEncounter newPatientEncounter = domainMapper.createPatientEncounter(patientEncounterItem, nurseUser.getId(), patientAgeClassificationId, missionTripId);
            newPatientEncounter = patientEncounterRepository.create(newPatientEncounter);

            List<IChiefComplaint> chiefComplaints = new ArrayList<>();
            for (String cc : patientEncounterItem.getChiefComplaints()) {
                chiefComplaints.add(domainMapper.createChiefComplaint(cc, newPatientEncounter.getId()));
            }
            if (chiefComplaints.size() > 0) {
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
    public ServiceResponse<PatientEncounterItem> checkPatientInToMedical(int encounterId, int userId) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<IPatientEncounter> checkPatientInToPharmacy(int encounterId, int userId) {
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
    public ServiceResponse<UserItem> getPhysicianThatCheckedInPatientToMedical(int encounterId) {
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
    public ServiceResponse<List<TabItem>> findAllTabsAndFieldsByEncounterId(int encounterId, boolean isActive) {

        ServiceResponse<List<TabItem>> response = new ServiceResponse<>();
        List<TabItem> tabItems = new ArrayList<>();
        TabItem tabItem;

        ExpressionList<Tab> tabQuery = QueryProvider.getTabQuery()
                .where()
                .eq("isDeleted", false);

        try {
            List<? extends ITab> allTabs = tabRepository.find(tabQuery);

            for (ITab t : allTabs) {

                //create a tab item that will store chief complaints and fields
                tabItem = ItemMapper.createTabItem(t.getName(), t.getIsCustom(), t.getLeftColumnSize(), t.getRightColumnSize());

                //does this work..?
                Collections.sort(t.getTabFields(), new Comparator<ITabField>() {

                    @Override
                    public int compare(ITabField o1, ITabField o2) {
                        if (o1.getOrder() == null && o2.getOrder() != null)
                            return -1;
                        if (o1.getOrder() == null && o2.getOrder() == null)
                            return 0;
                        if (o1.getOrder() != null && o2.getOrder() == null)
                            return 1;

                        return o1.getOrder().compareTo(o2.getOrder());
                    }
                });

                //goes through each tab field that belongs to a specific tab
                for (ITabField tf : t.getTabFields()) {

                    //get available chief complaints
                    ExpressionList<ChiefComplaint> chiefComplaintQuery = QueryProvider.getChiefComplaintQuery()
                            .where()
                            .eq("patient_encounter_id", encounterId);
                    List<? extends IChiefComplaint> chiefComplaints = chiefComplaintRepository.find(chiefComplaintQuery);
                    //get a list of values that have been recorded for a specific tab field
                    Query<PatientEncounterTabField> patientEncounterTabFieldQuery = QueryProvider.getPatientEncounterTabFieldQuery()
                            .where()
                            .eq("tabField", tf)
                            .eq("patient_encounter_id", encounterId)
                            .order()
                            .desc("date_taken");
                    List<? extends IPatientEncounterTabField> patientEncounterFieldsWithValue = patientEncounterTabFieldRepository.find(patientEncounterTabFieldQuery);

                    List<TabFieldItem> tabFieldItemsForChiefComplaint;

                    //separate the tab fields by chief complaint
                    Map<String, List<TabFieldItem>> separatedTabFieldsByChiefComplaint = new HashMap<>();
                    if (patientEncounterFieldsWithValue != null && patientEncounterFieldsWithValue.size() > 0) {
                        //the field has value(s)
                        separatedTabFieldsByChiefComplaint = separateTabFieldsByChiefComplaint(patientEncounterFieldsWithValue);
                    } else {
                        //the field has no values
                        List<TabFieldItem> tabFieldItems = new ArrayList<>();
                        String size = null;
                        if (tf.getTabFieldSize() != null) {
                            size = tf.getTabFieldSize().getName();
                        }
                        TabFieldItem tabFieldItem = ItemMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), size, tf.getOrder(), tf.getPlaceholder());
                        tabFieldItems.add(tabFieldItem);
                        if (t.getName().toLowerCase().equals("hpi")) {
                            //if the tab is HPI, separate the empty fields by chief complaint
                            //(each chief complaint gets the field)
                            if (chiefComplaints == null || chiefComplaints.size() < 1) {
                                //no chief complaints exist, add the field to the null key
                                separatedTabFieldsByChiefComplaint.put(null, tabFieldItems);
                            } else {
                                for (IChiefComplaint cc : chiefComplaints) {
                                    separatedTabFieldsByChiefComplaint.put(cc.getValue(), tabFieldItems);
                                }
                            }
                        }else{
                            separatedTabFieldsByChiefComplaint.put(null, tabFieldItems);
                        }


                    }


                    //iterate over all tab fields that have a value
                    for (Map.Entry<String, List<TabFieldItem>> tabFieldItems : separatedTabFieldsByChiefComplaint.entrySet()) {
                        String key_chiefComplaint = tabFieldItems.getKey();
                        List<TabFieldItem> tabFieldItemsWithValueForChiefComplaint = tabFieldItems.getValue();

                        if (tabFieldItemsWithValueForChiefComplaint.size() > 0) {
                            //pull the fields out of the map going back to the UI
                            tabFieldItemsForChiefComplaint = tabItem.getFields(key_chiefComplaint);
                            if (tabFieldItemsForChiefComplaint == null || tabFieldItemsForChiefComplaint.size() == 0) {

                                tabFieldItemsForChiefComplaint = new ArrayList<>();
                            }
                            tabFieldItemsForChiefComplaint.add(tabFieldItemsWithValueForChiefComplaint.get(0));
                            tabItem.setFields(key_chiefComplaint, tabFieldItemsForChiefComplaint);
                        } else {
                            tabFieldItemsForChiefComplaint = tabItem.getFields(key_chiefComplaint);
                            if (tabFieldItemsForChiefComplaint == null || tabFieldItemsForChiefComplaint.size() == 0) {

                                tabFieldItemsForChiefComplaint = new ArrayList<>();
                            }
                            tabFieldItemsForChiefComplaint.add(ItemMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), tf.getTabFieldSize().getName(), tf.getOrder(), tf.getPlaceholder()));
                            tabItem.setFields(key_chiefComplaint, tabFieldItemsForChiefComplaint);
                        }

                    }
                }

                tabItems.add(tabItem);

            }
            response.setResponseObject(tabItems);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }

    private Map<String, List<TabFieldItem>> separateTabFieldsByChiefComplaint(List<? extends IPatientEncounterTabField> patientEncounterTabFieldsWithValue) {
        Map<String, List<TabFieldItem>> mappedTabFields = new HashMap<>();
        List<TabFieldItem> patientEncounterTabFieldItemsForMap;
        TabFieldItem tabFieldItem;

        for (IPatientEncounterTabField petf : patientEncounterTabFieldsWithValue) {

            String size = null;
            String chiefComplaint = null;
            if (petf.getTabField().getTabFieldSize() != null)
                size = petf.getTabField().getTabFieldSize().getName();
            if (petf.getChiefComplaint() != null)
                chiefComplaint = petf.getChiefComplaint().getValue();
            tabFieldItem = ItemMapper.createTabFieldItem(petf.getTabField().getName(),
                    petf.getTabField().getTabFieldType().getName(),
                    size,
                    petf.getTabField().getOrder(),
                    petf.getTabField().getPlaceholder(),
                    petf.getTabFieldValue(),
                    chiefComplaint);

            if (!mappedTabFields.containsKey(chiefComplaint)) {
                //create a new entry
                patientEncounterTabFieldItemsForMap = new ArrayList<>();
                patientEncounterTabFieldItemsForMap.add(tabFieldItem);
                mappedTabFields.put(chiefComplaint, patientEncounterTabFieldItemsForMap);
            } else {

                patientEncounterTabFieldItemsForMap = mappedTabFields.get(chiefComplaint);
                patientEncounterTabFieldItemsForMap.add(tabFieldItem);
                mappedTabFields.put(chiefComplaint, patientEncounterTabFieldItemsForMap);
            }
        }

        return mappedTabFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFields(Map<String,String> tabFieldsWithValue, String chiefComplaint, int encounterId, int userId){

        ServiceResponse<List<TabFieldItem>> response = new ServiceResponse<>();
        if (tabFieldsWithValue.size() < 1){
            response.addError("", "no data to save");
            return response;
        }

        //list of values to insert into database
        List<IPatientEncounterTabField> tabFields = new ArrayList<>();
        try {
            for (Map.Entry<String, String> entry : tabFieldsWithValue.entrySet()) {
                //get the current tab field item
                ExpressionList<TabField> query = QueryProvider.getTabFieldQuery()
                        .where()
                        .eq("name", entry.getKey());
                ITabField tabField = tabFieldRepository.findOne(query);

                //create a patientEncounterTabField for saving
                IPatientEncounterTabField patientEncounterTabField = domainMapper.createPatientEncounterTabField(tabField, userId, entry.getValue(), encounterId);
                if (StringUtils.isNotNullOrWhiteSpace(chiefComplaint)) {
                    ExpressionList<ChiefComplaint> chiefComplaintExpressionList = QueryProvider.getChiefComplaintQuery()
                            .where()
                            .eq("value", chiefComplaint.trim())
                            .eq("patient_encounter_id", encounterId);
                    IChiefComplaint chiefComplaintData = chiefComplaintRepository.findOne(chiefComplaintExpressionList);
                    if (chiefComplaintData != null) {
                        patientEncounterTabField.setChiefComplaint(chiefComplaintData);
                    }
                }


                //check to see if the tab field item already exists (updating will result in a duplicate)
                ExpressionList<PatientEncounterTabField> query2 = QueryProvider.getPatientEncounterTabFieldQuery()
                        .where()
                        .eq("tabField", tabField)
                        .eq("patient_encounter_id", encounterId)
                        .eq("tab_field_value", entry.getValue());
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
                tabFieldItemsToReturn.add(DomainMapper.createTabFieldItem(petf));
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
                    if (petf.getTabField() != null)
                        problemItems.add(ItemMapper.createProblemItem(petf.getTabFieldValue()));
                }
                response.setResponseObject(problemItems);
            }
        } catch (Exception ex) {
            response.addError("", "error");
        }

        return response;
    }
}
