package femr.ui.models.research;

import java.util.List;

/**
 *  This is the available objects that the user can use to query
 */
public class QueryObjectPatientModel {

    private List<String> patientProperties;  // the pair is <Name of property, property type> ex. <age,int>
    private List<String> logicList; // and, or, not, etc.
    private List<String> comparisonList;



    public List<String> getPatientProperties() {
        return patientProperties;
    }

    public void setPatientProperties(List<String> patientProperties) {
        this.patientProperties = patientProperties;
    }

    public List<String> getLogicList() {
        return logicList;
    }

    public void setLogicList(List<String> logicList) {
        this.logicList = logicList;
    }

    public List<String> getComparisonList() {
        return comparisonList;
    }

    public void setComparisonList(List<String> comparisonList) {
        this.comparisonList = comparisonList;
    }

    // TODO-RESEARCH: Implement the object available for sql query's

}
