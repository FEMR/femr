package femr.data.models.core;


import org.joda.time.DateTime;

public interface IBurnRate {

    int getId();

    Float getRate();
    void setRate(Float rate);

    int getMedId();
    void setMedId(int id);

    DateTime getCalculatedTime();
    void setCalculatedTime(DateTime time);


}