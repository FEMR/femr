package femr.util;


import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
    One time use utility to migrate file system photos
      to the Photo table's respective clob field
 */
public class PhotoMigration {

    public class Photo {
        public String FilePath;
        public int Id;
    }

    public static void main(String[] args) {

        try
        {
            String url  = ConfigFactory.load().getString("db.default.url");
            String user = ConfigFactory.load().getString("db.default.username");
            String pass = ConfigFactory.load().getString("db.default.password");
            String patPath = ConfigFactory.load().getString("photos.path");
            String encPath = ConfigFactory.load().getString("photos.encounterPath");

            PhotoMigration pm = new PhotoMigration(url, user, pass, patPath, encPath);


            //NOTE: Set to TRUE if you want the migration
            //       to overwrite existing blob fields
            pm.MigratePhotos(false);

        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }


    }

    private String _patPhotoPath;
    private String _encPhotoPath;
    private String _dbUrl;
    private String _dbUser;
    private String _dbPass;

    public PhotoMigration(String dbUrl, String dbUserName, String dbPassword, String patientPhotoPath, String encounterPhotoPath)
    {
        _dbUrl = dbUrl;
        _dbUser = dbUserName;
        _dbPass = dbPassword;

        _patPhotoPath = patientPhotoPath;
        _encPhotoPath = encounterPhotoPath;

        if(!_patPhotoPath.endsWith("/"))
            _patPhotoPath += "/";

        if(!_encPhotoPath.endsWith("/"))
            _encPhotoPath += "/";
    }


    private Connection getConnection() throws Exception
    {
        return DriverManager.getConnection(_dbUrl, _dbUser, _dbPass);
    }

    private List<Photo> GetAllPhotoRecords(boolean allowReprocessing) throws Exception
    {
        List<Photo> rslts = new ArrayList<>();

        String sql = "select id, file_path from photos where length(file_path) > 0";
        if(!allowReprocessing)
            sql += " and photo is null";

        try(Connection con = getConnection()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                Photo p = new Photo();
                p.FilePath = rs.getString("file_path");
                p.Id = rs.getInt("id");
                rslts.add(p);
            }
        }
        System.out.println("Fetched " + rslts.size() + " records");
        return rslts;
    }

    private boolean UpdatePhotoBlob(Connection con, int id, byte[] imgData) throws Exception
    {
        String sql = "update photos set photo = ? where id = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setBytes(1, imgData);
        pstmt.setInt(2, id);
        return pstmt.execute();
    }


    /*
       Perform migration.  If allowReprocessing is true, blobs will get overwritten,
          otherwise, records with non-null photo fields will be skipped.
     */
    public void MigratePhotos(boolean allowReprocessing) throws Exception
    {
        int iSuccess = 0;
        int iFails = 0;
        try  {
            List<Photo> allPhotos = GetAllPhotoRecords(allowReprocessing);
            if(allPhotos != null)
            {
                try(Connection con = getConnection()){
                    for(Photo p : allPhotos)
                    {
                        String fullPath = GetFullFilePath(p.FilePath);
                        try {
                            byte[] imgData = GetFileData(fullPath);
                            if(imgData != null) {
                                UpdatePhotoBlob(con, p.Id, imgData);
                                System.out.println("Success: 'Id: " +  p.Id + " - " + fullPath + "'");
                                iSuccess++;
                            } else {
                                iFails++;
                                System.out.println("Warning: File not found: 'Id: " + p.Id + " - " + fullPath + "'");
                            }
                        } catch (Exception ex)
                        {
                            System.out.println("Error processing '" +  p.Id + " - " + fullPath +  "'.  " + ex.getMessage());
                            iFails++;
                        }
                    }
                }
                System.out.println("Total Records: " + allPhotos.size());
            }
            System.out.println("Total Completed: " + iSuccess + ", Total Failures: " + iFails);
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    protected byte[] GetFileData(String path) throws Exception
    {
            File tmpCheck = new File(path);
            if(tmpCheck.exists()) {
                return Files.readAllBytes(Paths.get(path));
            }
            return null;
    }

    protected String GetFullFilePath(String fileName)
    {
        String path;

        //Patient records start for a forward slash for some reason, need to remove that:
        if(fileName.startsWith("/"))
            fileName = fileName.substring(1);

        if(fileName.toLowerCase().contains("_enc_")) {
            //This is an encounter photo
            path = _encPhotoPath + fileName;

        } else {
            //This is a patient photo
            path = _patPhotoPath + fileName;
        }

        return path;
//            File tmpCheck = new File(path);
//            if(tmpCheck.exists()) {
//                return Files.readAllBytes(Paths.get(path));
//            } else {
//                System.out.println("Warning: File not found: '" + path.toString() + "'");
//            }


    }
}