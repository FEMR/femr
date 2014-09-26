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
package femr.common.models;

/**
 * Created by kevin on 5/12/14.
 */
public class MedicationItem {
    private int id;
    private String name;
    private Integer quantity_current;
    private Integer quantity_total;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity_current() {
        return quantity_current;
    }

    public void setQuantity_current(Integer quantity_current) {
        this.quantity_current = quantity_current;
    }

    public Integer getQuantity_total() {
        return quantity_total;
    }

    public void setQuantity_total(Integer quantity_total) {
        this.quantity_total = quantity_total;
    }

    public void setId(int id) {
        this.id = id;
    }
}
