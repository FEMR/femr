package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.typesafe.config.ConfigFactory;

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.nio.file.*;

import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import com.google.inject.Inject;
import femr.data.daos.IRepository;
import femr.data.models.*;


public class PhotoService implements IPhotoService {
    private String _path;
    private IRepository<IPhoto> patientPhotoRepository;
    private IRepository<IPatient> patientRepository;

    @Inject
    public PhotoService(IRepository<IPhoto> patientPhotoRepository,
                        IRepository<IPatient> patientRepository)
    {
        this.patientPhotoRepository = patientPhotoRepository;
        this.patientRepository = patientRepository;

        this.Init();
    }

    protected void Init()
    {
        File f;
        try
        {
            _path = ConfigFactory.load().getString("photos.path");
        }
        catch(Exception ex)
        {
            //If config doesn't exist, default to "photos"
            _path = "photos";
        }

        //Append ending slash if needed
        if(!_path.endsWith(File.separator))
            _path += File.separator;

        //Ensure folder exists, if not, create it
        f = new File(_path);
        if(!f.exists())
            f.mkdirs();
    }

    @Override
    public String GetPhotoPath() { return _path; }

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


    /**
     * Handles all things related to the patient photo. If img is null and
     *  deleteFlag == true, this method will delete the photo. Else,
     *  it will update/create the patient photo
     * @param img
     * @param patientId
     * @param coords
     * @param deleteFlag
     * @return Returns the new image Id on Save/Update, else null
     */
    @Override
    public void HandlePatientPhoto(File img, IPatient patient, String coords, Boolean deleteFlag)
    {
        String sFileName = "Patient_" + patient.getId() + ".jpg";
        Integer photoId;

        try
        {
            if(img != null)
            {
                if(patient.getPhotoId() == null)
                {
                    //Create new photo Id record
                    IPhoto pPhoto = new Photo();
                    pPhoto.setDescription("");
                    pPhoto.setFilePath(_path + sFileName);
                    ServiceResponse<IPhoto>  pPhotoResponse = createPhoto(pPhoto);
                    photoId = pPhoto.getId();
                }
                else
                {
                    //Record already exists:
                    photoId = patient.getPhotoId();
                }

                CropImage(img, coords); //Crop image
                SavePhoto(img, _path + sFileName);  //Save to disk


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
                        this.deletePhotoById(id);
                    }
            }
        }
        catch(Exception ex)
        {
            //TODO: Handle exception
        }
    }

    protected void SavePhoto(File imgFile, String fileName)
    {
        try
        {
            //File outFile = new File(_path + fileName);
            //outFile.createNewFile();
            //imgFile.renameTo(outFile);
            Path src = FileSystems.getDefault().getPath(imgFile.getAbsolutePath());
            Path dest = FileSystems.getDefault().getPath(fileName);
            //Path dest = basePath.resolve(_path + fileName);

            //Files.move(src, dest, REPLACE_EXISTING);

            java.nio.file.Files.move(src, dest, StandardCopyOption.ATOMIC_MOVE);
            //ImageIO.write(ImageIO.read(imgFile), "jpg", outFile);
        }catch(Exception ex)
        {
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

    protected ServiceResponse<IPhoto> createPhoto(IPhoto photo)
    {
        IPhoto newPhoto = patientPhotoRepository.create(photo);
        ServiceResponse<IPhoto> response = new ServiceResponse<>();

        if (newPhoto != null){
            response.setResponseObject(newPhoto);
        }
        else{
            response.addError("photo","photo could not be saved to database");
        }

        return response;
    }

    protected ServiceResponse<IPhoto> getPhotoById(int id)
    {

        ExpressionList<Photo> query = Ebean.find(Photo.class).where().eq("id", id);
        IPhoto savedPhoto = patientPhotoRepository.findOne(query);
        ServiceResponse<IPhoto> response = new ServiceResponse<>();

        if (savedPhoto != null){
            response.setResponseObject(savedPhoto);
        }
        else{
            response.addError("photo","photo could not be fetched from the database");
        }

        return response;
    }

    protected ServiceResponse<IPhoto> deletePhotoById(int id)
    {
        ExpressionList<Photo> query = Ebean.find(Photo.class).where().eq("id", id);
        IPhoto savedPhoto = patientPhotoRepository.findOne(query);
        if(savedPhoto != null)
            Ebean.delete(savedPhoto);
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
