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
package femr.ui.models.medical;

import femr.common.models.PrescriptionItem;
import femr.common.models.ProblemItem;
import femr.common.models.TabFieldItem;
import java.util.List;

public class EditViewModelPost {

    private int id;

    private List<TabFieldItem> tabFieldItems;
    private List<PrescriptionItem> prescriptions;
    private List<ProblemItem> problems;

    //Photo stuff
    private List<Boolean> deleteRequested;
    private List<Boolean> hasUpdatedDesc;
    private List<String>  imageDescText;
    private List<Integer> photoId;

    public List<Boolean> getDeleteRequested() {
        return deleteRequested;
    }

    public void setDeleteRequested(List<Boolean> deleteRequested) {
        this.deleteRequested = deleteRequested;
    }

    public List<Boolean> getHasUpdatedDesc() {
        return hasUpdatedDesc;
    }

    public void setHasUpdatedDesc(List<Boolean> hasUpdatedDesc) {
        this.hasUpdatedDesc = hasUpdatedDesc;
    }

    public List<Integer> getPhotoId() {
        return photoId;
    }

    public void setPhotoId(List<Integer> photoId) {
        this.photoId = photoId;
    }

    public List<String> getImageDescText() { return imageDescText; }

    public void setImageDescText(List<String> lst) { this.imageDescText = lst; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TabFieldItem> getTabFieldItems() {
        return tabFieldItems;
    }

    public void setTabFieldItems(List<TabFieldItem> tabFieldItems) {
        this.tabFieldItems = tabFieldItems;
    }

    public List<PrescriptionItem> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionItem> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<ProblemItem> getProblems() {
        return problems;
    }

    public void setProblems(List<ProblemItem> problems) {
        this.problems = problems;
    }
}
