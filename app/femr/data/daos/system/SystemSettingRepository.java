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
package femr.data.daos.system;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import femr.data.daos.core.ISystemSettingRepository;
import femr.data.models.core.ISystemSetting;
import femr.data.models.mysql.SystemSetting;
import femr.util.stringhelpers.StringUtils;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

public class SystemSettingRepository implements ISystemSettingRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends ISystemSetting> findAllSystemSettings() {

        List<? extends ISystemSetting> systemSettings = new ArrayList<>();

        try {

            systemSettings = getSystemSettingQuery().findList();
        } catch (Exception ex) {

            Logger.error("SettingsRepository-findAllSystemSettings", ex.getMessage());
        }

        return systemSettings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISystemSetting findSystemSettingByName(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        ExpressionList<SystemSetting> expressionList = getSystemSettingQuery()
                .where()
                .eq("name", name);

        ISystemSetting systemSetting = null;

        try {

            systemSetting = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("SettingsRepository-findSystemSettingByName", ex.getMessage());
        }

        return systemSetting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISystemSetting saveSystemSetting(ISystemSetting systemSetting) {

        if (systemSetting == null) {

            return null;
        }

        try {

            Ebean.save(systemSetting);
        } catch (Exception ex) {

            Logger.error("SettingsRepository-saveSystemSetting", ex.getMessage());
        }

        return systemSetting;
    }

    /**
     * Provides the Ebean object to start building queries
     *
     * @return The system setting type Query object
     */
    public static Query<SystemSetting> getSystemSettingQuery() {
        return Ebean.find(SystemSetting.class);
    }
}
