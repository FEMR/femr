package femr.ui.models.feedback;

import java.util.Date;

public class IndexViewModelPost {

    // Variable Declaration
    private String feedbackMsg;
    private Date feedbackDate;

    // Getters
    public String getFeedbackMsg() {
        return feedbackMsg;
    }

    public Date getFeedbackDate() {
        Date feedbackDate = new Date();
        return feedbackDate;
    }

    // Setters
    public void setFeedbackMsg(String feedbackMsg) {
        this.feedbackMsg = feedbackMsg;
    }
}