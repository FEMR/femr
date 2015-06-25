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
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IEncounterService;
import femr.business.services.core.IMissionTripService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.daos.core.IPatientEncounterRepository;
import femr.data.daos.core.IPatientRepository;
import femr.data.daos.core.ITabRepository;
import femr.data.daos.core.ITripRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.*;

public  class EncounterService implements IEncounterService {

    private final ITripRepository tripRepository;
    private final IPatientRepository patientRepository;
    private final IPatientEncounterRepository patientEncounterRepository;
    private final ITabRepository tabRepository;
    private final IRepository<IUser> userRepository;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public EncounterService(ITripRepository tripRepository,
                            IPatientRepository patientRepository,
                            IPatientEncounterRepository patientEncounterRepository,
                            ITabRepository tabRepository,
                            IRepository<IUser> userRepository,
                            IDataModelMapper dataModelMapper,
                            @Named("identified") IItemModelMapper itemModelMapper) {

        this.tripRepository = tripRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.tabRepository = tabRepository;
        this.userRepository = userRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
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
            //findPatientEncounterVital the nurse that checked in the patient
            ExpressionList<User> nurseQuery = QueryProvider.getUserQuery()
                    .where()
                    .eq("email", patientEncounterItem.getNurseEmailAddress());

            IUser nurseUser = userRepository.findOne(nurseQuery);

            IPatientAgeClassification patientAgeClassification = patientRepository.findPatientAgeClassification(patientEncounterItem.getAgeClassification());
            Integer patientAgeClassificationId = null;
            if (patientAgeClassification != null)
                patientAgeClassificationId = patientAgeClassification.getId();

            //findPatientEncounterVital the current trip, if one exists
            IMissionTrip missionTrip = tripRepository.retrieveCurrentMissionTrip();
            Integer missionTripId = null;
            if (missionTrip != null)
                missionTripId = missionTrip.getId();

            IPatientEncounter newPatientEncounter = dataModelMapper.createPatientEncounter(patientEncounterItem.getPatientId(), dateUtils.getCurrentDateTime(), patientEncounterItem.getWeeksPregnant(), nurseUser.getId(), patientAgeClassificationId, missionTripId);
            newPatientEncounter = patientEncounterRepository.createPatientEncounter(newPatientEncounter);

            List<IChiefComplaint> chiefComplaints = new ArrayList<>();
            Integer chiefComplaintSortOrder = 0;
            for (String cc : patientEncounterItem.getChiefComplaints()) {

                chiefComplaints.add(dataModelMapper.createChiefComplaint(cc, newPatientEncounter.getId(), chiefComplaintSortOrder));
                chiefComplaintSortOrder++;
            }
            if (chiefComplaints.size() > 0) {

                patientEncounterRepository.createAllChiefComplaints(chiefComplaints);
            }


            response.setResponseObject(itemModelMapper.createPatientEncounterItem(newPatientEncounter));
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

        try {
            IPatientEncounter patientEncounter = patientEncounterRepository.findPatientEncounterById(encounterId);


            patientEncounter.setDateOfMedicalVisit(DateTime.now());
            ExpressionList<User> getUserQuery = QueryProvider.getUserQuery()
                    .where()
                    .eq("id", userId);
            IUser user = userRepository.findOne(getUserQuery);
            patientEncounter.setDoctor(user);

            patientEncounter = patientEncounterRepository.updatePatientEncounter(patientEncounter);


            response.setResponseObject(itemModelMapper.createPatientEncounterItem(patientEncounter));
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

            IPatientEncounter patientEncounter = patientEncounterRepository.findPatientEncounterById(encounterId);
            patientEncounter.setDateOfPharmacyVisit(DateTime.now());
            ExpressionList<User> getUserQuery = QueryProvider.getUserQuery()
                    .where()
                    .eq("id", userId);
            IUser user = userRepository.findOne(getUserQuery);
            patientEncounter.setPharmacist(user);
            patientEncounter = patientEncounterRepository.updatePatientEncounter(patientEncounter);
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

            IPatientEncounter patientEncounter = patientEncounterRepository.findPatientEncounterById(encounterId);
            if (patientEncounter.getDoctor() == null) {
                response.setResponseObject(null);
            } else {
                UserItem userItem = itemModelMapper.createUserItem(patientEncounter.getDoctor());
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
        try {

            ITabField tabField = tabRepository.findTabField("problem");
            List<IPatientEncounterTabField> patientEncounterTabFields = new ArrayList<>();
            DateTime dateTaken = dateUtils.getCurrentDateTime();
            for (String problemval : problemValues) {

                IPatientEncounterTabField patientEncounterTabField = dataModelMapper.createPatientEncounterTabField(tabField.getId(), userId, problemval, encounterId, dateTaken, null);
                patientEncounterTabFields.add(patientEncounterTabField);
            }
            tabRepository.createPatientEncounterTabFields(patientEncounterTabFields);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }


        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFields(Map<String, String> tabFieldNameValues, int encounterId, int userId, String chiefComplaint){

        ServiceResponse<List<TabFieldItem>> response = new ServiceResponse<>();

        if (tabFieldNameValues.isEmpty() || StringUtils.isNullOrWhiteSpace(chiefComplaint)){
            response.addError("", "no fields");
            return response;
        }

        try {
            //the object we will use to populate to put in the ServiceResponse
            List<TabFieldItem> tabFieldItemsForResponse;
            //get all chief complaints for an encounter to findPatientEncounterVital reference IDs
            List<? extends IChiefComplaint> chiefComplaints = patientEncounterRepository.findAllChiefComplaintsByPatientEncounterId(encounterId);
            //foreign key IDs for patientEncounterTabField referencing
            Integer tabFieldId = null;
            Integer chiefComplaintId = null;

            //removes a tab field from the map to be saved if it contains the same name and value as an entry that
            //already exists in the database. This can be a problem if a user tries to change the value then change it back.
            List<? extends IPatientEncounterTabField> existingPatientEncounterTabFields = tabRepository.findPatientEncounterTabFieldsByEncounterId(encounterId);
            for (Iterator<Map.Entry<String, String>> it = tabFieldNameValues.entrySet().iterator(); it.hasNext(); ) {

                Map.Entry<String, String> entry = it.next();

                for (IPatientEncounterTabField petf : existingPatientEncounterTabFields) {

                    if (petf.getTabField().getName().equals(entry.getKey()) &&
                            petf.getTabFieldValue().equals(entry.getValue())) {

                        if (petf.getChiefComplaint() == null) {
                            it.remove();
                            break;
                        } else if (petf.getChiefComplaint().getValue().equals(chiefComplaint)) {
                            it.remove();
                            break;
                        }
                        break;
                    }
                }
            }

            //get all tab fields to use in finding reference Ids
            List<? extends ITabField> tabFields = tabRepository.findAllTabFields();
            //one datetime field to put in everything
            DateTime dateTaken = dateUtils.getCurrentDateTime();
            //the fields that we will be saving to the database after all is said and (almost) done
            List<IPatientEncounterTabField> patientEncounterTabFieldsForSaving = new ArrayList<>();
            //foreign key IDs for patientEncounterTabField referencing

            for (Map.Entry<String, String> entry : tabFieldNameValues.entrySet()){
                if (StringUtils.isNotNullOrWhiteSpace(entry.getKey()) || StringUtils.isNotNullOrWhiteSpace(entry.getValue())) {

                    //findPatientEncounterVital reference ID for tabfield
                    tabFieldId = getTabFieldIdByTabFieldName(tabFields, entry.getKey());
                    //findPatientEncounterVital reference ID for chief complaint
                    for (IChiefComplaint cc : chiefComplaints) {

                        if (chiefComplaint.equals(cc.getValue())) {

                            chiefComplaintId = cc.getId();
                            break;
                        }
                    }

                    if (tabFieldId != null) {

                        patientEncounterTabFieldsForSaving.add(dataModelMapper.createPatientEncounterTabField(tabFieldId, userId, entry.getValue(), encounterId, dateTaken, chiefComplaintId));
                    } else {

                        response.addError("name", "one of the tabfields had an invalid name");
                    }
                }
            }

            //SAVE EM
            List<? extends IPatientEncounterTabField> savedPatientEncounterTabFields = tabRepository.createPatientEncounterTabFields(patientEncounterTabFieldsForSaving);
            //RETURN EM
            tabFieldItemsForResponse = getTabFieldItemsFromPatientEncounterTabFields(savedPatientEncounterTabFields);
            response.setResponseObject(tabFieldItemsForResponse);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFields(Map<String, String> tabFieldNameValues, int encounterId, int userId){

        ServiceResponse<List<TabFieldItem>> response = new ServiceResponse<>();

        if (tabFieldNameValues.isEmpty()){
            response.addError("", "no fields");
            return response;
        }

        try {
            //the object we will use to populate to put in the ServiceResponse
            List<TabFieldItem> tabFieldItemsForResponse;

            //removes a tab field from the map to be saved if it contains the same name and value as an entry that
            //already exists in the database. This can be a problem if a user tries to change the value then change it back.
            List<? extends IPatientEncounterTabField> existingPatientEncounterTabFields = tabRepository.findPatientEncounterTabFieldsByEncounterId(encounterId);
            for (Iterator<Map.Entry<String, String>> it = tabFieldNameValues.entrySet().iterator(); it.hasNext(); ) {

                Map.Entry<String, String> entry = it.next();

                for (IPatientEncounterTabField petf : existingPatientEncounterTabFields) {

                    if (petf.getTabField().getName().equals(entry.getKey()) &&
                            petf.getTabFieldValue().equals(entry.getValue())) {

                        it.remove();
                        break;
                    }
                }
            }

            //get all tab fields to use in finding reference Ids
            List<? extends ITabField> tabFields = tabRepository.findAllTabFields();
            //one datetime field to put in everything
            DateTime dateTaken = dateUtils.getCurrentDateTime();
            //the fields that we will be saving to the database after all is said and (almost) done
            List<IPatientEncounterTabField> patientEncounterTabFieldsForSaving = new ArrayList<>();
            //foreign key IDs for patientEncounterTabField referencing
            Integer tabFieldId;
            for (Map.Entry<String, String> entry : tabFieldNameValues.entrySet()){
                if (StringUtils.isNotNullOrWhiteSpace(entry.getKey()) || StringUtils.isNotNullOrWhiteSpace(entry.getValue())) {

                    //findPatientEncounterVital reference ID for tabfield
                    tabFieldId = getTabFieldIdByTabFieldName(tabFields, entry.getKey());

                    if (tabFieldId != null) {

                        patientEncounterTabFieldsForSaving.add(dataModelMapper.createPatientEncounterTabField(tabFieldId, userId, entry.getValue(), encounterId, dateTaken, null));
                    } else {

                        response.addError("name", "one of the tabfields had an invalid name");
                    }
                }
            }

            //SAVE EM
            List<? extends IPatientEncounterTabField> savedPatientEncounterTabFields = tabRepository.createPatientEncounterTabFields(patientEncounterTabFieldsForSaving);
            //RETURN EM
            tabFieldItemsForResponse = getTabFieldItemsFromPatientEncounterTabFields(savedPatientEncounterTabFields);
            response.setResponseObject(tabFieldItemsForResponse);
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


        try {
            List<? extends IPatientEncounterTabField> patientEncounterTreatmentFields = tabRepository.findPatientEncounterTabFieldByEncounterIdAndTabNameOrderByDateTakenAsc(encounterId, "problem");
            if (patientEncounterTreatmentFields == null) {
                response.addError("", "bad query");
            } else {
                for (IPatientEncounterTabField petf : patientEncounterTreatmentFields) {
                    if (petf.getTabField() != null)
                        problemItems.add(itemModelMapper.createProblemItem(petf.getTabFieldValue()));
                }
                response.setResponseObject(problemItems);
            }
        } catch (Exception ex) {
            response.addError("", "error");
        }

        return response;
    }


    /**
     * Translates a list of PatientEncounterTabFields into a list of TabFieldItems
     */
    private List<TabFieldItem> getTabFieldItemsFromPatientEncounterTabFields(List<? extends IPatientEncounterTabField> patientEncounterTabFields){

        List<TabFieldItem> tabFieldItems = new ArrayList<>();
        String chiefComplaint = null;
        String size = null;
        boolean isCustom;
        for (IPatientEncounterTabField petf : patientEncounterTabFields) {
            isCustom = petf.getTabField().getTab().getIsCustom();
            if (petf.getChiefComplaint() != null)
                chiefComplaint = petf.getChiefComplaint().getValue();
            if (petf.getTabField().getTabFieldSize() != null)
                size = petf.getTabField().getTabFieldSize().getName();

            tabFieldItems.add(itemModelMapper.createTabFieldItem(
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
        return tabFieldItems;
    }

    /**
     * Gets the ID of a TabField so a foreign key can be set up when saving in eBean.
     *
     * @param tabFields a list of all available TabFields
     * @param tabFieldName the name of the requested TabField
     * @return the ID of the requested tab field or null if none are found
     */
    private Integer getTabFieldIdByTabFieldName(List<? extends ITabField> tabFields, String tabFieldName){

        Integer tabFieldId = null;
        for (ITabField tf : tabFields) {

            if (tabFieldName.equals(tf.getName())) {

                tabFieldId = tf.getId();
                break;
            }
        }

        return tabFieldId;
    }
}
