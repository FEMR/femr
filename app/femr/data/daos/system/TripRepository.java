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
import femr.business.helpers.QueryProvider;
import femr.data.daos.core.ITripRepository;
import femr.data.models.core.IMissionCity;
import femr.data.models.core.IMissionCountry;
import femr.data.models.core.IMissionTeam;
import femr.data.models.core.IMissionTrip;
import femr.data.models.mysql.MissionCity;
import femr.data.models.mysql.MissionCountry;
import femr.data.models.mysql.MissionTeam;
import femr.data.models.mysql.MissionTrip;
import femr.util.stringhelpers.StringUtils;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

public class TripRepository implements ITripRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public IMissionTrip retrieveCurrentMissionTrip() {

        ExpressionList<MissionTrip> expressionList = getMissionTripQuery()
                .where()
                .eq("isCurrent", true);

        IMissionTrip missionTrip = null;
        try {

            missionTrip = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("TripRepository-retrieveCurrentMissionTrip", ex.getMessage());
        }

        return missionTrip;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IMissionCity> retrieveMissionCitiesOrderByName() {

        Query<MissionCity> query = getMissionCityQuery()
                .orderBy("name");

        List<? extends IMissionCity> missionTeams = new ArrayList<>();

        try {

            missionTeams = query.findList();
        } catch (Exception ex) {

            Logger.error("TripRepository-retrieveMissionCitiesOrderByName", ex.getMessage());
        }

        return missionTeams;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMissionCity retrieveMissionCityByNameAndCountry(String name, IMissionCountry missionCountry) {

        if (StringUtils.isNullOrWhiteSpace(name) || missionCountry == null) {

            return null;
        }

        ExpressionList<MissionCity> expressionList = getMissionCityQuery()
                .where()
                .eq("name", name)
                .eq("missionCountry", missionCountry);

        IMissionCity missionCity = null;
        try {

            missionCity = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("TripRepository-retrieveMissionCityByNameAndCountry", ex.getMessage());
        }

        return missionCity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IMissionCountry> retrieveMissionCountriesOrderByName() {

        Query<MissionCountry> query = getMissionCountryQuery()
                .orderBy("name");

        List<? extends IMissionCountry> missionCountries = new ArrayList<>();

        try {

            missionCountries = query.findList();
        } catch (Exception ex) {

            Logger.error("TripRepository-retrieveMissionCountriesOrderByName", ex.getMessage());
        }

        return missionCountries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMissionCountry retrieveMissionCountryByName(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        ExpressionList<MissionCountry> expressionList = getMissionCountryQuery()
                .where()
                .eq("name", name);

        IMissionCountry missionCountry = null;
        try {

            missionCountry = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("TripRepository-retrieveMissionCountryByName", ex.getMessage());
        }

        return missionCountry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMissionTeam retrieveMissionTeamByName(String name) {

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        ExpressionList<MissionTeam> expressionList = getMissionTeamQuery()
                .where()
                .eq("name", name);

        IMissionTeam missionTeam = null;
        try {

            missionTeam = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("TripRepository-retrieveMissionTeamByName", ex.getMessage());
        }

        return missionTeam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IMissionTeam> retrieveMissionTeamsOrderByName() {

        Query<MissionTeam> query = getMissionTeamQuery()
                .orderBy("name");

        List<? extends IMissionTeam> missionTeams = new ArrayList<>();

        try {

            missionTeams = query.findList();
        } catch (Exception ex) {

            Logger.error("TripRepository-retrieveMissionTeamsOrderByName", ex.getMessage());
        }

        return missionTeams;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMissionTrip retrieveMissionTripById(int id) {

        ExpressionList<MissionTrip> expressionList = getMissionTripQuery()
                .where()
                .eq("id", id);

        IMissionTrip missionTrip = null;
        try {

            missionTrip = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("TripRepository-retrieveMissionTripById", ex.getMessage());
        }

        return missionTrip;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IMissionTrip> retrieveMissionTripsOrderByName() {

        Query<MissionTrip> query = getMissionTripQuery()
                .orderBy("name");

        List<? extends IMissionTrip> missionTrips = new ArrayList<>();

        try {

            missionTrips = query.findList();
        } catch (Exception ex) {

            Logger.error("TripRepository-retrieveMissionTripsOrderByName", ex.getMessage());
        }

        return missionTrips;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMissionCity saveMissionCity(IMissionCity missionCity) {

        if (missionCity == null) {

            return null;
        }

        try {

            Ebean.save(missionCity);
        } catch (Exception ex) {

            Logger.error("TripRepository-saveMissionCity", ex.getMessage());
        }

        return missionCity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMissionCountry saveMissionCountry(IMissionCountry missionCountry) {

        if (missionCountry == null) {

            return null;
        }

        try {

            Ebean.save(missionCountry);
        } catch (Exception ex) {

            Logger.error("TripRepository-saveMissionCountry", ex.getMessage());
        }

        return missionCountry;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMissionTeam saveMissionTeam(IMissionTeam missionTeam) {

        if (missionTeam == null) {

            return null;
        }

        try {

            Ebean.save(missionTeam);
        } catch (Exception ex) {

            Logger.error("TripRepository-saveMissionTeam", ex.getMessage());
        }

        return missionTeam;
    }

    public IMissionTrip saveMissionTrip(IMissionTrip missionTrip) {

        if (missionTrip == null) {

            return null;
        }

        try {

            Ebean.save(missionTrip);
        } catch (Exception ex) {

            Logger.error("TripRepository-saveMissionTrip", ex.getMessage());
        }

        return missionTrip;
    }

    /**
     * Provides the Ebean object to start building queries
     * eams
     *
     * @return The mission city type Query object
     */
    public static Query<MissionCity> getMissionCityQuery() {

        return Ebean.find(MissionCity.class);
    }

    /**
     * Provides the Ebean object to start building queries
     *
     * @return The mission country type Query object
     */
    public static Query<MissionCountry> getMissionCountryQuery() {

        return Ebean.find(MissionCountry.class);
    }

    /**
     * Provides the Ebean object to start building queries
     *
     * @return The mission team type Query object
     */
    public static Query<MissionTeam> getMissionTeamQuery() {

        return Ebean.find(MissionTeam.class);
    }

    /**
     * Provides the Ebean object to start building queries
     *
     * @return The mission trip type Query object
     */
    public static Query<MissionTrip> getMissionTripQuery() {

        return Ebean.find(MissionTrip.class);
    }
}
