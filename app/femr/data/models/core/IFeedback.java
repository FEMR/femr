package femr.data.models.core;

import org.joda.time.DateTime;


public interface IFeedback {

//    Getters
    int getId();

    DateTime getDate();

    String getFeedback();

//    Setters
    void setDate (DateTime theDate);
    void setFeedback (String theFeedback);

}
