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
import femr.data.models.core.IKitStatus;
import femr.data.models.core.ILanguageCode;
import femr.data.models.core.INetworkStatus;
import femr.data.models.core.IDatabaseStatus;

import java.util.List;

public interface IUpdatesService {

    /**
     * Retrieve network statuses.
     *
     * @return a list of current network statues.
     */
    ServiceResponse<List<? extends INetworkStatus>> retrieveNetworkStatuses();

    /**
     * Update network statuses.
     *
     * @return a list of network statues after the update (update by running a script).
     */
    ServiceResponse<List<? extends INetworkStatus>> updateNetworkStatuses();

    /**
     * Retrieve network statuses.
     *
     * @return a list of current network statues.
     */
    ServiceResponse<List<? extends IKitStatus>> retrieveKitStatuses();

    /**
     * Update kit statuses.
     *
     * @return a list of kit statuses after the update (update by running a script?).
     */
    ServiceResponse<List<? extends IKitStatus>> updateKitStatuses();

    /**
     * Retrieve database last update.
     *
     * @return a list of current database status.
     */
    ServiceResponse<List<? extends IDatabaseStatus>> retrieveDatabaseStatuses();

    /**
     * Update database last update and do the backup via script.
     *
     * @return a list of kit database status.
     */
    ServiceResponse<List<? extends IDatabaseStatus>> updateDatabaseStatuses();

    ServiceResponse<List<? extends ILanguageCode>> retrieveLanguages();
    ServiceResponse<List<? extends ILanguageCode>> initializeLanguages();

    ServiceResponse<List<? extends ILanguageCode>> updateLanguage(String code);
}
