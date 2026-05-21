package unit.app.femr.ui.controllers;

import femr.business.services.core.IPhotoService;
import femr.ui.controllers.PhotoController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;

import java.io.File;
import java.nio.file.Files;

import static org.mockito.Mockito.mock;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

public class PhotoControllerTest {

    private IPhotoService photoService;
    private File defaultPhotoFile;

    @Before
    public void setUp() throws Exception {
        photoService = mock(IPhotoService.class);
        defaultPhotoFile = File.createTempFile("femr-default-photo", ".jpg");
        Files.write(defaultPhotoFile.toPath(), new byte[]{1, 2, 3});
    }

    @After
    public void tearDown() {
        if (defaultPhotoFile != null && defaultPhotoFile.exists()) {
            defaultPhotoFile.delete();
        }
    }

    @Test
    public void getPatientPhoto_usesOverriddenDefaultPhotoPath() {
        PhotoController controller = new PhotoController(photoService) {
            @Override
            protected String getDefaultProfilePhotoPath() {
                return defaultPhotoFile.getAbsolutePath();
            }
        };

        Result result = controller.GetPatientPhoto(null, true);

        Assert.assertEquals(200, status(result));
        Assert.assertEquals("image/jpg", contentType(result).orElse(null));
    }

    @Test
    public void getPatientPhoto_returnsEmptyImageWhenDefaultNotRequested() {
        PhotoController controller = new PhotoController(photoService) {
            @Override
            protected String getDefaultProfilePhotoPath() {
                Assert.fail("The default profile photo path should not be needed for this code path");
                return null;
            }
        };

        Result result = controller.GetPatientPhoto(null, false);

        Assert.assertEquals(200, status(result));
        Assert.assertEquals("image/jpg", contentType(result).orElse(null));
    }
}