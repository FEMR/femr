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

import femr.data.models.core.IMissionCity;
import femr.data.models.core.IMissionCountry;
import femr.data.models.core.IMissionTeam;
import femr.data.models.core.IMissionTrip;

import java.util.List;

/**
 * A repository to cover the following tables:
 * <ul>
 * <li>mission_cities</li>
 * <li>mission_countries</li>
 * <li>mission_teams</li>
 * <li>mission_trips</li>
 * </ul>
 */
public interface ITripRepository {

    /**
     * Retrieve the current mission trip.
     *
     * @return the IMissionTrip that is active or null if an error occurs
     */
    IMissionTrip retrieveCurrentMissionTrip();

    /**
     * Retrieve a mission city by its name.
     *
     * @return all of the mission cities or an empty list
     */
    List<? extends IMissionCity> retrieveMissionCitiesOrderByName();

    /**
     * Retrieve a mission city by its name and its country (should only be 1)
     *
     * @param city name of the city, not null
     * @param missionCountry the country data object, not null
     * @returna mission city or null if either parameter is null/an error occurred
     */
    IMissionCity retrieveMissionCityByNameAndCountry(String name, IMissionCountry missionCountry);

    /**
     * Retrieve a mission country by its name.
     *
     * @return all of the mission countries or an empty list
     */
    List<? extends IMissionCountry> retrieveMissionCountriesOrderByName();

    /**
     * Retrieve a mission country by its name.
     *
     * @param name name of the mission country, not null
     * @return the mission country or null if the parameter is null/an error occurred
     */
    IMissionCountry retrieveMissionCountryByName(String name);

    /**
     * Retrieve a mission team by its name.
     *
     * @param name name of the mission team, not null
     * @return the mission team or null if the parameter is null/an error occurred
     */
    IMissionTeam retrieveMissionTeamByName(String name);

    /**
     * Retrieve a mission team by its name.
     *
     * @return all of the mission teams or an empty array
     */
    List<? extends IMissionTeam> retrieveMissionTeamsOrderByName();

    /**
     * Retrieve the current mission trip.
     *
     * @param id id (primary key) of the mission trip
     * @return the IMissionTrip that is active or null if an error occurs
     */
    IMissionTrip retrieveMissionTripById(int id);

    /**
     * Retrieve a mission trip by its name.
     *
     * @return all of the mission trips or an empty array
     */
    List<? extends IMissionTrip> retrieveMissionTripsOrderByName();

    /**
     * Updates or creates a mission city
     *
     * @param missionCity the mission city being updated or created, not null
     * @return the updated or created mission city or null if parameter is null
     */
    IMissionCity saveMissionCity(IMissionCity missionCity);

    /**
     * Updates or creates a mission country
     *
     * @param missionCountry the mission country being updated or created, not null
     * @return the updated or created mission country or null if parameter is null
     */
    IMissionCountry saveMissionCountry(IMissionCountry missionCountry);

    /**
     * Updates or creates a mission team
     *
     * @param missionTeam the mission team being updated or created, not null
     * @return the updated or created mission team or null if parameter is null
     */
    IMissionTeam saveMissionTeam(IMissionTeam missionTeam);

    /**
     * Updates or creates a mission trip
     *
     * @param missionTrip the mission trip being updated or created, not null
     * @return the updated or created mission trip or null if parameter is null
     */
    IMissionTrip saveMissionTrip(IMissionTrip missionTrip);
}
