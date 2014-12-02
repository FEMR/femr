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
import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientEncounterItem;
import femr.common.models.ProblemItem;
import femr.common.models.TabFieldItem;
import femr.common.models.UserItem;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EncounterService implements IEncounterService {

    private final IRepository<IChiefComplaint> chiefComplaintRepository;
    private final IRepository<IPatientAgeClassification> patientAgeClassificationRepository;
    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;
    private final IRepository<ITabField> tabFieldRepository;
    private final IRepository<IUser> userRepository;
    private final DomainMapper domainMapper;

    @Inject
    public EncounterService(IRepository<IChiefComplaint> chiefComplaintRepository,
                            IRepository<IPatientAgeClassification> patientAgeClassificationRepository,
                            IRepository<IPatientEncounter> patientEncounterRepository,
                            IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository,
                            IRepository<ITabField> tabFieldRepository,
                            IRepository<IUser> userRepository,
                            DomainMapper domainMapper){

        this.chiefComplaintRepository = chiefComplaintRepository;
        this.patientAgeClassificationRepository = patientAgeClassificationRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
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

            IPatientEncounter newPatientEncounter = domainMapper.createPatientEncounter(patientEncounterItem, nurseUser.getId(), patientAgeClassificationId);
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
                    problemItems.add(domainMapper.createProblemItem(petf));
                }
                response.setResponseObject(problemItems);
            }
        } catch (Exception ex) {
            response.addError("", "error");
        }

        return response;
    }
}
