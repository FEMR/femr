package unit.app.femr.business.services;

import controllers.AssetsFinder;
import femr.business.services.core.*;
import femr.business.services.system.UserService;
import femr.common.IItemModelMapper;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IUserRepository;
import femr.ui.controllers.helpers.FieldHelper;
import femr.util.encryptions.IPasswordEncryptor;
import femr.ui.controllers.MedicalController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import play.data.FormFactory;

import static org.mockito.Mockito.mock;

public class TranslationServiceTest {
    
    AssetsFinder assetsFinder;
    FormFactory formFactory;
    ITabService tabService;
    IEncounterService encounterService;
    IMedicationService medicationService;
    IPhotoService photoService;
    ISessionService sessionService;
    ISearchService searchService;
    IVitalService vitalService;
    MedicalController medicalController;
    
    @Before
    public void setUp(){
        assetsFinder = mock(AssetsFinder.class);
        formFactory = mock(FormFactory.class);
        tabService = mock(ITabService.class);
        encounterService = mock(IEncounterService.class);
        sessionService = mock(ISessionService.class);
        searchService = mock(ISearchService.class);
        medicationService = mock(IMedicationService.class);
        photoService = mock(IPhotoService.class);
        vitalService = mock(IVitalService.class);
        medicalController = new MedicalController(
                assetsFinder,
                formFactory,
                tabService,
                encounterService,
                medicationService,
                photoService,
                sessionService,
                searchService,
                vitalService);
    }

    @Test
    public void parseJsonResponse() {
        String data = "{\"translate_data\" : \"Hello, World\"}";
        Assert.assertEquals("Hello, World", medicalController.parseJsonResponse(data));
    }
}
