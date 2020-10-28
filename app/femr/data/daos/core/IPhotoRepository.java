package femr.data.daos.core;

import femr.data.models.core.IPatientEncounterPhoto;
import femr.data.models.core.IPhoto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public interface IPhotoRepository {

    /**
     * Creates a new photo entry in the database
     *
     * @param description description of the photo, not null - can use an empty string
     *                    instead of null
     * @param filePath location to the photo on the filesystem, not null
     * @return the new photo or NULL if an error occurs
     */
    IPhoto createPhoto(String description, String filePath, byte[] photoData);

    /**
     * Takes a FilePart and moves it out of a temporary folder on the
     * filesystem into fEMR's directory
     *
     * @param image the image itself, not null
     * @param filePath the location where the photo will be saved, not null
     * @return true if success, false otherwise
     */
    boolean createPhotoOnFilesystem(File image, String filePath);

    /**
     * Creates a new jpg image on the filesystem
     *
     * @param bufferedImage the buffered image, not null
     * @param filePath the location where the photo will be saved, not null
     * @return true if success, false otherwise
     */
    boolean createPhotoOnFilesystem(BufferedImage bufferedImage, String filePath);

    /**
     * Creates a new patient encounter photo entry in the database
     *
     * @param photoId id of the photo, not null
     * @param encounterId id of the encounter, not null
     * @return the new PatientEncounterPhoto
     */
    IPatientEncounterPhoto createEncounterPhoto(int photoId, int encounterId);

    /**
     * Retrieve an existing photo by id
     *
     * @param id primary key of the photo to retrieve, not null
     * @return the photo
     */
    IPhoto retrievePhotoById(int id);

    /**
     * Retrieve a set of encounter photos for a specific encounter
     *
     * @param encounterId id of the encounter, not null
     * @return a list of photos for this encounter
     */
    List<? extends IPatientEncounterPhoto> retrieveEncounterPhotosByEncounterId(int encounterId);

    /**
     * Retrieve a list of encounter photos for a specific photo
     *
     * @param photoId id of the encounter, not null
     * @return a list of photos for this encounter
     */
    List<? extends IPatientEncounterPhoto> retrieveEncounterPhotosByPhotoId(int photoId);

    /**
     * Update the description of a photo
     *
     * @param id primary key of the photo to update, not null
     * @param description the new description for the photo, not null - can use an empty string
     *                    instead of null
     * @return the current state of the Photo
     */
    IPhoto updatePhotoDescription(int id, String description);

    /**
     * Update the file path of a photo
     *
     * @param id primary key of the photo to update, not null
     * @param filePath the new file path for the photo, not null - can use an empty string instead
     *                    of null
     * @return the current state of the Photo
     */
    IPhoto updatePhotoFilePath(int id, String filePath);


    /**
     * Sets the binary image data field
     *
     * @param id primary key of the photo to update
     * @param photoData  binary image data
     * @return the current state of the Photo
     */
    IPhoto updatePhotoData(int id, byte[] photoData);

    /**
     * Performs a *hard delete* on the photo in the Photos table
     *
     * @param id id of the photo to delete, not null
     * @return true if the photo was deleted, throws an error if something went wrong
     */
    boolean deletePhotoById(int id);

    /**
     * Performs a *hard delete* on the photo on the filesystem
     *
     * @param filePath id of the photo to delete
     * @return true if the photo was deleted, throws an error if something went wrong
     */
    boolean deletePhotoFromFilesystemById(String filePath);

    /**
     * Performs a *hard delete* on the photo in the EncounterPhotos table
     *
     * @param id photo id for the encounter photos to be deleted
     * @return true if the photo was deleted, throws an error if something went wrong
     */
    boolean deleteEncounterPhotosByPhotoId(int id);
}
