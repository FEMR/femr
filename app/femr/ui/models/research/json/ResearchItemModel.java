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
package femr.ui.models.research.json;

import java.util.HashMap;
import java.util.Map;

public class ResearchItemModel {


    private String primaryName;
    private float primaryValue;
    private Map<String, Float> secondaryData;

    public ResearchItemModel(){

        primaryName = "";
        primaryValue = 0.0f;
        secondaryData = new HashMap<>();
    }

    public ResearchItemModel(String name){

        primaryName = name;
        primaryValue = 0.0f;
        secondaryData = new HashMap<>();
    }

    public String getPrimaryName() {
        return primaryName;
    }


    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public float getPrimaryValue() {
        return primaryValue;
    }

    public void setPrimaryValue(float primaryValue) {
        this.primaryValue = primaryValue;
    }


    public Map<String, Float> getSecondaryData() {

        if( secondaryData != null ) {
            return secondaryData;
        }
        else{
            return new HashMap<String, Float>();
        }
    }

    public void setSecondaryData(Map<String, Float> secondaryData) {
        this.secondaryData = secondaryData;
    }

}

