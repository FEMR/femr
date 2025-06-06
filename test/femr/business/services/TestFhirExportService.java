package femr.business.services;

import femr.business.services.system.FhirExportService;

import femr.data.daos.core.*;
import femr.data.models.core.*;
import femr.data.models.mysql.TabField;
import mock.femr.data.daos.*;

import mock.femr.data.models.*;
import org.junit.Test;
import org.json.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertTrue;

public class TestFhirExportService {

    @Test
    public void photo_Success() {
        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        MockPhotoRepository photoRepository = new MockPhotoRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");
        patientRepository.mockPatient.setId(1);

        // Mock Photo data
        List<MockPhoto> mockPhotos = new ArrayList<>();
        MockPhoto mockPhoto = new MockPhoto();
        mockPhoto.setId(1);
        mockPhoto.setContentType("image/jpeg");
        mockPhoto.setPhotoData(new byte[]{1, 2, 3});
        mockPhotos.add(mockPhoto);
        photoRepository.setMockPhotos(mockPhotos);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject documentReferenceResource = getSingleResourceFromBundle(bundle, "DocumentReference");

        assertEquals("5BE2ED_1", documentReferenceResource.getString("id"));

        JSONObject attachment = documentReferenceResource.getJSONArray("content").getJSONObject(0).getJSONObject("attachment");
        assertEquals("image/jpeg", attachment.getString("contentType"));
        assertEquals(Base64.getEncoder().encodeToString(new byte[]{1, 2, 3}), attachment.getString("data"));
    }

    @Test
    public void smokeTestBlankDocument() {

        //IPhotoRepository photoRepository = new MockPhotoRepository();

        IPatientRepository patientRepository = new MockPatientRepository();
        IEncounterRepository encounterRepository = new MockEncounterRepository();
        IPatientEncounterVitalRepository patientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        FhirExportService export = new FhirExportService(patientRepository, encounterRepository, prescriptionRepository, patientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        System.out.println(export.exportPatient(0));
    }

    @Test
    public void medicationRequest_success() {
        MockPatientRepository patientRepository = new MockPatientRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        MockPrescriptionRepository mockPrescriptionRepository = new MockPrescriptionRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();

        // Setup mock patient
        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("male");
        patientRepository.mockPatient.setId(1);

        // Setup mock patient encounter
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        // Create prescription with medicationAdministrationId = 2
        IPatientPrescription prescription = mockPrescriptionRepository.createPrescription(1, 1, 2, 1, 1);
        System.out.println("Processing prescription named: " + prescription.getMedication());

        // Adding vitals setup
        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();

        MockPatientEncounterVital respirationRate = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("respirationRate");
        respirationRate.setVital(vitalType);
        respirationRate.setVitalValue(16.0f);
        respirationRate.setId(1);

        vitals.add(respirationRate);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, mockPrescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        // Verify the MedicationRequest resource in the bundle
        JSONObject medicationRequestResource = getSingleResourceFromBundle(bundle, "MedicationRequest");

        assertNotNull(medicationRequestResource);
        assertEquals("Medication-1", medicationRequestResource
                .getJSONObject("medication")
                .getJSONObject("reference")
                .getString("reference"));
        assertEquals("Patient/1", medicationRequestResource.getJSONObject("subject").getString("reference"));
    }

    @Test
    public void medicationDispense_success() {
        MockPatientRepository patientRepository = new MockPatientRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        MockPrescriptionRepository mockPrescriptionRepository = new MockPrescriptionRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("male");
        patientRepository.mockPatient.setId(1);

        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        // Create prescription with medicationAdministrationId = 2
        IPatientPrescription prescription = mockPrescriptionRepository.createPrescription(1, 1, 2, 1, 1);
        assertNotNull(prescription);
        assertNotNull(prescription.getMedication());
        assertNotNull(prescription.getConceptPrescriptionAdministration());


        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();

        MockPatientEncounterVital respirationRate = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("respirationRate");
        respirationRate.setVital(vitalType);
        respirationRate.setVitalValue(16.0f);
        respirationRate.setId(1);

        vitals.add(respirationRate);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, mockPrescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject medicationDispenseResource = getSingleResourceFromBundle(bundle, "MedicationDispense");

        assertNotNull(medicationDispenseResource);
        assertEquals("Medication-1",
                medicationDispenseResource
                        .getJSONObject("medication")
                        .getJSONObject("reference")
                        .getString("reference"));
        assertEquals("Patient/1",
                medicationDispenseResource.getJSONObject("subject").getString("reference"));
    }
  
    @Test
    public void hpi_fields(){
        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        MockPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("male");
        patientRepository.mockPatient.setId(1);


        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        HashMap<Integer, List<? extends IPatientEncounterTabField>> encounterTabFields = new HashMap<>();

        IPatientEncounterTabField patientEncounterTabField = new MockPatientEncounterTabField();
        ITabField tabField = new TabField();
        tabField.setName("onset");
        patientEncounterTabField.setTabField(tabField);
        patientEncounterTabField.setTabFieldValue("The disease started at roughly 8 months\nBut maybe not");

        IPatientEncounterTabField patientEncounterTabField2 = new MockPatientEncounterTabField();
        ITabField tabField2 = new TabField();
        tabField2.setName("narrative");
        patientEncounterTabField2.setTabField(tabField2);
        patientEncounterTabField2.setTabFieldValue("Something else");

        ArrayList<IPatientEncounterTabField> tabFields = new ArrayList<>();
        tabFields.add(patientEncounterTabField);
        tabFields.add(patientEncounterTabField2);

        encounterTabFields.put(1,tabFields);

        tabFieldRepository.setEncounterTabFields(encounterTabFields);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository,"5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "DocumentReference");

        // Clinical Information
        JSONObject coding = (JSONObject) observationResource.getJSONObject("type").getJSONArray("coding").get(0);
        assertEquals("55752-0", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        JSONObject attachment = ((JSONObject) observationResource.getJSONArray("content").get(0)).getJSONObject("attachment");

        assertEquals("text/plain", attachment.getString("contentType"));
        byte[] bytes = Base64.getDecoder().decode(attachment.getString("data"));
        String decodedString = new String(bytes, StandardCharsets.UTF_8);

        assertEquals("onset__:The disease started at roughly 8 months\\nBut maybe not\nnarrative__:Something else", decodedString);
    }

    @Test
    public void glucose_success(){
        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("male");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();

        MockPatientEncounterVital wksPregnant = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("glucose");
        wksPregnant.setVital(vitalType);
        wksPregnant.setVitalValue(80.5f);
        wksPregnant.setId(1);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(wksPregnant);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        //  Gestational age (Weeks Pregnant)
        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("2339-0", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertEquals(80.5f, observationResource.getJSONObject("valueQuantity").getFloat("value"), 0.01);
        assertEquals("http://unitsofmeasure.org", observationResource.getJSONObject("valueQuantity").getString("system"));
        assertEquals("mg/dL", observationResource.getJSONObject("valueQuantity").getString("code"));
        assertEquals("mg/dL", observationResource.getJSONObject("valueQuantity").getString("unit"));
    }

    @Test
    public void weeksPregnant_success(){
        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("female");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();

        MockPatientEncounterVital wksPregnant = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("weeksPregnant");
        wksPregnant.setVital(vitalType);
        wksPregnant.setVitalValue(7f);
        wksPregnant.setId(1);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(wksPregnant);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        //  Gestational age (Weeks Pregnant)
        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("18185-9", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertEquals(7f, observationResource.getJSONObject("valueQuantity").getFloat("value"), 0.01);
        assertEquals("http://unitsofmeasure.org", observationResource.getJSONObject("valueQuantity").getString("system"));
        assertEquals("wk", observationResource.getJSONObject("valueQuantity").getString("code"));
        assertEquals("week", observationResource.getJSONObject("valueQuantity").getString("unit"));
    }

    @Test
    public void bodyHeight_success(){
        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("male");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();

        MockPatientEncounterVital heightInches = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("heightInches");
        heightInches.setVital(vitalType);
        heightInches.setVitalValue(7f);
        heightInches.setId(1);

        MockPatientEncounterVital heightFeet = new MockPatientEncounterVital();
        vitalType = new MockVital();
        vitalType.setName("heightFeet");
        heightFeet.setVital(vitalType);
        heightFeet.setVitalValue(5f);
        heightFeet.setId(5);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(heightInches);
        vitals.add(heightFeet);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        // We want to make sure that the id is from the FEET part and not from inches
        assertEquals("5BE2ED_5", observationResource.getString("id"));

        // Body height
        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("8302-2", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertEquals(67f, observationResource.getJSONObject("valueQuantity").getFloat("value"), 0.01);
        assertEquals("http://unitsofmeasure.org", observationResource.getJSONObject("valueQuantity").getString("system"));
        assertEquals("[in_i]", observationResource.getJSONObject("valueQuantity").getString("code"));
        assertEquals("in", observationResource.getJSONObject("valueQuantity").getString("unit"));
    }


    @Test
    public void oxygenSaturation_success(){
        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("male");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital o2Sat = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("oxygenSaturation");
        o2Sat.setVital(vitalType);
        o2Sat.setVitalValue(99.5f);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(o2Sat);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        // Oxygen saturation in Arterial blood
        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("2708-6", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        // Oxygen saturation in Arterial blood by Pulse oximetry
        coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(1);
        assertEquals("59408-5", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));


        assertEquals(99.5, observationResource.getJSONObject("valueQuantity").getFloat("value"), 0.01);
        assertEquals("http://unitsofmeasure.org", observationResource.getJSONObject("valueQuantity").getString("system"));
        assertEquals("%", observationResource.getJSONObject("valueQuantity").getString("code"));
        assertEquals("%O2", observationResource.getJSONObject("valueQuantity").getString("unit"));
    }

    @Test
    public void heartRate_success(){

        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("male");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital heartRate = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("heartRate");
        heartRate.setVital(vitalType);
        heartRate.setVitalValue(65.3f);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(heartRate);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("8867-4", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertEquals(65.3, observationResource.getJSONObject("valueQuantity").getFloat("value"), 0.01);
        assertEquals("http://unitsofmeasure.org", observationResource.getJSONObject("valueQuantity").getString("system"));
        assertEquals("/min", observationResource.getJSONObject("valueQuantity").getString("code"));
    }

    @Test
    public void bloodPressureDiastolic_Success(){

        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital bloodPressureDiastolic = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("bloodPressureDiastolic");
        bloodPressureDiastolic.setVital(vitalType);
        bloodPressureDiastolic.setVitalValue(80.0f);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(bloodPressureDiastolic);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("8462-4", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertEquals(80.0, observationResource.getJSONObject("valueQuantity").getFloat("value"), 0.01);
        assertEquals("http://unitsofmeasure.org", observationResource.getJSONObject("valueQuantity").getString("system"));
        assertEquals("mm[Hg]", observationResource.getJSONObject("valueQuantity").getString("code"));
    }

    @Test
    public void bloodPressureSystolic_Success(){

        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital bloodPressureSystolic = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("bloodPressureSystolic");
        bloodPressureSystolic.setVital(vitalType);
        bloodPressureSystolic.setVitalValue(120.0f);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(bloodPressureSystolic);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("8480-6", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertEquals(120.0, observationResource.getJSONObject("valueQuantity").getFloat("value"), 0.01);
        assertEquals("http://unitsofmeasure.org", observationResource.getJSONObject("valueQuantity").getString("system"));
        assertEquals("mm[Hg]", observationResource.getJSONObject("valueQuantity").getString("code"));
    }

    @Test
    public void bodyWeight_Success(){

        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital weight = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("weight");
        weight.setVital(vitalType);
        weight.setVitalValue(150.0f);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(weight);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("29463-7", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertEquals(150.0, observationResource.getJSONObject("valueQuantity").getFloat("value"), 0.01);
        assertEquals("http://unitsofmeasure.org", observationResource.getJSONObject("valueQuantity").getString("system"));
        assertEquals("[lb_av]", observationResource.getJSONObject("valueQuantity").getString("code"));
    }

    @Test
    public void bodyTemperature_Success(){

        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital temperature = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("temperature");
        temperature.setVital(vitalType);
        temperature.setVitalValue(36.0f);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(temperature);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("8310-5", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertEquals(36.0, observationResource.getJSONObject("valueQuantity").getFloat("value"), 0.01);
        assertEquals("http://unitsofmeasure.org", observationResource.getJSONObject("valueQuantity").getString("system"));
        assertEquals("Cel", observationResource.getJSONObject("valueQuantity").getString("code"));

    }

    @Test
    public void diabetes_History_Success(){

        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital diabetes = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("diabetic");
        diabetes.setVital(vitalType);
        diabetes.setVitalValue(1);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(diabetes);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject conditionResource = getSingleResourceFromBundle(bundle, "Condition");

        JSONObject coding = (JSONObject) conditionResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("45636-8", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

    }
    @Test
    public void hypertension_History_Success(){

        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital hypertension = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("hypertension");
        hypertension.setVital(vitalType);
        hypertension.setVitalValue(1);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(hypertension);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject conditionResource = getSingleResourceFromBundle(bundle, "Condition");

        JSONObject coding = (JSONObject) conditionResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("45643-4", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

    }

    @Test
    public void alcohol_History_Success(){

        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital alcohol_history = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("alcohol");
        alcohol_history.setVital(vitalType);
        alcohol_history.setVitalValue(1);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(alcohol_history);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("74013-4", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertTrue(observationResource.getBoolean("valueBoolean"));

    }

    @Test
    public void tobacco_History_Success(){

        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital tobacco_history = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("smoker");
        tobacco_history.setVital(vitalType);
        tobacco_history.setVitalValue(1);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(tobacco_history);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("72166-2", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertTrue(observationResource.getBoolean("valueBoolean"));

    }

    @Test
    public void high_Cholesterol_Success(){

        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");
        patientRepository.mockPatient.setId(1);

        HashMap<Integer, List<? extends IPatientEncounterVital>> encounterVitals = new HashMap<>();
        ArrayList<IPatientEncounterVital> vitals = new ArrayList<>();
        MockPatientEncounterVital cholesterol = new MockPatientEncounterVital();
        IVital vitalType = new MockVital();
        vitalType.setName("alcohol");
        cholesterol.setVital(vitalType);
        cholesterol.setVitalValue(1);

        ArrayList<IPatientEncounter> encounters = new ArrayList<>();
        MockPatientEncounter patientEncounter = new MockPatientEncounter();
        patientEncounter.setPatient(patientRepository.mockPatient);
        patientEncounter.setId(1);
        encounters.add(patientEncounter);
        mockEncounterRepository.setPatientEncounters(encounters);

        vitals.add(cholesterol);
        encounterVitals.put(1, vitals);
        mockPatientEncounterVitalRepository.setEncounterVitals(encounterVitals);

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

        String jsonString = export.exportPatient(1);

        JSONObject bundle = new JSONObject(jsonString);

        JSONObject observationResource = getSingleResourceFromBundle(bundle, "Observation");

        JSONObject coding = (JSONObject) observationResource.getJSONObject("code").getJSONArray("coding").get(0);
        assertEquals("74013-4", coding.getString("code"));
        assertEquals("http://loinc.org", coding.getString("system"));

        assertTrue(observationResource.getBoolean("valueBoolean"));

    }


    @Test
    public void nonStandardSex() {
        MockPatientRepository patientRepository = new MockPatientRepository();
        MockPatientEncounterVitalRepository mockPatientEncounterVitalRepository = new MockPatientEncounterVitalRepository();
        MockEncounterRepository mockEncounterRepository = new MockEncounterRepository();
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

        patientRepository.mockPatient = new MockPatient();
        patientRepository.mockPatient.setSex("SOMETHING NOT M or F");

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");
      
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
        IPatientEncounterTabFieldRepository tabFieldRepository = new MockPatientEncounterTabFieldRepository();
        IPrescriptionRepository prescriptionRepository = new MockPrescriptionRepository();
        IPhotoRepository photoRepository = new MockPhotoRepository();

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

        FhirExportService export = new FhirExportService(patientRepository, mockEncounterRepository, prescriptionRepository, mockPatientEncounterVitalRepository, tabFieldRepository, photoRepository, "5BE2ED");

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
