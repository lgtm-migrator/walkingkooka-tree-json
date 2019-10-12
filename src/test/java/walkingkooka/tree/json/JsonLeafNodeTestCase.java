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

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class JsonLeafNodeTestCase<N extends JsonLeafNode<V>, V> extends JsonNodeTestCase<N> {

    JsonLeafNodeTestCase() {
        super();
    }

    @Test
    public final void testCreate() {
        final N node = this.createJsonNode();
        assertEquals(Lists.empty(), node.children(), "children");
        this.parentMissingCheck(node);
        this.checkValue(node, this.value());
        this.textAndCheck(node, String.valueOf(this.value()));
    }

    @Test
    public final void testSetNameDifferent() {
        final N node = this.createJsonNode();
        final JsonNodeName originalName = node.name();
        final V value = node.value();

        final JsonNodeName differentName = JsonNodeName.with("different");
        final N different = (N)node.setName(differentName);
        assertEquals(differentName, different.name(), "name");
        this.checkValue(different, value);

        assertEquals(originalName, node.name(), "original name");
        this.checkValue(node, value);
    }

    @Test
    public final void testSetSameValue() {
        final N node = this.createJsonNode();
        assertSame(node, this.setValue(node, this.value()));
    }

    @Test
    public void testSetDifferentValue() {
        final N node = this.createJsonNode();

        final V differentValue = this.differentValue();
        final N different = this.setValue(node, differentValue);
        assertNotSame(node, different);
        this.checkValue(different, differentValue);
        this.parentMissingCheck(different);

        this.checkValue(node, this.value());
    }

    abstract N setValue(final N node, final V value);

    @Test
    public final void testRemoveParentWithParent() {
        final N node = this.createJsonNode();

        final JsonNodeName a = JsonNodeName.with("prop");
        final JsonObjectNode parent = JsonNode.object()
                .set(a, node);
        assertEquals(node, parent.getOrFail(a).removeParent());
    }

    @Test
    public final void testReplace() {
        final JsonNodeName key1 = JsonNodeName.with("key1");
        final JsonNodeName key2 = JsonNodeName.with("key2");

        final N value1 = this.createJsonNode(this.value());
        final N value2 = this.createJsonNode(this.differentValue());

        final JsonObjectNode before = JsonNode.object()
                .set(key1, value1)
                .set(key2, JsonNode.object());

        this.replaceAndCheck(before.getOrFail(key1),
                value2,
                before.set(key1, value2).getOrFail(key1));
    }

    @Test
    public final void testReplace2() {
        final JsonNodeName key1 = JsonNodeName.with("key1");
        final JsonNodeName key2 = JsonNodeName.with("key2");

        final N value1 = this.createJsonNode(this.value());
        final N value2 = this.createJsonNode(this.differentValue());

        final JsonObjectNode before = JsonNode.object()
                .set(key1, JsonNode.object())
                .set(key2, value1);

        this.replaceAndCheck(before.getOrFail(key2),
                value2,
                before.set(key2, value2).getOrFail(key2));
    }

    @Test
    public final void testSetChildrenFails() {
        assertThrows(UnsupportedOperationException.class, () -> this.createJsonNode().setChildren(Lists.empty()));
    }

    @Override
    public void testSetSameAttributes() {
        // Ignored
    }

    @Test
    public final void testText() {
        assertEquals(String.valueOf(this.value()), this.createJsonNode().text());
    }

    @Test
    public final void testArrayOrFailFails() {
        assertThrows(JsonNodeException.class, () -> this.createJsonNode().arrayOrFail());
    }

    @Test
    public final void testObjectOrFailFails() {
        assertThrows(JsonNodeException.class, () -> this.createJsonNode().objectOrFail());
    }

    @Override
    public void testParentWithoutChild() {
    }

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(JsonNode.number(99));
    }

    @Override
    final N createJsonNode() {
        return this.createJsonNode(this.value());
    }

    abstract N createJsonNode(final V value);

    abstract V value();

    abstract V differentValue();

    final void checkValue(final N node, final V value) {
        assertEquals(value, node.value(), "value");
    }
}
