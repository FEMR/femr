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
package femr.data.models.mysql;

import femr.data.models.core.IMeasurementUnit;

import javax.persistence.*;
@Entity
@Table(name = "measurement_units")
public class MeasurementUnit implements IMeasurementUnit {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "unit", unique = true, nullable = false, length=16)
    private String unit;
    @Column(name = "category", unique = true, nullable = false, length=16)
    private String category;



    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

  @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public void setUnit(String unit) {
        this.unit = unit;
    }

  @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }
}
