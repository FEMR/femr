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
package femr.data.daos.core;

import femr.data.models.core.ISystemSetting;
import java.util.List;

/**
 * A repository to cover the following tables:
 * <ul>
 * <li>system_settings</li>
 * </ul>
 */
public interface ISystemSettingRepository {

    /**
     * Retrieve all available system settings.
     *
     * @return a list of all system settings or an empty list if none exist/an error occured
     */
    List<? extends ISystemSetting> findAllSystemSettings();

    /**
     * Retrieve a system setting by its name.
     *
     * @param name name of the system setting, not null
     * @return the system setting or null if it doesn't exist or null if parameter is null
     */
    ISystemSetting findSystemSettingByName(String name);

    /**
     * Updates or creates a system setting
     *
     * @param systemSetting the setting to be updated or created, not null
     * @return the updated or created system setting or null if parameter is null
     */
    ISystemSetting saveSystemSetting(ISystemSetting systemSetting);
}
