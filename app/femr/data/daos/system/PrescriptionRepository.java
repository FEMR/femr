package femr.data.daos.system;

import femr.data.daos.core.IPrescriptionRepository;
import femr.data.models.core.IConceptPrescriptionAdministration;
import femr.data.models.mysql.concepts.ConceptPrescriptionAdministration;
import io.ebean.Ebean;
import play.Logger;

import java.util.List;

public class PrescriptionRepository implements IPrescriptionRepository {


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

}
