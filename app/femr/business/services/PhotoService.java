package femr.business.services;

import com.typesafe.config.ConfigFactory;

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import com.google.inject.Inject;
import femr.data.daos.IRepository;



public class PhotoService implements IPhotoService {
    private String _path;
    private IRepository<IPhoto> patientPhotoRepository;

    @Inject
    public PhotoService(IRepository<IPhoto> patientPhotoRepository)
    {
        this.patientPhotoRepository = patientPhotoRepository;

        this.Init();
    }

    protected void Init()
    {
        File f;
        _path = ConfigFactory.load().getString("photos.path");
        if(_path == null)
            _path = "photos";

        //Append ending slash if needed
        if(!_path.endsWith(File.separator))
            _path += File.separator;

        //Ensure folder exists, if not, create it
        f = new File(_path);
        if(!f.exists())
            f.mkdirs();
    }

    @Override
    public String GetPhotoPath() { return _path; }//returns the path to the photo

    //searches through the coordinate for data
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

    @Override
    public void SavePhoto(File imgFile, String fileName)
    {
        try
        {
            //setting up a path for the newely created file;
            //the image is going to be stored in that file and its a JPG format.
            File outFile = new File(_path + fileName);
            outFile.createNewFile();
            ImageIO.write(ImageIO.read(imgFile), "jpg", outFile);
        }catch(Exception ex)
        {
        }
    }

    @Override
    public void CropImage(File imgFile, String coords)
    {
        int[] vals = this.parseCoords(coords);
        if(vals != null)
            CropImage(imgFile, vals[0], vals[1], vals[2], vals[3]);
    }
//cropping the original image size so it fits with the format of the software
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

    @Override
    public ServiceResponse<IPhoto> createPhoto(IPhoto photo)
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
}
