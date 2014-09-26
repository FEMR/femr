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

import femr.common.dto.ServiceResponse;
import femr.data.models.ISystemSetting;

import java.util.List;

public interface IConfigureService {
    /**
     * Get all user settings
     * @return
     */
    ServiceResponse<List<? extends ISystemSetting>> getCurrentSettings();

    /**
     * Update more than one system setting
     * @param systemSettings the updated settings
     * @return
     */
    ServiceResponse<List<? extends ISystemSetting>> updateSystemSettings(List<? extends ISystemSetting> systemSettings);

    /**
     * Get a system setting by name
     * @param settingName the name
     * @return the setting
     */
    ServiceResponse<ISystemSetting> getSystemSetting(String settingName);

    /**
     * Update one system setting
     * @param systemSetting
     * @return
     */
    ServiceResponse<ISystemSetting> updateSystemSetting(ISystemSetting systemSetting);
}
