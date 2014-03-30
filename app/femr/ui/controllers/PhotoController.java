package femr.ui.controllers;

import com.google.inject.Inject;
import com.typesafe.config.ConfigFactory;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IPhotoService;
import femr.business.services.ISearchService;
import femr.common.models.*;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import play.mvc.*;

import java.io.File;

import static play.mvc.Results.ok;


@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class PhotoController {

    private ISearchService searchService;
    private IPhotoService photoService;

    @Inject
    public PhotoController(ISearchService searchService, IPhotoService photoService)
    {
        this.searchService = searchService;
        this.photoService = photoService;
    }


    /**
     * Returns patient image file
     * @param patientId Patient Primary Key Value
     * @param showDefault If True, return default image when patient photo is not found, else return nothing
     * @return
     */
    public Result GetPatientPhoto(Integer patientId, Boolean showDefault)
    {
        try
        {
            if(patientId != null)
            {
                ServiceResponse<IPatient> patRsp = searchService.findPatientById(patientId);
                //fetch patient:
                if(!patRsp.hasErrors())
                {
                    IPatient pat = patRsp.getResponseObject();
                    if(pat.getPhotoId() != null)
                    {
                        //fetch photo record:
                        ServiceResponse<IPhoto> photoRsp = photoService.getPhotoById(pat.getPhotoId());
                        if(!photoRsp.hasErrors())
                        {
                            IPhoto photo = photoRsp.getResponseObject();
                            //photoService.g
                            return ok(new File(photoService.GetRootPhotoPath() + photo.getFilePath())).as("image/jpg");
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
        }

        //If there is no photo, then attempt to return the default image from config file
        if(showDefault)
        {
            try
            {
                String path = ConfigFactory.load().getString("photos.defaultProfilePhoto");
                return ok(new File(path)).as("image/jpg");
            }
            catch(Exception ex)
            {
                String temp = ex.getMessage();
            }
        }

        //No luck, return nothing
        return ok("");
    }
}
