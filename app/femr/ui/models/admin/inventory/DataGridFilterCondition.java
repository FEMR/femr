package femr.ui.models.admin.inventory;

import java.util.List;

/**
 * Created by Andre Farah on 3/30/2015.
 * Model to load the the conditions from each filters[] from bs_grid into when the form is bound
 */
public class DataGridFilterCondition {
    private String field;
    private String filterType;
    private List<String> filterValue;
    private String operator;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public List<String> getFilterValue() { return filterValue; }

    public void setFilterValue(List<String> filterValue) {
        this.filterValue = filterValue;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
