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

/**
 * Problem specification.
 * 
 * @author leadpony
 */
class ProblemSpec {

    private final long line;
    private final long column;
    private final String keyword;
    
    ProblemSpec(long line, long column, String keyword) {
        this.line = line;
        this.column = column;
        this.keyword = keyword;
    }
    
    long lineNumber() {
        return line;
    }
    
    long columnNumber() {
        return column;
    }
    
    String keyword() {
        return keyword;
    }
}
