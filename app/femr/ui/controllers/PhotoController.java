package femr.ui.controllers;

import com.google.inject.Inject;
import com.typesafe.config.ConfigFactory;
import femr.common.dto.ServiceResponse;
import femr.business.services.IPhotoService;
import femr.data.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.util.stringhelpers.StringUtils;
import play.mvc.*;
import java.io.File;
import static play.mvc.Results.ok;


@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class PhotoController {

    private IPhotoService photoService;

    @Inject
    public PhotoController(IPhotoService photoService) {
        this.photoService = photoService;
    }

    /**
     * Returns an image file from the Upload folder for a patient
     *
     * @param patientId   Patient Primary Key Value
     * @param showDefault If True, return default image when patient photo is not found, else return nothing
     * @return
     */
    public Result GetPatientPhoto(Integer patientId, Boolean showDefault) {
        String pathToPhoto = "";

        if (patientId != null) {
            ServiceResponse<String> pathToPhotoResponse = photoService.getPhotoPathForPatient(patientId);
            if (pathToPhotoResponse.hasErrors()) {
                throw new RuntimeException();
            }
            if (pathToPhotoResponse.getResponseObject() != null) {
                pathToPhoto = pathToPhotoResponse.getResponseObject();
                File photo = new File(pathToPhoto);
                if (photo.canRead())
                    return ok(photo).as("image/jpg");
                else{
                    //need to be able to tell the difference between Triage and Search
                    //if Triage, do not show default
                    //if Search, show default
                    pathToPhoto = null;
                    showDefault = true;
                }

            }
        }

        if (StringUtils.isNullOrWhiteSpace(pathToPhoto) && showDefault) {
            pathToPhoto = ConfigFactory.load().getString("photos.defaultProfilePhoto");
        }

        return ok(new File(pathToPhoto)).as("image/jpg");
    }

    /**
     * Returns any image file from the Upload folder
     *
     * @param photoId id of the image
     * @return
     */
    public Result GetPhoto(int photoId) {
        if (photoId > 0) {
            ServiceResponse<String> pathToPhotoResponse = photoService.getPhotoPath(photoId);
            if (pathToPhotoResponse.hasErrors()) {
                throw new RuntimeException();
            }
            return ok(new File(pathToPhotoResponse.getResponseObject())).as("image/jpg");
        }
        //No luck, return nothing
        return ok("");
    }
}
