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

import femr.data.models.core.IMissionTrip;
import org.joda.time.DateTime;

import java.util.Map;

public interface IDailyReportRepository {

    /**
     * Get patient demographic counts for a specific trip and date.
     * Returns a nested map: sexCategory -> (ageCategory -> count)
     */
    Map<String, Map<String, Integer>> getDemographicCounts(int tripId, DateTime date);

    /**
     * Get mission trip entity with team, city, and country eagerly fetched.
     */
    IMissionTrip getMissionTripById(int tripId);

    /**
     * Get WHO health event counts for a specific trip and date.
     * Returns a nested map: healthEventName -> (ageGroup -> count)
     * where ageGroup is "lt5" (under 5) or "gte5" (5 and over).
     */
    Map<String, Map<String, Integer>> getHealthEventCounts(int tripId, DateTime date);

    /**
     * Get WHO procedure counts for a specific trip and date.
     * Returns a nested map: procedureName -> (ageGroup -> count)
     * where ageGroup is "lt5" (under 5) or "gte5" (5 and over).
     */
    Map<String, Map<String, Integer>> getProcedureCounts(int tripId, DateTime date);

}
