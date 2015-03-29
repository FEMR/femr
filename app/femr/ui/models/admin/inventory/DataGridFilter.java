package femr.ui.models.admin.inventory;

/**
 * Created by Andre Farah on 3/30/2015.
 * Model to load the filters[] from bs_grid into when the form is bound
 */
public class DataGridFilter {
    private DataGridFilterCondition condition;
    private String logicalOperator;

    public DataGridFilterCondition getCondition() {
        return condition;
    }

    public void setCondition(DataGridFilterCondition condition) {
        this.condition = condition;
    }

    /* Named using underscore because bs_grid has it named using underscore,
       So for the form to bind properly had to match the naming
     */
    public String getLogical_operator() {
        return logicalOperator;
    }

    /* Named using underscore because bs_grid has it named using underscore,
       So for the form to bind properly had to match the naming
     */
    public void setLogical_operator(String logicalOperator) {
        this.logicalOperator = logicalOperator;
    }
}
