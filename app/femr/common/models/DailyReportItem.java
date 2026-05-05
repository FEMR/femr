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
package femr.common.models;

import java.util.Collections;
import java.util.Map;

public class DailyReportItem {

    private String teamName;
    private String tripCity;
    private String tripCountry;
    private String departureDate;
    private String reportDate;

    private int maleUnder1;
    private int male1To4;
    private int male5To17;
    private int male18To64;
    private int male65Plus;

    private int femaleNonPregnantUnder1;
    private int femaleNonPregnant1To4;
    private int femaleNonPregnant5To17;
    private int femaleNonPregnant18To64;
    private int femaleNonPregnant65Plus;

    private int femalePregnantUnder1;
    private int femalePregnant1To4;
    private int femalePregnant5To17;
    private int femalePregnant18To64;
    private int femalePregnant65Plus;

    // WHO health event counts: eventName -> { "lt5" -> count, "gte5" -> count }
    private Map<String, Map<String, Integer>> whoHealthEventCounts = Collections.emptyMap();

    // WHO procedure counts: procedureName -> { "lt5" -> count, "gte5" -> count }
    private Map<String, Map<String, Integer>> whoProcedureCounts = Collections.emptyMap();

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getTripCity() { return tripCity; }
    public void setTripCity(String tripCity) { this.tripCity = tripCity; }

    public String getTripCountry() { return tripCountry; }
    public void setTripCountry(String tripCountry) { this.tripCountry = tripCountry; }

    public String getDepartureDate() { return departureDate; }
    public void setDepartureDate(String departureDate) { this.departureDate = departureDate; }

    public String getReportDate() { return reportDate; }
    public void setReportDate(String reportDate) { this.reportDate = reportDate; }

    public int getMaleUnder1() { return maleUnder1; }
    public void setMaleUnder1(int maleUnder1) { this.maleUnder1 = maleUnder1; }

    public int getMale1To4() { return male1To4; }
    public void setMale1To4(int male1To4) { this.male1To4 = male1To4; }

    public int getMale5To17() { return male5To17; }
    public void setMale5To17(int male5To17) { this.male5To17 = male5To17; }

    public int getMale18To64() { return male18To64; }
    public void setMale18To64(int male18To64) { this.male18To64 = male18To64; }

    public int getMale65Plus() { return male65Plus; }
    public void setMale65Plus(int male65Plus) { this.male65Plus = male65Plus; }

    public int getFemaleNonPregnantUnder1() { return femaleNonPregnantUnder1; }
    public void setFemaleNonPregnantUnder1(int v) { this.femaleNonPregnantUnder1 = v; }

    public int getFemaleNonPregnant1To4() { return femaleNonPregnant1To4; }
    public void setFemaleNonPregnant1To4(int v) { this.femaleNonPregnant1To4 = v; }

    public int getFemaleNonPregnant5To17() { return femaleNonPregnant5To17; }
    public void setFemaleNonPregnant5To17(int v) { this.femaleNonPregnant5To17 = v; }

    public int getFemaleNonPregnant18To64() { return femaleNonPregnant18To64; }
    public void setFemaleNonPregnant18To64(int v) { this.femaleNonPregnant18To64 = v; }

    public int getFemaleNonPregnant65Plus() { return femaleNonPregnant65Plus; }
    public void setFemaleNonPregnant65Plus(int v) { this.femaleNonPregnant65Plus = v; }

    public int getFemalePregnantUnder1() { return femalePregnantUnder1; }
    public void setFemalePregnantUnder1(int v) { this.femalePregnantUnder1 = v; }

    public int getFemalePregnant1To4() { return femalePregnant1To4; }
    public void setFemalePregnant1To4(int v) { this.femalePregnant1To4 = v; }

    public int getFemalePregnant5To17() { return femalePregnant5To17; }
    public void setFemalePregnant5To17(int v) { this.femalePregnant5To17 = v; }

    public int getFemalePregnant18To64() { return femalePregnant18To64; }
    public void setFemalePregnant18To64(int v) { this.femalePregnant18To64 = v; }

    public int getFemalePregnant65Plus() { return femalePregnant65Plus; }
    public void setFemalePregnant65Plus(int v) { this.femalePregnant65Plus = v; }

    public Map<String, Map<String, Integer>> getWhoHealthEventCounts() { return whoHealthEventCounts; }
    public void setWhoHealthEventCounts(Map<String, Map<String, Integer>> whoHealthEventCounts) {
        this.whoHealthEventCounts = whoHealthEventCounts != null ? whoHealthEventCounts : Collections.emptyMap();
    }

    public Map<String, Map<String, Integer>> getWhoProcedureCounts() { return whoProcedureCounts; }
    public void setWhoProcedureCounts(Map<String, Map<String, Integer>> whoProcedureCounts) {
        this.whoProcedureCounts = whoProcedureCounts != null ? whoProcedureCounts : Collections.emptyMap();
    }

    // Computed totals
    public int getTotalEncounters() {
        return getMaleTotal() + getFemaleNonPregnantTotal() + getFemalePregnantTotal();
    }

    public int getMaleTotal() {
        return maleUnder1 + male1To4 + male5To17 + male18To64 + male65Plus;
    }

    public int getFemaleNonPregnantTotal() {
        return femaleNonPregnantUnder1 + femaleNonPregnant1To4 + femaleNonPregnant5To17
                + femaleNonPregnant18To64 + femaleNonPregnant65Plus;
    }

    public int getFemalePregnantTotal() {
        return femalePregnantUnder1 + femalePregnant1To4 + femalePregnant5To17
                + femalePregnant18To64 + femalePregnant65Plus;
    }

    public int getUnder1Total() {
        return maleUnder1 + femaleNonPregnantUnder1 + femalePregnantUnder1;
    }

    public int get1To4Total() {
        return male1To4 + femaleNonPregnant1To4 + femalePregnant1To4;
    }

    public int get5To17Total() {
        return male5To17 + femaleNonPregnant5To17 + femalePregnant5To17;
    }

    public int get18To64Total() {
        return male18To64 + femaleNonPregnant18To64 + femalePregnant18To64;
    }

    public int get65PlusTotal() {
        return male65Plus + femaleNonPregnant65Plus + femalePregnant65Plus;
    }

    /** Helper: get lt5 count for a health event (0 if not present) */
    public int getHealthEventLt5(String eventName) {
        Map<String, Integer> counts = whoHealthEventCounts.get(eventName);
        return counts != null ? counts.getOrDefault("lt5", 0) : 0;
    }

    /** Helper: get gte5 count for a health event (0 if not present) */
    public int getHealthEventGte5(String eventName) {
        Map<String, Integer> counts = whoHealthEventCounts.get(eventName);
        return counts != null ? counts.getOrDefault("gte5", 0) : 0;
    }

    /** Helper: get total count for a health event */
    public int getHealthEventTotal(String eventName) {
        return getHealthEventLt5(eventName) + getHealthEventGte5(eventName);
    }

    /** Helper: get lt5 count for a procedure (0 if not present) */
    public int getProcedureLt5(String procedureName) {
        Map<String, Integer> counts = whoProcedureCounts.get(procedureName);
        return counts != null ? counts.getOrDefault("lt5", 0) : 0;
    }

    /** Helper: get gte5 count for a procedure (0 if not present) */
    public int getProcedureGte5(String procedureName) {
        Map<String, Integer> counts = whoProcedureCounts.get(procedureName);
        return counts != null ? counts.getOrDefault("gte5", 0) : 0;
    }

    /** Helper: get total count for a procedure */
    public int getProcedureTotal(String procedureName) {
        return getProcedureLt5(procedureName) + getProcedureGte5(procedureName);
    }
}
