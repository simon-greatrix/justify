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

import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.leadpony.justify.core.Evaluator;
import org.leadpony.justify.core.InstanceType;

/**
 * Assertion only inspecting events occurred at current depth.
 * 
 * @author leadpony
 */
public abstract class ShallowAssertion extends AbstractAssertion implements Evaluator {

    @Override
    public Evaluator createEvaluator(InstanceType type) {
        return this;
    }
    
    @Override
    public Result evaluate(Event event, JsonParser parser, int depth, ProblemReporter reporter) {
        if (depth <= 1) {
            return evaluateShallow(event, parser, depth, reporter);
        } else {
            return Result.PENDING;
        }
    }
    
    protected abstract Result evaluateShallow(Event event, JsonParser parser, int depth, ProblemReporter reporter);
}