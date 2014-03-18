package femr.ui.controllers.research;

/**
 * This is the controller for the research page
 */

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.services.IResearchService;
import femr.business.services.ISessionService;

import femr.common.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.research.CreateViewModelRequest;
import femr.ui.models.research.QueryObjectPatientModel;
import femr.ui.views.html.research.index;
import femr.ui.views.html.research.index$;
import femr.util.DataStructure.Pair;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;


@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ResearchController extends Controller {
    private IResearchService researchService;
    private ISessionService sessionService;

    @Inject
    public ResearchController(ISessionService sessionService, IResearchService researchService) {
        this.researchService = researchService;
        this.sessionService = sessionService;
    }

    public Result index() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        // Create the viewModel
        CreateViewModelRequest viewModelRequest = new CreateViewModelRequest();

        return ok(index.render(currentUserSession, null, null)) ;
    }

    /**
     * Creates and populates the Patient object model with all the possible values the
     * user can search by as well as the conditions to combine them
     * @return The {@link QueryObjectPatientModel} that was created
     */
    private QueryObjectPatientModel CreatePatientModel() {
        QueryObjectPatientModel patientModel = new QueryObjectPatientModel();

        // create a list of conditional
        List<String> conditionList = new ArrayList<>();
        conditionList.add("AND");
        conditionList.add("OR");
        conditionList.add("NOT");
        conditionList.add("XOR");

        patientModel.setConditionList(conditionList);

        // create a list of comparison symbols
        List<String> comparisonList = new ArrayList<>();
        comparisonList.add("=");
        comparisonList.add("!=");
        comparisonList.add("<");
        comparisonList.add("<=");
        comparisonList.add(">");
        comparisonList.add(">=");

        patientModel.setComparisonList(comparisonList);

        // TODO-RESEARCH: Add the properties associated with a patient that the user can choose from

        return patientModel; // temporary replace
    }



    //TODO-RESEARCH: Add the code for the Research controller here
}
