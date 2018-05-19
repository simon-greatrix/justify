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

package org.leadpony.justify.internal.evaluator;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.leadpony.justify.core.Evaluator;
import org.leadpony.justify.core.Problem;

/**
 * @author leadpony
 */
class ConjunctionEvaluator extends LogicalEvaluator {
    
    private Result finalResult = Result.TRUE;
    
    private ConjunctionEvaluator(Evaluator first, Evaluator second) {
        super(first, second);
    }

    static Evaluator of(Evaluator first, Evaluator second) {
        if (first == Evaluators.ALWAYS_TRUE) {
            return second;
        } else if (second == Evaluators.ALWAYS_TRUE) {
            return first;
        } else {
            return new ConjunctionEvaluator(first, second);
        }
    }
    
    @Override
    public Result evaluate(Event event, JsonParser parser, int depth, Consumer<Problem> consumer) {
        Iterator<Evaluator> it = evaluators.iterator();
        while (it.hasNext()) {
            Evaluator evaluator = it.next();
            Result result = evaluator.evaluate(event, parser, depth, consumer);
            if (result != Result.PENDING) {
                it.remove();
                if (result == Result.FALSE) {
                    this.finalResult = Result.FALSE;
                }
            }
        }
        return evaluators.isEmpty() ? this.finalResult : Result.PENDING;
    }
    
    @Override
    public Evaluator and(Evaluator other) {
        Objects.requireNonNull(other, "other must not be null.");
        if (other != Evaluators.ALWAYS_TRUE) {
            append(other);
        }
        return this;
    }
}
