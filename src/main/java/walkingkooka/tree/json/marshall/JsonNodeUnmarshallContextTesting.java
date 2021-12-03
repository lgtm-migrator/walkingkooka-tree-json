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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface JsonNodeUnmarshallContextTesting<C extends JsonNodeUnmarshallContext> extends JsonNodeContextTesting<C> {

    // setPreProcessor..................................................................................................

    @Test
    default void testSetPreProcessorNullFails() {
        assertThrows(java.lang.NullPointerException.class, () -> this.createContext().setPreProcessor(null));
    }

    @Test
    default void testSetPreProcessor() {
        final JsonNodeUnmarshallContext context = this.createContext();
        final BiFunction<JsonNode, Class<?>, JsonNode> processor = (json, type) -> json;

        final JsonNodeUnmarshallContext with = context.setPreProcessor(processor);
        assertNotSame(context, with);
    }

    @Test
    default void testSetPreProcessorSame() {
        final JsonNodeUnmarshallContext context = this.createContext();
        final BiFunction<JsonNode, Class<?>, JsonNode> processor = (json, type) -> json;

        final JsonNodeUnmarshallContext with = context.setPreProcessor(processor);
        assertSame(with, with.setPreProcessor(processor));
    }

    // unmarshall.....................................................................................................

    @Test
    default void testFromJsonNodeNullTypeFails() {
        assertThrows(java.lang.NullPointerException.class, () -> this.createContext().unmarshall(JsonNode.nullNode(), null));
    }

    // unmarshallList.................................................................................................

    @Test
    default void testFromJsonNodeListNullTypeFails() {
        assertThrows(java.lang.NullPointerException.class, () -> this.createContext().unmarshallList(JsonNode.nullNode(), null));
    }

    // unmarshallSet..................................................................................................

    @Test
    default void testFromJsonNodeSetNullTypeFails() {
        assertThrows(java.lang.NullPointerException.class, () -> this.createContext().unmarshallSet(JsonNode.nullNode(), null));
    }

    // unmarshallMap..................................................................................................

    @Test
    default void testFromJsonNodeMapNullKeyTypeFails() {
        assertThrows(java.lang.NullPointerException.class, () -> this.createContext().unmarshallMap(JsonNode.array(), null, String.class));
    }

    @Test
    default void testFromJsonNodeMapNullValueTypeFails() {
        assertThrows(java.lang.NullPointerException.class, () -> this.createContext().unmarshallMap(JsonNode.array(), String.class, null));
    }

    // unmarshall.....................................................................................................

    default <T> void unmarshallAndCheck(final JsonNode node,
                                        final Class<T> type,
                                        final T expected) {
        this.unmarshallAndCheck(this.createContext(),
                node,
                type,
                expected);
    }

    default <T> void unmarshallAndCheck(final JsonNodeUnmarshallContext context,
                                        final JsonNode node,
                                        final Class<T> type,
                                        final T expected) {
        this.checkEquals(expected,
                context.unmarshall(node, type),
                () -> context + " unmarshall " + node + " type " + type.getName());
    }

    // unmarshallList.................................................................................................

    default <T> void unmarshallListAndCheck(final JsonNode node,
                                            final Class<T> type,
                                            final List<T> expected) {
        this.unmarshallListAndCheck(this.createContext(),
                node,
                type,
                expected);
    }

    default <T> void unmarshallListAndCheck(final JsonNodeUnmarshallContext context,
                                            final JsonNode node,
                                            final Class<T> type,
                                            final List<T> expected) {
        this.checkEquals(expected,
                context.unmarshallList(node, type),
                () -> context + " unmarshallList " + node + " type " + type.getName());
    }

    // unmarshallSet..................................................................................................

    default <T> void unmarshallSetAndCheck(final JsonNode node,
                                           final Class<T> type,
                                           final Set<T> expected) {
        this.unmarshallSetAndCheck(this.createContext(),
                node,
                type,
                expected);
    }

    default <T> void unmarshallSetAndCheck(final JsonNodeUnmarshallContext context,
                                           final JsonNode node,
                                           final Class<T> type,
                                           final Set<T> expected) {
        this.checkEquals(expected,
                context.unmarshallSet(node, type),
                () -> context + " unmarshallSet " + node + " type " + type.getName());
    }

    // unmarshallMap.................................................................................................

    default <K, V> void unmarshallMapAndCheck(final JsonNode node,
                                              final Class<K> key,
                                              final Class<V> value,
                                              final Map<K, V> expected) {
        this.unmarshallMapAndCheck(this.createContext(),
                node,
                key,
                value,
                expected);
    }

    default <K, V> void unmarshallMapAndCheck(final JsonNodeUnmarshallContext context,
                                              final JsonNode node,
                                              final Class<K> key,
                                              final Class<V> value,
                                              final Map<K, V> expected) {
        this.checkEquals(expected,
                context.unmarshallMap(node, key, value),
                () -> context + " unmarshallMap " + node + " key " + key.getName() + " value " + value.getName());
    }

    // unmarshallWithType.............................................................................................

    default void unmarshallWithTypeAndCheck(final JsonNode node,
                                            final Object expected) {
        this.unmarshallWithTypeAndCheck(this.createContext(),
                node,
                expected);
    }

    default void unmarshallWithTypeAndCheck(final JsonNodeUnmarshallContext context,
                                            final JsonNode node,
                                            final Object expected) {
        this.checkEquals(expected,
                context.unmarshallWithType(node),
                () -> context + " unmarshallWithType " + node);
    }

    // unmarshallWithTypeList..........................................................................................

    default void unmarshallWithTypeListAndCheck(final JsonNode node,
                                                final List<?> expected) {
        this.unmarshallWithTypeListAndCheck(this.createContext(),
                node,
                expected);
    }

    default void unmarshallWithTypeListAndCheck(final JsonNodeUnmarshallContext context,
                                                final JsonNode node,
                                                final List<?> expected) {
        this.checkEquals(expected,
                context.unmarshallWithTypeList(node),
                () -> context + " unmarshallWithTypeList " + node);
    }

    // unmarshallWithTypeSet..........................................................................................

    default void unmarshallWithTypeSetAndCheck(final JsonNode node,
                                               final Set<?> expected) {
        this.unmarshallWithTypeSetAndCheck(this.createContext(),
                node,
                expected);
    }

    default void unmarshallWithTypeSetAndCheck(final JsonNodeUnmarshallContext context,
                                               final JsonNode node,
                                               final Set<?> expected) {
        this.checkEquals(expected,
                context.unmarshallWithTypeSet(node),
                () -> context + " unmarshallWithTypeSet " + node);
    }

    // unmarshallMap.................................................................................................

    default void unmarshallWithTypeMapAndCheck(final JsonNode node,
                                               final Map<?, ?> expected) {
        this.unmarshallMapWithTypeAndCheck(this.createContext(),
                node,
                expected);
    }

    default void unmarshallMapWithTypeAndCheck(final JsonNodeUnmarshallContext context,
                                               final JsonNode node,
                                               final Map<?, ?> expected) {
        this.checkEquals(expected,
                context.unmarshallWithTypeMap(node),
                () -> context + " unmarshallWithTypeMap " + node);
    }

    // TypeNameTesting..................................................................................................

    @Override
    default String typeNameSuffix() {
        return JsonNodeUnmarshallContext.class.getSimpleName();
    }
}
