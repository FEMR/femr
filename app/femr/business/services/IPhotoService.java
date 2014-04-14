package femr.business.services;

import java.io.*;
import java.util.List;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPhoto;
import femr.ui.models.medical.CreateViewModelPost;
import play.mvc.Http.MultipartFormData.FilePart;

public interface IPhotoService {
    String GetRootPhotoPath();
    String GetRootEncounterPhotoPath();
    ServiceResponse<IPhoto> getPhotoById(int id);
    ServiceResponse<List<IPhoto>> GetEncounterPhotos(int encounterId);
    ServiceResponse<Boolean> HandlePatientPhoto(File img, IPatient patient, String coords, Boolean deleteFlag);
    ServiceResponse<Boolean> HandleEncounterPhotos(List<FilePart> encounterImages,
                                                    IPatientEncounter patientEncounter, CreateViewModelPost mod);
}
