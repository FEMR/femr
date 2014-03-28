package femr.business.services;


import femr.ui.models.research.ResearchDataModel;

import java.util.List;
import java.util.Map;

/**
 * Interface for the Research Service
 */
public interface IResearchService {



    Map<String,String> getPatientPropertiesLookup();
    Map<String,String> getLogicLookup();
    Map<String,String> getConditionLookup();

    List<String> getPatientPropertiesLookupAsList();
    List<String> getLogicLookupAsList();
    List<String> getConditionLookupAsList();



    List<ResearchDataModel> ManualSqlQuery(String sql);


}
