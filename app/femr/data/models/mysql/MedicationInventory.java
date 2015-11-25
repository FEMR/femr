package femr.data.models.mysql;

import femr.data.models.core.IMedicationInventory;
import femr.data.models.core.IMedication;
import femr.data.models.core.IMedicationInventory;
import femr.data.models.core.IMissionTrip;

import javax.persistence.*;

@Entity
@Table(name = "medication_inventories")
public class MedicationInventory implements IMedicationInventory{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "quantity_current", unique = false, nullable = true)
    private Integer quantity_current;
    @Column(name = "quantity_initial", unique = false, nullable = true)
    private Integer quantity_initial;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "medication_id")
    private Medication medication;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mission_trip_id")
    private MissionTrip missionTrip;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Integer getQuantity_current() {
        return quantity_current;
    }

    @Override
    public void setQuantity_current(Integer quantity_current) {
        this.quantity_current = quantity_current;
    }

    @Override
    public Integer getQuantity_total() {
        return quantity_initial;
    }

    @Override
    public void setQuantity_total(Integer quantity_initial) {
        this.quantity_initial = quantity_initial;
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
    public void setMissionTrip(IMissionTrip missionTrip) {

        this.missionTrip = (MissionTrip) missionTrip;
    }
}
