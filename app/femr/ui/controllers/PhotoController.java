package femr.ui.controllers;

import com.google.inject.Inject;
import com.typesafe.config.ConfigFactory;
import femr.business.helpers.LogicDoer;
import femr.common.dtos.ServiceResponse;
import femr.business.services.core.IPhotoService;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.util.stringhelpers.StringUtils;
import play.mvc.*;
import static play.mvc.Results.ok;

import java.io.File;

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
        if (patientId != null) {
            ServiceResponse<byte[]> photoDataResponse = photoService.retrievePatientPhotoData(patientId);
            if (photoDataResponse.hasErrors()) {
                throw new RuntimeException();
            }
            if (photoDataResponse.getResponseObject() != null) {
                return ok(photoDataResponse.getResponseObject()).as("image/jpg");
            }
        }

        if (showDefault) {
            String pathToDefaultPhoto = ConfigFactory.load().getString("photos.defaultProfilePhoto");
            return ok(new File(pathToDefaultPhoto)).as("image/jpg");
        }

        return ok().as("image/jpg");
    }

    /**
     * Returns any image file from the Upload folder
     *
     * @param photoId id of the image
     * @return
     */
    public Result GetPhoto(int photoId) {
        if (photoId > 0) {
            ServiceResponse<byte[]> pathToPhotoResponse = photoService.retrievePhotoData(photoId);
            if (pathToPhotoResponse.hasErrors()) {
                throw new RuntimeException();
            }
            if(pathToPhotoResponse.getResponseObject() != null)
                return ok(pathToPhotoResponse.getResponseObject()).as("image/jpg");

        }
        //No luck, return nothing
        return ok().as("image/jpg");  //return empty image
    }

}
