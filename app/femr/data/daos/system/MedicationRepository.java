package femr.data.daos.system;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IMedicationRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.Medication;
import femr.data.models.mysql.MedicationGeneric;
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
                    .eq("name", 12);

            medicationUnit = medicationMeasurementUnitExpressionList.findUnique();
            medicationUnit.getDescription();
        } catch (Exception ex) {

            Logger.error("unitName: " + unitName);
            Logger.error("MedicationRepository-retrieveMedicationUnitByUnitName", ex);
            ex.printStackTrace();
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

            medicationGeneric = medicationActiveDrugNameExpressionList.findUnique();
        } catch(Exception ex){

            Logger.error("genericName: " + genericName);
            Logger.error("MedicationRepository-retrieveMedicationGenericByName", ex);
            ex.printStackTrace();
            throw ex;
        }
        return medicationGeneric;
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
            conceptMedicationForm = medicationFormExpressionList.findUnique();
        } catch(Exception ex){

            Logger.error("formName: " + formName);
            Logger.error("MedicationRepository-retrieveConceptMedicationFormByFormName", ex);
            ex.printStackTrace();
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

            Logger.error("MedicationRepository-retrieveAllPreInventoryMedications", ex);
            ex.printStackTrace();
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

        IMedication medication = medicationQuery.findUnique();
        medication.setIsDeleted(isDeleted);
        try {
            Ebean.save(medication);
        } catch (Exception ex) {

            Logger.error("medicationId: " + medicationId + "isDeleted: " + isDeleted);
            Logger.error("MedicationRepository-deleteMedication", ex);
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

        if (medicationName == null || medicationGenericStrengths == null || conceptMedicationForm == null) {
            return null;
        }

        try {

            // Create a new medication in the DB
            medication = dataModelMapper.createMedication(medicationName, medicationGenericStrengths, conceptMedicationForm);
            Ebean.save(medication);
        } catch (Exception ex) {

            Logger.error("medicationName: " + medicationName + "medicationGenericStrengths object, conceptMedicationForm object");
            Logger.error("MedicationRepository-createNewMedication", ex);
            ex.printStackTrace();
            throw ex;
        }

        return medication;
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
                    .gt("medicationInventory.quantityCurrent", 0)
                    .orderBy("name");

            response = medicationQuery.findList();
        } catch (Exception ex) {

            Logger.error("tripId: " + tripId);
            Logger.error("MedicationRepository-retrieveAllMedicationByTripId", ex);
            ex.printStackTrace();
            throw ex;
        }

        return response;
    }

}
