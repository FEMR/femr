package femr.business.services.system;

import femr.business.services.core.IInventoryService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.InventoryExportItem;
import femr.common.models.ShoppingListExportItem;
import femr.data.models.core.IBurnRate;
import mock.femr.common.MockItemModelMapper;
import mock.femr.data.MockDataModelMapper;
import mock.femr.data.daos.MockBurnRateRepository;
import mock.femr.data.daos.MockMedicationRepository;
import mock.femr.data.daos.MockPrescriptionRepository;
import mock.femr.data.daos.MockUserRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class InventoryServiceTest {

    private IInventoryService inventoryService;
    private MockMedicationRepository mockMedicationRepository;
    private MockUserRepository mockUserRepository;
    private MockDataModelMapper mockDataModelMapper;
    private MockBurnRateRepository mockBurnRateRepository;
    private MockPrescriptionRepository mockPrescriptionRepository;
    private MockItemModelMapper mockItemModelMapper;
    private CurrentUser currentUser;

    @Before
    public void setUp() {
        mockMedicationRepository = new MockMedicationRepository();
        mockUserRepository = new MockUserRepository();
        mockDataModelMapper = new MockDataModelMapper();
        mockBurnRateRepository = new MockBurnRateRepository();
        mockPrescriptionRepository = new MockPrescriptionRepository();
        mockItemModelMapper = new MockItemModelMapper();
        currentUser = new CurrentUser(1,"amin","hm",null,null,1,1);
        inventoryService = new InventoryService(mockMedicationRepository,mockUserRepository,mockDataModelMapper,mockItemModelMapper,mockBurnRateRepository,mockPrescriptionRepository);
    }


    @Test
    public void testExportCSV() {
        ServiceResponse<String> response;
        response = inventoryService.exportCSV(1);
        String csvFile = response.getResponseObject();
        List<String> items;
        items = Arrays.asList(csvFile.split("\n"));
        assertFalse(response.hasErrors());
        assertEquals(items.get(0),"ID,Medication,Current Quantity,Initial Quantity,Time Added,Created By");
        assertEquals(items.get(1),"0,abc,100,100,,");
        assertTrue(mockMedicationRepository.retrieveMedicationInventoriesByTripIdWasCalled);
        assertTrue(mockItemModelMapper.createMedicationItemWasCalled);

    }

    @Test
    public void testImportCSV() throws IOException {
        try {
            File f = new File("my_file.csv");
            FileWriter fr = new FileWriter(f);
            BufferedWriter br  = new BufferedWriter(fr);
            br.write("Medication,Quantity\namiiiin,200");
            br.close();
            File f1 = new File("my_file.csv");


            ServiceResponse<List<InventoryExportItem>> response;
            response = inventoryService.importCSV(1,f1,currentUser);

            assertTrue(mockMedicationRepository.retrieveMedicationInventoriesByTripIdWasCalled);
            assertTrue(mockItemModelMapper.createMedicationItemWasCalled);
            assertTrue(mockDataModelMapper.createMedicationInventoriesWasCalled);
            assertTrue(mockMedicationRepository.saveMedicationInventoryWasCalled);
            assertFalse(response.hasErrors());
            assertEquals(response.getResponseObject().get(0).getName(),"amiiiin");
            assertEquals(response.getResponseObject().get(0).getQuantityCurrent(),Integer.valueOf("200"));

        } catch (IOException e) {
        }

    }

    @Test
    public void testShoppingListAlgo() {
        IBurnRate burnRate = inventoryService.callPredictor(1,1);
        assertTrue(mockBurnRateRepository.retrieveBurnRateByMedIdAndTripIdWasCalled);
        assertEquals(Float.valueOf(burnRate.getRate()),Float.valueOf(22.2f));

    }

    @Test
    public void testCreatingShoppingList() {
        List<ShoppingListExportItem> shoppingListExportItems = inventoryService.createShoppingList(1,1);
        assertTrue(mockBurnRateRepository.retrieveAllBurnRatesByTripIdWasCalled);
        assertTrue(mockMedicationRepository.retrieveMedicationInventoryByMedicationIdAndTripIdWasCalled);
        assertTrue(mockItemModelMapper.createMedicationItemWasCalled);
        assertEquals(shoppingListExportItems.get(0).getQuantity(),Integer.valueOf(146));

    }


    @Test
    public void testExportingShoppingList() {
        ServiceResponse<String> response;
        response = inventoryService.exportShoppingListCSV(1,1);
        String csvFile = response.getResponseObject();
        List<String> items;
        items = Arrays.asList(csvFile.split("\n"));
        assertFalse(response.hasErrors());
        assertEquals(items.get(0),"ID,Medication,Quantity");
        assertEquals(items.get(1),"12222,aminhm,146");

    }


}