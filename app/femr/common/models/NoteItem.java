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

import org.joda.time.DateTime;

public class NoteItem {
    private String name;
    private DateTime datetimestamp;
    private String reporter;

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) { this.reporter = reporter; }

    public DateTime getDate() {
        return datetimestamp;
    }

    public void setDate(DateTime datetimestamp) { this.datetimestamp = datetimestamp; }
}
