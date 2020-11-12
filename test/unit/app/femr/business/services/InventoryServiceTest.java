package unit.app.femr.business.services;

import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationItem;
import mock.femr.common.MockItemModelMapper;
import mock.femr.data.MockDataModelMapper;
import mock.femr.data.daos.MockMedicationRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import femr.business.services.core.IInventoryService;
import femr.business.services.system.InventoryService;
import mock.femr.data.daos.MockUserRepository;

public class InventoryServiceTest {

    private IInventoryService inventoryService;
    private MockDataModelMapper mockDataModelMapper;
    private MockItemModelMapper mockItemModelMapper;
    private MockMedicationRepository mockMedicationRepository;
    private MockUserRepository mockUserRepository;

    @Before
    public void setUp() {

        mockDataModelMapper = new MockDataModelMapper();
        mockItemModelMapper = new MockItemModelMapper();
        mockMedicationRepository = new MockMedicationRepository();
        mockUserRepository = new MockUserRepository();

        inventoryService = new InventoryService(mockMedicationRepository, mockUserRepository, mockDataModelMapper, mockItemModelMapper);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void createMedicationInventory_parametersProvided_newInventoryMedication() {

        //arrange
        ServiceResponse<MedicationItem> response;

        //act
        response = inventoryService.createMedicationInventory(1, 1);
        MedicationItem medicalItemResponse = response.getResponseObject();
        //assert
        assertFalse(response.hasErrors());
        assertTrue(mockMedicationRepository.retrieveMedicationInventoriesByTripIdWasCalled);
        assertTrue(mockDataModelMapper.createMedicationInventoryWasCalled);

        //from MockMedication data values
        assertEquals(medicalItemResponse.getId(), -1);
        assertEquals(medicalItemResponse.getName(), "Tylenol");
    }
}
