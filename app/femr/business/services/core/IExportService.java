package femr.business.services.core;

import femr.common.dtos.ServiceResponse;

import java.io.File;
import java.util.Collection;

public interface IExportService {

    ServiceResponse<File> exportAllEncounters(Collection<Integer> tripIds);
}
