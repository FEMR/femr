package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.data.models.core.IInternetStatus;
import femr.data.models.mysql.InternetStatus;

public class InternetStatusProvider implements Provider<IInternetStatus> {
  @Override
  public IInternetStatus get() {
    return new InternetStatus();
  }
}
