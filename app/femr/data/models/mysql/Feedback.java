package femr.data.models.mysql;

import femr.data.models.core.IFeedback;
import org.joda.time.DateTime;
import play.data.format.Formats;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "feedback")
public class Feedback implements IFeedback {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "date", nullable = false)
    private DateTime date;

    @Column(name = "feedback", nullable = false)
    private String feedback;



    //    Getters
    @Override
    public int getId() {
        return id;
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    @Override
    public String getFeedback() {
        return feedback;
    }

    //    Setters
    @Override
    public void setDate (DateTime theDate) {
        date = theDate;
    }

    @Override
    public void setFeedback (String theFeedback) {
        feedback = theFeedback;
    }

}
