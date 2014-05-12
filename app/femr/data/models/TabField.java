package femr.data.models;

import femr.common.models.ITab;
import femr.common.models.ITabField;
import femr.common.models.ITabFieldSize;
import femr.common.models.ITabFieldType;

import javax.persistence.*;

@Entity
@Table(name = "tab_fields")
public class TabField implements ITabField {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tab_id", nullable = false)
    private Tab tab;
    @OneToOne
    @JoinColumn(name = "type_id", nullable = false)
    private TabFieldType tabFieldType;
    @OneToOne
    @JoinColumn(name = "size_id", nullable = false)
    private TabFieldSize tabFieldSize;
    @Column(name = "sort_order", nullable = true, unique = false)
    private Integer order;
    @Column(name = "placeholder", nullable = true, unique = false)
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
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public ITabFieldType getTabFieldType() {
        return tabFieldType;
    }

    @Override
    public void setTabFieldType(ITabFieldType tabFieldType) {
        this.tabFieldType = (TabFieldType) tabFieldType;
    }

    @Override
    public ITabFieldSize getTabFieldSize() {
        return tabFieldSize;
    }

    @Override
    public void setTabFieldSize(ITabFieldSize tabFieldSize) {
        this.tabFieldSize = (TabFieldSize) tabFieldSize;
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

    @Override
    public ITab getTab() {
        return tab;
    }

    @Override
    public void setTab(ITab tab) {
        this.tab = (Tab) tab;
    }
}
