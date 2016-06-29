package femr.data.daos.core;

import femr.data.models.core.IMedicationInventory;

public interface IInventoryRepository {

    IMedicationInventory createInventoryByMedicationIdAndTripId(int tripId, int medicationId);

    /**
     * Retrieve one inventory entry for a specific trip and medication. There will never be more than one
     * inventory entry for the same trip/medication id combination.
     *
     * @param medicationId id of the medication being inventoried, not null
     * @param tripId id of the trip using the medication, not null
     * @return the MedicationInventory object or null if it does not exist
     */
    IMedicationInventory retrieveInventoryByMedicationIdAndTripId(int medicationId, int tripId);
}
