package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.ui.models.triage.IndexViewModelGet;
import femr.ui.views.html.sessions.create;
import femr.ui.views.html.trauma.index;
import play.mvc.*;

import java.util.List;
import java.util.Map;

public class TraumaController extends Controller {

    private final AssetsFinder assetsFinder;
    private ISessionService sessionService;
    private final IVitalService vitalService;
    private final ISearchService searchService;
    private final IPatientService patientService;

    @Inject
    public TraumaController(AssetsFinder assetsFinder, ISessionService sessionService, IVitalService vitalService, ISearchService searchService, IPatientService patientService) {

        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
        this.vitalService = vitalService;
        this.searchService = searchService;
        this.patientService = patientService;
    }

    public Result index() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        if (currentUser != null) {
            //retrieve all the vitals in the database so we can dynamically name
            //the vitals in the view
            ServiceResponse<List<VitalItem>> vitalServiceResponse = vitalService.retrieveAllVitalItems();
            if (vitalServiceResponse.hasErrors()) {
                throw new RuntimeException();
            }

            //initialize an empty patient
            PatientItem patientItem = new PatientItem();

            //get settings
            ServiceResponse<SettingItem> settingItemServiceResponse = searchService.retrieveSystemSettings();
            if (settingItemServiceResponse.hasErrors()) {
                throw new RuntimeException();
            }

            //get age classifications
            ServiceResponse<Map<String, String>> patientAgeClassificationsResponse = patientService.retrieveAgeClassifications();
            if (patientAgeClassificationsResponse.hasErrors()) {
                throw new RuntimeException();
            }

            IndexViewModelGet viewModelGet = new IndexViewModelGet();
            viewModelGet.setVitalNames(vitalServiceResponse.getResponseObject());
            viewModelGet.setPatient(patientItem);
            viewModelGet.setSearchError(false);
            viewModelGet.setSettings(settingItemServiceResponse.getResponseObject());
            viewModelGet.setPossibleAgeClassifications(patientAgeClassificationsResponse.getResponseObject());

            return ok(index.render(currentUser, viewModelGet, assetsFinder));
        }
        return ok(create.render(null, 0, assetsFinder));
    }
}
