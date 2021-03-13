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
    public ServiceResponse<List<? extends INetworkStatus>> updateNetworkStatuses() {
        ServiceResponse<List<? extends INetworkStatus>> response = new ServiceResponse<>();
        //TODO: Lemur Team update the database with the right values
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
        //TODO: Lemur Team update the database with the right values
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
        //TODO: Feather Team backup database ---> run script to see if it successsfully return if it succeeds or not

        String updated_date = java.time.LocalDate.now().toString().replace("-", ".");
        DatabaseStatus databaseStatus = new DatabaseStatus();
        databaseStatus.setName("Last Backup");
        databaseStatus.setValue(updated_date);
        databaseStatusRepository.update(databaseStatus);

        /*List<? extends ISystemSetting> allSystemSettings = systemSettingRepository.findAll(SystemSetting.class);

        try {
            if(systemSettings == null){
                //If systemSettings is null, that means that all settings buttons were unchecked.
                for (ISystemSetting ss: allSystemSettings){
                    ss.setActive(false);
                    systemSettingRepository.update(ss);
                }
            } else {
                for (ISystemSetting ss : allSystemSettings) {
                    if (systemSettings.contains(ss.getName())) {
                        ss.setActive(true);
                        systemSettingRepository.update(ss);
                    } else {
                        ss.setActive(false);
                        systemSettingRepository.update(ss);
                    }
                }
                response.setResponseObject(allSystemSettings);
            }
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }*/
        return response;
    }


}

