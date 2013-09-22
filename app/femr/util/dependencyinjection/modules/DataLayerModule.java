package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import femr.common.models.IPatient;
import femr.common.models.IRole;
import femr.common.models.IUser;
import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.util.dependencyinjection.providers.UserProvider;

public class DataLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IUser.class).toProvider(UserProvider.class);

        bind(new TypeLiteral<IRepository<IRole>>() {}).to(new TypeLiteral<Repository<IRole>>() {});
        bind(new TypeLiteral<IRepository<IUser>>() {}).to(new TypeLiteral<Repository<IUser>>() {});
        bind(new TypeLiteral<IRepository<IPatient>>() {}).to(new TypeLiteral<Repository<IPatient>>() {});
    }
}
