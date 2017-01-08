package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import femr.util.startup.DatabaseSeeder;
import femr.util.startup.MedicationDatabaseSeeder;

//Defines the database seeders as eager singletons so they are
//created only once as the application starts
public class SeederModule  extends AbstractModule{
    @Override
    protected void configure() {

        bind(DatabaseSeeder.class).asEagerSingleton();
        bind(MedicationDatabaseSeeder.class).asEagerSingleton();
    }
}
