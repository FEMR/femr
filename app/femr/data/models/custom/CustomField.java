package femr.data.models.custom;

import femr.common.models.custom.ICustomField;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "custom_fields")
public class CustomField implements ICustomField {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "user_id", unique = false, nullable = false)
    private int userId;
    @Column(name = "date_created", nullable = false)
    private DateTime dateCreated;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "custom_tab_id", nullable = false)
    private CustomTab customTab;
    @OneToOne
    @JoinColumn(name = "custom_type_id", nullable = false)
    private CustomFieldType customFieldType;
    @OneToOne
    @JoinColumn(name = "custom_size_id", nullable = false)
    private CustomFieldSize customFieldSize;
    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted;
    @Column(name = "sort_order", nullable = true, unique = false)
    private Integer order;
    @Column(name="placeholder", nullable = true, unique = false)
    private String placeholder;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public DateTime getDateCreated() {
        return dateCreated;
    }

    @Override
    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public CustomTab getCustomTab() {
        return customTab;
    }

    @Override
    public void setCustomTab(CustomTab customTab) {
        this.customTab = customTab;
    }

    @Override
    public CustomFieldType getCustomFieldType() {
        return customFieldType;
    }

    @Override
    public void setCustomFieldType(CustomFieldType customFieldType) {
        this.customFieldType = customFieldType;
    }

    @Override
    public CustomFieldSize getCustomFieldSize() {
        return customFieldSize;
    }

    @Override
    public void setCustomFieldSize(CustomFieldSize customFieldSize) {
        this.customFieldSize = customFieldSize;
    }

    @Override
    public Integer getOrder() {
        return order;
    }

    @Override
    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}
