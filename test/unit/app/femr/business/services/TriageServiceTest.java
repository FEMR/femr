//package unit.app.femr.business.services;
//
//import femr.business.dtos.ServiceResponse;
//import femr.business.services.ITriageService;
//import femr.business.services.TriageService;
//import femr.common.models.IPatient;
//import femr.common.models.IPatientEncounter;
//import femr.common.models.IPatientEncounterVital;
//import mock.femr.data.daos.MockRepository;
//import mock.femr.data.models.MockPatient;
//import mock.femr.data.models.MockPatientEncounter;
//import mock.femr.data.models.MockPatientEncounterVital;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.fest.assertions.Assertions.assertThat;
//
////test naming convention:
////"MethodBeingTested_StateUnderTest_ExpectedOutcome"
//public class TriageServiceTest {
//
//    private ITriageService triageService;
//    private MockRepository<IPatient> mockPatientIRepository;
//    private MockRepository<IPatientEncounter> mockPatientEncounterIRepository;
//    private MockRepository<IPatientEncounterVital> mockPatientEncounterVitalIRepository;
//
//    @Before
//    public void setUp() {
//        mockPatientIRepository = new MockRepository<>();
//        mockPatientEncounterIRepository = new MockRepository<>();
//        mockPatientEncounterVitalIRepository = new MockRepository<>();
//
//        triageService = new TriageService(mockPatientIRepository, mockPatientEncounterIRepository, mockPatientEncounterVitalIRepository);
//    }
//
//    //region **createPatient**
//    @Test
//    public void CreatePatient_CreateWithCorrectParams_Accepted() {
//        MockPatient expectedPatient = new MockPatient();
//        expectedPatient.setId(56);
//        assertThat(mockPatientIRepository.createWasCalled).isFalse();
//        triageService.createPatient(expectedPatient);
//        assertThat(mockPatientIRepository.createWasCalled).isTrue();
//        assertThat(mockPatientIRepository.entityPassedIn).isEqualTo(expectedPatient);
//    }
//
//    @Test
//    public void CreatePatient_FailedSave_ServiceResponseContainsError() {
//        MockPatient badPatient = null;
//        ServiceResponse<IPatient> response = triageService.createPatient(badPatient);
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("patient")).isTrue();
//        assertThat(response.getErrors().containsValue("patient could not be saved to database")).isTrue();
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void CreatePatient_SuccessfulSave_ServiceResponseContainsPatient() {
//        MockPatient goodPatient = new MockPatient();
//        goodPatient.setId(51);
//        ServiceResponse<IPatient> response = triageService.createPatient(goodPatient);
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getErrors().size()).isEqualTo(0);
//        assertThat(response.getResponseObject()).isEqualTo(goodPatient);
//    }
//    //endregion
//
//    //region **updatePatient**
//    @Test
//    public void UpdatePatient_UpdateWithCorrectParams_Accepted() {
//        MockPatient expectedPatient = new MockPatient();
//        expectedPatient.setId(63);
//        assertThat(mockPatientIRepository.updateWasCalled).isFalse();
//        triageService.updatePatient(expectedPatient);
//        assertThat(mockPatientIRepository.updateWasCalled).isTrue();
//        assertThat(mockPatientIRepository.entityPassedIn).isEqualTo(expectedPatient);
//    }
//
//    @Test
//    public void UpdatePatient_FailedSave_ServiceResponseContainsError() {
//        MockPatient badPatient = null;
//        ServiceResponse<IPatient> response = triageService.updatePatient(badPatient);
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("")).isTrue();
//        assertThat(response.getErrors().containsValue("problem updating")).isTrue();
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void UpdatePatient_SuccessfulSave_ServiceResponseContainsPatient() {
//        MockPatient goodPatient = new MockPatient();
//        goodPatient.setId(64);
//        ServiceResponse<IPatient> response = triageService.updatePatient(goodPatient);
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getErrors().size()).isEqualTo(0);
//        assertThat(response.getResponseObject()).isEqualTo(goodPatient);
//    }
//    //endregion
//
//    //region **createPatientEncounter**
//    @Test
//    public void CreatePatientEncounter_CreateWithCorrectParams_Accepted() {
//        MockPatientEncounter expectedPatientEncounter = new MockPatientEncounter();
//        expectedPatientEncounter.setChiefComplaint("it hurts");
//        assertThat(mockPatientEncounterIRepository.createWasCalled).isFalse();
//        triageService.createPatientEncounter(expectedPatientEncounter);
//        assertThat(mockPatientEncounterIRepository.createWasCalled).isTrue();
//        assertThat(mockPatientEncounterIRepository.entityPassedIn).isEqualTo(expectedPatientEncounter);
//    }
//
//    @Test
//    public void CreatePatientEncounter_FailedSave_ServiceResponseContainsError() {
//        MockPatientEncounter badPatientEncounter = null;
//        ServiceResponse<IPatientEncounter> response = triageService.createPatientEncounter(badPatientEncounter);
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("patient encounter")).isTrue();
//        assertThat(response.getErrors().containsValue("patient encounter could not be saved to database")).isTrue();
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void CreatePatientEncounter_SuccessfulSave_ServiceResponseContainsPatientEncounter() {
//        MockPatientEncounter goodPatientEncounter = new MockPatientEncounter();
//        goodPatientEncounter.setChiefComplaint("test complains");
//        ServiceResponse<IPatientEncounter> response = triageService.createPatientEncounter(goodPatientEncounter);
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getErrors().size()).isEqualTo(0);
//        assertThat(response.getResponseObject()).isEqualTo(goodPatientEncounter);
//    }
//    //endregion
//
//    //region **createPatientEncounterVital**
//    @Test
//    public void CreatePatientEncounterVital_CreateWithCorrectParams_Accepted() {
//        MockPatientEncounterVital expectedPatientEncounterVital = new MockPatientEncounterVital();
//        expectedPatientEncounterVital.setVitalValue(12);
//        assertThat(mockPatientEncounterVitalIRepository.createWasCalled).isFalse();
//        triageService.createPatientEncounterVital(expectedPatientEncounterVital);
//        assertThat(mockPatientEncounterVitalIRepository.createWasCalled).isTrue();
//        assertThat(mockPatientEncounterVitalIRepository.entityPassedIn).isEqualTo(expectedPatientEncounterVital);
//    }
//
//    @Test
//    public void CreatePatientEncounterVital_FailedSave_ServiceResponseContainsError() {
//        MockPatientEncounterVital badPatientEncounterVital = null;
//        ServiceResponse<IPatientEncounterVital> response = triageService.createPatientEncounterVital(badPatientEncounterVital);
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("patient encounter vital")).isTrue();
//        assertThat(response.getErrors().containsValue("patient encounter vital could not be saved to database")).isTrue();
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void CreatePatientEncounterVital_SuccessfulSave_ServiceResponseContainsPatientEncounterVital() {
//        MockPatientEncounterVital goodPatientEncounterVital = new MockPatientEncounterVital();
//        goodPatientEncounterVital.setVitalValue(12);
//        ServiceResponse<IPatientEncounterVital> response = triageService.createPatientEncounterVital(goodPatientEncounterVital);
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getErrors().size()).isEqualTo(0);
//        assertThat(response.getResponseObject()).isEqualTo(goodPatientEncounterVital);
//    }
//    //endregion
//
//    //region **createPatientEncounterVitals**
//    @Test
//    public void CreatePatientEncounterVitals_CreateWithCorrectParams_Accepted() {
//        List<MockPatientEncounterVital> expectedPatientEncounterVitals = new ArrayList<>();
//        MockPatientEncounterVital patientEncounterVital = new MockPatientEncounterVital();
//        patientEncounterVital.setVitalValue(10);
//        expectedPatientEncounterVitals.add(patientEncounterVital);
//        patientEncounterVital.setVitalValue(11);
//        expectedPatientEncounterVitals.add(patientEncounterVital);
//        assertThat(mockPatientEncounterVitalIRepository.createAllWasCalled).isFalse();
//        triageService.createPatientEncounterVitals(expectedPatientEncounterVitals);
//        assertThat(mockPatientEncounterVitalIRepository.createAllWasCalled).isTrue();
//        assertThat(mockPatientEncounterVitalIRepository.entitiesPassedIn).isEqualTo(expectedPatientEncounterVitals);
//    }
//
//    @Test
//    public void CreatePatientEncounterVitals_FailedSave_ServiceResponseContainsError() {
//        List<MockPatientEncounterVital> badPatientEncounterVitals = null;
//        assertThat(mockPatientEncounterVitalIRepository.createAllWasCalled).isFalse();
//        ServiceResponse<List<? extends IPatientEncounterVital>> response = triageService.createPatientEncounterVitals(badPatientEncounterVitals);
//        assertThat(mockPatientEncounterVitalIRepository.createAllWasCalled).isTrue();
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("")).isTrue();
//        assertThat(response.getErrors().containsValue("patient encounter vitals could not be saved to database")).isTrue();
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void CreatePatientEncounterVitals_SuccessfulSave_ServiceResponseContainsPatientEncounterVitals() {
//        List<MockPatientEncounterVital> goodPatientEncounterVitals = new ArrayList<>();
//        MockPatientEncounterVital patientEncounterVital = new MockPatientEncounterVital();
//        patientEncounterVital.setVitalValue(10);
//        goodPatientEncounterVitals.add(patientEncounterVital);
//        patientEncounterVital.setVitalValue(11);
//        goodPatientEncounterVitals.add(patientEncounterVital);
//        assertThat(mockPatientEncounterVitalIRepository.createAllWasCalled).isFalse();
//        ServiceResponse<List<? extends IPatientEncounterVital>> response = triageService.createPatientEncounterVitals(goodPatientEncounterVitals);
//        assertThat(mockPatientEncounterVitalIRepository.createAllWasCalled).isTrue();
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getErrors().size()).isEqualTo(0);
//        assertThat(response.getResponseObject()).isEqualTo(goodPatientEncounterVitals);
//    }
//    //endregion
//}
