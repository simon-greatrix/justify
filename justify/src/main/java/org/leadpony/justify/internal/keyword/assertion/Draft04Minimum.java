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
package org.leadpony.justify.internal.keyword.assertion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.json.JsonValue;
import javax.json.spi.JsonProvider;

import org.leadpony.justify.internal.base.Message;
import org.leadpony.justify.internal.keyword.AbstractKeyword;
import org.leadpony.justify.internal.keyword.Evaluatable;
import org.leadpony.justify.internal.keyword.Keyword;

/**
 * An assertion keyword representing "minimum" for Draft-04.
 *
 * @author leadpony
 */
public class Draft04Minimum extends Maximum {

    private boolean exclusive = false;

    public Draft04Minimum(BigDecimal limit) {
        super(limit);
    }

    @Override
    public void addToEvaluatables(List<Evaluatable> evaluatables, Map<String, Keyword> keywords) {
        if (keywords.containsKey("exclusiveMinimum")) {
            ExclusiveMinimum keyword = (ExclusiveMinimum) keywords.get("exclusiveMinimum");
            exclusive = keyword.value;
        }
        super.addToEvaluatables(evaluatables, keywords);
    }

    @Override
    protected boolean testValue(BigDecimal actual, BigDecimal limit) {
        if (this.exclusive) {
            return actual.compareTo(limit) > 0;
        } else {
            return actual.compareTo(limit) >= 0;
        }
    }

    @Override
    protected Message getMessageForTest() {
        return exclusive ?
                Message.INSTANCE_PROBLEM_EXCLUSIVEMINIMUM :
                Message.INSTANCE_PROBLEM_MINIMUM;
    }

    @Override
    protected Message getMessageForNegatedTest() {
        return exclusive ?
                Message.INSTANCE_PROBLEM_MAXIMUM :
                Message.INSTANCE_PROBLEM_EXCLUSIVEMAXIMUM;
    }

    public static class ExclusiveMinimum extends AbstractKeyword {

        private final boolean value;

        public ExclusiveMinimum(boolean value) {
            this.value = value;
        }

        @Override
        public String name() {
            return "exclusiveMinimum";
        }

        @Override
        public JsonValue getValueAsJson(JsonProvider jsonProvider) {
            return value ? JsonValue.TRUE : JsonValue.FALSE;
        }
    }
}
