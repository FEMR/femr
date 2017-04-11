/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.business.helpers;

import com.typesafe.config.ConfigFactory;
import femr.data.models.core.IPatientEncounter;
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

    public static boolean getUseDbStorageFlag() {
        try
        {
            String sBoolRslt = ConfigFactory.load().getString("photos.useDbStorage");
            return Boolean.parseBoolean(sBoolRslt);
        }
        catch (Exception ex)
        {
            return false;
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

    /**
     * Attempts to retrieve the user specified photo path.
     * If nothing is found, defaults to ../Upload/Csv
     *
     * @return path to the photo
     */
    public static String getCsvFilePath() {
        String path;
        try {
            path = ConfigFactory.load().getString("csv.path");
            if (!path.endsWith(File.separator))
                path += File.separator;
            return path;
        } catch (Exception ex) {
            //If config doesn't exist, default to "photos"
            path = "../Upload/Csv";
            return path;
        }

    }


    /**
     * I wonder what this method does
     *
     * @param patientEncounter patient encounter
     * @return probably returns true if the encounter is closed
     */
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
