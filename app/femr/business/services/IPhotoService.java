package femr.business.services;

import java.util.List;
import femr.common.models.PatientEncounterItem;
import femr.common.dto.ServiceResponse;
import femr.common.models.PhotoItem;
import femr.ui.models.medical.EditViewModelPost;
import play.mvc.Http.MultipartFormData.FilePart;

public interface IPhotoService {

    /**
     * Retrieve all photos for the medical page
     *
     * @param encounterId
     * @return
     */
    ServiceResponse<List<PhotoItem>> GetEncounterPhotos(int encounterId);

    /**
     * Saves photos from the medical page
     *
     * @param encounterImages
     * @param patientEncounterItem
     * @param mod
     * @return
     */
    ServiceResponse<Boolean> HandleEncounterPhotos(List<FilePart> encounterImages, PatientEncounterItem patientEncounterItem, EditViewModelPost mod);

    /**
     * Saves a patient's photo and updates the patients photoId field to point
     * to the updated photo
     *
     * @param img image to save
     * @param patientId id of the patient
     * @param deleteFlag true if photo is being deleted instead of saved
     * @return i have no idea
     */
    ServiceResponse<Boolean> SavePatientPhotoAndUpdatePatient(String imageString, int patientId, Boolean deleteFlag);

    /**
     * Gets the path to a patients photo
     *
     * @param patientId id of the patient
     * @return the patient's photo
     */
    ServiceResponse<String> getPhotoPathForPatient(int patientId);

    /**
     * Gets the path to a photo
     *
     * @param photoId id of the photo
     * @return the photo path
     */
    ServiceResponse<String> getPhotoPath(int photoId);
}
