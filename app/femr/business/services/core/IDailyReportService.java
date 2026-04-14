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
import femr.common.models.DailyReportItem;
import femr.common.models.WhoReportConfigItem;
import org.joda.time.DateTime;

/**
 * Service interface for generating MDS Daily Reports.
 */
public interface IDailyReportService {

    /**
     * Generate a complete daily report for a specific trip and date.
     *
     * @param tripId the mission trip ID, not null
     * @param date   the date to generate the report for, not null
     * @return a ServiceResponse containing the DailyReportItem with all aggregated data,
     *         or errors if the operation failed
     */
    ServiceResponse<DailyReportItem> generateDailyReport(int tripId, DateTime date);

    /**
     * Retrieve the saved WHO report configuration for a specific trip.
     */
    ServiceResponse<WhoReportConfigItem> getWhoReportConfig(int tripId);

    /**
     * Save the WHO report configuration for a specific trip.
     */
    ServiceResponse<Void> saveWhoReportConfig(int tripId, WhoReportConfigItem config);
}
