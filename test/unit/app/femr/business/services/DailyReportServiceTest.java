package unit.app.femr.business.services;

import femr.business.services.core.IDailyReportService;
import femr.business.services.system.DailyReportService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.DailyReportItem;
import mock.femr.data.daos.MockDailyReportRepository;
import mock.femr.data.models.MockMissionCity;
import mock.femr.data.models.MockMissionCountry;
import mock.femr.data.models.MockMissionTeam;
import mock.femr.data.models.MockMissionTrip;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class DailyReportServiceTest {

    private IDailyReportService dailyReportService;
    private MockDailyReportRepository mockDailyReportRepository;

    @Before
    public void setUp() {
        mockDailyReportRepository = new MockDailyReportRepository();
        dailyReportService = new DailyReportService(mockDailyReportRepository);
    }

    @After
    public void tearDown() {
        mockDailyReportRepository = null;
        dailyReportService = null;
    }

    @Test
    public void generateDailyReport_validTripId_repositoryCalledWithCorrectId() {
        int tripId = 5;
        DateTime date = new DateTime(2024, 3, 10, 0, 0);

        dailyReportService.generateDailyReport(tripId, date);

        assertTrue(mockDailyReportRepository.getMissionTripByIdWasCalled);
        assertEquals(tripId, mockDailyReportRepository.lastTripIdRequested);
    }

    @Test
    public void generateDailyReport_validTripId_demographicCountsCalledWithCorrectParameters() {
        int tripId = 5;
        DateTime date = new DateTime(2024, 3, 10, 0, 0);

        dailyReportService.generateDailyReport(tripId, date);

        assertTrue(mockDailyReportRepository.getDemographicCountsWasCalled);
        assertEquals(tripId, mockDailyReportRepository.lastTripIdRequested);
        assertEquals(date, mockDailyReportRepository.lastDateRequested);
    }

    @Test
    public void generateDailyReport_tripNotFound_returnsError() {
        mockDailyReportRepository.returnNullMissionTrip = true;

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(999, new DateTime());

        assertTrue(response.hasErrors());
        assertNull(response.getResponseObject());
    }

    @Test
    public void generateDailyReport_tripNotFound_doesNotCallDemographicCounts() {
        mockDailyReportRepository.returnNullMissionTrip = true;

        dailyReportService.generateDailyReport(999, new DateTime());

        assertFalse(mockDailyReportRepository.getDemographicCountsWasCalled);
    }

    @Test
    public void generateDailyReport_validTrip_returnsTeamName() {
        MockMissionTrip mockTrip = new MockMissionTrip();
        MockMissionTeam mockTeam = new MockMissionTeam();
        mockTeam.setName("Test Medical Team");
        mockTrip.setMissionTeam(mockTeam);
        mockDailyReportRepository.mockMissionTrip = mockTrip;

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        assertEquals("Test Medical Team", response.getResponseObject().getTeamName());
    }

    @Test
    public void generateDailyReport_validTrip_returnsCityName() {
        MockMissionTrip mockTrip = new MockMissionTrip();
        MockMissionCity mockCity = new MockMissionCity();
        mockCity.setName("Test City");
        mockTrip.setMissionCity(mockCity);
        mockDailyReportRepository.mockMissionTrip = mockTrip;

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        assertEquals("Test City", response.getResponseObject().getTripCity());
    }

    @Test
    public void generateDailyReport_validTrip_returnsCountryName() {
        MockMissionTrip mockTrip = new MockMissionTrip();
        MockMissionCity mockCity = new MockMissionCity();
        MockMissionCountry mockCountry = new MockMissionCountry();
        mockCountry.setName("Test Country");
        mockCity.setMissionCountry(mockCountry);
        mockTrip.setMissionCity(mockCity);
        mockDailyReportRepository.mockMissionTrip = mockTrip;

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        assertEquals("Test Country", response.getResponseObject().getTripCountry());
    }

    @Test
    public void generateDailyReport_validTrip_formatsReportDateCorrectly() {
        DateTime date = new DateTime(2024, 3, 15, 0, 0);

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, date);

        assertFalse(response.hasErrors());
        assertEquals("15/03/2024", response.getResponseObject().getReportDate());
    }

    @Test
    public void generateDailyReport_validTrip_formatsDepartureDateCorrectly() {
        MockMissionTrip mockTrip = new MockMissionTrip();
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.DECEMBER, 25);
        mockTrip.setEndDate(cal.getTime());
        mockDailyReportRepository.mockMissionTrip = mockTrip;

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        assertEquals("25/12/2024", response.getResponseObject().getDepartureDate());
    }

    @Test
    public void generateDailyReport_nullEndDate_departureDateIsNull() {
        MockMissionTrip mockTrip = new MockMissionTrip();
        mockTrip.setEndDate(null);
        mockDailyReportRepository.mockMissionTrip = mockTrip;

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        assertNull(response.getResponseObject().getDepartureDate());
    }

    @Test
    public void generateDailyReport_nullMissionTeam_teamNameIsNull() {
        MockMissionTrip mockTrip = new MockMissionTrip();
        mockTrip.setMissionTeam(null);
        mockDailyReportRepository.mockMissionTrip = mockTrip;

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        assertNull(response.getResponseObject().getTeamName());
    }

    @Test
    public void generateDailyReport_nullMissionCity_cityAndCountryAreNull() {
        MockMissionTrip mockTrip = new MockMissionTrip();
        mockTrip.setMissionCity(null);
        mockDailyReportRepository.mockMissionTrip = mockTrip;

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        assertNull(response.getResponseObject().getTripCity());
        assertNull(response.getResponseObject().getTripCountry());
    }

    @Test
    public void generateDailyReport_nullMissionCountry_countryIsNull() {
        MockMissionTrip mockTrip = new MockMissionTrip();
        MockMissionCity mockCity = new MockMissionCity();
        mockCity.setName("Test City");
        mockCity.setMissionCountry(null);
        mockTrip.setMissionCity(mockCity);
        mockDailyReportRepository.mockMissionTrip = mockTrip;

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        assertEquals("Test City", response.getResponseObject().getTripCity());
        assertNull(response.getResponseObject().getTripCountry());
    }

    @Test
    public void generateDailyReport_maleDemographics_mappedCorrectly() {
        mockDailyReportRepository.addDemographicCount("UNDER_1", "MALE", 2);
        mockDailyReportRepository.addDemographicCount("AGE_1_TO_4", "MALE", 5);
        mockDailyReportRepository.addDemographicCount("AGE_5_TO_17", "MALE", 10);
        mockDailyReportRepository.addDemographicCount("AGE_18_TO_64", "MALE", 25);
        mockDailyReportRepository.addDemographicCount("AGE_65_PLUS", "MALE", 3);

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        DailyReportItem report = response.getResponseObject();
        assertEquals(2, report.getMaleUnder1());
        assertEquals(5, report.getMale1To4());
        assertEquals(10, report.getMale5To17());
        assertEquals(25, report.getMale18To64());
        assertEquals(3, report.getMale65Plus());
        assertEquals(45, report.getMaleTotal());
    }

    @Test
    public void generateDailyReport_femaleNonPregnantDemographics_mappedCorrectly() {
        mockDailyReportRepository.addDemographicCount("UNDER_1", "FEMALE_NON_PREGNANT", 1);
        mockDailyReportRepository.addDemographicCount("AGE_1_TO_4", "FEMALE_NON_PREGNANT", 4);
        mockDailyReportRepository.addDemographicCount("AGE_5_TO_17", "FEMALE_NON_PREGNANT", 8);
        mockDailyReportRepository.addDemographicCount("AGE_18_TO_64", "FEMALE_NON_PREGNANT", 20);
        mockDailyReportRepository.addDemographicCount("AGE_65_PLUS", "FEMALE_NON_PREGNANT", 6);

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        DailyReportItem report = response.getResponseObject();
        assertEquals(1, report.getFemaleNonPregnantUnder1());
        assertEquals(4, report.getFemaleNonPregnant1To4());
        assertEquals(8, report.getFemaleNonPregnant5To17());
        assertEquals(20, report.getFemaleNonPregnant18To64());
        assertEquals(6, report.getFemaleNonPregnant65Plus());
        assertEquals(39, report.getFemaleNonPregnantTotal());
    }

    @Test
    public void generateDailyReport_femalePregnantDemographics_mappedCorrectly() {
        mockDailyReportRepository.addDemographicCount("AGE_5_TO_17", "FEMALE_PREGNANT", 2);
        mockDailyReportRepository.addDemographicCount("AGE_18_TO_64", "FEMALE_PREGNANT", 7);

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        DailyReportItem report = response.getResponseObject();
        assertEquals(0, report.getFemalePregnantUnder1());
        assertEquals(0, report.getFemalePregnant1To4());
        assertEquals(2, report.getFemalePregnant5To17());
        assertEquals(7, report.getFemalePregnant18To64());
        assertEquals(0, report.getFemalePregnant65Plus());
        assertEquals(9, report.getFemalePregnantTotal());
    }

    @Test
    public void generateDailyReport_mixedDemographics_totalEncountersCorrect() {
        mockDailyReportRepository.addDemographicCount("AGE_18_TO_64", "MALE", 10);
        mockDailyReportRepository.addDemographicCount("AGE_18_TO_64", "FEMALE_NON_PREGNANT", 15);
        mockDailyReportRepository.addDemographicCount("AGE_18_TO_64", "FEMALE_PREGNANT", 5);

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        assertEquals(30, response.getResponseObject().getTotalEncounters());
    }

    @Test
    public void generateDailyReport_mixedDemographics_ageTotalsCorrect() {
        mockDailyReportRepository.addDemographicCount("UNDER_1", "MALE", 3);
        mockDailyReportRepository.addDemographicCount("UNDER_1", "FEMALE_NON_PREGNANT", 2);
        mockDailyReportRepository.addDemographicCount("AGE_65_PLUS", "MALE", 5);
        mockDailyReportRepository.addDemographicCount("AGE_65_PLUS", "FEMALE_NON_PREGNANT", 4);
        mockDailyReportRepository.addDemographicCount("AGE_65_PLUS", "FEMALE_PREGNANT", 1);

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        DailyReportItem report = response.getResponseObject();
        assertEquals(5, report.getUnder1Total());
        assertEquals(10, report.get65PlusTotal());
    }

    @Test
    public void generateDailyReport_noDemographicData_allCountsZero() {
        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        DailyReportItem report = response.getResponseObject();
        assertEquals(0, report.getMaleTotal());
        assertEquals(0, report.getFemaleNonPregnantTotal());
        assertEquals(0, report.getFemalePregnantTotal());
        assertEquals(0, report.getTotalEncounters());
    }

    @Test
    public void generateDailyReport_unknownSexCategory_ignored() {
        mockDailyReportRepository.addDemographicCount("AGE_18_TO_64", "UNKNOWN", 10);
        mockDailyReportRepository.addDemographicCount("AGE_18_TO_64", "MALE", 5);

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        assertEquals(5, response.getResponseObject().getTotalEncounters());
    }

    @Test
    public void generateDailyReport_unknownAgeCategory_ignored() {
        mockDailyReportRepository.addDemographicCount("AGE_UNKNOWN", "MALE", 10);
        mockDailyReportRepository.addDemographicCount("AGE_18_TO_64", "MALE", 5);

        ServiceResponse<DailyReportItem> response = dailyReportService.generateDailyReport(1, new DateTime());

        assertFalse(response.hasErrors());
        assertEquals(5, response.getResponseObject().getMaleTotal());
    }
}
