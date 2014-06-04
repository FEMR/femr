package femr.data.models;

public interface ITabField {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Boolean getIsDeleted();

    void setIsDeleted(Boolean isDeleted);

    ITabFieldType getTabFieldType();

    void setTabFieldType(ITabFieldType tabFieldType);

    ITabFieldSize getTabFieldSize();

    void setTabFieldSize(ITabFieldSize tabFieldSize);

    Integer getOrder();

    void setOrder(Integer order);

    String getPlaceholder();

    void setPlaceholder(String placeholder);

    ITab getTab();

    void setTab(ITab tab);
}
