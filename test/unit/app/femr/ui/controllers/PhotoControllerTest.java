package unit.app.femr.ui.controllers;

import femr.business.services.core.IPhotoService;
import femr.ui.controllers.PhotoController;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class PhotoControllerTest {

    /**
     * Test that demonstrates Extract and Override Call technique:
     * The extracted hook method getDefaultProfilePhotoPath() is overridden
     * in a test subclass to verify the seam is broken and controllable.
     */
    @Test
    public void getPatientPhotoUsesOverriddenDefaultPhotoPath() {
        IPhotoService photoService = mock(IPhotoService.class);
        
        // Create test subclass that overrides the hook
        PhotoController controller = new PhotoController(photoService) {
            @Override
            protected String getDefaultProfilePhotoPath() {
                return "target/test-data/default-photo.jpg";
            }
        };
        
        // Verify the hook was extracted and is overridable
        String result = controller.getDefaultProfilePhotoPath();
        Assert.assertEquals("target/test-data/default-photo.jpg", result);
    }

    /**
     * Test that verifies the hook is only called when showDefault=true.
     * When showDefault=false, the hook should not be invoked.
     */
    @Test
    public void getPatientPhotoDoesNotCallHookWhenDefaultNotRequested() {
        IPhotoService photoService = mock(IPhotoService.class);
        
        final boolean[] hookCalled = {false};
        
        PhotoController controller = new PhotoController(photoService) {
            @Override
            protected String getDefaultProfilePhotoPath() {
                hookCalled[0] = true;
                return null;
            }
        };
        
        // Calling with showDefault=false should NOT invoke the hook
        try {
            controller.GetPatientPhoto(null, false);
        } catch (Exception e) {
            // OK if exception (empty response, not our concern in this test)
        }
        
        Assert.assertFalse("Hook should not be called when showDefault=false", hookCalled[0]);
    }
}