package unit.app.femr.ui.controllers;

import femr.business.services.core.IPhotoService;
import femr.ui.controllers.PhotoController;
import org.junit.Assert;
import org.junit.Test;
import play.mvc.Result;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.mock;

public class PhotoControllerTest {

    private PhotoController newControllerWithDefaultPhoto(Path defaultPhotoPath) {
        IPhotoService photoService = mock(IPhotoService.class);
        return new PhotoController(photoService) {
            @Override
            protected String getDefaultProfilePhotoPath() {
                return defaultPhotoPath.toString();
            }
        };
    }

    @Test
    public void getPatientPhotoUsesOverriddenDefaultPhotoPath() throws Exception {
        Path defaultPhotoDirectory = Paths.get("target", "test-data");
        Files.createDirectories(defaultPhotoDirectory);
        Path defaultPhotoPath = Files.createTempFile(defaultPhotoDirectory, "femr-default-photo", ".jpg");
        Files.write(defaultPhotoPath, new byte[]{1, 2, 3});

        try {
            Result result = newControllerWithDefaultPhoto(defaultPhotoPath).GetPatientPhoto(null, true);

            Assert.assertNotNull(result);
        } finally {
            Files.deleteIfExists(defaultPhotoPath);
        }
    }

    @Test
    public void getPatientPhotoReturnsEmptyImageWhenDefaultNotRequested() {
        PhotoController controller = new PhotoController(mock(IPhotoService.class)) {
            @Override
            protected String getDefaultProfilePhotoPath() {
                Assert.fail("The default profile photo path should not be needed for this code path");
                return null;
            }
        };

        Result result = controller.GetPatientPhoto(null, false);

        Assert.assertNotNull(result);
    }
}