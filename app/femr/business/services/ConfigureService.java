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
package femr.business.services;

import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.common.dto.ServiceResponse;
import femr.data.daos.IRepository;
import femr.data.models.ISystemSetting;
import femr.data.models.SystemSetting;

import java.util.List;

public class ConfigureService implements IConfigureService {

    private final IRepository<ISystemSetting> systemSettingRepository;

    @Inject
    public ConfigureService(IRepository<ISystemSetting> systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    @Override
    public ServiceResponse<List<? extends ISystemSetting>> getCurrentSettings() {
        ServiceResponse<List<? extends ISystemSetting>> response = new ServiceResponse<>();
        try {
            List<? extends ISystemSetting> systemSettings = systemSettingRepository.findAll(SystemSetting.class);
            response.setResponseObject(systemSettings);

        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends ISystemSetting>> updateSystemSettings(List<? extends ISystemSetting> systemSettings) {
        ServiceResponse<List<? extends ISystemSetting>> response = new ServiceResponse<>();
        try {
            for (ISystemSetting ss : systemSettings) {
                systemSettingRepository.update(ss);
            }
            List<? extends ISystemSetting> updatedSystemSettings = systemSettingRepository.findAll(SystemSetting.class);
            response.setResponseObject(updatedSystemSettings);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<ISystemSetting> getSystemSetting(String settingName) {
        ServiceResponse<ISystemSetting> response = new ServiceResponse<>();
        try {
            ExpressionList<SystemSetting> query = QueryProvider.getSystemSettingQuery()
                    .where()
                    .eq("name", settingName);
            ISystemSetting systemSetting = systemSettingRepository.findOne(query);
            response.setResponseObject(systemSetting);

        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<ISystemSetting> updateSystemSetting(ISystemSetting systemSetting) {
        ServiceResponse<ISystemSetting> response = new ServiceResponse<>();
        try {
            ISystemSetting updatedSystemSetting = systemSettingRepository.update(systemSetting);
            response.setResponseObject(updatedSystemSetting);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }


}

