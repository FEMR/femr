package unit.app.femr.business.services;

import femr.business.services.core.IInventoryService;
import femr.business.services.core.ISearchService;
import femr.business.services.system.SearchService;
import femr.common.IItemModelMapper;
import femr.data.daos.IRepository;
import femr.data.daos.core.IEncounterRepository;
import femr.data.daos.core.IPatientRepository;
import femr.data.daos.core.IPrescriptionRepository;
import femr.data.models.mysql.Patient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class SearchServiceTest {

    ISearchService searchService;
    IRepository diagnosisRepository;
    IRepository missionRepository;
    IPatientRepository patientRepository;
    IEncounterRepository encounterRepository;
    IRepository vitalRepository;
    IPrescriptionRepository prescriptionRepository;
    IRepository systemRepository;
    IInventoryService inventoryService;
    IRepository cityRepository;
    IItemModelMapper itemModelMapper;

    @Before
    public void setUp() {
        diagnosisRepository = mock(IRepository.class);
        missionRepository = mock(IRepository.class);
        patientRepository = mock(IPatientRepository.class);
        encounterRepository = mock(IEncounterRepository.class);
        vitalRepository = mock(IRepository.class);
        prescriptionRepository = mock(IPrescriptionRepository.class);
        systemRepository = mock(IRepository.class);
        inventoryService = mock(IInventoryService.class);
        cityRepository = mock(IRepository.class);
        itemModelMapper = mock(IItemModelMapper.class);
        searchService = new SearchService(diagnosisRepository, missionRepository, patientRepository, encounterRepository, vitalRepository, prescriptionRepository, systemRepository, inventoryService, cityRepository, itemModelMapper);
    }

    @Test
    public void retrievePatientsFromTriageSearchTest() {
        Patient patient1 = new Patient();
        patient1.setFirstName("Andy");
        Assert.assertEquals(0, searchService.retrievePatientsFromTriageSearch(patient1.getFirstName(), null, null, null, null, null, null).getResponseObject().size());
    }
}
