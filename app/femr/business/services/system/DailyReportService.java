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
import femr.business.services.core.IDailyReportService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.DailyReportItem;
import femr.common.models.WhoReportConfigItem;
import femr.data.daos.IRepository;
import femr.data.daos.core.IDailyReportRepository;
import femr.data.models.core.IMissionTrip;
import femr.data.models.core.IWhoReportConfig;
import femr.data.models.mysql.WhoReportConfig;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.Logger;

import java.util.List;
import java.util.Map;

public class DailyReportService implements IDailyReportService {

    private static final DateTimeFormatter MDS_DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");

    private final IDailyReportRepository dailyReportRepository;
    private final IRepository<IWhoReportConfig> whoConfigRepository;

    @Inject
    public DailyReportService(IDailyReportRepository dailyReportRepository,
                              IRepository<IWhoReportConfig> whoConfigRepository) {
        this.dailyReportRepository = dailyReportRepository;
        this.whoConfigRepository = whoConfigRepository;
    }

    @Override
    public ServiceResponse<DailyReportItem> generateDailyReport(int tripId, DateTime date) {
        ServiceResponse<DailyReportItem> response = new ServiceResponse<>();

        try {
            DailyReportItem reportItem = new DailyReportItem();

            IMissionTrip missionTrip = dailyReportRepository.getMissionTripById(tripId);
            if (missionTrip == null) {
                response.addError("tripId", "Mission trip not found for ID: " + tripId);
                return response;
            }

            if (missionTrip.getMissionTeam() != null) {
                reportItem.setTeamName(missionTrip.getMissionTeam().getName());
            }
            if (missionTrip.getMissionCity() != null) {
                reportItem.setTripCity(missionTrip.getMissionCity().getName());
                if (missionTrip.getMissionCity().getMissionCountry() != null) {
                    reportItem.setTripCountry(missionTrip.getMissionCity().getMissionCountry().getName());
                }
            }
            if (missionTrip.getEndDate() != null) {
                DateTime endDate = new DateTime(missionTrip.getEndDate());
                reportItem.setDepartureDate(endDate.toString(MDS_DATE_FORMAT));
            }

            reportItem.setReportDate(date.toString(MDS_DATE_FORMAT));

            Map<String, Map<String, Integer>> demographicCounts = dailyReportRepository.getDemographicCounts(tripId, date);
            mapDemographicCounts(reportItem, demographicCounts);

            reportItem.setWhoHealthEventCounts(dailyReportRepository.getHealthEventCounts(tripId, date));
            reportItem.setWhoProcedureCounts(dailyReportRepository.getProcedureCounts(tripId, date));

            response.setResponseObject(reportItem);

        } catch (Exception ex) {
            Logger.error("DailyReportService-generateDailyReport", ex);
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    @Override
    public ServiceResponse<WhoReportConfigItem> getWhoReportConfig(int tripId) {
        ServiceResponse<WhoReportConfigItem> response = new ServiceResponse<>();
        try {
            List<? extends IWhoReportConfig> rows = whoConfigRepository.findAll(WhoReportConfig.class);
            IWhoReportConfig entity = rows.stream()
                    .filter(r -> r.getMissionTripId() == tripId)
                    .findFirst()
                    .orElse(null);
            WhoReportConfigItem item = new WhoReportConfigItem();
            if (entity != null) {
                item.setOrgName(entity.getOrgName());
                item.setType1Mobile(entity.isType1Mobile());
                item.setType1Fixed(entity.isType1Fixed());
                item.setType2(entity.isType2());
                item.setType3(entity.isType3());
                item.setSpecializedCell(entity.isSpecializedCell());
                item.setContactPersons(entity.getContactPersons());
                item.setPhoneNo(entity.getPhoneNo());
                item.setEmail(entity.getEmail());
                item.setStateAdmin1(entity.getStateAdmin1());
                item.setVillageAdmin3(entity.getVillageAdmin3());
                item.setFacilityName(entity.getFacilityName());
                item.setGeoLat(entity.getGeoLat());
                item.setGeoLong(entity.getGeoLong());
            }
            response.setResponseObject(item);
        } catch (Exception ex) {
            Logger.error("DailyReportService-getWhoReportConfig", ex);
            response.addError("exception", ex.getMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<Void> saveWhoReportConfig(int tripId, WhoReportConfigItem config) {
        ServiceResponse<Void> response = new ServiceResponse<>();
        try {
            List<? extends IWhoReportConfig> rows = whoConfigRepository.findAll(WhoReportConfig.class);
            IWhoReportConfig entity = rows.stream()
                    .filter(r -> r.getMissionTripId() == tripId)
                    .findFirst()
                    .orElse(null);
            boolean isNew = (entity == null);
            if (isNew) {
                entity = new WhoReportConfig();
                entity.setMissionTripId(tripId);
            }
            entity.setOrgName(config.getOrgName());
            entity.setType1Mobile(config.isType1Mobile());
            entity.setType1Fixed(config.isType1Fixed());
            entity.setType2(config.isType2());
            entity.setType3(config.isType3());
            entity.setSpecializedCell(config.isSpecializedCell());
            entity.setContactPersons(config.getContactPersons());
            entity.setPhoneNo(config.getPhoneNo());
            entity.setEmail(config.getEmail());
            entity.setStateAdmin1(config.getStateAdmin1());
            entity.setVillageAdmin3(config.getVillageAdmin3());
            entity.setFacilityName(config.getFacilityName());
            entity.setGeoLat(config.getGeoLat());
            entity.setGeoLong(config.getGeoLong());
            if (isNew) {
                whoConfigRepository.create(entity);
            } else {
                whoConfigRepository.update(entity);
            }
        } catch (Exception ex) {
            Logger.error("DailyReportService-saveWhoReportConfig", ex);
            response.addError("exception", ex.getMessage());
        }
        return response;
    }

    private void mapDemographicCounts(DailyReportItem reportItem, Map<String, Map<String, Integer>> counts) {
        Map<String, Integer> maleCounts = counts.get("MALE");
        if (maleCounts != null) {
            reportItem.setMaleUnder1(maleCounts.getOrDefault("UNDER_1", 0));
            reportItem.setMale1To4(maleCounts.getOrDefault("AGE_1_TO_4", 0));
            reportItem.setMale5To17(maleCounts.getOrDefault("AGE_5_TO_17", 0));
            reportItem.setMale18To64(maleCounts.getOrDefault("AGE_18_TO_64", 0));
            reportItem.setMale65Plus(maleCounts.getOrDefault("AGE_65_PLUS", 0));
        }

        Map<String, Integer> femaleNonPregnantCounts = counts.get("FEMALE_NON_PREGNANT");
        if (femaleNonPregnantCounts != null) {
            reportItem.setFemaleNonPregnantUnder1(femaleNonPregnantCounts.getOrDefault("UNDER_1", 0));
            reportItem.setFemaleNonPregnant1To4(femaleNonPregnantCounts.getOrDefault("AGE_1_TO_4", 0));
            reportItem.setFemaleNonPregnant5To17(femaleNonPregnantCounts.getOrDefault("AGE_5_TO_17", 0));
            reportItem.setFemaleNonPregnant18To64(femaleNonPregnantCounts.getOrDefault("AGE_18_TO_64", 0));
            reportItem.setFemaleNonPregnant65Plus(femaleNonPregnantCounts.getOrDefault("AGE_65_PLUS", 0));
        }

        Map<String, Integer> femalePregnantCounts = counts.get("FEMALE_PREGNANT");
        if (femalePregnantCounts != null) {
            reportItem.setFemalePregnantUnder1(femalePregnantCounts.getOrDefault("UNDER_1", 0));
            reportItem.setFemalePregnant1To4(femalePregnantCounts.getOrDefault("AGE_1_TO_4", 0));
            reportItem.setFemalePregnant5To17(femalePregnantCounts.getOrDefault("AGE_5_TO_17", 0));
            reportItem.setFemalePregnant18To64(femalePregnantCounts.getOrDefault("AGE_18_TO_64", 0));
            reportItem.setFemalePregnant65Plus(femalePregnantCounts.getOrDefault("AGE_65_PLUS", 0));
        }
    }
}
