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
import org.joda.time.DateTime;

/**
 * Service interface for generating MDS Daily Reports.
 * Aggregates data from the repository layer and returns it in a format
 * suitable for populating the MDS-Ver1.0 Daily Report form.
 */
public interface IDailyReportService {

    /**
     * Generate a complete daily report for a specific trip and date.
     * This aggregates all data needed for the MDS Daily Report form.
     *
     * @param tripId the mission trip ID, not null
     * @param date   the date to generate the report for, not null
     * @return a ServiceResponse containing the DailyReportItem with all aggregated data,
     * or errors if the operation failed
     */
    ServiceResponse<DailyReportItem> generateDailyReport(int tripId, DateTime date);
}