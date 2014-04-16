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
import femr.ui.models.research.ResearchDataModel;
import femr.ui.views.html.research.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.*;

/**
 * This is the controller for the research page
 */
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ResearchController extends Controller {
    private final Form<CreateViewModelPost> createViewModelForm = Form.form(CreateViewModelPost.class);
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
        CreateViewModelGet viewModel = new CreateViewModelGet();
        viewModel.setPatientModel(CreatePatientModel());

        return ok(index.render(currentUserSession, viewModel));
    }

    /**
     * Gets the generated search query and parses it then sends it to the service layer and displays the results
     * @return The Rendered results the the view
     */
    public Result createPost() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        //bind QueryString from POST request
        CreateViewModelPost viewModelPost = createViewModelForm.bindFromRequest().get();
        String sql = viewModelPost.getQueryString();

        // execute query
        List<ResearchDataModel> resultModel = researchService.ManualSqlQuery(sql);
        CreateViewModelGet viewModelGet = new CreateViewModelGet();
        viewModelGet.setPatientData(resultModel);


        //do a redirect
        return ok(result.render(currentUserSession,viewModelGet));
    }

    /**
     * Creates and populates the Patient object model with all the possible values the
     * user can search by as well as the conditions to combine them
     *
     * @return The {@link QueryObjectPatientModel} that was created
     */
    private QueryObjectPatientModel CreatePatientModel() {
        QueryObjectPatientModel patientModel = new QueryObjectPatientModel();

        List<String> Logic = researchService.getLogicLookupAsList();
        patientModel.setLogicList(Logic);


        List<String> Condition = researchService.getConditionLookupAsList();
        patientModel.setComparisonList(Condition);

        List<String> Properties = researchService.getPatientPropertiesLookupAsList();
        patientModel.setPatientProperties(Properties);

        return patientModel; // temporary replace

    }
}
