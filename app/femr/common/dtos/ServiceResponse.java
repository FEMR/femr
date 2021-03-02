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
package femr.common.dtos;

import femr.common.models.MissionTripItem;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponse<T> {
    private T responseObject;
    private Map<String, String> errors;

    public ServiceResponse() {
        this.responseObject = null;
        this.errors = new HashMap<>();

    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public T getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(T responseObject) {
        this.responseObject = responseObject;
    }

    public void addError(String field, String error) {
        errors.put(field, error);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
