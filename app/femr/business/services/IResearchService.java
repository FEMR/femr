package femr.business.services;


import femr.data.models.PatientResearch;
import femr.ui.controllers.research.ResearchDataModel;

import java.sql.ResultSet;
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

    ResultSet ManualSqlQuery(String sql);


}
