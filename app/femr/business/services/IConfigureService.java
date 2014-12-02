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
     * @param systemSettings a list of active system setting names. system settings that don't exist in this list are assumed to be inactive
     * @return
     */
    ServiceResponse<List<? extends ISystemSetting>> updateSystemSettings(List<String> systemSettings);
}
