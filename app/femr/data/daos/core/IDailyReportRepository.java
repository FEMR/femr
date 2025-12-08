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
     * Returns a nested map structure: sexCategory -> (ageCategory -> count)
     *
     * Example: { "MALE" -> { "UNDER_1" -> 5, "AGE_1_TO_4" -> 10, ... }, ... }
     *
     * @param tripId the mission trip ID
     * @param date   the date of activity
     * @return nested map of demographic counts by sex and age category
     */
    Map<String, Map<String, Integer>> getDemographicCounts(int tripId, DateTime date);

    /**
     * Get mission trip entity with team, city, and country eagerly fetched.
     *
     * @param tripId the mission trip ID
     * @return IMissionTrip entity or null if not found
     */
    IMissionTrip getMissionTripById(int tripId);
}