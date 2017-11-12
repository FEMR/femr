package femr.data.models.mysql;

import femr.data.models.core.IMedicationInventory;
import femr.data.models.core.IMedication;
import femr.data.models.core.IMissionTrip;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "medication_inventories")
public class MedicationInventory implements IMedicationInventory{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "quantity_current", unique = false, nullable = false)
    private Integer quantityCurrent;
    @Column(name = "quantity_initial", unique = false, nullable = false)
    private Integer quantityInitial;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mission_trip_id")
    private MissionTrip missionTrip;

    @Column(name = "isDeleted", nullable = true)
    private DateTime isDeleted;

    @Column(name = "timeAdded")
    private DateTime timeAdded;

    @Column(name = "createdBy")
    private Integer createdBy;

    @Override
    public int getId() {
        return id;
    }

    public Integer getQuantityCurrent() {
        return quantityCurrent;
    }

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
        this.medication = (Medication) medication;
    }

    @Override
    public IMissionTrip getMissionTrip() {

        return missionTrip;
    }

    @Override
    public void setMissionTrip(IMissionTrip missionTrip) { this.missionTrip = (MissionTrip) missionTrip; }

    @Override
    public DateTime getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(DateTime isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public DateTime getTimeAdded() { return timeAdded; }

    @Override
    public void setTimeAdded(DateTime timeAdded) { this.timeAdded = timeAdded; }

    @Override
    public Integer getCreatedBy() { return createdBy; }

    @Override
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }
}
