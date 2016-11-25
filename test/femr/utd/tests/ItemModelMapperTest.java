package femr.utd.tests;

import com.google.inject.Inject;
import femr.business.services.core.ITabService;
import femr.common.IItemModelMapper;
import femr.common.InputTabFieldItem;
import femr.common.ItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationItem;
import femr.common.models.TabFieldItem;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Unit Tests for TabService
 */
public class ItemModelMapperTest extends BaseTest {
    public IItemModelMapper newItemMapper;
    private static ITabService service;
    private static TabFieldItem newtab;

    @Inject
    public void setService(ITabService service) {
        this.service = service;
    }

    @BeforeClass
    public static void initialize() {
        System.out.println("Testing ItemModelMapper!!");
        //cleanDB();
    }


    @Test
    public void testRetrieveAllTab() throws Exception
    {
        TabFieldItem tabFieldItems = new TabFieldItem();
        tabFieldItems.setName("test");
        tabFieldItems.setIndex(1);
        tabFieldItems.setType("1");
        //create the medication
        ServiceResponse<TabFieldItem> response = service.createTabField(tabFieldItems, 92, "Treatment");

        //check for errors
        checkForErrors(response);

        //get the new medication
        newtab= response.getResponseObject();
        //assert the new medication is not null

        assertNotNull(newtab);

    }

}