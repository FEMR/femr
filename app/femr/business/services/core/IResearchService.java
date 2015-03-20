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
package femr.business.services.core;

import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.ui.models.research.json.ResearchGraphDataModel;

/**
 * Interface for the Research Service
 */
public interface IResearchService {


    /**
     * Take filters and build matching data for graph.
     *
     * @param filterItem object representing chosen filters
     * @return a service response that contains graph data bundled for display
     * and/or errors if they exist.
     */
    public ServiceResponse<ResearchResultSetItem> retrieveGraphData(ResearchFilterItem filterItem);
}
