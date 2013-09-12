package edu.wayne.femr.util.dependencyinjection;

import com.google.inject.AbstractModule;
import edu.wayne.femr.util.encryption.BCryptPasswordEncryptor;
import edu.wayne.femr.util.encryption.IPasswordEncryptor;

public class UtilitiesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IPasswordEncryptor.class).to(BCryptPasswordEncryptor.class);
    }
}
