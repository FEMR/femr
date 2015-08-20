package femr.data.models.mysql;

import femr.data.models.core.IMedicationInventory;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medication_id")
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
    public Medication getMedication() {
        return medication;
    }

    @Override
    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    @Override
    public MissionTrip getMissionTrip() {
        return missionTrip;
    }

    @Override
    public void setMissionTrip(MissionTrip missionTrip) {
        this.missionTrip = missionTrip;
    }
}
