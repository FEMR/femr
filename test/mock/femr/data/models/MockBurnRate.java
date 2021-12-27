package mock.femr.data.models;

import femr.data.models.core.IBurnRate;
import org.joda.time.DateTime;

public class MockBurnRate implements IBurnRate {

    private int id = 12222;
    private Float rate = 22.3f;
    private Boolean isDeleted = false;

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
        return 0;
    }

    @Override
    public void setMedId(int id) {

    }

    @Override
    public DateTime getCalculatedTime() {
        return null;
    }

    @Override
    public void setCalculatedTime(DateTime time) {

    }

    @Override
    public int getTripId() {
        return 0;
    }

    @Override
    public void setTripId(int id) {

    }
}
