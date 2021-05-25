package femr.util;

import femr.business.services.core.IUpdatesService;
import femr.data.models.core.INetworkStatus;
import java.util.List;
import femr.common.dtos.ServiceResponse;

public class ThreadHelper implements Runnable {

    private final IUpdatesService internetStatusService;

    public ThreadHelper(IUpdatesService internetStatusService) {
        this.internetStatusService = internetStatusService;
    }

    @Override
    public void run() {
        ServiceResponse<List<? extends INetworkStatus>>
            updateResponse = internetStatusService.updateNetworkStatuses();

        // if (updateResponse.hasErrors()) throw new RuntimeException();
    }
}
