package femr.data.daos.core;

import femr.data.models.core.IConceptMedicationUnit;
import femr.data.models.core.IMedication;

/**
 * Created by ajsaclayan on 6/27/16.
 */
public interface IMedicationRepository {
    /**
      *  Retrieve medication units by unit name
      *  @param  unitName name of the medication, not null
      *  @return the unit name and description
     **/
    IConceptMedicationUnit retrieveMedicationUnitByUnitName(String unitName);

    /**
     *  Retrieve generic name if it exists in the database by generic name
     *  @param  genericName name of the medication, not null
     *  @return the generic name if it exists, may be null
     **/
    IMedication retrieveMedicationGenericByName(String genericName);
}
