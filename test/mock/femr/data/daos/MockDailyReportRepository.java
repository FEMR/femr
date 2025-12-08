package mock.femr.data.daos;

import femr.data.daos.core.IDailyReportRepository;
import femr.data.models.core.IMissionTrip;
import mock.femr.data.models.MockMissionTrip;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class MockDailyReportRepository implements IDailyReportRepository {

    public boolean getDemographicCountsWasCalled = false;
    public boolean getMissionTripByIdWasCalled = false;

    public int lastTripIdRequested = -1;
    public DateTime lastDateRequested = null;

    public IMissionTrip mockMissionTrip;
    public Map<String, Map<String, Integer>> mockDemographicCounts;

    public boolean returnNullMissionTrip = false;

    public MockDailyReportRepository() {
        this.mockMissionTrip = new MockMissionTrip();
        this.mockDemographicCounts = new HashMap<>();
    }

    @Override
    public Map<String, Map<String, Integer>> getDemographicCounts(int tripId, DateTime date) {
        getDemographicCountsWasCalled = true;
        lastTripIdRequested = tripId;
        lastDateRequested = date;
        return mockDemographicCounts;
    }

    @Override
    public IMissionTrip getMissionTripById(int tripId) {
        getMissionTripByIdWasCalled = true;
        lastTripIdRequested = tripId;

        if (returnNullMissionTrip) {
            return null;
        }
        return mockMissionTrip;
    }

    public void addDemographicCount(String ageCategory, String sexCategory, int count) {
        mockDemographicCounts.computeIfAbsent(sexCategory, k -> new HashMap<>())
                .put(ageCategory, count);
    }

    public void clearDemographicCounts() {
        mockDemographicCounts.clear();
    }
}