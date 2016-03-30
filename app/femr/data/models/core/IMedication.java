/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.data.models.core;

import femr.data.models.mysql.MedicationInventory;

import java.util.List;

/**
 * Represents a medication by Brand name instead of generic name. A brand name
 * can have multiple generic names in it.
 * Example: (Brand: Aleve, Generic: Naproxen, Sodium
 */
public interface IMedication {
    int getId();

    String getName();

    void setName(String name);

    Boolean getIsDeleted();

    void setIsDeleted(Boolean isDeleted);

    IConceptMedicationForm getConceptMedicationForm();

    void setConceptMedicationForm(IConceptMedicationForm conceptmedicationForm);

    List<IMedicationGenericStrength> getMedicationGenericStrengths();

    void setMedicationGenericStrengths(List<IMedicationGenericStrength> medicationGenericStrengths);

    public List<MedicationInventory> getMedicationInventory();

    public void setMedicationInventory(List<MedicationInventory> medicationInventory);
}
