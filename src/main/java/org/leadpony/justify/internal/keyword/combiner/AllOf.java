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

import org.leadpony.justify.core.InstanceType;
import org.leadpony.justify.core.JsonSchema;
import org.leadpony.justify.internal.evaluator.DefaultEvaluatorFactory;
import org.leadpony.justify.internal.evaluator.LogicalEvaluator.Builder;
import org.leadpony.justify.internal.keyword.Keyword;

/**
 * Boolean logic specified with "allOf" validation keyword.
 *
 * @author leadpony
 */
class AllOf extends NaryBooleanLogic {
    
    AllOf(Collection<JsonSchema> subschemas) {
        super(subschemas);
    }

    @Override
    public String name() {
        return "allOf";
    }
    
    @Override
    public Keyword negate() {
        return new AnyOf(negateSubschemas());
    }

    @Override
    protected Builder createEvaluatorBuilder(InstanceType type) {
        return DefaultEvaluatorFactory.SINGLETON.createConjunctionEvaluatorBuilder(type);
    }
}
