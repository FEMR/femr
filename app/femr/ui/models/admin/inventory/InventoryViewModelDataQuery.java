package femr.ui.models.admin.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alaa Serhan on 3/29/2015.
 * This is the model that the bs_grid reqests are bound to
 * All setters here much correspond to fields passed in the request by bs_grid
 * for the model to map properly
 */
public class InventoryViewModelDataQuery {
    public List<DataGridColumn> columns; // Bind columns[] array to a list of DataGridColumn
    private List<DataGridSorting> sorting;  // Bind sorting[] array to a list of DataGridSorting
    private List<DataGridFilter> filters; // Bind filter_rules[] array to a list of DataGridFilter
    private int pageNum = 0; // Bind grid pageNum
    private int rowsPerPage = 0; // Bind rowsPerPage

    public void setPage_num(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPage_num() {
        return this.pageNum;
    }

    public void setRows_per_page(int rows) {
        this.rowsPerPage = rows;
    }
    public int getRows_per_page() {
        return this.rowsPerPage;
    }



    public void setSorting(List<DataGridSorting> sorting) {
        this.sorting = sorting;
    }
    public List<DataGridSorting> getSorting() {
        return this.sorting;
    }

    public void setColumns(List<DataGridColumn> columns) {
        this.columns = columns;
    }
    public List<DataGridColumn> getColumns() {
        return this.columns;
    }

    public List<DataGridFilter> getFilter_rules() {
        return filters;
    }

    public void setFilter_rules(List<DataGridFilter> filters) {
        this.filters = filters;
    }
}
