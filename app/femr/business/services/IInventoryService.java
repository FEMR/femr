package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.business.dtos.MedicationItem;

import java.util.List;

public interface IInventoryService {
    /**
     * Adds a list of medicine to the inventory
     *
     * @param medicationItems list of MedicationItems to save
     * @return the same list of MedicationItems WITHOUT primary key (id)
     */
    ServiceResponse<List<MedicationItem>> createMedicationInventory(List<MedicationItem> medicationItems);

    /**
     * Removes a medicine from the inventory
     *
     * @param id id of medication to remove
     * @return true if successful, false if failed
     */
    ServiceResponse<Boolean> removeMedicationFromInventory(int id);

    /**
     * Gets all medicine in the inventory
     *
     * @return list of MedicationItems for use by controller
     */
    ServiceResponse<List<MedicationItem>> getMedicationInventory();
}
