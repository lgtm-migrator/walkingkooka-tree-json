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

import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.json.JsonNode;

import java.util.function.Function;

/**
 * A {@link BasicJsonMarshaller} that handles {@link Expression} that have one children.
 */
final class BasicJsonMarshallerTypedExpressionUnaryExpression<N extends Expression> extends BasicJsonMarshallerTypedExpression<N> {

    static <N extends Expression> BasicJsonMarshallerTypedExpressionUnaryExpression<N> with(final Function<Expression, N> from,
                                                                                            final Class<N> expressionType) {

        return new BasicJsonMarshallerTypedExpressionUnaryExpression<>(from, expressionType);
    }

    private BasicJsonMarshallerTypedExpressionUnaryExpression(final Function<Expression, N> from,
                                                              final Class<N> type) {
        super(type);
        this.from = from;
    }

    @Override
    N unmarshallNonNull(final JsonNode node,
                        final JsonNodeUnmarshallContext context) {
        return this.from.apply(context.unmarshallWithType(node));
    }

    private final Function<Expression, N> from;

    @Override
    JsonNode marshallNonNull(final N value,
                             final JsonNodeMarshallContext context) {
        return context.marshallWithType(value.children().get(0));
    }
}
