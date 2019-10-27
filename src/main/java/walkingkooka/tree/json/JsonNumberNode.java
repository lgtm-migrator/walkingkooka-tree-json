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

import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.search.SearchNode;

/**
 * Represents an immutable json number.
 */
public final class JsonNumberNode extends JsonLeafNonNullNode<Double> {

    static JsonNumberNode with(final double value) {
        return new JsonNumberNode(NAME, NO_INDEX, value);
    }

    private final static JsonNodeName NAME = JsonNodeName.fromClass(JsonNumberNode.class);


    private JsonNumberNode(final JsonNodeName name, final int index, final double value) {
        super(name, index, value);
    }

    @Override
    public JsonNumberNode setName(final JsonNodeName name) {
        checkName(name);
        return this.setName0(name)
                .cast(JsonNumberNode.class);
    }

    public JsonNumberNode setValue(final double value) {
        return this.setValue0(value)
                .cast(JsonNumberNode.class);
    }

    @Override
    JsonNumberNode replace0(final JsonNodeName name, final int index, final Double value) {
        return new JsonNumberNode(name, index, value);
    }

    /**
     * Returns a {@link JsonNumberNode} with the same value.
     */
    @Override
    public JsonNumberNode removeParent() {
        return this.removeParent0()
                .cast(JsonNumberNode.class);
    }

    @Override
    JsonNodeName defaultName() {
        return NAME;
    }

    // HasText......................................................................................................

    @Override
    public String text() {
        return this.value.toString();
    }

    // HasSearchNode...............................................................................................

    @Override
    public SearchNode toSearchNode() {
        final String text = this.text();
        return SearchNode.doubleSearchNode(text, this.value());
    }

    // Visitor .........................................................................................................

    @Override
    public void accept(final JsonNodeVisitor visitor) {
        visitor.visit(this);
    }

    // JsonNode .................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonNumberNode;
    }

    @Override
    void printJson0(final IndentingPrinter printer) {
        final int i = this.value.intValue();
        printer.print(i == this.value ?
                String.valueOf(i) :
                String.valueOf(this.value));
    }
}
