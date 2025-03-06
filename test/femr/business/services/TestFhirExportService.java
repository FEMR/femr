package femr.business.services;

import femr.business.services.system.FhirExportService;
import femr.data.daos.core.IEncounterRepository;
import femr.data.daos.core.IPatientEncounterVitalRepository;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.IPatientEncounter;
import femr.data.models.core.IPatientEncounterVital;
import femr.data.models.core.IVital;
import mock.femr.data.daos.MockEncounterRepository;
import mock.femr.data.daos.MockPatientEncounterVitalRepository;
import femr.data.daos.core.IPrescriptionRepository;
import mock.femr.data.daos.MockPatientRepository;
import mock.femr.data.daos.MockPrescriptionRepository;
import mock.femr.data.models.MockPatient;
import mock.femr.data.models.MockPatientEncounter;
import mock.femr.data.models.MockPatientEncounterVital;
import mock.femr.data.models.MockVital;
import org.junit.Test;
import org.json.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TestFhirExportService {


    @Test
    public void smokeTestBlankDocument() {

        IPatientRepository patientRepository = new MockPatientRepository();
        IEncounterRepository encounterRepository = new MockEncounterRepository();
        IPatientEncounterVitalRepository patientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        FhirExportService export = new FhirExportService(patientRepository, encounterRepository, prescriptionRepository, patientEncounterVitalRepository, "5BE2ED");

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

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject patientResource = getSingleResourceFromBundle(bundle, "Patient");

        assertEquals("other", patientResource.getString("gender"));
    }

    @Test
    public void respiratoryRate_success() {
        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital respiratoryRate = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("respiratoryRate");
        respiratoryRate.setVital(vitalType);
        respiratoryRate.setVitalValue(20.5f);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(respiratoryRate);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("9279-1", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertEquals(20.5, observationResource.getJSONObject("valueQuantity").getFloat("value"), 0.01);
        assertEquals("http://unitsofmeasure.org", observationResource.getJSONObject("valueQuantity").getString("system"));
        assertEquals("/min", observationResource.getJSONObject("valueQuantity").getString("code"));

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
