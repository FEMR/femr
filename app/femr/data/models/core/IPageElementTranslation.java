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

/**
 * ConceptMedicationUnit represents the unit of
 * measurement for a medication. e.g. g/mg/mL. This
 * is a concept.
 */
public interface IPageElementTranslation {
    int getTranslationId();

    void setTranslationId(int TranslationId);

    int getElementId();

    void setElementId(int ElementId);

    String getLanguageCode();

    void setLanguageCode(String LanguageCode);
    String getTranslation();

    void setTranslation(String Translation);



}
