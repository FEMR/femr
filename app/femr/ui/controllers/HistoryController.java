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
import femr.ui.views.html.history.showError;

import play.mvc.Controller;
import play.mvc.Result;

import femr.util.stringhelpers.StringUtils;
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

    public Result indexPatientGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        boolean error = false;
        String firstName = request().getQueryString("searchFirstName");
        String lastName = request().getQueryString("searchLastName");
        String s_id = request().getQueryString("id");
        IndexPatientViewModelGet viewModel = new IndexPatientViewModelGet();

        Integer patientId = null;
        if (StringUtils.isNotNullOrWhiteSpace(s_id)) {
            patientId = Integer.parseInt(s_id);
        }

        ServiceResponse<List<PatientItem>> patientResponse = searchService.getPatientsFromQueryString(firstName, lastName, patientId);
        if (patientResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<PatientItem> patientItems = patientResponse.getResponseObject();
        if (patientItems == null || patientItems.size() < 1) {
            return ok(showError.render(currentUser));
        }

        if (patientItems.size() > 0){
            for (PatientItem patientItem : patientItems)
                patientItem.setPathToPhoto(routes.PhotoController.GetPatientPhoto(patientItem.getId(), true).toString());
            viewModel.setPatientItems(patientItems);
            viewModel.setPatientItem(patientItems.get(0));
        }

        ServiceResponse<List<PatientEncounterItem>> patientEncountersServiceResponse = searchService.findPatientEncounterItemsById(patientItems.get(0).getId());
        if (patientEncountersServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<PatientEncounterItem> patientEncounterItems = patientEncountersServiceResponse.getResponseObject();
        if (patientEncounterItems == null || patientEncounterItems.size() < 1) {
            return ok(showError.render(currentUser));
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
