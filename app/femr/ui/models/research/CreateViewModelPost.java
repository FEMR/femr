package femr.ui.models.research;

/**
 *  This defines all the objects being requested
 */
public class CreateViewModelPost {

    private QueryObjectPatientModel patientModel;
    // TODO-RESEARCH: Create a medication model and add it here
    private String QueryString;

    public QueryObjectPatientModel getPatientModel() {
        return patientModel;
    }

    public void setPatientModel(QueryObjectPatientModel patientModel) {
        this.patientModel = patientModel;
    }

    public String getQueryString() {
        return QueryString;
    }

    public void setQueryString(String queryString) {
        QueryString = queryString;
    }

    //TODO-RESEARCH: Add all the objects that we will need for the page

}
