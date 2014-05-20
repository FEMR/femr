package femr.business.services;


import femr.business.dtos.ServiceResponse;
import femr.common.models.custom.ICustomTab;
import femr.ui.models.data.custom.CustomFieldItem;
import femr.ui.models.data.custom.CustomTabItem;

import java.util.List;

public interface ISuperuserService {
    ServiceResponse<List<CustomTabItem>> getCustomMedicalTabs(Boolean isDeleted);
    ServiceResponse<Boolean> doesCustomTabExist(String tabName);
    ServiceResponse<ICustomTab> createCustomMedicalTab(ICustomTab newTab);
    ServiceResponse<ICustomTab> toggleCustomMedicalTab(String name);
    ServiceResponse<CustomTabItem> editCustomTab(CustomTabItem customTabItem, int userId);

    ServiceResponse<List<CustomFieldItem>> getCustomFields(Boolean isDeleted, String tabName);
    ServiceResponse<Boolean> doesCustomFieldExist(String fieldName);
    ServiceResponse<CustomFieldItem> createCustomField(CustomFieldItem customFieldItem, int userId, String tabName);
    ServiceResponse<CustomFieldItem> toggleCustomField(String fieldName, String tabName);
    ServiceResponse<CustomFieldItem> editCustomField(CustomFieldItem customFieldItem, int userId);

    ServiceResponse<List<String>> getTypes();
    ServiceResponse<List<String>> getSizes();
}
