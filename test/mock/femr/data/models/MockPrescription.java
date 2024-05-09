package mock.femr.data.models;

import femr.data.models.core.*;
import femr.data.models.mysql.PatientPrescriptionReplacement;
import org.joda.time.DateTime;

import java.util.List;

public class MockPrescription implements IPatientPrescription {

    private Integer amount = -1;
    private IUser mockPhysician = null;
    private IMedication mockMedication = null;
    private IPatientEncounter mockPatientEncounter = null;
    private IConceptPrescriptionAdministration mockPrescriptionAdministration = null;

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public IMedication getMedication() {

        return mockMedication;
    }

    @Override
    public void setMedication(IMedication medication) {

        this.mockMedication = medication;
    }

    @Override
    public IConceptPrescriptionAdministration getConceptPrescriptionAdministration() {
        return mockPrescriptionAdministration;
    }

    @Override
    public void setConceptPrescriptionAdministration(IConceptPrescriptionAdministration conceptPrescriptionAdministration) {

        this.mockPrescriptionAdministration = conceptPrescriptionAdministration;
    }

    @Override
    public IUser getPhysician() {
        return mockPhysician;
    }

    @Override
    public void setPhysician(IUser mockPhysician) {

        this.mockPhysician = mockPhysician;
    }

    @Override
    public Integer getAmount() {
        return amount;
    }

    @Override
    public void setAmount(Integer amount) {

        this.amount = amount;
    }

    @Override
    public DateTime getDateTaken() {
        return null;
    }

    @Override
    public void setDateTaken(DateTime dateTaken) {

    }

    @Override
    public IPatientEncounter getPatientEncounter() {
        return mockPatientEncounter;
    }

    @Override
    public void setPatientEncounter(IPatientEncounter patientEncounter) {

        this.mockPatientEncounter = patientEncounter;
    }

    @Override
    public String getSpecialInstructions() {
        return null;
    }

    @Override
    public void setSpecialInstructions(String specialInstructions) {

    }

    @Override
    public boolean isCounseled() {
        return false;
    }

    @Override
    public void setCounseled(boolean isCounseled) {

    }

    @Override
    public List<PatientPrescriptionReplacement> getPatientPrescriptionReplacements() {
        return null;
    }

    @Override
    public void setPatientPrescriptionReplacements(List<PatientPrescriptionReplacement> patientPrescriptionReplacements) {

    }

    @Override
    public DateTime getDateDispensed() {
        return null;
    }

    @Override
    public void setDateDispensed(DateTime dateDispensed) {

    }
}
