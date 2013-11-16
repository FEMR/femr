package femr.ui.helpers.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.ui.models.triage.CreateViewModelPost;
import femr.util.calculations.dateUtils;

import java.util.ArrayList;
import java.util.List;

public class TriageHelper {
    private Provider<IPatient> patientProvider;
    private Provider<IPatientEncounter> patientEncounterProvider;

    @Inject
    public TriageHelper(Provider<IPatient> patientProvider, Provider<IPatientEncounter> patientEncounterProvider){
        this.patientProvider = patientProvider;
        this.patientEncounterProvider = patientEncounterProvider;
    }

    public IPatient createPatient(CreateViewModelPost viewModelPost, CurrentUser currentUser){
        IPatient patient = patientProvider.get();
//        IPatient patient = new Patient();
        patient.setUserId(currentUser.getId());
        patient.setFirstName(viewModelPost.getFirstName());
        patient.setLastName(viewModelPost.getLastName());
        patient.setAge(viewModelPost.getAge());
        patient.setSex(viewModelPost.getSex());
        patient.setAddress(viewModelPost.getAddress());
        patient.setCity(viewModelPost.getCity());
        return patient;
    }

    public IPatientEncounter createPatientEncounter(CreateViewModelPost viewModelPost, CurrentUser currentUser, IPatient patient){
        IPatientEncounter patientEncounter = patientEncounterProvider.get();
//        IPatientEncounter patientEncounter = new PatientEncounter();
        patientEncounter.setPatientId(patient.getId());
        patientEncounter.setUserId(currentUser.getId());
        patientEncounter.setDateOfVisit(dateUtils.getCurrentDateTime());
        patientEncounter.setChiefComplaint(viewModelPost.getChiefComplaint());
        patientEncounter.setWeeksPregnant(viewModelPost.getWeeksPregnant());
        patientEncounter.setIsPregnant(viewModelPost.getIsPregnant());

        return patientEncounter;
    }

//    public List<IPatientEncounterVital> createVitals(CreateViewModelPost viewModelPost, CurrentUser currentUser, IPatientEncounter patientEncounter){
//
//        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();
//
//    }
}
