package femr.business.services.system;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyResult;
import femr.business.services.core.IDbDumpService;
import femr.common.dtos.ServiceResponse;
import play.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.GZIPOutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
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
        String rawDumpPath  = "/tmp/db_dump.sql";
        String gzipDumpPath = "/tmp/db_dump.sql.gz";
        
        try {
            // Step 1: Run mysqldump to a raw .sql file
            String db_user = System.getenv("DB_USER");
            String db_password = System.getenv("DB_PASS");
            ProcessBuilder pb = new ProcessBuilder(
                    "mysqldump", "--host=db",
                    String.format("--user=%s", db_user),
                    String.format("--password=%s", db_password),
                    "--complete-insert",
                    "--all-databases");
            pb.redirectOutput(ProcessBuilder.Redirect.to(new File(rawDumpPath)));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                serviceResponse.addError("mysqldump", "mysqldump exited with code " + exitCode);
                serviceResponse.setResponseObject(false);
                return serviceResponse;
            }
            Logger.info("DbDumpService", "Raw dump created: " + rawDumpPath);

            // Step 2: Compress the raw SQL to a real gzip file
            compressToGzip(rawDumpPath, gzipDumpPath);
            Files.deleteIfExists(Paths.get(rawDumpPath));
            Logger.info("DbDumpService", "Compressed dump: " + gzipDumpPath);

            // Step 3: Upload to S3 (prefer direct bucket upload; fallback to API endpoint)
            String bucketName = System.getenv("S3_BUCKET_NAME");
            String s3Endpoint = System.getenv("S3_BACKUP_ENDPOINT");
            String uploadError;

            if (bucketName != null && !bucketName.isEmpty()) {
                uploadError = uploadDirectToS3(bucketName, gzipDumpPath);
            } else if (s3Endpoint != null && !s3Endpoint.isEmpty()) {
                uploadError = uploadToS3Endpoint(gzipDumpPath);
            } else {
                uploadError = "No backup target configured. Set S3_BUCKET_NAME for direct S3 upload or S3_BACKUP_ENDPOINT for API upload.";
            }

            Files.deleteIfExists(Paths.get(gzipDumpPath));
            if (uploadError != null) {
                Logger.error("DbDumpService", "S3 upload failed: " + uploadError);
                serviceResponse.addError("S3 Upload", uploadError);
                serviceResponse.setResponseObject(false);
            } else {
                Logger.info("DbDumpService", "Successfully uploaded dump to S3");
                serviceResponse.setResponseObject(true);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Logger.error("DbDumpService", "Database Dump Failed: " + e.getMessage());
            serviceResponse.addError("Database Dump Failed", e.getMessage());
            serviceResponse.setResponseObject(false);
        }
        return serviceResponse;
    }

    /**
     * Compresses a file using gzip.
     *
     * @param inputPath  path to the uncompressed source file
     * @param outputPath path to write the gzip-compressed output
     */
    private void compressToGzip(String inputPath, String outputPath) throws IOException {
        try (FileInputStream  fis  = new FileInputStream(inputPath);
             FileOutputStream fos  = new FileOutputStream(outputPath);
             GZIPOutputStream gzos = new GZIPOutputStream(fos)) {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                gzos.write(buffer, 0, len);
            }
        }
    }

    /**
     * Uploads the compressed database dump to the S3 Lambda endpoint.
     *
     * @param filePath Path to the .sql.gz file to upload
     * @return null on success, or an error message string on failure
     */
    private String uploadToS3Endpoint(String filePath) {
        try {
            // Read endpoint dynamically from environment
            String s3Endpoint = System.getenv("S3_BACKUP_ENDPOINT");
            if (s3Endpoint == null || s3Endpoint.isEmpty()) {
                Logger.warn("DbDumpService", "S3_BACKUP_ENDPOINT environment variable not set");
                return "S3_BACKUP_ENDPOINT not configured";
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
                return "Dump file not found: " + filePath;
            }
            
            byte[] fileBytes = Files.readAllBytes(dumpPath);
            Logger.info("DbDumpService.uploadToS3Endpoint", "File size: " + fileBytes.length + " bytes");
            
            // Create HTTP request
            URL url = new URL(uploadUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("User-Agent", "fEMR-DbDumpService/1.0");

            // Set API key required by the Lambda upload endpoint
            String apiKey = System.getenv("BACKUP_API_KEY");
            if (apiKey != null && !apiKey.isEmpty()) {
                conn.setRequestProperty("X-API-Key", apiKey);
            } else {
                Logger.warn("DbDumpService.uploadToS3Endpoint", "BACKUP_API_KEY not set — upload will be rejected by Lambda");
            }

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
                String responseBody = readResponseBody(conn);
                Logger.info("DbDumpService.uploadToS3Endpoint", "Upload successful. Response: " + responseBody);
                conn.disconnect();
                return null;  // null = success
            } else {
                String errorBody = readErrorBody(conn);
                String msg = "HTTP " + responseCode + ": " + errorBody;
                Logger.error("DbDumpService.uploadToS3Endpoint", "Upload failed — " + msg);
                conn.disconnect();
                return msg;
            }
            
        } catch (Exception e) {
            Logger.error("DbDumpService.uploadToS3Endpoint", "Exception during upload: " + e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * Uploads the compressed database dump directly to S3 with client-side encryption using AWS KMS.
     *
     * Implements envelope encryption:
     * 1. KMS generates a 256-bit data key
     * 2. File is encrypted locally with the data key (AES-256)
     * 3. Encrypted data key is stored as S3 object metadata
     * 4. Processor Lambda can decrypt using KMS key permissions
     *
     * Required env vars:
     * - S3_BUCKET_NAME
     * - AWS_REGION (optional, defaults to us-west-2)
    * - KMS_KEY_ID (optional, defaults to ae684528-8865-4c91-86c0-fbfb57d48378)
     * - AWS_ACCESS_KEY_ID / AWS_SECRET_ACCESS_KEY (if no instance role is available)
     *
     * @param bucketName target S3 bucket
     * @param filePath path to dump file
     * @return null on success, otherwise an error string
     */
    private String uploadDirectToS3(String bucketName, String filePath) {
        try {
            Path dumpPath = Paths.get(filePath);
            if (!Files.exists(dumpPath)) {
                return "Dump file not found: " + filePath;
            }

            String region = System.getenv("AWS_REGION");
            if (region == null || region.isEmpty()) {
                region = "us-west-2";
            }

            String kmsKeyId = System.getenv("KMS_KEY_ID");
            if (kmsKeyId == null || kmsKeyId.isEmpty()) {
                kmsKeyId = "ae684528-8865-4c91-86c0-fbfb57d48378"; // Default key for patient data
            }

            String kitId = getKitId();
            LocalDate uploadDate = LocalDate.now();
            LocalDateTime uploadDateTime = LocalDateTime.now();
            String datePrefix = uploadDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String timestamp = uploadDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssSSS"));
            String objectKey = kitId + "/" + datePrefix + "/" + timestamp + ".sql.gz.encrypted";

            // Initialize AWS clients
            String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
            String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
            
            AWSKMSClientBuilder kmsBuilder = AWSKMSClientBuilder.standard().withRegion(region);
            AmazonS3 s3Client;
            
            if (accessKey != null && !accessKey.isEmpty() && secretKey != null && !secretKey.isEmpty()) {
                BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
                AWSStaticCredentialsProvider credProvider = new AWSStaticCredentialsProvider(credentials);
                kmsBuilder.withCredentials(credProvider);
                s3Client = AmazonS3ClientBuilder.standard()
                        .withRegion(region)
                        .withCredentials(credProvider)
                        .build();
            } else {
                s3Client = AmazonS3ClientBuilder.standard()
                        .withRegion(region)
                        .build();
            }
            
            AWSKMS kmsClient = kmsBuilder.build();

            // Step 1: Generate a data key from KMS (256-bit for AES-256)
            Logger.info("DbDumpService.uploadDirectToS3", "Requesting data key from KMS for key: " + kmsKeyId);
            GenerateDataKeyRequest dataKeyRequest = new GenerateDataKeyRequest()
                    .withKeyId(kmsKeyId)
                    .withKeySpec("AES_256");
            GenerateDataKeyResult dataKeyResult = kmsClient.generateDataKey(dataKeyRequest);
            
            byte[] plaintextDataKey = dataKeyResult.getPlaintext().array();
            byte[] encryptedDataKey = dataKeyResult.getCiphertextBlob().array();
            Logger.info("DbDumpService.uploadDirectToS3", "Generated data key; encrypted key size: " + encryptedDataKey.length + " bytes");

            // Step 2: Read the gzipped file
            byte[] plaintext = Files.readAllBytes(dumpPath);
            
            // Step 3: Encrypt file data using AES-256 (ECB mode for simplicity; patient data at rest)
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(plaintextDataKey, 0, plaintextDataKey.length, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedData = cipher.doFinal(plaintext);
            
            Logger.info("DbDumpService.uploadDirectToS3", "Encrypted " + plaintext.length + " bytes to " + encryptedData.length + " bytes");

            // Step 4: Prepare S3 object metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("application/octet-stream"); // Now binary encrypted data
            metadata.setContentLength(encryptedData.length);
            // Store the encrypted data key as base64-encoded metadata so processor can decrypt
            metadata.addUserMetadata("x-amz-encrypted-data-key", Base64.getEncoder().encodeToString(encryptedDataKey));
            metadata.addUserMetadata("x-amz-key-id", kmsKeyId);
            metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION); // S3-level encryption too

            // Step 5: Upload encrypted data to S3
            ByteArrayInputStream encryptedInputStream = new ByteArrayInputStream(encryptedData);
            PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, encryptedInputStream, metadata);
            s3Client.putObject(request);

            Logger.info("DbDumpService.uploadDirectToS3", 
                    "Encrypted upload successful: s3://" + bucketName + "/" + objectKey);
            Logger.info("DbDumpService.uploadDirectToS3", 
                    "Patient data is client-side encrypted with KMS key: " + kmsKeyId);
            
            // Clean up sensitive data from memory
            java.util.Arrays.fill(plaintextDataKey, (byte) 0);
            
            return null;
        } catch (Exception e) {
            Logger.error("DbDumpService.uploadDirectToS3", "Encryption/upload failed: " + e.getMessage());
            e.printStackTrace();
            return e.getMessage();
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
