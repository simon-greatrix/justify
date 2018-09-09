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

package org.leadpony.justify.internal.keyword.assertion.format;

import java.util.function.Consumer;

import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.leadpony.justify.core.Evaluator;
import org.leadpony.justify.core.InstanceType;
import org.leadpony.justify.core.Problem;
import org.leadpony.justify.core.spi.FormatAttribute;
import org.leadpony.justify.internal.base.ProblemBuilder;
import org.leadpony.justify.internal.evaluator.Evaluators;
import org.leadpony.justify.internal.keyword.assertion.AbstractAssertion;

/**
 * Assertion representing "format" keyword.
 * 
 * @author leadpony
 */
public class Format extends AbstractAssertion implements Evaluator {
    
    private final FormatAttribute attribute;
    
    public Format(FormatAttribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public String name() {
        return "format";
    }

    @Override
    public Evaluator createEvaluator(InstanceType type, JsonBuilderFactory builderFactory, boolean affirmative) {
        if (type != attribute.valueType()) {
            return Evaluators.ALWAYS_IGNORED;
        }
        return affirmative ? this : this::evaluateNegated;
    }
    
    @Override
    public void addToJson(JsonObjectBuilder builder, JsonBuilderFactory builderFactory) {
        builder.add(name(), attribute.name());
    }
    
    @Override
    public Result evaluate(Event event, JsonParser parser, int depth, Consumer<Problem> reporter) {
        JsonValue value = parser.getValue();
        if (attribute.test(value)) {
            return Result.TRUE;
        } else {
            ProblemBuilder builder = createProblemBuilder(parser)
                    .withMessage("instance.problem.format");
            reporter.accept(builder.build());
            return Result.FALSE;
        }
    }

    private Result evaluateNegated(Event event, JsonParser parser, int depth, Consumer<Problem> reporter) {
        JsonValue value = parser.getValue();
        if (attribute.test(value)) {
            ProblemBuilder builder = createProblemBuilder(parser)
                    .withMessage("instance.problem.not.format");
            reporter.accept(builder.build());
            return Result.FALSE;
        } else {
            return Result.TRUE;
        }
    }

    @Override
    public ProblemBuilder createProblemBuilder(JsonParser parser) {
        return super.createProblemBuilder(parser)
                    .withParameter("attribute", attribute.name());
    }
}