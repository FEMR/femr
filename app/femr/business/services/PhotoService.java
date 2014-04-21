package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.google.inject.Provider;
import com.typesafe.config.ConfigFactory;

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import com.google.inject.Inject;
import femr.data.daos.IRepository;
import femr.data.models.*;
import femr.ui.models.data.PatientItem;
import femr.ui.models.medical.CreateViewModelPost;
import play.mvc.Http.MultipartFormData.FilePart;


public class PhotoService implements IPhotoService {
    private String _profilePhotoPath;
    private String _encounterPhotoPath;
    private IRepository<IPhoto> patientPhotoRepository;
    private IRepository<IPatient> patientRepository;
    private IRepository<IPatientEncounterPhoto> patientEncounterPhotoRepository;
    private Provider<IPatient> patientProvider;

    @Inject
    public PhotoService(IRepository<IPhoto> patientPhotoRepository,
                        IRepository<IPatient> patientRepository,
                        IRepository<IPatientEncounterPhoto> patientEncounterPhotoRepository,
                        Provider<IPatient> patientProvider)
    {
        this.patientPhotoRepository = patientPhotoRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterPhotoRepository = patientEncounterPhotoRepository;
        this.patientProvider = patientProvider;

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

    @Override
    public String GetRootPhotoPath()
    {
        return _profilePhotoPath;
    }

    @Override
    public String GetRootEncounterPhotoPath()
    {
        return _encounterPhotoPath;
    }

    protected int[] parseCoords(String s)
    {
        int[] iout = {0,0,0,0};
        String[] sVals;
        if(s == null)
            return null;
        s = s.replace("{", "");
        s = s.replace("}", "");
        sVals = s.split(",");

        if(sVals.length == 4)
        {
            for(int i = 0; i < 4; i++)
                iout[i] = Integer.parseInt(sVals[i]);
            return iout;
        }
        return iout;
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
    public ServiceResponse<Boolean> HandleEncounterPhotos(List<FilePart> encounterImages, IPatientEncounter patientEncounter, CreateViewModelPost mod)
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
                    saveNewEncounterImage(fp, patientEncounter, mod.getImageDescText().get(i));
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

    protected void saveNewEncounterImage(FilePart image, IPatientEncounter patientEncounter, String descriptionText)
    {
        try
        {
            String imageFileName;
            int photoId;

            //Create photo record:
            IPhoto pPhoto = new Photo();
            pPhoto.setDescription(descriptionText);
            pPhoto.setFilePath("");
            pPhoto = createPhoto(pPhoto);
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
            this.createEncounterPhoto(pep);

            //Save image to disk
            SavePhoto(image.getFile(), this._encounterPhotoPath + imageFileName);
        }
        catch(Exception ex)
        {
            throw ex;
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
     * @param patientItem
     * @param coords
     * @param deleteFlag
     * @return Returns the new image Id on Save/Update, else null
     */
    @Override
    public ServiceResponse<Boolean> HandlePatientPhoto(File img, PatientItem patientItem, String coords, Boolean deleteFlag)
    {
        IPatient patient = populatePatient(patientItem);

        String sFileName = "Patient_" + patient.getId() + ".jpg";
        Integer photoId;

        ServiceResponse<Boolean> sr = new ServiceResponse<>();
        try
        {
            if(img != null)
            {
                if(patient.getPhotoId() == null)
                {
                    //Create new photo Id record
                    IPhoto pPhoto = new Photo();
                    pPhoto.setDescription("");
                    pPhoto.setFilePath(sFileName);
                    pPhoto = createPhoto(pPhoto);
                    photoId = pPhoto.getId();
                }
                else
                {
                    //Record already exists:
                    photoId = patient.getPhotoId();
                }

                CropImage(img, coords); //Crop image
                SavePhoto(img, _profilePhotoPath + sFileName);  //Save to disk


                //Update patient photoId
                patient.setPhotoId(photoId);
                patientRepository.update(patient);
            }
            else
            {
                if(deleteFlag != null)
                    if(deleteFlag && patient.getPhotoId() != null)
                    {
                        //delete photo
                        //First make sure the photoId is null in the patient record
                        Integer id = patient.getPhotoId();
                        patient.setPhotoId(null);
                        patientRepository.update(patient);
                        //Now remove the photo record:
                        this.deletePhotoById(id, _profilePhotoPath);
                    }
            }
            sr.setResponseObject(true);
        }
        catch(Exception ex)
        {
            sr.setResponseObject(false);
            sr.addError("HandlePatientPhoto", ex.getMessage());
        }
        return sr;
    }

    protected void SavePhoto(File imgFile, String fileName)
    {
        try
        {
            Path src = FileSystems.getDefault().getPath(imgFile.getAbsolutePath());
            Path dest = FileSystems.getDefault().getPath(fileName);
            java.nio.file.Files.move(src, dest, StandardCopyOption.ATOMIC_MOVE);
        }catch(Exception ex)
        {
        }
    }

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

    protected void CropImage(File imgFile, String coords)
    {
        int[] vals = this.parseCoords(coords);
        if(vals != null)
            CropImage(imgFile, vals[0], vals[1], vals[2], vals[3]);
    }

    protected void CropImage(File imgFile, int offset_x, int offset_y, int width, int height)
    {
        try
        {
            BufferedImage destImg;
            BufferedImage imgIn = ImageIO.read(imgFile);
            destImg = imgIn.getSubimage(offset_x, offset_y, width, height);

            //File outFile = new File(imgFile.getAbsolutePath());
            //outFile.createNewFile();
            ImageIO.write(destImg, "jpg", imgFile);
        }
        catch(Exception ex)
        {
        }
    }

    protected IPhoto createPhoto(IPhoto photo)
    {
        IPhoto newPhoto = patientPhotoRepository.create(photo);

        if (newPhoto != null){
            return newPhoto;
        }

        return null;
    }

    protected IPatientEncounterPhoto createEncounterPhoto(IPatientEncounterPhoto encPhoto)
    {
        return patientEncounterPhotoRepository.create(encPhoto);
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

    protected void deleteImageFile(String path)
    {
        try
        {
            File photo = new File(path);
            photo.delete();
        }
        catch(Exception ex)
        {
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

            deleteImageFile(imagePath + savedPhoto.getFilePath()); //Delete file
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

    private IPatient populatePatient(PatientItem patient){
        //create an IPatient from a PatientItem
        //includes the ID
        IPatient newPatient = patientProvider.get();
        newPatient.setId(newPatient.getId());
        newPatient.setUserId(patient.getUserId());
        newPatient.setFirstName(patient.getFirstName());
        newPatient.setLastName(patient.getLastName());
        newPatient.setAge(patient.getBirth());
        newPatient.setSex(patient.getSex());
        newPatient.setAddress(patient.getAddress());
        newPatient.setCity(patient.getCity());
        newPatient.setPhotoId(patient.getPhotoId());

        return newPatient;
    }

}
