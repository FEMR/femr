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
package mock.femr.data;

import com.google.inject.Inject;
import femr.data.IDataModelMapper;
import femr.data.models.core.*;
import mock.femr.data.models.MockPatient;
import org.joda.time.DateTime;
import java.util.Date;
import java.util.List;

public class MockDataModelMapper implements IDataModelMapper{



    @Inject
    public MockDataModelMapper() {

    }


    @Override
    public IChiefComplaint createChiefComplaint(String value, int patientEncounterId, Integer sortOrder) {
        return null;
    }

    @Override
    public IMedication createMedication(String name) {
        return null;
    }

    @Override
    public IMedication createMedication(String name, List<IMedicationGenericStrength> medicationGenericStrengths, IConceptMedicationForm conceptMedicationForm) {
        return null;
    }

    @Override
    public IMedicationGenericStrength createMedicationGenericStrength(Double value, boolean isDenominator, int activeDrugUnitId, IMedicationGeneric medicationGeneric) {
        return null;
    }

    @Override
    public IMedicationGeneric createMedicationActiveDrugName(String name) {
        return null;
    }

    @Override
    public IConceptMedicationForm createConceptMedicationForm(String name) {
        return null;
    }

    @Override
    public IMedicationInventory createMedicationInventory(int quantityCurrent, int quantityTotal, int medicationId, int missionTripId) {
        return null;
    }

    @Override
    public IMissionCity createMissionCity(String name, IMissionCountry missionCountry) {
        return null;
    }

    @Override
    public IMissionTeam createMissionTeam(String name, String location, String description) {
        return null;
    }

    @Override
    public IMissionTrip createMissionTrip(Date startDate, Date endDate, IMissionCity missionCity, IMissionTeam missionTeam) {
        return null;
    }

    @Override
    public IPatient createPatient(int userID, String firstName, String lastName, String phoneNumber, Date birthday, String sex, String address, String city, Integer photoID) {

        IPatient mockPatient = new MockPatient();
        mockPatient.setId(1);
        mockPatient.setUserId(userID);
        mockPatient.setFirstName(firstName);
        mockPatient.setLastName(lastName);
        mockPatient.setPhoneNumber(phoneNumber);
        mockPatient.setAge(birthday);
        mockPatient.setSex(sex);
        mockPatient.setAddress(address);
        mockPatient.setCity(city);
        //mockPatient.setPhoto();
        return mockPatient;
    }

    @Override
    public IPatientEncounterTabField createPatientEncounterTabField(int tabFieldId, int userId, String value, int encounterId, DateTime dateTaken, Integer chiefComplaintId) {
        return null;
    }

    @Override
    public IPatientEncounterVital createPatientEncounterVital(int encounterId, int userId, String time, int vitalID, float value) {
        return null;
    }

    @Override
    public IPatientPrescription createPatientPrescription(Integer amount, int medicationId, Integer medicationAdministrationId, int userId, int encounterId, DateTime dateDispensed, boolean isCounseled) {
        return null;
    }

    @Override
    public IPatientPrescriptionReplacement createPatientPrescriptionReplacement(int originalId, int replacementId, int reasonId) {
        return null;
    }

    @Override
    public IPhoto createPhoto(String description, String filePath, byte[] photoData) {
        return null;
    }

    @Override
    public IRole createRole(String name) {
        return null;
    }

    @Override
    public ITab createTab(DateTime date, int leftSize, int rightSize, String name, boolean isDeleted, int userId) {
        return null;
    }

    @Override
    public ITabField createTabField(String name, Integer order, String placeholder, boolean isDeleted, int tabFieldSizeID, int tabFieldTypeID, int tabID) {
        return null;
    }

    @Override
    public IUser createUser(String firstName, String lastName, String email, DateTime date, String notes, String password, boolean isDeleted, boolean isPasswordReset, List<? extends IRole> roles, int userId) {
        return null;
    }

    @Override
    public IUser createUser(int userId) {
        return null;
    }
}
