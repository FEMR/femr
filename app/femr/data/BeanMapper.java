/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.data;

import com.google.inject.Inject;
import femr.data.models.core.*;

import javax.inject.Provider;

/**
 * Responsible for creating model objects (data/models).
 * Only visible to data & service layer.
 */
public class BeanMapper {

    private final Provider<IChiefComplaint> chiefComplaintProvider;
    private final Provider<IMedication> medicationProvider;
    private final Provider<IMedicationActiveDrugName> medicationActiveDrugNameProvider;
    private final Provider<IMedicationActiveDrug> medicationActiveDrugProvider;
    private final Provider<IMedicationMeasurementUnit> medicationMeasurementUnitProvider;
    private final Provider<IMedicationForm> medicationFormProvider;
    private final Provider<IMissionCity> missionCityProvider;
    private final Provider<IMissionCountry> missionCountryProvider;
    private final Provider<IMissionTeam> missionTeamProvider;
    private final Provider<IMissionTrip> missionTripProvider;
    private final Provider<IPatient> patientProvider;
    private final Provider<IPatientAgeClassification> patientAgeClassificationProvider;
    private final Provider<IPatientEncounterPhoto> patientEncounterPhotoProvider;
    private final Provider<IPatientEncounter> patientEncounterProvider;
    private final Provider<IPatientEncounterTabField> patientEncounterTabFieldProvider;
    private final Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private final Provider<IPatientPrescription> patientPrescriptionProvider;
    private final Provider<IPhoto> photoProvider;
    private final Provider<IRole> roleProvider;
    private final Provider<ITabField> tabFieldProvider;
    private final Provider<ITabFieldSize> tabFieldSizeProvider;
    private final Provider<ITabFieldType> tabFieldTypeProvider;
    private final Provider<ITab> tabProvider;
    private final Provider<IUser> userProvider;
    private final Provider<IVital> vitalProvider;

    @Inject
    public BeanMapper(Provider<IChiefComplaint> chiefComplaintProvider,
                        Provider<IMedication> medicationProvider,
                        Provider<IMedicationActiveDrugName> medicationActiveDrugNameProvider,
                        Provider<IMedicationForm> medicationFormProvider,
                        Provider<IMedicationActiveDrug> medicationActiveDrugProvider,
                        Provider<IMedicationMeasurementUnit> medicationMeasurementUnitProvider,
                        Provider<IMissionCity> missionCityProvider,
                        Provider<IMissionCountry> missionCountryProvider,
                        Provider<IMissionTeam> missionTeamProvider,
                        Provider<IMissionTrip> missionTripProvider,
                        Provider<IPatient> patientProvider,
                        Provider<IPatientAgeClassification> patientAgeClassificationProvider,
                        Provider<IPatientEncounterPhoto> patientEncounterPhotoProvider,
                        Provider<IPatientEncounter> patientEncounterProvider,
                        Provider<IPatientEncounterTabField> patientEncounterTabFieldProvider,
                        Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                        Provider<IPatientPrescription> patientPrescriptionProvider,
                        Provider<IPhoto> photoProvider,
                        Provider<IRole> roleProvider,
                        Provider<ITabField> tabFieldProvider,
                        Provider<ITabFieldSize> tabFieldSizeProvider,
                        Provider<ITabFieldType> tabFieldTypeProvider,
                        Provider<ITab> tabProvider,
                        Provider<IUser> userProvider,
                        Provider<IVital> vitalProvider) {
        this.chiefComplaintProvider = chiefComplaintProvider;
        this.patientEncounterProvider = patientEncounterProvider;
        this.medicationProvider = medicationProvider;
        this.medicationActiveDrugNameProvider = medicationActiveDrugNameProvider;
        this.medicationFormProvider = medicationFormProvider;
        this.medicationActiveDrugProvider = medicationActiveDrugProvider;
        this.medicationMeasurementUnitProvider = medicationMeasurementUnitProvider;
        this.missionCityProvider = missionCityProvider;
        this.missionCountryProvider = missionCountryProvider;
        this.missionTeamProvider = missionTeamProvider;
        this.missionTripProvider = missionTripProvider;
        this.patientProvider = patientProvider;
        this.patientAgeClassificationProvider = patientAgeClassificationProvider;
        this.patientEncounterPhotoProvider = patientEncounterPhotoProvider;
        this.patientEncounterTabFieldProvider = patientEncounterTabFieldProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.patientPrescriptionProvider = patientPrescriptionProvider;
        this.photoProvider = photoProvider;
        this.roleProvider = roleProvider;
        this.tabFieldProvider = tabFieldProvider;
        this.tabFieldSizeProvider = tabFieldSizeProvider;
        this.tabFieldTypeProvider = tabFieldTypeProvider;
        this.tabProvider = tabProvider;
        this.userProvider = userProvider;
        this.vitalProvider = vitalProvider;
    }
}
