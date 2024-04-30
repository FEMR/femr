package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.data.models.core.ILanguageCode;
import femr.data.models.mysql.LanguageCode;

public class LanguageCodeProvider implements Provider<ILanguageCode> {
    @Override
    public ILanguageCode get() { return new LanguageCode(); }
}

