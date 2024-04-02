package femr.ui.models.admin.updates;

import java.util.*;
import femr.data.models.core.ILanguageCode;
import femr.data.models.mysql.LanguageCode;

public class IndexViewModelGet {

    private ArrayList<ILanguageCode> languages;
    private Map<String, String> networkStatus;
    private Map<String, String> databaseStatus;
    private Map<String, String> kitStatus;
    private boolean isUpdateAvailable;

    public IndexViewModelGet(){
        this.languages = new ArrayList<>();
        this.networkStatus = new HashMap<>();
        this.databaseStatus = new HashMap<>();
        this.kitStatus = new HashMap<>();
        this.isUpdateAvailable = true;
    }

    public Map<String, String> getNetworkStatus() { return networkStatus; }
    public Map<String, String> getDatabaseStatus() {
        return databaseStatus;
    }
    public Map<String, String> getKitStatus() {
        return kitStatus;
    }
    public boolean isUpdateAvailable(){
        return this.isUpdateAvailable;
    }
    public ArrayList<ILanguageCode> getLanguages() { Collections.sort(languages); return languages; }


    public void setNetworkStatus(String name, String value){
        networkStatus.put(name, value);
    }
    public void setDatabaseStatus(String name, String value){
        databaseStatus.put(name, value);
    }
    public void setKitStatus(String name, String value){
        kitStatus.put(name, value);
    }
    public void setLanguages(ILanguageCode lang){ languages.add(lang); }

    public void setNetworkStatuses(Map<String, String> networkStatus) {
        this.networkStatus = networkStatus;
    }
    public void setDatabaseStatuses(Map<String, String> databaseStatus) {
        this.databaseStatus = databaseStatus;
    }
    public void setKitStatuses(Map<String, String> kitStatus) {
        this.kitStatus = kitStatus;
    }

    public void setIsUpdateAvailable(boolean bool){
        this.isUpdateAvailable = bool;
    }

}
