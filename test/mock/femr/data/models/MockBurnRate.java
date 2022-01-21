package mock.femr.data.models;

import femr.data.models.core.IBurnRate;
import org.joda.time.DateTime;

public class MockBurnRate implements IBurnRate {

    private int id = 12222;
    private Float rate = 22.3f;
    private Boolean isDeleted = false;
    private DateTime calculatedTime = DateTime.now();
    private int tripId = 1;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Float getRate() {
        return rate;
    }

    @Override
    public void setRate(Float rate) {

        this.rate = rate;

    }

    @Override
    public int getMedId() {
        return id;
    }

    @Override
    public void setMedId(int id) {
        this.id=id;
    }

    @Override
    public DateTime getCalculatedTime() {
        return calculatedTime;
    }

    @Override
    public void setCalculatedTime(DateTime time) {
        this.calculatedTime = time;
    }

    @Override
    public int getTripId() {
        return tripId;
    }

    @Override
    public void setTripId(int id) {
        this.tripId=id;
    }
}
