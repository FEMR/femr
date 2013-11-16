package femr.ui.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IVital;
import femr.ui.models.triage.CreateViewModelGet;
import femr.ui.models.triage.CreateViewModelPost;
import femr.ui.helpers.controller.TriageHelper;
import femr.ui.views.html.triage.index;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;

public class TriageController extends Controller {

    private final Form<CreateViewModelPost> createViewModelForm = Form.form(CreateViewModelPost.class);
    private ITriageService triageService;
    private ISessionService sessionService;
    private Provider<IPatient> patientProvider;
    private ISearchService searchService;
    private TriageHelper triageHelper;


    @Inject
    public TriageController(ITriageService triageService, ISessionService sessionService, ISearchService searchService, Provider<IPatient> patientProvider, TriageHelper triageHelper) {

        this.triageService = triageService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.patientProvider = patientProvider;
        this.triageHelper = triageHelper;
    }

    public Result createGet() {

        CurrentUser currentUser = sessionService.getCurrentUserSession();

        CreateViewModelGet viewModelGet = new CreateViewModelGet();

        ServiceResponse<List<? extends IVital>> vitalServiceResponse = searchService.findAllVitals();
        if (vitalServiceResponse.hasErrors()) {
            //error
            //goto 404?
        }
        List<? extends IVital> vitalNames = vitalServiceResponse.getResponseObject();

        IPatient patient = patientProvider.get();
        patient.setId(0);
        return ok(index.render(currentUser, vitalNames, patient, viewModelGet));
    }

    /*
    *if id is 0 then it is a new patient
    * if id is > 0 then it is a new encounter
     */
    public Result createPost(int id) {

        CreateViewModelPost viewModel = createViewModelForm.bindFromRequest().get();

        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<IPatient> patientServiceResponse;
        if (id == 0) {
            IPatient patient = triageHelper.createPatient(viewModel, currentUser);
            patientServiceResponse = triageService.createPatient(patient);
        } else {
            patientServiceResponse = searchService.findPatientById(id);
        }

        if (patientServiceResponse.hasErrors()) {
            //error
            //goto 404 page
        }

        IPatientEncounter patientEncounter = triageHelper.createPatientEncounter(viewModel, currentUser, patientServiceResponse.getResponseObject());
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = triageService.createPatientEncounter(patientEncounter);
        if (patientEncounterServiceResponse.hasErrors()){
            //error
            //goto 404 page
        }

        List<IPatientEncounterVital> patientEncounterVitals = triageHelper.createVitals(viewModel, currentUser, patientEncounterServiceResponse.getResponseObject());

        for (int i = 0; i < patientEncounterVitals.size(); i++) {
            if (patientEncounterVitals.get(i).getVitalValue() > 0) {
                triageService.createPatientEncounterVital(patientEncounterVitals.get(i));
            }
        }

        return redirect("/show?id=" + patientServiceResponse.getResponseObject().getId());
    }

    /*
    Used when user is creating an encounter for an existing patient.
     */
    public Result createPopulatedGet() {

        String s_id = request().getQueryString("id");

        CreateViewModelGet viewModelGet = new CreateViewModelGet();

        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<List<? extends IVital>> vitalServiceResponse = searchService.findAllVitals();
        if (vitalServiceResponse.hasErrors()) {
            //error
            //should goto 404
        }
        List<? extends IVital> vitalNames = vitalServiceResponse.getResponseObject();



        if (StringUtils.isNullOrWhiteSpace(s_id)) {
            viewModelGet.setSearchError(true);
            return ok(index.render(currentUser, vitalNames, patientProvider.get(), viewModelGet));
        }
        s_id = s_id.trim();
        Integer id = Integer.parseInt(s_id);
        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);

        if (patientServiceResponse.hasErrors()) {
            viewModelGet.setSearchError(true);
            return ok(index.render(currentUser, vitalNames, patientProvider.get(), viewModelGet));
        } else {
            IPatient patient = patientServiceResponse.getResponseObject();
            viewModelGet = triageHelper.populateViewModelGet(patientServiceResponse.getResponseObject());

            return ok(index.render(currentUser, vitalNames, patient, viewModelGet));
        }
    }

}
