package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.typesafe.config.ConfigFactory;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.common.models.PatientEncounterItem;
import femr.common.dto.ServiceResponse;
import com.google.inject.Inject;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.ui.models.medical.EditViewModelPost;
import play.mvc.Http.MultipartFormData.FilePart;


public class PhotoService implements IPhotoService {
    private String _profilePhotoPath;
    private String _encounterPhotoPath;
    private IRepository<IPhoto> patientPhotoRepository;
    private IRepository<IPatient> patientRepository;
    private IRepository<IPatientEncounterPhoto> patientEncounterPhotoRepository;
    private DomainMapper domainMapper;

    @Inject
    public PhotoService(IRepository<IPhoto> patientPhotoRepository,
                        IRepository<IPatient> patientRepository,
                        IRepository<IPatientEncounterPhoto> patientEncounterPhotoRepository,
                        DomainMapper domainMapper)
    {
        this.patientPhotoRepository = patientPhotoRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterPhotoRepository = patientEncounterPhotoRepository;
        this.domainMapper = domainMapper;

        this.Init();
    }

    protected void Init()
    {
        File f;
        try
        {
            _profilePhotoPath = ConfigFactory.load().getString("photos.path");
        }
        catch(Exception ex)
        {
            //If config doesn't exist, default to "photos"
            _profilePhotoPath = "./Upload/Pictures/Patients";
        }

        try
        {
            _encounterPhotoPath = ConfigFactory.load().getString("photos.encounterPath");
        }
        catch(Exception ex)
        {
            _encounterPhotoPath = "./Upload/Pictures/PatientEncounters";
        }

        //Append ending slash if needed
        if(!_profilePhotoPath.endsWith(File.separator))
            _profilePhotoPath += File.separator;

        if(!_encounterPhotoPath.endsWith(File.separator))
            _encounterPhotoPath += File.separator;


        //Ensure folder exists, if not, create it
        f = new File(_profilePhotoPath);
        if(!f.exists())
            f.mkdirs();

        f = new File(_encounterPhotoPath);
        if(!f.exists())
            f.mkdirs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> SavePatientPhotoAndUpdatePatient(File img, int patientId, Boolean deleteFlag)
    {
        ServiceResponse<Boolean> response = new ServiceResponse<>();

        ExpressionList<Patient> query = QueryProvider.getPatientQuery()
                .where()
                .eq("id", patientId);

        try
        {
            IPatient patient = patientRepository.findOne(query);
            String imageFileName = "Patient_" + patient.getId() + ".jpg";

            if(img != null)
            {
                if(patient.getPhoto() == null)
                {
                    //Create new photo Id record
                    IPhoto pPhoto = domainMapper.createPhoto("", imageFileName);
                    pPhoto = patientPhotoRepository.create(pPhoto);
                    patient.setPhoto(pPhoto);
                    patientRepository.update(patient);
                }
                else
                {
                    //Record already exists:
                    //photoId = patient.getPhoto().getId();
                }

                //save image to disk
                Path src = FileSystems.getDefault().getPath(img.getAbsolutePath());
                Path dest = FileSystems.getDefault().getPath(_profilePhotoPath + imageFileName);
                java.nio.file.Files.move(src, dest, StandardCopyOption.ATOMIC_MOVE);

            }
            else
            {
                if(deleteFlag != null)
                    if(deleteFlag && patient.getPhoto() != null)
                    {
                        //delete photo
                        //First make sure the photoId is null in the patient record
                        Integer id = patient.getPhoto().getId();
                        patient.setPhoto(null);
                        patientRepository.update(patient);
                        //Now remove the photo record:
                        this.deletePhotoById(id, _profilePhotoPath);
                    }
            }
            response.setResponseObject(true);
        }
        catch(Exception ex)
        {
            response.setResponseObject(false);
            response.addError("HandlePatientPhoto", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<String> getPhotoPathForPatient(int patientId){
        ServiceResponse<String> response = new ServiceResponse<>();
        ExpressionList<Patient> query = QueryProvider.getPatientQuery()
                .where()
                .eq("id", patientId);
        try{
            IPatient patient = patientRepository.findOne(query);
            if (patient.getPhoto() == null){
                response.setResponseObject(null);
            }else{
                response.setResponseObject(_profilePhotoPath + patient.getPhoto().getFilePath());
            }
        }catch(Exception ex){
            response.addError("", ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<String> getPhotoPath(int photoId){
        ServiceResponse<String> response = new ServiceResponse<>();
        ExpressionList<Photo> query = QueryProvider.getPhotoQuery()
                .where()
                .eq("id", photoId);
        try{
            IPhoto photo = patientPhotoRepository.findOne(query);
            response.setResponseObject(_encounterPhotoPath + photo.getFilePath());
        }catch(Exception ex){
            response.addError("", ex.getMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<Boolean> HandleEncounterPhotos(List<FilePart> encounterImages, PatientEncounterItem patientEncounterItem, EditViewModelPost mod)
    {
        ServiceResponse<Boolean> sr = new ServiceResponse<>();
        try
        {
            int count = mod.getPhotoId().size();
            FilePart fp;
            for(int i = 0; i < count; i++)
            {
                Integer id = mod.getPhotoId().get(i);
                if(id == null)
                {
                    //This is a new image, add it to the DB and filesystem:
                    fp = GetImageByIndex(encounterImages, i);
                    saveNewEncounterImage(fp, patientEncounterItem, mod.getImageDescText().get(i));
                }
                else
                {
                    Boolean bDelete = mod.getDeleteRequested().get(i);
                    if(bDelete == null)
                        bDelete = false;

                    if(bDelete)
                    {
                        //Delete image
                        if(mod.getPhotoId().get(i) != null)
                            this.deletePhotoById(mod.getPhotoId().get(i), _encounterPhotoPath);
                    }
                    else
                    {
                        //Possibly update the image
                        Boolean bisUpdate = mod.getHasUpdatedDesc().get(i);
                        if(bisUpdate != null)
                        {
                            if(bisUpdate)
                            {
                                Integer photoId = mod.getPhotoId().get(i);
                                if(photoId != null)
                                    updateImageDescription(photoId, mod.getImageDescText().get(i));
                            }
                        }
                    }

                }
            }
            sr.setResponseObject(true);
        }
        catch(Exception ex)
        {
            sr.setResponseObject(false);
            sr.addError("HandleEncounterPhotos()", ex.getMessage());
        }

        return sr;
    }



    @Override
    public ServiceResponse<List<IPhoto>> GetEncounterPhotos(int encounterId)
    {

        ServiceResponse<List<IPhoto>> srlst = new ServiceResponse<>();
        try
        {
            ExpressionList<PatientEncounterPhoto> query = Ebean.find(PatientEncounterPhoto.class)
                    .where().eq("patient_encounter_id", encounterId);

            List<? extends IPatientEncounterPhoto> photoList = patientEncounterPhotoRepository.find(query);
            if(photoList != null)
            {
                List<IPhoto> returnList = new ArrayList<IPhoto>();
                for(IPatientEncounterPhoto pep : photoList)
                {
                    ServiceResponse<IPhoto> srp = getPhotoById(pep.getPhotoId());
                    if(!srp.hasErrors())
                        returnList.add(srp.getResponseObject());
                }

                srlst.setResponseObject(returnList);
            }
        }
        catch(Exception ex)
        {
            srlst.addError("photo", ex.getMessage());
        }

        return srlst;
    }



    /***
     * Helper function to fetch image by named index
     * @param encounterImages
     * @param index
     * @return
     */
    protected FilePart GetImageByIndex(List<FilePart> encounterImages, Integer index)
    {
        for(FilePart fp : encounterImages)
        {
            String keyName = fp.getKey();
            int leftBracket = keyName.indexOf("[");
            int rightBracket = keyName.indexOf("]");
            if(leftBracket >= 0 && rightBracket >= 0)
            {
                keyName = keyName.substring(leftBracket + 1, rightBracket);
                String tempindex = index.toString();

                if(keyName.equalsIgnoreCase(tempindex))
                {
                    return fp;
                }
            }
        }
        return null;
    }


















    @Override
    public ServiceResponse<IPhoto> getPhotoById(int id)
    {

        ExpressionList<Photo> query = Ebean.find(Photo.class).where().eq("id", id);
        IPhoto savedPhoto = patientPhotoRepository.findOne(query);

        ServiceResponse<IPhoto> response = new ServiceResponse<>();

        if (savedPhoto != null){
            response.setResponseObject(savedPhoto);
        }
        else
        {
            response.addError("photo","photo could not be fetched from the database");
        }

        return response;
    }

    protected void saveNewEncounterImage(FilePart image, PatientEncounterItem patientEncounter, String descriptionText)
    {
        try
        {
            String imageFileName;
            int photoId;

            //Create photo record:
            IPhoto pPhoto = new Photo();
            pPhoto.setDescription(descriptionText);
            pPhoto.setFilePath("");
            pPhoto = patientPhotoRepository.create(pPhoto);
            photoId = pPhoto.getId();

            imageFileName = "Patient_" + patientEncounter.getPatientId()
                + "_Enc_" + patientEncounter.getId() + "_Photo_" + photoId;
            pPhoto.setFilePath(imageFileName);

            //Since the record ID is part of the file name
            //  I am setting the filePath field after the record is created
            this.UpdatePhotoRecord(pPhoto);

            //Link photo record in photoEncounter table
            IPatientEncounterPhoto pep = new PatientEncounterPhoto();
            pep.setPhotoId(photoId);
            pep.setPatientEncounterId(patientEncounter.getId());
            patientEncounterPhotoRepository.create(pep);

            //Save image to disk
            Path src = FileSystems.getDefault().getPath(image.getFile().getAbsolutePath());
            Path dest = FileSystems.getDefault().getPath(this._encounterPhotoPath + imageFileName);
            java.nio.file.Files.move(src, dest, StandardCopyOption.ATOMIC_MOVE);
        }
        catch(Exception ex)
        {

        }
    }

    protected void updateImageDescription(int photoId, String descriptionText)
    {
        ServiceResponse<IPhoto> srPhoto = this.getPhotoById(photoId);
        if(!srPhoto.hasErrors())
        {
            IPhoto photo = srPhoto.getResponseObject();
            photo.setDescription(descriptionText);
            this.UpdatePhotoRecord(photo);
        }
    }

    /**
     * Handles all things related to the patient photo. If img is null and
     *  deleteFlag == true, this method will delete the photo. Else,
     *  it will update/create the patient photo
     * @param img
     * @param patientId
     * @param deleteFlag
     * @return Returns the new image Id on Save/Update, else null
     */




    protected void UpdatePhotoRecord(IPhoto photoRecord)
    {
        ExpressionList<Photo> query =
                Ebean.find(Photo.class).where().eq("id", photoRecord.getId());
        IPhoto editPhoto = patientPhotoRepository.findOne(query);

        if (editPhoto != null){
            editPhoto.setFilePath(photoRecord.getFilePath());
            editPhoto.setDescription(photoRecord.getDescription());
            patientPhotoRepository.update(editPhoto);
        }
    }







    protected ServiceResponse<IPhoto> deletePhotoById(int id, String imagePath)
    {
        ExpressionList<Photo> query = Ebean.find(Photo.class).where().eq("id", id);
        IPhoto savedPhoto = patientPhotoRepository.findOne(query);
        if(savedPhoto != null)
        {
            //Delete any references from the patientencounterphotos table
            ExpressionList<PatientEncounterPhoto> peQuery =
                       Ebean.find(PatientEncounterPhoto.class).where().eq("photo_id", id);
            List<? extends IPatientEncounterPhoto> pep = patientEncounterPhotoRepository.find(peQuery);
            if(pep != null)
                Ebean.delete(pep);

            File photo = new File(imagePath + savedPhoto.getFilePath());
            photo.delete();


            Ebean.delete(savedPhoto);

        }
        ServiceResponse<IPhoto> response = new ServiceResponse<>();

        if (savedPhoto != null){
            response.setResponseObject(savedPhoto);
        }
        else{
            response.addError("photo","photo could not be deleted from the database");
        }

        return response;
    }





}
