package femr.common.models;

/**
 * Created by kevin on 5/12/14.
 */
public class MedicationItem {
    private int id;
    private String name;
    private Integer quantity_current;
    private Integer quantity_total;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity_current() {
        return quantity_current;
    }

    public void setQuantity_current(Integer quantity_current) {
        this.quantity_current = quantity_current;
    }

    public Integer getQuantity_total() {
        return quantity_total;
    }

    public void setQuantity_total(Integer quantity_total) {
        this.quantity_total = quantity_total;
    }

    public void setId(int id) {
        this.id = id;
    }
}
