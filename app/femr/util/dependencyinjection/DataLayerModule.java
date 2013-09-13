package femr.util.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.data.models.IUser;

public class DataLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<IRepository<IUser>>() {}).to(new TypeLiteral<Repository<IUser>>() {});
    }
}
