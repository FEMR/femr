package femr.data.models.core;

import org.joda.time.DateTime;

/**
 * Keeps track of inventory for a target medication and trip.
 */
public interface IMedicationInventory {
    int getId();

    Integer getQuantity_current();

    void setQuantity_current(Integer quantity_current);

    Integer getQuantity_total();

    void setQuantity_total(Integer quantity_initial);

    IMedication getMedication();

    void setMedication(IMedication medication);

    IMissionTrip getMissionTrip();

    void setMissionTrip(IMissionTrip missionTrip);

    DateTime getIsDeleted();

    void setIsDeleted(DateTime isDeleted);

}
