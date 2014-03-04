package femr.ui.helpers.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.common.models.*;
import femr.data.models.Vital;
import femr.ui.controllers.routes;
import femr.ui.models.triage.CreateViewModelGet;
import femr.ui.models.triage.CreateViewModelPost;
import femr.util.calculations.dateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TriageHelper {
    private Provider<IPatient> patientProvider;
    private Provider<IPatientEncounter> patientEncounterProvider;
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private Provider<IPhoto> patientPhotoProvider;

    @Inject
    public TriageHelper(Provider<IPatient> patientProvider, Provider<IPatientEncounter> patientEncounterProvider,
                        Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                        Provider<IPhoto> patientPhotoService) {
        this.patientProvider = patientProvider;
        this.patientEncounterProvider = patientEncounterProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.patientPhotoProvider = patientPhotoService;
    }

    public CreateViewModelGet populateViewModelGet(IPatient patient, IPhoto patientPhoto, List<? extends IVital> vitalNames, boolean searchError) {
        CreateViewModelGet createViewModelGet = new CreateViewModelGet();
        createViewModelGet.setVitalNames(vitalNames);
        createViewModelGet.setSearchError(searchError);

        if (patient != null) {
            createViewModelGet.setId(patient.getId());
            createViewModelGet.setFirstName(patient.getFirstName());
            createViewModelGet.setLastName(patient.getLastName());
            createViewModelGet.setAddress(patient.getAddress());
            createViewModelGet.setCity(patient.getCity());
            createViewModelGet.setAge(dateUtils.getAge(patient.getAge()));
            createViewModelGet.setBirth(patient.getAge());
            createViewModelGet.setSex(patient.getSex());
            if(patientPhoto != null)
            {
                String photoPath = routes.PhotoController.GetPatientPhoto(patient.getId()).toString();
                createViewModelGet.setPhotoPath(photoPath);
            }
            else
            {
                createViewModelGet.setPhotoPath("");
            }

        } else {
            createViewModelGet.setId(0);
            //required to keep textbox clear
            createViewModelGet.setAge(null);
        }
        return createViewModelGet;
    }

    public IPatient getPatient(CreateViewModelPost viewModelPost, CurrentUser currentUser) {
        IPatient patient = patientProvider.get();
        patient.setUserId(currentUser.getId());
        patient.setFirstName(viewModelPost.getFirstName());
        patient.setLastName(viewModelPost.getLastName());
        patient.setAge(viewModelPost.getAge());
        patient.setSex(viewModelPost.getSex());
        patient.setAddress(viewModelPost.getAddress());
        patient.setCity(viewModelPost.getCity());

        return patient;
    }

    public IPatientEncounter getPatientEncounter(CreateViewModelPost viewModelPost, CurrentUser currentUser, IPatient patient) {
        IPatientEncounter patientEncounter = patientEncounterProvider.get();
        patientEncounter.setPatientId(patient.getId());
        patientEncounter.setUserId(currentUser.getId());
        patientEncounter.setDateOfVisit(dateUtils.getCurrentDateTimeString());
        patientEncounter.setChiefComplaint(viewModelPost.getChiefComplaint());
        patientEncounter.setWeeksPregnant(viewModelPost.getWeeksPregnant());
        patientEncounter.setIsPregnant(viewModelPost.getIsPregnant());
        return patientEncounter;
    }

    public IPatientEncounterVital getPatientEncounterVital(int userId, int patientEncounterId, IVital vital, float vitalValue){
        IPatientEncounterVital patientEncounterVital = patientEncounterVitalProvider.get();
        patientEncounterVital.setUserId(userId);
        patientEncounterVital.setPatientEncounterId(patientEncounterId);
        patientEncounterVital.setVital(vital);
        patientEncounterVital.setVitalValue(vitalValue);
        patientEncounterVital.setDateTaken(dateUtils.getCurrentDateTimeString());
        return patientEncounterVital;
    }
}
