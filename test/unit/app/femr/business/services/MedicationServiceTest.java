package unit.app.femr.business.services;

import femr.business.services.core.IMedicationService;
import femr.business.services.system.MedicationService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationItem;
import femr.common.models.PrescriptionItem;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IMedicationRepository;
import femr.data.daos.core.IPrescriptionRepository;
import femr.data.daos.system.PrescriptionRepository;
import femr.data.models.core.IMedication;
import femr.data.models.core.IPatientPrescription;
import femr.data.models.core.IUser;
import femr.data.models.mysql.concepts.ConceptPrescriptionAdministration;
import mock.femr.common.MockItemModelMapper;
import mock.femr.data.MockDataModelMapper;
import mock.femr.data.daos.MockMedicationRepository;
import mock.femr.data.daos.MockPrescriptionRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class MedicationServiceTest {

    private IMedicationService medicationService;
    private MockDataModelMapper mockDataModelMapper;
    private MockItemModelMapper mockItemModelMapper;
    private MockMedicationRepository mockMedicationRepository;
    private MockPrescriptionRepository mockPrescriptionRepository;

    @Before
    public void setUp() {

        mockDataModelMapper = new MockDataModelMapper();
        mockItemModelMapper = new MockItemModelMapper();
        mockMedicationRepository = new MockMedicationRepository();
        mockPrescriptionRepository = new MockPrescriptionRepository();

        medicationService = new MedicationService(mockMedicationRepository, mockPrescriptionRepository, mockDataModelMapper, mockItemModelMapper);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void createPrescription_parametersProvided_parametersSaved() {

        //arrange
        ServiceResponse<PrescriptionItem> response;

        //act
        response = medicationService.createPrescription(1, 2, 3, 4, 100, "test dispense");
        PrescriptionItem prescriptionItemResponse = response.getResponseObject();
        //assert
        assertFalse(response.hasErrors());
        assertTrue(mockPrescriptionRepository.createPrescriptionWasCalled);
        assertTrue(mockItemModelMapper.createPrescriptionItemWasCalled);

        assertEquals(prescriptionItemResponse.getMedicationID(), Integer.valueOf(1));
        assertEquals(prescriptionItemResponse.getAmount(), Integer.valueOf(100));
        assertEquals(prescriptionItemResponse.getAdministrationID(), Integer.valueOf(2));
    }

    @Test
    public void createPrescriptionWithNewMedication_parametersProvided_parametersSaved() {
        //Test values
        final int testAmount = 100;
        final int testMedicationId = 1;
        final int testMedicationAdministrationId = 2;
        final int testUserId = 4;
        final int testEncounterId = 3;
        final String testMedicationName = "Advil";
        final String testPhysicianFirst = "FName";
        final String testPhysicianLast = "LName";

        //Mocks
        ServiceResponse<PrescriptionItem> response;
        IDataModelMapper mockDataModelMapper = Mockito.mock(IDataModelMapper.class);
        IItemModelMapper mockItemModelMapper = Mockito.mock(IItemModelMapper.class);
        IMedicationRepository mockMedicationRepository = Mockito.mock(IMedicationRepository.class);
        IPrescriptionRepository mockPrescriptionRepository = Mockito.mock(IPrescriptionRepository.class);
        MedicationItem mockMedication = Mockito.mock(MedicationItem.class);
        IMedication mockIMedication = Mockito.mock(IMedication.class);
        IPatientPrescription mockPatientPrescription = Mockito.mock(IPatientPrescription.class);
        ConceptPrescriptionAdministration concept = Mockito.mock(ConceptPrescriptionAdministration.class);
        PrescriptionItem pItem = Mockito.mock(PrescriptionItem.class);
        IUser mockPhysician = Mockito.mock(IUser.class);

        Mockito.when(mockMedicationRepository
                .createNewMedication(testMedicationName))
                .thenReturn(mockIMedication);

        Mockito.when(mockIMedication
                .getId())
                .thenReturn(testMedicationId);

        Mockito.when(mockPrescriptionRepository.createPrescription(
                testAmount,
                testMedicationId,
                testMedicationAdministrationId,
                testUserId,
                testEncounterId))
                .thenReturn(mockPatientPrescription);

        Mockito.when(mockPatientPrescription.getId()).thenReturn(testMedicationId);
        Mockito.when(mockPatientPrescription.getMedication()).thenReturn(mockIMedication);
        Mockito.when(mockIMedication.getName()).thenReturn(testMedicationName);

        Mockito.when(mockPatientPrescription.getPhysician()).thenReturn(mockPhysician);
        Mockito.when(mockPhysician.getFirstName()).thenReturn(testPhysicianFirst);
        Mockito.when(mockPhysician.getLastName()).thenReturn(testPhysicianLast);
        Mockito.when(mockPatientPrescription.getConceptPrescriptionAdministration()).thenReturn(concept);
        Mockito.when(mockPatientPrescription.getAmount()).thenReturn(testAmount);
        Mockito.when(mockPatientPrescription.isCounseled()).thenReturn(false);

        Mockito.when(mockItemModelMapper.createMedicationItem(
                mockPatientPrescription.getMedication(),
                null,
                null,
                null,
                null,
                null))
                .thenReturn(mockMedication);

        Mockito.when(mockItemModelMapper.createPrescriptionItem(
            testMedicationId,
            testMedicationName,
            testPhysicianFirst,
            testPhysicianLast,
            concept,
            testAmount,
            false,
            mockMedication
        )).thenReturn(pItem);

        IMedicationService medicationService = new MedicationService(
                mockMedicationRepository,
                mockPrescriptionRepository,
                mockDataModelMapper,
                mockItemModelMapper);

        //act
        response = medicationService.createPrescriptionWithNewMedication(
                testMedicationName,
                testMedicationAdministrationId,
                testEncounterId,
                testUserId,
                testAmount,
                "test dispense");

        PrescriptionItem prescriptionItemResponse = response.getResponseObject();

        //assert
        assertFalse(response.hasErrors());

        Mockito.verify(mockMedicationRepository, Mockito.times(1))
                .createNewMedication(testMedicationName);

        Mockito.verify(mockPrescriptionRepository, Mockito.times(1)).createPrescription(
                testAmount,
                testMedicationId,
                testMedicationAdministrationId,
                testUserId,
                testEncounterId);

        Mockito.verify(mockItemModelMapper, Mockito.times(1)).createPrescriptionItem(
                testMedicationId,
                testMedicationName,
                testPhysicianFirst,
                testPhysicianLast,
                concept,
                testAmount,
                false,
                mockMedication);
    }
}
