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
import femr.business.services.core.IEncounterService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.daos.core.IEncounterRepository;
import femr.data.daos.core.IPatientRepository;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;
import play.Logger;

import java.util.*;

public class EncounterService implements IEncounterService {

    private final IRepository<IChiefComplaint> chiefComplaintRepository;
    private final IPatientRepository patientRepository;
    private final IEncounterRepository patientEncounterRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;
    private final IRepository<ITabField> tabFieldRepository;
    private final IUserRepository userRepository;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public EncounterService(IRepository<IChiefComplaint> chiefComplaintRepository,
                            IPatientRepository patientRepository,
                            IEncounterRepository patientEncounterRepository,
                            IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository,
                            IRepository<ITabField> tabFieldRepository,
                            IUserRepository userRepository,
                            IDataModelMapper dataModelMapper,
                            @Named("identified") IItemModelMapper itemModelMapper) {

        this.chiefComplaintRepository = chiefComplaintRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
        this.tabFieldRepository = tabFieldRepository;
        this.userRepository = userRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientEncounterItem> createPatientEncounter(int patientId, int userId, Integer tripId, String ageClassification, List<String> chiefComplaints) {

        ServiceResponse<PatientEncounterItem> response = new ServiceResponse<>();

        try {
            //find the nurse that checked in the patient
            IUser nurseUser = userRepository.retrieveUserById(userId);

            //find the age classification of the patient, if it exists
            IPatientAgeClassification patientAgeClassification = null;
            try {

                patientAgeClassification = patientRepository.retrievePatientAgeClassification(ageClassification);
            } catch (Exception ex) {

                Logger.error("EncounterService-createPatientEncounter", "invalid patient age classification caught in EncounterService");
            }

            Integer patientAgeClassificationId = null;
            if (patientAgeClassification != null)
                patientAgeClassificationId = patientAgeClassification.getId();

            IPatientEncounter newPatientEncounter = patientEncounterRepository.createPatientEncounter(patientId, dateUtils.getCurrentDateTime(), nurseUser.getId(), patientAgeClassificationId, tripId);

            List<IChiefComplaint> chiefComplaintBeans = new ArrayList<>();
            Integer chiefComplaintSortOrder = 0;
            for (String cc : chiefComplaints) {

                chiefComplaintBeans.add(dataModelMapper.createChiefComplaint(cc, newPatientEncounter.getId(), chiefComplaintSortOrder));
                chiefComplaintSortOrder++;
            }
            if (chiefComplaintBeans.size() > 0) {

                chiefComplaintRepository.createAll(chiefComplaintBeans);
            }


            response.setResponseObject(itemModelMapper.createPatientEncounterItem(newPatientEncounter));
        } catch (Exception ex) {

            Logger.error("EncounterService-createPatientEncounter", ex);
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

            IPatientEncounter patientEncounter = patientEncounterRepository.savePatientEncounterMedicalCheckin(encounterId, userId, DateTime.now());
            response.setResponseObject(itemModelMapper.createPatientEncounterItem(patientEncounter));
        } catch (Exception ex) {

            Logger.error("EncounterService-checkPatientInToMedical", ex);
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
            IPatientEncounter patientEncounter = patientEncounterRepository.savePatientEncounterPharmacyCheckin(encounterId, userId, DateTime.now());
            response.setResponseObject(patientEncounter);
        } catch (Exception ex) {

            Logger.error("EncounterService-checkPatientInToPharmacy", ex);
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

            IPatientEncounter patientEncounter = patientEncounterRepository.retrievePatientEncounterById(encounterId);
            if (patientEncounter.getDoctor() == null) {
                response.setResponseObject(null);
            } else {
                UserItem userItem = itemModelMapper.createUserItem(patientEncounter.getDoctor());
                response.setResponseObject(userItem);
            }
        } catch (Exception ex) {

            Logger.error("EncounterService-retrievePhysicianThatCheckedInPatientToMedical", ex);
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
    public ServiceResponse<List<TabFieldItem>> createPatientEncounterTabFields(Map<String, String> tabFieldNameValues, int encounterId, int userId, String chiefComplaint){

        ServiceResponse<List<TabFieldItem>> response = new ServiceResponse<>();

        if (tabFieldNameValues.isEmpty() || StringUtils.isNullOrWhiteSpace(chiefComplaint)){
            response.addError("", "no fields");
            return response;
        }

        try {

            ExpressionList<PatientEncounterTabField> patientEncounterTabFieldExpressionList = QueryProvider.getPatientEncounterTabFieldQuery()
                    .where()
                    .eq("patient_encounter_id", encounterId)
                    .isNull("isDeleted");
            ExpressionList<ChiefComplaint> chiefComplaintExpressionList = QueryProvider.getChiefComplaintQuery()
                    .where()
                    .eq("patient_encounter_id", encounterId);

            //the object we will use to populate to put in the ServiceResponse
            List<TabFieldItem> tabFieldItemsForResponse;
            //get all chief complaints for an encounter to find reference IDs
            List<? extends IChiefComplaint> chiefComplaints = chiefComplaintRepository.find(chiefComplaintExpressionList);

            //removes a tab field from the map to be saved if it contains the same name and value as an entry that
            //already exists in the database. This can be a problem if a user tries to change the value then change it back.
            List<? extends IPatientEncounterTabField> existingPatientEncounterTabFields = patientEncounterTabFieldRepository.find(patientEncounterTabFieldExpressionList);
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
            List<? extends ITabField> tabFields = tabFieldRepository.findAll(TabField.class);
            //one datetime field to put in everything
            DateTime dateTaken = dateUtils.getCurrentDateTime();
            //the fields that we will be saving to the database after all is said and (almost) done
            List<IPatientEncounterTabField> patientEncounterTabFieldsForSaving = new ArrayList<>();
            //foreign key IDs for patientEncounterTabField referencing
            Integer tabFieldId;
            Integer chiefComplaintId = null;
            for (Map.Entry<String, String> entry : tabFieldNameValues.entrySet()){
                if (StringUtils.isNotNullOrWhiteSpace(entry.getKey()) || StringUtils.isNotNullOrWhiteSpace(entry.getValue())) {

                    //find reference ID for tabfield
                    tabFieldId = getTabFieldIdByTabFieldName(tabFields, entry.getKey());
                    //find reference ID for chief complaint
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
            List<? extends IPatientEncounterTabField> savedPatientEncounterTabFields = patientEncounterTabFieldRepository.createAll(patientEncounterTabFieldsForSaving);
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

            ExpressionList<PatientEncounterTabField> patientEncounterTabFieldExpressionList = QueryProvider.getPatientEncounterTabFieldQuery()
                    .where()
                    .eq("patient_encounter_id", encounterId)
                    .isNull("isDeleted");;

            //the object we will use to populate to put in the ServiceResponse
            List<TabFieldItem> tabFieldItemsForResponse;

            //removes a tab field from the map to be saved if it contains the same name and value as an entry that
            //already exists in the database. This can be a problem if a user tries to change the value then change it back.
            List<? extends IPatientEncounterTabField> existingPatientEncounterTabFields = patientEncounterTabFieldRepository.find(patientEncounterTabFieldExpressionList);
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
            List<? extends ITabField> tabFields = tabFieldRepository.findAll(TabField.class);
            //one datetime field to put in everything
            DateTime dateTaken = dateUtils.getCurrentDateTime();
            //the fields that we will be saving to the database after all is said and (almost) done
            List<IPatientEncounterTabField> patientEncounterTabFieldsForSaving = new ArrayList<>();
            //foreign key IDs for patientEncounterTabField referencing
            Integer tabFieldId;
            for (Map.Entry<String, String> entry : tabFieldNameValues.entrySet()){
                if (StringUtils.isNotNullOrWhiteSpace(entry.getKey()) || StringUtils.isNotNullOrWhiteSpace(entry.getValue())) {

                    //find reference ID for tabfield
                    tabFieldId = getTabFieldIdByTabFieldName(tabFields, entry.getKey());

                    if (tabFieldId != null) {

                        patientEncounterTabFieldsForSaving.add(dataModelMapper.createPatientEncounterTabField(tabFieldId, userId, entry.getValue(), encounterId, dateTaken, null));
                    } else {

                        response.addError("name", "one of the tabfields had an invalid name");
                    }
                }
            }

            //SAVE EM
            List<? extends IPatientEncounterTabField> savedPatientEncounterTabFields = patientEncounterTabFieldRepository.createAll(patientEncounterTabFieldsForSaving);
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
        Query<PatientEncounterTabField> query = QueryProvider.getPatientEncounterTabFieldQuery()
                .fetch("tabField")
                .where()
                .isNull("IsDeleted")
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
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<NoteItem>> retrieveNoteItems(int encounterId) {
        ServiceResponse<List<NoteItem>> response = new ServiceResponse<>();
        List<NoteItem> noteItems = new ArrayList<>();
        Query<PatientEncounterTabField> query = QueryProvider.getPatientEncounterTabFieldQuery()
                .fetch("tabField")
                .where()
                .isNull("IsDeleted")
                .eq("patient_encounter_id", encounterId)
                .eq("tabField.name", "pharmacy_note")
                .order()
                .desc("date_taken");

        try {
            List<? extends IPatientEncounterTabField> patientEncounterTreatmentFields = patientEncounterTabFieldRepository.find(query);
            if (patientEncounterTreatmentFields == null) {
                response.addError("", "bad query");
            } else {
                if(!patientEncounterTreatmentFields.isEmpty())
                {
                    IPatientEncounterTabField petf = patientEncounterTreatmentFields.get(0); // get newest note; 4get the rest
                    noteItems.add(itemModelMapper.createNoteItem(petf.getTabFieldValue(), petf.getDateTaken(), userRepository.retrieveUserById(petf.getUserId()).getLastName() + ", " + userRepository.retrieveUserById(petf.getUserId()).getFirstName()));
                }
                response.setResponseObject(noteItems);
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
    public ServiceResponse<PatientEncounterItem> screenPatientForDiabetes(int encounterId, int userId, Boolean isScreened) {

        ServiceResponse<PatientEncounterItem> response = new ServiceResponse<>();

        try {

            IPatientEncounter patientEncounter = patientEncounterRepository.savePatientEncounterDiabetesScreening(encounterId, userId, dateUtils.getCurrentDateTime(), isScreened);
            PatientEncounterItem patientEncounterItem = itemModelMapper.createPatientEncounterItem(patientEncounter);
            response.setResponseObject(patientEncounterItem);

        } catch (Exception ex) {

            Logger.error("EncounterService-screenPatientForDiabetes", ex);
            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PatientEncounterItem>> retrieveCurrentDayPatientEncounters(int tripID)
    {
        ServiceResponse<List<PatientEncounterItem>> response = new ServiceResponse<>();
        List<PatientEncounterItem> patientEncounterItems = new ArrayList<>();
        //gets dates for today and tommorrow
        DateTime today= DateTime.now();
        today=today.withTimeAtStartOfDay();
        DateTime tommorrow=today;
        tommorrow=tommorrow.plusDays(1);

        try{
            List<? extends IPatientEncounter> patient = patientEncounterRepository.retrievePatientEncounters(today, tommorrow, tripID);

            for (IPatientEncounter patient1 : patient) {

                patientEncounterItems.add(itemModelMapper.createPatientEncounterItem(patient1));

            }
            response.setResponseObject(patientEncounterItems);
        }
        catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientEncounterItem> deleteEncounter(int deleteByUserID, String reason, int encounterId) {

        ServiceResponse<PatientEncounterItem> response = new ServiceResponse<>();

        try {

            IPatientEncounter savedEncounter = patientEncounterRepository.deletePatientEncounter(encounterId, reason, deleteByUserID);
            response.setResponseObject(itemModelMapper.createPatientEncounterItem(savedEncounter));

        } catch (Exception ex) {

            Logger.error("PatientEncounterService-deleteEncounter", ex);
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> deleteExistingProblem(int encounterId, String problem, int userId){
        ServiceResponse<Boolean> response = new ServiceResponse<>();

        try {
            //PatientEncounterTabField fieldData
            ExpressionList<PatientEncounterTabField> query = QueryProvider.getPatientEncounterTabFieldQuery()
                    .fetch("tabField")
                    .where()
                    .eq("patient_encounter_id", encounterId)
                    .eq("tabField.name", "problem")
                    .eq("tab_field_value", problem)
                    .isNull("IsDeleted")
                    .isNull("DeletedByUserId");

            // Query gets an entire list in case of duplicate rows of the same problem, will then only set the first instance to deleted
            IPatientEncounterTabField fieldData = patientEncounterTabFieldRepository.find(query).get(0);

            fieldData.setIsDeleted(dateUtils.getCurrentDateTime());
            fieldData.setDeletedByUserId(userId);

            fieldData = patientEncounterTabFieldRepository.update(fieldData);

            response.setResponseObject(true);
        }
        catch(Exception e){
            response.setResponseObject(false);
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
