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

import femr.data.daos.core.IPrescriptionRepository;
import io.ebean.ExpressionList;
import io.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryHelper;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IInventoryService;
import femr.business.services.core.ISearchService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.daos.core.IEncounterRepository;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.data.models.mysql.concepts.ConceptDiagnosis;
import femr.util.calculations.LocaleUnitConverter;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;
import play.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class SearchService implements ISearchService {

    private final IRepository<IConceptDiagnosis> diagnosisRepository;
    private final IRepository<IMissionTrip> missionTripRepository;
    private final IPatientRepository patientRepository;
    private final IPrescriptionRepository prescriptionRepository;
    private final IEncounterRepository patientEncounterRepository;
    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<ISystemSetting> systemSettingRepository;
    private final IItemModelMapper itemModelMapper;
    private final IInventoryService inventoryService;
    private final IRepository<IMissionCity> cityRepository;

    @Inject
    public SearchService(IRepository<IConceptDiagnosis> diagnosisRepository,
                         IRepository<IMissionTrip> missionTripRepository,
                         IPatientRepository patientRepository,
                         IEncounterRepository patientEncounterRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                         IPrescriptionRepository prescriptionRepository,
                         IRepository<ISystemSetting> systemSettingRepository,
                         IInventoryService inventoryService,
                         IRepository<IMissionCity> cityRepository,
                         @Named("identified") IItemModelMapper itemModelMapper) {

        this.diagnosisRepository = diagnosisRepository;
        this.missionTripRepository = missionTripRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.systemSettingRepository = systemSettingRepository;
        this.itemModelMapper = itemModelMapper;
        this.inventoryService = inventoryService;
        this.cityRepository = cityRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> retrievePatientItemByPatientId(int patientId) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (patientId < 0) {
            response.addError("", "id can not be null or less than 1");
            return response;
        }

        try {
            IPatient savedPatient = patientRepository.retrievePatientById(patientId);
            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.retrievePatientEncountersByPatientIdDesc(patientId);

            Integer patientHeightFeet = null;
            Integer patientHeightInches = null;
            Float patientWeight = null;
            Integer weeksPregnant = null;
            Integer smoker = null;
            Integer diabetic = null;
            Integer alcohol = null;
            Integer cholesterol = null;
            Integer hypertension = null;
            String ageClassification = null;
            if (patientEncounters.size() > 0){

                IPatientEncounter recentEncounter = patientEncounters.get(0);
                patientHeightFeet = QueryHelper.findPatientHeightFeet(patientEncounterVitalRepository, recentEncounter.getId());
                patientHeightInches = QueryHelper.findPatientHeightInches(patientEncounterVitalRepository, recentEncounter.getId());
                patientWeight = QueryHelper.findPatientWeight(patientEncounterVitalRepository, recentEncounter.getId());
                weeksPregnant = QueryHelper.findWeeksPregnant(patientEncounterVitalRepository, recentEncounter.getId());
                smoker = QueryHelper.findPatientSmoker(patientEncounterVitalRepository, recentEncounter.getId());
                diabetic = QueryHelper.findPatientDiabetic(patientEncounterVitalRepository, recentEncounter.getId());
                alcohol = QueryHelper.findPatientAlcohol(patientEncounterVitalRepository, recentEncounter.getId());
                cholesterol = QueryHelper.findPatientCholesterol(patientEncounterVitalRepository, recentEncounter.getId());
                hypertension = QueryHelper.findPatientHypertension(patientEncounterVitalRepository, recentEncounter.getId());

                if (recentEncounter.getPatientAgeClassification() != null){
                    ageClassification = recentEncounter.getPatientAgeClassification().getName();
                }
            }


            
            String pathToPhoto = null;
            Integer photoId = null;
            if (savedPatient.getPhoto() != null) {
                pathToPhoto = savedPatient.getPhoto().getFilePath();
                photoId = savedPatient.getPhoto().getId();


            }
            PatientItem patientItem = itemModelMapper.createPatientItem(
                    savedPatient.getId(),
                    savedPatient.getFirstName(),
                    savedPatient.getLastName(),
                    savedPatient.getPhoneNumber(),
                    savedPatient.getCity(),
                    savedPatient.getAddress(),
                    savedPatient.getUserId(),
                    savedPatient.getAge(),
                    savedPatient.getSex(),
                    weeksPregnant,
                    patientHeightFeet,
                    patientHeightInches,
                    patientWeight,
                    pathToPhoto,
                    photoId,
                    ageClassification,
                    smoker,
                    diabetic,
                    alcohol,
                    cholesterol,
                    hypertension

            );

            //TODO: why is this being repeated?
            if (savedPatient.getPhoto() != null) {
                patientItem.setPathToPhoto("/photo/patient/" + patientId + "?showDefault=false");
            }

            // If metric setting enabled convert response patientItem to metric
            if (isMetric()){
                patientItem = LocaleUnitConverter.toMetric(patientItem);
            }else {
               //added for femr-136 - dual unit display
                patientItem = LocaleUnitConverter.forDualUnitDisplay(patientItem);
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
    public ServiceResponse<PatientItem> retrievePatientItemByEncounterId(int encounterId) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (encounterId < 0) {
            response.addError("", "invalid id");
            return response;
        }

        try {

            IPatientEncounter patientEncounter = patientEncounterRepository.retrievePatientEncounterById(encounterId);
            IPatient patient = patientEncounter.getPatient();
            Integer patientHeightFeet = QueryHelper.findPatientHeightFeet(patientEncounterVitalRepository, patientEncounter.getId());
            Integer patientHeightInches = QueryHelper.findPatientHeightInches(patientEncounterVitalRepository, patientEncounter.getId());
            Float patientWeight = QueryHelper.findPatientWeight(patientEncounterVitalRepository, patientEncounter.getId());
            Integer weeksPregnant = QueryHelper.findWeeksPregnant(patientEncounterVitalRepository, patientEncounter.getId());
            Integer smoker = QueryHelper.findPatientSmoker(patientEncounterVitalRepository, patientEncounter.getId());
            Integer diabetic = QueryHelper.findPatientDiabetic(patientEncounterVitalRepository, patientEncounter.getId());
            Integer alcohol = QueryHelper.findPatientAlcohol(patientEncounterVitalRepository, patientEncounter.getId());
            Integer cholesterol = QueryHelper.findPatientCholesterol(patientEncounterVitalRepository, patientEncounter.getId());
            Integer hypertension = QueryHelper.findPatientHypertension(patientEncounterVitalRepository, patientEncounter.getId());

            String ageClassification = null;
            if (patientEncounter.getPatientAgeClassification() != null){
                ageClassification = patientEncounter.getPatientAgeClassification().getName();
            }

            String pathToPhoto = null;
            Integer photoId = null;
            if (patient.getPhoto() != null) {
                pathToPhoto = patient.getPhoto().getFilePath();
                photoId = patient.getPhoto().getId();
            }
            PatientItem patientItem = itemModelMapper.createPatientItem(
                    patient.getId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getPhoneNumber(),
                    patient.getCity(),
                    patient.getAddress(),
                    patient.getUserId(),
                    patient.getAge(),
                    patient.getSex(),
                    weeksPregnant,
                    patientHeightFeet,
                    patientHeightInches,
                    patientWeight,
                    pathToPhoto,
                    photoId,
                    ageClassification,
                    smoker,
                    diabetic,
                    alcohol,
                    cholesterol,
                    hypertension
            );

            // If metric setting enabled convert response patientItem to metric
            if (isMetric())
                patientItem = LocaleUnitConverter.toMetric(patientItem);

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
    public ServiceResponse<PatientEncounterItem> retrievePatientEncounterItemByEncounterId(int encounterId) {
        ServiceResponse<PatientEncounterItem> response = new ServiceResponse<>();
        if (encounterId < 1) {
            response.addError("", "invalid ID");
            return response;
        }

        try {
            IPatientEncounter patientEncounter = patientEncounterRepository.retrievePatientEncounterById(encounterId);
            PatientEncounterItem patientEncounterItem = itemModelMapper.createPatientEncounterItem(patientEncounter);
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
    public ServiceResponse<PatientEncounterItem> retrieveRecentPatientEncounterItemByPatientId(int patientId) {
        ServiceResponse<PatientEncounterItem> response = new ServiceResponse<>();
        if (patientId < 1) {
            response.addError("", "Invalid patient ID.");
            return response;
        }

        try {
            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.retrievePatientEncountersByPatientIdAsc(patientId);
            if (patientEncounters.size() > 0) {

                IPatientEncounter currentPatientEncounter = patientEncounters.get(patientEncounters.size() - 1);
                PatientEncounterItem patientEncounterItem = itemModelMapper.createPatientEncounterItem(currentPatientEncounter);
                response.setResponseObject(patientEncounterItem);
            }
        } catch (Exception ex) {

            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PatientEncounterItem>> retrievePatientEncounterItemsByPatientId(int patientId) {

        ServiceResponse<List<PatientEncounterItem>> response = new ServiceResponse<>();
        try {
            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.retrievePatientEncountersByPatientIdDesc(patientId);
            List<PatientEncounterItem> patientEncounterItems = new ArrayList<>();
            for (IPatientEncounter pe : patientEncounters) {
                patientEncounterItems.add(itemModelMapper.createPatientEncounterItem(pe));
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
    public ServiceResponse<List<PrescriptionItem>> retrieveUnreplacedPrescriptionItems(int encounterId, Integer tripId) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();

        try {

            List<? extends IPatientPrescription> patientPrescriptions = prescriptionRepository.retrieveUnreplacedPrescriptionsByEncounterId(encounterId);

            List<PrescriptionItem> prescriptionItems = new ArrayList<>();



            for( IPatientPrescription pp : patientPrescriptions )
            {
                // Don't get prescriptions with a replacement
                if( pp.getPatientPrescriptionReplacements() != null && pp.getPatientPrescriptionReplacements().size() > 0 )
                    continue;

                Integer quantityCurrent = null;
                Integer quantityInitial = null;
                DateTime isDeleted = null;
                String timeAdded = "";
                String createdBy = "";

                //if the medication resides in inventory and the user is on the trip using that inventory
                //this will make sure that information about the current state of inventory is included
                //in the PrescriptionItem being created below.
                if( pp.getMedication().getMedicationInventory().size() > 0 && tripId != null) {


                    //need to get the specific inventory for this medication (a medication can have multiple inventories if diferent trips are using it)
                    ServiceResponse<MedicationItem> inventoryResponse = inventoryService.retrieveMedicationInventoryByMedicationIdAndTripId(pp.getMedication().getId(), tripId);

                    //if this trip has an inventory for this medication
                    if (inventoryResponse.getResponseObject() != null){

                        quantityCurrent = inventoryResponse.getResponseObject().getQuantityCurrent();
                        quantityInitial = inventoryResponse.getResponseObject().getQuantityTotal();
                        isDeleted = inventoryResponse.getResponseObject().getIsDeleted();
                        timeAdded = inventoryResponse.getResponseObject().getTimeAdded();
                        createdBy = inventoryResponse.getResponseObject().getCreatedBy();
                    }

                }

                MedicationItem medicationItem = itemModelMapper.createMedicationItem(pp.getMedication(),quantityCurrent, quantityInitial, isDeleted, timeAdded, createdBy);

                PrescriptionItem item = itemModelMapper.createPrescriptionItem(

                        pp.getId(),
                        pp.getMedication().getName(),
                        pp.getPhysician().getFirstName(),
                        pp.getPhysician().getLastName(),
                        pp.getConceptPrescriptionAdministration(),
                        pp.getAmount(),
                        pp.isCounseled(),
                        medicationItem
                );
                prescriptionItems.add(item);
            }

            response.setResponseObject(prescriptionItems);
        } catch (Exception ex) {

            Logger.error("Attempted and failed to execute retrieveUnreplacedPrescriptionItems(" + encounterId + ") in SearchService. Stack trace to follow.");
            ex.printStackTrace();
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PrescriptionItem>> retrieveDispensedPrescriptionItems(int encounterId) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();



        try {
            List<? extends IPatientPrescription> patientPrescriptions = prescriptionRepository.retrieveAllDispensedPrescriptionsByEncounterId(encounterId);


            List<PrescriptionItem> prescriptionItems = patientPrescriptions.stream()
                    .filter(pp -> pp.getDateDispensed() != null)
                    .map(pp -> itemModelMapper.createPrescriptionItem(
                            pp.getId(),
                            pp.getMedication().getName(),
                            pp.getPhysician().getFirstName(),
                            pp.getPhysician().getLastName(),
                            pp.getConceptPrescriptionAdministration(),
                            pp.getAmount(),
                            pp.isCounseled(),
                            itemModelMapper.createMedicationItem(pp.getMedication(), null, null, null, null, null)
                    ))
                    .collect(Collectors.toList());

            List<PrescriptionItem> replacedPrescriptions = patientPrescriptions.stream()
                    .filter(pp -> pp.getPatientPrescriptionReplacements().size() > 0)
                    .map(pp -> itemModelMapper.createPrescriptionItemWithReplacement(
                            pp.getId(),
                            pp.getMedication().getName(),
                            pp.getPatientPrescriptionReplacements().get(0).getReplacementPrescription().getMedication().getName(),
                            pp.getPatientPrescriptionReplacements().get(0).getReplacementPrescription().getId(),
                            pp.getPhysician().getFirstName(),
                            pp.getPhysician().getLastName(),
                            pp.getConceptPrescriptionAdministration(),
                            pp.getAmount(),
                            pp.isCounseled(),
                            itemModelMapper.createMedicationItem(pp.getMedication(), null, null, null, null, null)
                    ))
                    .collect(Collectors.toList());
            prescriptionItems.addAll(replacedPrescriptions);

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
    public ServiceResponse<List<PatientItem>> retrievePatientsFromQueryString(String patientSearchQuery) {
        ServiceResponse<List<PatientItem>> response = new ServiceResponse<>();
        if (StringUtils.isNullOrWhiteSpace(patientSearchQuery)) {
            response.addError("", "bad parameters");
            return response;
        }

        String[] words = patientSearchQuery.trim().split(" ");
        Integer id = null;
        String firstName = null;
        String lastName = null;
        String phoneNumber = null;
        if (words.length == 0) {
            //nothing was in the query
            response.addError("", "query string empty");
            return response;
        } else if (words.length == 1) {
            //could be an ID, name, or phone number
            try {
                //see if it is a number
                if(isNumeric(words[0])) {
                    phoneNumber = words[0];
                    id = Integer.parseInt(words[0]);
                }
            } catch (NumberFormatException ex) {
                //see if it it a string
                firstName = words[0];
            }
        } else if (words.length == 2) {
            firstName = words[0];
            lastName = words[1];
        } else {
            response.addError("", "too many words in query string");
            return response;
        }

        List<? extends IPatient> patients;

        try {
            //Build the Query
            //TODO: filter these by the current country of the team
            if (id != null || phoneNumber != null) {

                List<IPatient> iPatients = new ArrayList<>();
                if(id != null) {

                    //if we have an id, that is all we need.
                    //this is the most ideal scenario
                    IPatient patient = patientRepository.retrievePatientById(id);
                    iPatients.add(patient);
                }

                // if we found a patient by id above
                if ( iPatients.size() > 0 ) {
                    patients = iPatients;
                } else {
                    patients = patientRepository.retrievePatientsByPhoneNumber(phoneNumber);
                }

            } else {
                patients = patientRepository.retrievePatientsByName(firstName, lastName);
            }

            //Execute the query

            List<PatientItem> patientItems = new ArrayList<>();
            for (IPatient patient : patients) {
                //patientItems.add(DomainMapper.createPatientItem(p, null, null, null, null));
                String pathToPhoto = null;
                Integer photoId = null;
                if (patient.getPhoto() != null) {
                    pathToPhoto = patient.getPhoto().getFilePath();
                    photoId = patient.getPhoto().getId();
                }
                patientItems.add(itemModelMapper.createPatientItem(
                        patient.getId(),
                        patient.getFirstName(),
                        patient.getLastName(),
                        patient.getPhoneNumber(),
                        patient.getCity(),
                        patient.getAddress(),
                        patient.getUserId(),
                        patient.getAge(),
                        patient.getSex(),
                        null,
                        null,
                        null,
                        null,
                        pathToPhoto,
                        photoId,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ));
            }
            response.setResponseObject(patientItems);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;
    }

    //Search for potential matching patients using all triage fields
    public ServiceResponse<List<RankedPatientItem>> retrievePatientsFromTriageSearch(String first, String last, String phone, String addr, String gender, Long age, String city) {
        ServiceResponse<List<RankedPatientItem>> response = new ServiceResponse<>();

        try {
            List<? extends IRankedPatientMatch> rankedPatients = patientRepository.retrievePatientMatchesFromTriageFields(first, last, phone, addr, gender, age, city);
            List<RankedPatientItem> rankedPatientItems = new ArrayList<>();

            for (IRankedPatientMatch r : rankedPatients) {
                String pathToPhoto = null;
                Integer photoId = null;
                if (r.getPatient().getPhoto() != null) {
                    pathToPhoto = r.getPatient().getPhoto().getFilePath();
                    photoId = r.getPatient().getPhoto().getId();
                }

                PatientItem patientItem = itemModelMapper.createPatientItem(
                        r.getPatient().getId(),
                        r.getPatient().getFirstName(),
                        r.getPatient().getLastName(),
                        r.getPatient().getPhoneNumber(),
                        r.getPatient().getCity(),
                        r.getPatient().getAddress(),
                        r.getPatient().getUserId(),
                        r.getPatient().getAge(),
                        r.getPatient().getSex(),
                        null,
                        null,
                        null,
                        null,
                        pathToPhoto,
                        photoId,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
                RankedPatientItem rankedPatientItem = new RankedPatientItem(patientItem, r.getRank());
                rankedPatientItems.add(rankedPatientItem);
            }
            response.setResponseObject(rankedPatientItems);

//            List<PatientItem> patientItems = new ArrayList<>();
//            for (Patient patient : patientList) {
//                String pathToPhoto = null;
//                Integer photoId = null;
//                if (patient.getPhoto() != null) {
//                    pathToPhoto = patient.getPhoto().getFilePath();
//                    photoId = patient.getPhoto().getId();
//                }
//                patientItems.add(itemModelMapper.createPatientItem(
//                        patient.getId(),
//                        patient.getFirstName(),
//                        patient.getLastName(),
//                        patient.getPhoneNumber(),
//                        patient.getCity(),
//                        patient.getAddress(),
//                        patient.getUserId(),
//                        patient.getAge(),
//                        patient.getSex(),
//                        null,
//                        null,
//                        null,
//                        null,
//                        pathToPhoto,
//                        photoId,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null
//                ));
//            }
//            response.setResponseObject(patientItems);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<SettingItem> retrieveSystemSettings() {
        ServiceResponse<SettingItem> response = new ServiceResponse<>();
        try {
            List<? extends ISystemSetting> systemSettings = systemSettingRepository.findAll(SystemSetting.class);

            if (systemSettings == null || systemSettings.size() == 0) {

                response.addError("", "no settings exist at this time");
            } else {

                SettingItem settingItem = itemModelMapper.createSettingItem(systemSettings);
                response.setResponseObject(settingItem);

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
    public ServiceResponse<List<PatientItem>> retrievePatientsForSearch(Integer tripId) {
        ServiceResponse<List<PatientItem>> response = new ServiceResponse<>();

        try {
            ExpressionList<SystemSetting> expressionList = QueryProvider.getSystemSettingQuery()
                    .where()
                    .eq("name", "Country Filter");
            ISystemSetting systemSetting = systemSettingRepository.findOne(expressionList);

            IMissionTrip missionTrip = null;
            List<? extends IPatient> allPatients;

            if (tripId != null) {
                //If the trip ID is not null then we can try to figure out which trip
                ExpressionList<MissionTrip> missionTripExpressionList = QueryProvider.getMissionTripQuery()
                        .where()
                        .eq("id", tripId);
                missionTrip = missionTripRepository.findOne(missionTripExpressionList);

                if (missionTrip == null)
                    response.addError("", "a trip was not found with that tripId");

            }

            if (systemSetting != null &&
                    systemSetting.isActive() &&
                    missionTrip != null &&
                    missionTrip.getMissionCity() != null &&
                    missionTrip.getMissionCity().getMissionCountry() != null &&
                    StringUtils.isNotNullOrWhiteSpace(missionTrip.getMissionCity().getMissionCountry().getName())) {
                allPatients = patientRepository.retrievePatientsInCountry(
                        missionTrip.getMissionCity().getMissionCountry().getName()
                );
            } else {

                //Otherwise we can just get ALL OF THE PATIENTS EVER MADE
                allPatients = patientRepository.retrieveAllPatients();
            }

            List<PatientItem> patientItems = new ArrayList<>();

            for (IPatient patient : allPatients) {

                String pathToPhoto = null;
                Integer photoId = null;
                if (patient.getPhoto() != null) {
                    pathToPhoto = patient.getPhoto().getFilePath();
                    photoId = patient.getPhoto().getId();
                }
                PatientItem currPatient = itemModelMapper.createPatientItem(
                        patient.getId(),
                        patient.getFirstName(),
                        patient.getLastName(),
                        patient.getPhoneNumber(),
                        patient.getCity(),
                        patient.getAddress(),
                        patient.getUserId(),
                        patient.getAge(),
                        patient.getSex(),
                        null,
                        null,
                        null,
                        null,
                        pathToPhoto,
                        photoId,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                currPatient.setPathToPhoto("/photo/patient/" + currPatient.getId() + "?showDefault=true");

                patientItems.add(currPatient);

            }


            response.setResponseObject(patientItems);

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> findDiagnosisForSearch() {

        ServiceResponse<List<String>> response = new ServiceResponse<>();
        try {

            List<? extends IConceptDiagnosis> allDiagnoses = diagnosisRepository.findAll(ConceptDiagnosis.class);
            List<String> diagnoses = new ArrayList<>();

            for (IConceptDiagnosis d : allDiagnoses) {
                if (StringUtils.isNotNullOrWhiteSpace(d.getName()))
                    diagnoses.add(d.getName());
            }

            response.setResponseObject(diagnoses);

        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * Gets isActive of the metric setting
     *
     * @return
     */
    private boolean isMetric() {
        ExpressionList<SystemSetting> query = QueryProvider.getSystemSettingQuery()
                .where()
                .eq("name", "Metric System Option");
        ISystemSetting isMetric = systemSettingRepository.findOne(query);
        return isMetric.isActive();
    }

    /** AJ Saclayan
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<CityItem>> retrieveCitiesFromQueryString(String citySearchQuery){
        ServiceResponse<List<CityItem>> response = new ServiceResponse<>();
        if (StringUtils.isNullOrWhiteSpace(citySearchQuery)) {
            response.addError("", "bad parameters");
            return response;
        }

//        String[] words = citySearchQuery.trim().split(" ");
//        String city = citySearchQuery
//        if (words.length == 0) {
//            //nothing was in the query
//            response.addError("", "query string empty");
//            return response;
//        } else if (words.length == 2) {
//            city = words[0];
//        } else {
//            response.addError("", "too many words in query string");
//            return response;
//        }


        //Build the Query
        //TODO: filter these by the current country of the team
        Query<MissionCity> query = null;

            query = QueryProvider.getMissionCityQuery()
                    .where()
                    .eq("name", citySearchQuery)
                    .order()
                    .desc("id");

        //Execute the query
        try {
            List<? extends IMissionCity> cities = cityRepository.find(query);
            List<CityItem> cityItems = new ArrayList<>();
            for (IMissionCity city : cities) {
                //patientItems.add(DomainMapper.createPatientItem(p, null, null, null, null));
                cityItems.add(itemModelMapper.createCityItem(
                        city.getName(),
                        city.getMissionCountry().getName()
                ));
            }
            response.setResponseObject(cityItems);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * AJ Saclayan
     * @return
     */
    @Override
    public ServiceResponse<List<CityItem>> retrieveCitiesForSearch(){
        ServiceResponse<List<CityItem>> response = new ServiceResponse<>();

        try {

            List<? extends IMissionCity> allCities;

            //Make sure that none of the values we will be checking are null.
            //If they are, just get all of the possible patients.

//                allPatients = QueryHelper.findPatients(patientRepository, missionTrip.getMissionCity().getMissionCountry().getName());
//            }else{

                allCities = QueryHelper.findCities(cityRepository);
//            }

            List<CityItem> cityItems = new ArrayList<>();

            for (IMissionCity city : allCities) {

                CityItem currCity = itemModelMapper.createCityItem(
                        city.getName(),
                        city.getMissionCountry().getName()
                );


                cityItems.add(currCity);

            }


            response.setResponseObject(cityItems);

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }
        return response;
    }
}
