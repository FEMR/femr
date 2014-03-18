package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IPhotoService;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.*;
import femr.data.models.Photo;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.triage.CreateViewModelGet;
import femr.ui.models.triage.CreateViewModelPost;
import femr.ui.helpers.controller.TriageHelper;
import femr.ui.views.html.triage.index;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.*;

import java.io.File;
import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class TriageController extends Controller {

    private final Form<CreateViewModelPost> createViewModelForm = Form.form(CreateViewModelPost.class);
    private ITriageService triageService;
    private ISessionService sessionService;
    private ISearchService searchService;
    private TriageHelper triageHelper;
    private IPhotoService photoService;


    @Inject
    public TriageController(ITriageService triageService,
                            ISessionService sessionService,
                            ISearchService searchService,
                            TriageHelper triageHelper,
                            IPhotoService photoService) {

        this.triageService = triageService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.triageHelper = triageHelper;
        this.photoService = photoService;
    }

    public Result createGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<List<? extends IVital>> vitalServiceResponse = searchService.findAllVitals();
        if (vitalServiceResponse.hasErrors()) {
            return internalServerError();
        }

        CreateViewModelGet viewModelGet = triageHelper.populateViewModelGet(null, null, vitalServiceResponse.getResponseObject(), false);

        return ok(index.render(currentUser, viewModelGet));
    }

    /*
    Used when user has searched for an existing patient
    and wants to create a new encounter
     */
    public Result createPopulatedGet() {
        boolean searchError = false;

        CurrentUser currentUser = sessionService.getCurrentUserSession();

        IPatient patient = null;
        IPhoto   patientPhoto = null;

        //retrieve patient id from query string
        String s_id = request().getQueryString("id");
        if (StringUtils.isNullOrWhiteSpace(s_id)) {
            searchError = true;
        } else {
            s_id = s_id.trim();
            Integer id = Integer.parseInt(s_id);
            ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);
            if (patientServiceResponse.hasErrors()) {
                searchError = true;
            } else {
                patient = patientServiceResponse.getResponseObject();
            }
        }

        if(patient != null)
            if(patient.getPhotoId() != null)
            {
                //fetch photo record:
                ServiceResponse<IPhoto> photoResponse = photoService.getPhotoById(patient.getPhotoId());
                if(!photoResponse.hasErrors())
                    patientPhoto = photoResponse.getResponseObject();
            }

        //retrieve vitals names for dynamic html element naming
        ServiceResponse<List<? extends IVital>> vitalServiceResponse = searchService.findAllVitals();
        if (vitalServiceResponse.hasErrors()) {
            return internalServerError();
        }

        CreateViewModelGet viewModelGet = triageHelper.populateViewModelGet(patient, patientPhoto, vitalServiceResponse.getResponseObject(), searchError);

        return ok(index.render(currentUser, viewModelGet));
    }

    /*
   *if id is 0 then it is a new patient and a new encounter
   * if id is > 0 then it is only a new encounter
    */
    public Result createPost(int id) {
        CreateViewModelPost viewModel = createViewModelForm.bindFromRequest().get();
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        //save new patient if new form
        //else find the patient to create a new encounter for
        ServiceResponse<IPatient> patientServiceResponse;
        IPatient patient;
        if (id == 0) {
            patient = triageHelper.getPatient(viewModel, currentUser);
            patientServiceResponse = triageService.createPatient(patient);
        } else {
            patientServiceResponse = searchService.findPatientById(id);
            patient = patientServiceResponse.getResponseObject();
            if (!StringUtils.isNullOrWhiteSpace(viewModel.getSex())) {
                patient.setSex(viewModel.getSex());
                patientServiceResponse = triageService.updatePatient(patient);
            }
        }
        if (patientServiceResponse.hasErrors()) {
            return internalServerError();
        }

        //Handle photo logic:
        try
        {
            Http.MultipartFormData.FilePart fpPhoto;
            File photoFile = null;
            fpPhoto = request().body().asMultipartFormData().getFile("patientPhoto");
            if(fpPhoto != null)
                photoFile = fpPhoto.getFile();

            photoService.HandlePatientPhoto(photoFile,
                    patientServiceResponse.getResponseObject(),
                    viewModel.getImageCoords(), viewModel.getDeletePhoto());
        }
        catch(Exception ex)
        {
        }

        //create and save a new encounter
        IPatientEncounter patientEncounter = triageHelper.getPatientEncounter(viewModel, currentUser, patientServiceResponse.getResponseObject());
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = triageService.createPatientEncounter(patientEncounter);
        if (patientEncounterServiceResponse.hasErrors()) {
            return internalServerError();
        }

        //create and save vitals in new encounter
        int success = 1;
        if (viewModel.getRespiratoryRate() != null && viewModel.getRespiratoryRate() > 0) {
            success = savePatientEncounterVital("respiratoryRate", viewModel.getRespiratoryRate(), currentUser.getId(), patientEncounter.getId());
        }
        if (viewModel.getHeartRate() != null && viewModel.getHeartRate() > 0) {
            success = savePatientEncounterVital("heartRate", viewModel.getHeartRate(), currentUser.getId(), patientEncounter.getId());
        }
        if (viewModel.getTemperature() != null && viewModel.getTemperature() > 0) {
            success = savePatientEncounterVital("temperature", viewModel.getTemperature(), currentUser.getId(), patientEncounter.getId());
        }
        if (viewModel.getOxygenSaturation() != null && viewModel.getOxygenSaturation() > 0) {
            success = savePatientEncounterVital("oxygenSaturation", viewModel.getOxygenSaturation(), currentUser.getId(), patientEncounter.getId());
        }
        if (viewModel.getHeightFeet() != null && viewModel.getHeightFeet() > 0) {
            success = savePatientEncounterVital("heightFeet", viewModel.getHeightFeet(), currentUser.getId(), patientEncounter.getId());
        }
        if (viewModel.getHeightInches() != null && viewModel.getHeightInches() > 0) {
            success = savePatientEncounterVital("heightInches", viewModel.getHeightInches(), currentUser.getId(), patientEncounter.getId());
        }
        if (viewModel.getWeight() != null && viewModel.getWeight() > 0) {
            success = savePatientEncounterVital("weight", viewModel.getWeight(), currentUser.getId(), patientEncounter.getId());
        }
        if (viewModel.getBloodPressureSystolic() != null && viewModel.getBloodPressureSystolic() > 0) {
            success = savePatientEncounterVital("bloodPressureSystolic", viewModel.getBloodPressureSystolic(), currentUser.getId(), patientEncounter.getId());
        }
        if (viewModel.getBloodPressureDiastolic() != null && viewModel.getBloodPressureDiastolic() > 0) {
            success = savePatientEncounterVital("bloodPressureDiastolic", viewModel.getBloodPressureDiastolic(), currentUser.getId(), patientEncounter.getId());
        }
        if (viewModel.getGlucose() != null && viewModel.getGlucose() > 0) {
            success = savePatientEncounterVital("glucose", viewModel.getGlucose(), currentUser.getId(), patientEncounter.getId());
        }
        if (success == 0){
            return internalServerError();
        }

        return redirect("/show?id=" + patientServiceResponse.getResponseObject().getId());
    }

    //returns 1 on success, 0 on failure
    private int savePatientEncounterVital(String name, float vitalValue, int userId, int patientEncounterId){
        ServiceResponse<IVital> vitalServiceResponse = searchService.findVital(name);
        if (vitalServiceResponse.hasErrors()){
            return 0;
        }
        IVital vital = vitalServiceResponse.getResponseObject();
        IPatientEncounterVital patientEncounterVital = triageHelper.getPatientEncounterVital(userId, patientEncounterId, vital, vitalValue);
        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse = triageService.createPatientEncounterVital(patientEncounterVital);
        if (patientEncounterVitalServiceResponse.hasErrors()){
            return 0;
        }
        return 1;
    }



}
