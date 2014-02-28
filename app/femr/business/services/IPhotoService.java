package femr.business.services;

import java.io.*;

import femr.common.models.IPatient;

public interface IPhotoService {


    String GetPhotoPath();
    //void SavePhoto(File imgFile, String fileName);
    //void CropImage(File imgFile, String coords);
    //ServiceResponse<IPhoto> createPhoto(IPhoto photo);
    void HandlePatientPhoto(File img, IPatient patient, String coords, Boolean deleteFlag);
}
