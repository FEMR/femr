package unit.app.femr.ui.controllers;

import femr.business.services.core.IPhotoService;
import femr.ui.controllers.PhotoController;
import org.junit.Assert;
import org.junit.Test;
import play.mvc.Result;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String, Object> getFakeAppConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("play.evolutions.autoApply", false);
        config.put("play.evolutions.autoApplyDowns", false);
        List<String> disabledModules = new ArrayList<>();
        disabledModules.add("play.api.db.DBModule");
        config.put("play.modules.disabled", disabledModules);
        return config;
    }

    @Test
    public void getPatientPhotoUsesOverriddenDefaultPhotoPath() {
        Helpers.running(Helpers.fakeApplication(getFakeAppConfig()), new Runnable() {
            @Override
            public void run() {
                Result result = newControllerWithDefaultPhoto("target/test-data/default-photo.jpg").GetPatientPhoto(null, true);
                Assert.assertNotNull(result);
            }
        });
    }

    @Test
    public void getPatientPhotoReturnsEmptyImageWhenDefaultNotRequested() {
        Helpers.running(Helpers.fakeApplication(getFakeAppConfig()), new Runnable() {
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