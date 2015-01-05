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
import femr.common.models.TripItem;

import java.util.List;

public interface IMissionTripService {
    /**
     * Retrieve the current trip information
     * @return the current trip or an error if one doesn't exist
     */
    ServiceResponse<TripItem> findCurrentMissionTrip();

    /**
     * Retrieve a list of the teams that are already in the database
     * @return a list of team names
     */
    ServiceResponse<List<String>> findAvailableTeams();

    /**
     * Retrieve a list of the cities that are already in the database
     * @return a list of cities
     */
    ServiceResponse<List<String>> findAvailableCities();

    /**
     * Retrieve a list of the countries that are already in the database
     * @return a list of countries
     */
    ServiceResponse<List<String>> findAvailableCountries();

    /**
     * Given a trip item, do:
     * <ul>
     * <li>Nothing if the current trip is the same as tripItem</li>
     * <li>Create new teams, cities, countries as needed</li>
     * <li>If something new is created (other than description), creates a new current trip</li>
     * </ul>
     * @return
     */
    ServiceResponse<TripItem> updateTrip(TripItem tripItem);

    /**
     * Get a JSON string representing trip information
     *
     * @return
     */
    ServiceResponse<String> getTripInformation();
}
