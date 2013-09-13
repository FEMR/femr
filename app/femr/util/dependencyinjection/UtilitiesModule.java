package femr.util.dependencyinjection;

import com.google.inject.AbstractModule;
import femr.util.encryption.BCryptPasswordEncryptor;
import femr.util.encryption.IPasswordEncryptor;

public class UtilitiesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IPasswordEncryptor.class).to(BCryptPasswordEncryptor.class);
    }
}
