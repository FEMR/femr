package femr.ui.models.search.json;

/**
 * AJ Saclayan
 * Created by 212433740 on 11/9/2015.
 */
public class CitySearch {
    //an object designed to represent a city while a user is searching
    String id;
    String name;
    String missionCountryId;
    public void setId(String id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setMissionCountryId(String missionCountryId){this.missionCountryId = missionCountryId;}

}
