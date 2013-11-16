package femr.ui.helpers.controller;

import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.ui.models.triage.CreateViewModelPost;
import femr.util.calculations.dateUtils;

public class TriageHelper {
    private Provider<IPatient> patientProvider;
    private Provider<IPatientEncounter> patientEncounterProvider;

    public TriageHelper(Provider<IPatient> patientProvider, Provider<IPatientEncounter> patientEncounterProvider){
        this.patientProvider = patientProvider;
        this.patientEncounterProvider = patientEncounterProvider;
    }

    public IPatient createPatient(CreateViewModelPost viewModelPost, CurrentUser currentUser){
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

    public IPatientEncounter createPatientEncounter(CreateViewModelPost viewModelPost, CurrentUser currentUser, IPatient patient){
        IPatientEncounter patientEncounter = patientEncounterProvider.get();
        patientEncounter.setPatientId(patient.getId());
        patientEncounter.setUserId(currentUser.getId());
        patientEncounter.setDateOfVisit(dateUtils.getCurrentDateTime());
        patientEncounter.setChiefComplaint(viewModelPost.getChiefComplaint());
        patientEncounter.setWeeksPregnant(viewModelPost.getWeeksPregnant());
        patientEncounter.setIsPregnant(viewModelPost.getIsPregnant());

        return patientEncounter;
    }
}
