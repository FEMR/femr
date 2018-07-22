package mock.femr.data.models;

import femr.data.models.core.IConceptMedicationForm;
import femr.data.models.core.IMedication;
import femr.data.models.core.IMedicationGenericStrength;
import femr.data.models.mysql.MedicationInventory;

import java.util.List;

public class MockMedication implements IMedication {

    private int id = -1;
    private String name = "Tylenol";
    private Boolean isDeleted = false;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public void setName(String name) {

        this.name = name;
    }

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Boolean isDeleted) {

        this.isDeleted = isDeleted;
    }

    @Override
    public IConceptMedicationForm getConceptMedicationForm() {
        return null;
    }

    @Override
    public void setConceptMedicationForm(IConceptMedicationForm conceptmedicationForm) {

    }

    @Override
    public List<IMedicationGenericStrength> getMedicationGenericStrengths() {
        return null;
    }

    @Override
    public void setMedicationGenericStrengths(List<IMedicationGenericStrength> medicationGenericStrengths) {

    }

    @Override
    public List<MedicationInventory> getMedicationInventory() {
        return null;
    }

    @Override
    public void setMedicationInventory(List<MedicationInventory> medicationInventory) {

    }
}
