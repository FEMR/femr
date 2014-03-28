package femr.common.models.research;

import org.joda.time.DateTime;

/**
 * Research patient prescription interface
 */
public interface IPatientPrescription {

    int getId();

    int getEncounterId();

    void setEncounterId(int encounterId);

    String getMedicationName();

    void setMedicationName(String medicationName);

}
