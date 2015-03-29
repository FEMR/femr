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
import femr.business.services.core.IEncounterService;
import femr.business.services.core.IMissionTripService;
import femr.common.UIModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.*;

public class EncounterService implements IEncounterService {

    private IMissionTripService missionTripService;
    private final IRepository<IChiefComplaint> chiefComplaintRepository;
    private final IRepository<IPatientAgeClassification> patientAgeClassificationRepository;
    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;
    private final IRepository<ITabField> tabFieldRepository;
    private final IRepository<IUser> userRepository;
    private final IDataModelMapper dataModelMapper;

    @Inject
    public EncounterService(IMissionTripService missionTripService,
                            IRepository<IChiefComplaint> chiefComplaintRepository,
                            IRepository<IPatientAgeClassification> patientAgeClassificationRepository,
                            IRepository<IPatientEncounter> patientEncounterRepository,
                            IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository,
                            IRepository<ITabField> tabFieldRepository,
                            IRepository<IUser> userRepository,
                            IDataModelMapper dataModelMapper) {

        this.missionTripService = missionTripService;
        this.chiefComplaintRepository = chiefComplaintRepository;
        this.patientAgeClassificationRepository = patientAgeClassificationRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
        this.tabFieldRepository = tabFieldRepository;
        this.userRepository = userRepository;
        this.dataModelMapper = dataModelMapper;
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
            IMissionTrip missionTrip = missionTripService.retrieveCurrentMissionTrip();
            Integer missionTripId = null;
            if (missionTrip != null)
                missionTripId = missionTrip.getId();

            IPatientEncounter newPatientEncounter = dataModelMapper.createPatientEncounter(patientEncounterItem.getPatientId(), dateUtils.getCurrentDateTime(), patientEncounterItem.getWeeksPregnant(), nurseUser.getId(), patientAgeClassificationId, missionTripId);
            newPatientEncounter = patientEncounterRepository.create(newPatientEncounter);

            List<IChiefComplaint> chiefComplaints = new ArrayList<>();
            Integer chiefComplaintSortOrder = 0;
            for (String cc : patientEncounterItem.getChiefComplaints()) {

                chiefComplaints.add(dataModelMapper.createChiefComplaint(cc, newPatientEncounter.getId(), chiefComplaintSortOrder));
                chiefComplaintSortOrder++;
            }
            if (chiefComplaints.size() > 0) {

                chiefComplaintRepository.createAll(chiefComplaints);
            }


            response.setResponseObject(UIModelMapper.createPatientEncounterItem(newPatientEncounter));
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


            response.setResponseObject(UIModelMapper.createPatientEncounterItem(patientEncounter));
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
    public ServiceResponse<UserItem> retrievePhysicianThatCheckedInPatientToMedical(int encounterId) {
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
                UserItem userItem = UIModelMapper.createUserItem(patientEncounter.getDoctor());
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
    public ServiceResponse<List<ProblemItem>> createProblems(List<String> problemValues, int encounterId, int userId) {

        ServiceResponse<List<ProblemItem>> response = new ServiceResponse<>();

        //get the current tab field item
        ExpressionList<TabField> query = QueryProvider.getTabFieldQuery()
                .where()
                .eq("name", "problem");

        try {

            ITabField tabField = tabFieldRepository.findOne(query);
            List<IPatientEncounterTabField> patientEncounterTabFields = new ArrayList<>();
            DateTime dateTaken = dateUtils.getCurrentDateTime();
            for (String problemval : problemValues) {

                IPatientEncounterTabField patientEncounterTabField = dataModelMapper.createPatientEncounterTabField(tabField.getId(), userId, problemval, encounterId, dateTaken, null);
                patientEncounterTabFields.add(patientEncounterTabField);
            }
            patientEncounterTabFieldRepository.createAll(patientEncounterTabFields);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }


        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFields(List<TabFieldItem> tabFieldItems, int encounterId, int userId) {

        ServiceResponse<List<TabFieldItem>> response = new ServiceResponse<>();
        if (tabFieldItems == null) {

            response.addError("", "don't send null lists wtf");
            return response;
        }

        try {

            ExpressionList<ChiefComplaint> chiefComplaintExpressionList = QueryProvider.getChiefComplaintQuery()
                    .where()
                    .eq("patient_encounter_id", encounterId);
            ExpressionList<PatientEncounterTabField> patientEncounterTabFieldExpressionList = QueryProvider.getPatientEncounterTabFieldQuery()
                    .where()
                    .eq("patient_encounter_id", encounterId);

            //the response object
            List<TabFieldItem> tabFieldItemsForResponse = new ArrayList<>();
            //the fields that will be saved
            List<IPatientEncounterTabField> patientEncounterTabFieldsForSaving = new ArrayList<>();
            //marking a date for each field
            DateTime dateTaken = dateUtils.getCurrentDateTime();
            //get all tab fields to find reference IDs
            List<? extends ITabField> tabFields = tabFieldRepository.findAll(TabField.class);
            //get all chief complaints for an encounter to find reference IDs
            List<? extends IChiefComplaint> chiefComplaints = chiefComplaintRepository.find(chiefComplaintExpressionList);
            //find fields that have already been saved so we don't duplicate
            List<? extends IPatientEncounterTabField> existingtabFields = patientEncounterTabFieldRepository.find(patientEncounterTabFieldExpressionList);
            //foreign key IDs for patientEncounterTabField referencing
            Integer tabFieldId = null;
            Integer chiefComplaintId = null;

            //removes a tab field from the list to be saved if it contains the same name and value as an entry that
            //already exists. This can be a problem if a user tries to change the value then change it back.
            Iterator<TabFieldItem> i = tabFieldItems.iterator();
            while (i.hasNext()) {
                TabFieldItem tfi = i.next();

                for (IPatientEncounterTabField petf : existingtabFields) {

                    if (petf.getTabField().getName().equals(tfi.getName()) &&
                            petf.getTabFieldValue().equals(tfi.getValue())) {

                        if (petf.getChiefComplaint() == null) {
                            i.remove();
                            break;
                        } else if (petf.getChiefComplaint().getValue().equals(tfi.getChiefComplaint())) {
                            i.remove();
                            break;
                        }
                    }
                }
            }

            for (TabFieldItem tfi : tabFieldItems) {

                if (StringUtils.isNotNullOrWhiteSpace(tfi.getName()) || StringUtils.isNotNullOrWhiteSpace(tfi.getValue())) {

                    //find reference ID for tabfield
                    for (ITabField tf : tabFields) {

                        if (tfi.getName().equals(tf.getName())) {

                            tabFieldId = tf.getId();
                            break;
                        }
                    }
                    //find reference ID for chief complaint
                    for (IChiefComplaint cc : chiefComplaints) {

                        if (tfi.getChiefComplaint() != null && tfi.getChiefComplaint().equals(cc.getValue())) {

                            chiefComplaintId = cc.getId();
                            break;
                        }
                    }

                    if (tabFieldId != null) {

                        patientEncounterTabFieldsForSaving.add(dataModelMapper.createPatientEncounterTabField(tabFieldId, userId, tfi.getValue(), encounterId, dateTaken, chiefComplaintId));
                    } else {

                        response.addError("name", "one of the tabfields had an invalid name");
                    }
                }
            }

            //SAVE EM
            List<? extends IPatientEncounterTabField> savedPatientEncounterTabFields = patientEncounterTabFieldRepository.createAll(patientEncounterTabFieldsForSaving);
            //RETURN EM
            String chiefComplaint = null;
            String size = null;
            boolean isCustom;
            for (IPatientEncounterTabField petf : savedPatientEncounterTabFields) {
                isCustom = petf.getTabField().getTab().getIsCustom();
                if (petf.getChiefComplaint() != null)
                    chiefComplaint = petf.getChiefComplaint().getValue();
                if (petf.getTabField().getTabFieldSize() != null)
                    size = petf.getTabField().getTabFieldSize().getName();

                tabFieldItemsForResponse.add(UIModelMapper.createTabFieldItem(
                                petf.getTabField().getName(),
                                petf.getTabField().getTabFieldType().getName(),
                                size,
                                petf.getTabField().getOrder(),
                                petf.getTabField().getPlaceholder(),
                                petf.getTabFieldValue(),
                                chiefComplaint,
                                isCustom
                        )
                );
            }
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }


        return response;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<ProblemItem>> retrieveProblemItems(int encounterId) {
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
                        problemItems.add(UIModelMapper.createProblemItem(petf.getTabFieldValue()));
                }
                response.setResponseObject(problemItems);
            }
        } catch (Exception ex) {
            response.addError("", "error");
        }

        return response;
    }
}
