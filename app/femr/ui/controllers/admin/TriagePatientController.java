package femr.ui.controllers.admin;

import com.google.inject.Inject;
import femr.business.services.core.IPatientService;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.configure.IndexViewModelGet;
import femr.ui.models.admin.users.ManageViewModelGet;
import femr.ui.models.sessions.CreateViewModel;
import femr.ui.models.triage.IndexViewModelPost;
import femr.ui.views.html.admin.triagePatients.triagePatient;
import org.apache.commons.lang3.time.DateUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fq251 on 10/20/2016.
 */

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE, Roles.ADMINISTRATOR})
public class TriagePatientController extends Controller {

    private final ISessionService sessionService;
    private final IPatientService patientService;
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);

   @Inject
    public TriagePatientController(ISessionService sessionService, IPatientService patientService){
        this.sessionService = sessionService;
       this.patientService = patientService;
    }
//    CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
//
//    ServiceResponse<List<UserItem>> userServiceResponse = userService.retrieveAllUsers();
//        if (userServiceResponse.hasErrors()) {
//        throw new RuntimeException();
//    }

//
//        return ok(manage.render(currentUser, viewModelGet));

    public  Result triagePatient()
    {

        IndexViewModelPost viewModel = new IndexViewModelPost();
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        PatientEncounterItem patientEncounterItem= new PatientEncounterItem();
        PatientItem patientItem=new PatientItem();
      //  ManageViewModelGet viewModelGet = new ManageViewModelGet();
       // viewModelGet.setUsers(userServiceResponse.getResponseObject());
        ServiceResponse<List<PatientItem>> patientServiceResponse = patientService.retrieveCurrentTriagePatients();
        ManageViewModelGet viewModelGet = new ManageViewModelGet();
        viewModel.setTriagePatients(patientServiceResponse.getResponseObject());


        return ok(triagePatient.render(currentUser,viewModel));
    }




//    public ServiceResponse<List<PatientItem>> retrieveCurrentTriagePatients{)
//
//        ServiceResponse<List<PatientItem>> response = new ServiceResponse<>();
//        List<PatientItem> patientItems = new ArrayList<>();
//        ExpressionList<Patient> query = QueryProvider.getPatientQuery()
//                .where()
//                .eq("date_Of_triage_visit", DateTime.now());
//
//        try{
//
//            List<? extends IPatient> patient = patientRepository.find(query);
//
//            for (IPatient patient1 : patient) {
//
//                patientItems.add(itemModelMapper.createPatientItem(patient1.getId(),patient1.getFirstName(),patient1.getLastName(),null,null,patient1.getUserId(),
//                        null,null,null,null,null,null,null,null,null));
//            }
//
//            response.setResponseObject(patientItems);
//
//        } catch (Exception ex) {
//
//            response.addError("", ex.getMessage());
//        }
//
//        return response;
//    }
    //public static String findPatientFirstName(IRepository
}
