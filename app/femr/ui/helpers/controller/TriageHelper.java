package femr.ui.helpers.controller;

import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.common.models.IPatient;
import femr.ui.models.triage.CreateViewModelPost;

public class TriageHelper {
    private Provider<IPatient> patientProvider;

    public TriageHelper(Provider<IPatient> patientProvider){
        this.patientProvider = patientProvider;
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
}
