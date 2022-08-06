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
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.CallExpression;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.FunctionExpressionName;
import walkingkooka.tree.expression.NamedFunctionExpression;
import walkingkooka.tree.json.JsonNode;

import java.util.List;

public final class BasicJsonMarshallerTypedExpressionCallTest extends BasicJsonMarshallerTypedExpressionTestCase<BasicJsonMarshallerTypedExpressionCall, CallExpression> {

    private final static ExpressionNumberKind KIND = ExpressionNumberKind.DEFAULT;

    @Test
    public void testFromBooleanFails() {
        this.unmarshallFailed(
                JsonNode.booleanNode(true),
                ClassCastException.class
        );
    }

    @Test
    public void testFromNumberFails() {
        this.unmarshallFailed(
                JsonNode.number(123),
                ClassCastException.class
        );
    }

    @Test
    public void testFromStringFails() {
        this.unmarshallFailed(
                JsonNode.string("abc123"),
                ClassCastException.class);
    }

    @Override
    BasicJsonMarshallerTypedExpressionCall marshaller() {
        return BasicJsonMarshallerTypedExpressionCall.instance();
    }

    private final static String FUNCTION_NAME = "function123";

    @Override
    CallExpression value() {
        return Expression.call(
                this.namedFunction(),
                this.parameters()
        );
    }

    private NamedFunctionExpression namedFunction() {
        return Expression.namedFunction(
                FunctionExpressionName.with(FUNCTION_NAME)
        );
    }

    private List<Expression> parameters() {
        return Lists.of(
                Expression.value(KIND.create(11)),
                Expression.value("b2"),
                Expression.add(
                        Expression.value(KIND.create(3)),
                        Expression.value(KIND.create(33))
                )
        );
    }

    @Override
    JsonNode node() {
        final JsonNodeMarshallContext context = this.marshallContext();

        return JsonNode.object()
                .set(BasicJsonMarshallerTypedExpressionCall.CALLABLE_PROPERTY, context.marshallWithType(this.namedFunction()))
                .set(BasicJsonMarshallerTypedExpressionCall.PARAMETERS_PROPERTY, context.marshallWithTypeCollection(this.parameters()));
    }

    @Override
    String typeName() {
        return "call-expression";
    }

    @Override
    Class<CallExpression> marshallerType() {
        return CallExpression.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionCall> type() {
        return BasicJsonMarshallerTypedExpressionCall.class;
    }
}