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
package femr.business.services.system;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.SqlUpdate;
import com.google.inject.Inject;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IMissionTripService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.TripItem;
import femr.data.daos.IRepository;
import femr.data.models.core.IMissionCity;
import femr.data.models.core.IMissionCountry;
import femr.data.models.core.IMissionTeam;
import femr.data.models.core.IMissionTrip;
import femr.data.models.mysql.MissionCity;
import femr.data.models.mysql.MissionCountry;
import femr.data.models.mysql.MissionTeam;
import femr.data.models.mysql.MissionTrip;

import java.util.List;

public class MissionTripService implements IMissionTripService {

    private final IRepository<IMissionCity> missionCityRepository;
    private final IRepository<IMissionCountry> missionCountryRepository;
    private final IRepository<IMissionTeam> missionTeamRepository;
    private final IRepository<IMissionTrip> missionTripRepository;
    private final DomainMapper domainMapper;

    @Inject
    public MissionTripService(IRepository<IMissionCity> missionCityRepository,
                              IRepository<IMissionCountry> missionCountryRepository,
                              IRepository<IMissionTeam> missionTeamRepository,
                              IRepository<IMissionTrip> missionTripRepository,
                              DomainMapper domainMapper) {

        this.missionCityRepository = missionCityRepository;
        this.missionCountryRepository = missionCountryRepository;
        this.missionTripRepository = missionTripRepository;
        this.missionTeamRepository = missionTeamRepository;
        this.domainMapper = domainMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TripItem> findCurrentMissionTrip() {

        ServiceResponse<TripItem> response = new ServiceResponse<>();
        IMissionTrip missionTrip = getCurrentMissionTrip();

        if (missionTrip == null) {

            response.setResponseObject(null);
            //    response.addError("", "there is not a current trip.");
        } else {

            response.setResponseObject(
                    DomainMapper.createTripItem(
                            missionTrip.getMissionTeam().getName(),
                            missionTrip.getMissionTeam().getLocation(),
                            missionTrip.getMissionCity().getName(),
                            missionTrip.getMissionCity().getMissionCountry().getName(),
                            missionTrip.getMissionTeam().getDescription(),
                            missionTrip.getStartDate(),
                            missionTrip.getEndDate()
                    )
            );
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> findAvailableTeams() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> findAvailableCities() {

        ServiceResponse<List<String>> response = new ServiceResponse<>();
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> findAvailableCountries() {

        ServiceResponse<List<String>> response = new ServiceResponse<>();
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TripItem> updateTrip(TripItem tripItem) {
        //ALL COMMENTS IN THIS METHOD ARE IN CAPS AND ANGRY
        ServiceResponse<TripItem> response = new ServiceResponse<>();
        boolean isNewTrip = false;

        //CHECK TRIP COUNTRY
        IMissionCountry missionCountry = null;
        ExpressionList<MissionCountry> missionCountryExpressionList = QueryProvider.getMissionCountryQuery()
                .where()
                .eq("name", tripItem.getCountry());
        try {
            missionCountry = missionCountryRepository.findOne(missionCountryExpressionList);
            if (missionCountry == null) {
                missionCountry = domainMapper.createMissionCountry(tripItem.getCountry());
                missionCountry = missionCountryRepository.create(missionCountry);
                isNewTrip = true;
            }
        } catch (Exception ex) {
            response.addError("", "error searching for the country, more than one might exist");
            return response;
        }

        //CHECK TRIP CITY
        IMissionCity missionCity = null;
        ExpressionList<MissionCity> missionCityExpressionList = QueryProvider.getMissionCityQuery()
                .where()
                .eq("name", tripItem.getCity());
        try {
            missionCity = missionCityRepository.findOne(missionCityExpressionList);
            if (missionCity == null || isNewTrip) {
                missionCity = domainMapper.createMissionCity(tripItem.getCity(), missionCountry);
                missionCity = missionCityRepository.create(missionCity);
                isNewTrip = true;
            }
        } catch (Exception ex) {
            response.addError("", "error searching for the city, more than one might exist");
            return response;
        }

        //CHECK TEAM NAME
        IMissionTeam missionTeam = null;
        ExpressionList<MissionTeam> missionTeamExpressionList = QueryProvider.getMissionTeamQuery()
                .where()
                .eq("name", tripItem.getTeam());
        try {
            missionTeam = missionTeamRepository.findOne(missionTeamExpressionList);
            if (missionTeam == null) {
                missionTeam = domainMapper.createMissionTeam(tripItem.getTeam(), tripItem.getTeamLocation(), tripItem.getDescription());
                missionTeam = missionTeamRepository.create(missionTeam);
                isNewTrip = true;
            } else {
                //the team was found, just update the teams description and location
                missionTeam.setDescription(tripItem.getDescription());
                missionTeam.setLocation(tripItem.getTeamLocation());
                missionTeam = missionTeamRepository.update(missionTeam);
            }
        } catch (Exception ex) {
            response.addError("", "error searching for the team, more than one might exist");
            return response;
        }

        //CHECK TRIP START AND END DATE
        ExpressionList<MissionTrip> missionTripExpressionList = QueryProvider.getMissionTripQuery()
                .where()
                .eq("mission_team_id", missionTeam.getId())
                .eq("mission_city_id", missionCity.getId())
                .eq("start_date", tripItem.getTripStartDate())
                .eq("end_date", tripItem.getTripEndDate());
        try {
            IMissionTrip missionTrip = missionTripRepository.findOne(missionTripExpressionList);
            if (missionTrip == null) {
                isNewTrip = true;
            }
        } catch (Exception ex) {
            response.addError("", "error checking start and end dates");
            return response;
        }

        //CREATE THE NEW TRIP IF NECESSARY, OTHERWISE DON'T
        if (isNewTrip) {
            IMissionTrip missionTrip = domainMapper.createMissionTrip(tripItem.getTripStartDate(), tripItem.getTripEndDate(), true, missionCity, missionTeam);
            if (missionTrip == null) {
                response.addError("", "serious problems are happening now");
                return response;
            } else {
                String setAllTripsToNotCurrent = "UPDATE `mission_trips` SET `isCurrent` = false";
                SqlUpdate update = Ebean.createSqlUpdate(setAllTripsToNotCurrent);
                try {
                    int modifiedCount = Ebean.execute(update);
                    if (modifiedCount > 0) {
                        //a current trip was removed
                    }
                } catch (Exception ex) {
                    response.addError("", "error updating current trips");
                    return response;
                }
                missionTrip = missionTripRepository.create(missionTrip);
            }
        } else {
            //do nothing
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<String> getTripInformation(){

        ServiceResponse<String> response = new ServiceResponse<>();

        return response;
    }

    /**
     * Get the current mission trip.
     *
     * @return null if none or more than one exists
     */
    private IMissionTrip getCurrentMissionTrip() {
        ExpressionList<MissionTrip> missionTripQuery = QueryProvider.getMissionTripQuery()
                .where()
                .eq("isCurrent", true);
        IMissionTrip missionTrip = null;

        try {
            missionTrip = missionTripRepository.findOne(missionTripQuery);
        } catch (Exception ex) {

        }

        return missionTrip;
    }
}
