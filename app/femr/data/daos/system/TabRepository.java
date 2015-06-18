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

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import femr.data.daos.core.ITabRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.stringhelpers.StringUtils;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

public class TabRepository implements ITabRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounterTabField> createPatientEncounterTabFields(List<IPatientEncounterTabField> patientEncounterTabFields) {

        try {

            Ebean.save(patientEncounterTabFields);
        } catch (Exception ex) {

            Logger.error("TabRepository-createPatientEncounterTabFields", ex.getMessage());
        }

        return patientEncounterTabFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounterTabField> findPatientEncounterTabFieldsByEncounterId(int encounterId) {

        ExpressionList<PatientEncounterTabField> expressionList = getPatientEncounterTabFieldQuery()
                .where()
                .eq("patient_encounter_id", encounterId);

        List<? extends IPatientEncounterTabField> patientEncounterTabFields = new ArrayList<>();

        try {

            patientEncounterTabFields = expressionList.findList();
        } catch (Exception ex) {

            Logger.error("TabRepository-findPatientEncounterTabFieldsByEncounterId", ex.getMessage());
        }

        return patientEncounterTabFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounterTabField> findPatientEncounterTabFieldsByEncounterIdOrderByDateTakenDesc(int encounterId) {

        Query<PatientEncounterTabField> query = getPatientEncounterTabFieldQuery()
                .where()
                .eq("patient_encounter_id", encounterId)
                .order()
                .desc("date_taken");

        List<? extends IPatientEncounterTabField> patientEncounterTabFields = new ArrayList<>();

        try {

            patientEncounterTabFields = query.findList();
        } catch (Exception ex) {

            Logger.error("TabRepository-findPatientEncounterTabFieldsByEncounterIdOrderByDateTakenDesc", ex.getMessage());
        }

        return patientEncounterTabFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounterTabField> findPatientEncounterTabFieldByEncounterIdAndTabNameOrderByDateTakenAsc(int encounterId, String tabFieldName) {

        if (StringUtils.isNullOrWhiteSpace(tabFieldName)) {

            return null;
        }

        Query<PatientEncounterTabField> query = getPatientEncounterTabFieldQuery()
                .fetch("tabField")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("tabField.name", tabFieldName)
                .order()
                .asc("date_taken");

        List<? extends IPatientEncounterTabField> patientEncounterTabFields = new ArrayList<>();

        try {

            patientEncounterTabFields = query.findList();
        } catch (Exception ex) {

            Logger.error("TabRepository-findPatientEncounterTabFieldByEncounterIdAndTabNameOrderByDateTakenAsc", ex.getMessage());
        }

        return patientEncounterTabFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounterTabField> findPatientEncounterTabFieldByEncounterIdAndTabNameOrderByDateTakenDesc(int encounterId, String tabFieldName) {

        if (StringUtils.isNullOrWhiteSpace(tabFieldName)) {

            return null;
        }

        Query<PatientEncounterTabField> query = getPatientEncounterTabFieldQuery()
                .fetch("tabField")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("tabField.name", tabFieldName)
                .order()
                .desc("date_taken");

        List<? extends IPatientEncounterTabField> patientEncounterTabFields = new ArrayList<>();

        try {

            patientEncounterTabFields = query.findList();
        } catch (Exception ex) {

            Logger.error("TabRepository-findPatientEncounterTabFieldByEncounterIdAndTabNameOrderByDateTakenDesc", ex.getMessage());
        }

        return patientEncounterTabFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends ITab> findCustomTabs(boolean isDeleted) {

        ExpressionList<Tab> expressionList = getTabQuery()
                .where()
                .eq("isDeleted", false)
                .eq("isCustom", true);

        List<? extends ITab> tabs = new ArrayList<>();
        try {

            tabs = expressionList.findList();
        } catch (Exception ex) {

            Logger.error("TabRepository-findCustomTabs", ex.getMessage());
        }

        return tabs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITab findTab(String tabName) {

        if (StringUtils.isNullOrWhiteSpace(tabName)) {

            return null;
        }

        ExpressionList<Tab> expressionList = getTabQuery()
                .where()
                .eq("name", tabName);

        ITab tab = null;
        try {

            tab = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("TabRepository-findTab", ex.getMessage());
        }

        return tab;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends ITab> findTabs(boolean isDeleted) {

        ExpressionList<Tab> expressionList = getTabQuery()
                .where()
                .eq("isDeleted", false);

        List<? extends ITab> tabs = new ArrayList<>();
        try {

            tabs = expressionList.findList();
        } catch (Exception ex) {

            Logger.error("TabRepository-findTabs", ex.getMessage());
        }

        return tabs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITab saveTab(ITab tab) {

        if (tab == null) {

            return null;
        }

        try {

            Ebean.save(tab);
        } catch (Exception ex) {

            Logger.error("TabRepository-saveTab", ex.getMessage());
        }

        return tab;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITabField findTabField(String fieldName) {

        if (StringUtils.isNullOrWhiteSpace(fieldName)) {
            return null;
        }

        ExpressionList<TabField> expressionList = getTabFieldQuery()
                .where()
                .eq("name", fieldName);

        ITabField tabField = null;
        try {

            tabField = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("TabRepository-findTabField", ex.getMessage());
        }

        return tabField;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends ITabField> findTabFields(boolean isDeleted) {

        ExpressionList<TabField> expressionList = getTabFieldQuery()
                .where()
                .eq("isDeleted", isDeleted);

        List<? extends ITabField> tabFields = new ArrayList<>();
        try {

            tabFields = expressionList.findList();
        } catch (Exception ex) {

            Logger.error("TabRepository-findTabFields", ex.getMessage());
        }

        return tabFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends ITabField> findAllTabFields() {

        List<? extends ITabField> tabFields = new ArrayList<>();
        try {

            tabFields = Ebean.find(TabField.class).findList();
        } catch (Exception ex) {

            Logger.error("TabRepository-findAllTabFields", ex.getMessage());
        }

        return tabFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITabField findTabFieldByTabNameAndTabFieldName(String tabName, String tabFieldName) {

        if (StringUtils.isNullOrWhiteSpace(tabName) || StringUtils.isNullOrWhiteSpace(tabFieldName)) {

            return null;
        }

        ExpressionList<TabField> expressionList = getTabFieldQuery()
                .fetch("tab")
                .where()
                .eq("name", tabFieldName)
                .eq("tab.name", tabName);

        ITabField tabField = null;
        try {

            tabField = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("TabRepository-findTabFieldByTabNameAndTabFieldName", ex.getMessage());
        }

        return tabField;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends ITabField> findTabFieldsByTabNameOrderBySortOrderAsc(String tabName, boolean isDeleted) {
        if (StringUtils.isNullOrWhiteSpace(tabName)) {

            return null;
        }

        Query<TabField> query = getTabFieldQuery()
                .fetch("tab")
                .where()
                .eq("isDeleted", isDeleted)
                .eq("tab.name", tabName)
                .order()
                .asc("sort_order");

        List<? extends ITabField> tabFields = new ArrayList<>();
        try {

            tabFields = query.findList();
        } catch (Exception ex) {

            Logger.error("TabRepository-findTabFieldsByTabNameOrderBySortOrderAsc", ex.getMessage());
        }

        return tabFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITabField saveTabField(ITabField tabField) {

        if (tabField == null) {

            return null;
        }

        try {

            Ebean.save(tabField);
        } catch (Exception ex) {

            Logger.error("TabRepository-saveTabField", ex.getMessage());
        }

        return tabField;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends ITabFieldSize> findAllTabFieldSizes() {

        List<? extends ITabFieldSize> tabFieldSizes = new ArrayList<>();
        try {

            tabFieldSizes = Ebean.find(TabFieldSize.class).findList();
        } catch (Exception ex) {

            Logger.error("TabRepository-findAllTabFieldSizes", ex.getMessage());
        }

        return tabFieldSizes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITabFieldSize findTabFieldSize(String size) {

        if (StringUtils.isNullOrWhiteSpace(size)) {

            return null;
        }

        ExpressionList<TabFieldSize> expressionList = getTabFieldSizeQuery()
                .where()
                .eq("name", size);

        ITabFieldSize tabFieldSize = null;
        try {

            tabFieldSize = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("TabRepository-findTabFieldSize", ex.getMessage());
        }

        return tabFieldSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITabFieldType findTabFieldType(String type) {

        if (StringUtils.isNullOrWhiteSpace(type)) {

            return null;
        }

        ExpressionList<TabFieldType> expressionList = getTabFieldTypeQuery()
                .where()
                .eq("name", type);

        ITabFieldType tabFieldType = null;
        try {

            tabFieldType = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("TabRepository-findTabFieldType", ex.getMessage());
        }

        return tabFieldType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends ITabFieldType> findAllTabFieldTypes() {

        List<? extends ITabFieldType> tabFieldTypes = new ArrayList<>();
        try {

            tabFieldTypes = Ebean.find(TabFieldType.class).findList();
        } catch (Exception ex) {

            Logger.error("TabRepository-findAllTabFieldTypes", ex.getMessage());
        }

        return tabFieldTypes;
    }

    /**
     * Provides the Ebean object to start building queries
     *
     * @return The patient encounter tab field Query object
     */
    private static Query<PatientEncounterTabField> getPatientEncounterTabFieldQuery() {

        return Ebean.find(PatientEncounterTabField.class);
    }

    /**
     * Provides the Ebean object to start building queries
     *
     * @return The tab Query object
     */
    private static Query<Tab> getTabQuery() {

        return Ebean.find(Tab.class);
    }

    /**
     * Provides the Ebean object to start building queries
     *
     * @return The tab field Query object
     */
    private static Query<TabField> getTabFieldQuery() {

        return Ebean.find(TabField.class);
    }

    /**
     * Provides the Ebean object to start building queries
     *
     * @return The tab field size Query object
     */
    private static Query<TabFieldSize> getTabFieldSizeQuery() {

        return Ebean.find(TabFieldSize.class);
    }

    /**
     * Provides the Ebean object to start building queries
     *
     * @return The tab field type Query object
     */
    private static Query<TabFieldType> getTabFieldTypeQuery() {

        return Ebean.find(TabFieldType.class);
    }


}
