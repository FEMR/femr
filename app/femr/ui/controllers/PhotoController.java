package femr.ui.controllers;

import com.google.inject.Inject;
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


    public Result GetPatientPhoto(Integer patientId)
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

        String temp = null;
        return ok(temp);
    }
}
