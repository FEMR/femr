package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import femr.data.models.IUser;
import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.data.models.*;
import femr.util.dependencyinjection.providers.*;

public class DataLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        //Model Injection
        bind(IChiefComplaint.class).toProvider(ChiefComplaintProvider.class);
        bind(IMedication.class).toProvider(MedicationProvider.class);
        bind(IPatient.class).toProvider(PatientProvider.class);
        bind(IPatientEncounter.class).toProvider(PatientEncounterProvider.class);
        bind(IPatientEncounterPhoto.class).toProvider(PatientEncounterPhotoProvider.class);
        bind(IPatientEncounterTabField.class).toProvider(PatientEncounterTabFieldProvider.class);
        bind(IPatientEncounterVital.class).toProvider(PatientEncounterVitalProvider.class);
        bind(IPatientPrescription.class).toProvider(PatientPrescriptionProvider.class);
        bind(IPhoto.class).toProvider(PhotoProvider.class);
        bind(ISystemSetting.class).toProvider(SystemSettingProvider.class);
        bind(ITab.class).toProvider(TabProvider.class);
        bind(ITabField.class).toProvider(TabFieldProvider.class);
        bind(ITabFieldType.class).toProvider(TabFieldTypeProvider.class);
        bind(ITabFieldSize.class).toProvider(TabFieldSizeProvider.class);
        bind(IUser.class).toProvider(UserProvider.class);
        bind(IVital.class).toProvider(VitalProvider.class);


        //Repository Injection
        bind(new TypeLiteral<IRepository<IChiefComplaint>>() {}).to(new TypeLiteral<Repository<IChiefComplaint>>() {});
        bind(new TypeLiteral<IRepository<IMedication>>() {}).to(new TypeLiteral<Repository<IMedication>>() {});
        bind(new TypeLiteral<IRepository<IPatient>>() {}).to(new TypeLiteral<Repository<IPatient>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounter>>() {}).to(new TypeLiteral<Repository<IPatientEncounter>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounterPhoto>>() {}).to(new TypeLiteral<Repository<IPatientEncounterPhoto>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounterTabField>>(){}).to(new TypeLiteral<Repository<IPatientEncounterTabField>>(){});
        bind(new TypeLiteral<IRepository<IPatientEncounterVital>>() {}).to(new TypeLiteral<Repository<IPatientEncounterVital>>() {});
        bind(new TypeLiteral<IRepository<IPatientPrescription>>() {}).to(new TypeLiteral<Repository<IPatientPrescription>>() {});
        bind(new TypeLiteral<IRepository<IPhoto>>() {}).to(new TypeLiteral<Repository<IPhoto>>() {});
        bind(new TypeLiteral<IRepository<IRole>>() {}).to(new TypeLiteral<Repository<IRole>>() {});
        bind(new TypeLiteral<IRepository<ISystemSetting>>() {}).to(new TypeLiteral<Repository<ISystemSetting>>(){});
        bind(new TypeLiteral<IRepository<ITab>>(){}).to(new TypeLiteral<Repository<ITab>>(){});
        bind(new TypeLiteral<IRepository<ITabField>>(){}).to(new TypeLiteral<Repository<ITabField>>(){});
        bind(new TypeLiteral<IRepository<ITabFieldType>>(){}).to(new TypeLiteral<Repository<ITabFieldType>>(){});
        bind(new TypeLiteral<IRepository<ITabFieldSize>>(){}).to(new TypeLiteral<Repository<ITabFieldSize>>(){});
        bind(new TypeLiteral<IRepository<IUser>>() {}).to(new TypeLiteral<Repository<IUser>>() {});
        bind(new TypeLiteral<IRepository<IVital>>() {}).to(new TypeLiteral<Repository<IVital>>() {});
    }
}
