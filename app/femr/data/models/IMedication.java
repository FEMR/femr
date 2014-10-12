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
package femr.data.models;

import java.util.List;

/**
 * Represents a medication by name (not active/inactive ingrediant).
 * Also keeps track of inventory - current quantity should be altered
 * when creating a prescription.
 */
public interface IMedication {
    int getId();

    String getName();

    void setName(String name);

    Integer getQuantity_current();

    void setQuantity_current(Integer quantity_current);

    Integer getQuantity_total();

    void setQuantity_total(Integer quantity_initial);

    Boolean getIsDeleted();

    void setIsDeleted(Boolean isDeleted);

    IMedicationForm getMedicationForm();

    void setMedicationForm(IMedicationForm medicationForm);

    List<IMedicationActiveDrug> getMedicationActiveDrugs();

    void setMedicationActiveDrugs(List<IMedicationActiveDrug> medicationActiveDrugs);
}
