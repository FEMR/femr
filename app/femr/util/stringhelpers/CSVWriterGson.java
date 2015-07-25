/*
    This file was originally taken from SEL-Columbia's json-to-csv repository and is licensed under Apache 2.0
    to "Dristhi software" (https://github.com/SEL-Columbia/json-to-csv).

    See relevant licensing from Dristhi software below for more information.

    It has been modified by Team fEMR to support Gson.

-----------------------------

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

----------------------------

    Dristhi software

    Copyright 2012-2014

    Foundation for Research in Health Systems; Sustainable Engineering
    Lab, Columbia University; and The Special Programme of Research,
    Development and Research Training in Human Reproduction (HRP), World
    Health Organization.

    Licensed under the Apache License, Version 2.0 (the "License"); you may
    not use this file except in compliance with the License. You may obtain
    a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
    License for the specific language governing permissions and limitations
    under the License.

    Notice: The licensors gratefully acknowledge the generous support of the
    Wellcome Trust, the Norwegian Agency for Development Cooperation, and
    the Bill and Melinda Gates Foundation; and the technical contributions
    of ThoughtWorks; which have contributed to making this software possible.
*/
package femr.util.stringhelpers;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CSVWriterGson {

    public void writeAsCSV(List<Map<String, String>> flatJson, String fileName) throws FileNotFoundException {
        Set<String> headers = collectHeaders(flatJson);

        String output = "";

        String headerStr[] = new String[headers.size()];
        headerStr = headers.toArray(headerStr);
        for( int i = 0; i < headerStr.length; i++ ){

            output += headerStr[i];
            if( i < headerStr.length - 1 ) output += ',';
        }
        output += "\n";

        for (Map<String, String> map : flatJson) {
            output = output + getCommaSeperatedRow(headers, map);
        }
        writeToFile(output, fileName);
    }

    private void writeToFile(String output, String fileName) throws FileNotFoundException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(writer);
        }
    }

    private void close(BufferedWriter writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCommaSeperatedRow(Set<String> headers, Map<String, String> map) {
        List<String> items = new ArrayList<String>();
        for (String header : headers) {
            String value = map.get(header) == null ? "" : map.get(header).replace(",", "");
            items.add(value);
        }

        String output = "";
        String itemStr[] = new String[items.size()];
        itemStr = items.toArray(itemStr);
        for( int i = 0; i < itemStr.length; i++ ){

            String current = itemStr[i];

            // escape double quotes with double quotes
            if( current.contains("\"") ){

                current = current.replaceAll("\"", "\"\"");
            }

            // If string contains newline or comma add surrounding quotes
            if( current.contains("\r\n") || current.contains("\n") || current.contains(",") ) {
                current = '"'+current+'"';
            }

            output += current;
            if( i < itemStr.length - 1 ) output += ',';
        }
        output += "\n";

        return output;
    }

    private Set<String> collectHeaders(List<Map<String, String>> flatJson) {
        Set<String> headers = new TreeSet<String>();
        for (Map<String, String> map : flatJson) {
            headers.addAll(map.keySet());
        }
        return headers;
    }
}
