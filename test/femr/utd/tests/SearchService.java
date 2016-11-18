package femr.utd.tests;

        import com.google.inject.Inject;
        import femr.business.services.core.ISearchService;
        import femr.common.InputTabFieldItem;
        import femr.common.dtos.ServiceResponse;
        import femr.common.models.PatientItem;
        import org.junit.AfterClass;
        import org.junit.BeforeClass;
        import org.junit.Test;

        import java.util.List;

        import static org.junit.Assert.*;

/**
 * Unit Tests for MedicationService
 */
public class SearchService extends BaseTest {

    private static ISearchService service;
    private static List<PatientItem> newPatient;


    @Inject
    public void setService(ISearchService service) {
        this.service = service;
    }

    @BeforeClass
    public static void initialize(){
        System.out.println("Testing MedicationService!!");

    }



    @Test
    public void testgetPatientsForSearch(){

        //create the medication
        ServiceResponse<List<PatientItem>> response = service.retrievePatientsForSearch(1);

        //check for errors
        checkForErrors(response);

        //get the new medication
        newPatient = response.getResponseObject();

        //assert the new medication is not null
        assertNotNull(newPatient);

    }


}