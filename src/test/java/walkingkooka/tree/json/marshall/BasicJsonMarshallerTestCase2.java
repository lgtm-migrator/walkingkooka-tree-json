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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import walkingkooka.ToStringTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class BasicJsonMarshallerTestCase2<M extends BasicJsonMarshaller<T>, T> extends BasicJsonMarshallerTestCase<M>
        implements ToStringTesting<M> {

    BasicJsonMarshallerTestCase2() {
        super();
    }

    @Test
    public final void testType() {
        final M marshaller = this.marshaller();
        assertEquals(this.marshallerType(), marshaller.type(), () -> ".type failed for " + marshaller);
    }

    abstract Class<T> marshallerType();

    @Test
    public final void testFromJsonNodeNullFails() {
        assertThrows(NullPointerException.class, () -> this.marshaller().unmarshall(null, this.unmarshallContext()));
    }

    @Test
    public void testFromJsonNodeJsonNullNode() {
        this.unmarshallAndCheck(JsonNode.nullNode(), this.jsonNullNode());
    }

    abstract T jsonNullNode();

    @Test
    public final void testFromJsonNode() {
        this.unmarshallAndCheck(this.node(), this.value());
    }

    @Test
    public final void testFromJsonNodeWithType() {
        this.unmarshallWithTypeAndCheck(this.nodeWithType(), this.value());
    }

    @Test
    public final void testMarshallWithNull() {
        this.marshallAndCheck(null, JsonNode.nullNode());
    }

    @Test
    public final void testMarshall() {
        this.marshallAndCheck(this.value(), this.node());
    }

    @Test
    public final void testMarshallWithTypeNull() {
        this.marshallWithTypeAndCheck(null, JsonNode.nullNode());
    }

    @Test
    public final void testMarshallWithType() {
        this.marshallWithTypeAndCheck(this.value(), this.nodeWithType());
    }

    @Test
    public final void testRoundtripMarshallWithTypeFromJsonNodeWithType() {
        final T value = this.value();

        final JsonNode json = this.marshaller()
                .marshallWithType(value, this.marshallContext());

        assertEquals(value,
                this.unmarshallContext().unmarshallWithType(json),
                () -> "roundtrip starting with value failed fromValue: " + value + " -> json: " + json);
    }

    @Test
    public final void testRoundtripFromJsonNodeWithTypeMapperMarshallWithType() {
        final JsonNode json = this.nodeWithType();

        final T value = this.unmarshallContext().
                unmarshallWithType(json);

        assertEquals(json,
                this.marshaller().marshallWithType(value, this.marshallContext()),
                () -> "roundtrip starting with node failed, json: " + json + " -> value:: " + value);
    }

    @Test
    public final void testRoundtripMarshallWithTypeObjectFromJsonNodeWithType() {
        final T value = this.value();

        final JsonNode json = this.marshaller()
                .marshallWithType(value, this.marshallContext());

        assertEquals(value,
                this.unmarshallContext().unmarshallWithType(json),
                () -> "roundtrip starting with value failed, value: " + value + " -> json: " + json);
    }

    @Test
    public void testRoundtripList() {
        final T value = this.value();
        final List<T> list = List.of(value);

        final JsonNode jsonNode = this.marshallContext().marshallList(list);

        assertEquals(list,
                this.unmarshallContext().unmarshallList(jsonNode, value.getClass()),
                () -> "roundtrip list: " + list + " -> json: " + jsonNode);
    }

    @Test
    public void testRoundtripSet() {
        final T value = this.value();
        final Set<T> set = Set.of(value);

        final JsonNode jsonNode = this.marshallContext().marshallSet(set);

        try {
            set.equals(this.unmarshallContext().unmarshallSet(jsonNode, value.getClass()));
        } catch (final Exception e) {
            e.printStackTrace();
        }


        assertEquals(set,
                this.unmarshallContext().unmarshallSet(jsonNode, value.getClass()),
                () -> "roundtrip set: " + set + " -> json: " + jsonNode);
    }

    @Test
    public void testRoundtripMap() {
        final T value = this.value();
        final Map<String, T> map = Map.of("key1", value);

        final JsonNode jsonNode = this.marshallContext().marshallMap(map);

        assertEquals(map,
                this.unmarshallContext().unmarshallMap(jsonNode, String.class, value.getClass()),
                () -> "roundtrip marshall: " + map + " -> json: " + jsonNode);
    }

    @Test
    public void testRoundtripTypeList() {
        final List<T> list = List.of(this.value());

        final JsonNode jsonNode = this.marshallContext().marshallWithTypeList(list);

        assertEquals(list,
                this.unmarshallContext().unmarshallWithTypeList(jsonNode),
                () -> "roundtrip list: " + list + " -> json: " + jsonNode);
    }

    @Test
    public void testRoundtripTypeSet() {
        final Set<T> set = Set.of(this.value());

        final JsonNode jsonNode = this.marshallContext().marshallWithTypeSet(set);

        assertEquals(set,
                this.unmarshallContext().unmarshallWithTypeSet(jsonNode),
                () -> "roundtrip set: " + set + " -> json: " + jsonNode);
    }

    @Test
    public void testRoundtripTypeMap() {
        final Map<String, T> map = Map.of("key1", this.value());

        final JsonNode jsonNode = this.marshallContext().marshallWithTypeMap(map);

        assertEquals(map,
                this.unmarshallContext().unmarshallWithTypeMap(jsonNode),
                () -> "roundtrip marshall: " + map + " -> json: " + jsonNode);
    }

    @Test
    public final void testToString() {
        this.toStringAndCheck(this.marshaller(), this.typeName());
    }

    abstract M marshaller();

    abstract T value();

    abstract boolean requiresTypeName();

    abstract JsonNode node();

    final JsonNode nodeWithType() {
        final JsonNode node = this.node();
        return this.requiresTypeName() ?
                this.typeAndValue(node) :
                node;
    }

    final void unmarshallFailed(final JsonNode node, final Class<? extends Throwable> wrapped) {
        final JsonNodeUnmarshallContext context = this.unmarshallContext();

        if (NullPointerException.class == wrapped) {
            assertThrows(NullPointerException.class, () -> this.marshaller().unmarshall(node, context));
        } else {
            final JsonNodeUnmarshallException from = assertThrows(JsonNodeUnmarshallException.class, () -> this.marshaller().unmarshall(node, context));
            final Throwable cause = from.getCause();
            if (null == wrapped) {
                from.printStackTrace();
                Assertions.assertEquals(null, cause, "Cause");
            } else {
                if (wrapped != cause.getClass()) {
                    from.printStackTrace();
                    Assertions.assertEquals(wrapped, cause, "Wrong cause type");
                }
            }
        }
    }

    final void unmarshallAndCheck(final JsonNode node, final T value) {
        this.unmarshallAndCheck(this.marshaller(), node, value);
    }

    final void unmarshallAndCheck(final BasicJsonMarshaller<T> marshaller,
                                  final JsonNode node,
                                  final T value) {
        assertEquals(value,
                marshaller.unmarshall(node, this.unmarshallContext()),
                () -> "unmarshall failed " + node);
    }

    final void unmarshallWithTypeAndCheck(final JsonNode node,
                                          final T value) {
        assertEquals(value,
                this.unmarshallContext().unmarshallWithType(node),
                () -> "unmarshall failed " + node);
    }

    final void marshallWithTypeFailed(final T value,
                                      final Class<? extends Throwable> thrown) {
        assertThrows(thrown, () -> this.marshaller().marshallWithType(value, this.marshallContext()));
    }

    final void marshallAndCheck(final T value,
                                final JsonNode node) {
        this.marshallAndCheck(this.marshaller(), value, node);
    }

    final void marshallAndCheck(final BasicJsonMarshaller<T> marshaller,
                                final T value,
                                final JsonNode node) {
        assertEquals(node,
                marshaller.marshall(value, this.marshallContext()),
                () -> "marshall failed " + node);
    }

    final void marshallWithTypeAndCheck(final T value,
                                        final JsonNode node) {
        this.marshallWithTypeAndCheck(this.marshaller(), value, node);
    }

    final void marshallWithTypeAndCheck(final BasicJsonMarshaller<T> marshaller,
                                        final T value,
                                        final JsonNode node) {
        assertEquals(node,
                marshaller.marshallWithType(value, this.marshallContext()),
                () -> "marshallWithType failed " + node);
    }

    abstract String typeName();

    final JsonObject typeAndValue(final JsonNode value) {
        return this.typeAndValue(this.typeName(), value);
    }

    final JsonNodeUnmarshallContext unmarshallContext() {
        return BasicJsonNodeUnmarshallContext.INSTANCE;
    }

    final JsonNodeMarshallContext marshallContext() {
        return BasicJsonNodeMarshallContext.INSTANCE;
    }
}
