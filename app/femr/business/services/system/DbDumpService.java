package femr.business.services.system;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import femr.business.services.core.IDbDumpService;
import femr.common.dtos.ServiceResponse;
import play.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbDumpService implements IDbDumpService {

    private static final String DEFAULT_BUCKET_NAME = "femr-kit-db-dumps-west";
    private static final String DEFAULT_AWS_REGION = "us-west-2";

    public DbDumpService() {
    }

    /**
     * Creates a plain SQL database dump and uploads it to S3.
     * The local dump is deleted after a successful upload.
     */
    @Override
    public ServiceResponse<Boolean> getAllData() {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        Path dumpPath = Paths.get("/tmp", buildDumpFileName());

        try {
            createDatabaseDump(dumpPath);
            uploadDumpToS3(dumpPath);
            Files.deleteIfExists(dumpPath);
            serviceResponse.setResponseObject(true);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error("DbDumpService", "Database Dump Failed: " + e.getMessage());
            serviceResponse.addError("Database Dump Failed", e.getMessage());
            serviceResponse.setResponseObject(false);
        }

        return serviceResponse;
    }

    private void createDatabaseDump(Path dumpPath) throws IOException, InterruptedException {
        String dbUser = getConfigValue("DB_USER");
        String dbPassword = getConfigValue("DB_PASS");
        String databaseName = getDatabaseName();

        if (dbUser == null || dbUser.isEmpty() || dbPassword == null || dbPassword.isEmpty()) {
            throw new IllegalStateException("DB_USER and DB_PASS must be configured before creating a dump");
        }
        if (databaseName == null || databaseName.isEmpty()) {
            throw new IllegalStateException("Database name could not be resolved from DB_URL or DB_NAME");
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                "mysqldump",
                "--host=db",
            "--skip-ssl",
                String.format("--user=%s", dbUser),
                String.format("--password=%s", dbPassword),
                "--single-transaction",
                "--skip-lock-tables",
                "--databases",
                databaseName);

        File outputFile = dumpPath.toFile();
        processBuilder.redirectOutput(ProcessBuilder.Redirect.to(outputFile));
        processBuilder.redirectErrorStream(false);

        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            String errorOutput = readProcessOutput(process.getErrorStream());
            throw new IOException("mysqldump exited with code " + exitCode + ". Error: " + errorOutput);
        }

        Logger.info("DbDumpService", "Database dump created: " + dumpPath);
    }

    private void uploadDumpToS3(Path dumpPath) throws IOException {
        String bucketName = getConfigValue("S3_BACKUP_BUCKET", DEFAULT_BUCKET_NAME);
        String awsRegion = getConfigValue("AWS_REGION");
        if (awsRegion == null || awsRegion.isEmpty()) {
            awsRegion = getConfigValue("AWS_DEFAULT_REGION", DEFAULT_AWS_REGION);
        }

        if (!Files.exists(dumpPath)) {
            throw new FileNotFoundException("Dump file not found: " + dumpPath);
        }

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(awsRegion)
                .build();

        String objectKey = buildObjectKey(dumpPath.getFileName().toString());
        File dumpFile = dumpPath.toFile();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/sql");
        metadata.setContentLength(dumpFile.length());

        PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, dumpFile);
        request.setMetadata(metadata);

        Logger.info("DbDumpService", "Uploading dump to s3://" + bucketName + "/" + objectKey);
        s3Client.putObject(request);
        s3Client.shutdown();
        Logger.info("DbDumpService", "Successfully uploaded SQL dump to S3 bucket " + bucketName);
    }

    private String buildDumpFileName() {
        return "db_dump_" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + ".sql";
    }

    private String buildObjectKey(String fileName) {
        return sanitizeKeyComponent(getKitId()) + "/backups/" + fileName;
    }

    private String sanitizeKeyComponent(String value) {
        if (value == null || value.isEmpty()) {
            return "default-kit";
        }
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private String getConfigValue(String key) {
        String value = System.getenv(key);
        if (value == null || value.isEmpty()) {
            value = System.getProperty(key);
        }
        return value;
    }

    private String getConfigValue(String key, String defaultValue) {
        String value = getConfigValue(key);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }

    private String getDatabaseName() {
        String databaseName = getConfigValue("DB_NAME");
        if (databaseName != null && !databaseName.isEmpty()) {
            return databaseName;
        }

        String dbUrl = getConfigValue("DB_URL");
        if (dbUrl == null || dbUrl.isEmpty()) {
            return null;
        }

        int schemeEnd = dbUrl.indexOf("//");
        String pathPortion = schemeEnd >= 0 ? dbUrl.substring(schemeEnd + 2) : dbUrl;
        int slashIndex = pathPortion.indexOf('/');
        if (slashIndex < 0) {
            return null;
        }

        String databasePortion = pathPortion.substring(slashIndex + 1);
        int queryIndex = databasePortion.indexOf('?');
        if (queryIndex >= 0) {
            databasePortion = databasePortion.substring(0, queryIndex);
        }

        return databasePortion;
    }

    /**
     * Gets the kit ID from environment variables or generates a default.
     * Priority: KIT_ID env var -> TRIP_ID env var -> hostname -> "default-kit"
     */
    private String getKitId() {
        String kitId = getConfigValue("KIT_ID");
        if (kitId != null && !kitId.isEmpty()) {
            return kitId;
        }

        String tripId = getConfigValue("TRIP_ID");
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

    private String readProcessOutput(InputStream inputStream) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.append(line);
            }
            return output.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
