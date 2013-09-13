package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import femr.util.encryptions.BCryptPasswordEncryptor;
import femr.util.encryptions.IPasswordEncryptor;

public class UtilitiesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IPasswordEncryptor.class).to(BCryptPasswordEncryptor.class);
    }
}
