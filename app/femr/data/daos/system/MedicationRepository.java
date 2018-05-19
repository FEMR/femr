package femr.data.daos.system;

import femr.data.models.mysql.concepts.ConceptMedication;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;
import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IMedicationRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.Medication;
import femr.data.models.mysql.MedicationGeneric;
import femr.data.models.mysql.MedicationInventory;
import femr.data.models.mysql.concepts.ConceptMedicationForm;
import femr.data.models.mysql.concepts.ConceptMedicationUnit;
import femr.util.stringhelpers.StringUtils;
import play.Logger;

import java.util.List;

public class MedicationRepository implements IMedicationRepository {

    private final IDataModelMapper dataModelMapper;
    @Inject
    public MedicationRepository(IDataModelMapper dataModelMapper){
        this.dataModelMapper = dataModelMapper;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public IConceptMedicationUnit retrieveMedicationUnitByUnitName(String unitName){
        IConceptMedicationUnit medicationUnit = null;
        try {
            if(StringUtils.isNullOrWhiteSpace(unitName)){
                return null;
            }

            ExpressionList<ConceptMedicationUnit> medicationMeasurementUnitExpressionList
                    = QueryProvider.getConceptMedicationUnitQuery()
                                .where()
                                .eq("name", unitName);

            medicationUnit = medicationMeasurementUnitExpressionList.findOne();
        }catch (Exception ex) {
            Logger.error("MedicationRepository-retrieveMedicationUnitByUnitName", ex.getMessage(), "unitName: " + unitName);
            throw ex;
        }
        return medicationUnit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedicationGeneric retrieveMedicationGenericByName(String genericName){
        IMedicationGeneric medicationGeneric = null;
        try {
        if(StringUtils.isNullOrWhiteSpace(genericName)){
            return null;
        }

        ExpressionList<MedicationGeneric> medicationActiveDrugNameExpressionList;
        medicationActiveDrugNameExpressionList = QueryProvider.getMedicationGenericQuery()
                .where()
                .eq("name", genericName);

            medicationGeneric = medicationActiveDrugNameExpressionList.findOne();
        } catch(Exception ex){
            Logger.error("MedicationRepository-retrieveMedicationGenericByName", ex.getMessage(), "genericName: " + genericName);
            throw ex;
        }
        return medicationGeneric;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedicationInventory retrieveMedicationInventoryByMedicationIdAndTripId(int medicationId, int tripId) {

        IMedicationInventory medicationInventory = null;
        try {

            ExpressionList<MedicationInventory> medicationInventoryExpressionList;
            medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                    .where()
                    .eq("missionTrip.id", tripId)
                    .eq("medication.id", medicationId);

            medicationInventory = medicationInventoryExpressionList.findOne();
        } catch(Exception ex) {
            Logger.error("MedicationRepository-retrieveMedicationInventoryByMedicationIdAndTripId", ex.getMessage(), "medicationId: " + medicationId + "tripId: " + tripId);
            throw ex;
        }
        return medicationInventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IMedicationInventory> retrieveMedicationInventoriesByTripId(int tripId, Boolean isDeleted) {

        List<? extends IMedicationInventory> response;
        //Querying based on the trip id.  Each trip will have its own inventory.
        ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                .where()
                .eq("missionTrip.id", tripId);

        if (!isDeleted) {

            //does this work?
            medicationInventoryExpressionList.eq("isDeleted", null);
        }

        try {
            response = medicationInventoryExpressionList.findList();
        } catch (Exception ex) {
            Logger.error("MedicationRepository-retrieveMedicationInventoriesByTripId", ex.getMessage(), "trip id: " + tripId );
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IConceptMedicationForm retrieveConceptMedicationFormByFormName(String formName){
        ExpressionList<ConceptMedicationForm> medicationFormExpressionList;
        if(StringUtils.isNullOrWhiteSpace(formName)){
            return null;
        }
        IConceptMedicationForm conceptMedicationForm = null;
        medicationFormExpressionList = QueryProvider.getConceptMedicationFormQuery()
                .where()
                .eq("name", formName);
        try {
            conceptMedicationForm = medicationFormExpressionList.findOne();
        } catch(Exception ex){
            Logger.error("MedicationRepository-retrieveConceptMedicationFormByFormName", ex.getMessage(), "formName: " + formName);
            throw ex;
        }

        return conceptMedicationForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IMedication>  retrieveAllPreInventoryMedications() {
        // Based on fEMR-95.  Retrieve all medication with the same name AND not an old medication from previous trips
        // that did not require a medication form.
        Query<Medication> query = QueryProvider.getMedicationQuery()
                .where()
                .ne("concept_medication_forms_id", null)
                .orderBy("isDeleted asc");

        List<? extends IMedication> medications = null;
        try {
            medications = query.findList();
        } catch(Exception ex){
            Logger.error("MedicationRepository-retrieveAllPreInventoryMedications", ex.getMessage());
            throw ex;
        }

        return medications;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public IMedication deleteMedication (Integer medicationId, boolean isDeleted){
        ExpressionList<Medication> medicationQuery = QueryProvider.getMedicationQuery()
                .where()
                .eq("id", medicationId);

        IMedication medication = medicationQuery.findOne();
        medication.setIsDeleted(isDeleted);
        try {
            Ebean.save(medication);
        } catch (Exception ex) {
            Logger.error("MedicationRepository-deleteMedication", ex.getMessage(), "medicationId: " + medicationId, "isDeleted: " + isDeleted);
            throw ex;
        }

        return medication;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedication createNewMedication(String medicationName) {
        IMedication medication = null;

        if (StringUtils.isNullOrWhiteSpace(medicationName)){

            return null;
        }
        try{

            medication = dataModelMapper.createMedication(medicationName);
            Ebean.save(medication);
        } catch (Exception ex){

            Logger.error("Attempted and failed to execute createNewMedication("+medicationName+") in MedicationRepository. Stack trace to follow.");
            ex.printStackTrace();
            throw ex;
        }

        return medication;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedication createNewMedication (String medicationName, List<IMedicationGenericStrength> medicationGenericStrengths, IConceptMedicationForm conceptMedicationForm){
        IMedication medication = null;

        if (StringUtils.isNullOrWhiteSpace(medicationName)) {

            return null;
        }

        try {

            // Create a new medication in the DB
            medication = dataModelMapper.createMedication(medicationName, medicationGenericStrengths, conceptMedicationForm);
            Ebean.save(medication);
        } catch (Exception ex) {

            Logger.error("Attempted and failed to execute createNewMedication() in MedicationRepository. Stack trace to follow.");
            ex.printStackTrace();
            throw ex;
        }

        return medication;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IMedication> retrieveAllConceptMedications() {

        ExpressionList<ConceptMedication> conceptMedicationExpressionList = QueryProvider.getConceptMedicationQuery()
                .where()
                .eq("isDeleted", false);

        List<? extends IMedication> allMedications;

        try {

            allMedications = conceptMedicationExpressionList.findList();
        } catch (Exception ex) {

            Logger.error("MedicationRepository-retrieveAllConceptMedications", ex.getMessage(), ex);
            throw ex;
        }

        return allMedications;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedication retrieveConceptMedicationById(int id) {

        ExpressionList<ConceptMedication> conceptMedicationExpressionList = QueryProvider.getConceptMedicationQuery()
                .where()
                .eq("isDeleted", false)
                .eq("id", id);

        IMedication medication;

        try {

            medication = conceptMedicationExpressionList.findOne();
        } catch (Exception ex) {

            Logger.error("MedicationRepository-retrieveConceptMedicationById", ex.getMessage(), ex);
            throw ex;
        }

        return medication;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IConceptMedicationForm> retrieveAllConceptMedicationForms() {

        List<? extends IConceptMedicationForm> conceptMedicationForms;
        try{

            conceptMedicationForms = Ebean.find(ConceptMedicationForm.class).findList();
        } catch (Exception ex) {

            Logger.error("MedicationRepository-retrieveAllConceptMedicationForms", ex.getMessage());
            throw ex;
        }

        return conceptMedicationForms;
    }

    /**
     * {@inheritDoc}
     */
    public List<? extends IConceptMedicationUnit> retrieveAllConceptMedicationUnits() {

        List<? extends IConceptMedicationUnit> conceptMedicationUnits;
        try{

            conceptMedicationUnits = Ebean.find(ConceptMedicationUnit.class).findList();
        } catch (Exception ex) {

            Logger.error("MedicationRepository-retrieveAllConceptMedicationUnits", ex.getMessage());
            throw ex;
        }

        return conceptMedicationUnits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IMedication> retrieveAllMedicationByTripId(Integer tripId){

        List<? extends IMedication> response = null;
        try {

            Query<Medication> medicationQuery = QueryProvider.getMedicationQuery()
                    .where()
                    .eq("medicationInventory.missionTrip.id", tripId)
                    .isNotNull("conceptMedicationForm")
                    .isNull("medicationInventory.isDeleted")
                    .gt("medicationInventory.quantityCurrent", 0)
                    .orderBy("name");

            response = medicationQuery.findList();
        } catch (Exception ex) {

            Logger.error("MedicationRepository-retrieveAllMedicationByTripId", ex.getMessage(), "tripId: " + tripId);
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMedicationInventory saveMedicationInventory(IMedicationInventory medicationInventory) {

        try {

            Ebean.save(medicationInventory);
        } catch (Exception ex) {

            Logger.error("MedicationRepository-saveMedicationInventory", ex.getMessage());
            throw ex;
        }

        return medicationInventory;
    }

}
