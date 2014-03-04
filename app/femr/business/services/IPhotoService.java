package femr.business.services;

import java.io.*;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPhoto;

public interface IPhotoService {
    void HandlePatientPhoto(File img, IPatient patient, String coords, Boolean deleteFlag);
    ServiceResponse<IPhoto> getPhotoById(int id);
    String GetRootPhotoPath();
}
