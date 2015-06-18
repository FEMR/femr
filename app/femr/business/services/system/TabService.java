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
package femr.business.services.system;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.services.core.ITabService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.TabFieldItem;
import femr.common.models.TabItem;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.daos.core.IPatientEncounterRepository;
import femr.data.daos.core.ITabRepository;
import femr.data.models.core.*;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.*;

public class TabService implements ITabService {

    private final IPatientEncounterRepository patientEncounterRepository;
    private final ITabRepository tabRepository;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public TabService(IPatientEncounterRepository patientEncounterRepository,
                      ITabRepository tabRepository,
                      IDataModelMapper DataModelMapper,
                      @Named("identified") IItemModelMapper itemModelMapper) {

        this.patientEncounterRepository = patientEncounterRepository;
        this.tabRepository = tabRepository;
        this.dataModelMapper = DataModelMapper;
        this.itemModelMapper = itemModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabItem> toggleTab(String name) {

        ServiceResponse<TabItem> response = new ServiceResponse<>();

        if (StringUtils.isNullOrWhiteSpace(name)) {

            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }

        ITab tab;
        try {

            tab = tabRepository.findTab(name);
        } catch (Exception ex) {

            response.addError("exception", ex.getMessage());
            return response;
        }

        tab.setIsDeleted(!tab.getIsDeleted());
        try {

            tab = tabRepository.saveTab(tab);
        } catch (Exception ex) {

            response.addError("exception", ex.getMessage());
            return response;
        }

        TabItem tabItem = itemModelMapper.createTabItem(tab.getName(), tab.getIsCustom(), tab.getLeftColumnSize(), tab.getRightColumnSize());
        response.setResponseObject(tabItem);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabItem> createTab(TabItem newTab, int userId) {
        ServiceResponse<TabItem> response = new ServiceResponse<>();
        if (newTab == null || StringUtils.isNullOrWhiteSpace(newTab.getName())) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }
        ITab tab = dataModelMapper.createTab(dateUtils.getCurrentDateTime(), newTab.getLeftColumnSize(), newTab.getRightColumnSize(), newTab.getName(), false, userId);

        //verify the tab doesn't already exist
        try {

            ITab doesTabExist = tabRepository.findTab(tab.getName());
            if (doesTabExist != null) {
                response.addError("", "tab already exists");
                return response;
            }
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        //createPatientEncounter the tab
        try {
            tabRepository.saveTab(tab);
            response.setResponseObject(newTab);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabItem>> retrieveCustomTabs(boolean isDeleted) {

        ServiceResponse<List<TabItem>> response = new ServiceResponse<>();

        List<? extends ITab> tabs = new ArrayList<>();
        try {

            tabs = tabRepository.findCustomTabs(isDeleted);
        } catch (Exception ex) {

            response.addError("exception", ex.getMessage());
        }

        List<TabItem> tabItems = new ArrayList<>();
        for (ITab t : tabs) {
            tabItems.add(itemModelMapper.createTabItem(t.getName(), t.getIsCustom(), t.getLeftColumnSize(), t.getRightColumnSize()));
        }
        response.setResponseObject(tabItems);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabFieldItem>> retrieveTabFieldsByTabName(String tabName, boolean isDeleted) {
        ServiceResponse<List<TabFieldItem>> response = new ServiceResponse<>();
        if (StringUtils.isNullOrWhiteSpace(tabName)) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }

        try {
            List<? extends ITabField> tabFields = tabRepository.findTabFieldsByTabNameOrderBySortOrderAsc(tabName, isDeleted);
            List<TabFieldItem> customFieldItems = new ArrayList<>();
            String size = null;
            for (ITabField tf : tabFields) {

                if (tf.getTabFieldSize() != null)
                    size = tf.getTabFieldSize().getName();

                customFieldItems.add(itemModelMapper.createTabFieldItem(tf.getName(),
                        tf.getTabFieldType().getName(),
                        size,
                        tf.getOrder(),
                        tf.getPlaceholder(),
                        null,
                        null,
                        true));
            }
            response.setResponseObject(customFieldItems);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabFieldItem> toggleTabField(String fieldName, String tabName) {

        //TODO: properly mark tabs as custom
        ServiceResponse<TabFieldItem> response = new ServiceResponse<>();
        if (StringUtils.isNullOrWhiteSpace(fieldName) || StringUtils.isNullOrWhiteSpace(tabName)) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }

        try {

            ITabField tabField = tabRepository.findTabFieldByTabNameAndTabFieldName(tabName, fieldName);
            tabField.setIsDeleted(!tabField.getIsDeleted());
            //delete the sort order when the tab gets deactivated
            if (tabField.getIsDeleted()) tabField.setOrder(null);
            tabField = tabRepository.saveTabField(tabField);
            String size = null;
            if (tabField.getTabFieldSize() != null)
                size = tabField.getTabFieldSize().getName();

            TabFieldItem tabFieldItem = itemModelMapper.createTabFieldItem(tabField.getName(),
                    tabField.getTabFieldType().getName(),
                    size,
                    tabField.getOrder(),
                    tabField.getPlaceholder(),
                    null,
                    null,
                    true);
            response.setResponseObject(tabFieldItem);

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabItem> updateTab(TabItem tabItem, int userId) {
        ServiceResponse<TabItem> response = new ServiceResponse<>();
        if (tabItem == null || StringUtils.isNullOrWhiteSpace(tabItem.getName()) || userId < 1) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }

        try {
            //needs a null check
            ITab newTab = tabRepository.findTab(tabItem.getName());
            newTab.setDateCreated(DateTime.now());
            newTab.setLeftColumnSize(tabItem.getLeftColumnSize());
            newTab.setRightColumnSize(tabItem.getRightColumnSize());
            newTab.setUserId(userId);
            newTab = tabRepository.saveTab(newTab);
            TabItem newTabItem = itemModelMapper.createTabItem(newTab.getName(), newTab.getIsCustom(), newTab.getLeftColumnSize(), newTab.getRightColumnSize());
            response.setResponseObject(newTabItem);
        } catch (Exception ex) {

            response.addError("", "error");
        }
        return response;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabFieldItem> updateTabField(TabFieldItem tabFieldItem) {

        ServiceResponse<TabFieldItem> response = new ServiceResponse<>();
        if (tabFieldItem == null || tabFieldItem.getName() == null) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }

        try {

            ITabField tabField = tabRepository.findTabField(tabFieldItem.getName());

            if (tabFieldItem.getPlaceholder() != null) tabField.setPlaceholder(tabFieldItem.getPlaceholder());
            if (tabFieldItem.getOrder() != null) tabField.setOrder(tabFieldItem.getOrder());

            //check size updatePatientEncounter
            if (StringUtils.isNotNullOrWhiteSpace(tabFieldItem.getSize())) {

                ITabFieldSize tabFieldSize = tabRepository.findTabFieldSize(tabFieldItem.getSize());
                tabField.setTabFieldSize(tabFieldSize);
            }

            //check type updatePatientEncounter
            if (StringUtils.isNotNullOrWhiteSpace(tabFieldItem.getType())) {

                ITabFieldType tabFieldType = tabRepository.findTabFieldType(tabFieldItem.getType());
                tabField.setTabFieldType(tabFieldType);
            }
            tabField = tabRepository.saveTabField(tabField);

            String size = null;

            if (tabField.getTabFieldSize() != null)
                size = tabField.getTabFieldSize().getName();

            TabFieldItem newTabFieldItem = itemModelMapper.createTabFieldItem(tabField.getName(),
                    tabField.getTabFieldType().getName(),
                    size,
                    tabField.getOrder(),
                    tabField.getPlaceholder(),
                    null,
                    null,
                    true);

            response.setResponseObject(newTabFieldItem);

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabFieldItem> createTabField(TabFieldItem tabFieldItem, int userId, String tabName) {
        ServiceResponse<TabFieldItem> response = new ServiceResponse<>();
        if (tabFieldItem == null ||
                StringUtils.isNullOrWhiteSpace(tabFieldItem.getName()) ||
                StringUtils.isNullOrWhiteSpace(tabFieldItem.getSize()) ||
                StringUtils.isNullOrWhiteSpace(tabFieldItem.getType()) ||
                StringUtils.isNullOrWhiteSpace(tabName)) {
            response.addError("", "you're TabFieldItem sucks, fix it up and try again");
            return response;
        }

        //verify the tab exists?
        ITab tab;
        try {

            tab = tabRepository.findTab(tabName);
        } catch (Exception ex) {

            response.addError("exception", ex.getMessage());
            return response;
        }


        //get field type
        ITabFieldType tabFieldType;
        try {
            tabFieldType = tabRepository.findTabFieldType(tabFieldItem.getType());
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }

        //get field size
        ITabFieldSize tabFieldSize;
        try {
            tabFieldSize = tabRepository.findTabFieldSize(tabFieldItem.getSize());
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }

        ITabField customField = dataModelMapper.createTabField(tabFieldItem.getName(), tabFieldItem.getOrder(), tabFieldItem.getPlaceholder(), false, tabFieldSize.getId(), tabFieldType.getId(), tab.getId());

        try {
            customField = tabRepository.saveTabField(customField);
            String size = null;

            if (customField.getTabFieldSize() != null)
                size = customField.getTabFieldSize().getName();

            TabFieldItem newTabFieldItem = itemModelMapper.createTabFieldItem(customField.getName(),
                    customField.getTabFieldType().getName(),
                    size,
                    customField.getOrder(),
                    customField.getPlaceholder(),
                    null,
                    null,
                    true);

            response.setResponseObject(newTabFieldItem);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> retrieveTypes() {

        ServiceResponse<List<String>> response = new ServiceResponse<>();

        List<String> fields = new ArrayList<>();

        try {

            List<? extends ITabFieldType> tabFieldTypes = tabRepository.findAllTabFieldTypes();

            for (ITabFieldType cft : tabFieldTypes) {
                fields.add(cft.getName());
            }

            response.setResponseObject(fields);
        } catch (Exception ex) {

            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> retrieveSizes() {

        ServiceResponse<List<String>> response = new ServiceResponse<>();

        List<String> fields = new ArrayList<>();

        try {

            List<? extends ITabFieldSize> tabFieldSizes = tabRepository.findAllTabFieldSizes();

            for (ITabFieldSize cfs : tabFieldSizes) {
                fields.add(cfs.getName());
            }

            response.setResponseObject(fields);
        } catch (Exception ex) {

            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> doesTabFieldExist(String fieldName) {

        ServiceResponse<Boolean> response = new ServiceResponse<>();

        try {

            ITabField customField = tabRepository.findTabField(fieldName);
            if (customField == null) {
                response.setResponseObject(false);
            } else {
                response.setResponseObject(true);
            }

        } catch (Exception ex) {

            response.setResponseObject(false);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> doesTabExist(String tabName) {

        ServiceResponse<Boolean> response = new ServiceResponse<>();

        try {

            ITab customTab = tabRepository.findTab(tabName);
            if (customTab == null) {
                response.setResponseObject(false);
            } else {
                response.setResponseObject(true);
            }
        } catch (Exception ex) {

            response.setResponseObject(false);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabFieldMultiMap> retrieveTabFieldMultiMap(int encounterId) {

        ServiceResponse<TabFieldMultiMap> response = new ServiceResponse<>();
        TabFieldMultiMap tabFieldMultiMap = mapTabFields(encounterId);
        if (tabFieldMultiMap != null)
            response.setResponseObject(tabFieldMultiMap);
        else
            response.addError("", "there was an issue building the multi map");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabFieldMultiMap> findTabFieldMultiMap(int encounterId, String tabFieldName, String chiefComplaintName) {
        ServiceResponse<TabFieldMultiMap> response = new ServiceResponse<>();
        TabFieldMultiMap tabFieldMultiMap = new TabFieldMultiMap();

        List<? extends IPatientEncounterTabField> patientEncounterTabFields = tabRepository.findPatientEncounterTabFieldByEncounterIdAndTabNameOrderByDateTakenDesc(encounterId, tabFieldName);

        for (IPatientEncounterTabField tf : patientEncounterTabFields) {
            if (tf.getChiefComplaint() != null && tf.getChiefComplaint().getValue().equals(chiefComplaintName)) {
                //Only add tabFields for the request chief complaint
                tabFieldMultiMap.put(tabFieldName, tf.getDateTaken().toString().trim(), null, itemModelMapper.createTabFieldItem(tf.getTabField().getName(), tf.getTabField().getTabFieldType().getName(), "", 0, "", tf.getTabFieldValue(), null, false, tf.getUserName()));
            } else if (chiefComplaintName == null || chiefComplaintName.isEmpty()) {
                //No chief complaint, so put all matching fields
                tabFieldMultiMap.put(tabFieldName, tf.getDateTaken().toString().trim(), null, itemModelMapper.createTabFieldItem(tf.getTabField().getName(), tf.getTabField().getTabFieldType().getName(), "", 0, "", tf.getTabFieldValue(), null, false, tf.getUserName()));
            }
        }

        if (tabFieldMultiMap != null)
            response.setResponseObject(tabFieldMultiMap);
        else
            response.addError("", "there was an issue building the multi map");
        return response;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabItem>> retrieveAvailableTabs(boolean isDeleted) {

        ServiceResponse<List<TabItem>> response = new ServiceResponse<>();
        List<TabItem> tabItems = new ArrayList<>();

        try {

            List<? extends ITab> tabs = tabRepository.findTabs(false);
            for (ITab tab : tabs) {
                tabItems.add(itemModelMapper.createTabItem(tab.getName(), tab.getIsCustom(), tab.getLeftColumnSize(), tab.getRightColumnSize()));
            }
            response.setResponseObject(tabItems);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * Maps all tab fields to their tab. Values and chief complaints are also recorded.
     *
     * @param encounterId id of the patient encounter
     * @return {@link TabFieldMultiMap}
     */
    private TabFieldMultiMap mapTabFields(int encounterId) {

        TabFieldMultiMap tabFieldMultiMap = new TabFieldMultiMap();
        String tabFieldName;

        try {

            List<? extends ITabField> tabFields = tabRepository.findTabFields(false);
            List<? extends IPatientEncounterTabField> patientEncounterTabFields = tabRepository.findPatientEncounterTabFieldsByEncounterIdOrderByDateTakenDesc(encounterId);


            //need all chief complaints regardless
            List<? extends IChiefComplaint> chiefComplaints = patientEncounterRepository.findAllChiefComplaintsByPatientEncounterIdOrderBySortOrderAsc(encounterId);


            //Collections.reverse(patientEncounterTabFields);

            //all fields that have values
            for (IPatientEncounterTabField petf : patientEncounterTabFields) {
                String tabFieldSize = null;
                String chiefComplaint = null;
                boolean isCustom = petf.getTabField().getTab().getIsCustom();
                if (petf.getTabField().getTabFieldSize() != null)
                    tabFieldSize = petf.getTabField().getTabFieldSize().getName();
                if (petf.getChiefComplaint() != null)
                    chiefComplaint = petf.getChiefComplaint().getValue();

                tabFieldName = petf.getTabField().getName();

                if (petf.getTabField().getTab().getName().equals("HPI")) {

                    if (chiefComplaints != null && chiefComplaints.size() > 0) {

                        tabFieldMultiMap.put(tabFieldName, petf.getDateTaken().toString().trim(), chiefComplaint, itemModelMapper.createTabFieldItem(petf.getTabField().getName(), petf.getTabField().getTabFieldType().getName(), tabFieldSize, petf.getTabField().getOrder(), petf.getTabField().getPlaceholder(), petf.getTabFieldValue(), chiefComplaint, isCustom, petf.getUserName()));
                    } else {

                        tabFieldMultiMap.put(tabFieldName, petf.getDateTaken().toString().trim(), null, itemModelMapper.createTabFieldItem(petf.getTabField().getName(), petf.getTabField().getTabFieldType().getName(), tabFieldSize, petf.getTabField().getOrder(), petf.getTabField().getPlaceholder(), petf.getTabFieldValue(), null, isCustom, petf.getUserName()));
                    }
                } else {

                    tabFieldMultiMap.put(tabFieldName, petf.getDateTaken().toString().trim(), null, itemModelMapper.createTabFieldItem(petf.getTabField().getName(), petf.getTabField().getTabFieldType().getName(), tabFieldSize, petf.getTabField().getOrder(), petf.getTabField().getPlaceholder(), petf.getTabFieldValue(), chiefComplaint, isCustom, petf.getUserName()));
                }


            }

            //all empty fields

            for (ITabField tf : tabFields) {
                String tabFieldSize = null;
                boolean isCustom = tf.getTab().getIsCustom();
                if (tf.getTabFieldSize() != null)
                    tabFieldSize = tf.getTabFieldSize().getName();

                //hpi gets special treatment for each chief complaint
                if (tf.getTab().getName().equals("HPI")) {

                    if (chiefComplaints != null && chiefComplaints.size() > 0) {
                        for (IChiefComplaint cc : chiefComplaints) {
                            if (!tabFieldMultiMap.containsTabField(tf.getName(), cc.getValue())) {
                                tabFieldMultiMap.put(tf.getName(), null, cc.getValue(), itemModelMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), tabFieldSize, tf.getOrder(), tf.getPlaceholder(), null, null, isCustom));
                            }
                        }
                    } else {
                        tabFieldMultiMap.put(tf.getName(), null, null, itemModelMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), tabFieldSize, tf.getOrder(), tf.getPlaceholder(), null, null, isCustom));
                    }

                } else {
                    if (!tabFieldMultiMap.containsTabField(tf.getName())) {
                        tabFieldMultiMap.put(tf.getName(), null, null, itemModelMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), tabFieldSize, tf.getOrder(), tf.getPlaceholder(), null, null, isCustom));
                    }
                }
            }

            //sort the chief complaints
            for (IChiefComplaint cc : chiefComplaints) {
                tabFieldMultiMap.setChiefComplaintOrder(cc.getValue(), cc.getSortOrder());
            }
        } catch (Exception ex) {

            return null;
        }

        return tabFieldMultiMap;
    }


}
