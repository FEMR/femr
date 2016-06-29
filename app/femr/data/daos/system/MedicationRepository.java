package femr.data.daos.system;
import com.avaje.ebean.ExpressionList;
import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IMedicationRepository;
import femr.data.models.core.IConceptMedicationUnit;
import femr.data.models.core.IMedication;
import femr.data.models.mysql.MedicationGeneric;
import femr.data.models.mysql.concepts.ConceptMedicationUnit;
import femr.util.stringhelpers.StringUtils;
import play.Logger;
/**
 * Created by ajsaclayan on 6/27/16.
 */
public class MedicationRepository {
    /**
     * {@inheritDoc}
     */
    IConceptMedicationUnit retrieveMedicationUnitByUnitName(String unitName){
        if(StringUtils.isNullOrWhiteSpace(unitName)){
            return null;
        }
        IConceptMedicationUnit medicationUnit = null;
        ExpressionList<ConceptMedicationUnit> medicationMeasurementUnitExpressionList
                = QueryProvider.getConceptMedicationUnitQuery()
                            .where()
                            .eq("name", unitName);
        try {
            medicationUnit = medicationMeasurementUnitExpressionList.findUnique();
        }catch (Exception ex) {
            Logger.error("MedicationRepository-retrieveMedicationUnitByUnitName", ex.getMessage());
        }
        return medicationUnit;
    }

    /**
     * {@inheritDoc}
     */
    IMedication retrieveMedicationGenericByName(String genericName){
        if(StringUtils.isNullOrWhiteSpace(genericName)){
            return null;
        }
        IMedication medicationGeneric = null;
        ExpressionList<IMedication> medicationActiveDrugNameExpressionList;
        medicationActiveDrugNameExpressionList = QueryProvider.getMedicationQuery()
                .where()
                .eq("name", genericName);
        try {
            medicationGeneric = medicationActiveDrugNameExpressionList.findUnique();
        } catch(Exception ex){
            Logger.error("MedicationRepository-retrieveMedicationGenericByName", ex.getMessage());
        }
        return medicationGeneric;
    }
}
