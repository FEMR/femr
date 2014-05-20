package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.custom.ICustomField;
import femr.common.models.custom.ICustomFieldSize;
import femr.common.models.custom.ICustomFieldType;
import femr.common.models.custom.ICustomTab;
import femr.data.daos.IRepository;
import femr.data.models.custom.CustomField;
import femr.data.models.custom.CustomFieldSize;
import femr.data.models.custom.CustomFieldType;
import femr.data.models.custom.CustomTab;
import femr.ui.models.data.custom.CustomFieldItem;
import femr.ui.models.data.custom.CustomTabItem;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.List;

public class SuperuserService implements ISuperuserService {
    private IRepository<ICustomTab> customTabRepository;
    private IRepository<ICustomField> customFieldRepository;
    private IRepository<ICustomFieldType> customFieldTypeRepository;
    private IRepository<ICustomFieldSize> customFieldSizeRepository;

    @Inject
    public SuperuserService(IRepository<ICustomTab> customTabRepository,
                            IRepository<ICustomField> customFieldRepository,
                            IRepository<ICustomFieldType> customFieldTypeRepository,
                            IRepository<ICustomFieldSize> customFieldSizeRepository) {
        this.customTabRepository = customTabRepository;
        this.customFieldRepository = customFieldRepository;
        this.customFieldTypeRepository = customFieldTypeRepository;
        this.customFieldSizeRepository = customFieldSizeRepository;
    }

    @Override
    public ServiceResponse<ICustomTab> toggleCustomMedicalTab(String name) {

        ServiceResponse<ICustomTab> response = new ServiceResponse<>();
        ExpressionList<CustomTab> query = getCustomMedicalTabQuery()
                .where()
                .eq("name", name);
        ICustomTab customTab = customTabRepository.findOne(query);
        if (customTab == null) {
            response.addError("", "error");
            return response;
        }
        customTab.setIsDeleted(!customTab.getIsDeleted());
        customTab = customTabRepository.update(customTab);
        if (customTab == null) {
            response.addError("", "error");
            return response;
        }
        response.setResponseObject(customTab);
        return response;
    }


    @Override
    public ServiceResponse<ICustomTab> createCustomMedicalTab(ICustomTab newTab) {
        ServiceResponse<ICustomTab> response = new ServiceResponse<>();
        ExpressionList<CustomTab> query = getCustomMedicalTabQuery()
                .where()
                .eq("name", newTab.getName());
        ICustomTab dupilicateTab = customTabRepository.findOne(query);
        if (dupilicateTab == null) {//create new
            newTab = customTabRepository.create(newTab);
            if (newTab == null) {
                response.addError("", "error");
            } else {
                response.setResponseObject(newTab);
            }
        } else {//activate old
            dupilicateTab.setIsDeleted(false);
            dupilicateTab = customTabRepository.update(dupilicateTab);
            response.setResponseObject(dupilicateTab);
        }

        return response;
    }

    @Override
    public ServiceResponse<List<CustomTabItem>> getCustomMedicalTabs(Boolean isDeleted) {
        ServiceResponse<List<CustomTabItem>> response = new ServiceResponse<>();
        //List<? extends ICustomTab> customTabs = customTabRepository.findAll(CustomTab.class);
        ExpressionList<CustomTab> query = getCustomMedicalTabQuery()
                .where()
                .eq("isDeleted", isDeleted);
        List<? extends ICustomTab> customTabs = customTabRepository.find(query);
        List<CustomTabItem> customTabItems = new ArrayList<>();
        for (ICustomTab ct: customTabs){
            customTabItems.add(getCustomTabItem(ct));
        }

        if (customTabs == null) {
            response.addError("", "error");
        } else {
            response.setResponseObject(customTabItems);
        }
        return response;
    }

    @Override
    public ServiceResponse<Boolean> doesCustomTabExist(String tabName){
        ServiceResponse<Boolean> response = new ServiceResponse<>();
        ExpressionList<CustomTab> query = getCustomMedicalTabQuery()
                .where()
                .eq("name", tabName);
        try{
            ICustomTab customTab = customTabRepository.findOne(query);
            if (customTab == null){
                response.setResponseObject(false);
            }else{
                response.setResponseObject(true);
            }
        }catch(Exception ex){
            response.setResponseObject(false);
        }
        return response;
    }

    @Override
    public ServiceResponse<Boolean> doesCustomFieldExist(String fieldName){
        ServiceResponse<Boolean> response = new ServiceResponse<>();
        ExpressionList<CustomField> query = getCustomFieldQuery()
                .where()
                .eq("name", fieldName);
        try{
            ICustomField customField = customFieldRepository.findOne(query);
            if (customField == null){
                response.setResponseObject(false);
            }else{
                response.setResponseObject(true);
            }

        }catch (Exception ex){
            response.setResponseObject(false);
        }
        return response;
    }

    @Override
    public ServiceResponse<List<CustomFieldItem>> getCustomFields(Boolean isDeleted, String tabName) {
        ServiceResponse<List<CustomFieldItem>> response = new ServiceResponse<>();
        Query<CustomField> query = getCustomFieldQuery()
                .fetch("customTab")
                .where()
                .eq("isDeleted", isDeleted)
                .eq("customTab.name", tabName)
                .order()
                .asc("sort_order");
        List<? extends ICustomField> customFields = customFieldRepository.find(query);
        List<CustomFieldItem> customFieldItems = new ArrayList<>();
        if (customFields == null) {
            response.addError("", "error");
        } else {
            for (ICustomField cf : customFields) {
                CustomFieldItem customFieldItem = getCustomFieldItem(cf);
                customFieldItems.add(customFieldItem);
            }
            response.setResponseObject(customFieldItems);
        }
        return response;
    }

    @Override
    public ServiceResponse<CustomFieldItem> toggleCustomField(String fieldName, String tabName){
        ServiceResponse<CustomFieldItem> response = new ServiceResponse<>();
        ExpressionList<CustomField> query = getCustomFieldQuery()
                .fetch("customTab")
                .where()
                .eq("name", fieldName)
                .eq("customTab.name", tabName);
        ICustomField customField = customFieldRepository.findOne(query);
        if (customField == null){
            response.addError("", "error");
        }
        customField.setIsDeleted(!customField.getIsDeleted());
        if (customField.getIsDeleted() == true){
            customField.setOrder(null);
        }
        customField = customFieldRepository.update(customField);
        CustomFieldItem customFieldItem = new CustomFieldItem();
        customFieldItem.setName(customField.getName());
        customFieldItem.setSize(customField.getCustomFieldSize().getName());
        customFieldItem.setType(customField.getCustomFieldType().getName());
        response.setResponseObject(customFieldItem);
        return response;

    }

    @Override
    public ServiceResponse<CustomTabItem> editCustomTab(CustomTabItem customTabItem, int userId){
        ServiceResponse<CustomTabItem> response = new ServiceResponse<>();
        if (customTabItem.getName() == null){
            response.addError("", "no item");
            return response;
        }

        ExpressionList<CustomTab> query = getCustomMedicalTabQuery()
                .where()
                .eq("name", customTabItem.getName());
        try{
            ICustomTab customTab = customTabRepository.findOne(query);
            customTab.setDateCreated(DateTime.now());
            customTab.setIsDeleted(customTab.getIsDeleted());
            customTab.setLeftColumnSize(customTabItem.getLeftColumnSize());
            customTab.setRightColumnSize(customTabItem.getRightColumnSize());
            customTab.setUserId(userId);
            customTab = customTabRepository.update(customTab);
            response.setResponseObject(customTabItem);
        }catch(Exception ex){
            response.addError("", "error");
        }
        return response;

    }

    @Override
    public ServiceResponse<CustomFieldItem> editCustomField(CustomFieldItem customFieldItem, int userId){
        ServiceResponse<CustomFieldItem> response = new ServiceResponse<>();
        if (customFieldItem.getName() == null){
            response.addError("", "no item");
            return response;
        }

        ExpressionList<CustomField> query = getCustomFieldQuery()
                .where()
                .eq("name", customFieldItem.getName());
        ICustomField customField = customFieldRepository.findOne(query);
        customField.setDateCreated(DateTime.now());
        customField.setUserId(userId);
        if (customFieldItem.getPlaceholder() != null){
            customField.setPlaceholder(customFieldItem.getPlaceholder());
        }
        if (customFieldItem.getOrder() != null){
            customField.setOrder(customFieldItem.getOrder());
        }
        //check size update
        if (StringUtils.isNotNullOrWhiteSpace(customFieldItem.getSize())){
            ExpressionList<CustomFieldSize> sizeQuery = getCustomFieldSizeQuery()
                    .where()
                    .eq("name", customFieldItem.getSize());
            ICustomFieldSize customFieldSize = customFieldSizeRepository.findOne(sizeQuery);
            customField.setCustomFieldSize((CustomFieldSize) customFieldSize);
        }
        //check type update
        if (StringUtils.isNotNullOrWhiteSpace(customFieldItem.getType())){
            ExpressionList<CustomFieldType> typeQuery = getCustomFieldTypeQuery()
                    .where()
                    .eq("name", customFieldItem.getType());
            ICustomFieldType customFieldType = customFieldTypeRepository.findOne(typeQuery);
            customField.setCustomFieldType((CustomFieldType) customFieldType);
        }
        customField = customFieldRepository.update(customField);
        response.setResponseObject(customFieldItem);
        return response;
    }

    @Override
    public ServiceResponse<CustomFieldItem> createCustomField(CustomFieldItem customFieldItem, int userId, String tabName) {
        ServiceResponse<CustomFieldItem> response = new ServiceResponse<>();
        if (StringUtils.isNullOrWhiteSpace(customFieldItem.getName()) || StringUtils.isNullOrWhiteSpace(customFieldItem.getSize()) || StringUtils.isNullOrWhiteSpace(customFieldItem.getType()) || StringUtils.isNullOrWhiteSpace(tabName)) {
            response.addError("", "error");
            return response;
        }

        //get tabs
        ExpressionList<CustomTab> tabQuery = getCustomMedicalTabQuery()
                .where()
                .eq("name", tabName);
        ICustomTab customTab = customTabRepository.findOne(tabQuery);
        if (customTab == null) {
            response.addError("", "error");
            return response;
        }

        //get field type
        ExpressionList<CustomFieldType> typeQuery = getCustomFieldTypeQuery()
                .where()
                .eq("name", customFieldItem.getType());
        ICustomFieldType customFieldType = customFieldTypeRepository.findOne(typeQuery);
        if (customFieldType == null) {
            response.addError("", "error");
            return response;
        }

        //get field size
        ExpressionList<CustomFieldSize> sizeQuery = getCustomFieldSizeQuery()
                .where()
                .eq("name", customFieldItem.getSize());
        ICustomFieldSize customFieldSize = customFieldSizeRepository.findOne(sizeQuery);
        if (customFieldSize == null) {
            response.addError("", "error");
            return response;
        }

        ICustomField customField = new CustomField();
        customField.setName(customFieldItem.getName());
        customField.setUserId(userId);
        customField.setDateCreated(DateTime.now());
        customField.setCustomTab((CustomTab) customTab);
        customField.setCustomFieldType((CustomFieldType) customFieldType);
        customField.setIsDeleted(false);
        customField.setCustomFieldSize((CustomFieldSize) customFieldSize);
        customField.setOrder(customFieldItem.getOrder());
        customField.setPlaceholder(customFieldItem.getPlaceholder());

        customField = customFieldRepository.create(customField);
        if (customField == null) {
            response.addError("", "error creating field");
        } else {
            response.setResponseObject(customFieldItem);
        }
        return response;
    }

    @Override
    public ServiceResponse<List<String>> getTypes(){
        ServiceResponse<List<String>> response = new ServiceResponse<>();
        List<String> fields = new ArrayList<>();
        List<? extends ICustomFieldType> customFieldTypes = customFieldTypeRepository.findAll(CustomFieldType.class);
        if (customFieldTypes == null){
            response.addError("","error");
        }else{
            for (ICustomFieldType cft : customFieldTypes){
                fields.add(cft.getName());
            }
            response.setResponseObject(fields);
        }
        return response;
    }

    @Override
    public ServiceResponse<List<String>> getSizes(){
        ServiceResponse<List<String>> response = new ServiceResponse<>();
        List<String> fields = new ArrayList<>();
        List<? extends ICustomFieldSize> customFieldSizes = customFieldSizeRepository.findAll(CustomFieldSize.class);
        if (customFieldSizes == null){
            response.addError("","error");
        }else{
            for (ICustomFieldSize cfs : customFieldSizes){
                fields.add(cfs.getName());
            }
            response.setResponseObject(fields);
        }
        return response;
    }

    private CustomFieldItem getCustomFieldItem(ICustomField cf){
        CustomFieldItem customFieldItem = new CustomFieldItem();
        customFieldItem.setName(cf.getName());
        customFieldItem.setType(cf.getCustomFieldType().getName());
        customFieldItem.setSize(cf.getCustomFieldSize().getName());
        customFieldItem.setOrder(cf.getOrder());
        customFieldItem.setPlaceholder(cf.getPlaceholder());
        return customFieldItem;
    }

    private CustomTabItem getCustomTabItem(ICustomTab ct){
        CustomTabItem customTabItem = new CustomTabItem();
        customTabItem.setLeftColumnSize(ct.getLeftColumnSize());
        customTabItem.setRightColumnSize(ct.getRightColumnSize());
        customTabItem.setName(ct.getName());
        return customTabItem;
    }

    private Query<CustomTab> getCustomMedicalTabQuery() {
        return Ebean.find(CustomTab.class);
    }

    private Query<CustomField> getCustomFieldQuery() {
        return Ebean.find(CustomField.class);
    }

    private Query<CustomFieldSize> getCustomFieldSizeQuery() {
        return Ebean.find(CustomFieldSize.class);
    }

    private Query<CustomFieldType> getCustomFieldTypeQuery() {
        return Ebean.find(CustomFieldType.class);
    }


}
