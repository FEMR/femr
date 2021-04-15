/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.business.services.system;

import com.google.inject.Inject;
import femr.business.services.core.IUpdatesService;
import femr.common.dtos.ServiceResponse;
import femr.data.daos.IRepository;
import femr.data.models.core.IKitStatus;
import femr.data.models.core.INetworkStatus;
import femr.data.models.core.IDatabaseStatus;
import femr.data.models.mysql.KitStatus;
import femr.data.models.mysql.NetworkStatus;
import femr.data.models.mysql.DatabaseStatus;
import femr.ui.controllers.BackEndControllerHelper;

import java.io.File;
import java.io.IOException;
import java.security.UnresolvedPermission;
import java.util.ArrayList;
import java.util.List;

public class UpdatesService implements IUpdatesService {

    private final IRepository<INetworkStatus> networkStatusRepository;
    private final IRepository<IKitStatus> kitStatusRepository;
    private final IRepository<IDatabaseStatus> databaseStatusRepository;

    @Inject
    public UpdatesService(IRepository<INetworkStatus> networkStatusRepository,
                          IRepository<IKitStatus> kitStatusRepository,
                          IRepository<IDatabaseStatus> databaseStatusRepository) {
        this.networkStatusRepository = networkStatusRepository;
        this.kitStatusRepository = kitStatusRepository;
        this.databaseStatusRepository = databaseStatusRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends INetworkStatus>> retrieveNetworkStatuses() {
        ServiceResponse<List<? extends INetworkStatus>> response = new ServiceResponse<>();
        try {
            List<? extends INetworkStatus> networkStatuses = networkStatusRepository.findAll(NetworkStatus.class);
            response.setResponseObject(networkStatuses);

        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public  ServiceResponse<List<? extends INetworkStatus>> updateNetworkStatuses() {
        ServiceResponse<List<? extends INetworkStatus>> response = new ServiceResponse<>();
        ArrayList<String> data = new ArrayList<>();
        try {
            data = BackEndControllerHelper.executeSpeedTestScript("speedtest/sptest.py");
            System.out.println(data);
            //Update Status
            Float Ping = Float.parseFloat(data.get(2));
            String updatedStatus = "Connection stable";
            if(Ping <= 0){
                updatedStatus = "Connection unavailable";
            }
            INetworkStatus status = retrieveNetworkStatuses().getResponseObject().get(0);
            status.setValue(updatedStatus);
            networkStatusRepository.update(status);

            //Update Download
            String updatedDownload = data.get(0);
            INetworkStatus download = retrieveNetworkStatuses().getResponseObject().get(1);
            download.setValue(updatedDownload);
            networkStatusRepository.update(download);

            //Update Upload
            String updatedUpload = data.get(1);
            INetworkStatus upload = retrieveNetworkStatuses().getResponseObject().get(2);
            upload.setValue(updatedUpload);
            networkStatusRepository.update(upload);

            //Update Ping
            String updatedPing = data.get(2);
            INetworkStatus ping = retrieveNetworkStatuses().getResponseObject().get(3);
            ping.setValue(updatedPing);
            networkStatusRepository.update(ping);

            //Update Date
            String updatedDate = java.time.LocalDate.now().toString().replace("-", ".");
            INetworkStatus date = retrieveNetworkStatuses().getResponseObject().get(4);
            date.setValue(updatedDate);
            networkStatusRepository.update(date);
        } catch (Exception e) {
            response.addError("Network status", e.toString());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends IKitStatus>> retrieveKitStatuses() {
        ServiceResponse<List<? extends IKitStatus>> response = new ServiceResponse<>();
        try {
            List<? extends IKitStatus> kitStatuses = kitStatusRepository.findAll(KitStatus.class);
            response.setResponseObject(kitStatuses);

        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends IKitStatus>> updateKitStatuses() {
        ServiceResponse<List<? extends IKitStatus>> response = new ServiceResponse<>();
        try {
            BackEndControllerHelper.executePythonScript("s3scripts/download.py");
            String updatedDate = java.time.LocalDate.now().toString().replace("-", ".");
            IKitStatus kitStatusDate = retrieveKitStatuses().getResponseObject().get(2);
            kitStatusDate.setValue(updatedDate);
            kitStatusRepository.update(kitStatusDate);
        } catch (Exception e) {
            response.addError("Kit update", e.toString());
            e.printStackTrace();
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends IDatabaseStatus>> retrieveDatabaseStatuses() {
        ServiceResponse<List<? extends IDatabaseStatus>> response = new ServiceResponse<>();
        try {
            List<? extends IDatabaseStatus> databaseStatuses = databaseStatusRepository.findAll(DatabaseStatus.class);
            response.setResponseObject(databaseStatuses);

        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends IDatabaseStatus>> updateDatabaseStatuses() {
        ServiceResponse<List<? extends IDatabaseStatus>> response = new ServiceResponse<>();
        //TODO: Do some more robust error checking
        String[] cmd = new String[]{"/bin/bash", "femr.sh"};
        try {
            Process pr = Runtime.getRuntime().exec(cmd, null, new File("/Users/yashsatyavarpu/Documents/super-femr/app/femr/util/backup"));
            String updated_date = java.time.LocalDate.now().toString().replace("-", ".");
            DatabaseStatus databaseStatus = new DatabaseStatus();
            databaseStatus.setName("Last Backup");
            databaseStatus.setValue(updated_date);
            databaseStatusRepository.update(databaseStatus);
        } catch (IOException e) {
            response.addError("Database update", e.toString());
            e.printStackTrace();
        }
        return response;
    }


}

