package femr.data.models.mysql;


import femr.data.models.core.IBurnRate;
import org.joda.time.DateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "burn_rates")
public class BurnRate implements IBurnRate {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "as")
    private String as;

    @Column(name = "med_id")
    private int medId;
    @Column(name = "trip_id")
    private int tripId;
    @Column(name = "calculated_time")
    private DateTime calculatedTime;

    @Override
    public int getId() {
        return id;
    }


    @Override
    public int getMedId() {
        return medId;
    }

    @Override
    public void setMedId(int id) {
        this.medId=id;
    }

    @Override
    public DateTime getCalculatedTime() {
        return calculatedTime;
    }

    @Override
    public void setCalculatedTime(DateTime time) {
        this.calculatedTime=time;
    }

    @Override
    public int getTripId() {
        return tripId;
    }

    @Override
    public void setTripId(int id) {
        this.tripId=id;
    }
    @Override
    public String getAs() {
        return as;
    }

    @Override
    public void setAs(String as ) {
        this.as=as;
    }

}