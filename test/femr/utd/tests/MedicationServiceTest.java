package femr.utd.tests;

import com.google.inject.Inject;
import femr.business.services.core.IMedicationService;
import femr.business.services.system.MedicationService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationItem;
import femr.data.models.core.IMedication;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit Tests for MedicationService
 */
public class MedicationServiceTest extends BaseTest {

    private static IMedicationService service;
    private static MedicationItem newMed;
    private static MedicationItem newMed2;

    @Inject
    public void setService(IMedicationService service) {
        this.service = service;
    }

    @BeforeClass
    public static void initialize(){
        System.out.println("Testing MedicationService!!");
        cleanDB();
    }

    @AfterClass
    public static void cleanDB(){

        //clean the DB
        if (newMed!=null) {

            //remove the new medication
            ServiceResponse<MedicationItem> response2 = service.removeMedication(newMed.getId());
            checkForErrors(response2);

            //assert deletion
            MedicationItem medDeleted = response2.getResponseObject();
            assertNull(medDeleted);

            //remove the new medication
            ServiceResponse<MedicationItem> response3 = service.removeMedication(newMed2.getId());
            checkForErrors(response2);

            //assert deletion
            MedicationItem medDeleted2 = response3.getResponseObject();
            assertNull(medDeleted2);
        }
    }


    @Test
    public void testRetrieveAllMedications() throws Exception {

        //retrieve all the medications
        ServiceResponse<List<String>> response = service.retrieveAllMedications();

        //check for errors
        checkForErrors(response);

        //get the list of medications
        List<String> medications = response.getResponseObject();

        //assert the number of medications
        int numMeds = 3351 + (newMed!=null ? 1 : 0);
        assertEquals(numMeds, medications.size());

    }

    @Test
    public void testCreateMedication(){

        //create the medication
        ServiceResponse<MedicationItem> response = service.createMedication("Medication 1", "formtest", null);

        //check for errors
        checkForErrors(response);

        //get the new medication
        newMed = response.getResponseObject();

        //assert the new medication is not null
        assertNotNull(newMed);
    }

    @Test
    public void testDeleteMedication(){

        //create a new medication
        ServiceResponse<MedicationItem> response = service.createMedication("Medication 1", "formtest", null);
        checkForErrors(response);
        //call deleteMedication -- experimental, DO NOT TURN IN
        newMed2 = response.getResponseObject();
        int id = newMed2.getId();
        service.deleteMedication(id);
        //Retrieve new medication (Should return an object of type ServiceResponce<IMedication>
        ServiceResponse<IMedication> test = service.getMedicationfromID(id);
        //Assert that the new medication is actually marked as deleted
        assertTrue(test.getResponseObject().getIsDeleted());
        //After each call, Assert for errors using checkForErrors
        checkForErrors(response);
        checkForErrors(test);
        //Implement the DB cleaning strategy
        //Make sure all the test cases for MedicationServiceTest pass
    }

}