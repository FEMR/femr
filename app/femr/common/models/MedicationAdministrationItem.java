package femr.common.models;

import femr.data.models.mysql.MedicationAdministration;

/**
 * Created by owner1 on 4/8/2015.
 */
public class MedicationAdministrationItem {
    private int Id;
    private String name;
    private float dailyModifier;

    public MedicationAdministrationItem() {}

    public MedicationAdministrationItem(int Id, String name, float dailyModifier) {
        this.Id = Id;
        this.name = name;
        this.dailyModifier = dailyModifier;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDailyModifier() {
        return dailyModifier;
    }

    public void setDailyModifier(float dailyModifier) {
        this.dailyModifier = dailyModifier;
    }
}
