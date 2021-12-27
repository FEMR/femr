package mock.femr.data.daos;

import femr.data.daos.core.IMedicationRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.Medication;
import mock.femr.data.models.MockMedication;
import mock.femr.data.models.MockMedicationInventory;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MockMedicationRepository implements IMedicationRepository {

    public boolean deleteMedicationWasCalled = false;
    public boolean retrieveMedicationInventoriesByTripIdWasCalled = false;
    public boolean createNewMedByNameWasCalled = false;
    public boolean saveMedicationInventoryWasCalled = false;
    public boolean retrieveMedicationInventoryByMedicationIdAndTripIdWasCalled = false;


    private List<? extends IMedicationInventory> mockMedications;

    public IMedicationInventory mockMedicationInventory;

    public IMedication mockMedication;

    public MockMedicationRepository() {

        this.mockMedication = new MockMedication();

        this.mockMedicationInventory = new MockMedicationInventory();

        List<IMedicationInventory> tempList = new ArrayList<>();
        tempList.add(new MockMedicationInventory());
        tempList.add(new MockMedicationInventory());


        this.mockMedications = tempList;
    }

    @Override
    public IConceptMedicationUnit retrieveMedicationUnitByUnitName(String unitName) {
        return null;
    }

    @Override
    public IMedicationGeneric retrieveMedicationGenericByName(String genericName) {
        return null;
    }

    @Override
    public IMedicationInventory retrieveMedicationInventoryByMedicationIdAndTripId(int medicationId, int tripId) {
        retrieveMedicationInventoryByMedicationIdAndTripIdWasCalled = true;
        return mockMedicationInventory;
    }

    @Override
    public List<? extends IMedicationInventory> retrieveMedicationInventoriesByTripId(int tripId, Boolean isDeleted) {
        retrieveMedicationInventoriesByTripIdWasCalled = true;
        List<? extends IMedicationInventory> _mockMedications = null;
        IMedication medication = new Medication();
        List<IMedicationInventory> tempList = new ArrayList<>();
        MockMedicationInventory medicationInventory = new MockMedicationInventory();
        medicationInventory.id=11;
        medicationInventory.name="abc";
        medication.setName("abc");
        medicationInventory.setMedication(medication);
        mockMedicationInventory.setTimeAdded(DateTime.now());
        tempList.add(medicationInventory);
        _mockMedications = tempList;
        return _mockMedications;
    }

    @Override
    public IConceptMedicationForm retrieveConceptMedicationFormByFormName(String formName) {
        return null;
    }

    @Override
    public List<? extends IMedication> retrieveAllPreInventoryMedications() {
        return null;
    }

    @Override
    public IMedication deleteMedication(Integer medicationId, boolean isDeleted) {

        deleteMedicationWasCalled = true;
        mockMedication.setIsDeleted(isDeleted);
        return mockMedication;
    }

    @Override
    public IMedication createNewMedication(String medicationName) {
        createNewMedByNameWasCalled=true;

        return mockMedication;
    }

    @Override
    public IMedication createNewMedication(String medicationName, List<IMedicationGenericStrength> medicationGenericStrengths, IConceptMedicationForm conceptMedicationForm) {
        return null;
    }

    @Override
    public List<? extends IConceptMedicationForm> retrieveAllConceptMedicationForms() {
        return null;
    }

    @Override
    public List<? extends IMedication> retrieveAllConceptMedications() {
        return null;
    }

    @Override
    public IMedication retrieveConceptMedicationById(int id) {
        return null;
    }

    @Override
    public List<? extends IConceptMedicationUnit> retrieveAllConceptMedicationUnits() {
        return null;
    }

    @Override
    public List<? extends IMedication> retrieveAllMedicationByTripId(Integer tripId) {
        return null;
    }

    @Override
    public IMedicationInventory saveMedicationInventory(IMedicationInventory medicationInventory) {
        saveMedicationInventoryWasCalled=true;
        return mockMedicationInventory;
    }
}