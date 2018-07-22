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
import femr.business.services.core.IConfigureService;
import femr.common.dtos.ServiceResponse;
import femr.data.daos.IRepository;
import femr.data.models.core.ISystemSetting;
import femr.data.models.mysql.SystemSetting;

import java.util.List;

public class ConfigureService implements IConfigureService {

    private final IRepository<ISystemSetting> systemSettingRepository;

    @Inject
    public ConfigureService(IRepository<ISystemSetting> systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends ISystemSetting>> retrieveCurrentSettings() {
        ServiceResponse<List<? extends ISystemSetting>> response = new ServiceResponse<>();
        try {
            List<? extends ISystemSetting> systemSettings = systemSettingRepository.findAll(SystemSetting.class);
            response.setResponseObject(systemSettings);

        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends ISystemSetting>> updateSystemSettings(List<String> systemSettings) {
        ServiceResponse<List<? extends ISystemSetting>> response = new ServiceResponse<>();
        List<? extends ISystemSetting> allSystemSettings = systemSettingRepository.findAll(SystemSetting.class);

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
        }
        return response;
    }
}

