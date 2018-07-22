package femr.data.daos.system;

import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IPrescriptionRepository;
import femr.data.models.core.IConceptPrescriptionAdministration;
import femr.data.models.core.IPatientPrescription;
import femr.data.models.core.IPatientPrescriptionReplacement;
import femr.data.models.core.IPatientPrescriptionReplacementReason;
import femr.data.models.mysql.PatientPrescription;
import femr.data.models.mysql.PatientPrescriptionReplacementReason;
import femr.data.models.mysql.concepts.ConceptPrescriptionAdministration;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepository implements IPrescriptionRepository {

    private final IDataModelMapper dataModelMapper;
    @Inject
    public PrescriptionRepository(IDataModelMapper dataModelMapper){
        this.dataModelMapper = dataModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientPrescription createPrescription(Integer amount, int medicationId, Integer medicationAdministrationId, int userId, int encounterId) {

        IPatientPrescription patientPrescription;

        try {
            patientPrescription = dataModelMapper.createPatientPrescription(
                    amount,
                    medicationId,
                    medicationAdministrationId,
                    userId,
                    encounterId,
                    null,
                    false);

            Ebean.save(patientPrescription);
        } catch (Exception ex) {
            Logger.error("PrescriptionRepository-createPrescription", ex.getMessage(), ex);
            throw ex;
        }

        return patientPrescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientPrescriptionReplacement> createPrescriptionReplacements(List<? extends IPatientPrescriptionReplacement> prescriptionReplacements) {

        try {
            Ebean.saveAll(prescriptionReplacements);

        } catch (Exception ex) {

            Logger.error("PrescriptionRepository-createPrescriptionReplacements", ex.getMessage(), ex);
            throw ex;
        }
        return prescriptionReplacements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IConceptPrescriptionAdministration> retrieveAllConceptPrescriptionAdministrations() {

        List<? extends IConceptPrescriptionAdministration> conceptPrescriptionAdministrations;
        try{

            conceptPrescriptionAdministrations = Ebean.find(ConceptPrescriptionAdministration.class).findList();
        } catch (Exception ex) {

            Logger.error("PrescriptionRepository-retrieveAllConceptPrescriptionAdministrations", ex.getMessage());
            throw ex;
        }

        return conceptPrescriptionAdministrations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientPrescription> retrieveAllDispensedPrescriptionsByEncounterId(int encounterId) {
        List<? extends IPatientPrescription> patientPrescriptions;
        try {
            ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery()
                    .fetch("patientEncounter")
                    .where()
                    .isNull("patientEncounter.isDeleted")
                    .eq("encounter_id", encounterId)
                    .ne("user_id_pharmacy", null);
            patientPrescriptions = query.findList();
        } catch (Exception ex) {

            Logger.error("PrescriptionRepository-retrieveAllDispensedPrescriptionsByEncounterId", ex.getMessage());
            throw ex;
        }

        return patientPrescriptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientPrescriptionReplacementReason retrieveReplacementReasonByName(String name) {
        IPatientPrescriptionReplacementReason patientPrescriptionReplacementReason;
        try {
            ExpressionList<PatientPrescriptionReplacementReason> replacementReasonExpressionList = QueryProvider.getPatientPrescriptionReasonQuery()
                    .where()
                    .eq("name", name);
            patientPrescriptionReplacementReason = replacementReasonExpressionList.findOne();
        } catch (Exception ex) {
            Logger.error("PrescriptionRepository-retrieveReplacementReasonByName", ex.getMessage());
            throw ex;
        }

        return patientPrescriptionReplacementReason;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientPrescription> retrieveUnreplacedPrescriptionsByEncounterId(int encounterId) {
        List<? extends IPatientPrescription> patientPrescriptions;
        try {
            ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery()
                    .fetch("medication.medicationInventory" )
                    .fetch("patientEncounter")
                    .where()
                    .isNull("patientEncounter.isDeleted")
                    .eq("encounter_id", encounterId);
            patientPrescriptions = query.findList();
        } catch (Exception ex) {

            Logger.error("PrescriptionRepository-retrieveUnreplacedPrescriptionsByEncounterId", ex.getMessage());
            throw ex;
        }

        return patientPrescriptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientPrescription retrievePrescriptionById(int prescriptionId) {

        IPatientPrescription patientPrescription;
        try {

            ExpressionList<PatientPrescription> prescriptionExpressionList = QueryProvider.getPatientPrescriptionQuery()
                    .where()
                    .eq("id", prescriptionId);
            patientPrescription = prescriptionExpressionList.findOne();
        } catch (Exception ex) {

            Logger.error("PrescriptionRepository-retrievePrescriptionById", ex.getMessage(), ex);
            throw ex;
        }

        return patientPrescription;
    }

    @Override
    public IPatientPrescription updatePrescription(IPatientPrescription patientPrescription) {

        if (patientPrescription == null)
            return null;

        try {

            Ebean.save(patientPrescription);
        } catch (Exception ex) {

            Logger.error("PrescriptionRepository-updatePrescription", ex.getMessage(), ex);
            throw ex;
        }

        return patientPrescription;
    }
}
