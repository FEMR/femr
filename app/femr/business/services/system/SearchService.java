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
import femr.business.services.core.ISearchService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.daos.core.IPatientRepository;
import femr.data.daos.core.IPatientEncounterRepository;
import femr.data.daos.core.IVitalRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.calculations.LocaleUnitConverter;
import femr.util.stringhelpers.StringUtils;

import java.util.*;

public class SearchService implements ISearchService {

    private final IRepository<IDiagnosis> diagnosisRepository;
    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private final IRepository<ISystemSetting> systemSettingRepository;
    private final IPatientRepository patientRepository;
    private final IPatientEncounterRepository patientEncounterRepository;
    private final IItemModelMapper itemModelMapper;
    private final IVitalRepository vitalRepository;

    @Inject
    public SearchService(IRepository<IDiagnosis> diagnosisRepository,
                         IRepository<IPatientPrescription> patientPrescriptionRepository,
                         IRepository<ISystemSetting> systemSettingRepository,
                         IPatientRepository patientRepository,
                         @Named("identified") IItemModelMapper itemModelMapper,
                         IPatientEncounterRepository patientEncounterRepository,
                         IVitalRepository vitalRepository) {

        this.diagnosisRepository = diagnosisRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.systemSettingRepository = systemSettingRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.itemModelMapper = itemModelMapper;
        this.vitalRepository = vitalRepository;
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

            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.findPatientEncounterByIdOrderByDateOfTriageVisitDesc(patientId);

            if (patientEncounters.size() < 1) throw new Exception();

            IPatientEncounter recentEncounter = patientEncounters.get(0);
            IPatient savedPatient = patientEncounters.get(0).getPatient();


            Integer currentPatientHeightFeet = null;
            Integer currentPatientHeightInches = null;
            Float currentPatientWeight = null;

            List<? extends IPatientEncounterVital> encounterHeightFeetValues = vitalRepository.findHeightFeetValues(recentEncounter.getId());
            List<? extends IPatientEncounterVital> encounterHeightInchesValues = vitalRepository.findHeightInchesValues(recentEncounter.getId());
            List<? extends IPatientEncounterVital> encounterWeightValues = vitalRepository.findWeightValues(recentEncounter.getId());
            if (encounterHeightFeetValues.size() > 0) {
                currentPatientHeightFeet = Math.round(encounterHeightFeetValues.get(0).getVitalValue());
            }
            if (encounterHeightInchesValues.size() > 0) {
                currentPatientHeightInches = Math.round(encounterHeightInchesValues.get(0).getVitalValue());
            }
            if (encounterWeightValues.size() > 0) {
                currentPatientWeight = encounterWeightValues.get(0).getVitalValue();
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
                    recentEncounter.getWeeksPregnant(),
                    currentPatientHeightFeet,
                    currentPatientHeightInches,
                    currentPatientWeight,
                    pathToPhoto,
                    photoId
            );

            //TODO: why is this being repeated?
            if (savedPatient.getPhoto() != null) {
                patientItem.setPathToPhoto("/photo/patient/" + patientId + "?showDefault=false");
            }

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
    public ServiceResponse<PatientItem> retrievePatientItemByEncounterId(int encounterId) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (encounterId < 0) {
            response.addError("", "invalid id");
            return response;
        }

        try {

            IPatientEncounter patientEncounter = patientEncounterRepository.findPatientEncounterById(encounterId);

            IPatient patient = patientEncounter.getPatient();


            Integer currentPatientHeightFeet = null;
            Integer currentPatientHeightInches = null;
            Float currentPatientWeight = null;
            List<? extends IPatientEncounterVital> encounterHeightFeetValues = vitalRepository.findHeightFeetValues(patientEncounter.getId());
            List<? extends IPatientEncounterVital> encounterHeightInchesValues = vitalRepository.findHeightInchesValues(patientEncounter.getId());
            List<? extends IPatientEncounterVital> encounterWeightValues = vitalRepository.findWeightValues(patientEncounter.getId());
            if (encounterHeightFeetValues.size() > 0) {
                currentPatientHeightFeet = Math.round(encounterHeightFeetValues.get(0).getVitalValue());
            }
            if (encounterHeightInchesValues.size() > 0) {
                currentPatientHeightInches = Math.round(encounterHeightInchesValues.get(0).getVitalValue());
            }
            if (encounterWeightValues.size() > 0) {
                currentPatientWeight = encounterWeightValues.get(0).getVitalValue();
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
                    patientEncounter.getWeeksPregnant(),
                    currentPatientHeightFeet,
                    currentPatientHeightInches,
                    currentPatientWeight,
                    pathToPhoto,
                    photoId
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

            IPatientEncounter patientEncounter = patientEncounterRepository.findPatientEncounterById(encounterId);
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
            //TODO: fix this to work as descending like the others
            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.findPatientEncounterByIdOrderByDateOfTriageVisitAsc(patientId);
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

        try {
            List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.findPatientEncounterByIdOrderByDateOfTriageVisitDesc(patientId);

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
                .eq("encounter_id", encounterId)
                .eq("replacement_id", null);
        try {
            List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(query);
            List<PrescriptionItem> prescriptionItems = new ArrayList<>();

            for (IPatientPrescription pp : patientPrescriptions) {

                prescriptionItems.add(
                        itemModelMapper.createPrescriptionItem(
                                pp.getId(),
                                pp.getMedication().getName(),
                                pp.getReplacementId(),
                                pp.getPhysician().getFirstName(),
                                pp.getPhysician().getLastName()
                        )
                );
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
    public ServiceResponse<List<PrescriptionItem>> retrieveDispensedPrescriptionItems(int encounterId) {
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

                prescriptionItems.add(
                        itemModelMapper.createPrescriptionItem(
                                pp.getId(),
                                pp.getMedication().getName(),
                                pp.getReplacementId(),
                                pp.getPhysician().getFirstName(),
                                pp.getPhysician().getLastName()
                        )
                );
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


        List<IPatient> patients = new ArrayList<>();
        if (id != null) {
            //if we have an id, that is all we need.
            //this is the most ideal scenario
            IPatient patient = patientRepository.findPatientById(id);
            patients.add(patient);

        } else if (StringUtils.isNotNullOrWhiteSpace(firstName) && StringUtils.isNotNullOrWhiteSpace(lastName)) {
            //if we have a first and last name
            //this is the second most ideal scenario
            List<? extends IPatient> patientsResponse = patientRepository.findPatientsByFirstNameAndLastName(firstName, lastName);
            for (IPatient patient : patientsResponse)
                patients.add(patient);


        } else if (StringUtils.isNotNullOrWhiteSpace(firstOrLastName)) {
            //if we have a word that could either be a first name or a last name
            List<? extends IPatient> patientsResponse = patientRepository.findPatientsByFirstNameOrLastName(firstOrLastName);
            for (IPatient patient : patientsResponse)
                patients.add(patient);
        } else {
            response.addError("", "too many parameters in query");
        }

        //Execute the query
        try {

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
                        photoId
                ));

                response.setResponseObject(patientItems);

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
    public ServiceResponse<List<PatientItem>> retrievePatientsForSearch() {
        ServiceResponse<List<PatientItem>> response = new ServiceResponse<>();

        try {
            List<? extends IPatient> allPatients = patientRepository.findAllPatients();
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
                        photoId
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

            List<? extends IDiagnosis> allDiagnoses = diagnosisRepository.findAll(Diagnosis.class);
            List<String> diagnoses = new ArrayList<>();

            for (IDiagnosis d : allDiagnoses) {
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
}
