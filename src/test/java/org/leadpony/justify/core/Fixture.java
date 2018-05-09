/*
 * Copyright 2018 the Justify authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.leadpony.justify.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 * @author leadpony
 */
public class Fixture {

    private final JsonObject schema;
    private final String schemaDescription;
    private final JsonValue instance;
    private final String instanceDescription;
    private final boolean result;
    
    private Fixture(JsonObject schema, String schemaDescription, JsonValue instance, String instanceDescription, boolean result) {
        this.schema = schema;
        this.schemaDescription = schemaDescription;
        this.instance = instance;
        this.instanceDescription = instanceDescription;
        this.result = result;
    }
    
    public JsonObject schema() {
        return schema;
    }
    
    public String schemaDescription() {
        return schemaDescription;
    }

    public JsonValue instance() {
        return instance;
    }
    
    public String instanceDescription() {
        return instanceDescription;
    }

    public boolean result() {
        return result;
    }

    public static List<Fixture> load(String name) {
        JsonArray schemas = readArray(name);
        return schemas.stream()
            .map(JsonValue::asJsonObject)
            .flatMap(schema->{
                return schema.getJsonArray("tests").stream()
                        .map(JsonValue::asJsonObject)
                        .map(test->{
                            return new Fixture(
                                    schema.getJsonObject("schema"), 
                                    schema.getString("description"),
                                    test.get("data"), 
                                    test.getString("description"),
                                    test.getBoolean("valid")
                                    );
                        });
            })
            .collect(Collectors.toList());
    }
    
    private static JsonArray readArray(String name) {
        try (InputStream in = Resources.newInputStream(name)) {
            try (JsonReader reader = Json.createReader(in)) {
                return reader.readArray();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}