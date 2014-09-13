package femr.business.helpers;

import com.typesafe.config.ConfigFactory;
import femr.data.models.IPatientEncounter;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;

import java.io.File;

public class LogicDoer {

    /**
     * Attempts to retrieve the user specified photo path.
     * If nothing is found, defaults to ../Upload/Pictures/Patients
     *
     * @return path to the photo
     */
    public static String getPatientPhotoPath() {
        String path;
        try {
            path = ConfigFactory.load().getString("photos.path");
            if (!path.endsWith(File.separator))
                path += File.separator;
            return path;
        } catch (Exception ex) {
            //If config doesn't exist, default to "photos"
            path = "../Upload/Pictures/Patients";
            return path;
        }

    }

    /**
     * Attempts to retrieve the user specified photo path.
     * If nothing is found, defaults to ../Upload/Pictures/PatientEncounters
     *
     * @return path to the photo
     */
    public static String getMedicalPhotoPath() {
        String path;
        try {
            path = ConfigFactory.load().getString("photos.encounterPath");
            if (!path.endsWith(File.separator))
                path += File.separator;
            return path;
        } catch (Exception ex) {
            path = "../Upload/Pictures/PatientEncounters";
            return path;
        }
    }

    public static boolean isEncounterClosed(IPatientEncounter patientEncounter) {
        DateTime dateOfMedicalVisit = patientEncounter.getDateOfMedicalVisit();
        DateTime dateOfPharmacyVisit = patientEncounter.getDateOfPharmacyVisit();
        Boolean isClosed = false;
        DateTime dateNow = dateUtils.getCurrentDateTime();

        if (dateOfPharmacyVisit != null) {
            isClosed = true;

        } else if (dateOfMedicalVisit != null) {
            //give 1 day before closing
            DateTime dayAfterMedicalVisit = dateOfMedicalVisit.plusDays(1);
            if (dateNow.isAfter(dayAfterMedicalVisit)) {
                isClosed = true;
            }

        } else {
            //give 2 days before closing
            DateTime dayAfterAfterEncounter = patientEncounter.getDateOfTriageVisit().plusDays(2);
            if (dateNow.isAfter(dayAfterAfterEncounter)) {
                isClosed = true;
            }
        }
        return isClosed;
    }
}
