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
package org.leadpony.justify.internal.base;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * A utility class operating on {@link Set} instances.
 *
 * @author leadpony
 */
public final class Sets {

    public static <E> Set<E> newIdentitySet() {
        return Collections.newSetFromMap(
                new IdentityHashMap<E, Boolean>());
    }

    private Sets() {
    }
}
