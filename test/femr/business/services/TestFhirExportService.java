package femr.business.services;

import femr.business.services.system.FhirExportService;
import femr.data.daos.core.IPatientRepository;
import mock.femr.data.daos.MockPatientRepository;
import org.junit.Test;

public class TestFhirExportService {


    @Test
    public void smokeTestBlankDocument() {
        IPatientRepository patientRepository = new MockPatientRepository();
        FhirExportService export = new FhirExportService(patientRepository);

        System.out.println(export.exportPatient(1));

    }
}
