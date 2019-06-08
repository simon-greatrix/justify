/*
 * Copyright 2018-2019 the Justify authors.
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

package org.leadpony.justify.internal.keyword.assertion.format;

import java.util.List;
import java.util.Map;

import javax.json.JsonValue;
import javax.json.spi.JsonProvider;

import org.leadpony.justify.internal.keyword.Evaluatable;
import org.leadpony.justify.internal.keyword.Keyword;
import org.leadpony.justify.internal.keyword.assertion.AbstractAssertion;
import org.leadpony.justify.spi.FormatAttribute;

/**
 * An assertion representing "format" keyword.
 *
 * @author leadpony
 */
public class Format extends AbstractAssertion {

    private final String attribute;

    public static Format of(String attribute) {
        return new Format(attribute);
    }

    public static Format of(FormatAttribute attribute) {
        return new EvaluatableFormat(attribute);
    }

    protected Format(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String name() {
        return "format";
    }

    @Override
    public JsonValue getValueAsJson(JsonProvider jsonProvider) {
        return jsonProvider.createValue(attribute);
    }

    @Override
    public void addToEvaluatables(List<Evaluatable> evaluatables, Map<String, Keyword> keywords) {
    }
}
