package femr.business.services.system;

import femr.business.services.core.IDbDumpService;
import femr.common.dtos.ServiceResponse;


import java.io.*;


public class DbDumpService implements IDbDumpService {

    public DbDumpService(){}

    @Override
    public ServiceResponse<Boolean> getAllData() {

        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        try {
            String db_user = System.getenv("DB_USER");
            String db_password = System.getenv("DB_PASS");
            ProcessBuilder pb = new ProcessBuilder
                    ("mysqldump", "--host=db", String.format("--user=%s", db_user), String.format("--password=%s", db_password), "--all-databases");
            File outputFile = new File("db_dump.sql.gz");
            pb.redirectOutput(ProcessBuilder.Redirect.to(outputFile));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            serviceResponse.setResponseObject(true);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            serviceResponse.addError("Database Dump Failed", e.getMessage());
            serviceResponse.setResponseObject(false);
        }
        return serviceResponse;

    }
}
