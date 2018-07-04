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

package org.leadpony.justify.internal.assertion;

import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.leadpony.justify.core.Evaluator;
import org.leadpony.justify.core.InstanceType;
import org.leadpony.justify.core.Problem;
import org.leadpony.justify.internal.base.ProblemBuilder;
import org.leadpony.justify.internal.evaluator.ShallowEvaluator;

/**
 * Assertion specified with "minProperties" keyword.
 * 
 * @author leadpony
 */
class MinProperties extends AbstractAssertion {
    
    private final int bound;
    
    MinProperties(int bound) {
        this.bound = bound;
    }

    @Override
    public String name() {
        return "minProperties";
    }

    @Override
    public boolean canApplyTo(InstanceType type) {
        return type == InstanceType.OBJECT;
    }
    
    @Override
    public Evaluator createEvaluator(InstanceType type) {
        return new InnerEvaluator();
    }

    @Override
    public void addToJson(JsonObjectBuilder builder) {
        builder.add(name(), bound);
    }
    
    @Override
    protected AbstractAssertion createNegatedAssertion() {
        return new MaxProperties(bound - 1);
    }

    private class InnerEvaluator implements ShallowEvaluator {

        private int currentCount;
        
        @Override
        public Result evaluateShallow(Event event, JsonParser parser, int depth, Reporter reporter) {
            if (depth == 1) {
                if (event == Event.KEY_NAME && ++currentCount >= bound) {
                    return Result.TRUE;
                }
            } else if (depth == 0 && event == Event.END_OBJECT) {
                if (currentCount >= bound) {
                    return Result.TRUE;
                } else {
                    Problem p = ProblemBuilder.newBuilder(parser)
                            .withMessage("instance.problem.min.properties")
                            .withParameter("actual", currentCount)
                            .withParameter("bound", bound)
                            .build();
                    reporter.reportProblem(p);
                    return Result.FALSE;
                }
            }
            return Result.PENDING;
        }
    }
}