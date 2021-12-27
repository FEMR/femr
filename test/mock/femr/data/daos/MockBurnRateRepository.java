package mock.femr.data.daos;

import femr.data.daos.core.IBurnRateRepository;
import femr.data.models.core.IBurnRate;
import femr.data.models.mysql.BurnRate;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MockBurnRateRepository implements IBurnRateRepository {

    public boolean retrieveBurnRateByMedIdAndTripIdWasCalled = false;

    public boolean retrieveAllBurnRatesByTripIdWasCalled = false;


    @Override
    public IBurnRate createBurnRate(int medID, float burnRate, DateTime calculatedDateTime, int tripId) {

        return null;
    }

    @Override
    public IBurnRate updateBurnRate(IBurnRate burnRate) {
        return null;
    }

    @Override
    public IBurnRate deleteBurnRate(IBurnRate burnRate) {
        return null;
    }

    @Override
    public IBurnRate retrieveBurnRateByMedId(int medid) {
        return null;
    }

    @Override
    public List<? extends IBurnRate> retrieveAllBurnRates() {
        return null;
    }

    @Override
    public List<? extends IBurnRate> retrieveAllBurnRatesByTripId(int tripId) {
        retrieveAllBurnRatesByTripIdWasCalled = true;
        IBurnRate burnRate = new BurnRate();
        burnRate.setRate(22.2f);
        burnRate.setCalculatedTime(DateTime.now());
        burnRate.setMedId(12222);
        burnRate.setTripId(tripId);
        List<IBurnRate> burnRates  = new ArrayList<>();
        burnRates.add(burnRate);
        return burnRates;
    }

    @Override
    public IBurnRate retrieveBurnRateByMedIdAndTripId(int medid, int tripId) {
        retrieveBurnRateByMedIdAndTripIdWasCalled = true;
        IBurnRate burnRate = new BurnRate();
        burnRate.setRate(22.2f);
        burnRate.setCalculatedTime(DateTime.now());
        burnRate.setMedId(medid);
        burnRate.setTripId(tripId);
        return burnRate;
    }
}