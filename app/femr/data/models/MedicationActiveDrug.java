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

import javax.persistence.*;

@Entity
@Table(name = "medication_active_drugs")
public class MedicationActiveDrug implements IMedicationActiveDrug {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "medication_measurement_units_id")
    private MedicationMeasurementUnit medicationMeasurementUnit;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "medication_active_drug_names_id")
    private MedicationActiveDrugName medicationActiveDrugName;
    @Column(name = "isDenominator", nullable = false)
    private boolean isDenominator;
    @Column(name = "value", unique = true, nullable = false)
    private int value;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public IMedicationMeasurementUnit getMedicationMeasurementUnit() {
        return medicationMeasurementUnit;
    }

    @Override
    public void setMedicationMeasurementUnit(IMedicationMeasurementUnit medicationMeasurementUnit) {
        this.medicationMeasurementUnit = (MedicationMeasurementUnit) medicationMeasurementUnit;
    }

    @Override
    public IMedicationActiveDrugName getMedicationActiveDrugName() {
        return medicationActiveDrugName;
    }

    @Override
    public void setMedicationActiveDrugName(IMedicationActiveDrugName medicationActiveDrugName) {
        this.medicationActiveDrugName = (MedicationActiveDrugName) medicationActiveDrugName;
    }

    @Override
    public boolean isDenominator() {
        return isDenominator;
    }

    @Override
    public void setDenominator(boolean isDenominator) {
        this.isDenominator = isDenominator;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }
}
