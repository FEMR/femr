package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.business.services.system.DbDumpService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;

import femr.ui.views.html.backup.backup;
import femr.ui.views.html.backup.upload;
import femr.business.services.core.IPatientService;
import femr.business.services.core.IEncounterService;
import femr.common.models.PatientItem;
import femr.common.dtos.ServiceResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import play.data.FormFactory;
import play.db.Database;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import play.mvc.Http.MultipartFormData;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE, Roles.MANAGER, Roles.RESEARCHER, Roles.ADMINISTRATOR})
public class DatabaseDumpController extends Controller {
    private final AssetsFinder assetsFinder;
    private final ISessionService sessionService;
    private final Database database;
    private final IPatientService patientService;
    private final IEncounterService encounterService;

    @Inject
    public DatabaseDumpController(AssetsFinder assetsFinder, ISessionService sessionService, Database database, IPatientService patientService, IEncounterService encounterService){
        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
        this.database = database;
        this.patientService = patientService;
        this.encounterService = encounterService;
    }
    public Result indexGet(){
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        return ok(backup.render(currentUser, assetsFinder, getCommand(), ""));
    }

    public Result indexPost(){
        String successMessage = "Data Backup Successful!";
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        DbDumpService dbService = new DbDumpService();
        ServiceResponse<Boolean> response = dbService.getAllData();
        if (!response.getResponseObject()){
            successMessage = "Data Backup Failed..";
        }
        return ok(backup.render(currentUser, assetsFinder, getCommand(), successMessage));
    }

    public Result uploadGet(){
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        return ok(upload.render(currentUser, assetsFinder, ""));
    }

    /**
     * Accepts a multipart form upload with a single file input named "sqlFile".
     * The uploader accepts either a CSV (.csv) to create patients, or a SQL dump (.sql)
     * which will be executed (naively split by semicolon) within a single transaction.
     * 
     * CSV Format (optional columns):
     * - firstName / first_name
     * - lastName / last_name
     * - phone / phoneNumber / phone_number
     * - address, city, sex
     * - birth / dob / dateOfBirth / date_of_birth (YYYY-MM-DD)
     * - years, months
     * - patientPhoto / patient_photo / patPhotoId / pat_photo_cropped (path to photo)
     * - encounterDate / encounter_date / checkInDate / checkin_date (YYYY-MM-DD) — if today, creates encounter
     * 
     * NOTE: CSV parsing is simple (does not support quoted fields with embedded commas).
     * NOTE: SQL import is best-effort; complex dumps (custom DELIMITER, stored routines)
     * may fail and should be handled offline.
     * NOTE: If encounterDate is today's date, an encounter will be automatically created
     * for the patient so it appears in the Manager "patients checked in today" view.
     */
    public Result uploadPost(){
        MultipartFormData<File> body = request().body().asMultipartFormData();
        MultipartFormData.FilePart<File> filePart = body.getFile("sqlFile");
        if(filePart == null){
            CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
            return badRequest(upload.render(currentUser, assetsFinder, "No file uploaded."));
        }

        File uploaded = filePart.getFile();
        String filename = "";
        if(filePart.getFilename() != null){
            filename = filePart.getFilename().toLowerCase();
        }
        String contentType = filePart.getContentType();

        // If CSV, parse and create patients
        if(filename.endsWith(".csv") || "text/csv".equalsIgnoreCase(contentType) || "application/csv".equalsIgnoreCase(contentType)){
            List<String> errors = new ArrayList<>();
            int created = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(uploaded))){
                String headerLine = reader.readLine();
                if(headerLine == null){
                    CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
                    return badRequest(upload.render(currentUser, assetsFinder, "CSV is empty."));
                }
                String[] headers = headerLine.split(",");
                Map<String, Integer> idx = new HashMap<>();
                for(int i=0;i<headers.length;i++){
                    idx.put(headers[i].trim().toLowerCase(), i);
                }

                String line;
                int row = 1;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
                while((line = reader.readLine()) != null){
                    row++;
                    if(line.trim().isEmpty()) continue;
                    // naive CSV split - supports simple CSVs without embedded commas/quotes
                    String[] cols = line.split(",", -1);
                    try{
                        PatientItem p = new PatientItem();
                        p.setUserId(currentUser.getId());
                        if(idx.containsKey("firstname")){
                            p.setFirstName(getCol(cols, idx.get("firstname")));
                        } else if(idx.containsKey("first_name")){
                            p.setFirstName(getCol(cols, idx.get("first_name")));
                        }
                        if(idx.containsKey("lastname")){
                            p.setLastName(getCol(cols, idx.get("lastname")));
                        } else if(idx.containsKey("last_name")){
                            p.setLastName(getCol(cols, idx.get("last_name")));
                        }
                        if(idx.containsKey("phone") || idx.containsKey("phonenumber") || idx.containsKey("phone_number")){
                            Integer k = idx.containsKey("phone")?idx.get("phone"):(idx.containsKey("phonenumber")?idx.get("phonenumber"):idx.get("phone_number"));
                            p.setPhoneNumber(getCol(cols, k));
                        }
                        if(idx.containsKey("address")) p.setAddress(getCol(cols, idx.get("address")));
                        if(idx.containsKey("city")) p.setCity(getCol(cols, idx.get("city")));
                        if(idx.containsKey("sex")) p.setSex(getCol(cols, idx.get("sex")));
                        if(idx.containsKey("patphoto") || idx.containsKey("pat_photo") || idx.containsKey("patientphoto") || idx.containsKey("patient_photo") || idx.containsKey("pat_photo_cropped") || idx.containsKey("patientphotocropped") ){
                            Integer k = null;
                            if(idx.containsKey("patientphotocropped")) k = idx.get("patientphotocropped");
                            else if(idx.containsKey("pat_photo_cropped")) k = idx.get("pat_photo_cropped");
                            else if(idx.containsKey("patient_photo_cropped")) k = idx.get("patient_photo_cropped");
                            else if(idx.containsKey("patientphoto")) k = idx.get("patientphoto");
                            else if(idx.containsKey("patient_photo")) k = idx.get("patient_photo");
                            else if(idx.containsKey("patphoto")) k = idx.get("patphoto");
                            if(k != null) p.setPathToPhoto(getCol(cols, k));
                        }

                        // birth date if provided
                        if(idx.containsKey("birth") || idx.containsKey("dob") || idx.containsKey("dateofbirth") || idx.containsKey("date_of_birth")){
                            Integer k = idx.containsKey("birth")?idx.get("birth"):(idx.containsKey("dob")?idx.get("dob"):(idx.containsKey("dateofbirth")?idx.get("dateofbirth"):idx.get("date_of_birth")));
                            String births = getCol(cols, k);
                            if(births != null && !births.isEmpty()){
                                try{
                                    Date d = sdf.parse(births);
                                    p.setBirth(d);
                                }catch(ParseException pe){
                                    // ignore parse error, fallback to years/months
                                }
                            }
                        }

                        // years/months
                        if(idx.containsKey("years") && !hasBirth(p)){
                            String yrs = getCol(cols, idx.get("years"));
                            String mns = idx.containsKey("months")?getCol(cols, idx.get("months")):"0";
                            try{
                                int yi = Integer.parseInt(yrs.trim());
                                int mi = Integer.parseInt(mns.trim());
                                p.setYearsOld(yi);
                                p.setMonthsOld(mi);
                                // compute birth by subtracting years/months
                                java.util.Calendar calendar = java.util.Calendar.getInstance();
                                calendar.add(java.util.Calendar.YEAR, -yi);
                                calendar.add(java.util.Calendar.MONTH, -mi);
                                p.setBirth(calendar.getTime());
                            }catch(NumberFormatException nfe){
                                // ignore
                            }
                        }

                        // create patient
                        ServiceResponse<PatientItem> resp = patientService.createPatient(p);
                        if(resp.hasErrors() || resp.getResponseObject() == null){
                            errors.add("Row " + row + ": " + String.join("; ", resp.getErrors().values()));
                        } else {
                            created++;
                            
                            // If encounterDate is provided and it's today, create an encounter for this patient
                            if(idx.containsKey("encounterdate") || idx.containsKey("encounter_date") || idx.containsKey("checkindate") || idx.containsKey("checkin_date")){
                                Integer dateIdx = null;
                                if(idx.containsKey("encounterdate")) dateIdx = idx.get("encounterdate");
                                else if(idx.containsKey("encounter_date")) dateIdx = idx.get("encounter_date");
                                else if(idx.containsKey("checkindate")) dateIdx = idx.get("checkindate");
                                else if(idx.containsKey("checkin_date")) dateIdx = idx.get("checkin_date");
                                
                                if(dateIdx != null){
                                    String dateStr = getCol(cols, dateIdx);
                                    if(dateStr != null && !dateStr.isEmpty()){
                                        try{
                                            Date encDate = sdf.parse(dateStr);
                                            // Check if encounter date is today
                                            java.util.Calendar encCal = java.util.Calendar.getInstance();
                                            encCal.setTime(encDate);
                                            encCal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                                            encCal.set(java.util.Calendar.MINUTE, 0);
                                            encCal.set(java.util.Calendar.SECOND, 0);
                                            
                                            java.util.Calendar todayCal = java.util.Calendar.getInstance();
                                            todayCal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                                            todayCal.set(java.util.Calendar.MINUTE, 0);
                                            todayCal.set(java.util.Calendar.SECOND, 0);
                                            
                                            if(encCal.getTime().equals(todayCal.getTime())){
                                                // Create encounter for today with empty chief complaints
                                                ServiceResponse<?> encResp = encounterService.createPatientEncounter(
                                                    resp.getResponseObject().getId(),
                                                    currentUser.getId(),
                                                    null,
                                                    null,
                                                    new ArrayList<>()
                                                );
                                                if(encResp.hasErrors()){
                                                    errors.add("Row " + row + " (encounter): " + String.join("; ", encResp.getErrors().values()));
                                                }
                                            }
                                        }catch(ParseException pe){
                                            // ignore parse error for encounter date
                                        }
                                    }
                                }
                            }
                        }

                    }catch(Exception ex){
                        errors.add("Row " + row + ": " + ex.getMessage());
                    }
                }

            }catch(IOException e){
                CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
                return internalServerError(upload.render(currentUser, assetsFinder, "CSV read failed: " + e.getMessage()));
            }

            String summary = "Created " + created + " patients.";
            if(!errors.isEmpty()){
                summary += " Errors: " + String.join(" | ", errors);
            }
            CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
            return ok(upload.render(currentUser, assetsFinder, summary));
        }
        // Read and execute statements
        try(Connection con = database.getConnection()){
            con.setAutoCommit(false);
            try(BufferedReader reader = new BufferedReader(new FileReader(uploaded))){
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    line = line.trim();
                    // Skip comments and empty lines
                    if(line.isEmpty() || line.startsWith("--") || line.startsWith("/*") || line.startsWith("*/") || line.startsWith("#")){
                        continue;
                    }
                    sb.append(line).append(' ');
                    // naive statement delimiter
                    if(line.endsWith(";")){
                        String sql = sb.toString();
                        // remove trailing semicolon
                        sql = sql.substring(0, sql.lastIndexOf(';')).trim();
                        if(!sql.isEmpty()){
                            try(Statement stmt = con.createStatement()){
                                stmt.execute(sql);
                            }
                        }
                        sb.setLength(0);
                    }
                }
                con.commit();
            }catch(IOException | SQLException e){
                try { con.rollback(); } catch (SQLException ex) { /* ignore */ }
                CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
                return internalServerError(upload.render(currentUser, assetsFinder, "Upload failed: " + e.getMessage()));
            }
        }catch(SQLException e){
            CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
            return internalServerError(upload.render(currentUser, assetsFinder, "Database connection failed: " + e.getMessage()));
        }

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        return ok(upload.render(currentUser, assetsFinder, "Upload and import completed."));
    }

    private boolean getCommand(){
        try{
            Process process = new ProcessBuilder("which", "mysqldump").start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        }
        catch (IOException | InterruptedException e){
            return false;
        }

    }

    private String getCol(String[] cols, Integer index){
        if(index == null) return "";
        if(index < 0 || index >= cols.length) return "";
        String v = cols[index].trim();
        if(v.startsWith("\"") && v.endsWith("\"") && v.length() >= 2){
            v = v.substring(1, v.length()-1);
        }
        return v;
    }

    private boolean hasBirth(PatientItem p){
        return p.getBirth() != null;
    }

}
