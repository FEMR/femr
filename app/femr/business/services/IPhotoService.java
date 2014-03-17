package femr.business.services;

import java.io.*;
import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
//gets the path for the image
public interface IPhotoService {


    String GetPhotoPath();
    void SavePhoto(File imgFile, String fileName);
    void CropImage(File imgFile, String coords);
    ServiceResponse<IPhoto> createPhoto(IPhoto photo);
}
