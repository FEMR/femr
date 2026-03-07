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

import io.ebean.Ebean;
import io.ebean.SqlRow;
import femr.data.daos.core.IDailyReportRepository;
import femr.data.models.core.IMissionTrip;
import femr.business.helpers.QueryProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyReportRepository implements IDailyReportRepository {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");

    private volatile Integer weeksPregnantVitalId;

    private Integer getWeeksPregnantVitalId() {
        if (weeksPregnantVitalId == null) {
            synchronized (this) {
                if (weeksPregnantVitalId == null) {
                    String sql = "SELECT id FROM vitals WHERE name = 'weeksPregnant' AND isDeleted = 0 LIMIT 1";
                    SqlRow row = Ebean.createSqlQuery(sql).findOne();
                    weeksPregnantVitalId = (row != null) ? row.getInteger("id") : -1;
                }
            }
        }
        return weeksPregnantVitalId == -1 ? null : weeksPregnantVitalId;
    }

    @Override
    public Map<String, Map<String, Integer>> getDemographicCounts(int tripId, DateTime date) {
        String dateStart = date.toString(DATE_FORMAT);
        String dateEnd = date.plusDays(1).toString(DATE_FORMAT);
        Integer pregnancyVitalId = getWeeksPregnantVitalId();

        String sql =
                "SELECT " +
                        "  age_category, " +
                        "  sex_category, " +
                        "  COUNT(*) as cnt " +
                        "FROM ( " +
                        "  SELECT " +
                        "    CASE " +
                        "      WHEN TIMESTAMPDIFF(MONTH, p.age, :dateStart) < 12 THEN 'UNDER_1' " +
                        "      WHEN TIMESTAMPDIFF(YEAR, p.age, :dateStart) BETWEEN 1 AND 4 THEN 'AGE_1_TO_4' " +
                        "      WHEN TIMESTAMPDIFF(YEAR, p.age, :dateStart) BETWEEN 5 AND 17 THEN 'AGE_5_TO_17' " +
                        "      WHEN TIMESTAMPDIFF(YEAR, p.age, :dateStart) BETWEEN 18 AND 64 THEN 'AGE_18_TO_64' " +
                        "      ELSE 'AGE_65_PLUS' " +
                        "    END AS age_category, " +
                        "    CASE " +
                        "      WHEN p.sex = 'Male' THEN 'MALE' " +
                        "      WHEN pev.vital_value IS NOT NULL AND pev.vital_value > 0 THEN 'FEMALE_PREGNANT' " +
                        "      ELSE 'FEMALE_NON_PREGNANT' " +
                        "    END AS sex_category " +
                        "  FROM patient_encounters pe " +
                        "  JOIN patients p ON pe.patient_id = p.id " +
                        "  LEFT JOIN patient_encounter_vitals pev ON pe.id = pev.patient_encounter_id " +
                        "    AND pev.vital_id = :pregnancyVitalId " +
                        "  WHERE pe.mission_trip_id = :tripId " +
                        "    AND pe.date_of_triage_visit >= :dateStart " +
                        "    AND pe.date_of_triage_visit < :dateEnd " +
                        "    AND pe.isDeleted IS NULL " +
                        "    AND p.isDeleted IS NULL " +
                        ") AS categorized " +
                        "GROUP BY age_category, sex_category";

        try {
            List<SqlRow> rows = Ebean.createSqlQuery(sql)
                    .setParameter("tripId", tripId)
                    .setParameter("dateStart", dateStart)
                    .setParameter("dateEnd", dateEnd)
                    .setParameter("pregnancyVitalId", pregnancyVitalId != null ? pregnancyVitalId : -1)
                    .findList();

            Map<String, Map<String, Integer>> results = new HashMap<>();
            for (SqlRow row : rows) {
                String sexCategory = row.getString("sex_category");
                String ageCategory = row.getString("age_category");
                Integer count = row.getInteger("cnt");

                results.computeIfAbsent(sexCategory, k -> new HashMap<>())
                        .put(ageCategory, count != null ? count : 0);
            }
            return results;
        } catch (Exception ex) {
            Logger.error("DailyReportRepository-getDemographicCounts", ex);
            throw ex;
        }
    }

    @Override
    public IMissionTrip getMissionTripById(int tripId) {
        try {
            return QueryProvider.getMissionTripQuery()
                    .fetch("missionTeam")
                    .fetch("missionCity")
                    .fetch("missionCity.missionCountry")
                    .where()
                    .eq("id", tripId)
                    .findOne();
        } catch (Exception ex) {
            Logger.error("DailyReportRepository-getMissionTripById", ex);
            throw ex;
        }
    }
}