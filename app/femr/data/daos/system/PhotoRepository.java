package femr.data.daos.system;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.helpers.QueryProvider;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IPhotoRepository;
import femr.data.models.core.IPatientEncounterPhoto;
import femr.data.models.core.IPhoto;
import femr.data.models.mysql.PatientEncounterPhoto;
import femr.data.models.mysql.Photo;
import play.Logger;

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
    public IPhoto createPhoto(String description, String filePath) {

        IPhoto photo;

        //this can't be done because of weird logic in the service layer right now
       /* if (StringUtils.isNullOrWhiteSpace(filePath)){

            Logger.debug("a NULL or blank filePath was passed into createPhoto for a new photo");
            return photo;
        }*/

        try {

            photo = dataModelMapper.createPhoto(description, filePath);
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

            photo = query.findUnique();
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
    public boolean deleteEncounterPhotosByPhotoId(int id){

        try {

            List<? extends IPatientEncounterPhoto> photoRecordsToDelete = retrieveEncounterPhotosByPhotoId(id);

            if (photoRecordsToDelete != null) {

                Ebean.delete(photoRecordsToDelete);
            }

        } catch (Exception ex) {

            Logger.error("PhotoRepository-deleteEncounterPhotosByPhotoId", ex);
            throw ex;
        }

        return true;
    }
}
