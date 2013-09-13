package edu.wayne.femr.util.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import edu.wayne.femr.data.daos.IRepository;
import edu.wayne.femr.data.daos.Repository;
import edu.wayne.femr.data.models.IUser;

public class DataLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<IRepository<IUser>>() {}).to(new TypeLiteral<Repository<IUser>>() {});
    }
}
