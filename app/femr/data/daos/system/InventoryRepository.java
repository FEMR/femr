package femr.data.daos.system;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IInventoryRepository;
import femr.data.models.core.IMedicationInventory;
import femr.data.models.mysql.MedicationInventory;

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

        IMedicationInventory newMedicationInventory = findInventory(medicationId, tripId);
        if (newMedicationInventory != null) {
            //the inventory already exists, don't create a new one.
            newMedicationInventory = null;
        } else {
            //the inventory does not exist, create a new one!
            newMedicationInventory = dataModelMapper.createMedicationInventory(quantity, quantity, medicationId, tripId);
            Ebean.save(newMedicationInventory);
        }

        return newMedicationInventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedicationInventory retrieveInventoryByMedicationIdAndTripId(int medicationId, int tripId){

        return findInventory(medicationId, tripId);
    }


    /**
     * Searches for inventory. Returns null if it doesn't exist.
     *
     * @param medicationId id of the medication
     * @param tripId id of the trip
     * @return the MedicationInventory object if it exists, otherwise null
     */
    private IMedicationInventory findInventory(int medicationId, int tripId){

        ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                .where()
                .eq("medication.id", medicationId)
                .eq("missionTrip.id", tripId);

        IMedicationInventory medicationInventory = medicationInventoryExpressionList.findUnique();

        return medicationInventory;
    }
}
