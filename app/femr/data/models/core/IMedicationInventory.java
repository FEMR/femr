package femr.data.models.core;

import femr.data.models.mysql.Medication;
import femr.data.models.mysql.MissionTrip;

/**
 * Keeps track of inventory for a target medication and trip.
 */
public interface IMedicationInventory {
    int getId();

    Integer getQuantity_current();

    void setQuantity_current(Integer quantity_current);

    Integer getQuantity_total();

    void setQuantity_total(Integer quantity_initial);

    Medication getMedication();

    void setMedication(Medication medication);

    MissionTrip getMissionTrip();

    void setMissionTrip(MissionTrip missionTrip);
}
