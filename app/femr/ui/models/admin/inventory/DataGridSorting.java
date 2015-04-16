package femr.ui.models.admin.inventory;

/**
 * Created by Amney Iskandar on 3/30/2015.
 * Model to load the sorting[] from bs_grid into when the form is bound
 */
public class DataGridSorting {
    private String sortName;
    private String field;
    private String order;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }

    public String getField() {
        return field;

    }
    public void setField(String field) {
        this.field = field;
    }

    public String toString() {
        if (order == null || field == null) return "";
        if (order.equalsIgnoreCase("descending")) return field + " desc";
        if (order.equalsIgnoreCase("ascending")) return field + " asc";
        return "";
    }
}
