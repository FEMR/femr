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
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IPatientService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientItem;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.*;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;
import play.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class PatientService implements IPatientService {

    private final IPatientRepository patientRepository;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public PatientService(IPatientRepository patientRepository,
                          IDataModelMapper dataModelMapper,
                          @Named("identified") IItemModelMapper itemModelMapper) {

        this.patientRepository = patientRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<Map<String, String>> retrieveAgeClassifications() {

        ServiceResponse<Map<String, String>> response = new ServiceResponse<>();

        Map<String, String> patientAgeClassificationStrings = new LinkedHashMap<>();
        try {


            List<? extends IPatientAgeClassification> patientAgeClassifications = patientRepository.retrieveAllPatientAgeClassifications(false);
            for (IPatientAgeClassification pac : patientAgeClassifications) {

                patientAgeClassificationStrings.put(pac.getName(), pac.getDescription());
            }
            response.setResponseObject(patientAgeClassificationStrings);
        } catch (Exception ex) {

            Logger.error("PatientService-retrieveAgeClassifications", ex);
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> updatePatientPhoneNumber(int id, String phoneNumber) {

        ServiceResponse<PatientItem> response = new ServiceResponse<>();

        try {

            IPatient savedPatient = patientRepository.retrievePatientById(id);

            if (savedPatient == null) {

                response.addError("exception", "Patient not found");
            } else {

                if (StringUtils.isNotNullOrWhiteSpace(phoneNumber)) {

                    savedPatient.setPhoneNumber(phoneNumber);
                    savedPatient = patientRepository.savePatient(savedPatient);
                }
                String photoPath = getPatientPhotoPathOrNull(savedPatient);
                Integer photoId = getPatientPhotoIdOrNull(savedPatient);

                PatientItem patientItem = itemModelMapper.createPatientItem(savedPatient.getId(),
                        savedPatient.getFirstName(),
                        savedPatient.getLastName(),
                        savedPatient.getPhoneNumber(),
                        savedPatient.getCity(),
                        savedPatient.getAddress(),
                        savedPatient.getUserId(),
                        savedPatient.getAge(),
                        savedPatient.getSex(),
                        null,
                        null,
                        null,
                        null,
                        photoPath,
                        photoId,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
                //Osman

                response.setResponseObject(patientItem);
            }
        } catch (Exception ex) {

            Logger.error("PatientService-updatePatientPhoneNumber", ex);
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> updatePatientSex(int id, String sex) {

        ServiceResponse<PatientItem> response = new ServiceResponse<>();

        try {

            IPatient savedPatient = patientRepository.retrievePatientById(id);

            if (savedPatient == null) {

                response.addError("exception", "Patient not found");
            } else {

                if (StringUtils.isNotNullOrWhiteSpace(sex)) {

                    savedPatient.setSex(sex);
                    savedPatient = patientRepository.savePatient(savedPatient);
                }

                String photoPath = getPatientPhotoPathOrNull(savedPatient);
                Integer photoId = getPatientPhotoIdOrNull(savedPatient);

                PatientItem patientItem = itemModelMapper.createPatientItem(savedPatient.getId(),
                        savedPatient.getFirstName(),
                        savedPatient.getLastName(),
                        savedPatient.getPhoneNumber(),
                        savedPatient.getCity(),
                        savedPatient.getAddress(),
                        savedPatient.getUserId(),
                        savedPatient.getAge(),
                        savedPatient.getSex(),
                        null,
                        null,
                        null,
                        null,
                        photoPath,
                        photoId,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

                response.setResponseObject(patientItem);
            }
        } catch (Exception ex) {

            Logger.error("PatientService-updatePatientSex", ex);
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> updatePatientAge(int id, Date age) {

        ServiceResponse<PatientItem> response = new ServiceResponse<>();

        try {

            IPatient savedPatient = patientRepository.retrievePatientById(id);

            if (savedPatient == null) {

                response.addError("exception", "Patient not found");
            } else {
                if (age != null) {

                    savedPatient.setAge(age);
                    savedPatient = patientRepository.savePatient(savedPatient);
                }

                String photoPath = getPatientPhotoPathOrNull(savedPatient);
                Integer photoId = getPatientPhotoIdOrNull(savedPatient);

                PatientItem patientItem = itemModelMapper.createPatientItem(savedPatient.getId(),
                        savedPatient.getFirstName(),
                        savedPatient.getLastName(),
                        savedPatient.getPhoneNumber(),
                        savedPatient.getCity(),
                        savedPatient.getAddress(),
                        savedPatient.getUserId(),
                        savedPatient.getAge(),
                        savedPatient.getSex(),
                        null,
                        null,
                        null,
                        null,
                        photoPath,
                        photoId,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
                //Osman
                response.setResponseObject(patientItem);

            }
        } catch (Exception ex) {

            Logger.error("PatientService-updatePatientAge", ex);
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> updatePatientAddress(int id, String address) {

        ServiceResponse<PatientItem> response = new ServiceResponse<>();

        try {

            IPatient savedPatient = patientRepository.retrievePatientById(id);

            if (savedPatient == null) {

                response.addError("exception", "Patient Not Found");
            } else {

                if (StringUtils.isNotNullOrWhiteSpace(address)) {

                    savedPatient.setAddress(address);
                    savedPatient = patientRepository.savePatient(savedPatient);
                }

                String photoPath = null;
                Integer photoId = null;
                if (savedPatient.getPhoto() != null) {
                    photoPath = savedPatient.getPhoto().getFilePath();
                    photoId = savedPatient.getPhoto().getId();
                }
                PatientItem patientItem = itemModelMapper.createPatientItem(savedPatient.getId(),
                        savedPatient.getFirstName(),
                        savedPatient.getLastName(),
                        savedPatient.getPhoneNumber(),
                        savedPatient.getCity(),
                        savedPatient.getAddress(),
                        savedPatient.getUserId(),
                        savedPatient.getAge(),
                        savedPatient.getSex(),
                        null,
                        null,
                        null,
                        null,
                        photoPath,
                        photoId,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
                //Osman
                response.setResponseObject(patientItem);
            }

        } catch (Exception ex) {

            Logger.error("PatientService-updateAddress", ex);
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> createPatient(PatientItem patient) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (patient == null) {
            response.addError("", "no patient received");
            return response;
        }

        try {
            IPatient newPatient = dataModelMapper.createPatient(patient.getUserId(), patient.getFirstName(), patient.getLastName(), patient.getPhoneNumber(),
                    patient.getBirth(), patient.getSex(), patient.getAddress(), patient.getCity(), patient.getPhotoId());
            newPatient = patientRepository.savePatient(newPatient);

            String photoPath = getPatientPhotoPathOrNull(newPatient);
            Integer photoId = getPatientPhotoIdOrNull(newPatient);

            response.setResponseObject(
                    itemModelMapper.createPatientItem(newPatient.getId(),
                            newPatient.getFirstName(),
                            newPatient.getLastName(),
                            newPatient.getPhoneNumber(),
                            newPatient.getCity(),
                            newPatient.getAddress(),
                            newPatient.getUserId(),
                            newPatient.getAge(),
                            newPatient.getSex(),
                            null,
                            null,
                            null,
                            null,
                            photoPath,
                            photoId,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null)
                    //Osman
            );
        } catch (Exception ex) {

            Logger.error("PatientService-createPatient", ex);
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> deletePatient(int id, int deleteByUserID, String reason) {

        ServiceResponse<PatientItem> response = new ServiceResponse<>();

        if (StringUtils.isNullOrWhiteSpace(reason)) {

            response.addError("", "reason not provided");
            return response;
        }

        try {

            IPatient savedPatient = patientRepository.retrievePatientById(id);
            savedPatient.setIsDeleted(DateTime.now());
            savedPatient.setDeletedByUserId(deleteByUserID);
            savedPatient.setReasonDeleted(reason);
            patientRepository.savePatient(savedPatient);
        } catch (Exception ex) {

            Logger.error("PatientService-deletePatient", ex);
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * Retrieve the patient's photo path or null if it doesn't exist
     *
     * @param patient patient object with or without Photo
     * @return the path to the Photo or null if there's no photo
     */
    private String getPatientPhotoPathOrNull(IPatient patient){

        String photoPath = null;
        if (patient.getPhoto() != null) {
            photoPath = patient.getPhoto().getFilePath();
        }

        return photoPath;
    }

    /**
     * Retrieve the patient's photo ID or null if it doesn't exist
     *
     * @param patient patient object with or without Photo
     * @return the path to the Photo or null if there's no photo
     */
    private Integer getPatientPhotoIdOrNull(IPatient patient){

        Integer photoId = null;
        if (patient.getPhoto() != null){
            photoId = patient.getPhoto().getId();
        }

        return photoId;
    }
}
