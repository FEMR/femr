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

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IMissionTripService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.stringhelpers.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MissionTripService implements IMissionTripService {

    private final IRepository<IMissionCity> missionCityRepository;
    private final IRepository<IMissionCountry> missionCountryRepository;
    private final IRepository<IMissionTeam> missionTeamRepository;
    private final IRepository<IMissionTrip> missionTripRepository;
    private final IRepository<IUser> userRepository;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public MissionTripService(IRepository<IMissionCity> missionCityRepository,
                              IRepository<IMissionCountry> missionCountryRepository,
                              IRepository<IMissionTeam> missionTeamRepository,
                              IRepository<IMissionTrip> missionTripRepository,
                              IRepository<IUser> userRepository,
                              IDataModelMapper dataModelMapper,
                              @Named("identified") IItemModelMapper itemModelMapper) {

        this.missionCityRepository = missionCityRepository;
        this.missionCountryRepository = missionCountryRepository;
        this.missionTripRepository = missionTripRepository;
        this.missionTeamRepository = missionTeamRepository;
        this.userRepository = userRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMissionTrip retrieveCurrentMissionTrip() {

        return getCurrentMissionTrip();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<CityItem>> retrieveAvailableCities() {

        ServiceResponse<List<CityItem>> response = new ServiceResponse<>();
        List<CityItem> cities = new ArrayList<>();

        Query<MissionCity> missionCityQuery = QueryProvider.getMissionCityQuery()
                .orderBy("name");
        List<? extends IMissionCity> missionCities = missionCityRepository.find(missionCityQuery);

        for (IMissionCity mc : missionCities) {

            cities.add(itemModelMapper.createCityItem(mc.getName(), mc.getMissionCountry().getName()));
        }

        response.setResponseObject(cities);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> retrieveAvailableCountries() {

        ServiceResponse<List<String>> response = new ServiceResponse<>();
        List<String> countries = new ArrayList<>();
        Query<MissionCountry> missionCountryQuery = QueryProvider.getMissionCountryQuery()
                .orderBy("name");
        List<? extends IMissionCountry> missionCountries = missionCountryRepository.find(missionCountryQuery);
        for (IMissionCountry mc : missionCountries) {
            countries.add(mc.getName());
        }
        response.setResponseObject(countries);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<MissionItem>> retrieveAllTripInformation() {

        ServiceResponse<List<MissionItem>> response = new ServiceResponse<>();
        List<MissionItem> missionItems = new ArrayList<>();

        try {
            //start by getting all available mission teams. If you start by getting all the trips then
            //you might miss some teams that don't have a trip, yet.
            List<? extends IMissionTeam> missionTeams = missionTeamRepository.findAll(MissionTeam.class);

            List<MissionTripItem> missionTripItems;


            for (IMissionTeam missionTeam : missionTeams) {

                missionTripItems = new ArrayList<>();

                missionTripItems.addAll(missionTeam.getMissionTrips()
                        .stream()
                        .map(itemModelMapper::createMissionTripItem)
                        .collect(Collectors.toList()));

                missionItems.add(itemModelMapper.createMissionItem(missionTeam, missionTripItems));
            }

            response.setResponseObject(missionItems);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<MissionTripItem>> retrieveAllTripInformationByUserId(int userId) {

        ServiceResponse<List<MissionTripItem>> response = new ServiceResponse<>();
        List<MissionTripItem> missionTripItems = new ArrayList<>();

        try {

            ExpressionList<User> query = QueryProvider.getUserQuery().fetch("roles").where().eq("id", userId);

            IUser user = userRepository.findOne(query);


            //start by getting all available mission teams. If you start by getting all the trips then
            //you might miss some teams that don't have a trip, yet.


            missionTripItems.addAll(user.getMissionTrips()
                    .stream()
                    .map(itemModelMapper::createMissionTripItem)
                    .collect(Collectors.toList()));

            response.setResponseObject(missionTripItems);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<TeamItem> createNewTeam(TeamItem teamItem) {

        ServiceResponse<TeamItem> response = new ServiceResponse<>();
        if (teamItem == null || StringUtils.isNullOrWhiteSpace(teamItem.getName())) {

            response.addError("", "team must have a name");
        } else {

            try {

                IMissionTeam missionTeam = dataModelMapper.createMissionTeam(teamItem.getName(), teamItem.getLocation(), teamItem.getDescription());
                missionTeam = missionTeamRepository.create(missionTeam);
                response.setResponseObject(itemModelMapper.createTeamItem(missionTeam.getName(), missionTeam.getLocation(), missionTeam.getDescription()));
            } catch (Exception ex) {

                response.addError("", ex.getMessage());
            }
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<TripItem> createNewTrip(TripItem tripItem) {

        ServiceResponse<TripItem> response = new ServiceResponse<>();
        if (tripItem == null ||
                StringUtils.isNullOrWhiteSpace(tripItem.getTeamName()) ||
                StringUtils.isNullOrWhiteSpace(tripItem.getTripCity()) ||
                StringUtils.isNullOrWhiteSpace(tripItem.getTripCountry()) ||
                tripItem.getTripStartDate() == null ||
                tripItem.getTripEndDate() == null) {
            response.addError("", "you're missing required fields, try again");
        } else {
            try {


                ExpressionList<MissionTeam> missionTeamExpressionList = QueryProvider.getMissionTeamQuery()
                        .where()
                        .eq("name", tripItem.getTeamName());
                IMissionTeam missionTeam = missionTeamRepository.findOne(missionTeamExpressionList);

                ExpressionList<MissionCountry> missionCountryExpressionList = QueryProvider.getMissionCountryQuery()
                        .where()
                        .eq("name", tripItem.getTripCountry());
                IMissionCountry missionCountry = missionCountryRepository.findOne(missionCountryExpressionList);

                if (missionCountry == null) {
                    //make sure we have a country to work with
                    response.addError("", "country does not exist");
                } else if (missionTeam == null) {
                    //make sure we have a team to work with
                    response.addError("", "team does not exist");
                } else {

                    ExpressionList<MissionCity> missionCityExpressionList = QueryProvider.getMissionCityQuery()
                            .where()
                            .eq("name", tripItem.getTripCity())
                            .eq("missionCountry", missionCountry);
                    IMissionCity missionCity = missionCityRepository.findOne(missionCityExpressionList);

                    if (missionCity == null) {
                        //city doesn't exist
                        missionCity = dataModelMapper.createMissionCity(tripItem.getTripCity(), missionCountry);
                        missionCity = missionCityRepository.create(missionCity);
                    }


                    IMissionTrip missionTrip = dataModelMapper.createMissionTrip(tripItem.getTripStartDate(), tripItem.getTripEndDate(), missionCity, missionTeam);
                    missionTrip = missionTripRepository.create(missionTrip);
                    response.setResponseObject(itemModelMapper.createTripItem(missionTrip.getMissionTeam().getName(), missionTrip.getMissionCity().getName(), missionTrip.getMissionCity().getMissionCountry().getName(), missionTrip.getStartDate(), missionTrip.getEndDate()));

                }
            } catch (Exception ex) {

                response.addError("", ex.getMessage());
            }
        }

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

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<CityItem> createNewCity(String cityName, String countryName) {

        ServiceResponse<CityItem> response = new ServiceResponse<>();

        if (cityName == null || StringUtils.isNullOrWhiteSpace(cityName)){

            response.addError("", "city must have a name");
            return response;
        }

        if (countryName == null || StringUtils.isNullOrWhiteSpace(countryName)){

            response.addError("", "country must have a name");
            return response;
        }

        ExpressionList<MissionCountry> missionCountryExpressionList = QueryProvider.getMissionCountryQuery()
                .where()
                .eq("name", countryName);
        try {

            IMissionCountry missionCountry = missionCountryRepository.findOne(missionCountryExpressionList);
            if (missionCountry == null) {
                response.addError("", "that country does not exist");
            } else {

                //check for duplicate
                List<? extends IMissionCity> missionCities = missionCityRepository.findAll(MissionCity.class);
                boolean isDuplicate = false;
                for (IMissionCity mc : missionCities) {
                    if (mc.getName().toUpperCase().equals(cityName.toUpperCase())) {
                        isDuplicate = true;
                        response.addError("", "duplicate city");
                    }
                }
                if (!isDuplicate) {
                    IMissionCity missionCity = dataModelMapper.createMissionCity(cityName, missionCountry);
                    missionCity = missionCityRepository.create(missionCity);
                    response.setResponseObject(itemModelMapper.createCityItem(missionCity.getName(), missionCity.getMissionCountry().getName()));
                }
            }
        } catch (Exception ex) {

            response.addError("", "there was an issue saving the city");
        }

        return response;
    }
}
