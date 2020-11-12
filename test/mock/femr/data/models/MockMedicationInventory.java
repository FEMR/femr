package mock.femr.data.models;

import femr.data.models.core.IMedication;
import femr.data.models.core.IMedicationInventory;
import femr.data.models.core.IMissionTrip;
import org.joda.time.DateTime;

public class MockMedicationInventory implements IMedicationInventory {

    private int id = -1;
    private int quantityCurrent = 1;
    private int quantityInitial = 0;

    public int getId() { return id; }

    public Integer getQuantityCurrent() { return quantityCurrent; }

    public void setQuantityCurrent(Integer quantityCurrent) {}

    public Integer getQuantityInitial() { return quantityInitial; }

    public void setQuantityInitial(Integer quantityInitial) {}

    public IMedication getMedication() {
        MockMedication medication = new MockMedication();
        return medication;
    }

    public void setMedication(IMedication medication) {}

    public IMissionTrip getMissionTrip() { return null; }

    public void setMissionTrip(IMissionTrip missionTrip){}

    public DateTime getIsDeleted() { return null; }

    public void setIsDeleted(DateTime isDeleted) {}

    public DateTime getTimeAdded() { return null; }

    public void setTimeAdded(DateTime timeAdded) {}

    public Integer getCreatedBy() { return null; }

    public void setCreatedBy(Integer createdBy) {

    }
}
