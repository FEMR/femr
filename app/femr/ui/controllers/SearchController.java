package femr.ui.controllers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import femr.business.services.core.IMedicationService;
import femr.business.services.core.ISearchService;
import femr.business.services.core.ISessionService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.search.json.PatientSearch;
import org.h2.util.StringUtils;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

//The purpose of this controller is to provide a universal
//way of handling search requests - all requests will be
//redirected to another controller. Also, typeahead for now
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class SearchController extends Controller {

    private ISessionService sessionService;
    private ISearchService searchService;
    private IMedicationService medicationService;

    @Inject
    public SearchController(ISessionService sessionService,
                            ISearchService searchService,
                            IMedicationService medicationService) {

        this.sessionService = sessionService;
        this.searchService = searchService;
        this.medicationService = medicationService;
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
        } else if (patientItems.size() > 1) {
            return redirect(routes.HistoryController.indexPatientGet(patientSearchQuery.replace(" ", "-")));
        } else if (patientItems.size() == 0) {
            //if the patient ends up not being found, go back to the index of the page where the search was called from.
            //AJAX should prevent this from happening.
            if (StringUtils.equals(page, "medical")) {
                return redirect(routes.MedicalController.indexGet());
            } else if (StringUtils.equals(page, "pharmacy")) {
                return redirect(routes.PharmaciesController.indexGet());
            } else if (StringUtils.equals(page, "triage")) {
                return redirect(routes.TriageController.indexGet());
            } else if (StringUtils.equals(page, "history")) {
                return redirect(routes.HistoryController.indexPatientGet(patientSearchQuery.replace(" ", "-")));
            }
        }

        throw new RuntimeException();
    }

    /**
     * AJAX call to check if a patient actually exists before submission of a search
     *
     * @param query
     * @return
     */
    public Result doesPatientExist(String query){

        ServiceResponse<List<PatientItem>> patientResponse = searchService.getPatientsFromQueryString(query);
        if (patientResponse.hasErrors() || patientResponse.getResponseObject().size() == 0) {
            return ok("false");
        }
        return ok("true");
    }

    public Result typeaheadPatientsJSONGet(){

        ServiceResponse<List<PatientItem>> patientItemsServiceResponse = searchService.findPatientsForSearch();

        if (patientItemsServiceResponse.hasErrors()){
            return ok("");
        }
        List<PatientItem> patientItems = patientItemsServiceResponse.getResponseObject();
        List<PatientSearch> patientSearches = new ArrayList<>();
        PatientSearch patientSearch;

        for (PatientItem patientItem : patientItems) {
            patientSearch = new PatientSearch();
            patientSearch.setId(Integer.toString(patientItem.getId()));
            patientSearch.setFirstName(patientItem.getFirstName());
            patientSearch.setLastName(patientItem.getLastName());
            if (patientItem.getAge() != null)
                patientSearch.setAge(patientItem.getAge());
            if (patientItem.getPathToPhoto() != null)
                patientSearch.setPhoto(patientItem.getPathToPhoto());
            patientSearches.add(patientSearch);
        }

        return ok(new Gson().toJson(patientSearches));
    }

    public Result typeaheadDiagnosisJSONGet(){

        ServiceResponse<List<String>> allDiagnosesServiceResponse = searchService.findDiagnosisForSearch();
        if (allDiagnosesServiceResponse.hasErrors())
            return ok("");

        return ok(new Gson().toJson(allDiagnosesServiceResponse.getResponseObject()));
    }

    /**
     * Used for typeahead in replacement prescription boxes
     * Called via ajax
     *
     * @return JSON object of medications that exist in the medications table
     */
    public Result typeaheadMedicationsJSONGet() {

        ServiceResponse<List<String>> medicationServiceResponse = medicationService.findAllMedications();
        if (medicationServiceResponse.hasErrors()) {
            return ok("");
        }

        return ok(new Gson().toJson(medicationServiceResponse.getResponseObject()));
    }
}
