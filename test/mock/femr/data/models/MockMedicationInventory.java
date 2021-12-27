package mock.femr.data.models;

import femr.data.models.core.IMedication;
import femr.data.models.core.IMedicationInventory;
import femr.data.models.core.IMissionTrip;
import org.joda.time.DateTime;

public class MockMedicationInventory implements IMedicationInventory {

    public int id =12222;
    public String name = "aminhm";
    private Boolean isDeleted = false;
    private Integer quantityCurrent = 100;
    private Integer quantityInitial = 100;
    private IMedication medication = new MockMedication();



    @Override
    public int getId() {
        return id;
    }

    @Override
    public Integer getQuantityCurrent() {
        return quantityCurrent;
    }

    @Override
    public void setQuantityCurrent(Integer quantityCurrent) {
        this.quantityCurrent = quantityCurrent;
    }

    @Override
    public Integer getQuantityInitial() {
        return quantityInitial;
    }

    @Override
    public void setQuantityInitial(Integer quantityInitial) {
        this.quantityInitial = quantityInitial;
    }

    @Override
    public IMedication getMedication() {
        return medication;
    }

    @Override
    public void setMedication(IMedication medication) {
        this.medication=medication;
    }

    @Override
    public IMissionTrip getMissionTrip() {
        return null;
    }

    @Override
    public void setMissionTrip(IMissionTrip missionTrip) {

    }

    @Override
    public DateTime getIsDeleted() {
        return null;
    }

    @Override
    public void setIsDeleted(DateTime isDeleted) {

    }

    @Override
    public DateTime getTimeAdded() {
        return null;
    }

    @Override
    public void setTimeAdded(DateTime timeAdded) {

    }

    @Override
    public Integer getCreatedBy() {
        return null;
    }

    @Override
    public void setCreatedBy(Integer createdBy) {

    }
}
