package femr.ui.controllers.research;


import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.services.IResearchService;
import femr.business.services.ISessionService;

import femr.common.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.research.CreateViewModelGet;
import femr.ui.models.research.CreateViewModelPost;
import femr.ui.models.research.QueryObjectPatientModel;
import femr.ui.views.html.research.index;
import femr.util.DataStructure.Pair;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the controller for the research page
 */
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ResearchController extends Controller {
    private IResearchService researchService;
    private ISessionService sessionService;

    /**
     * Research Controller constructer that Injects the services indicated by the parameters
     *
     * @param sessionService  {@link ISessionService}
     * @param researchService {@link IResearchService}
     */
    @Inject
    public ResearchController(ISessionService sessionService, IResearchService researchService) {
        this.researchService = researchService;
        this.sessionService = sessionService;
    }

    /**
     * Creates the model and renders the view for the index page of Research
     *
     * @return The models for the index page
     */
    public Result index() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        // Create the viewModel
        //CreateViewModelPost viewModelPost = new CreateViewModelPost();
        //QueryObjectPatientModel viewPatientModel = CreatePatientModel();
        CreateViewModelGet viewModel = new CreateViewModelGet();
        viewModel.setPatientModel(CreatePatientModel());

        return ok(index.render(currentUserSession, viewModel));
    }

    /**
     * Creates and populates the Patient object model with all the possible values the
     * user can search by as well as the conditions to combine them
     *
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

        // Create the tempoaray patient info
        List<Pair<String, Object>> patientProperties = new ArrayList<>();
        patientProperties.add(new Pair<String, Object>("ID", Integer.class));
        patientProperties.add(new Pair<String, Object>("Age", Integer.class));
        patientProperties.add(new Pair<String, Object>("City", String.class));
        patientProperties.add(new Pair<String, Object>("Gender", String.class));
        patientProperties.add(new Pair<String, Object>("Date Taken", String.class));
        patientProperties.add(new Pair<String, Object>("Medication", String.class));
        patientProperties.add(new Pair<String, Object>("Treatment", String.class));

        patientModel.setPatientProperties(patientProperties);
        // TODO-RESEARCH: Add the properties associated with a patient that the user can choose from

        return patientModel; // temporary replace

    }


    //TODO-RESEARCH: Add the code for rest the Research controller here

    public Result createPost() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        // Create the viewModel
        CreateViewModelGet viewModel = new CreateViewModelGet();
        viewModel.setPatientModel(CreatePatientModel());

        return ok(index.render(currentUserSession, viewModel));
    }
}
