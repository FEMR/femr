package mock.femr.data.daos;

import femr.data.daos.core.IPrescriptionRepository;
import femr.data.models.core.*;
import mock.femr.data.models.*;
import org.joda.time.DateTime;

import java.util.List;

public class MockPrescriptionRepository implements IPrescriptionRepository {


    public boolean createPrescriptionWasCalled = false;
    public IPatientPrescription mockPrescription;


    public MockPrescriptionRepository() {

        this.mockPrescription = new MockPrescription();
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
}
