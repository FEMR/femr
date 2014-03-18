package femr.ui.models.research;

import femr.util.DataStructure.Pair;

import java.util.List;

/**
 *  This is the available objects that the user can use to query
 */
public class QueryObjectPatientModel {

    private List<Pair<String,Object>> patientProperties;  // the pair is <Name of property, property type> ex. <age,int>
    private List<String> conditionList; // and, or, not, etc.
    private List<String> comparisonList;



    public List<Pair<String, Object>> getPatientProperties() {
        return patientProperties;
    }

    public void setPatientProperties(List<Pair<String, Object>> patientProperties) {
        this.patientProperties = patientProperties;
    }

    public List<String> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<String> conditionList) {
        this.conditionList = conditionList;
    }

    public List<String> getComparisonList() {
        return comparisonList;
    }

    public void setComparisonList(List<String> comparisonList) {
        this.comparisonList = comparisonList;
    }

    // TODO-RESEARCH: Implement the object available for sql query's

}
