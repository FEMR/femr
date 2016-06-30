package femr.data.daos.core;

import femr.data.models.core.IMedicationInventory;

import java.util.List;

public interface IInventoryRepository {

    /**
     * Create a new Inventory for a medication. Specify the id of the medication and trip. The quantity
     * will be set in the Initial and Current field.
     *
     * @param medicationId id of the medication for inventory, not null
     * @param tripId id of the trip that the user is/will be on, not null
     * @param quantity quantity of the medication being put in the inventory, not null
     * @return the new MedicationInventory object or null if something went wrong such as an instance of this
     * inventory already existing!
     */
    IMedicationInventory createInventoryWithMedicationIdAndTripIdAndQuantity(int medicationId, int tripId, int quantity);

    /**
     * Toggles the deleted status of an inventory item.
     *
     * @param inventoryId id of the inventory entry, not null
     * @return the updated MedicationInventory object or null if an error occurred
     */
    IMedicationInventory deleteInventory(int inventoryId);

    /**
     * Retrieves all inventory entries for an entire trip.
     *
     * @param tripId id of the trip that the user is/was on, not null
     * @return the MedicationInventory object or null if it does not exist
     */
    List<? extends IMedicationInventory> retrieveAllInventoriesByTripId(int tripId);

    /**
     * Retrieve one inventory entry for a specific trip and medication. There will never be more than one
     * inventory entry for the same trip/medication id combination.
     *
     * @param medicationId id of the medication for inventory, not null
     * @param tripId id of the trip that the user is/was on, not null
     * @return the MedicationInventory object or null if it does not exist
     */
    IMedicationInventory retrieveInventoryByMedicationIdAndTripId(int medicationId, int tripId);

    /**
     * Updates the current quantity of the medication in the inventory. This does not make any changes
     * to the initial quantity.
     *
     * @param inventoryId id of the inventory entry, not null
     * @param quantityCurrent current quantity to update the inventory with, not null
     * @return the updated MedicationInventory object or null if an error occurred
     */
    IMedicationInventory updateInventoryQuantityCurrent(int inventoryId, int quantityCurrent);

    /**
     * Updates the initial quantity of the medication in the inventory. This does not make any changes
     * to the current quantity.
     *
     * @param inventoryId id of the inventory entry, not null
     * @param quantityInitial initial quantity to update the inventory with, not null
     * @return the updated MedicationInventory object or null if an error occurred
     */
    IMedicationInventory updateInventoryQuantityInitial(int inventoryId, int quantityInitial);

}