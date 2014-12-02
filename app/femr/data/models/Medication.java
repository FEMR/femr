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
import java.util.List;

@Entity
@Table(name = "medications")
public class Medication implements IMedication {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", unique = true, nullable = true)
    private String name;
    @Column(name = "quantity_current", unique = false, nullable = true)
    private Integer quantity_current;
    @Column(name = "quantity_initial", unique = false, nullable = true)
    private Integer quantity_initial;
    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "medication_forms_id")
    private MedicationForm medicationForm;
    @ManyToMany(fetch = FetchType.EAGER,
            targetEntity = MedicationActiveDrug.class,
            cascade = CascadeType.ALL)
    @JoinTable(name = "medication_medication_active_drugs",
            joinColumns = {@JoinColumn(name = "medications_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "medication_active_drugs_id", referencedColumnName = "id")})
    private List<IMedicationActiveDrug> medicationActiveDrugs;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getQuantity_current() {
        return quantity_current;
    }

    @Override
    public void setQuantity_current(Integer quantity_current) {
        this.quantity_current = quantity_current;
    }

    @Override
    public Integer getQuantity_total() {
        return quantity_initial;
    }

    @Override
    public void setQuantity_total(Integer quantity_initial) {
        this.quantity_initial = quantity_initial;
    }

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public IMedicationForm getMedicationForm() {
        return medicationForm;
    }

    @Override
    public void setMedicationForm(IMedicationForm medicationForm) {
        this.medicationForm = (MedicationForm) medicationForm;
    }

    @Override
    public List<IMedicationActiveDrug> getMedicationActiveDrugs() {
        return medicationActiveDrugs;
    }

    @Override
    public void setMedicationActiveDrugs(List<IMedicationActiveDrug> medicationActiveDrugs) {
        this.medicationActiveDrugs = medicationActiveDrugs;
    }
}
