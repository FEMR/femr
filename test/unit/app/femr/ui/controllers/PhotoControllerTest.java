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
        
        final String[] capturedPath = {null};
        
        // Create test subclass that overrides the hook
        PhotoController controller = new PhotoController(photoService) {
            @Override
            protected String getDefaultProfilePhotoPath() {
                capturedPath[0] = "target/test-data/default-photo.jpg";
                return "target/test-data/default-photo.jpg";
            }
        };
        
        // Call the public method which will invoke the overridden hook
        try {
            controller.GetPatientPhoto(null, true);
        } catch (Exception e) {
            // OK - we're just verifying the hook was called with our override
        }
        
        // Verify the hook was actually invoked (and our override was used)
        Assert.assertNotNull("Override hook was not called", capturedPath[0]);
        Assert.assertEquals("Override value should be used", "target/test-data/default-photo.jpg", capturedPath[0]);
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
            // OK if exception
        }
        
        Assert.assertFalse("Hook should not be called when showDefault=false", hookCalled[0]);
    }
}