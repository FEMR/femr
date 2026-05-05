package unit.app.femr.business.services;

import femr.business.services.core.IInventoryService;
import femr.business.services.core.ISearchService;
import femr.business.services.system.SearchService;
import femr.common.IItemModelMapper;
import femr.data.daos.IRepository;
import femr.data.daos.core.IEncounterRepository;
import femr.data.daos.core.IPatientRepository;
import femr.data.daos.core.IPrescriptionRepository;
import femr.data.models.core.IConceptDiagnosis;
import femr.data.models.core.IConceptWhoHealthEvent;
import femr.data.models.core.IConceptWhoProcedure;
import femr.data.models.core.IMissionCity;
import femr.data.models.core.IMissionTrip;
import femr.data.models.core.IPatientEncounterVital;
import femr.data.models.core.ISystemSetting;
import femr.data.models.mysql.Patient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class SearchServiceTest {

    ISearchService searchService;
    IRepository<IConceptDiagnosis> diagnosisRepository;
    IRepository<IConceptWhoHealthEvent> whoHealthEventRepository;
    IRepository<IConceptWhoProcedure> whoProcedureRepository;
    IRepository<IMissionTrip> missionRepository;
    IPatientRepository patientRepository;
    IEncounterRepository encounterRepository;
    IRepository<IPatientEncounterVital> vitalRepository;
    IPrescriptionRepository prescriptionRepository;
    IRepository<ISystemSetting> systemRepository;
    IInventoryService inventoryService;
    IRepository<IMissionCity> cityRepository;
    IItemModelMapper itemModelMapper;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        diagnosisRepository = (IRepository<IConceptDiagnosis>) mock(IRepository.class);
        whoHealthEventRepository = (IRepository<IConceptWhoHealthEvent>) mock(IRepository.class);
        whoProcedureRepository = (IRepository<IConceptWhoProcedure>) mock(IRepository.class);
        missionRepository = (IRepository<IMissionTrip>) mock(IRepository.class);
        patientRepository = mock(IPatientRepository.class);
        encounterRepository = mock(IEncounterRepository.class);
        vitalRepository = (IRepository<IPatientEncounterVital>) mock(IRepository.class);
        prescriptionRepository = mock(IPrescriptionRepository.class);
        systemRepository = (IRepository<ISystemSetting>) mock(IRepository.class);
        inventoryService = mock(IInventoryService.class);
        cityRepository = (IRepository<IMissionCity>) mock(IRepository.class);
        itemModelMapper = mock(IItemModelMapper.class);
        searchService = new SearchService(diagnosisRepository, whoHealthEventRepository, whoProcedureRepository, missionRepository, patientRepository, encounterRepository, vitalRepository, prescriptionRepository, systemRepository, inventoryService, cityRepository, itemModelMapper);
    }

    @Test
    public void retrievePatientsFromTriageSearchTest() {
        Patient patient1 = new Patient();
        patient1.setFirstName("Andy");
        Assert.assertEquals(0, searchService.retrievePatientsFromTriageSearch(patient1.getFirstName(), null, null, null, null, null, null).getResponseObject().size());
    }
}
