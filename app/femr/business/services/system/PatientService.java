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
import femr.business.services.core.IPatientService;
import femr.common.UIModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientItem;
import femr.data.DataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.Patient;
import femr.data.models.mysql.PatientAgeClassification;
import femr.util.stringhelpers.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PatientService implements IPatientService {

    private final IRepository<IPatient> patientRepository;
    private final IRepository<IPatientAgeClassification> patientAgeClassificationRepository;
    private final DataModelMapper dataModelMapper;

    @Inject
    public PatientService(IRepository<IPatient> patientRepository,
                          IRepository<IPatientAgeClassification> patientAgeClassificationRepository,
                          DataModelMapper dataModelMapper) {

        this.patientRepository = patientRepository;
        this.patientAgeClassificationRepository = patientAgeClassificationRepository;
        this.dataModelMapper = dataModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<Map<String, String>> findPossibleAgeClassifications() {
        ServiceResponse<Map<String, String>> response = new ServiceResponse<>();
        Map<String, String> patientAgeClassificationStrings = new LinkedHashMap<>();
        try {
            Query<PatientAgeClassification> patientAgeClassificationExpressionList = QueryProvider.getPatientAgeClassificationQuery()
                    .where()
                    .eq("isDeleted", false)
                    .order()
                    .asc("sortOrder");
            List<? extends IPatientAgeClassification> patientAgeClassifications = patientAgeClassificationRepository.find(patientAgeClassificationExpressionList);
            for (IPatientAgeClassification pac : patientAgeClassifications) {
                patientAgeClassificationStrings.put(pac.getName(), pac.getDescription());
            }
            response.setResponseObject(patientAgeClassificationStrings);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PatientItem> findPatientAndUpdateSex(int id, String sex) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (id < 1) {
            response.addError("", "patient id can not be less than 1");
            return response;
        }

        ExpressionList<Patient> query = QueryProvider.getPatientQuery()
                .where()
                .eq("id", id);

        try {
            IPatient savedPatient = patientRepository.findOne(query);
            //if a patient doesn't have a sex and the
            //user is trying to identify the patients sex
            if (StringUtils.isNullOrWhiteSpace(savedPatient.getSex()) && StringUtils.isNotNullOrWhiteSpace(sex)) {
                savedPatient.setSex(sex);
                savedPatient = patientRepository.update(savedPatient);
            }
            String photoPath = null;
            Integer photoId = null;
            if (savedPatient.getPhoto() != null) {
                photoPath = savedPatient.getPhoto().getFilePath();
                photoId = savedPatient.getPhoto().getId();
            }
            PatientItem patientItem = UIModelMapper.createPatientItem(savedPatient.getId(),
                    savedPatient.getFirstName(),
                    savedPatient.getLastName(),
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
                    photoId);
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
    public ServiceResponse<PatientItem> createPatient(PatientItem patient) {
        ServiceResponse<PatientItem> response = new ServiceResponse<>();
        if (patient == null) {
            response.addError("", "no patient received");
            return response;
        }

        try {
            IPatient newPatient = dataModelMapper.createPatient(patient);
            newPatient = patientRepository.create(newPatient);
            String photoPath = null;
            Integer photoId = null;
            if (newPatient.getPhoto() != null) {
                photoPath = newPatient.getPhoto().getFilePath();
                photoId = newPatient.getPhoto().getId();
            }
            response.setResponseObject(
                    UIModelMapper.createPatientItem(newPatient.getId(),
                            newPatient.getFirstName(),
                            newPatient.getLastName(),
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
                            photoId)
            );
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }
}
