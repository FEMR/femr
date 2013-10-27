package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.services.IPharmacyService;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IPatientPrescription;
import femr.business.services.SessionService;
import femr.ui.models.pharmacy.CreateViewModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.pharmacies.index;
import femr.ui.views.html.pharmacies.populated;

import java.util.List;

public class PharmaciesController extends Controller {
    private ISessionService sessionService;
    private ISearchService searchService;
    private ITriageService triageService;
    private IPharmacyService pharmacyService;

    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);


    @Inject
    public PharmaciesController(IPharmacyService pharmacyService,
                                ITriageService triageService,
                                ISessionService sessionService,
                                ISearchService searchService) {
        this.pharmacyService = pharmacyService;
        this.triageService = triageService;
        this.sessionService = sessionService;
        this.searchService = searchService;
    }

    public Result index() {

        boolean error = false;
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        return ok(index.render(currentUserSession,error));
    }

    public Result createGet(){

        String s_id = request().getQueryString("id");
        Integer id = Integer.parseInt(s_id);

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(id);
        IPatient patient = patientServiceResponse.getResponseObject();
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        return ok(populated.render(currentUserSession, patient, patientEncounter));
    }

}
