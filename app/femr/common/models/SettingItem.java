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

public class SettingItem {
    private boolean isMultipleChiefComplaint;
    private boolean isPmhTab;
    private boolean isPhotoTab;
    private boolean isConsolidateHPI;
    // Alaa Serhan  - adding setting for isMetric
    private boolean isMetric;
    private boolean isDiabetesPrompt;


    public boolean isMultipleChiefComplaint() {
        return isMultipleChiefComplaint;
    }

    public void setMultipleChiefComplaint(boolean isMultipleChiefComplaint) {
        this.isMultipleChiefComplaint = isMultipleChiefComplaint;
    }

    public boolean isPmhTab() {
        return isPmhTab;
    }

    public void setPmhTab(boolean isPmhTab) {
        this.isPmhTab = isPmhTab;
    }

    public boolean isPhotoTab() {
        return isPhotoTab;
    }

    public void setPhotoTab(boolean isPhotoTab) {
        this.isPhotoTab = isPhotoTab;
    }

    public boolean isConsolidateHPI() {
        return isConsolidateHPI;
    }

    public void setConsolidateHPI(boolean isConsolidateHPI) {
        this.isConsolidateHPI = isConsolidateHPI;
    }

    public boolean isMetric(){ return isMetric;} //getter

    public void setMetric(boolean isMetric) { this.isMetric = isMetric; }

    public boolean isDiabetesPrompt() {
        return isDiabetesPrompt;
    }

    public void setIsDiabetesPrompt(boolean isDiabetesPrompt) {
        this.isDiabetesPrompt = isDiabetesPrompt;
    }
}
