package femr.data.daos.core;

import femr.data.models.core.IConceptPrescriptionAdministration;

import java.util.List;

public interface IPrescriptionRepository {

    /**
     * Returns all available ways concept prescription administration methods. i.e. "BID", "q4h", etc.. and their
     * modifier rules
     * @return List of concept prescription administrations from db
     */
    List<? extends IConceptPrescriptionAdministration> retrieveAllConceptPrescriptionAdministrations();
}
