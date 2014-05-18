package femr.common.models.custom;

import femr.data.models.custom.CustomFieldSize;
import femr.data.models.custom.CustomFieldType;
import femr.data.models.custom.CustomTab;
import org.joda.time.DateTime;

public interface ICustomField {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    int getUserId();

    void setUserId(int userId);

    DateTime getDateCreated();

    void setDateCreated(DateTime dateCreated);

    Boolean getIsDeleted();

    void setIsDeleted(Boolean isDeleted);

    CustomTab getCustomTab();

    void setCustomTab(CustomTab customTab);

    CustomFieldType getCustomFieldType();

    void setCustomFieldType(CustomFieldType customFieldType);

    CustomFieldSize getCustomFieldSize();

    void setCustomFieldSize(CustomFieldSize customFieldSize);

    Integer getOrder();

    void setOrder(Integer order);

    String getPlaceholder();

    void setPlaceholder(String placeholder);
}
