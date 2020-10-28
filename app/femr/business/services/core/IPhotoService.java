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
package femr.business.services.core;

import femr.common.models.PatientEncounterItem;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PhotoItem;
import femr.ui.models.medical.EditViewModelPost;
import play.mvc.Http.MultipartFormData.FilePart;

import java.io.File;
import java.util.List;

public interface IPhotoService {

    /**
     * Retrieve all photos for an encounter. Not patient photos.
     *
     * @param encounterId id of the encounter, not null
     * @return a service response that contains a list of PhotoItems that were found
     * and/or errors if they exist.
     */
    ServiceResponse<List<PhotoItem>> retrieveEncounterPhotos(int encounterId);

    /**
     * Creates photos for an encounter. Replaces/deletes them as necessary Not patient photos.
     *
     * @param encounterImages       list of images to save
     * @param patientEncounterItem, the patient encounter, TODO: change to parameters
     * @param mod,                  the viewmodel, TODO: change to parameters
     * @return a service response that contains true if creation successful, false if not
     * and/or errors if they exist.
     */
    ServiceResponse<Boolean> createEncounterPhotos(List<FilePart<File>> encounterImages, PatientEncounterItem patientEncounterItem, EditViewModelPost mod);

    /**
     * Saves a patient's photo and updates the patients photoId field to point
     * to the updated photo.
     *
     * @param imageString image to save as a base64 encoded string, TODO: make not null
     * @param patientId   id of the patient, not null
     * @param deleteFlag  true if photo is being deleted instead of saved, not null
     * @return a service response that contains true if creation successful, false if not
     * and/or errors if they exist.
     */
    ServiceResponse<Boolean> createPatientPhoto(String imageString, int patientId, Boolean deleteFlag);


    /**
     *  Returns patient photo in binary form.  Will determine where to fetch the photo (file system or blob)
     *    based on the photos.useDbStorage property.
     * @param patientId
     * @return
     */
    ServiceResponse<byte[]> retrievePatientPhotoData(int patientId);


    /**
     *  Returns any photo by ID in binary form.  Will determine where to fetch the photo (file system or blob)
     *    based on the photos.useDbStorage property.
     * @param photoId
     * @return
     */
    ServiceResponse<byte[]> retrievePhotoData(int photoId);

}