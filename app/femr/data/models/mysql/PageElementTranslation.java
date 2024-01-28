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

import femr.data.models.core.IMeasurementCategory;
import femr.data.models.core.IPageElementTranslation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "page_element_translation")
public class PageElementTranslation implements IPageElementTranslation {

    @Id
    @Column(name = "language_code", unique = true, nullable = false, length=5)
    private String language_code;

    @Override
    public int getTranslationId() {
        return 0;
    }

    @Override
    public void setTranslationId(int TranslationId) {

    }

    @Override
    public int getElementId() {
        return 0;
    }

    @Override
    public void setElementId(int ElementId) {

    }

    @Override
    public String getLanguageCode() {
        return null;
    }

    @Override
    public void setLanguageCode(String LanguageCode) {

    }

    @Override
    public String getTranslation() {
        return null;
    }

    @Override
    public void setTranslation(String Translation) {

    }
}
