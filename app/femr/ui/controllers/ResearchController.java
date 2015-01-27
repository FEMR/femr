package femr.ui.controllers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import femr.business.helpers.DomainMapper;
import femr.business.services.core.IMedicationService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.ResearchFilterItem;
import femr.data.models.core.IMedication;
import femr.ui.models.research.json.ResearchGraphDataItem;
import femr.common.dtos.CurrentUser;
import femr.business.services.core.IResearchService;
import femr.business.services.core.ISessionService;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.views.html.research.index;
import femr.ui.views.html.research.generatedata;
import femr.ui.models.research.FilterViewModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This is the controller for the research page, it is currently not supported.
 * Research was designed before combining of some tables in the database
 */
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ResearchController extends Controller {

    private final Form<FilterViewModel> FilterViewModelForm = Form.form(FilterViewModel.class);

    private IResearchService researchService;
    private IMedicationService medicationService;
    private ISessionService sessionService;

    /**
     * Research Controller constructor that Injects the services indicated by the parameters
     *
     * @param sessionService  {@link ISessionService}
     * @param researchService {@link IResearchService}
     * @param medicationService {@link IMedicationService}
     */
    @Inject
    public ResearchController(ISessionService sessionService, IResearchService researchService, IMedicationService medicationService) {
        this.researchService = researchService;
        this.medicationService = medicationService;
        this.sessionService = sessionService;
    }

    public Result indexGet() {

        FilterViewModel filterViewModel = new FilterViewModel();

        // Set Default Start (30 Days Ago) and End Date (Today)
        Calendar today = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        filterViewModel.setEndDate(dateFormat.format(today.getTime()));
        today.add(Calendar.DAY_OF_MONTH, -30);
        filterViewModel.setStartDate(dateFormat.format(today.getTime()));

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        return ok(index.render(currentUserSession, filterViewModel));
    }

    public Result generateData() {
        FilterViewModel viewModel = FilterViewModelForm.bindFromRequest().get();
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        return ok(generatedata.render(currentUserSession, viewModel));
    }

    public Result getGraphPost(){

        FilterViewModel filterViewModel = FilterViewModelForm.bindFromRequest().get();
        //TODO: domain mapper out of scope
        ResearchFilterItem researchFilterItem = DomainMapper.createResearchFilterItem(filterViewModel);

        ServiceResponse<ResearchGraphDataItem> response = researchService.getGraphData(researchFilterItem);
        ResearchGraphDataItem graphModel = new ResearchGraphDataItem();
        if( !response.hasErrors() ) {

            graphModel = response.getResponseObject();
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(graphModel);
        return ok(jsonString);
    }

    public Result typeaheadJSONGet(){

        ServiceResponse<List<String>> medicationServiceResponse = medicationService.findAllMedications();
        if (medicationServiceResponse.hasErrors()) {
            return ok("");
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(medicationServiceResponse.getResponseObject());

        return ok(jsonString);
    }

}
