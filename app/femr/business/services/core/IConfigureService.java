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
package femr.business.services.core;

import femr.common.dtos.ServiceResponse;
import femr.data.models.core.ISystemSetting;

import java.util.List;

public interface IConfigureService {

    /**
     * Retrieve available settings.
     *
     * @return a list of current system settings.
     */
    ServiceResponse<List<? extends ISystemSetting>> retrieveCurrentSettings();

    /**
     * Update more than one system setting.
     *
     * @param systemSettings a list of active system setting names. system settings that don't exist in this list are assumed to be inactive.
     *                       null if all system settings are to be set to "off"
     * @return a list of system settings after the update.
     */
    ServiceResponse<List<? extends ISystemSetting>> updateSystemSettings(List<String> systemSettings);
}
