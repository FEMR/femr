package femr.data.daos.system;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.helpers.QueryProvider;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IPhotoRepository;
import femr.data.models.core.IPatientEncounterPhoto;
import femr.data.models.core.IPhoto;
import femr.data.models.mysql.PatientEncounterPhoto;
import femr.data.models.mysql.Photo;
import femr.util.stringhelpers.StringUtils;
import play.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class PhotoRepository implements IPhotoRepository {

    private final IDataModelMapper dataModelMapper;
    private final Provider<IPatientEncounterPhoto> patientEncounterPhotoProvider;

    @Inject
    public PhotoRepository(IDataModelMapper dataModelMapper,
                           Provider<IPatientEncounterPhoto> patientEncounterPhotoProvider){

        this.dataModelMapper = dataModelMapper;
        this.patientEncounterPhotoProvider = patientEncounterPhotoProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPhoto createPhoto(String description, String filePath, byte[] photoData) {

        IPhoto photo;

        //this can't be done because of weird logic in the service layer right now
       /* if (StringUtils.isNullOrWhiteSpace(filePath)){

            Logger.debug("a NULL or blank filePath was passed into createPhoto for a new photo");
            return photo;
        }*/

        try {
            photo = dataModelMapper.createPhoto(description, filePath, photoData);
            Ebean.save(photo);
        } catch (Exception ex) {

            Logger.error("PhotoRepository-createPhoto", ex);
            throw ex;
        }

        return photo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createPhotoOnFilesystem(File image, String filePath){

        if (image == null || StringUtils.isNullOrWhiteSpace(filePath)){

            return false;
        }

        try {

            //find out where the file is being stored on the filesystem (usually in /tmp)
            Path src = FileSystems.getDefault().getPath(image.getAbsolutePath());
            //identify where fEMR wants to store the file
            Path dest = FileSystems.getDefault().getPath(filePath);
            //move the file from a temporary to a permanent location
            java.nio.file.Files.move(src, dest, StandardCopyOption.ATOMIC_MOVE);
        } catch (Exception ex) {

            Logger.error("PhotoRepository-createPhotoOnFilesystem", ex);
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createPhotoOnFilesystem(BufferedImage bufferedImage, String filePath){

        if (bufferedImage == null || StringUtils.isNullOrWhiteSpace(filePath)){

            return false;
        }

        try {

            File photo = new File(filePath);
            ImageIO.write(bufferedImage, "jpg", photo);
        } catch (Exception ex) {

            Logger.error("PhotoRepository-createPhotoOnFilesystem", ex);
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientEncounterPhoto createEncounterPhoto(int photoId, int encounterId){

        IPatientEncounterPhoto patientEncounterPhoto = patientEncounterPhotoProvider.get();
        patientEncounterPhoto.setPatientEncounterId(encounterId);
        patientEncounterPhoto.setPhotoId(photoId);
        try{

            Ebean.save(patientEncounterPhoto);
        }catch(Exception ex){

            Logger.error("PhotoRepository-createEncounterPhoto", ex);
            throw ex;
        }

        return patientEncounterPhoto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounterPhoto> retrieveEncounterPhotosByEncounterId(int encounterId){

        List<? extends IPatientEncounterPhoto> photos;

        try{

            ExpressionList<PatientEncounterPhoto> query = QueryProvider.getPatientEncounterPhotoQuery()
                    .where()
                    .eq("patient_encounter_id", encounterId);

            photos = query.findList();
        } catch (Exception ex){

            Logger.error("PhotoRepository-retrieveEncounterPhotosByEncounterId", ex);
            throw ex;
        }

        return photos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounterPhoto> retrieveEncounterPhotosByPhotoId(int photoId){

        List<? extends IPatientEncounterPhoto> photos;

        try{

            ExpressionList<PatientEncounterPhoto> query = QueryProvider.getPatientEncounterPhotoQuery()
                    .where()
                    .eq("photo_id", photoId);

            photos = query.findList();
        } catch (Exception ex){

            Logger.error("PhotoRepository-retrieveEncounterPhotosByPhotoId", ex);
            throw ex;
        }

        return photos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPhoto retrievePhotoById(int id){

        IPhoto photo;

        try {

            ExpressionList<Photo> query = QueryProvider.getPhotoQuery()
                    .where()
                    .eq("id", id);

            photo = query.findOne();
        } catch (Exception ex) {

            Logger.error("PhotoRepository-retrievePhotoById", ex);
            throw ex;
        }

        return photo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPhoto updatePhotoDescription(int id, String description){

        IPhoto photo;

        try {

            photo = retrievePhotoById(id);
            photo.setDescription(description);
            Ebean.save(photo);
        } catch (Exception ex) {

            Logger.error("PhotoRepository-updatePhotoDescription", ex);
            throw ex;
        }

        return photo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPhoto updatePhotoFilePath(int id, String filePath){

        IPhoto photo;

        try {

            photo = retrievePhotoById(id);
            photo.setFilePath(filePath);
            Ebean.save(photo);
        } catch (Exception ex) {

            Logger.error("PhotoRepository-updatePhotoDescription", ex);
            throw ex;
        }

        return photo;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public IPhoto updatePhotoData(int id, byte[] photoData){

        IPhoto photo;

        try {

            photo = retrievePhotoById(id);
            photo.setPhotoBlob(photoData);
            Ebean.save(photo);
        } catch (Exception ex) {

            Logger.error("PhotoRepository-updatePhotoData", ex);
            throw ex;
        }

        return photo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deletePhotoById(int id){

        try {

            IPhoto photo = retrievePhotoById(id);
            Ebean.delete(photo);
        } catch (Exception ex) {

            Logger.error("PhotoRepository-deletePhoto", ex);
            throw ex;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deletePhotoFromFilesystemById(String filePath){

        //track if the photo located @ filePath actually gets deleted
        boolean isDeleted;

        if (StringUtils.isNullOrWhiteSpace(filePath)){

            Logger.error("PhotoService-deletePhotoFromFilesystemById: no filePath to delete");
            return false;
        }

        try {

            File photoToDelete = new File(filePath);
            isDeleted = photoToDelete.delete();
        } catch (Exception ex) {

            Logger.error("PhotoService-deletePhotoFromFilesystemById", ex);
            return false;
        }

        return isDeleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEncounterPhotosByPhotoId(int id){

        try {

            List<? extends IPatientEncounterPhoto> photoRecordsToDelete = retrieveEncounterPhotosByPhotoId(id);

            if (photoRecordsToDelete != null) {

                Ebean.deleteAll(photoRecordsToDelete);
            }

        } catch (Exception ex) {

            Logger.error("PhotoRepository-deleteEncounterPhotosByPhotoId", ex);
            throw ex;
        }

        return true;
    }
}
