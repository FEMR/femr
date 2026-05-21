package unit.app.femr.ui.controllers;

import femr.business.services.core.IPhotoService;
import femr.ui.controllers.PhotoController;
import org.junit.Assert;
import org.junit.Test;
import play.mvc.Result;
import play.test.Helpers;

import static org.mockito.Mockito.mock;

public class PhotoControllerTest {

    private PhotoController newControllerWithDefaultPhoto(String defaultPhotoPath) {
        IPhotoService photoService = mock(IPhotoService.class);
        return new PhotoController(photoService) {
            @Override
            protected String getDefaultProfilePhotoPath() {
                return defaultPhotoPath;
            }
        };
    }

    @Test
    public void getPatientPhotoUsesOverriddenDefaultPhotoPath() {
        Helpers.running(Helpers.fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Result result = newControllerWithDefaultPhoto("target/test-data/default-photo.jpg").GetPatientPhoto(null, true);
                Assert.assertNotNull(result);
            }
        });
    }

    @Test
    public void getPatientPhotoReturnsEmptyImageWhenDefaultNotRequested() {
        Helpers.running(Helpers.fakeApplication(), new Runnable() {
            @Override
            public void run() {
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
        });
    }
}