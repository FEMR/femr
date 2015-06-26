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
     * Retrieve a list of all team names.
     *
     * @return a service response that contains a list of available teams as Strings
     * and/or errors if they exist.
     */
    ServiceResponse<List<String>> retrieveAvailableTeams();

    /**
     * Retrieve a list of all cities.
     *
     * @return a service response that contains a list of available cities as CityItems
     * and/or errors if they exist.
     */
    ServiceResponse<List<CityItem>> retrieveAvailableCities();

    /**
     * Retrieve a list of all countries.
     *
     * @return a service response that contains a list of available countries as Strings
     * and/or errors if they exist.
     */
    ServiceResponse<List<String>> retrieveAvailableCountries();

    /**
     * Get a comprehensive list of trip information
     *
     * @return a service response that contains a list of trip information
     * and/or errors if they exist.
     */
    ServiceResponse<List<MissionItem>> retrieveAllTripInformation();

    /**
     * Create a new team.
     *
     * @param teamItem - the name is required TODO: separate into parameters
     * @return a service response that contains a new TeamItem that was created
     * and/or errors if they exist.
     */
    ServiceResponse<TeamItem> createNewTeam(TeamItem teamItem);

    /**
     * Create a new trip
     *
     * @param tripItem everything except end date required TODO: separate into parameters
     * @return a service response that contains a new TripItem that was created
     * and/or errors if they exist.
     */
    ServiceResponse<TripItem> createNewTrip(TripItem tripItem);

    /**
     * Create a new city
     *
     * @param cityName name of the city, TODO: make not nullable
     * @param countryName name of the country, TODO: make not nullable
     * @return a service response that contains a new CityItem that was created
     * and/or errors if they exist.
     */
    ServiceResponse<CityItem> createNewCity(String cityName, String countryName);

    /**
     * Mark a trip as current and all others as not current
     *
     * @param tripId the id of the trip to mark current
     * @return a service response that contains a new TripItem that is now current
     * and/or errors if they exist.
     */
    ServiceResponse<TripItem> updateCurrentTrip(int tripId);
}
