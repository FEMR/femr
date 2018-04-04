package femr.util.tasks;

import com.google.inject.AbstractModule;

/**
 * Enable tasks. 
 */
public class AsynchronousTaskConfigurer extends AbstractModule {

    @Override
    protected void configure() {
        //All asynchronous tasks must be loaded as eager singletons
        bind(CheckInternetConnectionTask.class).asEagerSingleton();
        bind(SendLocationDataTask.class).asEagerSingleton();
    }
}
