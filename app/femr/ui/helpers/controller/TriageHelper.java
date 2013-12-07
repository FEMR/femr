package femr.ui.helpers.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IVital;
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

    @Inject
    public TriageHelper(Provider<IPatient> patientProvider, Provider<IPatientEncounter> patientEncounterProvider, Provider<IPatientEncounterVital> patientEncounterVitalProvider) {
        this.patientProvider = patientProvider;
        this.patientEncounterProvider = patientEncounterProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
    }

    public CreateViewModelGet populateViewModelGet(IPatient patient, List<? extends IVital> vitalNames, boolean searchError) {
        CreateViewModelGet createViewModelGet = new CreateViewModelGet();
        createViewModelGet.setVitalNames(vitalNames);
        createViewModelGet.setSearchError(searchError);

        if (patient != null) {
            createViewModelGet.setId(patient.getId());
            createViewModelGet.setFirstName(patient.getFirstName());
            createViewModelGet.setLastName(patient.getLastName());
            createViewModelGet.setAddress(patient.getAddress());
            createViewModelGet.setCity(patient.getCity());
            createViewModelGet.setAge(dateUtils.calculateYears(patient.getAge()));
            createViewModelGet.setBirth(patient.getAge());
            createViewModelGet.setSex(patient.getSex());
        } else {
            createViewModelGet.setId(0);
            //required to keep textbox clear
            createViewModelGet.setAge(null);
        }
        return createViewModelGet;
    }

    public IPatient populatePatient(CreateViewModelPost viewModelPost, CurrentUser currentUser) {
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

    public IPatientEncounter populatePatientEncounter(CreateViewModelPost viewModelPost, CurrentUser currentUser, IPatient patient) {
        IPatientEncounter patientEncounter = patientEncounterProvider.get();
        patientEncounter.setPatientId(patient.getId());
        patientEncounter.setUserId(currentUser.getId());
        patientEncounter.setDateOfVisit(dateUtils.getCurrentDateTimeString());
        patientEncounter.setChiefComplaint(viewModelPost.getChiefComplaint());
        patientEncounter.setWeeksPregnant(viewModelPost.getWeeksPregnant());
        patientEncounter.setIsPregnant(viewModelPost.getIsPregnant());

        return patientEncounter;
    }

    public List<IPatientEncounterVital> populateVitals(CreateViewModelPost viewModelPost, CurrentUser currentUser, IPatientEncounter patientEncounter) {

        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();

        int NUMBER_OF_VITALS = 9;
        IPatientEncounterVital[] patientEncounterVital = new IPatientEncounterVital[NUMBER_OF_VITALS];
        for (int i = 0; i < NUMBER_OF_VITALS; i++) {
            patientEncounterVital[i] = patientEncounterVitalProvider.get();
            patientEncounterVital[i].setDateTaken((dateUtils.getCurrentDateTimeString()));
            patientEncounterVital[i].setUserId(currentUser.getId());
            patientEncounterVital[i].setPatientEncounterId(patientEncounter.getId());
            patientEncounterVital[i].setVitalId(i + 1);
        }

        //Respiratory Rate
        if (viewModelPost.getRespiratoryRate() == null) {
            patientEncounterVital[0].setVitalValue(-1);
        } else {
            patientEncounterVital[0].setVitalValue(viewModelPost.getRespiratoryRate().floatValue());
        }

        //Heart Rate
        if (viewModelPost.getHeartRate() == null) {
            patientEncounterVital[1].setVitalValue(-1);
        } else {
            patientEncounterVital[1].setVitalValue(viewModelPost.getHeartRate().floatValue());
        }

        //Temperature
        if (viewModelPost.getTemperature() == null) {
            patientEncounterVital[2].setVitalValue(-1);
        } else {
            patientEncounterVital[2].setVitalValue(viewModelPost.getTemperature().floatValue());
        }

        //Oxygen Saturation
        if (viewModelPost.getOxygenSaturation() == null) {
            patientEncounterVital[3].setVitalValue(-1);
        } else {
            patientEncounterVital[3].setVitalValue(viewModelPost.getOxygenSaturation().floatValue());
        }

        //Height - Feet
        if (viewModelPost.getHeightFeet() == null) {
            patientEncounterVital[4].setVitalValue(-1);
        } else {
            patientEncounterVital[4].setVitalValue(viewModelPost.getHeightFeet().floatValue());
        }

        //Height - Inches
        if (viewModelPost.getHeightInches() == null) {
            //if HeightFeet is set and HeightInches is not, make HeightInches 0
            if (patientEncounterVital[4].getVitalValue() > -1) {
                patientEncounterVital[5].setVitalValue(0);
            } else {
                patientEncounterVital[5].setVitalValue(-1);
            }
        } else {
            patientEncounterVital[5].setVitalValue(viewModelPost.getHeightInches().floatValue());
        }

        //Weight
        if (viewModelPost.getWeight() == null) {
            patientEncounterVital[6].setVitalValue(-1);
        } else {
            patientEncounterVital[6].setVitalValue(viewModelPost.getWeight().floatValue());
        }

        //Blood Pressure - Systolic
        if (viewModelPost.getBloodPressureSystolic() == null) {
            patientEncounterVital[7].setVitalValue(-1);
        } else {
            patientEncounterVital[7].setVitalValue(viewModelPost.getBloodPressureSystolic().floatValue());
        }

        //Blood Pressure - Diastolic
        if (viewModelPost.getBloodPressureDiastolic() == null) {
            patientEncounterVital[8].setVitalValue(-1);
        } else {
            patientEncounterVital[8].setVitalValue(viewModelPost.getBloodPressureDiastolic().floatValue());
        }

        //Glucose
        if (viewModelPost.getGlucose() == null) {
            patientEncounterVital[9].setVitalValue(-1);
        } else {
            patientEncounterVital[9].setVitalValue(viewModelPost.getGlucose().floatValue());
        }

        patientEncounterVitals.addAll(Arrays.asList(patientEncounterVital));
        return patientEncounterVitals;
    }
}
