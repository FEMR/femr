package femr.business.services.system;

import femr.business.services.core.IDbDumpService;
import femr.common.dtos.ServiceResponse;
import play.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;


public class DbDumpService implements IDbDumpService {

    // S3 upload timeout configuration
    private static final int S3_UPLOAD_TIMEOUT = 30000; // 30 seconds

    public DbDumpService(){}

    /**
     * Creates a database dump and uploads it to S3 via the Lambda endpoint.
     * Falls back to local storage if S3 endpoint is not configured.
     * 
     * @return ServiceResponse with success/failure status
     */
    @Override
    public ServiceResponse<Boolean> getAllData() {

        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        // Use absolute path in /tmp to ensure file is created in a known location
        String dumpFilePath = "/tmp/db_dump.sql.gz";
        
        try {
            // Step 1: Create the database dump using mysqldump
            String db_user = System.getenv("DB_USER");
            String db_password = System.getenv("DB_PASS");
            ProcessBuilder pb = new ProcessBuilder
                    ("mysqldump", "--host=db", String.format("--user=%s", db_user), 
                     String.format("--password=%s", db_password), "--all-databases");
            
            File outputFile = new File(dumpFilePath);
            pb.redirectOutput(ProcessBuilder.Redirect.to(outputFile));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            
            Logger.info("DbDumpService", "Database dump created: " + dumpFilePath);
            
            // Step 2: Upload to S3 if endpoint is configured
            String s3Endpoint = System.getenv("S3_BACKUP_ENDPOINT");  // Read dynamically
            if (s3Endpoint != null && !s3Endpoint.isEmpty()) {
                boolean uploadSuccess = uploadToS3Endpoint(dumpFilePath);
                if (uploadSuccess) {
                    Logger.info("DbDumpService", "Successfully uploaded dump to S3");
                    serviceResponse.setResponseObject(true);
                    // Clean up local copy after successful S3 upload
                    Files.deleteIfExists(Paths.get(dumpFilePath));
                    return serviceResponse;
                } else {
                    Logger.warn("DbDumpService", "S3 upload failed, keeping local copy");
                    // Fall through to return local dump success
                }
            } else {
                Logger.info("DbDumpService", "S3_BACKUP_ENDPOINT not configured, using local storage");
            }
            
            serviceResponse.setResponseObject(true);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Logger.error("DbDumpService", "Database Dump Failed: " + e.getMessage());
            serviceResponse.addError("Database Dump Failed", e.getMessage());
            serviceResponse.setResponseObject(false);
        }
        return serviceResponse;
    }

    /**
     * Uploads the compressed database dump to the S3 Lambda endpoint.
     * 
     * The endpoint expects:
     * - POST to: {S3_BACKUP_ENDPOINT}/upload_dump/{kit_id}
     * - Body: Binary gzip file data
     * - Header: Content-Type: application/octet-stream
     * 
     * @param filePath Path to the .sql.gz file to upload
     * @return true if upload succeeded, false otherwise
     */
    private boolean uploadToS3Endpoint(String filePath) {
        try {
            // Read endpoint dynamically from environment
            String s3Endpoint = System.getenv("S3_BACKUP_ENDPOINT");
            if (s3Endpoint == null || s3Endpoint.isEmpty()) {
                Logger.warn("DbDumpService", "S3_BACKUP_ENDPOINT environment variable not set");
                return false;
            }
            
            // Get kit ID from environment or use default
            String kitId = getKitId();
            String endpoint = s3Endpoint.replaceAll("/$", ""); // Remove trailing slash
            String uploadUrl = endpoint + "/upload_dump/" + kitId;
            
            Logger.info("DbDumpService", "Uploading to: " + uploadUrl);
            
            // Read the gzip file
            Path dumpPath = Paths.get(filePath);
            if (!Files.exists(dumpPath)) {
                Logger.warn("DbDumpService.uploadToS3Endpoint", "File not found: " + filePath);
                return false;
            }
            
            byte[] fileBytes = Files.readAllBytes(dumpPath);
            Logger.info("DbDumpService.uploadToS3Endpoint", "File size: " + fileBytes.length + " bytes");
            
            // Base64 encode the file
            String base64Payload = Base64.getEncoder().encodeToString(fileBytes);
            Logger.info("DbDumpService.uploadToS3Endpoint", "Base64 payload size: " + base64Payload.length() + " bytes");
            
            // Create HTTP request
            URL url = new URL(uploadUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("User-Agent", "fEMR-DbDumpService/1.0");
            conn.setConnectTimeout(S3_UPLOAD_TIMEOUT);
            conn.setReadTimeout(S3_UPLOAD_TIMEOUT);
            conn.setDoOutput(true);
            
            // Send the binary file data directly
            try (OutputStream os = conn.getOutputStream()) {
                os.write(fileBytes);
                os.flush();
            }
            
            // Check response
            int responseCode = conn.getResponseCode();
            Logger.info("DbDumpService.uploadToS3Endpoint", "Response code: " + responseCode);
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response body for logging
                String responseBody = readResponseBody(conn);
                Logger.info("DbDumpService.uploadToS3Endpoint", "Upload successful. Response: " + responseBody);
                conn.disconnect();
                return true;
            } else {
                String errorBody = readErrorBody(conn);
                Logger.error("DbDumpService.uploadToS3Endpoint", 
                    "Upload failed with code " + responseCode + ". Error: " + errorBody);
                conn.disconnect();
                return false;
            }
            
        } catch (Exception e) {
            Logger.error("DbDumpService.uploadToS3Endpoint", 
                "Exception during upload: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the kit ID from environment variables or generates a default.
     * Priority: KIT_ID env var → TRIP_ID env var → hostname → "default-kit"
     * 
     * @return kit ID string
     */
    private String getKitId() {
        String kitId = System.getenv("KIT_ID");
        if (kitId != null && !kitId.isEmpty()) {
            return kitId;
        }
        
        String tripId = System.getenv("TRIP_ID");
        if (tripId != null && !tripId.isEmpty()) {
            return "trip-" + tripId;
        }
        
        try {
            String hostname = java.net.InetAddress.getLocalHost().getHostName();
            return "kit-" + hostname;
        } catch (Exception e) {
            Logger.warn("DbDumpService.getKitId", "Could not get hostname: " + e.getMessage());
        }
        
        return "default-kit";
    }

    /**
     * Reads successful response body from HTTP connection.
     * 
     * @param conn HttpURLConnection with 2xx response
     * @return response body as string, or empty string on error
     */
    private String readResponseBody(HttpURLConnection conn) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * Reads error response body from HTTP connection.
     * 
     * @param conn HttpURLConnection with error response
     * @return error body as string, or empty string on error
     */
    private String readErrorBody(HttpURLConnection conn) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getErrorStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
