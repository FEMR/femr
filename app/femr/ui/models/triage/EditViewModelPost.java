package femr.ui.models.triage;

import femr.common.models.CityItem;

import java.util.List;

/**
 * Created by AJ Saclayan Cities on 11/9/2015.
 */
public class EditViewModelPost {
    private int id;

    public List<CityItem> cities;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
}
