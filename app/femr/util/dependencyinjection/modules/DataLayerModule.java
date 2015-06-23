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
package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import femr.data.daos.core.*;
import femr.data.daos.system.*;
import femr.data.models.core.*;
import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.data.models.core.research.IResearchEncounter;
import femr.data.models.core.research.IResearchEncounterVital;
import femr.util.dependencyinjection.providers.*;

public class DataLayerModule extends AbstractModule {

    @Override
    protected void configure() {

        //Provider Injection
        bind(IChiefComplaint.class).toProvider(ChiefComplaintProvider.class);
        bind(IDiagnosis.class).toProvider(DiagnosisProvider.class);
        bind(IMedication.class).toProvider(MedicationProvider.class);
        bind(IMedicationActiveDrug.class).toProvider(MedicationActiveDrugProvider.class);
        bind(IMedicationActiveDrugName.class).toProvider(MedicationActiveDrugNameProvider.class);
        bind(IMedicationAdministration.class).toProvider(MedicationAdministrationProvider.class);
        bind(IMedicationForm.class).toProvider(MedicationFormProvider.class);
        bind(IMedicationMeasurementUnit.class).toProvider(MedicationMeasurementUnitProvider.class);
        bind(IMissionCity.class).toProvider(MissionCityProvider.class);
        bind(IMissionCountry.class).toProvider(MissionCountryProvider.class);
        bind(IMissionTeam.class).toProvider(MissionTeamProvider.class);
        bind(IMissionTrip.class).toProvider(MissionTripProvider.class);
        bind(IPatient.class).toProvider(PatientProvider.class);
        bind(IPatientAgeClassification.class).toProvider(PatientAgeClassificationProvider.class);
        bind(IPatientEncounter.class).toProvider(PatientEncounterProvider.class);
        bind(IPatientEncounterPhoto.class).toProvider(PatientEncounterPhotoProvider.class);
        bind(IPatientEncounterTabField.class).toProvider(PatientEncounterTabFieldProvider.class);
        bind(IPatientEncounterVital.class).toProvider(PatientEncounterVitalProvider.class);
        bind(IPatientPrescription.class).toProvider(PatientPrescriptionProvider.class);
        bind(IPhoto.class).toProvider(PhotoProvider.class);
        bind(IRole.class).toProvider(RoleProvider.class);
        bind(ISystemSetting.class).toProvider(SystemSettingProvider.class);
        bind(ITab.class).toProvider(TabProvider.class);
        bind(ITabField.class).toProvider(TabFieldProvider.class);
        bind(ITabFieldType.class).toProvider(TabFieldTypeProvider.class);
        bind(ITabFieldSize.class).toProvider(TabFieldSizeProvider.class);
        bind(IUser.class).toProvider(UserProvider.class);
        bind(IVital.class).toProvider(VitalProvider.class);

        // Research
        bind(IResearchEncounter.class).toProvider(ResearchEncounterProvider.class);

        //non-generic Repository Injection
        bind(IMedicationRepository.class).to(MedicationRepository.class);
        bind(IPatientRepository.class).to(PatientRepository.class);
        bind(IPatientEncounterRepository.class).to(PatientEncounterRepository.class);
        bind(IPhotoRepository.class).to(PhotoRepository.class);
        bind(IPrescriptionRepository.class).to(PrescriptionRepository.class);
        bind(ISettingsRepository.class).to(SettingsRepository.class);
        bind(ITabRepository.class).to(TabRepository.class);
        bind(ITripRepository.class).to(TripRepository.class);
        bind(IUserRepository.class).to(UserRepository.class);
        bind(IVitalRepository.class).to(VitalRepository.class);

        //Repository Injection
        bind(new TypeLiteral<IRepository<IDiagnosis>>() {}).to(new TypeLiteral<Repository<IDiagnosis>>() {});
        bind(new TypeLiteral<IRepository<IMedication>>() {}).to(new TypeLiteral<Repository<IMedication>>() {});
        bind(new TypeLiteral<IRepository<IMedicationActiveDrug>>() {}).to(new TypeLiteral<Repository<IMedicationActiveDrug>>() {});
        bind(new TypeLiteral<IRepository<IMedicationActiveDrugName>>() {}).to(new TypeLiteral<Repository<IMedicationActiveDrugName>>() {});
        bind(new TypeLiteral<IRepository<IMedicationAdministration>>() {}).to(new TypeLiteral<Repository<IMedicationAdministration>>() {});
        bind(new TypeLiteral<IRepository<IMedicationForm>>() {}).to(new TypeLiteral<Repository<IMedicationForm>>() {});
        bind(new TypeLiteral<IRepository<IMedicationMeasurementUnit>>() {}).to(new TypeLiteral<Repository<IMedicationMeasurementUnit>>() {});
        bind(new TypeLiteral<IRepository<IMissionCity>>() {}).to(new TypeLiteral<Repository<IMissionCity>>() {});
        bind(new TypeLiteral<IRepository<IMissionCountry>>() {}).to(new TypeLiteral<Repository<IMissionCountry>>() {});
        bind(new TypeLiteral<IRepository<IMissionTeam>>() {}).to(new TypeLiteral<Repository<IMissionTeam>>() {});
        bind(new TypeLiteral<IRepository<IMissionTrip>>() {}).to(new TypeLiteral<Repository<IMissionTrip>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounterPhoto>>() {}).to(new TypeLiteral<Repository<IPatientEncounterPhoto>>() {});
        bind(new TypeLiteral<IRepository<IPatientPrescription>>() {}).to(new TypeLiteral<Repository<IPatientPrescription>>() {});
        bind(new TypeLiteral<IRepository<IPhoto>>() {}).to(new TypeLiteral<Repository<IPhoto>>() {});
        bind(new TypeLiteral<IRepository<IRole>>() {}).to(new TypeLiteral<Repository<IRole>>() {});
        bind(new TypeLiteral<IRepository<ISystemSetting>>() {}).to(new TypeLiteral<Repository<ISystemSetting>>(){});
        bind(new TypeLiteral<IRepository<IUser>>() {}).to(new TypeLiteral<Repository<IUser>>() {});

        // Research
        bind(new TypeLiteral<IRepository<IResearchEncounter>>() {}).to(new TypeLiteral<Repository<IResearchEncounter>>() {});
        bind(new TypeLiteral<IRepository<IResearchEncounterVital>>() {}).to(new TypeLiteral<Repository<IResearchEncounterVital>>() {});
    }
}
