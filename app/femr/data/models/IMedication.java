package femr.data.models;


public interface IMedication {
    int getId();

    String getName();

    void setName(String name);

    Integer getQuantity_current();

    void setQuantity_current(Integer quantity_current);

    Integer getQuantity_total();

    void setQuantity_total(Integer quantity_initial);

    Boolean getIsDeleted();

    void setIsDeleted(Boolean isDeleted);
}
