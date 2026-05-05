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
package femr.ui.models.manager;

import femr.common.models.DailyReportItem;
import femr.common.models.WhoReportConfigItem;

public class DailyReportViewModelGet {

    private DailyReportItem reportItem;
    private String userFriendlyTrip;
    private WhoReportConfigItem whoConfig;

    public DailyReportItem getReportItem() {
        return reportItem;
    }

    public void setReportItem(DailyReportItem reportItem) {
        this.reportItem = reportItem;
    }

    public String getUserFriendlyTrip() {
        return userFriendlyTrip;
    }

    public void setUserFriendlyTrip(String userFriendlyTrip) {
        this.userFriendlyTrip = userFriendlyTrip;
    }

    public WhoReportConfigItem getWhoConfig() {
        return whoConfig;
    }

    public void setWhoConfig(WhoReportConfigItem whoConfig) {
        this.whoConfig = whoConfig;
    }
}
