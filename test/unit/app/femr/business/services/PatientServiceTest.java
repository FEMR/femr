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
package unit.app.femr.business.services;

import com.google.inject.*;
import femr.business.services.core.IPatientService;
import femr.business.services.system.PatientService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientItem;
import femr.data.DataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.util.dependencyinjection.providers.*;
import mock.femr.data.daos.MockRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

public class PatientServiceTest {

    private IPatientService patientService;
    private MockRepository<IPatient> mockPatientRepository;
    private MockRepository<IPatientAgeClassification> mockPatientAgeClassificationRepository;

    private DataModelMapper dataModelMapper;

    @Before
    public void setUp() throws Exception {

        Injector injector = Guice.createInjector(new ServiceTestModule());

        mockPatientRepository = new MockRepository<>();
        mockPatientAgeClassificationRepository = new MockRepository<>();

        dataModelMapper = injector.getInstance(DataModelMapper.class);
        patientService = injector.getInstance(PatientService.class);

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void CreatePatient_nullPatient_Fails() throws Exception {

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.createPatient(null);

        //assert
        assertThat(response.hasErrors()).isTrue();
    }

    @Test
    public void CreatePatient_patientItem_Returned() throws Exception {

        //arrange
        ServiceResponse<PatientItem> response;
        String firstName = "Test";
        String lastName = "Patient";
        String city = "Detroit";
        String address = "1234 Main St.";
        int userID = 1;
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date birth = cal.getTime();

        String sex = "Female";

        PatientItem newPatient = new PatientItem();
        newPatient.setFirstName(firstName);
        newPatient.setLastName(lastName);
        newPatient.setCity(city);
        newPatient.setAddress(address);
        newPatient.setBirth(birth);
        newPatient.setUserId(userID);
        newPatient.setSex(sex);

        //act
        response = patientService.createPatient(newPatient);

        //assert

        // Make sure create was called in the repository
        assertThat(mockPatientRepository.createWasCalled);

        // Should be no errors returned
        assertThat(response.getResponseObject()).isNotNull();
        assertThat(response.hasErrors()).isFalse();

        // Patient Item fields should match what was passed in
        PatientItem createdPatient = response.getResponseObject();
        assertThat(createdPatient.getFirstName().equals(firstName)).isTrue();
        assertThat(createdPatient.getLastName().equals(lastName)).isTrue();
        assertThat(createdPatient.getCity().equals(city)).isTrue();
        assertThat(createdPatient.getAddress().equals(address)).isTrue();
        assertThat(createdPatient.getBirth().compareTo(birth) == 0).isTrue();
        assertThat(createdPatient.getUserId() == userID).isTrue();
        assertThat(createdPatient.getSex().equals(sex)).isTrue();

    }

    @Test
    public void UpdateSex_nullSex_Fails() throws Exception{

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.updateSex(0, null);

        //assert
        assertThat(response.hasErrors()).isTrue();

    }

    @Test
    public void UpdateSex_patientItem_Returned() throws Exception{

        // arrange
        ServiceResponse<PatientItem> response;
        String firstName = "Test";
        String lastName = "Patient";
        String city = "Detroit";
        String address = "1234 Main St.";
        int userID = 1;
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date birth = cal.getTime();

        String sex = "Female";

        PatientItem newPatient = new PatientItem();
        newPatient.setFirstName(firstName);
        newPatient.setLastName(lastName);
        newPatient.setCity(city);
        newPatient.setAddress(address);
        newPatient.setBirth(birth);
        newPatient.setUserId(userID);
        newPatient.setSex(sex);


        // put patient in mock repository, update sex with service
        // make sure response matches expected


        // act


        // assert


    }


    public class ServiceTestModule extends AbstractModule {
        @Override
        protected void configure() {

            bind(new TypeLiteral<IRepository<IPatient>>() {}).to(new TypeLiteral<MockRepository<IPatient>>() {});
            bind(new TypeLiteral<IRepository<IPatientAgeClassification>>() {}).to(new TypeLiteral<MockRepository<IPatientAgeClassification>>() {});

            bind(IChiefComplaint.class).toProvider(ChiefComplaintProvider.class);
            bind(IDiagnosis.class).toProvider(DiagnosisProvider.class);
            bind(IMedication.class).toProvider(MedicationProvider.class);
            bind(IMedicationActiveDrug.class).toProvider(MedicationActiveDrugProvider.class);
            bind(IMedicationActiveDrugName.class).toProvider(MedicationActiveDrugNameProvider.class);
            bind(IMedicationAdministration.class).toProvider(MedicationAdministrationProvider.class);
            bind(IMedicationForm.class).toProvider(MedicationFormProvider.class);
            bind(IMedicationMeasurementUnit.class).toProvider(MedicationMeasurementUnitProvider.class);
            bind(IMissionCity.class).toProvider(MissionCityProvider.class);
            bind(IMissionCountry.class).toProvider(MissionCountryProvider.class);
            bind(IMissionTeam.class).toProvider(MissionTeamProvider.class);
            bind(IMissionTrip.class).toProvider(MissionTripProvider.class);
            bind(IPatient.class).toProvider(PatientProvider.class);
            bind(IPatientAgeClassification.class).toProvider(PatientAgeClassificationProvider.class);
            bind(IPatientEncounter.class).toProvider(PatientEncounterProvider.class);
            bind(IPatientEncounterPhoto.class).toProvider(PatientEncounterPhotoProvider.class);
            bind(IPatientEncounterTabField.class).toProvider(PatientEncounterTabFieldProvider.class);
            bind(IPatientEncounterVital.class).toProvider(PatientEncounterVitalProvider.class);
            bind(IPatientPrescription.class).toProvider(PatientPrescriptionProvider.class);
            bind(IPhoto.class).toProvider(PhotoProvider.class);
            bind(IRole.class).toProvider(RoleProvider.class);
            bind(ISystemSetting.class).toProvider(SystemSettingProvider.class);
            bind(ITab.class).toProvider(TabProvider.class);
            bind(ITabField.class).toProvider(TabFieldProvider.class);
            bind(ITabFieldType.class).toProvider(TabFieldTypeProvider.class);
            bind(ITabFieldSize.class).toProvider(TabFieldSizeProvider.class);
            bind(IUser.class).toProvider(UserProvider.class);
            bind(IVital.class).toProvider(VitalProvider.class);
        }
    }

}