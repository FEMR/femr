package femr.data.daos.system;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IInventoryRepository;
import femr.data.models.core.IMedicationInventory;
import femr.data.models.mysql.MedicationInventory;
import play.Logger;

public class InventoryRepository implements IInventoryRepository {

    private IDataModelMapper dataModelMapper;

    @Inject
    public InventoryRepository(IDataModelMapper dataModelMapper){

        this.dataModelMapper = dataModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedicationInventory createInventoryWithMedicationIdAndTripIdAndQuantity(int medicationId, int tripId, int quantity) {

        IMedicationInventory newMedicationInventory;
        try {

            newMedicationInventory = findInventory(medicationId, tripId);

            if (newMedicationInventory != null) {
                //the inventory already exists, don't create a new one.
                newMedicationInventory = null;
            } else {
                //the inventory does not exist, create a new one!
                newMedicationInventory = dataModelMapper.createMedicationInventory(quantity, quantity, medicationId, tripId);
                Ebean.save(newMedicationInventory);
            }
        } catch (Exception ex) {

            Logger.error("InventoryRepository-createInventoryWithMedicationIdAndTripIdAndQuantity", ex.getMessage(), "medicationId=" + medicationId, "tripId=" + tripId, "quantity=" + quantity);
            throw ex;
        }

        return newMedicationInventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedicationInventory retrieveInventoryByMedicationIdAndTripId(int medicationId, int tripId){

        IMedicationInventory medicationInventory;
        try{

            medicationInventory = findInventory(medicationId, tripId);
        } catch (Exception ex){

            Logger.error("InventoryRepository-retrieveInventoryByMedicationIdAndTripId", ex.getMessage(), "medicationId=" + medicationId, "tripId=" + tripId);
            throw ex;
        }

        return medicationInventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedicationInventory updateInventoryQuantityInitial(int inventoryId, int quantityInitial){

        IMedicationInventory medicationInventory;
        try {

            medicationInventory = findInventory(inventoryId);

            if (medicationInventory == null) {
                //there's nothing here to be done
            } else {
                //there's something here to be done
                medicationInventory.setQuantityInitial(quantityInitial);
                Ebean.save(medicationInventory);
            }
        } catch (Exception ex) {

            Logger.error("InventoryRepository-updateInventoryQuantityInitial", ex.getMessage(), "inventoryId=" + inventoryId, "quantityInitial=" + quantityInitial);
            throw ex;
        }

        return medicationInventory;
    }


    /**
     * Searches for inventory by inventory id. Returns null if it doesn't exist.
     *
     * @param inventoryId id of the inventory, not null
     * @return the MedicationInventory object if it exists, otherwise null
     */
    private IMedicationInventory findInventory(int inventoryId){

        IMedicationInventory medicationInventory;
        try {

            ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                    .where()
                    .eq("id", inventoryId);

            medicationInventory = medicationInventoryExpressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("InventoryRepository-findInventory", ex.getMessage(), "inventoryId=" + inventoryId);
            throw ex;
        }

        return medicationInventory;
    }

    /**
     * Searches for inventory by medicationId/tripId. Returns null if it doesn't exist.
     *
     * @param medicationId id of the medication
     * @param tripId id of the trip
     * @return the MedicationInventory object if it exists, otherwise null
     */
    private IMedicationInventory findInventory(int medicationId, int tripId){

        IMedicationInventory medicationInventory;
        try {
            ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                    .where()
                    .eq("medication.id", medicationId)
                    .eq("missionTrip.id", tripId);

            medicationInventory = medicationInventoryExpressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("InventoryRepository-findInventory", ex.getMessage(), "medicationId=" + medicationId, "tripId" + tripId);
            throw ex;
        }

        return medicationInventory;
    }
}
