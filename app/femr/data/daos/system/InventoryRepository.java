package femr.data.daos.system;

import com.avaje.ebean.ExpressionList;
import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IInventoryRepository;
import femr.data.models.core.IMedicationInventory;
import femr.data.models.mysql.MedicationInventory;

public class InventoryRepository implements IInventoryRepository {

    public IMedicationInventory createInventoryByMedicationIdAndTripId(int tripId, int medicationId){




        return null;
    }

    public IMedicationInventory retrieveInventoryByMedicationIdAndTripId(int medicationId, int tripId){

        ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                .where()
                .eq("medication.id", medicationId)
                .eq("missionTrip.id", tripId);

        IMedicationInventory medicationInventory = medicationInventoryExpressionList.findUnique();

        return medicationInventory;
    }
}
