package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.data.models.mysql.MedicationInventory;


public class MedicationInventoryProvider implements Provider<MedicationInventory> {

    @Override
    public MedicationInventory get() {
        return new MedicationInventory();
    }
}

