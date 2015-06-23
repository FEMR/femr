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


import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import femr.data.models.mysql.*;
import femr.data.models.mysql.research.ResearchEncounter;
import femr.data.models.mysql.research.ResearchEncounterVital;

/**
 * Responsible for providing all queries
 */
public class QueryProvider {

    public static Query<Medication> getMedicationQuery() {
        return Ebean.find(Medication.class);
    }

    public static Query<MedicationMeasurementUnit> getMedicationMeasurementUnitQuery() {
        return Ebean.find(MedicationMeasurementUnit.class);
    }

    public static Query<MedicationActiveDrugName> getMedicationActiveDrugNameQuery() {
        return Ebean.find(MedicationActiveDrugName.class);
    }

    public static Query<MedicationForm> getMedicationFormQuery() {
        return Ebean.find(MedicationForm.class);
    }

    public static Query<MissionTrip> getMissionTripQuery() { return Ebean.find(MissionTrip.class);}

    public static Query<MissionCity> getMissionCityQuery() { return Ebean.find(MissionCity.class);}

    public static Query<MissionCountry> getMissionCountryQuery() { return Ebean.find(MissionCountry.class);}

    public static Query<MissionTeam> getMissionTeamQuery() { return Ebean.find(MissionTeam.class);}

    public static Query<PatientEncounterPhoto> getPatientEncounterPhotoQuery() {
        return Ebean.find(PatientEncounterPhoto.class);
    }

    public static Query<PatientPrescription> getPatientPrescriptionQuery() {
        return Ebean.find(PatientPrescription.class);
    }

    public static Query<Photo> getPhotoQuery() {
        return Ebean.find(Photo.class);
    }

    public static Query<SystemSetting> getSystemSettingQuery() {
        return Ebean.find(SystemSetting.class);
    }

    public static Query<Role> getRoleQuery() {
        return Ebean.find(Role.class);
    }

    public static Query<User> getUserQuery() {
        return Ebean.find(User.class);
    }


    // Research
    public static Query<ResearchEncounter> getResearchEncounterQuery() {
        return Ebean.find(ResearchEncounter.class);
    }
}
