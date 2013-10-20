package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.common.models.IPatient;
import femr.common.models.IPatientPrescription;
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
    private IPatientPrescription pharmacyService;
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);


    @Inject
    public PharmaciesController(IPatientPrescription pharmacyService,
                                ISessionService sessionService,
                                ISearchService searchService) {
        this.pharmacyService = pharmacyService;
        this.sessionService = sessionService;
        this.searchService = searchService;
    }

    public Result createGet(){
        String s_patientID = request().getQueryString("searchId");
        int i_patientID = Integer.parseInt(s_patientID);

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(i_patientID);

        CreateViewModel viewModel = new CreateViewModel();
        IPatient patient = patientServiceResponse.getResponseObject();

        viewModel.setFirstName(patient.getFirstName());
        viewModel.setLastName(patient.getLastName());


        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        return ok(populated.render(currentUserSession, viewModel));
    }

    public Result index() {
        return ok(index.render());
    }
}
