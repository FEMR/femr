package unit.app.femr.business.services;

import femr.business.services.system.ConceptService;
import femr.common.IItemModelMapper;
import femr.common.models.MedicationItem;
import femr.data.daos.core.IMedicationRepository;
import femr.data.models.core.IMedication;
import femr.data.models.mysql.concepts.ConceptMedication;
import femr.util.dependencyinjection.providers.MedicationProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ConceptServiceTest {

    ConceptService conceptService;
    IMedicationRepository medicationRepository;
    IItemModelMapper itemModelMapper;
    Provider<IMedication> medicationProvider;

    @Before
    public void setUp() {
        medicationRepository = mock(IMedicationRepository.class);
        itemModelMapper = mock(IItemModelMapper.class);
        conceptService = new ConceptService(medicationRepository, itemModelMapper);
        medicationProvider = new MedicationProvider();
    }


    @Test
    public void retrieveAllMedicationConceptsTest() {
        List<IMedication> allMedications = new ArrayList<>();
        IMedication medication = medicationProvider.get();
        medication.setName("medication");
        allMedications.add(medication);

        doReturn(allMedications).when(medicationRepository).retrieveAllConceptMedications();

        MedicationItem medicationItem = new MedicationItem();
        when(itemModelMapper.createMedicationItem(medication, null, null, null, null, null))
                .thenReturn(medicationItem);

        List<MedicationItem> medicationItems = new ArrayList<>();
        medicationItems.add(medicationItem);
        Assert.assertEquals(medicationItems, conceptService.retrieveAllMedicationConcepts().getResponseObject());
    }


    @Test
    public void retrieveAllMedicationConceptsExceptionTest() {
        when(medicationRepository.retrieveAllConceptMedications()).thenThrow(new RuntimeException());
        Assert.assertEquals(true, conceptService.retrieveAllMedicationConcepts().hasErrors());
    }


    @Test
    public void retrieveConceptMedicationTest() {
        IMedication medication = new ConceptMedication();
        medication.setName("medication");

        when(medicationRepository.retrieveConceptMedicationById(2)).thenReturn(medication);

        MedicationItem medicationItem = new MedicationItem();
        when(itemModelMapper.createMedicationItem(medication, null, null, null, null, null))
                .thenReturn(medicationItem);
        Assert.assertEquals(medicationItem, conceptService.retrieveConceptMedication(2).getResponseObject());
    }


    @Test
    public void retrieveConceptMedicationExceptionTest() {
        when(medicationRepository.retrieveConceptMedicationById(2)).thenThrow(new RuntimeException());
        Assert.assertEquals(true, conceptService.retrieveConceptMedication(2).hasErrors());
    }
}
