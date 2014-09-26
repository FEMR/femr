/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
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
