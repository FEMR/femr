package mock.femr.data.daos;

import femr.data.daos.core.IMedicationRepository;
import femr.data.models.core.*;
import mock.femr.data.models.MockMedication;

import java.util.List;

public class MockMedicationRepository implements IMedicationRepository {

    public boolean deleteMedicationWasCalled = false;

    public IMedication mockMedication;

    public MockMedicationRepository() {

        this.mockMedication = new MockMedication();
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
        return null;
    }

    @Override
    public List<? extends IMedicationInventory> retrieveMedicationInventoriesByTripId(int tripId, Boolean isDeleted) {
        return null;
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
        return null;
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
        return null;
    }
}