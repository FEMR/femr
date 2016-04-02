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

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryHelper;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.ISearchService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.calculations.LocaleUnitConverter;
import femr.util.stringhelpers.StringUtils;
import java.util.*;
import java.util.stream.Collectors;

public class SearchService implements ISearchService {

    private final IRepository<IConceptDiagnosis> diagnosisRepository;
    private final IRepository<IMissionTrip> missionTripRepository;
    private final IRepository<IPatient> patientRepository;
    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private final IRepository<ISystemSetting> systemSettingRepository;
    private final IItemModelMapper itemModelMapper;
    private final IRepository<IPatientPrescriptionReplacement> patientPrescriptionReplacementRepository;
    private final IRepository<IMissionCity> cityRepository;

    @Inject
    public SearchService(IRepository<IConceptDiagnosis> diagnosisRepository,
                         IRepository<IMissionTrip> missionTripRepository,
                         IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                         IRepository<IPatientPrescription> patientPrescriptionRepository,
                         IRepository<ISystemSetting> systemSettingRepository,
                         IRepository<IPatientPrescriptionReplacement> patientPrescriptionReplacementRepository,
                         IRepository<IMissionCity> cityRepository,
                         @Named("identified") IItemModelMapper itemModelMapper) {

        this.diagnosisRepository = diagnosisRepository;
        this.missionTripRepository = missionTripRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.systemSettingRepository = systemSettingRepository;
        this.itemModelMapper = itemModelMapper;
        this.patientPrescriptionReplacementRepository = patientPrescriptionReplacementRepository;
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
            Integer weeksPregnant = QueryHelper.findWeeksPregnant(patientEncounterVitalRepository, recentEncounter.getId());

            String ageClassification = null;
            if (recentEncounter.getPatientAgeClassification() != null){
                ageClassification = recentEncounter.getPatientAgeClassification().getName();
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
                    ageClassification
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

        ExpressionList<PatientEncounter> patientEncounterQuery = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("id", encounterId);

        try {
            IPatientEncounter patientEncounter = patientEncounterRepository.findOne(patientEncounterQuery);
            IPatient patient = patientEncounter.getPatient();
            Integer patientHeightFeet = QueryHelper.findPatientHeightFeet(patientEncounterVitalRepository, patientEncounter.getId());
            Integer patientHeightInches = QueryHelper.findPatientHeightInches(patientEncounterVitalRepository, patientEncounter.getId());
            Float patientWeight = QueryHelper.findPatientWeight(patientEncounterVitalRepository, patientEncounter.getId());
            Integer weeksPregnant = QueryHelper.findWeeksPregnant(patientEncounterVitalRepository, patientEncounter.getId());

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
                    ageClassification
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
        ExpressionList<PatientEncounter> patientEncounterQuery = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("id", encounterId);

        try {
            IPatientEncounter patientEncounter = patientEncounterRepository.findOne(patientEncounterQuery);
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
            PatientEncounterItem patientEncounterItem = itemModelMapper.createPatientEncounterItem(currentPatientEncounter);
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
    public ServiceResponse<List<PatientEncounterItem>> retrievePatientEncounterItemsByPatientId(int patientId) {
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
    public ServiceResponse<List<PrescriptionItem>> retrieveUnreplacedPrescriptionItems(int encounterId) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();
        ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery()
                .where()
                .eq("encounter_id", encounterId);
        try {
            List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(query);
            List<PrescriptionItem> prescriptionItems = patientPrescriptions
                    .stream()
                    .filter(pp -> pp.getPatientPrescriptionReplacements() == null || pp.getPatientPrescriptionReplacements().size() == 0)
                    .map(pp -> itemModelMapper.createPrescriptionItem(
                            pp.getId(),
                            pp.getMedication().getName(),
                            null,
                            pp.getPhysician().getFirstName(),
                            pp.getPhysician().getLastName(),
                            pp.getConceptPrescriptionAdministration(),
                            pp.getAmount(),
                            pp.getMedication(),
                            null,
                            pp.isCounseled()

                    ))
                    .collect(Collectors.toList());

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
    public ServiceResponse<List<PrescriptionItem>> retrieveDispensedPrescriptionItems(int encounterId) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();

        ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery()
                .fetch("patientEncounter")
                .where()
                .eq("encounter_id", encounterId)
                .ne("user_id_pharmacy", null);

        try {
            List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(query);
            List<PrescriptionItem> prescriptionItems = patientPrescriptions.stream()
                    .filter(pp -> pp.getDateDispensed() != null)
                    .map(pp -> itemModelMapper.createPrescriptionItem(
                            pp.getId(),
                            pp.getMedication().getName(),
                            null,
                            pp.getPhysician().getFirstName(),
                            pp.getPhysician().getLastName(),
                            pp.getConceptPrescriptionAdministration(),
                            pp.getAmount(),
                            pp.getMedication(),
                            null,
                            pp.isCounseled()
                    ))
                    .collect(Collectors.toList());
            List<PrescriptionItem> replacedPrescriptions = patientPrescriptions.stream()
                    .filter(pp -> pp.getPatientPrescriptionReplacements().size() > 0)
                    .map(pp -> itemModelMapper.createPrescriptionItem(
                            pp.getId(),
                            pp.getPatientPrescriptionReplacements().get(0).getReplacementPrescription().getMedication().getName(),
                            pp.getMedication().getName(),
                            pp.getPhysician().getFirstName(),
                            pp.getPhysician().getLastName(),
                            pp.getConceptPrescriptionAdministration(),
                            pp.getAmount(),
                            pp.getMedication(),
                            null,
                            pp.isCounseled()
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
        //TODO: filter these by the current country of the team
        Query<Patient> query = null;
        if (id != null) {
            //if we have an id, that is all we need.
            //this is the most ideal scenario
            query = QueryProvider.getPatientQuery()
                    .where()
                    .eq("id", id)
                    .isNull("isDeleted")
                    .order()
                    .desc("id");

        } else if (StringUtils.isNotNullOrWhiteSpace(firstName) && StringUtils.isNotNullOrWhiteSpace(lastName)) {
            //if we have a first and last name
            //this is the second most ideal scenario
            query = QueryProvider.getPatientQuery()
                    .where()
                    .eq("first_name", firstName)
                    .eq("last_name", lastName)
                    .isNull("isDeleted")
                    .order()
                    .desc("id");

        } else if (StringUtils.isNotNullOrWhiteSpace(firstOrLastName)) {
            //if we have a word that could either be a first name or a last name
            query = QueryProvider.getPatientQuery()
                    .where()
                    .or(
                            Expr.eq("first_name", firstOrLastName),
                            Expr.eq("last_name", firstOrLastName))
                    .isNull("isDeleted")
                    .order()
                    .desc("id");

        } else {
            response.addError("", "too many parameters in query");
        }

        //Execute the query
        try {
            List<? extends IPatient> patients = patientRepository.find(query);
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
                        null
                ));
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
                allPatients = QueryHelper.findPatients(
                        patientRepository,
                        missionTrip.getMissionCity().getMissionCountry().getName()
                );
            } else {

                //Otherwise we can just get ALL OF THE PATIENTS EVER MADE
                allPatients = QueryHelper.findPatients(patientRepository);
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
                        null
                );

                if (patient.getPhoto() != null) {
                    currPatient.setPathToPhoto("/photo/patient/" + currPatient.getId() + "?showDefault=false");
                } else {
                    // If no photo for patient, show default
                    currPatient.setPathToPhoto("/photo/patient/" + currPatient.getId() + "?showDefault=true");
                }

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
