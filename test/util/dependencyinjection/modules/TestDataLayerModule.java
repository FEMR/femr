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
package util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import femr.data.daos.IRepository;
import mock.femr.data.daos.MockRepository;
import femr.data.models.core.*;
import femr.data.models.core.research.IResearchEncounter;
import femr.data.models.core.research.IResearchEncounterVital;
import femr.util.dependencyinjection.providers.*;

public class TestDataLayerModule extends AbstractModule {

    @Override
    protected void configure() {

        // DataModelMapper Injection
        //bind(IDataModelMapper.class).to(MockDataModelMapper.class);

        //Provider Injection
        bind(IChiefComplaint.class).toProvider(ChiefComplaintProvider.class);
        bind(IDiagnosis.class).toProvider(DiagnosisProvider.class);
        bind(IMedication.class).toProvider(MedicationProvider.class);
        bind(IMedicationActiveDrug.class).toProvider(MedicationActiveDrugProvider.class);
        bind(IMedicationGeneric.class).toProvider(MedicationGenericProvider.class);
        bind(IMedicationAdministration.class).toProvider(MedicationAdministrationProvider.class);
        bind(IConceptMedicationForm.class).toProvider(ConceptMedicationFormProvider.class);
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


        //Repository Injection
        bind(new TypeLiteral<IRepository<IChiefComplaint>>() {}).to(new TypeLiteral<MockRepository<IChiefComplaint>>() {});
        bind(new TypeLiteral<IRepository<IDiagnosis>>() {}).to(new TypeLiteral<MockRepository<IDiagnosis>>() {});
        bind(new TypeLiteral<IRepository<IMedication>>() {}).to(new TypeLiteral<MockRepository<IMedication>>() {});
        bind(new TypeLiteral<IRepository<IMedicationActiveDrug>>() {}).to(new TypeLiteral<MockRepository<IMedicationActiveDrug>>() {});
        bind(new TypeLiteral<IRepository<IMedicationGeneric>>() {}).to(new TypeLiteral<MockRepository<IMedicationGeneric>>() {});
        bind(new TypeLiteral<IRepository<IMedicationAdministration>>() {}).to(new TypeLiteral<MockRepository<IMedicationAdministration>>() {});
        bind(new TypeLiteral<IRepository<IConceptMedicationForm>>() {}).to(new TypeLiteral<MockRepository<IConceptMedicationForm>>() {});
        bind(new TypeLiteral<IRepository<IConceptMedicationUnit>>() {}).to(new TypeLiteral<MockRepository<IConceptMedicationUnit>>() {});
        bind(new TypeLiteral<IRepository<IMissionCity>>() {}).to(new TypeLiteral<MockRepository<IMissionCity>>() {});
        bind(new TypeLiteral<IRepository<IMissionCountry>>() {}).to(new TypeLiteral<MockRepository<IMissionCountry>>() {});
        bind(new TypeLiteral<IRepository<IMissionTeam>>() {}).to(new TypeLiteral<MockRepository<IMissionTeam>>() {});
        bind(new TypeLiteral<IRepository<IMissionTrip>>() {}).to(new TypeLiteral<MockRepository<IMissionTrip>>() {});
        bind(new TypeLiteral<IRepository<IPatient>>() {}).to(new TypeLiteral<MockRepository<IPatient>>() {});
        bind(new TypeLiteral<IRepository<IPatientAgeClassification>>() {}).to(new TypeLiteral<MockRepository<IPatientAgeClassification>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounter>>() {}).to(new TypeLiteral<MockRepository<IPatientEncounter>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounterPhoto>>() {}).to(new TypeLiteral<MockRepository<IPatientEncounterPhoto>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounterTabField>>(){}).to(new TypeLiteral<MockRepository<IPatientEncounterTabField>>(){});
        bind(new TypeLiteral<IRepository<IPatientEncounterVital>>() {}).to(new TypeLiteral<MockRepository<IPatientEncounterVital>>() {});
        bind(new TypeLiteral<IRepository<IPatientPrescription>>() {}).to(new TypeLiteral<MockRepository<IPatientPrescription>>() {});
        bind(new TypeLiteral<IRepository<IPhoto>>() {}).to(new TypeLiteral<MockRepository<IPhoto>>() {});
        bind(new TypeLiteral<IRepository<IRole>>() {}).to(new TypeLiteral<MockRepository<IRole>>() {});
        bind(new TypeLiteral<IRepository<ISystemSetting>>() {}).to(new TypeLiteral<MockRepository<ISystemSetting>>(){});
        bind(new TypeLiteral<IRepository<ITab>>(){}).to(new TypeLiteral<MockRepository<ITab>>(){});
        bind(new TypeLiteral<IRepository<ITabField>>(){}).to(new TypeLiteral<MockRepository<ITabField>>(){});
        bind(new TypeLiteral<IRepository<ITabFieldType>>(){}).to(new TypeLiteral<MockRepository<ITabFieldType>>(){});
        bind(new TypeLiteral<IRepository<ITabFieldSize>>(){}).to(new TypeLiteral<MockRepository<ITabFieldSize>>(){});
        bind(new TypeLiteral<IRepository<IUser>>() {}).to(new TypeLiteral<MockRepository<IUser>>() {});
        bind(new TypeLiteral<IRepository<IVital>>() {}).to(new TypeLiteral<MockRepository<IVital>>() {});

        // Research
        bind(new TypeLiteral<IRepository<IResearchEncounter>>() {}).to(new TypeLiteral<MockRepository<IResearchEncounter>>() {});
        bind(new TypeLiteral<IRepository<IResearchEncounterVital>>() {}).to(new TypeLiteral<MockRepository<IResearchEncounterVital>>() {});
    }
}
