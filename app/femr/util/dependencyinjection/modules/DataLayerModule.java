package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IRole;
import femr.common.models.IUser;
import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.util.dependencyinjection.providers.PatientEncounterProvider;
import femr.util.dependencyinjection.providers.PatientEncounterVitalProvider;
import femr.util.dependencyinjection.providers.PatientProvider;
import femr.util.dependencyinjection.providers.UserProvider;

public class DataLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IUser.class).toProvider(UserProvider.class);
        bind(IPatient.class).toProvider(PatientProvider.class);
        bind(IPatientEncounter.class).toProvider(PatientEncounterProvider.class);
        bind(IPatientEncounterVital.class).toProvider(PatientEncounterVitalProvider.class);

        bind(new TypeLiteral<IRepository<IRole>>() {
        }).to(new TypeLiteral<Repository<IRole>>() {});
        bind(new TypeLiteral<IRepository<IUser>>() {}).to(new TypeLiteral<Repository<IUser>>() {});
        bind(new TypeLiteral<IRepository<IPatient>>() {}).to(new TypeLiteral<Repository<IPatient>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounter>>() {}).to(new TypeLiteral<Repository<IPatientEncounter>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounterVital>>() {}).to(new TypeLiteral<Repository<IPatientEncounterVital>>() {});
    }
}
