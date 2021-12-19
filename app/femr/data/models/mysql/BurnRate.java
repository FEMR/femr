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
    @Column(name = "burn_rate")
    private Float burnRate;
    @Column(name = "med_id")
    private int medId;
    @Column(name = "calculated_time")
    private DateTime calculatedTime;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Float getRate() {
        return burnRate;
    }

    @Override
    public void setRate(Float rate) {
        this.burnRate=rate;
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
}