package femr.ui.models.medical;

public class CreateViewModelPost {
    //patient identifier
    private int id;
    //treatment fields
    private String assessment;
    private String problem;
    private String prescription1;
    private String prescription2;
    private String prescription3;
    private String prescription4;
    private String prescription5;
    private String treatment;
    private String familyHistory;

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getPrescription1() {
        return prescription1;
    }

    public void setPrescription1(String prescription1) {
        this.prescription1 = prescription1;
    }
    public String getPrescription2() {
        return prescription2;
    }

    public void setPrescription2(String prescription2) {
        this.prescription2 = prescription2;
    }

    public String getPrescription3() {
        return prescription3;
    }

    public void setPrescription3(String prescription3) {
        this.prescription3 = prescription3;
    }

    public String getPrescription4() {
        return prescription4;
    }

    public void setPrescription4(String prescription4) {
        this.prescription4 = prescription4;
    }

    public String getPrescription5() {
        return prescription5;
    }

    public void setPrescription5(String prescription5) {
        this.prescription5 = prescription5;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
