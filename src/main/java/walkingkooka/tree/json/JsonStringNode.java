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

package walkingkooka.tree.json;

import walkingkooka.text.CharSequences;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.search.SearchNode;

import java.util.Objects;

/**
 * Represents an immutable json string.
 */
public final class JsonStringNode extends JsonLeafNonNullNode<String> {

    static JsonStringNode with(final String value) {
        Objects.requireNonNull(value, "value");
        return new JsonStringNode(NAME, NO_INDEX, value);
    }

    private final static JsonNodeName NAME = JsonNodeName.fromClass(JsonStringNode.class);

    private JsonStringNode(final JsonNodeName name, final int index, final String value) {
        super(name, index, value);
    }

    @Override
    public JsonStringNode setName(final JsonNodeName name) {
        checkName(name);
        return this.setName0(name).cast();
    }

    public JsonStringNode setValue(final String value) {
        return this.setValue0(value).cast();
    }

    @Override
    JsonStringNode replace0(final JsonNodeName name, final int index, final String value) {
        return new JsonStringNode(name, index, value);
    }

    /**
     * Returns a {@link JsonNumberNode} with the same value.
     */
    @Override
    public JsonStringNode removeParent() {
        return this.removeParent0().cast();
    }

    @Override
    JsonNodeName defaultName() {
        return NAME;
    }

    // HasText......................................................................................................

    @Override
    public String text() {
        return this.value;
    }

    // HasSearchNode...............................................................................................

    @Override
    public SearchNode toSearchNode() {
        final String text = this.text();
        return SearchNode.text(text, this.value());
    }

    // JsonNodeVisitor .................................................................................................

    @Override
    public void accept(final JsonNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonStringNode;
    }

    @Override
    void printJson0(final IndentingPrinter printer) {
        printer.print(CharSequences.quoteAndEscape(this.value));
    }
}
