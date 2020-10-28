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

import com.google.gson.*;

import java.util.*;

public class GsonFlattener {
    public Map<String, String> parse(JsonObject jsonObject) {
        Map<String, String> flatJson = new HashMap<String, String>();
        flatten(jsonObject, flatJson, "");
        return flatJson;
    }

    public List<Map<String, String>> parse(JsonArray jsonArray) {
        List<Map<String, String>> flatJson = new ArrayList<Map<String, String>>();

        int length = jsonArray.size();
        for (int i = 0; i < length; i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            Map<String, String> stringMap = parse(jsonObject);
            flatJson.add(stringMap);
        }
        return flatJson;
    }

    public List<Map<String, String>> parseJson(String json) { //} throws Exception {
        List<Map<String, String>> flatJson = null;
//        try {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        flatJson = new ArrayList<Map<String, String>>();
        flatJson.add(parse(jsonObject));
//        } catch (JsonEx je) {
//            flatJson = handleAsArray(json);
//        }
        return flatJson;
    }

    public List<Map<String, String>> parseJsonAsArray(String json) { //} throws Exception {
        List<Map<String, String>> flatJson = null;
//        try {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        flatJson = new ArrayList<Map<String, String>>();
        flatJson.add(parse(jsonObject));
//        } catch (JsonEx je) {
//            flatJson = handleAsArray(json);
//        }
        return flatJson;
    }

    private List<Map<String, String>> handleAsArray(String json) { //} throws Exception {
        List<Map<String, String>> flatJson = null;
//        try {
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(json).getAsJsonArray();
        flatJson = parse(jsonArray);
//        } catch (Exception e) {
//            throw new Exception("Json might be malformed");
//        }
        return flatJson;
    }

    private void flatten(JsonArray obj, Map<String, String> flatJson, String prefix) {
        int length = obj.size();
        for (int i = 0; i < length; i++) {
            if (obj.get(i).getClass() == JsonArray.class) {
                JsonArray jsonArray = obj.get(i).getAsJsonArray();
                if (jsonArray.size() < 1) continue;
                flatten(jsonArray, flatJson, prefix + i);
            } else if (obj.get(i).getClass() == JsonObject.class) {
                JsonObject jsonObject = obj.get(i).getAsJsonObject();
                flatten(jsonObject, flatJson, prefix + (i + 1));
            } else {
                String value = obj.get(i).getAsString();
                if (value != null)
                    flatJson.put(prefix + (i + 1), value);
            }
        }
    }

    private void flatten(JsonObject obj, Map<String, String> flatJson, String prefix) {


        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {

            if (entry.getValue().isJsonObject()) {

                JsonObject jsonObject = entry.getValue().getAsJsonObject();
                flatten(jsonObject, flatJson, prefix);
            } else if (entry.getValue().isJsonArray()) {

                JsonArray jsonArray = entry.getValue().getAsJsonArray();
                flatten(jsonArray, flatJson, entry.getKey());
            } else {

                String value = entry.getValue().getAsString();
                if (value != null && !value.equals("null"))
                    flatJson.put(prefix + entry.getKey(), value);
            }
        }

    }
}

