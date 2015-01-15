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
import femr.common.models.CityItem;
import femr.common.models.MissionItem;
import femr.common.models.TeamItem;
import femr.common.models.TripItem;
import femr.data.models.core.IMissionTrip;

import java.util.List;

public interface IMissionTripService {
    /**
     * Retrieve the current trip information
     * @return the current trip or an error if one doesn't exist
     */
    IMissionTrip findCurrentMissionTrip();

    /**
     * Retrieve a list of the teams that are already in the database
     * @return a list of team names
     */
    ServiceResponse<List<String>> findAvailableTeams();

    /**
     * Retrieve a list of the cities that are already in the database
     * @return a list of cities with respective country
     */
    ServiceResponse<List<CityItem>> findAvailableCities();

    /**
     * Retrieve a list of the countries that are already in the database
     * @return a list of countries
     */
    ServiceResponse<List<String>> findAvailableCountries();

    /**
     * Get all available team and trip information
     *
     * @return all mission teams with their respective trips
     */
    ServiceResponse<List<MissionItem>> findAllTripInformation();

    /**
     * Create a new team
     *
     * @param teamItem the name is required
     * @return
     */
    ServiceResponse<TeamItem> createNewTeam(TeamItem teamItem);

    /**
     * Create a new trip
     *
     * @param tripItem everything except end date required
     * @return
     */
    ServiceResponse<TripItem> createNewTrip(TripItem tripItem);

    /**
     * Create a new city
     * @param cityName name of the city
     * @param countryName name of the country
     * @return name of the created city
     */
    ServiceResponse<CityItem> createNewCity(String cityName, String countryName);

    /**
     * Mark a trip as current and all others as not current
     *
     * @param tripId the id of the trip to mark current
     * @return the current trip
     */
    ServiceResponse<TripItem> updateCurrentTrip(int tripId);
}
