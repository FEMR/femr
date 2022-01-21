package mock.femr.data.daos;

import femr.data.daos.core.IPrescriptionRepository;
import femr.data.models.core.*;
import mock.femr.data.models.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MockPrescriptionRepository implements IPrescriptionRepository {


    public boolean createPrescriptionWasCalled = false;
    public boolean retrieveAllPrescriptionsByMedicationId = false;
    public IPatientPrescription mockPrescription;
    public IMedication mockMedication;


    public MockPrescriptionRepository() {

        this.mockPrescription = new MockPrescription();
        this.mockMedication = new MockMedication();
    }

    @Override
    public IPatientPrescription createPrescription(Integer amount, int medicationId, Integer medicationAdministrationId, int userId, int encounterId) {

        createPrescriptionWasCalled = true;

        MockPrescription mockPrescription = new MockPrescription();
        MockUser mockPhysician = new MockUser();
        MockMedication mockMedication = new MockMedication();
        MockPatientEncounter mockPatientEncounter = new MockPatientEncounter();
        MockPrescriptionAdministration mockPrescriptionAdministration = new MockPrescriptionAdministration();

        mockPhysician.setId(userId);
        mockMedication.setId(medicationId);
        mockPatientEncounter.setId(encounterId);
        mockPrescriptionAdministration.setId(medicationAdministrationId);

        mockPrescription.setAmount(amount);
        mockPrescription.setDateTaken(DateTime.now());
        mockPrescription.setPatientEncounter(mockPatientEncounter);
        mockPrescription.setMedication(mockMedication);
        mockPrescription.setConceptPrescriptionAdministration(mockPrescriptionAdministration);
        mockPrescription.setPhysician(mockPhysician);
        mockPrescription.setDateDispensed(null);
        mockPrescription.setCounseled(false);

        return mockPrescription;
    }

    @Override
    public List<? extends IPatientPrescriptionReplacement> createPrescriptionReplacements(List<? extends IPatientPrescriptionReplacement> prescriptionReplacements) {
        return null;
    }

    @Override
    public List<? extends IConceptPrescriptionAdministration> retrieveAllConceptPrescriptionAdministrations() {
        return null;
    }

    @Override
    public List<? extends IPatientPrescription> retrieveAllDispensedPrescriptionsByEncounterId(int encounterId) {
        return null;
    }

    @Override
    public IPatientPrescriptionReplacementReason retrieveReplacementReasonByName(String name) {
        return null;
    }

    @Override
    public List<? extends IPatientPrescription> retrieveUnreplacedPrescriptionsByEncounterId(int encounterId) {
        return null;
    }

    @Override
    public IPatientPrescription retrievePrescriptionById(int prescriptionId) {
        return null;
    }

    @Override
    public IPatientPrescription updatePrescription(IPatientPrescription patientPrescription) {
        return null;
    }

    @Override
    public List<? extends IPatientPrescription> retrieveAllPrescriptionsByMedicationId(int med_id, DateTime startDT, DateTime endDT) {
        retrieveAllPrescriptionsByMedicationId = true;
        List<IPatientPrescription> patientPrescriptions = new ArrayList<>();
        List<IPatientPrescription> patientPrescriptions2 = new ArrayList<>();
        MockPrescription mockPrescription1 = new MockPrescription();
        mockPrescription1.setAmount(100);
        mockPrescription1.setMedication(mockMedication);
        mockPrescription1.setDateTaken(DateTime.now().minusDays(1));
        patientPrescriptions.add(mockPrescription1);
        MockPrescription mockPrescription2 = new MockPrescription();
        mockPrescription2.setAmount(70);
        mockPrescription2.setMedication(mockMedication);
        mockPrescription2.setDateTaken(DateTime.now().minusDays(2));
        patientPrescriptions.add(mockPrescription2);
        MockPrescription mockPrescription3 = new MockPrescription();
        mockPrescription3.setAmount(60);
        mockPrescription3.setMedication(mockMedication);
        mockPrescription3.setDateTaken(DateTime.now().minusDays(3));
        patientPrescriptions.add(mockPrescription3);
        MockPrescription mockPrescription4 = new MockPrescription();
        mockPrescription4.setAmount(30);
        mockPrescription4.setMedication(mockMedication);
        mockPrescription4.setDateTaken(DateTime.now().minusDays(4));
        patientPrescriptions.add(mockPrescription4);
        MockPrescription mockPrescription5 = new MockPrescription();
        mockPrescription5.setAmount(10);
        mockPrescription5.setMedication(mockMedication);
        mockPrescription5.setDateTaken(DateTime.now().minusDays(5));
        patientPrescriptions.add(mockPrescription5);
        for (IPatientPrescription p : patientPrescriptions){
            if(p.getDateTaken().getMillis() > startDT.getMillis() && p.getDateTaken().getMillis() < endDT.getMillis()){
                patientPrescriptions2.add(p);
            }
        }
        return patientPrescriptions2;
    }
}
