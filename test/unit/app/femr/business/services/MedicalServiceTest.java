//package unit.app.femr.business.services;
//
//import femr.business.dtos.ServiceResponse;
//import femr.business.services.IMedicalService;
//import femr.business.services.MedicalService;
//import femr.common.models.IPatientEncounterHpiField;
//import femr.common.models.IPatientEncounterPmhField;
//import femr.common.models.IPatientEncounterTreatmentField;
//import femr.common.models.IPatientPrescription;
//import mock.femr.data.daos.MockRepository;
//import mock.femr.data.models.MockPatientEncounterHpiField;
//import mock.femr.data.models.MockPatientEncounterPmhField;
//import mock.femr.data.models.MockPatientEncounterTreatmentField;
//import mock.femr.data.models.MockPatientPrescription;
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
//public class MedicalServiceTest {
//
//    private IMedicalService medicalService;
//    private MockRepository<IPatientEncounterTreatmentField> mockPatientEncounterTreatmentFieldRepository;
//    private MockRepository<IPatientEncounterHpiField> mockPatientEncounterHpiFieldRepository;
//    private MockRepository<IPatientEncounterPmhField> mockPatientEncounterPmhFieldRepository;
//    private MockRepository<IPatientPrescription> mockPatientPrescriptionRepository;
//
//    @Before
//    public void setUp() {
//        mockPatientEncounterTreatmentFieldRepository = new MockRepository<>();
//        mockPatientEncounterHpiFieldRepository = new MockRepository<>();
//        mockPatientEncounterPmhFieldRepository = new MockRepository<>();
//        mockPatientPrescriptionRepository = new MockRepository<>();
//
//        medicalService = new MedicalService(mockPatientEncounterTreatmentFieldRepository, mockPatientEncounterHpiFieldRepository, mockPatientEncounterPmhFieldRepository, mockPatientPrescriptionRepository);
//    }
//
//    //region **createPatientEncounterTreatmentFields**
//    @Test
//    public void CreatePatientEncounterTreatmentFields_CreateWithCorrectParameters_Accepted() {
//        List<MockPatientEncounterTreatmentField> expectedPatientEncounterTreatmentFields = new ArrayList<>();
//        MockPatientEncounterTreatmentField patientEncounterTreatmentField = new MockPatientEncounterTreatmentField();
//        patientEncounterTreatmentField.setId(42);
//        expectedPatientEncounterTreatmentFields.add(patientEncounterTreatmentField);
//        patientEncounterTreatmentField = new MockPatientEncounterTreatmentField();
//        patientEncounterTreatmentField.setId(43);
//        expectedPatientEncounterTreatmentFields.add(patientEncounterTreatmentField);
//        assertThat(mockPatientEncounterTreatmentFieldRepository.createAllWasCalled).isFalse();
//        medicalService.createPatientEncounterTreatmentFields(expectedPatientEncounterTreatmentFields);
//        assertThat(mockPatientEncounterTreatmentFieldRepository.createAllWasCalled).isTrue();
//        assertThat(mockPatientEncounterTreatmentFieldRepository.entitiesPassedIn).isEqualTo(expectedPatientEncounterTreatmentFields);
//    }
//
//    @Test
//    public void CreatePatientEncounterTreatmentFields_FailedSave_ServiceResponseContainsError() {
//        List<MockPatientEncounterTreatmentField> badPatientEncounterTreatmentFields = null;
//        assertThat(mockPatientEncounterTreatmentFieldRepository.createAllWasCalled).isFalse();
//        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> response = medicalService.createPatientEncounterTreatmentFields(badPatientEncounterTreatmentFields);
//        assertThat(mockPatientEncounterTreatmentFieldRepository.createAllWasCalled).isTrue();
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("patientEncounterTreatmentField")).isTrue();
//        assertThat(response.getErrors().containsValue("Failed to save"));
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void CreatePatientEncounterTreatmentFields_SuccessfulSave_ServiceResponseContainsPatientEncounterTreatmentField(){
//        List<MockPatientEncounterTreatmentField> goodPatientEncounterTreatmentFields = new ArrayList<>();
//        MockPatientEncounterTreatmentField patientEncounterTreatmentField = new MockPatientEncounterTreatmentField();
//        patientEncounterTreatmentField.setId(42);
//        goodPatientEncounterTreatmentFields.add(patientEncounterTreatmentField);
//        patientEncounterTreatmentField = new MockPatientEncounterTreatmentField();
//        patientEncounterTreatmentField.setId(43);
//        goodPatientEncounterTreatmentFields.add(patientEncounterTreatmentField);
//        assertThat(mockPatientEncounterTreatmentFieldRepository.createAllWasCalled).isFalse();
//        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> response = medicalService.createPatientEncounterTreatmentFields(goodPatientEncounterTreatmentFields);
//        assertThat(mockPatientEncounterTreatmentFieldRepository.createAllWasCalled).isTrue();
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getErrors().size()).isEqualTo(0);
//        assertThat(response.getResponseObject()).isEqualTo(goodPatientEncounterTreatmentFields);
//    }
//    //endregion
//
//    //region **createPatientPrescriptions**
//    @Test
//    public void CreatePatientPrescriptions_CreateWithCorrectParameters_Accepted() {
//        List<MockPatientPrescription> expectedPatientPrescriptions = new ArrayList<>();
//        MockPatientPrescription patientPrescription = new MockPatientPrescription();
//        patientPrescription.setId(42);
//        expectedPatientPrescriptions.add(patientPrescription);
//        patientPrescription = new MockPatientPrescription();
//        patientPrescription.setId(43);
//        expectedPatientPrescriptions.add(patientPrescription);
//        assertThat(mockPatientPrescriptionRepository.createAllWasCalled).isFalse();
//        medicalService.createPatientPrescriptions(expectedPatientPrescriptions);
//        assertThat(mockPatientPrescriptionRepository.createAllWasCalled).isTrue();
//        assertThat(mockPatientPrescriptionRepository.entitiesPassedIn).isEqualTo(expectedPatientPrescriptions);
//    }
//
//    @Test
//    public void CreatePatientPrescriptions_FailedSave_ServiceResponseContainsError() {
//        List<MockPatientPrescription> badPatientPrescriptions = null;
//        assertThat(mockPatientPrescriptionRepository.createAllWasCalled).isFalse();
//        ServiceResponse<List<? extends IPatientPrescription>> response = medicalService.createPatientPrescriptions(badPatientPrescriptions);
//        assertThat(mockPatientPrescriptionRepository.createAllWasCalled).isTrue();
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("patientPrescription")).isTrue();
//        assertThat(response.getErrors().containsValue("failed to save"));
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void CreatePatientPrescriptions_SuccessfulSave_ServiceResponseContainsPatientEncounterTreatmentField(){
//        List<MockPatientPrescription> goodPatientPrescriptions = new ArrayList<>();
//        MockPatientPrescription patientPrescription = new MockPatientPrescription();
//        patientPrescription.setId(42);
//        goodPatientPrescriptions.add(patientPrescription);
//        patientPrescription = new MockPatientPrescription();
//        patientPrescription.setId(43);
//        goodPatientPrescriptions.add(patientPrescription);
//        assertThat(mockPatientPrescriptionRepository.createAllWasCalled).isFalse();
//        ServiceResponse<List<? extends IPatientPrescription>> response = medicalService.createPatientPrescriptions(goodPatientPrescriptions);
//        assertThat(mockPatientPrescriptionRepository.createAllWasCalled).isTrue();
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getErrors().size()).isEqualTo(0);
//        assertThat(response.getResponseObject()).isEqualTo(goodPatientPrescriptions);
//    }
//    //endregion
//
//    //region **createPatientPrescription**
//    @Test
//    public void CreatePatientPrescription_CreateWithCorrectParameters_Accepted() {
//        MockPatientPrescription expectedPatientPrescription = new MockPatientPrescription();
//        expectedPatientPrescription.setId(42);
//        assertThat(mockPatientPrescriptionRepository.createWasCalled).isFalse();
//        medicalService.createPatientPrescription(expectedPatientPrescription);
//        assertThat(mockPatientPrescriptionRepository.createWasCalled).isTrue();
//        assertThat(mockPatientPrescriptionRepository.entityPassedIn).isEqualTo(expectedPatientPrescription);
//    }
//
//    @Test
//    public void CreatePatientPrescription_FailedSave_ServiceResponseContainsError() {
//        MockPatientPrescription badPatientPrescription = null;
//        assertThat(mockPatientPrescriptionRepository.createWasCalled).isFalse();
//        ServiceResponse<IPatientPrescription> response = medicalService.createPatientPrescription(badPatientPrescription);
//        assertThat(mockPatientPrescriptionRepository.createWasCalled).isTrue();
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("patientPrescription")).isTrue();
//        assertThat(response.getErrors().containsValue("failed to save"));
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void CreatePatientPrescription_SuccessfulSave_ServiceResponseContainsPatientEncounterTreatmentField(){
//        MockPatientPrescription goodPatientPrescription = new MockPatientPrescription();
//        goodPatientPrescription.setId(42);
//        assertThat(mockPatientPrescriptionRepository.createWasCalled).isFalse();
//        ServiceResponse<IPatientPrescription> response = medicalService.createPatientPrescription(goodPatientPrescription);
//        assertThat(mockPatientPrescriptionRepository.createWasCalled).isTrue();
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getErrors().size()).isEqualTo(0);
//        assertThat(response.getResponseObject()).isEqualTo(goodPatientPrescription);
//    }
//    //endregion
//
//    //region **createPatientEncounterHpiFields**
//    @Test
//    public void CreatePatientEncounterHpiFields_CreateWithCorrectParameters_Accepted() {
//        List<MockPatientEncounterHpiField> expectedPatientEncounterHpiFields = new ArrayList<>();
//        MockPatientEncounterHpiField patientEncounterHpiField = new MockPatientEncounterHpiField();
//        patientEncounterHpiField.setId(42);
//        expectedPatientEncounterHpiFields.add(patientEncounterHpiField);
//        patientEncounterHpiField = new MockPatientEncounterHpiField();
//        patientEncounterHpiField.setId(43);
//        expectedPatientEncounterHpiFields.add(patientEncounterHpiField);
//        assertThat(mockPatientEncounterHpiFieldRepository.createAllWasCalled).isFalse();
//        medicalService.createPatientEncounterHpiFields(expectedPatientEncounterHpiFields);
//        assertThat(mockPatientEncounterHpiFieldRepository.createAllWasCalled).isTrue();
//        assertThat(mockPatientEncounterHpiFieldRepository.entitiesPassedIn).isEqualTo(expectedPatientEncounterHpiFields);
//    }
//
//    @Test
//    public void CreatePatientEncounterHpiFields_FailedSave_ServiceResponseContainsError() {
//        List<MockPatientEncounterHpiField> badPatientEncounterHpiFields = null;
//        assertThat(mockPatientEncounterHpiFieldRepository.createAllWasCalled).isFalse();
//        ServiceResponse<List<? extends IPatientEncounterHpiField>> response = medicalService.createPatientEncounterHpiFields(badPatientEncounterHpiFields);
//        assertThat(mockPatientEncounterHpiFieldRepository.createAllWasCalled).isTrue();
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("patientEncounterHpiField")).isTrue();
//        assertThat(response.getErrors().containsValue("Failed to save"));
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void CreatePatientEncounterHpiFields_SuccessfulSave_ServiceResponseContainsPatientEncounterTreatmentField(){
//        List<MockPatientEncounterHpiField> goodPatientEncounterHpiFields = new ArrayList<>();
//        MockPatientEncounterHpiField patientEncounterHpiField = new MockPatientEncounterHpiField();
//        patientEncounterHpiField.setId(42);
//        goodPatientEncounterHpiFields.add(patientEncounterHpiField);
//        patientEncounterHpiField = new MockPatientEncounterHpiField();
//        patientEncounterHpiField.setId(43);
//        goodPatientEncounterHpiFields.add(patientEncounterHpiField);
//        assertThat(mockPatientEncounterHpiFieldRepository.createAllWasCalled).isFalse();
//        ServiceResponse<List<? extends IPatientEncounterHpiField>> response = medicalService.createPatientEncounterHpiFields(goodPatientEncounterHpiFields);
//        assertThat(mockPatientEncounterHpiFieldRepository.createAllWasCalled).isTrue();
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getErrors().size()).isEqualTo(0);
//        assertThat(response.getResponseObject()).isEqualTo(goodPatientEncounterHpiFields);
//    }
//    //endregion
//
//    //region **createPatientEncounterPmhFields**
//    @Test
//    public void CreatePatientEncounterPmhFields_CreateWithCorrectParameters_Accepted() {
//        List<MockPatientEncounterPmhField> expectedPatientEncounterPmhFields = new ArrayList<>();
//        MockPatientEncounterPmhField patientEncounterPmhField = new MockPatientEncounterPmhField();
//        patientEncounterPmhField.setId(42);
//        expectedPatientEncounterPmhFields.add(patientEncounterPmhField);
//        patientEncounterPmhField = new MockPatientEncounterPmhField();
//        patientEncounterPmhField.setId(43);
//        expectedPatientEncounterPmhFields.add(patientEncounterPmhField);
//        assertThat(mockPatientEncounterPmhFieldRepository.createAllWasCalled).isFalse();
//        medicalService.createPatientEncounterPmhFields(expectedPatientEncounterPmhFields);
//        assertThat(mockPatientEncounterPmhFieldRepository.createAllWasCalled).isTrue();
//        assertThat(mockPatientEncounterPmhFieldRepository.entitiesPassedIn).isEqualTo(expectedPatientEncounterPmhFields);
//    }
//
//    @Test
//    public void CreatePatientEncounterPmhFields_FailedSave_ServiceResponseContainsError() {
//        List<MockPatientEncounterPmhField> badPatientEncounterPmhFields = null;
//        assertThat(mockPatientEncounterPmhFieldRepository.createAllWasCalled).isFalse();
//        ServiceResponse<List<? extends IPatientEncounterPmhField>> response = medicalService.createPatientEncounterPmhFields(badPatientEncounterPmhFields);
//        assertThat(mockPatientEncounterPmhFieldRepository.createAllWasCalled).isTrue();
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("patientEncounterPmhField")).isTrue();
//        assertThat(response.getErrors().containsValue("Failed to save"));
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void CreatePatientEncounterPmhFields_SuccessfulSave_ServiceResponseContainsPatientEncounterTreatmentField(){
//        List<MockPatientEncounterPmhField> goodPatientEncounterPmhFields = new ArrayList<>();
//        MockPatientEncounterPmhField patientEncounterPmhField = new MockPatientEncounterPmhField();
//        patientEncounterPmhField.setId(42);
//        goodPatientEncounterPmhFields.add(patientEncounterPmhField);
//        patientEncounterPmhField = new MockPatientEncounterPmhField();
//        patientEncounterPmhField.setId(43);
//        goodPatientEncounterPmhFields.add(patientEncounterPmhField);
//        assertThat(mockPatientEncounterPmhFieldRepository.createAllWasCalled).isFalse();
//        ServiceResponse<List<? extends IPatientEncounterPmhField>> response = medicalService.createPatientEncounterPmhFields(goodPatientEncounterPmhFields);
//        assertThat(mockPatientEncounterPmhFieldRepository.createAllWasCalled).isTrue();
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getErrors().size()).isEqualTo(0);
//        assertThat(response.getResponseObject()).isEqualTo(goodPatientEncounterPmhFields);
//    }
//    //endregion
//
//}
