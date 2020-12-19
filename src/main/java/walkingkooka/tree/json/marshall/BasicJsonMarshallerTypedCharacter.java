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

import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;

final class BasicJsonMarshallerTypedCharacter extends BasicJsonMarshallerTyped<Character> {

    static BasicJsonMarshallerTypedCharacter instance() {
        return new BasicJsonMarshallerTypedCharacter();
    }

    private BasicJsonMarshallerTypedCharacter() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<Character> type() {
        return Character.class;
    }

    @Override
    String typeName() {
        return "character";
    }

    @Override
    Character unmarshallNonNull(final JsonNode node,
                                final JsonNodeUnmarshallContext context) {
        final String string = node.stringOrFail();
        final int length = string.length();
        if (1 != length) {
            throw new java.lang.IllegalArgumentException("Character string must have length of 1 not " + length + "=" + CharSequences.quoteAndEscape(string));
        }
        return string.charAt(0);
    }

    @Override
    Character unmarshallNull(final JsonNodeUnmarshallContext context) {
        throw new java.lang.NullPointerException();
    }

    @Override
    JsonNode marshallNonNull(final Character value,
                             final JsonNodeMarshallContext context) {
        return JsonNode.string(value.toString());
    }
}
