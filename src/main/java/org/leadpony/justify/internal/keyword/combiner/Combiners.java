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

package org.leadpony.justify.internal.keyword.combiner;

import java.util.Collection;

import org.leadpony.justify.core.JsonSchema;
import org.leadpony.justify.internal.keyword.Keyword;

/**
 * @author leadpony
 */
public final class Combiners {
    
    public static Combiner allOf(Collection<JsonSchema> subschemas) {
        return new AllOf(subschemas);
    }

    public static Combiner anyOf(Collection<JsonSchema> subschemas) {
        return new AnyOf(subschemas);
    }

    public static Combiner oneOf(Collection<JsonSchema> subschemas) {
        return new OneOf(subschemas);
    }

    public static Combiner not(JsonSchema subschema) {
        return new Not(subschema);
    }

    public static Combiner if_(JsonSchema subschema) {
        return new If(subschema);
    }

    public static Combiner then_(JsonSchema subschema) {
        return new Then(subschema);
    }

    public static Combiner else_(JsonSchema subschema) {
        return new Else(subschema);
    }

    public static Combiner contains(JsonSchema subschema) {
        return new Contains(subschema);
    }
    
    public static Keyword maxContains(int value) {
        return new MaxContains(value);
    }

    public static Keyword minContains(int value) {
        return new MinContains(value);
    }

    private Combiners() {
    }
}