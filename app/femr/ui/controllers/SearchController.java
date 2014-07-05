package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.common.dto.ServiceResponse;
import femr.common.models.PatientItem;
import femr.data.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import org.h2.util.StringUtils;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

//The purpose of this controller is to provide a universal
//way of handling search requests - all requests will be
//redirected to another controller
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class SearchController extends Controller {
    private ISessionService sessionService;
    private ISearchService searchService;

    @Inject
    public SearchController(ISessionService sessionService,
                             ISearchService searchService) {
        this.sessionService = sessionService;
        this.searchService = searchService;
    }

    public Result handleSearch(String page) {

        String patientSearchQuery = request().getQueryString("patientSearchQuery");

        ServiceResponse<List<PatientItem>> patientResponse = searchService.getPatientsFromQueryString(patientSearchQuery);
        if (patientResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<PatientItem> patientItems = patientResponse.getResponseObject();

        if (patientItems.size() == 1) {
            PatientItem patientItem = patientItems.get(0);
            if (StringUtils.equals(page, "medical")) {
                return redirect(routes.MedicalController.editGet(patientItem.getId()));
            } else if (StringUtils.equals(page, "pharmacy")) {
                return redirect(routes.PharmaciesController.editGet(patientItem.getId()));
            } else if (StringUtils.equals(page, "triage")) {
                return redirect(routes.TriageController.indexPopulatedGet(patientItem.getId()));
            } else if (StringUtils.equals(page, "history")) {
                return redirect(routes.HistoryController.indexPatientGet(Integer.toString(patientItem.getId())));
            }
        }else if(patientItems.size() > 1){
            return redirect(routes.HistoryController.indexPatientGet(patientSearchQuery.replace(" ", "-")));
        }

        throw new RuntimeException();
    }


}
