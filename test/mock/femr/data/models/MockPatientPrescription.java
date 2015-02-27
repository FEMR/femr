//package mock.femr.data.models;
//
//import femr.data.models.*;
//import femr.data.models.core.IPatientPrescription;
//import org.joda.time.DateTime;
//
//public class MockPatientPrescription implements IPatientPrescription {
//
//    private int id;
//    private PatientEncounter patientEncounter;
//    private Medication medication;
//    private MedicationAdministration medicationAdministration;
//    private User physician;
//    private  int amount;
//    private DateTime dateTaken;
//    private Integer replacementId;
//    private String specialInstructions;
//
//    @Override
//    public int getId() {
//        return id;
//    }
//
//    @Override
//    public IPatientEncounter getPatientEncounter() {
//        return patientEncounter;
//    }
//
//    @Override
//    public void setPatientEncounter(IPatientEncounter patientEncounter) {
//        this.patientEncounter = (PatientEncounter) patientEncounter;
//    }
//
//    @Override
//    public IMedication getMedication() {
//        return medication;
//    }
//
//    @Override
//    public void setMedication(IMedication medication) {
//        this.medication = (Medication) medication;
//    }
//
//    @Override
//    public IMedicationAdministration getMedicationAdministration() {
//        return medicationAdministration;
//    }
//
//    @Override
//    public void setMedicationAdministration(IMedicationAdministration medicationAdministration) {
//        this.medicationAdministration = (MedicationAdministration) medicationAdministration;
//    }
//
//    @Override
//    public IUser getPhysician() {
//        return physician;
//    }
//
//    @Override
//    public void setPhysician(IUser physician) {
//        this.physician = (User) physician;
//    }
//
//    @Override
//    public int getAmount() {
//        return amount;
//    }
//
//    @Override
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }
//
//    @Override
//    public Integer getReplacementId() {
//        return replacementId;
//    }
//
//    @Override
//    public void setReplacementId(Integer replacementId) {
//        this.replacementId = replacementId;
//    }
//
//    @Override
//    public DateTime getDateTaken() {
//        return dateTaken;
//    }
//
//    @Override
//    public void setDateTaken(DateTime dateTaken) {
//        this.dateTaken = dateTaken;
//    }
//
//    @Override
//    public String getSpecialInstructions() {
//        return specialInstructions;
//    }
//
//    @Override
//    public void setSpecialInstructions(String specialInstructions) {
//        this.specialInstructions = specialInstructions;
//    }
//}
