package femr.business.services;


import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientResearch;
import femr.data.models.PatientResearch;
import femr.ui.controllers.research.ResearchDataModel;

import java.util.List;

/**
 * Interface for the Research Service
 */
public interface IResearchService {
   // ServiceResponse<List<? extends IPatient>> findAllPatient();  // returns a list of all the patients so we can narrow it down
    //TODO-RESEARCH: Declare the services

    ServiceResponse<List<? extends IPatientResearch>> testModel();

    List<PatientResearch> testModelSQL();
}
