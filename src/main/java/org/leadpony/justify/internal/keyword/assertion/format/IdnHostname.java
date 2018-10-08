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

/**
 * Format attribute representing "idn-hostname" attribute.
 * 
 * @author leadpony
 * 
 * @see <a href="https://tools.ietf.org/html/rfc5890">RFC 5890</a>
 * @see <a href="https://tools.ietf.org/html/rfc5892">RFC 5892</a>
 */
public class IdnHostname extends AbstractFormatAttribute {

    @Override
    public String name() {
        return "idn-hostname";
    }

    @Override
    public boolean test(String value) {
        return new IdnHostnameMatcher(value).matches();
    }
}
