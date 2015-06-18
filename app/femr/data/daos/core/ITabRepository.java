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

import femr.data.models.core.*;

import java.util.List;

/**
 * A repository to cover the following tables:
 * <ul>
 * <li>diagnoses</li>
 * <li>patient_encounter_tab_fields</li>
 * <li>tabs</li>
 * <li>tab_fields</li>
 * <li>tab_field_sizes</li>
 * <li>tab_field_types</li>
 * </ul>
 */
public interface ITabRepository {



    /**
     * Retrieve new patient encounter tab fields
     *
     * @param patientEncounterTabFields the patient encounter tab fields to create
     * @return the created patient encounter tab fields or an empty list if none exist
     */
    List<? extends IPatientEncounterTabField> createPatientEncounterTabFields(List<IPatientEncounterTabField> patientEncounterTabFields);

    /**
     * Retrieve all patient encounter tab fields for an encounter. No ordering.
     *
     * @param encounterId id of the patient's encounter
     * @return list of unordered tab fields or an empty list if none exist
     */
    List<? extends IPatientEncounterTabField> findPatientEncounterTabFieldsByEncounterId(int encounterId);

    /**
     * Retrieve all patient encounter tab fields for an encounter in reverse chronological order.
     *
     * @param encounterId id of the patient's encounter
     * @return list of ordered tab fields or an empty list if none exist
     */
    List<? extends IPatientEncounterTabField> findPatientEncounterTabFieldsByEncounterIdOrderByDateTakenDesc(int encounterId);

    /**
     * Retrieve specific patient encounter tab fields for an encounter, then orders the result by the date they were taken
     * in ascending order.
     *
     * @param encounterId  id of the patient's encounter
     * @param tabFieldName name of the tab field to search for, not null
     * @return list of tab fields in ascending order by the date taken or an empty list if none exist. Will return null if tabFieldName is null.
     */
    List<? extends IPatientEncounterTabField> findPatientEncounterTabFieldByEncounterIdAndTabNameOrderByDateTakenAsc(int encounterId, String tabFieldName);

    /**
     * Retrieve specific patient encounter tab fields for an encounter, then orders the result by the date they were taken
     * in descending order.
     *
     * @param encounterId  id of the patient's encounter
     * @param tabFieldName name of the tab field to search for, not null
     * @return list of tab fields in descending order by the date taken or an empty list if none exist. Will return null if tabFieldName is null.
     */
    List<? extends IPatientEncounterTabField> findPatientEncounterTabFieldByEncounterIdAndTabNameOrderByDateTakenDesc(int encounterId, String tabFieldName);

    /**
     * Retrieve a list of all custom tabs.
     *
     * @param isDeleted specify the isDeleted flag.
     * @return list of unordered custom tabs or an empty list if none exist
     */
    List<? extends ITab> findCustomTabs(boolean isDeleted);

    /**
     * Retrieve a tab.
     *
     * @param tabName name of the tab, not null
     * @return a tab or null if it doesn't exist or null if tabName parameter is null
     */
    ITab findTab(String tabName);

    /**
     * Retrieve a list of all tabs.
     *
     * @param isDeleted specify the isDeleted flag.
     * @return list of unordered tabs or an empty list if none exist
     */
    List<? extends ITab> findTabs(boolean isDeleted);

    /**
     * Updates or creates a tab
     *
     * @param tab the tab being updated or created
     * @return the updated or created tab
     */
    ITab saveTab(ITab tab);

    /**
     * Retrieve one tab field by name
     *
     * @param fieldName name of the tab field, not null
     * @return the tab field if it exists, or null if it doesn't
     */
    ITabField findTabField(String fieldName);

    /**
     * Retrieve a list of all tab fields based on isDeleted flag.
     *
     * @param isDeleted specify the isDeleted flag, not null
     * @return list of unordered tab fields or an empty list if none exist
     */
    List<? extends ITabField> findTabFields(boolean isDeleted);

    /**
     * Retrieve a list of all tab fields, deleted or not deleted.
     *
     * @return list of unordered tab fields or an empty list if none exist
     */
    List<? extends ITabField> findAllTabFields();

    /**
     * Retrieve a tab field based on tab name and tab field name
     *
     * @param tabName      name of the tab, not null
     * @param tabFieldName name of the tab field, not null
     * @return the tab field or null if not found or null if parameters are null
     */
    ITabField findTabFieldByTabNameAndTabFieldName(String tabName, String tabFieldName);

    /**
     * Retrieve all tab fields that are on a specific tab.
     *
     * @param tabName name of the tab
     * @param isDeleted specify the isDeleted flag
     * @return list of tab fields or an empty list or null if tabName is null
     */
    List<? extends ITabField> findTabFieldsByTabNameOrderBySortOrderAsc(String tabName, boolean isDeleted);

    /**
     * Updates or creates a tab field
     *
     * @param tabField the tab field being updated or created
     * @return the updated or created tab field
     */
    ITabField saveTabField(ITabField tabField);

    /**
     * Retrieve all tab field sizes
     *
     * @return a list of all tab field sizes, or an empty list if not found
     */
    List<? extends ITabFieldSize> findAllTabFieldSizes();

    /**
     * Retrieve a tab field size based on its name
     *
     * @param size name of the tab field size
     * @return the tab field size or null if not found
     */
    ITabFieldSize findTabFieldSize(String size);

    /**
     * Retrieve a tab field type based on its name
     *
     * @param type name of the tab field type
     * @return the tab field type or null if not found
     */
    ITabFieldType findTabFieldType(String type);

    /**
     * Retrieve all tab field types
     *
     * @return a list of all tab field types, or an empty list if not found
     */
    List<? extends ITabFieldType> findAllTabFieldTypes();
}
