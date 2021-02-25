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


import femr.data.models.mysql.RankedPatientMatch;
import io.ebean.Ebean;
import io.ebean.Query;
import femr.data.models.mysql.*;
import femr.data.models.mysql.concepts.ConceptMedication;
import femr.data.models.mysql.concepts.ConceptMedicationForm;
import femr.data.models.mysql.concepts.ConceptMedicationUnit;
import femr.data.models.mysql.research.ResearchEncounter;
import femr.data.models.mysql.research.ResearchEncounterVital;

/**
 * Responsible for providing all queries
 */
public class QueryProvider {

    public static Query<ChiefComplaint> getChiefComplaintQuery() {
        return Ebean.find(ChiefComplaint.class);
    }

    public static Query<Medication> getMedicationQuery() {
        return Ebean.find(Medication.class);
    }

    public static Query<ConceptMedicationUnit> getConceptMedicationUnitQuery() {
        return Ebean.find(ConceptMedicationUnit.class);
    }

    public static Query<MedicationGeneric> getMedicationGenericQuery() {
        return Ebean.find(MedicationGeneric.class);
    }

    public static Query<ConceptMedicationForm> getConceptMedicationFormQuery() {
        return Ebean.find(ConceptMedicationForm.class);
    }

    public static Query<ConceptMedication> getConceptMedicationQuery(){
        return Ebean.find(ConceptMedication.class);
    }

    public static Query<MedicationInventory> getMedicationInventoryQuery() {
        return Ebean.find(MedicationInventory.class);
    }

    public static Query<MissionTrip> getMissionTripQuery() {
        return Ebean.find(MissionTrip.class);
    }

    public static Query<MissionCity> getMissionCityQuery() {
        return Ebean.find(MissionCity.class);
    }

    public static Query<MissionCountry> getMissionCountryQuery() {
        return Ebean.find(MissionCountry.class);
    }

    public static Query<MissionTeam> getMissionTeamQuery() {
        return Ebean.find(MissionTeam.class);
    }

    public static Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }

    public static Query<RankedPatientMatch> getRankedPatientMatchQuery() {
        return Ebean.find(RankedPatientMatch.class);
    }

    public static Query<PatientAgeClassification> getPatientAgeClassificationQuery() {
        return Ebean.find(PatientAgeClassification.class);
    }

    public static Query<PatientEncounter> getPatientEncounterQuery() {
        return Ebean.find(PatientEncounter.class);
    }

    public static Query<PatientEncounterPhoto> getPatientEncounterPhotoQuery() {
        return Ebean.find(PatientEncounterPhoto.class);
    }

    public static Query<PatientEncounterTabField> getPatientEncounterTabFieldQuery() {
        return Ebean.find(PatientEncounterTabField.class);
    }

    public static Query<PatientEncounterVital> getPatientEncounterVitalQuery() {
        return Ebean.find(PatientEncounterVital.class);
    }

    public static Query<PatientPrescription> getPatientPrescriptionQuery() {
        return Ebean.find(PatientPrescription.class);
    }

    public static Query<PatientPrescriptionReplacement> getPatientPrescriptionReplacementQuery(){
        return Ebean.find(PatientPrescriptionReplacement.class);
    }

    public static Query<PatientPrescriptionReplacementReason> getPatientPrescriptionReasonQuery(){
        return Ebean.find(PatientPrescriptionReplacementReason.class);
    }

    public static Query<Photo> getPhotoQuery() {
        return Ebean.find(Photo.class);
    }

    public static Query<SystemSetting> getSystemSettingQuery() {
        return Ebean.find(SystemSetting.class);
    }

    public static Query<Tab> getTabQuery() {
        return Ebean.find(Tab.class);
    }

    public static Query<TabField> getTabFieldQuery() {
        return Ebean.find(TabField.class);
    }

    public static Query<TabFieldType> getTabFieldTypeQuery() {
        return Ebean.find(TabFieldType.class);
    }

    public static Query<TabFieldSize> getTabFieldSizeQuery() {
        return Ebean.find(TabFieldSize.class);
    }

    public static Query<Role> getRoleQuery() {
        return Ebean.find(Role.class);
    }

    public static Query<User> getUserQuery() {
        return Ebean.find(User.class);
    }

    public static Query<Vital> getVitalQuery() {
        return Ebean.find(Vital.class);
    }


    // Research
    public static Query<ResearchEncounter> getResearchEncounterQuery() {
        return Ebean.find(ResearchEncounter.class);
    }

    public static Query<ResearchEncounterVital> getResearchEncounterVitalQuery() {
        return Ebean.find(ResearchEncounterVital.class);
    }
}
