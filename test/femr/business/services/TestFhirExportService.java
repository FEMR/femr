package femr.business.services;

import femr.business.services.system.FhirExportService;
import femr.data.daos.core.IEncounterRepository;
import femr.data.daos.core.IPatientEncounterVitalRepository;
import femr.data.daos.core.IPatientRepository;
import mock.femr.data.daos.MockEncounterRepository;
import mock.femr.data.daos.MockPatientEncounterVitalRepository;
import femr.data.daos.core.IPatientRepository;
import femr.data.daos.core.IPrescriptionRepository;
import femr.data.daos.system.EncounterRepository;
import mock.femr.data.daos.MockEncounterRepository;
import mock.femr.data.daos.MockPatientRepository;
import mock.femr.data.daos.MockPrescriptionRepository;
import mock.femr.data.models.MockPatient;
import mock.femr.data.models.MockPatientEncounterVital;
import org.junit.Test;
import org.json.*;

import static org.junit.Assert.assertEquals;


public class TestFhirExportService {


    @Test
    public void smokeTestBlankDocument() {

        IPatientRepository patientRepository = new MockPatientRepository();
        IEncounterRepository encounterRepository = new MockEncounterRepository();
        IPatientEncounterVitalRepository patientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        FhirExportService export = new FhirExportService(patientRepository,encounterRepository, patientEncounterVitalRepository, "5BE2ED");
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        FhirExportService export = new FhirExportService(patientRepository, encounterRepository, prescriptionRepository);

        System.out.println(export.exportPatient(0));
    }

    @Test
    public void nonStandardSex() {
        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, mockPatientEncounterVitalRepository, prescriptionRepository, "5BE2ED");

      IEncounterRepository encounterRepository = new MockEncounterRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");


        FhirExportService export = new FhirExportService(patientRepository, encounterRepository, prescriptionRepository);

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject patientResource = getSingleResourceFromBundle(bundle, "Patient");

        assertEquals("other", patientResource.getString("gender"));
    }

    private JSONObject getSingleResourceFromBundle(JSONObject jsonBundle, String wantedResourceType) {
        for (Object jsonObject: jsonBundle.getJSONArray("entry")) {
            JSONObject entry = (JSONObject) jsonObject;

            JSONObject resource = entry.getJSONObject("resource");
            String resourceType = resource.getString("resourceType");

            if (resourceType.equals(wantedResourceType)) {
                return resource;
            }

        }

        throw new RuntimeException(String.format("Unable to find resource type \"%s\" in jsonBundle: \n%s", wantedResourceType, jsonBundle.toString(2)));
    }
}
