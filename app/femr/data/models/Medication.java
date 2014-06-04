package femr.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medications")
public class Medication implements IMedication {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", unique = true, nullable = true)
    private String name;
    @Column(name = "quantity_current", unique = false, nullable = true)
    private Integer quantity_current;
    @Column(name = "quantity_initial", unique = false, nullable = true)
    private Integer quantity_initial;
    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted;

    @Override
    public int getId() {
        return id;
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
    public Integer getQuantity_current() {
        return quantity_current;
    }

    @Override
    public void setQuantity_current(Integer quantity_current) {
        this.quantity_current = quantity_current;
    }

    @Override
    public Integer getQuantity_total() {
        return quantity_initial;
    }

    @Override
    public void setQuantity_total(Integer quantity_initial) {
        this.quantity_initial = quantity_initial;
    }

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
