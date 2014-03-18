package femr.ui.models.research;

/**
 *  This defines all the objects being requested
 */
public class CreateViewModelRequest {

    private QueryObjectPatientModel patientModel;
    // TODO-RESEARCH: Create a medication model and add it here

    public QueryObjectPatientModel getPatientModel() {
        return patientModel;
    }

    public void setPatientModel(QueryObjectPatientModel patientModel) {
        this.patientModel = patientModel;
    }


    //TODO-RESEARCH: Add all the objects that we will need for the page

}
