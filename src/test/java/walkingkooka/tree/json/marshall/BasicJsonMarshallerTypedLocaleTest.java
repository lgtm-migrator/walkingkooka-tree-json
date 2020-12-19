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
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeException;

import java.util.Locale;

public final class BasicJsonMarshallerTypedLocaleTest extends BasicJsonMarshallerTypedTestCase<BasicJsonMarshallerTypedLocale, Locale> {

    @Test
    public void testFromBooleanFails() {
        this.unmarshallFailed(JsonNode.booleanNode(true), ClassCastException.class);
    }

    @Test
    public void testFromNumberFails() {
        this.unmarshallFailed(JsonNode.number(1.5), ClassCastException.class);
    }

    @Test
    public void testFromObjectFails() {
        this.unmarshallFailed(JsonNode.object(), ClassCastException.class);
    }

    @Test
    public void testFromArrayFails() {
        this.unmarshallFailed(JsonNode.array(), ClassCastException.class);
    }

    @Override
    BasicJsonMarshallerTypedLocale marshaller() {
        return BasicJsonMarshallerTypedLocale.instance();
    }

    @Override
    Locale value() {
        return Locale.ENGLISH;
    }

    @Override
    JsonNode node() {
        return JsonNode.string(this.value().toLanguageTag());
    }

    @Override
    Locale jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "locale";
    }

    @Override
    Class<Locale> marshallerType() {
        return Locale.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedLocale> type() {
        return BasicJsonMarshallerTypedLocale.class;
    }
}
