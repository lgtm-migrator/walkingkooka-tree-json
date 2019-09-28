/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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
 *
 */

package walkingkooka.tree.json.marshall;

import org.junit.jupiter.api.Test;
import walkingkooka.test.ToStringTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeException;

public abstract class BasicJsonMarshallerTypedNumberTestCase<M extends BasicJsonMarshallerTypedNumber<T>, T extends Number> extends BasicJsonMarshallerTypedTestCase<M, T>
        implements ToStringTesting<M> {

    BasicJsonMarshallerTypedNumberTestCase() {
        super();
    }

    @Test
    public final void testFromArrayFails() {
        this.unmarshallFailed(JsonNode.array(), JsonNodeException.class);
    }

    @Test
    public final void testFromBooleanFails() {
        this.unmarshallFailed(JsonNode.booleanNode(true), JsonNodeException.class);
    }

    @Test
    public final void testFromObjectFails() {
        this.unmarshallFailed(JsonNode.object(), JsonNodeException.class);
    }

    @Test
    public final void testFromStringFails() {
        this.unmarshallFailed(JsonNode.string("abc123"), JsonNodeException.class);
    }
}