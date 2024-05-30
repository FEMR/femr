package unit.app.femr.business.services;

import controllers.AssetsFinder;
import femr.business.services.core.*;
import femr.ui.controllers.MedicalController;
import femr.util.translation.TranslationServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import play.data.FormFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    //MedicalController Tests
    @Test
    public void parseJsonResponseTest() {
        String data = "{\"translate_data\" : \"Hello, World\"}";
        Assert.assertEquals("Hello, World", medicalController.parseJsonResponse(data));
    }

    // TranslationServer Tests
    @Test
    public void getPortFromLogNoFileTest(){
        String logPath = "test/none.server.log";
        Assert.assertEquals(0, TranslationServer.getPortFromLog(logPath, false));
    }

    @Test
    public void getPortFromLogEmptyFileTest() throws IOException {
        String logPath = "test/empty.server.log";
        File log = new File(logPath);
        log.createNewFile();
        Assert.assertEquals(0, TranslationServer.getPortFromLog(logPath, false));
        log.delete();
    }

    @Test
    public void getPortFromLogTest() throws IOException {
        String logPath = "test/server.log";
        File log = new File(logPath);
        log.createNewFile();
        FileWriter writer = new FileWriter(logPath);
        writer.write("Serving at port: 8000");
        writer.close();
        Assert.assertEquals(8000, TranslationServer.getPortFromLog(logPath, false));
        log.delete();
    }

    @Test
    public void serverNotRunningTrue() throws IOException {
        String logPath = "test/server.log";
        File log = new File(logPath);
        log.createNewFile();
        FileWriter writer = new FileWriter(logPath);
        writer.write("Serving at port: 8010");
        writer.close();
        Assert.assertTrue(TranslationServer.serverNotRunning("test/server.log"));
        log.delete();
    }

    @Test
    public void serverNotRunningFalse() {
        TranslationServer.start("10");
        Assert.assertFalse(TranslationServer.serverNotRunning("translator/server.log"));
    }
    @Test
    public void makeServerRequestTest() {
        TranslationServer.start("10");
        String jsonString = "[{\"id\":\"#complaintInfo\", \"text\":\"Hello, World\"}, " +
                "{\"id\":\"#onset\", \"text\":\"Hello, World\"}]";
        String outputString =
                "{\"translate_data\": \"[{\\\"id\\\": \\\"#complaintInfo\\\", \\\"text\\\": \\\"Hola, Mundo\\\"}, " +
                "{\\\"id\\\": \\\"#onset\\\", \\\"text\\\": \\\"Hola, Mundo\\\"}]\"}";
        Assert.assertEquals(outputString, TranslationServer.makeServerRequest(jsonString, "en", "es"));
    }
}
