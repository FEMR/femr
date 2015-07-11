///*
//     fEMR - fast Electronic Medical Records
//     Copyright (C) 2014  Team fEMR
//     fEMR is free software: you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation, either version 3 of the License, or
//     (at your option) any later version.
//     fEMR is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.
//     You should have received a copy of the GNU General Public License
//     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
//     you have any questions, contact <info@teamfemr.org>.
//*/
//package unit.app.femr.business.services;
//
//import com.google.inject.Guice;
//import com.google.inject.Inject;
//import com.google.inject.Injector;
//import com.typesafe.config.Config;
//import com.typesafe.config.ConfigFactory;
//import femr.business.services.core.IPatientService;
//import femr.business.services.system.PatientService;
//import femr.common.dtos.ServiceResponse;
//import femr.common.models.PatientItem;
//import femr.data.IDataModelMapper;
//import femr.data.daos.IRepository;
//import femr.data.models.core.*;
//import femr.data.models.mysql.Patient;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import play.Configuration;
//import play.test.Helpers;
//import play.test.WithApplication;
//import util.dependencyinjection.modules.TestBusinessLayerModule;
//import util.dependencyinjection.modules.TestDataLayerModule;
//import util.dependencyinjection.modules.TestUtilitiesModule;
//import util.runners.GuiceJUnitRunner;
//
//import java.io.File;
//import java.util.Calendar;
//import java.util.Date;
//
//import static org.fest.assertions.Assertions.assertThat;
//
//@RunWith(GuiceJUnitRunner.class)
//@GuiceJUnitRunner.GuiceModules({TestBusinessLayerModule.class, TestDataLayerModule.class, TestUtilitiesModule.class})
//public class PatientServiceTest extends WithApplication{
//
//    private IPatientService patientService;
//    private IRepository<IPatient> mockPatientRepository;
//    private IRepository<IPatientAgeClassification> mockPatientAgeClassificationRepository;
//    private IDataModelMapper mockDataModelMapper;
//
//    @Override
//    protected play.test.FakeApplication provideFakeApplication() {
//
//        Config additionalConfig = ConfigFactory.parseFile(new File("conf/application.dev.conf"));
//        Configuration additionalConfigurations = new Configuration(additionalConfig);
//        return Helpers.fakeApplication(additionalConfigurations.asMap());
//    }
//
//    @Inject
//    public void setService(IPatientService patientService) {
//        this.patientService = patientService;
//    }
//
//
//    @Before
//    public void setUp() throws Exception {
//
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//
//    }
//
//
//    @Test
//    public void CreatePatient_nullPatient_Fails() throws Exception {
//
//        //arrange
//        ServiceResponse<PatientItem> response;
//
//        //act
//        response = patientService.createPatient(null);
//
//        //assert
//        assertThat(response.hasErrors()).isTrue();
//
//        assertThat(response.getResponseObject() != null);
//        //assertThat(mockPatientRepository.findOneWasCalled);
//        //assertThat(mockPatientRepository.updateWasCalled);
//
//    }
//
//
//    @Test
//    public void CreatePatient_patientItem_Returned() throws Exception {
//
//        //arrange
//        ServiceResponse<PatientItem> response;
//        String firstName = "Test";
//        String lastName = "Patient";
//        String city = "Detroit";
//        String address = "1234 Main St.";
//        int userID = 1;
//        Calendar cal = Calendar.getInstance();
//        cal.set(1990, Calendar.JANUARY, 1);
//        Date birth = cal.getTime();
//
//        String sex = "Female";
//
//        PatientItem newPatient = new PatientItem();
//        newPatient.setFirstName(firstName);
//        newPatient.setLastName(lastName);
//        newPatient.setCity(city);
//        newPatient.setAddress(address);
//        newPatient.setBirth(birth);
//        newPatient.setUserId(userID);
//        newPatient.setSex(sex);
//
//        //act
//        response = patientService.createPatient(newPatient);
//
//        //assert
//
//        // Make sure create was called in the repository
//        //assertThat(mockPatientRepository.createWasCalled);
//
//        // Should be no errors returned
//        assertThat(response.getResponseObject()).isNotNull();
//        assertThat(response.hasErrors()).isFalse();
//
//        // Patient Item fields should match what was passed in
//        PatientItem createdPatient = response.getResponseObject();
//        assertThat(createdPatient.getFirstName().equals(firstName)).isTrue();
//        assertThat(createdPatient.getLastName().equals(lastName)).isTrue();
//        assertThat(createdPatient.getCity().equals(city)).isTrue();
//        assertThat(createdPatient.getAddress().equals(address)).isTrue();
//        assertThat(createdPatient.getBirth().compareTo(birth) == 0).isTrue();
//        assertThat(createdPatient.getUserId() == userID).isTrue();
//        assertThat(createdPatient.getSex().equals(sex)).isTrue();
//    }
//
//    @Test
//    public void UpdateSex_nullSex_Fails() throws Exception{
//
//        //arrange
//        ServiceResponse<PatientItem> response;
//
//        //act
//        response = patientService.updateSex(0, null);
//
//        //assert
//        assertThat(response.hasErrors()).isTrue();
//
//    }
//
//    @Test
//    public void UpdateSex_patientItem_Returned() throws Exception{
//
//        // @TODO - finish
//
//        // arrange
//
//        // Make Test Patient
//        ServiceResponse<PatientItem> response;
//        String firstName = "Test";
//        String lastName = "Patient";
//        String city = "Detroit";
//        String address = "1234 Main St.";
//        int userID = 1;
//        Calendar cal = Calendar.getInstance();
//        cal.set(1990, Calendar.JANUARY, 1);
//        Date birth = cal.getTime();
//
//        String sex = "Female";
//
//        IPatient newPatient = new Patient();
//        newPatient.setFirstName(firstName);
//        newPatient.setLastName(lastName);
//        newPatient.setCity(city);
//        newPatient.setAddress(address);
//        newPatient.setAge(birth);
//        newPatient.setUserId(userID);
//        newPatient.setSex(sex);
//
//        // put patient in mock repository, update sex with service
//        //newPatient = mockPatientRepository.create(newPatient);
//        //response = patientService.updateSex(newPatient.getId(), "Male");
//
//        //assert
//
//        // make sure response matches expected
////      assertThat(response.getResponseObject()).isNotNull();
////      assertThat(mockPatientRepository.findOneWasCalled);
////      assertThat(mockPatientRepository.updateWasCalled);
//
//    }
//}