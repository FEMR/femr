package femr.ui.models.history;

import java.util.List;
import java.util.Map;

public class IndexEncounterPharmacyViewModel {
    private List<String> problems;
    private List<String> prescriptions;
    //list of prescriptions and their replacements if applicable
//    private List<Map<String, String>> prescriptionList;

    public List<String> getProblems() {
        return problems;
    }

    public void setProblems(List<String> problems) {
        this.problems = problems;
    }

    public List<String> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<String> prescriptions) {
        this.prescriptions = prescriptions;
    }

//    public List<Map<String, String>> getPrescriptionList() {
//        return prescriptionList;
//    }
//
//    public void setPrescriptionList(List<Map<String, String>> prescriptionList) {
//        this.prescriptionList = prescriptionList;
//    }
}
