package femr.common.models;

import java.util.List;
import java.util.Map;

/**
 * Defines the interface for all the items that are returned from the research
 * Patient query
 */
public interface IPatientResearch {

    Map<String, String> getPatientPropertiesLookup();
    Map<String, String> getLogicLookup();
    Map<String, String> getConditionLookup();

    List<String> getPatientPropertiesLookupAsList();
    List<String> getLogicLookupAsList();
    List<String> getConditionLookupAsList();


}
