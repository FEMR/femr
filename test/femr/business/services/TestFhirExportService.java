package femr.business.services;

import femr.business.services.system.FhirExportService;
import femr.data.daos.core.IPatientRepository;
import mock.femr.data.daos.MockPatientRepository;
import mock.femr.data.models.MockPatient;
import org.junit.Test;
import org.json.*;

import static org.junit.Assert.assertEquals;


public class TestFhirExportService {


    @Test
    public void smokeTestBlankDocument() {
        IPatientRepository patientRepository = new MockPatientRepository();
        FhirExportService export = new FhirExportService(patientRepository);

        System.out.println(export.exportPatient(1));
    }

    @Test
    public void nonStandardSex() {
        MockPatientRepository patientRepository = new MockPatientRepository();
        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");


        FhirExportService export = new FhirExportService(patientRepository);

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
