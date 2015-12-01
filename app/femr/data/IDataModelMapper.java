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
package femr.data;

import femr.data.models.core.*;
import org.joda.time.DateTime;
import java.util.Date;
import java.util.List;

public interface IDataModelMapper {

    /**
     * Generate and provide an implementation of IChiefComplaint.
     *
     * @param value              the chief complaint itself, not null
     * @param patientEncounterId id of the encounter that the chief complaint belongs to, not null
     * @param sortOrder          the order in which the chief complaint is sorted(when dealing with >1 chief complaint), may be null
     * @return an implementation of IChiefComplaint or null if processing fails
     */
    IChiefComplaint createChiefComplaint(String value, int patientEncounterId, Integer sortOrder);

    /**
     * Creates a login attempt
     *
     * @param usernameValue the value that the device/person submitted to the server as a username, not null
     * @param isSuccessful whether or not the login attempt was successful, not null
     * @param ipAddress the ip address of the device trying to log in - in binary form, not null
     * @param userId id of the user account that is trying to be logged into, may be null
     * @return a new login attempt
     */
    ILoginAttempt createLoginAttempt(String usernameValue, boolean isSuccessful, byte[] ipAddress, Integer userId);

    /**
     * Generate and provide an implementation of IMedication.
     *
     * @param name name of the medication, not null
     * @return an implementation of IMedication or null if processing fails
     */
    IMedication createMedication(String name);

    /**
     * Generate and provide an implementation of IMedication for use in adding to inventory.
     *
     * @param name                  name of the medication, not null
     * @param medicationActiveDrugs active drugs in the medication, may be null
     * @param medicationForm        the medications form e.g. cream/chewable/pill, may be null
     * @return an implementation of IMedication or null if processing fails
     */
    IMedication createMedication(String name, List<IMedicationActiveDrug> medicationActiveDrugs, IMedicationForm medicationForm);

    /**
     * Generate and provide an implementation of IMedicationActiveDrug.
     *
     * @param value                    strength of the drug, not null
     * @param isDenominator            is the drug a denominator, not null
     * @param activeDrugUnitId         id of the unit for measurement of the drug, not null
     * @param medicationActiveDrugName the drug name, may be null
     * @return an implementation of IMedicationActiveDrug
     */
    IMedicationActiveDrug createMedicationActiveDrug(int value, boolean isDenominator, int activeDrugUnitId, IMedicationActiveDrugName medicationActiveDrugName);

    /**
     * Generate and provide an implementation of IMedicationActiveDrugName.
     *
     * @param name name of the drug, not null
     * @return an implementation of IMedicationActiveDrugName or null if processing fails
     */
    IMedicationActiveDrugName createMedicationActiveDrugName(String name);

    /**
     * Generate and provide an implementation of IMedicationForm.
     *
     * @param name name of the form e.g. cream/chewable/pill, not null
     * @return an implementation of IMedicationForm or null if processing fails
     */
    IMedicationForm createMedicationForm(String name);

    /**
     * Generate and provide an implementation of IMedicationInventory.
     *
     * @param quantityCurrent current available amount of medication, not null
     * @param quantityTotal amount of medication initially in the inventory, not null
     * @param medicationId id of the medication, not null
     * @param missionTripId id of the mission trip, not null
     * @return an implementation of IMedicationInventory or null if processing fails
     */
    IMedicationInventory createMedicationInventory(int quantityCurrent, int quantityTotal, int medicationId, int missionTripId);

    /**
     * Generate and provide an implementation of IMissionCity.
     *
     * @param name           name of the city, not null
     * @param missionCountry the country model, not null
     * @return an implementation of IMissionCity or null if processing fails
     */
    IMissionCity createMissionCity(String name, IMissionCountry missionCountry);

    /**
     * Generate and provide an implementation of IMissionTeam.
     *
     * @param name        the team name, not null
     * @param location    where the team is based out of, may be null
     * @param description a description of the team, may be null
     * @return an implementation of IMissionTeam or null if processing fails
     */
    IMissionTeam createMissionTeam(String name, String location, String description);

    /**
     * Generate and provide an implementation of IMissionTrip.
     *
     * @param startDate   start date of the trip, not null
     * @param endDate     end date of the trip, not null
     * @param missionCity the city where the trip is taking place, not null
     * @param missionTeam the country where the trip is taking place, not null
     * @return an implementation of IMissionTrip or null if processing fails
     */
    IMissionTrip createMissionTrip(Date startDate, Date endDate, IMissionCity missionCity, IMissionTeam missionTeam);

    /**
     * Generate and provide an implementation of IPatient.
     *
     * @param userID    id of the user creating the patient, not null
     * @param firstName first name of the patient, not null
     * @param lastName  last name of the patient, not null
     * @param birthday  the patients birthday, may be null
     * @param sex       the sex of the patient, may be null
     * @param address   the address of the patients residence, may be null
     * @param city      the city of the patient, may be null
     * @param photoID   the id of a photo of the patient, may be null
     * @return an implementation of IPatient or null if processing fails
     */
    IPatient createPatient(int userID, String firstName, String lastName, Date birthday, String sex, String address, String city, Integer photoID);

    /**
     * Generate and provide an implementation of IPatientEncounter.
     *
     * @param patientID                  id of the patient, not null
     * @param date                       date of checking for triage, not null
     * @param userId                     id of the user creating the encounter, not null
     * @param patientAgeClassificationId id of the age classification, may be null
     * @param tripId                     id of the trip, may be null
     * @return an implementation of IPatientEncounter or null if processing fails
     */
    IPatientEncounter createPatientEncounter(int patientID, DateTime date, int userId, Integer patientAgeClassificationId, Integer tripId);

    /**
     * Generate and provide an implementation of IPatientEncounterTabField
     *
     * @param tabFieldId       id of the field, not null
     * @param userId           id of the user creating the field, not null
     * @param value            value of the field, not null
     * @param encounterId      id of the encounter, not null
     * @param dateTaken        date the field was recorded, not null
     * @param chiefComplaintId id of the chief complaint, may be null
     * @return an implementation of IPatientEncounterTabfield or null if processing fails
     */
    IPatientEncounterTabField createPatientEncounterTabField(int tabFieldId, int userId, String value, int encounterId, DateTime dateTaken, Integer chiefComplaintId);

    /**
     * Generate and provide an implementation of IPatientEncounterVital
     *
     * @param encounterId id of the encounter, not null
     * @param userId      id of the user creating the vital value, not null
     * @param time        when the vital was recorded, not null
     * @param vitalID     id of the vital field, not null
     * @param value       value of the vital, not null
     * @return an implementation of IPatientEncounterVital or null if processing fails
     */
    IPatientEncounterVital createPatientEncounterVital(int encounterId, int userId, String time, int vitalID, float value);

    /**
     * Generate and provide an implementation of IPatientPrescription
     *
     * @param amount        amount of medication dispensed, not null
     * @param medicationId    the id of the dispensed medication, not null
     * @param medicationAdministrationId  ID of Administration type of the prescription
     * @param userId        id of the user creating the prescription, not null
     * @param encounterId   encounter id of the prescription, not null
     * @param dateDispensed   date and time the patient prescription dispensed to the patient, can be null
     * @return an implementation of IPatientPrescription or null if processing fails, not null
     */
    IPatientPrescription createPatientPrescription(int amount, int medicationId, Integer medicationAdministrationId, int userId, int encounterId, DateTime dateDispensed, boolean isCounseled);

    /**
     * Creates a patient prescription replacement based on the ID of the prescriptions.
     *
     * @param originalId id of the original prescription, not null
     * @param replacementId id of the prescription that is replacing the original prescription, not null
     * @param reasonId id of the reason for replacement, not null
     * @return a new patient prescription replacement item.
     */
    IPatientPrescriptionReplacement createPatientPrescriptionReplacement(int originalId, int replacementId, int reasonId);

    /**
     * Generate and provide an implementation of IPhoto.
     *
     * @param description description of the photo, may be null
     * @param filePath    path to the file, not null
     * @return an implementation of IPhoto or null if processing fails
     */
    IPhoto createPhoto(String description, String filePath);

    /**
     * Generate and provide an implementation of IPhoto.
     *
     * @param name name of the role, may be null
     * @return an implementation of IRole
     */
    IRole createRole(String name);

    /**
     * Generate and provide an implementation of ITab.
     *
     * @param date      date of creation, not null
     * @param leftSize  left column size, not null
     * @param rightSize right column size, not null
     * @param name      name of the tab, not null
     * @param isDeleted is the tab deleted, not null
     * @param userId    id of the user creating the tab, not null
     * @return an implementation of ITab or null if processing fails
     */
    ITab createTab(DateTime date, int leftSize, int rightSize, String name, boolean isDeleted, int userId);

    /**
     * Generate and provide an implementation of ITabField
     *
     * @param name           name of the field, not null
     * @param order          order of the field, may be null
     * @param placeholder    placeholder for the field, may be null
     * @param isDeleted      is the field deleted, not null
     * @param tabFieldSizeID id of {@link femr.data.models.core.ITabFieldSize}, not null
     * @param tabFieldTypeID id of {@link femr.data.models.core.ITabFieldType}, not null
     * @param tabID          id of {@link femr.data.models.core.ITab}, not null
     * @return an implementation of ITabField or null if processing fails
     */
    ITabField createTabField(String name, Integer order, String placeholder, boolean isDeleted, int tabFieldSizeID, int tabFieldTypeID, int tabID);

    /**
     * Generate and provide an implementation of IUser
     *
     * @param firstName       first name of the user, not null
     * @param lastName        last name of the user, may be null
     * @param email           email address of the user, not null
     * @param date            date of last login, not null
     * @param notes           notes about who the user is, may be null
     * @param password        password for the user, not null
     * @param isDeleted       is the user deleted, not null
     * @param isPasswordReset is the users password marked for a reset the next time they log in, not null
     * @param roles           a list of roles, must have at least one, not null
     * @param userId          id of the user responsible for creating new users, not null
     * @return an implementation of IUser or null if processing fails
     */
    IUser createUser(String firstName, String lastName, String email, DateTime date, String notes, String password, boolean isDeleted, boolean isPasswordReset, List<? extends IRole> roles, int userId);

    /**
     * Provider a reference object for a user
     *
     * @param userId id of the user, not null
     * @return a User reference object
     */
    IUser createUser(int userId);
    
    /**
     * Uses the user provider to update a patient encounter with diabetes screening information
     *
     * @param patientEncounter the patient encounter to be updated, not null
     * @param diabetesScreenerId the id of the physician that screened the patient for diabetes, not null
     * @return a PatientEncounter ready to be updated with the date of screening and the physician that did the screening
     */
    IPatientEncounter updatePatientEncounterWithDiabetesScreening(IPatientEncounter patientEncounter, int diabetesScreenerId);

}
