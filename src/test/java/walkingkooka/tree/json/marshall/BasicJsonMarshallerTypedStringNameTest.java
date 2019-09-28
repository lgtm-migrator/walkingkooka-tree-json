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

import walkingkooka.naming.Names;
import walkingkooka.naming.StringName;
import walkingkooka.tree.json.JsonNode;

public final class BasicJsonMarshallerTypedStringNameTest extends BasicJsonMarshallerTypedTestCase2<BasicJsonMarshallerTypedStringName, StringName> {

    @Override
    BasicJsonMarshallerTypedStringName marshaller() {
        return BasicJsonMarshallerTypedStringName.instance();
    }

    @Override
    StringName value() {
        return Names.string("abc123");
    }

    @Override
    JsonNode node() {
        return JsonNode.string(this.value().toString());
    }

    @Override
    StringName jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "string-name";
    }

    @Override
    Class<StringName> marshallerType() {
        return StringName.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedStringName> type() {
        return BasicJsonMarshallerTypedStringName.class;
    }
}
