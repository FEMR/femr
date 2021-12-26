package femr.data.models.core;


import org.joda.time.DateTime;

public interface IBurnRate {

    int getId();

    String getAs();
    void setAs(String as);

    int getMedId();
    void setMedId(int id);

    DateTime getCalculatedTime();
    void setCalculatedTime(DateTime time);

    int getTripId();
    void setTripId(int id);


}