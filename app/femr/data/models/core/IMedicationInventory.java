package femr.data.models.core;

import org.joda.time.DateTime;

/**
 * Keeps track of inventory for a target medication and trip.
 */
public interface IMedicationInventory {
    int getId();

    Integer getQuantityCurrent();

    void setQuantityCurrent(Integer quantityCurrent);

    Integer getQuantityInitial();

    void setQuantityInitial(Integer quantityInitial);

    IMedication getMedication();

    void setMedication(IMedication medication);

    IMissionTrip getMissionTrip();

    void setMissionTrip(IMissionTrip missionTrip);

    DateTime getIsDeleted();

    void setIsDeleted(DateTime isDeleted);

    DateTime getTimeAdded();

    void setTimeAdded(DateTime timeAdded);

    Integer getCreatedBy();

    void setCreatedBy(Integer createdBy);
}
