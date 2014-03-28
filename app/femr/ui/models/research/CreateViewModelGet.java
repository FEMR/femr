package femr.ui.models.research;

import java.util.List;

/**
 * this is the view model for the research page for sending the data back from the server
 *
 * this is the blank page !!! Update this description when done !!!
 */
public class CreateViewModelGet {

    private QueryObjectPatientModel patientModel;
    private List<ResearchDataModel> patientData;

    public QueryObjectPatientModel getPatientModel() {
        return patientModel;
    }

    public void setPatientModel(QueryObjectPatientModel patientModel) {
        this.patientModel = patientModel;
    }

    public List<ResearchDataModel> getPatientData() {
        return patientData;
    }

    public void setPatientData(List<ResearchDataModel> patientData) {
        this.patientData = patientData;
    }
    // TODO-RESEARCH: Create model for displaying the data
}
