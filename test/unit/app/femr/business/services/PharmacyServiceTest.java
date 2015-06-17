//package unit.app.femr.business.services;
//
//import femr.common.dto.ServiceResponse;
//import femr.business.services.IPharmacyService;
//import femr.business.services.PharmacyService;
//import femr.data.models.IPatientPrescription;
//import mock.femr.data.daos.MockRepository;
//import mock.femr.data.models.MockPatientPrescription;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import static org.fest.assertions.Assertions.assertThat;
//
////test naming convention:
////"MethodBeingTested_StateUnderTest_ExpectedOutcome"
//public class PharmacyServiceTest {
//    private IPharmacyService pharmacyService;
//    private MockRepository<IPatientPrescription> mockPatientPrescriptionIRepository;
//
//    @Before
//    public void setUp(){
//        mockPatientPrescriptionIRepository = new MockRepository<>();
//
//        pharmacyService = new PharmacyService(mockPatientPrescriptionIRepository);
//    }
//
//    //region **findPatientPrescription**
//
//    //Perhaps use a mock non-generic repository?
//    //Need a way to control testing the response of a
//    //good/bad query
//    @Test
//    @Ignore
//    public void FindPatientPrescription_FindWithCorrectParams_Accepted(){
//        assertThat(mockPatientPrescriptionIRepository.findOneWasCalled).isFalse();
//        pharmacyService.findPatientPrescription(42,"tylenol");
//        //assertThat(mockPatientPrescriptionIRepository.findOneWasCalled).isTrue();
//    }
//
//
//
//
//    //endregion
//
//
//    //region **updatePatientPrescription**
//    @Test
//    public void UpdatePatientPrescription_UpdateWithCorrectParams_Accepted(){
//        MockPatientPrescription expectedPatientPrescription = new MockPatientPrescription();
//        expectedPatientPrescription.setId(42);
//        assertThat(mockPatientPrescriptionIRepository.updateWasCalled).isFalse();
//        pharmacyService.updatePatientPrescription(expectedPatientPrescription);
//        assertThat(mockPatientPrescriptionIRepository.updateWasCalled).isTrue();
//        assertThat(mockPatientPrescriptionIRepository.entityPassedIn).isEqualTo(expectedPatientPrescription);
//    }
//
//    @Test
//    public void UpdatePatientPrescription_FailedSave_ServiceResponseContainsError(){
//        MockPatientPrescription badPatientPrescription = null;
//        assertThat(mockPatientPrescriptionIRepository.updateWasCalled).isFalse();
//        ServiceResponse<IPatientPrescription> response = pharmacyService.updatePatientPrescription(badPatientPrescription);
//        assertThat(mockPatientPrescriptionIRepository.updateWasCalled).isTrue();
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("updatePatientEncounter")).isTrue();
//        assertThat(response.getErrors().containsValue("updatePatientEncounter failed")).isTrue();
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void UpdatePatientPrescription_SuccessfulSave_ServiceResponseContainsPatientPrescription(){
//
//        MockPatientPrescription goodPatientPrescription = new MockPatientPrescription();
//        goodPatientPrescription.setId(42);
//        assertThat(mockPatientPrescriptionIRepository.updateWasCalled).isFalse();
//        ServiceResponse<IPatientPrescription> response = pharmacyService.updatePatientPrescription(goodPatientPrescription);
//        assertThat(mockPatientPrescriptionIRepository.updateWasCalled).isTrue();
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getErrors().size()).isEqualTo(0);
//        assertThat(response.getResponseObject()).isEqualTo(goodPatientPrescription);
//    }
//    //endregion
//}
