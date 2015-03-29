package femr.ui.models.admin.inventory;

/**
 * Created by Amney Iskandar on 3/30/2015.
 * Model to load the columns[] from bs_grid into when the form is bound
 */
public class DataGridColumn {
    private String field;
    private String header;


    public void setField(String field){
        this.field = field;
    }
    public String getField() {
        return this.field;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    public String getHeader() {
        return this.header;
    }
}