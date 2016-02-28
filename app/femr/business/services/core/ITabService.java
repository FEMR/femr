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
import femr.common.models.TabFieldItem;
import femr.common.models.TabItem;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;

import java.util.List;
import java.util.Map;

public interface ITabService {

    /**
     * Edit a tab field.
     *
     * @param customFieldItem new tab field item being created TODO: separate into parameters
     * @return ServiceResponse that contains the new TabFieldItem
     * and/or errors if they exist.
     */
    ServiceResponse<TabFieldItem> updateTabField(TabFieldItem customFieldItem);

    /**
     * Edit a tab - updates the date created, left column size, right column size,
     * userId.
     *
     * @param customTabItem new tab item being created TODO: separate into parameters
     * @param userId user editing the tab
     * @return ServiceResponse that contains the new TabItem
     * and/or errors if they exist.
     */
    ServiceResponse<TabItem> updateTab(TabItem customTabItem, int userId);

    /**
     * Deletes or un-Deletes a tab.
     *
     * @param name name of the tab (unique identifier), not null
     * @return ServiceResponse that contains the toggled TabItem
     * and/or errors if they exist.
     */
    ServiceResponse<TabItem> toggleTab(String name);

    /**
     * Deletes or un-Deletes a tab field.
     *
     * @param fieldName name of the field to toggle, not null
     * @param tabName name of the tab the fields is in, not null
     * @return ServiceResponse that contains the toggled TabItemItem
     * and/or errors if they exist.
     */
    ServiceResponse<TabFieldItem> toggleTabField(String fieldName, String tabName);

    /**
     * Create a new tab.
     *
     * @param newTab the new TabItem being created, not null
     * @param userId id of the user that is creating the tab, not null
     * @return ServiceResponse that contains the new TabItem
     * and/or errors if they exist.
     */
    ServiceResponse<TabItem> createTab(TabItem newTab, int userId);

    /**
     * Create a new tab field.
     *
     * @param customFieldItem the new TabFieldItem being created TODO: separate into parameters
     * @param userId id of the user that is creating the tab field, not null
     * @param tabName name of the tab the fields is in, not null
     * @return ServiceResponse that contains the new TabFieldItem
     * and/or errors if they exist.
     */
    ServiceResponse<TabFieldItem> createTabField(TabFieldItem customFieldItem, int userId, String tabName);

    /**
     * Get all custom Tabs based on whether or not they are deleted.
     *
     * @param isDeleted whether or not the tabs are deleted, not null
     * @return ServiceResponse that contains a list of custom TabItems
     * and/or errors if they exist.
     */
    ServiceResponse<List<TabItem>> retrieveCustomTabs(boolean isDeleted);

    /**
     * Get all fields for one tab. This only works for custom tabs.
     *
     * @param tabName name of the tab to get fields for, not null
     * @param isDeleted whether or not the fields are deleted, not null
     * @return ServiceResponse that contains a list of TabFieldItems
     * and/or errors if they exist.
     */
    ServiceResponse<List<TabFieldItem>> retrieveTabFieldsByTabName(String tabName, boolean isDeleted);


    /**
     * Get all possible types of tab fields.
     *
     * @return ServiceResponse that contains a list of Strings representing types
     * and/or errors if they exist.
     */
    ServiceResponse<List<String>> retrieveTypes();

    /**
     * Get all possible sizes of tab fields.
     *
     * @return ServiceResponse that contains a list of Strings representing sizes
     * and/or errors if they exist.
     */
    ServiceResponse<List<String>> retrieveSizes();

    /**
     * Checks to see if a tab field exists.
     *
     * @param fieldName name of the field (unique identifier), TODO: check for null
     * @return ServiceResponse that contains true if the field exists
     * and/or errors if they exist.
     */
    ServiceResponse<Boolean> doesTabFieldExist(String fieldName);

    /**
     * Checks to see if a tab exists.
     *
     * @param tabName name of the tab (unique identifier)
     * @return ServiceResponse that contains true if the tab exists
     * and/or errors if they exist.
     */
    ServiceResponse<Boolean> doesTabExist(String tabName);

    /**
     * Create a map of tabs and their fields where the key is the name, date, and chief complaint.
     * Chief complaint is null if it doesn't exist.
     *
     * @param encounterId id of the encounter, not null
     * @return a ServiceResponse that contains a TabFieldMultiMap which contains all
     * tab fields and their values. <strong>It will also contain empty fields.</strong>
     * and/or errors if they exist.
     */
    ServiceResponse<TabFieldMultiMap> retrieveTabFieldMultiMap(int encounterId);

    /**
     * Get all available tabs for use.
     *
     * @return a ServiceResponse that contains a list of TabItems
     * and/or errors if they exist.
     * Create a map for a specific tab where the key is the name, date, and chief complaint.
     * Chief complaint is null if it doesn't exist.
     *
     * @param encounterId id of the encounter
     * @param tabFieldName Name of tab
     * @param chiefComplaintName Chief complaint name
     * @return a "TabFieldMultiMap" that contains tab field and values. <strong>It will also contain empty fields.</strong>
     */
    ServiceResponse<TabFieldMultiMap> findTabFieldMultiMap(int encounterId, String tabFieldName, String chiefComplaintName);

    /**
     * Get all available tabs for use
     * @return list of available tabs
     */
    ServiceResponse<List<TabItem>> retrieveAvailableTabs(boolean isDeleted);

    /**
     * Gets a mapping of all the tab fields to their tab as strings.
     *
     * @param isTabDeleted if true, deleted tabs will be retrieved, not null
     * @param isTabFieldDeleted if true, deleted tab fields will be retrieved, not null
     * @return a map of tab names as the key and tab fields that map to that tab name as the value. All
     * tab names are lowercased.
     */
    ServiceResponse<Map<String, List<String>>> retrieveTabFieldToTabMapping(boolean isTabDeleted, boolean isTabFieldDeleted);
}
