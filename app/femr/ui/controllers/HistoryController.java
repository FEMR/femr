package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.services.*;
import femr.common.dto.CurrentUser;
import femr.common.dto.ServiceResponse;
import femr.common.models.*;
import femr.data.models.*;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.search.EncounterViewModel;
import femr.ui.models.search.IndexPatientViewModelGet;
import femr.ui.views.html.history.indexEncounter;
import femr.ui.views.html.history.indexPatient;
import org.h2.util.StringUtils;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import play.mvc.Security;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class HistoryController extends Controller {
    private ISessionService sessionService;
    private ISearchService searchService;

    @Inject
    public HistoryController(ISessionService sessionService,
                             ISearchService searchService) {
        this.sessionService = sessionService;
        this.searchService = searchService;
    }

    public Result indexPatientGet(String query) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        boolean error = false;


        IndexPatientViewModelGet viewModel = new IndexPatientViewModelGet();

        //how do we show more than one?
        query = query.replace("-", " ");
        ServiceResponse<List<PatientItem>> patientResponse = searchService.getPatientsFromQueryString(query);
        if (patientResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<PatientItem> patientItems = patientResponse.getResponseObject();


        if (patientItems == null || patientItems.size() < 1) {
//            return ok(showError.render(currentUser));
            //return an error near the search box
        }

        for (PatientItem patientItem : patientItems)
            patientItem.setPathToPhoto(routes.PhotoController.GetPatientPhoto(patientItem.getId(), true).toString());
        viewModel.setPatientItems(patientItems);
        viewModel.setPatientItem(patientItems.get(0));

        ServiceResponse<List<PatientEncounterItem>> patientEncountersServiceResponse = searchService.findPatientEncounterItemsById(patientItems.get(0).getId());
        if (patientEncountersServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<PatientEncounterItem> patientEncounterItems = patientEncountersServiceResponse.getResponseObject();
        if (patientEncounterItems == null || patientEncounterItems.size() < 1) {
            //return ok(showError.render(currentUser));
            //return an error near the search box
        }
        viewModel.setPatientEncounterItems(patientEncounterItems);


        return ok(indexPatient.render(currentUser, error, viewModel, patientEncounterItems));
    }

    public Result indexEncounterGet(int encounterId) {
        EncounterViewModel viewModel = new EncounterViewModel();
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        return ok(indexEncounter.render(currentUser, viewModel));
    }

}
