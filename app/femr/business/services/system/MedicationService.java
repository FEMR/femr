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

import com.avaje.ebean.*;
import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IMedicationService;
import femr.common.UIModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PrescriptionItem;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.IMedication;
import femr.data.models.core.IPatientPrescription;
import femr.data.models.mysql.Medication;
import femr.data.models.mysql.PatientPrescription;
import femr.util.stringhelpers.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MedicationService implements IMedicationService {

    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private final IRepository<IMedication> medicationRepository;
    private final IDataModelMapper dataModelMapper;

    @Inject
    public MedicationService(IRepository<IPatientPrescription> patientPrescriptionRepository,
                             IDataModelMapper dataModelMapper,
                             IRepository<IMedication> medicationRepository) {

        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.dataModelMapper = dataModelMapper;
        this.medicationRepository = medicationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PrescriptionItem> createAndReplacePrescription(PrescriptionItem prescriptionItem, int oldScriptId, int userId, boolean isCounseled) {
        ServiceResponse<PrescriptionItem> response = new ServiceResponse<>();
        if (prescriptionItem == null || StringUtils.isNullOrWhiteSpace(prescriptionItem.getName()) || oldScriptId < 1 || userId < 1) {
            response.addError("", "bad parameters");
            return response;
        }

        try {
            ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery()
                    .where()
                    .eq("id", oldScriptId);

            IPatientPrescription oldPatientPrescription = patientPrescriptionRepository.findOne(query);

            //create new prescription
            IMedication medication = dataModelMapper.createMedication(prescriptionItem.getName());
            IPatientPrescription newPatientPrescription = dataModelMapper.createPatientPrescription(
                    oldPatientPrescription.getAmount(),
                    medication,
                    oldPatientPrescription.getMedicationAdministration().getId(),
                    userId,
                    oldPatientPrescription.getPatientEncounter().getId(),
                    null,
                    true,
                    isCounseled
            );
            newPatientPrescription = patientPrescriptionRepository.create(newPatientPrescription);

            //replace the old prescription
            oldPatientPrescription.setReplacementId(newPatientPrescription.getId());
            patientPrescriptionRepository.update(oldPatientPrescription);

            PrescriptionItem newPrescriptionItem = UIModelMapper.createPrescriptionItem(
                    newPatientPrescription.getId(),
                    newPatientPrescription.getMedication().getName(),
                    newPatientPrescription.getReplacementId(),
                    newPatientPrescription.getPhysician().getFirstName(),
                    newPatientPrescription.getPhysician().getLastName(),
                    newPatientPrescription.getMedicationAdministration().getId(),
                    newPatientPrescription.getMedicationAdministration().getName(),
                    newPatientPrescription.getMedicationAdministration().getDailyModifier(),
                    newPatientPrescription.getAmount()
            );
            response.setResponseObject(newPrescriptionItem);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PrescriptionItem>> createPatientPrescriptions(List<PrescriptionItem> prescriptions, int userId, int encounterId, boolean isDispensed, boolean isCounseled) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();
        if (prescriptions == null || prescriptions.size() < 1 || encounterId < 1) {
            response.addError("", "invalid parameters");
            return response;
        }

        List<IPatientPrescription> patientPrescriptions = new ArrayList<>();
        for (PrescriptionItem script : prescriptions) {
            ExpressionList<Medication> query = QueryProvider.getMedicationQuery()
                    .where()
                    .eq("id", script.getName());
            IMedication medication = medicationRepository.findOne(query);
            patientPrescriptions.add(dataModelMapper.createPatientPrescription(script.getAmount(), medication, script.getAdministrationId(), userId, encounterId, null, isDispensed, isCounseled));
        }

        try {
            List<? extends IPatientPrescription> newPatientPrescriptions = patientPrescriptionRepository.createAll(patientPrescriptions);
            List<PrescriptionItem> newPrescriptionItems = new ArrayList<>();
            for (IPatientPrescription pp : newPatientPrescriptions) {
                if (pp.getMedication() != null)
                    newPrescriptionItems.add(UIModelMapper.createPrescriptionItem(
                            pp.getId(),
                            pp.getMedication().getName(),
                            pp.getReplacementId(),
                            pp.getPhysician().getFirstName(),
                            pp.getPhysician().getLastName(),
                            pp.getMedicationAdministration().getId(),
                            pp.getMedicationAdministration().getName(),
                            pp.getMedicationAdministration().getDailyModifier(),
                            pp.getAmount()
                    ));
            }
            response.setResponseObject(newPrescriptionItems);
        } catch (Exception ex) {

            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PrescriptionItem>> flagPrescriptionsAsFilled(List<Integer> prescriptionIds) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();

        List<PrescriptionItem> updatedPrescriptions = new ArrayList<>();
        try {

            for (Integer i : prescriptionIds) {
                if (i != null && i > 0) {
                    ExpressionList<PatientPrescription> patientPrescriptionExpressionList = QueryProvider.getPatientPrescriptionQuery()
                            .where()
                            .eq("id", i);
                    IPatientPrescription patientPrescription = patientPrescriptionRepository.findOne(patientPrescriptionExpressionList);
                    patientPrescription.setDispensed(true);
                    patientPrescription = patientPrescriptionRepository.update(patientPrescription);
                    updatedPrescriptions.add(UIModelMapper.createPrescriptionItem(
                            patientPrescription.getId(),
                            patientPrescription.getMedication().getName(),
                            patientPrescription.getReplacementId(),
                            patientPrescription.getPhysician().getFirstName(),
                            patientPrescription.getPhysician().getLastName(),
                            patientPrescription.getMedicationAdministration().getId(),
                            patientPrescription.getMedicationAdministration().getName(),
                            patientPrescription.getMedicationAdministration().getDailyModifier(),
                            patientPrescription.getAmount()
                    ));
                }
            }
            response.setResponseObject(updatedPrescriptions);
        } catch (Exception ex) {

            response.addError("", "prescriptions were not updated");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PrescriptionItem>> flagPrescriptionsAsCounseled(List<Integer> prescriptionIds) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();

        List<PrescriptionItem> updatedPrescriptions = new ArrayList<>();
        try {

            for (Integer i : prescriptionIds) {
                if (i != null && i > 0) {
                    ExpressionList<PatientPrescription> patientPrescriptionExpressionList = QueryProvider.getPatientPrescriptionQuery()
                            .where()
                            .eq("id", i);
                    IPatientPrescription patientPrescription = patientPrescriptionRepository.findOne(patientPrescriptionExpressionList);
                    patientPrescription.setCounseled(true);
                    patientPrescription = patientPrescriptionRepository.update(patientPrescription);
                    updatedPrescriptions.add(UIModelMapper.createPrescriptionItem(
                            patientPrescription.getId(),
                            patientPrescription.getMedication().getName(),
                            patientPrescription.getReplacementId(),
                            patientPrescription.getPhysician().getFirstName(),
                            patientPrescription.getPhysician().getLastName(),
                            patientPrescription.getMedicationAdministration().getId(),
                            patientPrescription.getMedicationAdministration().getName(),
                            patientPrescription.getMedicationAdministration().getDailyModifier(),
                            patientPrescription.getAmount()
                    ));
                }
            }
            response.setResponseObject(updatedPrescriptions);
        } catch (Exception ex) {

            response.addError("", "prescriptions were not updated");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> retrieveAllMedications() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();

        try {
            List<String> medicationNames = new ArrayList<>();

            //List<? extends IMedication> medications = medicationRepository.findAll(Medication.class);

            //use raw sql to temporarily filter out the duplicate medication names
            //after implementing the inventory tracking feature, this shouldn't be needed
            //as duplicates will never exist.
            //Also - ebeans "setDistinct" method has known bugs in it hence rawsql crap
            String rawSqlString = "SELECT id, name FROM medications GROUP BY name";
            RawSql rawSql = RawSqlBuilder.parse(rawSqlString).create();

            Query<Medication> medicationQuery = Ebean.find(Medication.class);
            medicationQuery.setRawSql(rawSql);

            List<? extends IMedication> medications = medicationQuery.findList();


            for (IMedication m : medications) {
                medicationNames.add(m.getName());
            }
            response.setResponseObject(medicationNames);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }
}
