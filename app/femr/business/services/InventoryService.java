package femr.business.services;

import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import femr.business.DomainMapper;
import femr.business.QueryProvider;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IMedication;
import femr.data.daos.IRepository;
import femr.data.models.Medication;
import femr.business.dtos.MedicationItem;

import java.util.ArrayList;
import java.util.List;

public class InventoryService implements IInventoryService {
    private IRepository<IMedication> medicationRepository;
    private DomainMapper domainMapper;

    @Inject
    public InventoryService(IRepository<IMedication> medicationRepository,
                            DomainMapper domainMapper) {
        this.medicationRepository = medicationRepository;
        this.domainMapper = domainMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<MedicationItem>> createMedicationInventory(List<MedicationItem> medicationItems) {
        ServiceResponse<List<MedicationItem>> response = new ServiceResponse<>();
        if (medicationItems == null) {
            response.addError("", "bad parameter");
            return response;
        }

        List<IMedication> medications = new ArrayList<>();
        for (MedicationItem mi : medicationItems) {
            medications.add(domainMapper.createMedication(mi));
        }

        List<? extends IMedication> newMedications = new ArrayList<>();
        try {
            newMedications = medicationRepository.createAll(medications);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        List<MedicationItem> medicationItemsForResponse = new ArrayList<>();
        for (IMedication m : newMedications) {
            medicationItemsForResponse.add(domainMapper.createMedicationItem(m));
        }
        response.setResponseObject(medicationItemsForResponse);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> removeMedicationFromInventory(int id) {
        ServiceResponse<Boolean> response = new ServiceResponse<>();
        if (id < 1){
            response.addError("", "i cannot be less than 0");
            return response;
        }
        ExpressionList<Medication> query = QueryProvider.getMedicationQuery().where().eq("id", id);

        IMedication medication = medicationRepository.findOne(query);
        if (medication == null) {
            response.addError("", "couldn't find medication");
            return response;
        } else {
            medication.setIsDeleted(true);
            medication = medicationRepository.update(medication);
            if (medication != null && medication.getIsDeleted() == true) {
                response.setResponseObject(true);
            }
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<MedicationItem>> getMedicationInventory() {
        ServiceResponse<List<MedicationItem>> response = new ServiceResponse<>();

        ExpressionList<Medication> query = QueryProvider.getMedicationQuery()
                .where()
                .eq("isDeleted", false);

        List<? extends IMedication> medications;
        try {
            medications = medicationRepository.find(query);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }

        List<MedicationItem> medicationItems = new ArrayList();
        for (IMedication m : medications) {
            medicationItems.add(domainMapper.createMedicationItem(m));
        }
        response.setResponseObject(medicationItems);

        return response;
    }

}
