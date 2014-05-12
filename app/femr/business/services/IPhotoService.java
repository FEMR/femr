package femr.business.services;

import java.io.*;
import java.util.List;

import femr.business.dtos.PatientEncounterItem;
import femr.business.dtos.PhotoItem;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPhoto;
import femr.business.dtos.PatientItem;
import femr.ui.models.medical.EditViewModelPost;
import play.mvc.Http.MultipartFormData.FilePart;

public interface IPhotoService {



    String GetRootPhotoPath();
    String GetRootEncounterPhotoPath();
    ServiceResponse<IPhoto> getPhotoById(int id);
    ServiceResponse<List<IPhoto>> GetEncounterPhotos(int encounterId);
    ServiceResponse<Boolean> HandlePatientPhoto(File img, PatientItem patient, String coords, Boolean deleteFlag);



    ServiceResponse<Boolean> HandleEncounterPhotos(List<FilePart> encounterImages, PatientEncounterItem patientEncounterItem, EditViewModelPost mod);



}
