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
        bind(IConceptDiagnosis.class).toProvider(ConceptDiagnosisProvider.class);
        bind(ILoginAttempt.class).toProvider(LoginAttemptProvider.class);
        bind(IMedication.class).toProvider(MedicationProvider.class);
        bind(IMedicationGenericStrength.class).toProvider(MedicationGenericStrengthProvider.class);
        bind(IMedicationGeneric.class).toProvider(MedicationGenericProvider.class);
        bind(IConceptPrescriptionAdministration.class).toProvider(ConceptPrescriptionAdministrationProvider.class);
        bind(IConceptMedicationForm.class).toProvider(ConceptMedicationFormProvider.class);
        bind(IMedicationInventory.class).toProvider(MedicationInventoryProvider.class);
        bind(IConceptMedicationUnit.class).toProvider(ConceptMedicationUnitProvider.class);
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
        bind(IPatientPrescriptionReplacement.class).toProvider(PatientPrescriptionReplacementProvider.class);
        bind(IPatientPrescriptionReplacementReason.class).toProvider(PatientPrescriptionReplacementReasonProvider.class);
        bind(IPhoto.class).toProvider(PhotoProvider.class);
        bind(IRole.class).toProvider(RoleProvider.class);
        bind(ISystemSetting.class).toProvider(SystemSettingProvider.class);
        bind(ITab.class).toProvider(TabProvider.class);
        bind(ITabField.class).toProvider(TabFieldProvider.class);
        bind(ITabFieldType.class).toProvider(TabFieldTypeProvider.class);
        bind(ITabFieldSize.class).toProvider(TabFieldSizeProvider.class);
        bind(IUser.class).toProvider(UserProvider.class);
        bind(IVital.class).toProvider(VitalProvider.class);

        //Non generic repositories
        bind(IEncounterRepository.class).to(EncounterRepository.class);
        bind(IMedicationRepository.class).to(MedicationRepository.class);
        bind(IPatientRepository.class).to(PatientRepository.class);
        bind(IPhotoRepository.class).to(PhotoRepository.class);
        bind(IUserRepository.class).to(UserRepository.class);
        bind(IPrescriptionRepository.class).to(PrescriptionRepository.class);

        // Research
        bind(IResearchEncounter.class).toProvider(ResearchEncounterProvider.class);

        //Generic repositories (being phased out in place of non generic)
        bind(new TypeLiteral<IRepository<IChiefComplaint>>() {}).to(new TypeLiteral<Repository<IChiefComplaint>>() {});
        bind(new TypeLiteral<IRepository<IConceptDiagnosis>>() {}).to(new TypeLiteral<Repository<IConceptDiagnosis>>() {});
        bind(new TypeLiteral<IRepository<IMissionCity>>() {}).to(new TypeLiteral<Repository<IMissionCity>>() {});
        bind(new TypeLiteral<IRepository<IMissionCountry>>() {}).to(new TypeLiteral<Repository<IMissionCountry>>() {});
        bind(new TypeLiteral<IRepository<IMissionTeam>>() {}).to(new TypeLiteral<Repository<IMissionTeam>>() {});
        bind(new TypeLiteral<IRepository<IMissionTrip>>() {}).to(new TypeLiteral<Repository<IMissionTrip>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounterPhoto>>() {}).to(new TypeLiteral<Repository<IPatientEncounterPhoto>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounterTabField>>(){}).to(new TypeLiteral<Repository<IPatientEncounterTabField>>(){});
        bind(new TypeLiteral<IRepository<IPatientEncounterVital>>() {}).to(new TypeLiteral<Repository<IPatientEncounterVital>>() {});
        bind(new TypeLiteral<IRepository<ISystemSetting>>() {}).to(new TypeLiteral<Repository<ISystemSetting>>(){});
        bind(new TypeLiteral<IRepository<ITab>>(){}).to(new TypeLiteral<Repository<ITab>>(){});
        bind(new TypeLiteral<IRepository<ITabField>>(){}).to(new TypeLiteral<Repository<ITabField>>(){});
        bind(new TypeLiteral<IRepository<ITabFieldType>>(){}).to(new TypeLiteral<Repository<ITabFieldType>>(){});
        bind(new TypeLiteral<IRepository<ITabFieldSize>>(){}).to(new TypeLiteral<Repository<ITabFieldSize>>(){});
        bind(new TypeLiteral<IRepository<IVital>>() {}).to(new TypeLiteral<Repository<IVital>>() {});
        // Feedback
        bind(new TypeLiteral<IRepository<IFeedback>>(){}).to(new TypeLiteral<Repository<IFeedback>>(){});
        // Research
        bind(new TypeLiteral<IRepository<IResearchEncounter>>() {}).to(new TypeLiteral<Repository<IResearchEncounter>>() {});
        bind(new TypeLiteral<IRepository<IResearchEncounterVital>>() {}).to(new TypeLiteral<Repository<IResearchEncounterVital>>() {});

        //Generic repositories that are required by the database seeder
        bind(new TypeLiteral<IRepository<IConceptMedicationForm>>() {}).to(new TypeLiteral<Repository<IConceptMedicationForm>>() {});
        bind(new TypeLiteral<IRepository<IConceptMedicationUnit>>() {}).to(new TypeLiteral<Repository<IConceptMedicationUnit>>() {});
        bind(new TypeLiteral<IRepository<IConceptPrescriptionAdministration>>() {}).to(new TypeLiteral<Repository<IConceptPrescriptionAdministration>>() {});
        bind(new TypeLiteral<IRepository<IMedication>>() {}).to(new TypeLiteral<Repository<IMedication>>() {});
        bind(new TypeLiteral<IRepository<IMedicationGeneric>>() {}).to(new TypeLiteral<Repository<IMedicationGeneric>>() {});
        bind(new TypeLiteral<IRepository<IMedicationGenericStrength>>() {}).to(new TypeLiteral<Repository<IMedicationGenericStrength>>() {});
    }
}
