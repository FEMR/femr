package femr.data.daos.system;

import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IPrescriptionRepository;
import femr.data.models.core.IConceptPrescriptionAdministration;
import femr.data.models.core.IPatientPrescription;
import femr.data.models.mysql.PatientPrescription;
import femr.data.models.mysql.concepts.ConceptPrescriptionAdministration;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import play.Logger;

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
