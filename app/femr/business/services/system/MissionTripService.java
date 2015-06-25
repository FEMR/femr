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

import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.common.dtos.ServiceResponse;
import femr.common.models.CityItem;
import femr.common.models.MissionItem;
import femr.common.models.TeamItem;
import femr.common.models.TripItem;
import femr.util.stringhelpers.StringUtils;
import java.util.ArrayList;
import femr.business.services.core.IMissionTripService;
import femr.common.IItemModelMapper;
import femr.data.IDataModelMapper;
import femr.data.daos.core.ITripRepository;
import femr.data.models.core.IMissionCity;
import femr.data.models.core.IMissionCountry;
import femr.data.models.core.IMissionTeam;
import femr.data.models.core.IMissionTrip;
import java.util.List;

public class MissionTripService implements IMissionTripService {

    private final ITripRepository tripRepository;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public MissionTripService(ITripRepository tripRepository,
                              IDataModelMapper dataModelMapper,
                              @Named("identified") IItemModelMapper itemModelMapper) {

        this.tripRepository = tripRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> retrieveAvailableTeams() {

        ServiceResponse<List<String>> response = new ServiceResponse<>();
        List<String> teams = new ArrayList<>();

        List<? extends IMissionTeam> missionTeams = tripRepository.retrieveMissionTeamsOrderByName();

        for (IMissionTeam mt : missionTeams) {

            teams.add(mt.getName());
        }

        response.setResponseObject(teams);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<CityItem>> retrieveAvailableCities() {

        ServiceResponse<List<CityItem>> response = new ServiceResponse<>();
        List<CityItem> cities = new ArrayList<>();

        List<? extends IMissionCity> missionCities = tripRepository.retrieveMissionCitiesOrderByName();

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

        List<? extends IMissionCountry> missionCountries = tripRepository.retrieveMissionCountriesOrderByName();
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
            List<? extends IMissionTeam> missionTeams = tripRepository.retrieveMissionTeamsOrderByName();
            for (IMissionTeam missionTeam : missionTeams) {

                missionItems.add(itemModelMapper.createMissionItem(missionTeam));
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
    public ServiceResponse<TeamItem> createNewTeam(TeamItem teamItem) {

        ServiceResponse<TeamItem> response = new ServiceResponse<>();
        if (teamItem == null || StringUtils.isNullOrWhiteSpace(teamItem.getName())) {

            response.addError("", "team must have a name");
        } else {

            try {

                IMissionTeam missionTeam = dataModelMapper.createMissionTeam(teamItem.getName(), teamItem.getLocation(), teamItem.getDescription());
                missionTeam = tripRepository.saveMissionTeam(missionTeam);
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
                tripItem.getTripStartDate() == null) {

            response.addError("", "you're missing required fields, try again");
        } else {
            try {

                IMissionTeam missionTeam = tripRepository.retrieveMissionTeamByName(tripItem.getTeamName());

                IMissionCountry missionCountry = tripRepository.retrieveMissionCountryByName(tripItem.getTripCountry());

                if (missionCountry == null) {
                    //make sure we have a country to work with
                    response.addError("", "someone sent a bad country in the request");
                } else if (missionTeam == null) {
                    //make sure we have a team to work with
                    response.addError("", "someone sent a bad team name in the request");
                } else {

                    IMissionCity missionCity = tripRepository.retrieveMissionCityByNameAndCountry(tripItem.getTripCity(), missionCountry);

                    if (missionCity == null) {
                        //city doesn't exist
                        missionCity = dataModelMapper.createMissionCity(tripItem.getTripCity(), missionCountry);
                        missionCity = tripRepository.saveMissionCity(missionCity);
                    }

                    IMissionTrip missionTrip = dataModelMapper.createMissionTrip(tripItem.getTripStartDate(), tripItem.getTripEndDate(), false, missionCity, missionTeam);
                    missionTrip = tripRepository.saveMissionTrip(missionTrip);
                    response.setResponseObject(itemModelMapper.createTripItem(missionTrip.getMissionTeam().getName(), missionTrip.getMissionCity().getName(), missionTrip.getMissionCity().getMissionCountry().getName(), missionTrip.getStartDate(), missionTrip.getEndDate()));
                }
            } catch (Exception ex) {

                response.addError("", ex.getMessage());
            }
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<CityItem> createNewCity(String cityName, String countryName) {

        ServiceResponse<CityItem> response = new ServiceResponse<>();

        try {

            IMissionCountry missionCountry = tripRepository.retrieveMissionCountryByName(countryName);

            if (missionCountry == null) {

                response.addError("", "that country does not exist");
            } else {

                //check for duplicate
                List<? extends IMissionCity> missionCities = tripRepository.retrieveMissionCitiesOrderByName();
                boolean isDuplicate = false;
                for (IMissionCity mc : missionCities) {

                    if (mc.getName().toUpperCase().equals(cityName.toUpperCase())) {

                        isDuplicate = true;
                        response.addError("", "duplicate city");
                    }
                }
                if (!isDuplicate) {

                    IMissionCity missionCity = dataModelMapper.createMissionCity(cityName, missionCountry);
                    missionCity = tripRepository.saveMissionCity(missionCity);
                    response.setResponseObject(itemModelMapper.createCityItem(missionCity.getName(), missionCity.getMissionCountry().getName()));
                }
            }
        } catch (Exception ex) {

            response.addError("", "there was an issue saving the city");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<TripItem> updateCurrentTrip(int tripId) {

        ServiceResponse<TripItem> response = new ServiceResponse<>();
        try {

            IMissionTrip missionTrip = tripRepository.retrieveMissionTripById(tripId);

            if (missionTrip == null) {

                response.addError("", "could not find a trip with that id");
            } else {

                List<? extends IMissionTrip> allTrips = tripRepository.retrieveMissionTripsOrderByName();
                for (IMissionTrip mt : allTrips) {

                    if (mt.getId() == missionTrip.getId()) {

                        mt.setCurrent(true);
                    } else {

                        mt.setCurrent(false);
                    }

                    tripRepository.saveMissionTrip(mt);
                    response.setResponseObject(itemModelMapper.createTripItem(mt.getMissionTeam().getName(), mt.getMissionCity().getName(), mt.getMissionCity().getMissionCountry().getName(), mt.getStartDate(), mt.getEndDate()));
                }
            }

        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }
}
