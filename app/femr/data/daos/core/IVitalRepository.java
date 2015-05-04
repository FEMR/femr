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

import femr.data.models.core.IVital;

import java.util.List;

public interface IVitalRepository {

    /**
     * Finds all available vitals
     *
     * @return a list of available vitals
     */
    List<? extends IVital> findAll();

    /**
     * Finds a vital by name
     *
     * @param name the name of the vital
     * @return returns the vital or null if none exist
     */
    IVital findByName(String name);

    /**
     * Finds the heightFeet vital
     *
     * @return the heightFeet vital or null if it doesn't exist
     */
    IVital findHeightFeet();

    /**
     * Finds the heightInches vital
     *
     * @return the heightInches vital or null if it doesn't exist
     */
    IVital findHeightInches();
}
