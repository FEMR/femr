package unit.app.femr.business.services;

import femr.business.services.core.IMedicationService;
import femr.business.services.system.MedicationService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PrescriptionItem;
import mock.femr.common.MockItemModelMapper;
import mock.femr.data.MockDataModelMapper;
import mock.femr.data.daos.MockMedicationRepository;
import mock.femr.data.daos.MockPrescriptionRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
}
